package ru.gltexture.zpm3.modules.blocks.init.helper;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.modules.blocks.init.ZPBlocks;
import ru.gltexture.zpm3.modules.blocks.init.ZPCampfireBlocks;
import ru.gltexture.zpm3.modules.blocks.init.ZPLanternBlocks;
import ru.gltexture.zpm3.modules.blocks.init.ZPTorchBlocks;
import ru.gltexture.zpm3.modules.common.init.*;
import ru.gltexture.zpm3.engine.core.ZPLogger;
import ru.gltexture.zpm3.engine.exceptions.ZPRuntimeException;
import ru.gltexture.zpm3.engine.helpers.ZPItemBlockHelper;
import ru.gltexture.zpm3.engine.instances.ZPBlockItemsRegistry;
import ru.gltexture.zpm3.engine.registry.ZPRegistry;
import ru.gltexture.zpm3.engine.registry.ZPRegistryCollections;
import ru.gltexture.zpm3.engine.service.ZPUtility;

public abstract class ZPRegBlockItems {
    public static void init(@NotNull ZPRegistry.ZPRegSupplier<Item> regSupplier) {
        ZPRegBlockItems.commonBlocks(regSupplier);
        ZPRegBlockItems.regTorchBlocks(regSupplier);
        ZPRegBlockItems.regLanternBlocks(regSupplier);

        {
            ZPRegBlockItems.regLanternOrCampfireBlock(regSupplier, ZPCampfireBlocks.campfire2);
        }
    }

    private static void regTorchBlocks(@NotNull ZPRegistry.ZPRegSupplier<Item> regSupplier) {
        ZPRegBlockItems.regTorchBlock(regSupplier, ZPTorchBlocks.wall_lamp, ZPTorchBlocks.wall_lamp_wall);
        ZPRegBlockItems.regTorchBlock(regSupplier, ZPTorchBlocks.wall_lamp_off, ZPTorchBlocks.wall_lamp_off_wall);
        ZPRegBlockItems.regTorchBlock(regSupplier, ZPTorchBlocks.torch2, ZPTorchBlocks.torch2_wall);
        ZPRegBlockItems.regTorchBlock(regSupplier, ZPTorchBlocks.torch3, ZPTorchBlocks.torch3_wall);
        ZPRegBlockItems.regTorchBlock(regSupplier, ZPTorchBlocks.torch4, ZPTorchBlocks.torch4_wall);
        ZPRegBlockItems.regTorchBlock(regSupplier, ZPTorchBlocks.torch5, ZPTorchBlocks.torch5_wall);
    }

    private static void regLanternBlocks(@NotNull ZPRegistry.ZPRegSupplier<Item> regSupplier) {
        ZPRegBlockItems.regLanternOrCampfireBlock(regSupplier, ZPLanternBlocks.lantern2);
        ZPRegBlockItems.regLanternOrCampfireBlock(regSupplier, ZPLanternBlocks.lantern3);
        ZPRegBlockItems.regLanternOrCampfireBlock(regSupplier, ZPLanternBlocks.lantern4);
        ZPRegBlockItems.regLanternOrCampfireBlock(regSupplier, ZPLanternBlocks.lantern5);
    }

    private static void commonBlocks(@NotNull ZPRegistry.ZPRegSupplier<Item> regSupplier) {
        final RegistryObject<CreativeModeTab> tabToAdd = ZPTabs.zp_blocks_tab;

        try {
            for (RegistryObject<Block> registryObject : ZPRegistryCollections.getCollectionById(ZPBlocks.class, "blocks")) {
                RegistryObject<BlockItem> blockItemRegistryObject = ZPItemBlockHelper.createBlockItem(regSupplier, registryObject
                ).afterCreated((e, utils) -> {
                    ZPUtility.sides().onlyClient(() -> {
                        utils.items().addItemInTab(e, tabToAdd);
                    });
                }).end();
                ZPBlockItemsRegistry.putNewEntry(registryObject, blockItemRegistryObject);
            }
        } catch (ZPRuntimeException e) {
            ZPLogger.warn(e.getMessage());
        }
    }

    private static void regTorchBlock(@NotNull ZPRegistry.ZPRegSupplier<Item> regSupplier, @NotNull RegistryObject<? extends Block> block, @NotNull RegistryObject<? extends Block> wallBlock) {
        final RegistryObject<CreativeModeTab> tabToAdd = ZPTabs.zp_fading_blocks_tab;

        RegistryObject<BlockItem> blockItemRegistryObject = ZPItemBlockHelper.createWallBlockItem(regSupplier, block, wallBlock
        ).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.items().addItemInTab(e, tabToAdd);
            });
        }).end();

        ZPBlockItemsRegistry.putNewEntry(block, blockItemRegistryObject);
    }

    private static void regLanternOrCampfireBlock(@NotNull ZPRegistry.ZPRegSupplier<Item> regSupplier, @NotNull RegistryObject<? extends Block> block) {
        final RegistryObject<CreativeModeTab> tabToAdd = ZPTabs.zp_fading_blocks_tab;

        RegistryObject<BlockItem> blockItemRegistryObject = ZPItemBlockHelper.createBlockItem(regSupplier, block
        ).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.items().addItemInTab(e, tabToAdd);
            });
        }).end();

        ZPBlockItemsRegistry.putNewEntry(block, blockItemRegistryObject);
    }
}