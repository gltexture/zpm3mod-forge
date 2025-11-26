package ru.gltexture.zpm3.engine.instances;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public abstract class ZPBlockItemsRegistry {
    private static final Map<@NotNull RegistryObject<? extends Block>, @NotNull RegistryObject<BlockItem>> registryObjectMap = new HashMap<>();

    public static RegistryObject<BlockItem> getBlockItem(@NotNull RegistryObject<? extends Block> registryObject) {
        return ZPBlockItemsRegistry.registryObjectMap.get(registryObject);
    }

    public static void putNewEntry(@NotNull RegistryObject<? extends Block> a, @NotNull RegistryObject<BlockItem> b) {
        ZPBlockItemsRegistry.registryObjectMap.put(a, b);
    }
}