package ru.gltexture.zpm3.modules.armor.init.helper;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.helpers.gen.ZPDataGenHelper;
import ru.gltexture.zpm3.engine.helpers.gen.data.ZPGenTextureData;
import ru.gltexture.zpm3.engine.instances.armor.ZPArmorItem;
import ru.gltexture.zpm3.engine.registry.ZPRegistry;
import ru.gltexture.zpm3.engine.service.ZPUtility;
import ru.gltexture.zpm3.modules.armor.init.ZPArmorItems;
import ru.gltexture.zpm3.modules.armor.instances.armor.ZPArmorMaterialsList;
import ru.gltexture.zpm3.modules.common.init.ZPTabs;

public abstract class ZPRegArmorItemsHelper {
    public static void init(@NotNull ZPRegistry.ZPRegSupplier<Item> regSupplier) {
        //ZPMiscItems.wrench = regSupplier.register("wrench",
        //        () -> new ZPWrenchTool(ZPCommonToolMeleeTiers.ZP_WRENCH, 1, 0.0f, new Item.Properties())
        //).afterCreated((e, utils) -> {
        //    ZPUtility.sides().onlyClient(() -> {
        //        utils.items().addTagToItem(e, ZPTags.I_MINEABLE_WITH_WRENCH);
        //        utils.items().addItemInTab(e, ZPTabs.zp_items_tab);
        //        utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.TOOLS_ITEMS_DIRECTORY);
        //    });
        //}).end();

        ZPArmorItems.night_vision_goggles = regSupplier.register("night_vision_goggles",
                () -> new ZPArmorItem(ZPArmorMaterialsList.NIGHT_VIS, ArmorItem.Type.HELMET, new Item.Properties())
        ).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.items().addItemInTab(e, ZPTabs.zp_armor_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.ARMOR_ITEMS_DIRECTORY);
            });
        }).end();
    }
}
