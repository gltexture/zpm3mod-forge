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

import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.modules.loot_cases.loot_tables.ZPLootTable;

import java.util.ArrayList;
import java.util.List;

public abstract class ZPLootTablesRegistry {
    private static List<ZPLootTablesRegistry> allRegistries = new ArrayList<>();

    public static void REG(@NotNull ZPLootTablesRegistry registry) {
        ZPLootTablesRegistry.allRegistries.add(registry);
    }

    public static List<ZPLootTablesRegistry> ALL_REG() {
        return ZPLootTablesRegistry.allRegistries;
    }

    public static void CLEAR_REG() {
        ZPLootTablesRegistry.allRegistries = null;
    }


    private final List<ZPLootTable> zpLootTableList;

    public ZPLootTablesRegistry() {
        this.zpLootTableList = new ArrayList<>();
    }

    public List<ZPLootTable> getZpLootTableList() {
        return this.zpLootTableList;
    }

    public abstract void init();

    protected void register(@NotNull ZPLootTable lootTable) {
        this.zpLootTableList.add(lootTable);
    }
}
