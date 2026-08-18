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

package ru.gltexture.zpm3.modules.loot_cases.reload;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZPLogger;
import ru.gltexture.zpm3.modules.loot_cases.loot_tables.ZPLootTable;
import ru.gltexture.zpm3.modules.loot_cases.loot_tables.nbt.ZPLootNbtValue;
import ru.gltexture.zpm3.modules.loot_cases.registry.ZPLootTablesCollection;
import ru.gltexture.zpm3.modules.loot_cases.reload.gson.ZPTypeAdapterNBTValue;

import java.util.HashMap;
import java.util.Map;

public final class ZPLootTablesReloadListener extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new GsonBuilder().registerTypeAdapter(ZPLootNbtValue.class, new ZPTypeAdapterNBTValue()).create();

    public ZPLootTablesReloadListener() {
        super(ZPLootTablesReloadListener.GSON, "zp_loot_tables");
    }

    @Override
    protected void apply(@NotNull Map<ResourceLocation, JsonElement> resources, @NotNull ResourceManager resourceManager, @NotNull ProfilerFiller profilerFiller) {
        ZPLootTablesCollection.INSTANCE.clear();
        for (Map.Entry<ResourceLocation, JsonElement> entry : resources.entrySet()) {
            final ResourceLocation resourceLocation = entry.getKey();
            if (resourceLocation.getPath().equals("zp_loot_tables/index.json")) {
                continue;
            }
            final JsonElement json = entry.getValue();
            try {
                final ZPLootTable lootTable = ZPLootTablesReloadListener.GSON.fromJson(json, ZPLootTable.class);
                if (lootTable == null) {
                    ZPLogger.error("Couldn't read loot table: " + resourceLocation);
                    continue;
                }
                ZPLootTablesCollection.INSTANCE.putInMap(entry.getKey(), lootTable);
                ZPLogger.info("Loaded loot table: " + resourceLocation);
            } catch (Exception e) {
                ZPLogger.error("Couldn't read loot table: " + resourceLocation);
                ZPLogger.exception(e);
            }
        }
    }
}