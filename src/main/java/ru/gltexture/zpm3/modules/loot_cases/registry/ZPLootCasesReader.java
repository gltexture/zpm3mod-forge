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

package ru.gltexture.zpm3.modules.loot_cases.registry;

import com.google.gson.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.modules.loot_cases.events.provider.ZPLootTableProvider;
import ru.gltexture.zpm3.engine.core.ZPLogger;
import ru.gltexture.zpm3.engine.service.ZPPath;
import ru.gltexture.zpm3.engine.service.ZPUtility;
import ru.gltexture.zpm3.modules.loot_cases.events.provider.ZPSyntheticLootCasesDataGenRegistry;
import ru.gltexture.zpm3.modules.loot_cases.loot_tables.synthetic.ZPSyntheticLootCaseDescription;

import java.io.IOException;

public abstract class ZPLootCasesReader {
    private static final String PATH_TO_JSONS = "data/zpm3/" + ZPLootTableProvider.lootCases + "/";
    private static final Gson GSON = new GsonBuilder().create();

    public static void readFiles() {
        final ZPPath indexPath = new ZPPath(ZPLootCasesReader.PATH_TO_JSONS, ZPLootTableProvider.indexFile);
        final String jsonRaw;
        try {
            jsonRaw = ZPUtility.files().readTextFromJar(indexPath);
        } catch (IOException e) {
            ZPLogger.error("Couldn't get loot cases index: " + indexPath);
            ZPLogger.exception(e);
            return;
        }

        try {
            final JsonArray files = GsonHelper.parseArray(jsonRaw);
            for (JsonElement element : files) {
                final String fileName = element.getAsString();
                final ZPSyntheticLootCaseDescription description = readDescription(fileName + ".json");
                if (description == null) {
                    continue;
                }
                ZPSyntheticLootCasesDataGenRegistry.registerRuntime(description);
                ZPLogger.info("Read synthetic loot-case " + fileName);
            }
        } catch (Exception e) {
            ZPLogger.error("Couldn't read loot cases index: " + indexPath);
            ZPLogger.exception(e);
        }
    }

    private static @Nullable ZPSyntheticLootCaseDescription readDescription(@NotNull String path) {
        final ZPPath jsonPath = new ZPPath(PATH_TO_JSONS, path);
        final String jsonRaw;
        try {
            jsonRaw = ZPUtility.files().readTextFromJar(jsonPath);
        } catch (IOException e) {
            ZPLogger.error("Couldn't read loot case: " + jsonPath);
            ZPLogger.exception(e);
            return null;
        }
        try {
            return GSON.fromJson(GsonHelper.parse(jsonRaw), ZPSyntheticLootCaseDescription.class);
        } catch (Exception e) {
            ZPLogger.error("Couldn't parse loot case: " + jsonPath);
            ZPLogger.exception(e);
            return null;
        }
    }

    private ZPLootCasesReader() {
    }
}