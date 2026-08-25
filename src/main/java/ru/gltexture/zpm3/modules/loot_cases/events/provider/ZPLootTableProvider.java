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
import com.google.gson.JsonElement;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.injection.struct.InjectorGroupInfo;
import ru.gltexture.zpm3.modules.loot_cases.loot_tables.ZPLootTable;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.modules.loot_cases.loot_tables.nbt.values.ZPLootNbtCompoundTag;
import ru.gltexture.zpm3.modules.loot_cases.loot_tables.nbt.values.ZPLootNbtListTag;
import ru.gltexture.zpm3.modules.loot_cases.loot_tables.synthetic.ZPSyntheticLootCaseDescription;
import ru.gltexture.zpm3.modules.loot_cases.reload.ZPLootTableExtensionsReloadListener;
import ru.gltexture.zpm3.modules.loot_cases.reload.gson.ZPLootNBTCompTagSerializer;
import ru.gltexture.zpm3.modules.loot_cases.reload.gson.ZPLootNBTListCompTagSerializer;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ZPLootTableProvider implements DataProvider {
    public static final String indexFile = "index.json";
    public static final String lootTables = "zp_loot_tables";
    public static final String lootTablesExtensions = "zp_loot_tables_extensions";
    public static final String lootCases = "zp_loot_cases";

    private final PackOutput output;
    private final Gson gson;

    public ZPLootTableProvider(PackOutput output) {
        this.output = output;
        this.gson = new GsonBuilder().setPrettyPrinting()
                .registerTypeAdapter(ZPLootNbtCompoundTag.class, new ZPLootNBTCompTagSerializer())
                .registerTypeAdapter(ZPLootNbtListTag.class, new ZPLootNBTListCompTagSerializer())
                .create();
    }

    @Override
    public @NotNull CompletableFuture<?> run(@NotNull CachedOutput cache) {
        final Path tablesFolder = this.output.getOutputFolder(PackOutput.Target.DATA_PACK).resolve(ZombiePlague3.MOD_ID()).resolve(ZPLootTableProvider.lootTables);
        final Path casesFolder = this.output.getOutputFolder(PackOutput.Target.DATA_PACK).resolve(ZombiePlague3.MOD_ID()).resolve(ZPLootTableProvider.lootCases);
        final Path lootExtensionsFolder = this.output.getOutputFolder(PackOutput.Target.DATA_PACK).resolve(ZombiePlague3.MOD_ID()).resolve(ZPLootTableProvider.lootTablesExtensions);
        final List<CompletableFuture<?>> futures = new ArrayList<>();

        for (ZPLootTable lootTable : ZPSyntheticLootCasesDataGenRegistry.getDataToGenTables()) {
            final String id = lootTable.getUniqueId();
            final Path file = tablesFolder.resolve(id + ".json");
            final JsonElement json = this.gson.toJsonTree(lootTable);
            futures.add(DataProvider.saveStable(cache, json, file));
        }

        final List<String> caseIds = new ArrayList<>();
        for (ZPSyntheticLootCaseDescription lootCase : ZPSyntheticLootCasesDataGenRegistry.getDataToGenCases()) {
            final String id = lootCase.blockId();
            caseIds.add(id);
            final Path file = casesFolder.resolve(id + ".json");
            final JsonElement json = this.gson.toJsonTree(lootCase);
            futures.add(DataProvider.saveStable(cache, json, file));
        }

        ZPSyntheticLootCasesDataGenRegistry.getDataToGenGather_LootCaseExtensions().forEach((k, v) -> {
            final Path file = lootExtensionsFolder.resolve(k.replaceAll(":", "_") + ".json");
            final JsonElement json = this.gson.toJsonTree(new ZPLootTableExtensionsReloadListener.ZPLootTableExtensionData(k, v));
            futures.add(DataProvider.saveStable(cache, json, file));
        });

        final Path indexFile = casesFolder.resolve("index.json");
        final JsonElement jsonIndex = this.gson.toJsonTree(caseIds);
        futures.add(DataProvider.saveStable(cache, jsonIndex, indexFile));
        ZPSyntheticLootCasesDataGenRegistry.clearGather();
        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }

    @Override
    public @NotNull String getName() {
        return "ZP LootTables Provider";
    }
}
