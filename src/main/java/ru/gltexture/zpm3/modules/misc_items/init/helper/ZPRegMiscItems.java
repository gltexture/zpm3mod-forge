package ru.gltexture.zpm3.modules.misc_items.init.helper;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.helpers.gen.ZPDataGenHelper;
import ru.gltexture.zpm3.engine.helpers.gen.data.ZPGenTextureData;
import ru.gltexture.zpm3.engine.instances.items.ZPItem;
import ru.gltexture.zpm3.engine.registry.ZPRegistry;
import ru.gltexture.zpm3.engine.service.ZPUtility;
import ru.gltexture.zpm3.modules.misc_items.init.ZPMiscItems;
import ru.gltexture.zpm3.modules.common.init.ZPTabs;

public abstract class ZPRegMiscItems {
    public static void init(@NotNull ZPRegistry.ZPRegSupplier<Item> regSupplier) {
        ZPMiscItems.cement_material = regSupplier.register("cement_material",
                () -> new ZPItem(new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1))
        ).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.items().addItemInTab(e, ZPTabs.zp_misc_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.MISC_ITEMS_DIRECTORY);
            });
        }).end();

        ZPMiscItems.chisel_material = regSupplier.register("chisel_material",
                () -> new ZPItem(new Item.Properties().stacksTo(16))
        ).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.items().addItemInTab(e, ZPTabs.zp_misc_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.MISC_ITEMS_DIRECTORY);
            });
        }).end();

        ZPMiscItems.table_material = regSupplier.register("table_material",
                () -> new ZPItem(new Item.Properties().stacksTo(64))
        ).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.items().addItemInTab(e, ZPTabs.zp_misc_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.MISC_ITEMS_DIRECTORY);
            });
        }).end();

        ZPMiscItems.shelves_material = regSupplier.register("shelves_material",
                () -> new ZPItem(new Item.Properties().stacksTo(16))
        ).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.items().addItemInTab(e, ZPTabs.zp_misc_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.MISC_ITEMS_DIRECTORY);
            });
        }).end();

        ZPMiscItems.scrap_material = regSupplier.register("scrap_material",
                () -> new ZPItem(new Item.Properties().stacksTo(64))
        ).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.items().addItemInTab(e, ZPTabs.zp_misc_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.MISC_ITEMS_DIRECTORY);
            });
        }).end();

        ZPMiscItems.scrap_stack_material = regSupplier.register("scrap_stack_material",
                () -> new ZPItem(new Item.Properties().stacksTo(16))
        ).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.items().addItemInTab(e, ZPTabs.zp_misc_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.MISC_ITEMS_DIRECTORY);
            });
        }).end();
    }
}
