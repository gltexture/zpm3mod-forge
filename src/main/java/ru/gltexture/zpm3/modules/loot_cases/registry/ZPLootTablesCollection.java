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

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import ru.gltexture.zpm3.modules.loot_cases.loot_tables.ZPLootTable;
import java.util.*;

public final class ZPLootTablesCollection {
    public static ZPLootTablesCollection INSTANCE = new ZPLootTablesCollection();

    private final Map<ResourceLocation, ZPLootTable> lootTableMap;

    private ZPLootTablesCollection() {
        this.lootTableMap = new HashMap<>();
    }

    public ZPLootTable getLootTableById(ResourceLocation id) {
        return this.lootTableMap.get(id);
    }

    public void clear() {
        this.lootTableMap.clear();
    }

    public @Unmodifiable Set<Map.Entry<ResourceLocation, ZPLootTable>> getAllLootTableEntries() {
        return Collections.unmodifiableSet(this.lootTableMap.entrySet());
    }

    public @Unmodifiable Collection<ZPLootTable> getAllLootTables() {
        return Collections.unmodifiableCollection(this.lootTableMap.values());
    }

    public void putInMap(@NotNull ResourceLocation resourceLocation, @NotNull ZPLootTable lootTable) {
        this.lootTableMap.put(resourceLocation, lootTable);
    }
}