package ru.gltexture.zpm3.assets.common.init.helper;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.init.ZPEntities;
import ru.gltexture.zpm3.assets.common.init.ZPItems;
import ru.gltexture.zpm3.assets.common.init.ZPTabs;
import ru.gltexture.zpm3.assets.common.instances.entities.ZPAcidBottleEntity;
import ru.gltexture.zpm3.assets.common.instances.items.ZPItemAxe;
import ru.gltexture.zpm3.assets.common.instances.items.ZPItemPickaxe;
import ru.gltexture.zpm3.assets.common.instances.items.ZPItemSword;
import ru.gltexture.zpm3.assets.common.instances.items.tier.ZPTiers;
import ru.gltexture.zpm3.engine.helpers.ZPDispenserHelper;
import ru.gltexture.zpm3.engine.helpers.ZPItemTabAddHelper;
import ru.gltexture.zpm3.engine.registry.base.ZPRegistry;

public abstract class ZPRegMelee {
    public static void init(@NotNull ZPRegistry.ZPRegSupplier<Item> regSupplier) {
        ZPItems.bat = regSupplier.register("bat", () -> new ZPItemSword(ZPTiers.ZP_WOOD, 6, -3.2F, new Item.Properties())).postConsume(Dist.CLIENT, (e) -> {
            ZPItemTabAddHelper.matchTabItem(e, ZPTabs.zp_melee_tab);
        }).registryObject();

        ZPItems.iron_club = regSupplier.register("iron_club", () -> new ZPItemSword(ZPTiers.ZP_IRON_2, 3, -1.2F, new Item.Properties())).postConsume(Dist.CLIENT, (e) -> {
            ZPItemTabAddHelper.matchTabItem(e, ZPTabs.zp_melee_tab);
        }).registryObject();

        ZPItems.pipe = regSupplier.register("pipe", () -> new ZPItemSword(ZPTiers.ZP_IRON_1, 3, -2.8F, new Item.Properties())).postConsume(Dist.CLIENT, (e) -> {
            ZPItemTabAddHelper.matchTabItem(e, ZPTabs.zp_melee_tab);
        }).registryObject();

        ZPItems.golf_club = regSupplier.register("golf_club", () -> new ZPItemSword(ZPTiers.ZP_IRON_1, 4, -1.8F, new Item.Properties())).postConsume(Dist.CLIENT, (e) -> {
            ZPItemTabAddHelper.matchTabItem(e, ZPTabs.zp_melee_tab);
        }).registryObject();

        ZPItems.crowbar = regSupplier.register("crowbar", () -> new ZPItemSword(ZPTiers.ZP_IRON_2, 4, -1.8F, new Item.Properties())).postConsume(Dist.CLIENT, (e) -> {
            ZPItemTabAddHelper.matchTabItem(e, ZPTabs.zp_melee_tab);
        }).registryObject();

        ZPItems.hatchet = regSupplier.register("hatchet", () -> new ZPItemAxe(ZPTiers.ZP_IRON_3, 5.0f, -3.2F, new Item.Properties())).postConsume(Dist.CLIENT, (e) -> {
            ZPItemTabAddHelper.matchTabItem(e, ZPTabs.zp_melee_tab);
        }).registryObject();

        ZPItems.sledgehammer = regSupplier.register("sledgehammer", () -> new ZPItemPickaxe(ZPTiers.ZP_IRON_3, 5, -3.2F, new Item.Properties())).postConsume(Dist.CLIENT, (e) -> {
            ZPItemTabAddHelper.matchTabItem(e, ZPTabs.zp_melee_tab);
        }).registryObject();


    }

    /*
     public static final Item WOODEN_SWORD = registerItem("wooden_sword", new SwordItem(Tiers.WOOD, 3, -2.4F, new Item.Properties()));
   public static final Item WOODEN_SHOVEL = registerItem("wooden_shovel", new ShovelItem(Tiers.WOOD, 1.5F, -3.0F, new Item.Properties()));
   public static final Item WOODEN_PICKAXE = registerItem("wooden_pickaxe", new PickaxeItem(Tiers.WOOD, 1, -2.8F, new Item.Properties()));
   public static final Item WOODEN_AXE = registerItem("wooden_axe", new AxeItem(Tiers.WOOD, 6.0F, -3.2F, new Item.Properties()));
   public static final Item WOODEN_HOE = registerItem("wooden_hoe", new HoeItem(Tiers.WOOD, 0, -3.0F, new Item.Properties()));
   public static final Item STONE_SWORD = registerItem("stone_sword", new SwordItem(Tiers.STONE, 3, -2.4F, new Item.Properties()));
   public static final Item STONE_SHOVEL = registerItem("stone_shovel", new ShovelItem(Tiers.STONE, 1.5F, -3.0F, new Item.Properties()));
   public static final Item STONE_PICKAXE = registerItem("stone_pickaxe", new PickaxeItem(Tiers.STONE, 1, -2.8F, new Item.Properties()));
   public static final Item STONE_AXE = registerItem("stone_axe", new AxeItem(Tiers.STONE, 7.0F, -3.2F, new Item.Properties()));
   public static final Item STONE_HOE = registerItem("stone_hoe", new HoeItem(Tiers.STONE, -1, -2.0F, new Item.Properties()));
   public static final Item GOLDEN_SWORD = registerItem("golden_sword", new SwordItem(Tiers.GOLD, 3, -2.4F, new Item.Properties()));
   public static final Item GOLDEN_SHOVEL = registerItem("golden_shovel", new ShovelItem(Tiers.GOLD, 1.5F, -3.0F, new Item.Properties()));
   public static final Item GOLDEN_PICKAXE = registerItem("golden_pickaxe", new PickaxeItem(Tiers.GOLD, 1, -2.8F, new Item.Properties()));
   public static final Item GOLDEN_AXE = registerItem("golden_axe", new AxeItem(Tiers.GOLD, 6.0F, -3.0F, new Item.Properties()));
   public static final Item GOLDEN_HOE = registerItem("golden_hoe", new HoeItem(Tiers.GOLD, 0, -3.0F, new Item.Properties()));
   public static final Item IRON_SWORD = registerItem("iron_sword", new SwordItem(Tiers.IRON, 3, -2.4F, new Item.Properties()));
   public static final Item IRON_SHOVEL = registerItem("iron_shovel", new ShovelItem(Tiers.IRON, 1.5F, -3.0F, new Item.Properties()));
   public static final Item IRON_PICKAXE = registerItem("iron_pickaxe", new PickaxeItem(Tiers.IRON, 1, -2.8F, new Item.Properties()));
   public static final Item IRON_AXE = registerItem("iron_axe", new AxeItem(Tiers.IRON, 6.0F, -3.1F, new Item.Properties()));
   public static final Item IRON_HOE = registerItem("iron_hoe", new HoeItem(Tiers.IRON, -2, -1.0F, new Item.Properties()));
   public static final Item DIAMOND_SWORD = registerItem("diamond_sword", new SwordItem(Tiers.DIAMOND, 3, -2.4F, new Item.Properties()));
   public static final Item DIAMOND_SHOVEL = registerItem("diamond_shovel", new ShovelItem(Tiers.DIAMOND, 1.5F, -3.0F, new Item.Properties()));
   public static final Item DIAMOND_PICKAXE = registerItem("diamond_pickaxe", new PickaxeItem(Tiers.DIAMOND, 1, -2.8F, new Item.Properties()));
   public static final Item DIAMOND_AXE = registerItem("diamond_axe", new AxeItem(Tiers.DIAMOND, 5.0F, -3.0F, new Item.Properties()));
   public static final Item DIAMOND_HOE = registerItem("diamond_hoe", new HoeItem(Tiers.DIAMOND, -3, 0.0F, new Item.Properties()));
   public static final Item NETHERITE_SWORD = registerItem("netherite_sword", new SwordItem(Tiers.NETHERITE, 3, -2.4F, (new Item.Properties()).fireResistant()));
   public static final Item NETHERITE_SHOVEL = registerItem("netherite_shovel", new ShovelItem(Tiers.NETHERITE, 1.5F, -3.0F, (new Item.Properties()).fireResistant()));
   public static final Item NETHERITE_PICKAXE = registerItem("netherite_pickaxe", new PickaxeItem(Tiers.NETHERITE, 1, -2.8F, (new Item.Properties()).fireResistant()));
   public static final Item NETHERITE_AXE = registerItem("netherite_axe", new AxeItem(Tiers.NETHERITE, 5.0F, -3.0F, (new Item.Properties()).fireResistant()));
   public static final Item NETHERITE_HOE = registerItem("netherite_hoe", new HoeItem(Tiers.NETHERITE, -4, 0.0F, (new Item.Properties()).fireResistant()));
     */
}
