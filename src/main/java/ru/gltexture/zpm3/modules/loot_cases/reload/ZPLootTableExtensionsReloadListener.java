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

import java.util.List;
import java.util.Map;

public final class ZPLootTableExtensionsReloadListener extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new GsonBuilder().registerTypeAdapter(ZPLootNbtValue.class, new ZPTypeAdapterNBTValue()).create();

    public ZPLootTableExtensionsReloadListener() {
        super(ZPLootTableExtensionsReloadListener.GSON, "zp_loot_tables_extensions");
    }

    @Override
    protected void apply(@NotNull Map<ResourceLocation, JsonElement> resources, @NotNull ResourceManager resourceManager, @NotNull ProfilerFiller profiler) {
        for (Map.Entry<ResourceLocation, JsonElement> entry : resources.entrySet()) {
            final ResourceLocation extensionResource = entry.getKey();
            try {
                final ZPLootTableExtension extension = ZPLootTableExtensionsReloadListener.GSON.fromJson(entry.getValue(), ZPLootTableExtension.class);
                if (extension == null) {
                    ZPLogger.error("Couldn't read loot table extension: " + extensionResource);
                    continue;
                }
                final ZPLootTable lootTable = ZPLootTablesCollection.INSTANCE.getLootTableById(extension.lootTableId());
                if (lootTable == null) {
                    ZPLogger.error("Couldn't extend unknown loot table '" + extension.lootTableId() + "' from " + extensionResource);
                    continue;
                }
                lootTable.getExtendBy().addAll(extension.extendBy());
                ZPLogger.info("Extended loot table '" + extension.lootTableId() + "' from " + extensionResource);
            } catch (Exception e) {
                ZPLogger.error("Couldn't read loot table extension: " + extensionResource);
                ZPLogger.exception(e);
            }
        }
    }

    private record ZPLootTableExtension(@NotNull ResourceLocation lootTableId, @NotNull List<ResourceLocation> extendBy) {
    }
}