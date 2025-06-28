package ru.gltexture.zpm3.assets.common.init.helper;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.init.ZPCommonBlocks;
import ru.gltexture.zpm3.assets.common.init.ZPTabs;
import ru.gltexture.zpm3.engine.helpers.ZPItemBlockHelper;
import ru.gltexture.zpm3.engine.registry.ZPRegistry;
import ru.gltexture.zpm3.engine.registry.ZPRegistryCollections;
import ru.gltexture.zpm3.engine.registry.collection.IZPRegistryObjectsCollector;

import java.util.*;

public abstract class ZPRegBlockItems {
    private static final Map<@NotNull RegistryObject<Block>, @NotNull RegistryObject<BlockItem>> registryObjectMap = new HashMap<>();

    public static RegistryObject<BlockItem> getBlockItem(@NotNull RegistryObject<? extends Block> registryObject) {
        return ZPRegBlockItems.registryObjectMap.get(registryObject);
    }

    public static void init(@NotNull ZPRegistry.ZPRegSupplier<Item> regSupplier) {
        final RegistryObject<CreativeModeTab> tabToAdd = ZPTabs.zp_blocks_tab;
        IZPRegistryObjectsCollector<Block> registryObjectsCollector = ZPRegistryCollections.getCollector(ZPCommonBlocks.class);

        if (registryObjectsCollector != null) {
            for (RegistryObject<Block> registryObject : registryObjectsCollector.getObjectsToCollect()) {
                RegistryObject<BlockItem> blockItemRegistryObject = ZPItemBlockHelper.createBlockItem(regSupplier, registryObject
                ).postConsume(Dist.CLIENT, (e, utils) -> {
                    utils.addItemInTab(e, tabToAdd);
                }).registryObject();
                ZPRegBlockItems.registryObjectMap.put(registryObject, blockItemRegistryObject);
            }
        }
    }
}