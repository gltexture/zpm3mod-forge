package ru.gltexture.zpm3.assets.common.init.helper;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.api.distmarker.Dist;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.init.ZPFluids;
import ru.gltexture.zpm3.assets.common.init.ZPItems;
import ru.gltexture.zpm3.assets.common.init.ZPTabs;
import ru.gltexture.zpm3.assets.common.init.ZPTags;
import ru.gltexture.zpm3.assets.common.instances.items.ZPMatches;
import ru.gltexture.zpm3.assets.common.instances.items.ZPWrenchTool;
import ru.gltexture.zpm3.engine.helpers.gen.ZPDataGenHelper;
import ru.gltexture.zpm3.engine.helpers.gen.data.ZPGenTextureData;
import ru.gltexture.zpm3.engine.instances.items.ZPItem;
import ru.gltexture.zpm3.engine.instances.items.ZPItemBucket;
import ru.gltexture.zpm3.engine.instances.items.tier.ZPTiers;
import ru.gltexture.zpm3.engine.registry.ZPRegistry;
import ru.gltexture.zpm3.engine.service.ZPUtility;

public abstract class ZPRegItems {
    public static void init(@NotNull ZPRegistry.ZPRegSupplier<Item> regSupplier) {
        ZPItems.wrench = regSupplier.register("wrench",
                () -> new ZPWrenchTool(ZPTiers.ZP_WRENCH, 1, -3.8F, new Item.Properties())
        ).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.items().addTagToItem(e, ZPTags.I_MINEABLE_WITH_WRENCH);
                utils.items().addItemInTab(e, ZPTabs.zp_items_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.TOOLS_ITEMS_DIRECTORY);
            });
        }).end();

        ZPItems.matches = regSupplier.register("matches",
                () -> new ZPMatches(new Item.Properties().durability(24))
        ).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.items().addItemInTab(e, ZPTabs.zp_items_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.TOOLS_ITEMS_DIRECTORY);
            });
        }).end();

        ZPItems.acid_bucket = regSupplier.register("acid_bucket",
                () -> new ZPItemBucket(() -> ZPFluids.acid_fluid.get(), new Item.Properties().stacksTo(1))
        ).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.items().addItemInTab(e, ZPTabs.zp_items_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.ITEMS_ITEMS_DIRECTORY);
            });
        }).end();

        ZPItems.toxicwater_bucket = regSupplier.register("toxicwater_bucket",
                () -> new ZPItemBucket(() -> ZPFluids.toxic_fluid.get(), new Item.Properties().stacksTo(1))
        ).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.items().addItemInTab(e, ZPTabs.zp_items_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.ITEMS_ITEMS_DIRECTORY);
            });
        }).end();

        ZPItems.cement_material = regSupplier.register("cement_material",
                () -> new ZPItem(new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1))
        ).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.items().addItemInTab(e, ZPTabs.zp_misc_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.MISC_ITEMS_DIRECTORY);
            });
        }).end();

        ZPItems.chisel_material = regSupplier.register("chisel_material",
                () -> new ZPItem(new Item.Properties().stacksTo(16))
        ).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.items().addItemInTab(e, ZPTabs.zp_misc_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.MISC_ITEMS_DIRECTORY);
            });
        }).end();

        ZPItems.table_material = regSupplier.register("table_material",
                () -> new ZPItem(new Item.Properties().stacksTo(64))
        ).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.items().addItemInTab(e, ZPTabs.zp_misc_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.MISC_ITEMS_DIRECTORY);
            });
        }).end();

        ZPItems.shelves_material = regSupplier.register("shelves_material",
                () -> new ZPItem(new Item.Properties().stacksTo(16))
        ).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.items().addItemInTab(e, ZPTabs.zp_misc_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.MISC_ITEMS_DIRECTORY);
            });
        }).end();

        ZPItems.scrap_material = regSupplier.register("scrap_material",
                () -> new ZPItem(new Item.Properties().stacksTo(64))
        ).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.items().addItemInTab(e, ZPTabs.zp_misc_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.MISC_ITEMS_DIRECTORY);
            });
        }).end();

        ZPItems.scrap_stack_material = regSupplier.register("scrap_stack_material",
                () -> new ZPItem(new Item.Properties().stacksTo(16))
        ).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.items().addItemInTab(e, ZPTabs.zp_misc_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.MISC_ITEMS_DIRECTORY);
            });
        }).end();
    }
}
