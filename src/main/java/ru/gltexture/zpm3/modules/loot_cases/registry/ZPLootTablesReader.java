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
import net.minecraft.util.GsonHelper;
import ru.gltexture.zpm3.modules.loot_cases.events.provider.ZPLootTableProvider;
import ru.gltexture.zpm3.modules.loot_cases.loot_tables.ZPLootTable;
import ru.gltexture.zpm3.engine.core.ZPLogger;
import ru.gltexture.zpm3.engine.service.ZPPath;
import ru.gltexture.zpm3.engine.service.ZPUtility;

import java.io.IOException;

public abstract class ZPLootTablesReader {
    public static final String pathToJsons = "data/zpm3/zp_loot_tables/";

    public static void READ_FILES() {
        final Gson gsonReadFile = (new GsonBuilder()).create();
        final ZPPath readIndex = new ZPPath(ZPLootTablesReader.pathToJsons, ZPLootTableProvider.indexFile);
        String jsonRaw = null;
        try {
            jsonRaw = ZPUtility.files().readTextFromJar(readIndex);
        } catch (IOException e) {
            ZPLogger.error("Couldn't get file: " + readIndex);
            return;
        }
        try {
            JsonArray arrayOfFiles = GsonHelper.parseArray(jsonRaw);
            for (int i = 0; i < arrayOfFiles.size(); i++) {
                JsonElement element = arrayOfFiles.get(i);
                String jsonFileToRead = element.getAsString();
                ZPLootTable zpLootTable = ZPLootTablesReader.readLootTable(gsonReadFile, jsonFileToRead + ".json");
                if (zpLootTable != null) {
                    ZPLootTablesCollection.INSTANCE.putInMap(zpLootTable);
                    ZPLogger.info("Read ZPLootTable: " + zpLootTable.getUniqueId());
                }
            }
        } catch (Exception e) {
            ZPLogger.exception(e);
        }
    }

    private static ZPLootTable readLootTable(Gson gson, String path) {
        final ZPPath readJson = new ZPPath(ZPLootTablesReader.pathToJsons, path);
        String jsonRaw = null;
        try {
            jsonRaw = ZPUtility.files().readTextFromJar(readJson);
        } catch (IOException e) {
            ZPLogger.error("Couldn't get file: " + readJson);
            ZPLogger.exception(e);
            return null;
        }

        JsonObject jsonObject = GsonHelper.parse(jsonRaw);
        try {
            return gson.fromJson(jsonObject, ZPLootTable.class);
        } catch (Exception e) {
            ZPLogger.error("Couldn't read file: " + readJson);
            return null;
        }
    }
}
