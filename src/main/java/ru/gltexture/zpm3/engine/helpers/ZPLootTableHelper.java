package ru.gltexture.zpm3.engine.helpers;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Supplier;

public abstract class ZPLootTableHelper {
    private static final Map<ResourceLocation, Set<Supplier<LootPool>>> existingLootPools = new HashMap<>();
    private static final Map<Supplier<Block>, Set<Supplier<LootPool.Builder>>> lootPoolsToCreate = new HashMap<>();

    public static void changeBlockExistingLootTable(@NotNull ResourceLocation registryObject, @NotNull Supplier<LootPool> lootPool) {
        if (!ZPLootTableHelper.existingLootPools.containsKey(registryObject)) {
            ZPLootTableHelper.existingLootPools.put(registryObject, new HashSet<>());
        }
        ZPLootTableHelper.existingLootPools.get(registryObject).add(lootPool);
    }

    public static void addBlockLootTable(@NotNull Supplier<Block> blockSupplier, @NotNull Supplier<LootPool.Builder> lootPool) {
        if (!ZPLootTableHelper.lootPoolsToCreate.containsKey(blockSupplier)) {
            ZPLootTableHelper.lootPoolsToCreate.put(blockSupplier, new HashSet<>());
        }
        ZPLootTableHelper.lootPoolsToCreate.get(blockSupplier).add(lootPool);
    }

    public static @Nullable Set<Supplier<LootPool>> getExistingLootPoolsToChange(@NotNull ResourceLocation registryObject) {
        return ZPLootTableHelper.existingLootPools.get(registryObject);
    }

    public static @NotNull Map<Supplier<Block>, Set<Supplier<LootPool.Builder>>> getLootPoolsToCreate() {
        return ZPLootTableHelper.lootPoolsToCreate;
    }

    public static void clearPoolMapToCreate() {
        ZPLootTableHelper.lootPoolsToCreate.clear();
    }

    public static void clearExistingPoolMap() {
        ZPLootTableHelper.existingLootPools.clear();
    }
}
