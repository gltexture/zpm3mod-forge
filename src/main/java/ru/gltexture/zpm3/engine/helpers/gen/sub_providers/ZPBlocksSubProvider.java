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
    private final Map<RegistryObject<? extends Block>, Set<Supplier<LootPool.Builder>>> map;

    public ZPBlocksSubProvider(Set<Item> pExplosionResistant, FeatureFlagSet pEnabledFeatures, Map<RegistryObject<? extends Block>, Set<Supplier<LootPool.Builder>>> map) {
        super(pExplosionResistant, pEnabledFeatures);
        this.map = map;
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return this.getMap().keySet().stream().map(e -> (Block) e.get()).toList();
    }

    @Override
    protected void generate() {
        for (Map.Entry<RegistryObject<? extends Block>, Set<Supplier<LootPool.Builder>>> entry : this.getMap().entrySet()) {
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

    public Map<RegistryObject<? extends Block>, Set<Supplier<LootPool.Builder>>> getMap() {
        return this.map;
    }
}