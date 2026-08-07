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

package ru.gltexture.zpm3.engine.helpers.gen.sub_providers;

import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ZPEntitiesSubProvider extends EntityLootSubProvider {
    private final Map<Supplier<EntityType<?>>, Set<Supplier<LootPool.Builder>>> map;

    public ZPEntitiesSubProvider(FeatureFlagSet enabledFeatures, Map<Supplier<EntityType<?>>, Set<Supplier<LootPool.Builder>>> map) {
        super(enabledFeatures);
        this.map = map;
    }

    @Override
    public void generate() {
        for (Map.Entry<Supplier<EntityType<?>>, Set<Supplier<LootPool.Builder>>> entry : this.map.entrySet()) {
            final EntityType<?> entityType = entry.getKey().get();
            final LootTable.Builder lootTable = LootTable.lootTable();
            for (Supplier<LootPool.Builder> poolSupplier : entry.getValue()) {
                lootTable.withPool(poolSupplier.get());
            }
            this.add(entityType, lootTable);
        }
    }

    @Override
    protected @NotNull Stream<EntityType<?>> getKnownEntityTypes() {
        return this.map.keySet().stream().map(Supplier::get);
    }

    public Map<Supplier<EntityType<?>>, Set<Supplier<LootPool.Builder>>> getMap() {
        return this.map;
    }
}