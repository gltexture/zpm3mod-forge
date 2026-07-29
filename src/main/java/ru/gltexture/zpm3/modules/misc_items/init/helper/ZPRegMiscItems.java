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

package ru.gltexture.zpm3.modules.misc_items.init.helper;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.helpers.gen.ZPDataGenHelper;
import ru.gltexture.zpm3.engine.helpers.gen.data.ZPGenTextureData;
import ru.gltexture.zpm3.engine.instances.items.ZPItem;
import ru.gltexture.zpm3.engine.registry.ZPCommonRegistry;
import ru.gltexture.zpm3.engine.service.ZPUtility;
import ru.gltexture.zpm3.modules.common.init.ZPTags;
import ru.gltexture.zpm3.modules.misc_items.init.ZPMiscItems;
import ru.gltexture.zpm3.modules.common.init.ZPTabs;

public abstract class ZPRegMiscItems {
    public static void init(@NotNull ZPCommonRegistry.ZPRegSupplier<Item> regSupplier) {
        ZPMiscItems.cement_material = regSupplier.register("cement_material",
                () -> new ZPItem(new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1))
        ).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.items().addItemInTab(e, ZPTabs.zp_items_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.MISC_ITEMS_DIRECTORY);
            });
        }).end();

        ZPMiscItems.chisel_material = regSupplier.register("chisel_material",
                () -> new ZPItem(new Item.Properties().stacksTo(16))
        ).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.items().addItemInTab(e, ZPTabs.zp_items_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.MISC_ITEMS_DIRECTORY);
            });
        }).end();

        ZPMiscItems.shelves_material = regSupplier.register("shelves_material",
                () -> new ZPItem(new Item.Properties().stacksTo(16))
        ).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.items().addItemInTab(e, ZPTabs.zp_items_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.MISC_ITEMS_DIRECTORY);
            });
        }).end();

        ZPMiscItems.scrap_material = regSupplier.register("scrap_material",
                () -> new ZPItem(new Item.Properties().stacksTo(64))
        ).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.items().addItemInTab(e, ZPTabs.zp_items_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.MISC_ITEMS_DIRECTORY);
            });
        }).end();

        ZPMiscItems.scrap_stack_material = regSupplier.register("scrap_stack_material",
                () -> new ZPItem(new Item.Properties().stacksTo(16))
        ).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.items().addItemInTab(e, ZPTabs.zp_items_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.MISC_ITEMS_DIRECTORY);
            });
        }).end();

        ZPMiscItems.oxygen = regSupplier.register("oxygen",
                () -> new ZPItem(new Item.Properties().stacksTo(1).durability(500))
        ).afterCreated((e, utils) -> {
            utils.items().addTagToItem(e, ZPTags.I_AQUALUNG_O2_ITEM);
            ZPUtility.sides().onlyClient(() -> {
                utils.items().addItemInTab(e, ZPTabs.zp_items_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_STICK, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.ITEMS_ITEMS_DIRECTORY);
            });
        }).end();
    }
}
