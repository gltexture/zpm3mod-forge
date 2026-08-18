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

import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.modules.loot_cases.loot_tables.ZPLootTable;
import ru.gltexture.zpm3.modules.loot_cases.loot_tables.synthetic.ZPSyntheticLootCaseDescription;

import java.util.Collections;
import java.util.*;

public abstract class ZPSyntheticLootCasesDataGenRegistry {
    private static final List<ZPLootTable> DATA_TO_GEN_TABLES = new ArrayList<>();
    private static final List<ZPSyntheticLootCaseDescription> DATA_TO_GEN_CASES = new ArrayList<>();
    private static final Map<ZPLootTable, List<ZPSyntheticLootCaseDescription>> DATA_TO_GEN_TABLE_CASES = new LinkedHashMap<>();
    private static final List<ZPSyntheticLootCaseDescription> DATA_TO_GEN_RUNTIME = new ArrayList<>();

    public static void registerSyntheticLootTable(@NotNull ZPLootTable lootTable) {
        ZPSyntheticLootCasesDataGenRegistry.DATA_TO_GEN_TABLES.add(lootTable);
    }

    public static void registerSyntheticLootCase(@NotNull ZPSyntheticLootCaseDescription lootCase) {
        ZPSyntheticLootCasesDataGenRegistry.DATA_TO_GEN_CASES.add(lootCase);
    }

    public static void registerSyntheticLootCase(@NotNull ZPSyntheticLootCaseDescription lootCase, @NotNull ZPLootTable lootTable) {
        ZPSyntheticLootCasesDataGenRegistry.DATA_TO_GEN_TABLE_CASES.computeIfAbsent(lootTable, ignored -> new ArrayList<>()).add(lootCase);
    }

    public static void registerRuntime(@NotNull ZPSyntheticLootCaseDescription lootCase) {
        ZPSyntheticLootCasesDataGenRegistry.DATA_TO_GEN_RUNTIME.add(lootCase);
    }

    public static @NotNull List<ZPLootTable> getDataToGenTables() {
        return Collections.unmodifiableList(ZPSyntheticLootCasesDataGenRegistry.DATA_TO_GEN_TABLES);
    }

    public static @NotNull List<ZPSyntheticLootCaseDescription> getDataToGenCases() {
        return Collections.unmodifiableList(ZPSyntheticLootCasesDataGenRegistry.DATA_TO_GEN_CASES);
    }

    public static @NotNull Map<ZPLootTable, List<ZPSyntheticLootCaseDescription>> getDataToGenTableCases() {
        return Collections.unmodifiableMap(ZPSyntheticLootCasesDataGenRegistry.DATA_TO_GEN_TABLE_CASES);
    }

    public static @NotNull List<ZPSyntheticLootCaseDescription> getDataToGenRuntime_LootCases() {
        return Collections.unmodifiableList(ZPSyntheticLootCasesDataGenRegistry.DATA_TO_GEN_RUNTIME);
    }

    public static void clearGather() {
        ZPSyntheticLootCasesDataGenRegistry.DATA_TO_GEN_TABLES.clear();
        ZPSyntheticLootCasesDataGenRegistry.DATA_TO_GEN_CASES.clear();
        ZPSyntheticLootCasesDataGenRegistry.DATA_TO_GEN_TABLE_CASES.clear();
    }

    public static void clearRuntime() {
        ZPSyntheticLootCasesDataGenRegistry.DATA_TO_GEN_RUNTIME.clear();
    }

    private ZPSyntheticLootCasesDataGenRegistry() {
    }
}