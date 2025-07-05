package ru.gltexture.zpm3.assets.common.init.helper;

import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.init.ZPItems;
import ru.gltexture.zpm3.assets.common.init.ZPTabs;
import ru.gltexture.zpm3.engine.helpers.gen.data.ZPGenTextureData;
import ru.gltexture.zpm3.engine.objects.items.ZPItemAxe;
import ru.gltexture.zpm3.engine.objects.items.ZPItemPickaxe;
import ru.gltexture.zpm3.engine.objects.items.ZPItemSword;
import ru.gltexture.zpm3.engine.objects.items.tier.ZPTiers;
import ru.gltexture.zpm3.engine.helpers.gen.ZPDataGenHelper;
import ru.gltexture.zpm3.engine.helpers.ZPItemTabAddHelper;
import ru.gltexture.zpm3.engine.registry.ZPRegistry;

public abstract class ZPRegMelee {
    public static void init(@NotNull ZPRegistry.ZPRegSupplier<Item> regSupplier) {
        ZPItems.bat = regSupplier.register("bat", () -> new ZPItemSword(ZPTiers.ZP_WOOD, 6, -3.2F, new Item.Properties())
        ).postConsume(Dist.CLIENT, (e, utils) -> {
            ZPItemTabAddHelper.addItemInTab(e, ZPTabs.zp_melee_tab);
            utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_MELEE, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.MELEE_ITEMS_DIRECTORY);
        }).registryObject();

        ZPItems.iron_club = regSupplier.register("iron_club", () -> new ZPItemSword(ZPTiers.ZP_IRON_2, 3, -1.2F, new Item.Properties())
        ).postConsume(Dist.CLIENT, (e, utils) -> {
            utils.items().addItemInTab(e, ZPTabs.zp_melee_tab);
            utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_MELEE, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.MELEE_ITEMS_DIRECTORY);
        }).registryObject();

        ZPItems.pipe = regSupplier.register("pipe", () -> new ZPItemSword(ZPTiers.ZP_IRON_1, 3, -2.8F, new Item.Properties())
        ).postConsume(Dist.CLIENT, (e, utils) -> {
            utils.items().addItemInTab(e, ZPTabs.zp_melee_tab);
            utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_MELEE, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.MELEE_ITEMS_DIRECTORY);
        }).registryObject();

        ZPItems.golf_club = regSupplier.register("golf_club", () -> new ZPItemSword(ZPTiers.ZP_IRON_1, 4, -1.8F, new Item.Properties())
        ).postConsume(Dist.CLIENT, (e, utils) -> {
            utils.items().addItemInTab(e, ZPTabs.zp_melee_tab);
            utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_MELEE, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.MELEE_ITEMS_DIRECTORY);
        }).registryObject();

        ZPItems.crowbar = regSupplier.register("crowbar", () -> new ZPItemSword(ZPTiers.ZP_IRON_2, 4, -1.8F, new Item.Properties())
        ).postConsume(Dist.CLIENT, (e, utils) -> {
            utils.items().addItemInTab(e, ZPTabs.zp_melee_tab);
            utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_MELEE, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.MELEE_ITEMS_DIRECTORY);
        }).registryObject();

        ZPItems.cleaver = regSupplier.register("cleaver", () -> new ZPItemSword(ZPTiers.ZP_IRON_1, 2, -0.2F, new Item.Properties())
        ).postConsume(Dist.CLIENT, (e, utils) -> {
            utils.items().addItemInTab(e, ZPTabs.zp_melee_tab);
            utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_MELEE, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.MELEE_ITEMS_DIRECTORY);
        }).registryObject();

        ZPItems.hatchet = regSupplier.register("hatchet", () -> new ZPItemAxe(ZPTiers.ZP_IRON_3, 5.0f, -3.2F, new Item.Properties())
        ).postConsume(Dist.CLIENT, (e, utils) -> {
            utils.items().addItemInTab(e, ZPTabs.zp_melee_tab);
            utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_MELEE, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.MELEE_ITEMS_DIRECTORY);
        }).registryObject();

        ZPItems.sledgehammer = regSupplier.register("sledgehammer", () -> new ZPItemPickaxe(ZPTiers.ZP_IRON_3, 5, -3.2F, new Item.Properties())
        ).postConsume(Dist.CLIENT, (e, utils) -> {
            utils.items().addItemInTab(e, ZPTabs.zp_melee_tab);
            utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_MELEE, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.MELEE_ITEMS_DIRECTORY);
        }).registryObject();
    }

    /*
     public static final EItem WOODEN_SWORD = registerItem("wooden_sword", new SwordItem(Tiers.WOOD, 3, -2.4F, new EItem.Properties()));
   public static final EItem WOODEN_SHOVEL = registerItem("wooden_shovel", new ShovelItem(Tiers.WOOD, 1.5F, -3.0F, new EItem.Properties()));
   public static final EItem WOODEN_PICKAXE = registerItem("wooden_pickaxe", new PickaxeItem(Tiers.WOOD, 1, -2.8F, new EItem.Properties()));
   public static final EItem WOODEN_AXE = registerItem("wooden_axe", new AxeItem(Tiers.WOOD, 6.0F, -3.2F, new EItem.Properties()));
   public static final EItem WOODEN_HOE = registerItem("wooden_hoe", new HoeItem(Tiers.WOOD, 0, -3.0F, new EItem.Properties()));
   public static final EItem STONE_SWORD = registerItem("stone_sword", new SwordItem(Tiers.STONE, 3, -2.4F, new EItem.Properties()));
   public static final EItem STONE_SHOVEL = registerItem("stone_shovel", new ShovelItem(Tiers.STONE, 1.5F, -3.0F, new EItem.Properties()));
   public static final EItem STONE_PICKAXE = registerItem("stone_pickaxe", new PickaxeItem(Tiers.STONE, 1, -2.8F, new EItem.Properties()));
   public static final EItem STONE_AXE = registerItem("stone_axe", new AxeItem(Tiers.STONE, 7.0F, -3.2F, new EItem.Properties()));
   public static final EItem STONE_HOE = registerItem("stone_hoe", new HoeItem(Tiers.STONE, -1, -2.0F, new EItem.Properties()));
   public static final EItem GOLDEN_SWORD = registerItem("golden_sword", new SwordItem(Tiers.GOLD, 3, -2.4F, new EItem.Properties()));
   public static final EItem GOLDEN_SHOVEL = registerItem("golden_shovel", new ShovelItem(Tiers.GOLD, 1.5F, -3.0F, new EItem.Properties()));
   public static final EItem GOLDEN_PICKAXE = registerItem("golden_pickaxe", new PickaxeItem(Tiers.GOLD, 1, -2.8F, new EItem.Properties()));
   public static final EItem GOLDEN_AXE = registerItem("golden_axe", new AxeItem(Tiers.GOLD, 6.0F, -3.0F, new EItem.Properties()));
   public static final EItem GOLDEN_HOE = registerItem("golden_hoe", new HoeItem(Tiers.GOLD, 0, -3.0F, new EItem.Properties()));
   public static final EItem IRON_SWORD = registerItem("iron_sword", new SwordItem(Tiers.IRON, 3, -2.4F, new EItem.Properties()));
   public static final EItem IRON_SHOVEL = registerItem("iron_shovel", new ShovelItem(Tiers.IRON, 1.5F, -3.0F, new EItem.Properties()));
   public static final EItem IRON_PICKAXE = registerItem("iron_pickaxe", new PickaxeItem(Tiers.IRON, 1, -2.8F, new EItem.Properties()));
   public static final EItem IRON_AXE = registerItem("iron_axe", new AxeItem(Tiers.IRON, 6.0F, -3.1F, new EItem.Properties()));
   public static final EItem IRON_HOE = registerItem("iron_hoe", new HoeItem(Tiers.IRON, -2, -1.0F, new EItem.Properties()));
   public static final EItem DIAMOND_SWORD = registerItem("diamond_sword", new SwordItem(Tiers.DIAMOND, 3, -2.4F, new EItem.Properties()));
   public static final EItem DIAMOND_SHOVEL = registerItem("diamond_shovel", new ShovelItem(Tiers.DIAMOND, 1.5F, -3.0F, new EItem.Properties()));
   public static final EItem DIAMOND_PICKAXE = registerItem("diamond_pickaxe", new PickaxeItem(Tiers.DIAMOND, 1, -2.8F, new EItem.Properties()));
   public static final EItem DIAMOND_AXE = registerItem("diamond_axe", new AxeItem(Tiers.DIAMOND, 5.0F, -3.0F, new EItem.Properties()));
   public static final EItem DIAMOND_HOE = registerItem("diamond_hoe", new HoeItem(Tiers.DIAMOND, -3, 0.0F, new EItem.Properties()));
   public static final EItem NETHERITE_SWORD = registerItem("netherite_sword", new SwordItem(Tiers.NETHERITE, 3, -2.4F, (new EItem.Properties()).fireResistant()));
   public static final EItem NETHERITE_SHOVEL = registerItem("netherite_shovel", new ShovelItem(Tiers.NETHERITE, 1.5F, -3.0F, (new EItem.Properties()).fireResistant()));
   public static final EItem NETHERITE_PICKAXE = registerItem("netherite_pickaxe", new PickaxeItem(Tiers.NETHERITE, 1, -2.8F, (new EItem.Properties()).fireResistant()));
   public static final EItem NETHERITE_AXE = registerItem("netherite_axe", new AxeItem(Tiers.NETHERITE, 5.0F, -3.0F, (new EItem.Properties()).fireResistant()));
   public static final EItem NETHERITE_HOE = registerItem("netherite_hoe", new HoeItem(Tiers.NETHERITE, -4, 0.0F, (new EItem.Properties()).fireResistant()));
     */
}
