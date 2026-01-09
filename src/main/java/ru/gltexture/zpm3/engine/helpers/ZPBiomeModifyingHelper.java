package ru.gltexture.zpm3.engine.helpers;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class ZPBiomeModifyingHelper {
    private static List<ModifyEntryAddSpawns> modifyAddSpawnsEntries = new ArrayList<>();
    private static List<ModifyEntryRemoveSpawns> modifyRemoveSpawnsEntries = new ArrayList<>();

    public static String ADD_FEATURES = "forge:add_features";
    public static String REMOVE_FEATURES = "forge:remove_features";
    public static String ADD_SPAWNS = "forge:add_spawns";
    public static String REMOVE_SPAWNS = "forge:remove_spawns";

    public static void addNewAddSpawnEntry(@NotNull ZPBiomeModifyingHelper.ModifyEntryAddSpawns modifyEntryAddSpawns) {
        ZPBiomeModifyingHelper.modifyAddSpawnsEntries.add(modifyEntryAddSpawns);
    }

    public static List<ModifyEntryAddSpawns> getModifyAddSpawnsEntries() {
        return ZPBiomeModifyingHelper.modifyAddSpawnsEntries;
    }

    public static void addNewRemoveSpawnEntry(@NotNull ZPBiomeModifyingHelper.ModifyEntryRemoveSpawns modifyEntryRemoveSpawns) {
        ZPBiomeModifyingHelper.modifyRemoveSpawnsEntries.add(modifyEntryRemoveSpawns);
    }

    public static List<ModifyEntryRemoveSpawns> getModifyRemoveSpawnsEntries() {
        return ZPBiomeModifyingHelper.modifyRemoveSpawnsEntries;
    }

    public static void clear() {
        ZPBiomeModifyingHelper.modifyAddSpawnsEntries.clear();
        ZPBiomeModifyingHelper.modifyRemoveSpawnsEntries.clear();
    }

    public record SpawnerEntry(@NotNull String type, int weight, int minCount, int maxCount) {;}
    public record ModifyEntryAddSpawns(@NotNull String fileName, @NotNull List<String> biomes, @NotNull SpawnerEntry spawnerEntry) {
    }
    public record ModifyEntryRemoveSpawns(@NotNull String fileName, @NotNull List<String> biomes, @NotNull List<String> entityTypes) {
    }
}
