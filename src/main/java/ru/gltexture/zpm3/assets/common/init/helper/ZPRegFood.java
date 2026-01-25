package ru.gltexture.zpm3.assets.common.init.helper;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.init.ZPItems;
import ru.gltexture.zpm3.assets.common.init.ZPTabs;
import ru.gltexture.zpm3.engine.helpers.gen.data.ZPGenTextureData;
import ru.gltexture.zpm3.engine.instances.items.ZPItemFood;
import ru.gltexture.zpm3.engine.helpers.gen.ZPDataGenHelper;
import ru.gltexture.zpm3.engine.helpers.ZPItemTabAddHelper;
import ru.gltexture.zpm3.engine.registry.ZPRegistry;
import ru.gltexture.zpm3.engine.service.ZPUtility;

public abstract class ZPRegFood {
    public static final FoodProperties BEAN = (new FoodProperties.Builder()).nutrition(6).saturationMod(0.6F).build();
    public static final FoodProperties JAM = (new FoodProperties.Builder()).nutrition(4).saturationMod(0.3F).build();
    public static final FoodProperties MYSTERIOUS_CAN = (new FoodProperties.Builder()).nutrition(5).saturationMod(0.6F).effect(() -> new MobEffectInstance(MobEffects.HUNGER, 600), 0.5F).build();
    public static final FoodProperties PEACHES = (new FoodProperties.Builder()).nutrition(6).saturationMod(0.6F).build();
    public static final FoodProperties FRIED_EGG = (new FoodProperties.Builder()).nutrition(2).saturationMod(0.2F).build();
    public static final FoodProperties SODA = (new FoodProperties.Builder()).nutrition(2).saturationMod(0.3F).build();
    public static final FoodProperties SPRATS = (new FoodProperties.Builder()).nutrition(4).saturationMod(0.3F).effect(() -> new MobEffectInstance(MobEffects.POISON, 400), 0.5F).build();
    public static final FoodProperties WATER = (new FoodProperties.Builder()).nutrition(3).saturationMod(0.6F).build();

    public static void init(@NotNull ZPRegistry.ZPRegSupplier<Item> regSupplier) {
        ZPItems.bean = regSupplier.register("bean", () -> new ZPItemFood(new Item.Properties().stacksTo(16), ZPRegFood.BEAN, new ZPItemFood.ZPFoodProperties()))
                .afterCreated((e, utils) -> {
                    ZPUtility.sides().onlyClient(() -> {
                        utils.items().addItemInTab(e, ZPTabs.zp_food_tab);
                        utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_FOOD, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.FOOD_ITEMS_DIRECTORY);
                    });
                }).end();

        ZPItems.jam = regSupplier.register("jam", () -> new ZPItemFood(new Item.Properties().stacksTo(16), ZPRegFood.JAM, new ZPItemFood.ZPFoodProperties()))
                .afterCreated((e, utils) -> {
                    ZPUtility.sides().onlyClient(() -> {
                        utils.items().addItemInTab(e, ZPTabs.zp_food_tab);
                        utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_FOOD, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.FOOD_ITEMS_DIRECTORY);
                    });
                }).end();

        ZPItems.mysterious_can = regSupplier.register("mysterious_can", () -> new ZPItemFood(new Item.Properties().stacksTo(16), ZPRegFood.MYSTERIOUS_CAN, new ZPItemFood.ZPFoodProperties()))
                .afterCreated((e, utils) -> {
                    ZPUtility.sides().onlyClient(() -> {
                        ZPItemTabAddHelper.addItemInTab(e, ZPTabs.zp_food_tab);
                        utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_FOOD, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.FOOD_ITEMS_DIRECTORY);
                    });
                }).end();

        ZPItems.peaches = regSupplier.register("peaches", () -> new ZPItemFood(new Item.Properties().stacksTo(16), ZPRegFood.PEACHES, new ZPItemFood.ZPFoodProperties()))
                .afterCreated((e, utils) -> {
                    ZPUtility.sides().onlyClient(() -> {
                        utils.items().addItemInTab(e, ZPTabs.zp_food_tab);
                        utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_FOOD, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.FOOD_ITEMS_DIRECTORY);
                    });
                }).end();

        ZPItems.fried_egg = regSupplier.register("fried_egg", () -> new ZPItemFood(new Item.Properties().stacksTo(16), ZPRegFood.FRIED_EGG, new ZPItemFood.ZPFoodProperties()))
                .afterCreated((e, utils) -> {
                    ZPUtility.sides().onlyClient(() -> {
                        utils.items().addItemInTab(e, ZPTabs.zp_food_tab);
                        utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_FOOD, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.FOOD_ITEMS_DIRECTORY);
                    });
                }).end();

        ZPItems.soda = regSupplier.register("soda", () -> new ZPItemFood(new Item.Properties().stacksTo(16), ZPRegFood.SODA, new ZPItemFood.ZPFoodProperties().setDrinkable(true).setEatTime(16)))
                .afterCreated((e, utils) -> {
                    ZPUtility.sides().onlyClient(() -> {
                        utils.items().addItemInTab(e, ZPTabs.zp_food_tab);
                        utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_FOOD, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.FOOD_ITEMS_DIRECTORY);
                    });
                }).end();

        ZPItems.sprats = regSupplier.register("sprats", () -> new ZPItemFood(new Item.Properties().stacksTo(16), ZPRegFood.SPRATS, new ZPItemFood.ZPFoodProperties()))
                .afterCreated((e, utils) -> {
                    ZPUtility.sides().onlyClient(() -> {
                        utils.items().addItemInTab(e, ZPTabs.zp_food_tab);
                        utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_FOOD, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.FOOD_ITEMS_DIRECTORY);
                    });
                }).end();

        ZPItems.water = regSupplier.register("water", () -> new ZPItemFood(new Item.Properties().stacksTo(16), ZPRegFood.WATER, new ZPItemFood.ZPFoodProperties().setDrinkable(true)))
                .afterCreated((e, utils) -> {
                    ZPUtility.sides().onlyClient(() -> {
                        utils.items().addItemInTab(e, ZPTabs.zp_food_tab);
                        utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_FOOD, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.FOOD_ITEMS_DIRECTORY);
                    });
                }).end();
    }

    /*
    public static final FoodProperties APPLE = (new FoodProperties.Builder()).nutrition(4).saturationMod(0.3F).build();
   public static final FoodProperties BAKED_POTATO = (new FoodProperties.Builder()).nutrition(5).saturationMod(0.6F).build();
   public static final FoodProperties BEEF = (new FoodProperties.Builder()).nutrition(3).saturationMod(0.3F).meat().build();
   public static final FoodProperties BEETROOT = (new FoodProperties.Builder()).nutrition(1).saturationMod(0.6F).build();
   public static final FoodProperties BEETROOT_SOUP = stew(6).build();
   public static final FoodProperties BREAD = (new FoodProperties.Builder()).nutrition(5).saturationMod(0.6F).build();
   public static final FoodProperties CARROT = (new FoodProperties.Builder()).nutrition(3).saturationMod(0.6F).build();
   public static final FoodProperties CHICKEN = (new FoodProperties.Builder()).nutrition(2).saturationMod(0.3F).effect(new MobEffectInstance(MobEffects.HUNGER, 600, 0), 0.3F).meat().build();
   public static final FoodProperties CHORUS_FRUIT = (new FoodProperties.Builder()).nutrition(4).saturationMod(0.3F).alwaysEat().build();
   public static final FoodProperties COD = (new FoodProperties.Builder()).nutrition(2).saturationMod(0.1F).build();
   public static final FoodProperties COOKED_BEEF = (new FoodProperties.Builder()).nutrition(8).saturationMod(0.8F).meat().build();
   public static final FoodProperties COOKED_CHICKEN = (new FoodProperties.Builder()).nutrition(6).saturationMod(0.6F).meat().build();
   public static final FoodProperties COOKED_COD = (new FoodProperties.Builder()).nutrition(5).saturationMod(0.6F).build();
   public static final FoodProperties COOKED_MUTTON = (new FoodProperties.Builder()).nutrition(6).saturationMod(0.8F).meat().build();
   public static final FoodProperties COOKED_PORKCHOP = (new FoodProperties.Builder()).nutrition(8).saturationMod(0.8F).meat().build();
   public static final FoodProperties COOKED_RABBIT = (new FoodProperties.Builder()).nutrition(5).saturationMod(0.6F).meat().build();
   public static final FoodProperties COOKED_SALMON = (new FoodProperties.Builder()).nutrition(6).saturationMod(0.8F).build();
   public static final FoodProperties COOKIE = (new FoodProperties.Builder()).nutrition(2).saturationMod(0.1F).build();
   public static final FoodProperties DRIED_KELP = (new FoodProperties.Builder()).nutrition(1).saturationMod(0.3F).fast().build();
   public static final FoodProperties ENCHANTED_GOLDEN_APPLE = (new FoodProperties.Builder()).nutrition(4).saturationMod(1.2F).effect(new MobEffectInstance(MobEffects.REGENERATION, 400, 1), 1.0F).effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 6000, 0), 1.0F).effect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 6000, 0), 1.0F).effect(new MobEffectInstance(MobEffects.ABSORPTION, 2400, 3), 1.0F).alwaysEat().build();
   public static final FoodProperties GOLDEN_APPLE = (new FoodProperties.Builder()).nutrition(4).saturationMod(1.2F).effect(new MobEffectInstance(MobEffects.REGENERATION, 100, 1), 1.0F).effect(new MobEffectInstance(MobEffects.ABSORPTION, 2400, 0), 1.0F).alwaysEat().build();
   public static final FoodProperties GOLDEN_CARROT = (new FoodProperties.Builder()).nutrition(6).saturationMod(1.2F).build();
   public static final FoodProperties HONEY_BOTTLE = (new FoodProperties.Builder()).nutrition(6).saturationMod(0.1F).build();
   public static final FoodProperties MELON_SLICE = (new FoodProperties.Builder()).nutrition(2).saturationMod(0.3F).build();
   public static final FoodProperties MUSHROOM_STEW = stew(6).build();
   public static final FoodProperties MUTTON = (new FoodProperties.Builder()).nutrition(2).saturationMod(0.3F).meat().build();
   public static final FoodProperties POISONOUS_POTATO = (new FoodProperties.Builder()).nutrition(2).saturationMod(0.3F).effect(new MobEffectInstance(MobEffects.POISON, 100, 0), 0.6F).build();
   public static final FoodProperties PORKCHOP = (new FoodProperties.Builder()).nutrition(3).saturationMod(0.3F).meat().build();
   public static final FoodProperties POTATO = (new FoodProperties.Builder()).nutrition(1).saturationMod(0.3F).build();
   public static final FoodProperties PUFFERFISH = (new FoodProperties.Builder()).nutrition(1).saturationMod(0.1F).effect(new MobEffectInstance(MobEffects.POISON, 1200, 1), 1.0F).effect(new MobEffectInstance(MobEffects.HUNGER, 300, 2), 1.0F).effect(new MobEffectInstance(MobEffects.CONFUSION, 300, 0), 1.0F).build();
   public static final FoodProperties PUMPKIN_PIE = (new FoodProperties.Builder()).nutrition(8).saturationMod(0.3F).build();
   public static final FoodProperties RABBIT = (new FoodProperties.Builder()).nutrition(3).saturationMod(0.3F).meat().build();
   public static final FoodProperties RABBIT_STEW = stew(10).build();
   public static final FoodProperties ROTTEN_FLESH = (new FoodProperties.Builder()).nutrition(4).saturationMod(0.1F).effect(new MobEffectInstance(MobEffects.HUNGER, 600, 0), 0.8F).meat().build();
   public static final FoodProperties SALMON = (new FoodProperties.Builder()).nutrition(2).saturationMod(0.1F).build();
   public static final FoodProperties SPIDER_EYE = (new FoodProperties.Builder()).nutrition(2).saturationMod(0.8F).effect(new MobEffectInstance(MobEffects.POISON, 100, 0), 1.0F).build();
   public static final FoodProperties SUSPICIOUS_STEW = stew(6).alwaysEat().build();
   public static final FoodProperties SWEET_BERRIES = (new FoodProperties.Builder()).nutrition(2).saturationMod(0.1F).build();
   public static final FoodProperties GLOW_BERRIES = (new FoodProperties.Builder()).nutrition(2).saturationMod(0.1F).build();
   public static final FoodProperties TROPICAL_FISH = (new FoodProperties.Builder()).nutrition(1).saturationMod(0.1F).build();
     */
}
