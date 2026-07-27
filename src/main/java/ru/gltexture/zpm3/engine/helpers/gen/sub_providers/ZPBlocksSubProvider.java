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

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Supplier;

public class ZPBlocksSubProvider extends BlockLootSubProvider {
    private final Map<Supplier<Block>, Set<Supplier<LootPool.Builder>>> map;

    public ZPBlocksSubProvider(Set<Item> pExplosionResistant, FeatureFlagSet pEnabledFeatures, Map<Supplier<Block>, Set<Supplier<LootPool.Builder>>> map) {
        super(pExplosionResistant, pEnabledFeatures);
        this.map = map;
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return this.getMap().keySet().stream().map(e -> (Block) e.get()).toList();
    }

    @Override
    protected void generate() {
        for (Map.Entry<Supplier<Block>, Set<Supplier<LootPool.Builder>>> entry : this.getMap().entrySet()) {
            Block block = entry.getKey().get();
            Supplier<LootTable.Builder> supplier = () -> {
                LootTable.Builder lt = LootTable.lootTable();
                for (Supplier<LootPool.Builder> poolSupplier : entry.getValue()) {
                    lt.withPool(poolSupplier.get());
                }
                return lt;
            };
            this.add(block, b -> supplier.get());
        }
    }

    public Map<Supplier<Block>, Set<Supplier<LootPool.Builder>>> getMap() {
        return this.map;
    }
}