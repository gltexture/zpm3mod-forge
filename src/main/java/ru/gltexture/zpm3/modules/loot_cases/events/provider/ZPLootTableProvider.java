/*
 *
 *  * zpm3forge
 *  * Copyright (C) 2026 gltexture
 *  *
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program. If not, see <https://www.gnu.org/licenses/>.
 *
 */

package ru.gltexture.zpm3.modules.loot_cases.events.provider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.modules.loot_cases.loot_tables.ZPLootTable;
import ru.gltexture.zpm3.modules.loot_cases.registry.ZPLootTablesRegistry;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ZPLootTableProvider implements DataProvider {
    public static final String indexFile = "index.json";

    private final PackOutput output;
    private final Gson gson;

    public ZPLootTableProvider(PackOutput output) {
        this.output = output;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public @NotNull CompletableFuture<?> run(@NotNull CachedOutput cache) {
        Path folder = this.output.getOutputFolder(PackOutput.Target.DATA_PACK).resolve(ZombiePlague3.MOD_ID()).resolve("zp_loot_tables");

        List<String> allIds = new ArrayList<>();
        List<ZPLootTable> allTables = new ArrayList<>();

        List<ZPLootTablesRegistry> regs = ZPLootTablesRegistry.ALL_REG();
        regs.forEach(reg -> {
            reg.init();
            allTables.addAll(reg.getZpLootTableList());
        });
        ZPLootTablesRegistry.CLEAR_REG();
        
        List<CompletableFuture<?>> futures = new ArrayList<>();

        for (ZPLootTable table : allTables) {
            String id = table.getUniqueId();
            allIds.add(id);

            Path file = folder.resolve(id + ".json");

            String json = gson.toJson(table);
            futures.add(DataProvider.saveStable(cache, JsonParser.parseString(json), file));
        }

        {
            Path index = folder.resolve(ZPLootTableProvider.indexFile);
            String jsonIndex = gson.toJson(allIds);
            futures.add(DataProvider.saveStable(cache, JsonParser.parseString(jsonIndex), index));
        }

        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }

    @Override
    public @NotNull String getName() {
        return "ZP LootTables Provider";
    }
}
