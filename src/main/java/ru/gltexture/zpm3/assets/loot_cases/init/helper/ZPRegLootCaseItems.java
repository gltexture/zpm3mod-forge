package ru.gltexture.zpm3.assets.loot_cases.init.helper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.init.ZPTabs;
import ru.gltexture.zpm3.assets.loot_cases.init.ZPLootCases;
import ru.gltexture.zpm3.assets.loot_cases.instances.blocks.ZPDefaultBlockLootCase;
import ru.gltexture.zpm3.assets.loot_cases.rendering.ZPLootCaseItemRenderer;
import ru.gltexture.zpm3.engine.core.ZPLogger;
import ru.gltexture.zpm3.engine.exceptions.ZPRuntimeException;
import ru.gltexture.zpm3.engine.helpers.ZPItemBlockHelper;
import ru.gltexture.zpm3.engine.instances.ZPBlockItemsRegistry;
import ru.gltexture.zpm3.engine.registry.ZPRegistry;
import ru.gltexture.zpm3.engine.registry.ZPRegistryCollections;

public abstract class ZPRegLootCaseItems {
    public static void init(@NotNull ZPRegistry.ZPRegSupplier<Item> regSupplier) {
        ZPRegLootCaseItems.lootCases(regSupplier);
    }

    private static void lootCases(@NotNull ZPRegistry.ZPRegSupplier<Item> regSupplier) {
        final RegistryObject<CreativeModeTab> tabToAdd = ZPTabs.zp_blocks_tab;
        try {
            for (RegistryObject<ZPDefaultBlockLootCase> registryObject : ZPRegistryCollections.getCollectionById(ZPLootCases.class, "lootCases")) {
                RegistryObject<BlockItem> blockItemRegistryObject = ZPItemBlockHelper.createBlockItemWithClientCustomInit(regSupplier, registryObject, (consumer ->
                        consumer.accept(new IClientItemExtensions() {
                            @Override
                            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                                return new ZPLootCaseItemRenderer(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels(), registryObject.get());
                            }
                        }))
                ).postConsume(Dist.CLIENT, (e, utils) -> {
                    utils.items().addItemInTab(e, tabToAdd);
                }).registryObject();
                ZPBlockItemsRegistry.putNewEntry(registryObject, blockItemRegistryObject);
            }
        } catch (ZPRuntimeException e) {
            ZPLogger.warn(e.getMessage());
        }
    }
}