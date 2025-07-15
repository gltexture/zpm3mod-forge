package ru.gltexture.zpm3.assets.common.init.helper;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.init.ZPBlocks;
import ru.gltexture.zpm3.assets.common.init.ZPTabs;
import ru.gltexture.zpm3.assets.common.init.ZPTorchBlocks;
import ru.gltexture.zpm3.engine.core.ZPLogger;
import ru.gltexture.zpm3.engine.exceptions.ZPRuntimeException;
import ru.gltexture.zpm3.engine.helpers.ZPItemBlockHelper;
import ru.gltexture.zpm3.engine.registry.ZPRegistry;
import ru.gltexture.zpm3.engine.registry.ZPRegistryCollections;

import java.util.*;

public abstract class ZPRegBlockItems {
    private static final Map<@NotNull RegistryObject<? extends Block>, @NotNull RegistryObject<BlockItem>> registryObjectMap = new HashMap<>();

    public static RegistryObject<BlockItem> getBlockItem(@NotNull RegistryObject<? extends Block> registryObject) {
        return ZPRegBlockItems.registryObjectMap.get(registryObject);
    }

    public static void init(@NotNull ZPRegistry.ZPRegSupplier<Item> regSupplier) {
        ZPRegBlockItems.commonBlocks(regSupplier);
        ZPRegBlockItems.regTorchBlocks(regSupplier);
    }

    private static void regTorchBlocks(@NotNull ZPRegistry.ZPRegSupplier<Item> regSupplier) {
        ZPRegBlockItems.regTorchBlock(regSupplier, ZPTorchBlocks.wall_lamp, ZPTorchBlocks.wall_lamp_wall);
        ZPRegBlockItems.regTorchBlock(regSupplier, ZPTorchBlocks.wall_lamp_off, ZPTorchBlocks.wall_lamp_off_wall);
        ZPRegBlockItems.regTorchBlock(regSupplier, ZPTorchBlocks.torch2, ZPTorchBlocks.torch2_wall);
        ZPRegBlockItems.regTorchBlock(regSupplier, ZPTorchBlocks.torch3, ZPTorchBlocks.torch3_wall);
        ZPRegBlockItems.regTorchBlock(regSupplier, ZPTorchBlocks.torch4, ZPTorchBlocks.torch4_wall);
        ZPRegBlockItems.regTorchBlock(regSupplier, ZPTorchBlocks.torch5, ZPTorchBlocks.torch5_wall);
    }

    private static void commonBlocks(@NotNull ZPRegistry.ZPRegSupplier<Item> regSupplier) {
        final RegistryObject<CreativeModeTab> tabToAdd = ZPTabs.zp_blocks_tab;

        try {
            for (RegistryObject<Block> registryObject : ZPRegistryCollections.getCollectionById(ZPBlocks.class, "blocks")) {
                RegistryObject<BlockItem> blockItemRegistryObject = ZPItemBlockHelper.createBlockItem(regSupplier, registryObject
                ).postConsume(Dist.CLIENT, (e, utils) -> {
                    utils.items().addItemInTab(e, tabToAdd);
                }).registryObject();
                ZPRegBlockItems.registryObjectMap.put(registryObject, blockItemRegistryObject);
            }
        } catch (ZPRuntimeException e) {
            ZPLogger.warn(e.getMessage());
        }
    }

    private static void regTorchBlock(@NotNull ZPRegistry.ZPRegSupplier<Item> regSupplier, @NotNull RegistryObject<? extends Block> block, @NotNull RegistryObject<? extends Block> wallBlock) {
        final RegistryObject<CreativeModeTab> tabToAdd = ZPTabs.zp_blocks_tab;

        RegistryObject<BlockItem> blockItemRegistryObject = ZPItemBlockHelper.createWallBlockItem(regSupplier, block, wallBlock
        ).postConsume(Dist.CLIENT, (e, utils) -> {
            utils.items().addItemInTab(e, tabToAdd);
        }).registryObject();

        ZPRegBlockItems.registryObjectMap.put(block, blockItemRegistryObject);
    }
}