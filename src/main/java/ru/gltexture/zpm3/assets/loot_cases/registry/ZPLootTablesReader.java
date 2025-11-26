package ru.gltexture.zpm3.assets.loot_cases.registry;

import com.google.gson.*;
import net.minecraft.util.GsonHelper;
import ru.gltexture.zpm3.assets.loot_cases.events.provider.ZPLootTableProvider;
import ru.gltexture.zpm3.assets.loot_cases.loot_tables.ZPLootTable;
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
            e.printStackTrace(System.err);
        }
    }

    private static ZPLootTable readLootTable(Gson gson, String path) {
        final ZPPath readJson = new ZPPath(ZPLootTablesReader.pathToJsons, path);
        String jsonRaw = null;
        try {
            jsonRaw = ZPUtility.files().readTextFromJar(readJson);
        } catch (IOException e) {
            ZPLogger.error("Couldn't get file: " + readJson);
            e.printStackTrace(System.err);
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
