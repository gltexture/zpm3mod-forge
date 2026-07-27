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

package ru.gltexture.zpm3.modules.loot_cases.init.helper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.modules.common.init.ZPTabs;
import ru.gltexture.zpm3.modules.loot_cases.init.ZPLootCases;
import ru.gltexture.zpm3.modules.loot_cases.instances.blocks.ZPDefaultBlockLootCase;
import ru.gltexture.zpm3.modules.loot_cases.rendering.ZPLootCaseItemRenderer;
import ru.gltexture.zpm3.engine.core.ZPLogger;
import ru.gltexture.zpm3.engine.exceptions.ZPRuntimeException;
import ru.gltexture.zpm3.engine.helpers.ZPItemBlockHelper;
import ru.gltexture.zpm3.engine.instances.ZPBlockItemsRegistry;
import ru.gltexture.zpm3.engine.registry.ZPRegistry;
import ru.gltexture.zpm3.engine.registry.ZPRegistryCollections;
import ru.gltexture.zpm3.engine.service.ZPUtility;

public abstract class ZPRegLootCaseItems {
    public static void init(@NotNull ZPRegistry.ZPRegSupplier<Item> regSupplier) {
        ZPRegLootCaseItems.lootCases(regSupplier);
    }

    private static void lootCases(@NotNull ZPRegistry.ZPRegSupplier<Item> regSupplier) {
        if (ZPTabs.zp_blocks_tab != null) {
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
    }
}