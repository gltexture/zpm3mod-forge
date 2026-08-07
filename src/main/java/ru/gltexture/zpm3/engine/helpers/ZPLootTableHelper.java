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

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Supplier;

public abstract class ZPLootTableHelper {
    private static final Map<ResourceLocation, Set<Supplier<LootPool>>> EXISTING_POOLS = new HashMap<>();

    private static final Map<Supplier<Block>, Set<Supplier<LootPool.Builder>>> BLOCK_POOLS = new HashMap<>();
    private static final Map<Supplier<EntityType<?>>, Set<Supplier<LootPool.Builder>>> ENTITY_POOLS = new HashMap<>();

    public static void changeExistingLootTable(@NotNull ResourceLocation lootTable, @NotNull Supplier<LootPool> pool) {
        ZPLootTableHelper.EXISTING_POOLS.computeIfAbsent(lootTable, k -> new HashSet<>()).add(pool);
    }

    public static void addBlockLootTable(@NotNull Supplier<Block> block, @NotNull Supplier<LootPool.Builder> pool) {
        ZPLootTableHelper.BLOCK_POOLS.computeIfAbsent(block, k -> new HashSet<>()).add(pool);
    }

    public static void addEntityLootTable(@NotNull Supplier<EntityType<?>> entity, @NotNull Supplier<LootPool.Builder> pool) {
        ZPLootTableHelper.ENTITY_POOLS.computeIfAbsent(entity, k -> new HashSet<>()).add(pool);
    }

    public static @Nullable Set<Supplier<LootPool>> getExistingLootPools(@NotNull ResourceLocation lootTable) {
        return ZPLootTableHelper.EXISTING_POOLS.get(lootTable);
    }

    public static @NotNull Map<Supplier<Block>, Set<Supplier<LootPool.Builder>>> getBlockLootTables() {
        return ZPLootTableHelper.BLOCK_POOLS;
    }

    public static @NotNull Map<Supplier<EntityType<?>>, Set<Supplier<LootPool.Builder>>> getEntityLootTables() {
        return ZPLootTableHelper.ENTITY_POOLS;
    }

    public static void clearGeneratedLootTables() {
        ZPLootTableHelper.BLOCK_POOLS.clear();
        ZPLootTableHelper.ENTITY_POOLS.clear();
    }

    public static void clearExistingLootTableChanges() {
        ZPLootTableHelper.EXISTING_POOLS.clear();
    }
}