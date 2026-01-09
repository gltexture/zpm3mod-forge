package ru.gltexture.zpm3.engine.helpers.gen.providers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.helpers.ZPBiomeModifyingHelper;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ZPBiomeModifyingProvider implements DataProvider {
    private final DataGenerator generator;
    private final String modId;

    public ZPBiomeModifyingProvider(DataGenerator generator, String modId) {
        this.generator = generator;
        this.modId = modId;
    }

    @SuppressWarnings("all")
    @Override
    public @NotNull CompletableFuture<?> run(@NotNull CachedOutput pOutput) {
        List<CompletableFuture<?>> futures = new ArrayList<>();
        for (ZPBiomeModifyingHelper.ModifyEntryAddSpawns entry : ZPBiomeModifyingHelper.getModifyAddSpawnsEntries()) {
            JsonObject json = new JsonObject();
            json.addProperty("type", ZPBiomeModifyingHelper.ADD_SPAWNS);
            JsonArray biomesArray = new JsonArray();
            for (String biome : entry.biomes()) {
                biomesArray.add(biome);
            }
            json.add("biomes", biomesArray);
            JsonObject spawner = new JsonObject();
            spawner.addProperty("type", entry.spawnerEntry().type());
            spawner.addProperty("weight", entry.spawnerEntry().weight());
            spawner.addProperty("minCount", entry.spawnerEntry().minCount());
            spawner.addProperty("maxCount", entry.spawnerEntry().maxCount());
            json.add("spawners", spawner);
            Path jsonPath = this.generator.getPackOutput().getOutputFolder().resolve("data/" + this.modId + "/forge/biome_modifier/" + entry.fileName() + ".json");
            futures.add(DataProvider.saveStable(pOutput, json, jsonPath));
        }

        for (ZPBiomeModifyingHelper.ModifyEntryRemoveSpawns entry : ZPBiomeModifyingHelper.getModifyRemoveSpawnsEntries()) {
            for (String entity : entry.entityTypes()) {
                JsonObject json = new JsonObject();
                json.addProperty("type", ZPBiomeModifyingHelper.REMOVE_SPAWNS);
                JsonArray biomesArray = new JsonArray();
                for (String biome : entry.biomes()) {
                    biomesArray.add(biome);
                }
                json.add("biomes", biomesArray);
                json.addProperty("entity_types", entity);
                Path jsonPath = this.generator.getPackOutput().getOutputFolder().resolve("data/" + this.modId + "/forge/biome_modifier/" + entry.fileName() + "_" + entity.replace(":", "__") + ".json");
                futures.add(DataProvider.saveStable(pOutput, json, jsonPath));
            }
        }

        ZPBiomeModifyingHelper.clear();
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
    }

    @Override
    public @NotNull String getName() {
        return "ZP Biome Modifiers Datagen";
    }
}