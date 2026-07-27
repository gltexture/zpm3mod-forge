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

package ru.gltexture.zpm3.engine.helpers;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

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

    public record SpawnerEntry(@NotNull Supplier<String> type, int weight, int minCount, int maxCount) {;}
    public record ModifyEntryAddSpawns(@NotNull String fileName, @NotNull List<String> biomes, @NotNull SpawnerEntry spawnerEntry) {
    }
    public record ModifyEntryRemoveSpawns(@NotNull String fileName, @NotNull List<String> biomes, @NotNull List<String> entityTypes) {
    }
}
