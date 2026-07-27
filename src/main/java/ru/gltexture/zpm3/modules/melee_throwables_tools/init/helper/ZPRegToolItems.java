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

package ru.gltexture.zpm3.modules.melee_throwables_tools.init.helper;

import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.modules.fluids.init.ZPFluids;
import ru.gltexture.zpm3.modules.common.init.ZPTabs;
import ru.gltexture.zpm3.modules.common.init.ZPTags;
import ru.gltexture.zpm3.modules.melee_throwables_tools.init.ZPMeleeThrowableToolsItems;
import ru.gltexture.zpm3.modules.melee_throwables_tools.instances.items.ZPMatches;
import ru.gltexture.zpm3.modules.melee_throwables_tools.instances.items.ZPMetalCuttersTool;
import ru.gltexture.zpm3.modules.melee_throwables_tools.instances.items.ZPWrenchTool;
import ru.gltexture.zpm3.engine.helpers.gen.ZPDataGenHelper;
import ru.gltexture.zpm3.engine.helpers.gen.data.ZPGenTextureData;
import ru.gltexture.zpm3.engine.instances.items.ZPItemBucket;
import ru.gltexture.zpm3.modules.melee_throwables_tools.instances.items.admin.ZPAdminWrenchFadingBlocks;
import ru.gltexture.zpm3.modules.melee_throwables_tools.tiers.ZPCommonToolMeleeTiers;
import ru.gltexture.zpm3.engine.registry.ZPRegistry;
import ru.gltexture.zpm3.engine.service.ZPUtility;

public abstract class ZPRegToolItems {
    public static void init(@NotNull ZPRegistry.ZPRegSupplier<Item> regSupplier) {
        ZPMeleeThrowableToolsItems.admin_wrench_torches = regSupplier.register("admin_wrench_torches",
                () -> new ZPAdminWrenchFadingBlocks(new Item.Properties())
        ).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.items().addItemInTab(e, ZPTabs.zp_items_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.TOOLS_ITEMS_DIRECTORY);
            });
        }).end();

        ZPMeleeThrowableToolsItems.wrench = regSupplier.register("wrench",
                () -> new ZPWrenchTool(ZPCommonToolMeleeTiers.ZP_WRENCH_CUTTERS, 1, 0.0f, new Item.Properties())
        ).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.items().addTagToItem(e, ZPTags.I_CAN_MINE_SCRAP);
                utils.items().addItemInTab(e, ZPTabs.zp_items_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.TOOLS_ITEMS_DIRECTORY);
            });
        }).end();

        ZPMeleeThrowableToolsItems.metal_cutters = regSupplier.register("metal_cutters",
                () -> new ZPMetalCuttersTool(ZPCommonToolMeleeTiers.ZP_WRENCH_CUTTERS, 1, 0.0f, new Item.Properties())
        ).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.items().addTagToItem(e, ZPTags.I_CAN_MINE_BARBARED_WIRE);
                utils.items().addTagToItem(e, ZPTags.I_CAN_MINE_CHAIN_LINK);
                utils.items().addItemInTab(e, ZPTabs.zp_items_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.TOOLS_ITEMS_DIRECTORY);
            });
        }).end();

        ZPMeleeThrowableToolsItems.matches = regSupplier.register("matches",
                () -> new ZPMatches(new Item.Properties().durability(24))
        ).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.items().addItemInTab(e, ZPTabs.zp_items_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.TOOLS_ITEMS_DIRECTORY);
            });
        }).end();

        ZPMeleeThrowableToolsItems.acid_bucket = regSupplier.register("acid_bucket",
                () -> new ZPItemBucket(() -> ZPFluids.acid_fluid.get(), new Item.Properties().stacksTo(1))
        ).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.items().addItemInTab(e, ZPTabs.zp_items_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.ITEMS_ITEMS_DIRECTORY);
            });
        }).end();

        ZPMeleeThrowableToolsItems.toxicwater_bucket = regSupplier.register("toxicwater_bucket",
                () -> new ZPItemBucket(() -> ZPFluids.toxic_fluid.get(), new Item.Properties().stacksTo(1))
        ).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.items().addItemInTab(e, ZPTabs.zp_items_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.ITEMS_ITEMS_DIRECTORY);
            });
        }).end();
    }
}
