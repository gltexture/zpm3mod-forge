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

package ru.gltexture.zpm3.modules.food_medicine.init.helper;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.modules.food_medicine.init.ZPFoodMedicineItems;
import ru.gltexture.zpm3.modules.misc_items.init.ZPMiscItems;
import ru.gltexture.zpm3.modules.common.init.ZPTabs;
import ru.gltexture.zpm3.engine.helpers.gen.data.ZPGenTextureData;
import ru.gltexture.zpm3.engine.instances.items.ZPItemFood;
import ru.gltexture.zpm3.engine.helpers.gen.ZPDataGenHelper;
import ru.gltexture.zpm3.engine.helpers.ZPItemTabAddHelper;
import ru.gltexture.zpm3.engine.registry.ZPRegistry;
import ru.gltexture.zpm3.engine.service.ZPUtility;

public abstract class ZPRegFood {
    public static final FoodProperties BEAN = (new FoodProperties.Builder()).nutrition(6).saturationMod(0.8F).build();
    public static final FoodProperties JAM = (new FoodProperties.Builder()).nutrition(4).saturationMod(0.4F).build();
    public static final FoodProperties MYSTERIOUS_CAN = (new FoodProperties.Builder()).nutrition(5).saturationMod(1.0F).effect(() -> new MobEffectInstance(MobEffects.HUNGER, 600), 0.5F).build();
    public static final FoodProperties PEACHES = (new FoodProperties.Builder()).nutrition(6).saturationMod(0.8F).build();
    public static final FoodProperties FRIED_EGG = (new FoodProperties.Builder()).nutrition(3).saturationMod(0.4F).build();
    public static final FoodProperties CHOCOLATE = (new FoodProperties.Builder()).nutrition(2).saturationMod(0.2F).build();
    public static final FoodProperties MINECAKE = (new FoodProperties.Builder()).nutrition(4).saturationMod(0.3F).build();
    public static final FoodProperties ROTTEN_APPLE = (new FoodProperties.Builder()).nutrition(3).saturationMod(0.1F)
            .effect(() -> new MobEffectInstance(MobEffects.HUNGER, 200), 0.25F)
            .effect(() -> new MobEffectInstance(MobEffects.POISON, 200), 0.05F)
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 200), 0.15F).build();
    public static final FoodProperties SODA = (new FoodProperties.Builder()).nutrition(2).saturationMod(0.3F).build();
    public static final FoodProperties SPRATS = (new FoodProperties.Builder()).nutrition(4).saturationMod(0.5F).effect(() -> new MobEffectInstance(MobEffects.POISON, 400), 0.3F).build();
    public static final FoodProperties WATER = (new FoodProperties.Builder()).nutrition(3).saturationMod(0.6F).build();


    public static final ZPItemFood.ZPFoodProperties DEFAULT = new ZPItemFood.ZPFoodProperties();
    public static final ZPItemFood.ZPFoodProperties DRINK = new ZPItemFood.ZPFoodProperties().setDrinkable(true);
    public static final ZPItemFood.ZPFoodProperties DRINK_FAST = new ZPItemFood.ZPFoodProperties().setDrinkable(true).setEatTime(16);
    public static final ZPItemFood.ZPFoodProperties FAST_EAT = new ZPItemFood.ZPFoodProperties().setEatTime(16);

    public static void init(@NotNull ZPRegistry.ZPRegSupplier<Item> regSupplier) {
        ZPFoodMedicineItems.bean = regSupplier.register("bean", () -> new ZPItemFood(new Item.Properties().stacksTo(16), ZPRegFood.BEAN, ZPRegFood.DEFAULT))
                .afterCreated((e, utils) -> {
                    ZPUtility.sides().onlyClient(() -> {
                        utils.items().addItemInTab(e, ZPTabs.zp_food_tab);
                        utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_FOOD, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.FOOD_ITEMS_DIRECTORY);
                    });
                }).end();

        ZPFoodMedicineItems.jam = regSupplier.register("jam", () -> new ZPItemFood(new Item.Properties().stacksTo(16), ZPRegFood.JAM, ZPRegFood.DEFAULT))
                .afterCreated((e, utils) -> {
                    ZPUtility.sides().onlyClient(() -> {
                        utils.items().addItemInTab(e, ZPTabs.zp_food_tab);
                        utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_FOOD, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.FOOD_ITEMS_DIRECTORY);
                    });
                }).end();

        ZPFoodMedicineItems.mysterious_can = regSupplier.register("mysterious_can", () -> new ZPItemFood(new Item.Properties().stacksTo(16), ZPRegFood.MYSTERIOUS_CAN, ZPRegFood.DEFAULT))
                .afterCreated((e, utils) -> {
                    ZPUtility.sides().onlyClient(() -> {
                        ZPItemTabAddHelper.addItemInTab(e, ZPTabs.zp_food_tab);
                        utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_FOOD, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.FOOD_ITEMS_DIRECTORY);
                    });
                }).end();

        ZPFoodMedicineItems.peaches = regSupplier.register("peaches", () -> new ZPItemFood(new Item.Properties().stacksTo(16), ZPRegFood.PEACHES, ZPRegFood.DEFAULT))
                .afterCreated((e, utils) -> {
                    ZPUtility.sides().onlyClient(() -> {
                        utils.items().addItemInTab(e, ZPTabs.zp_food_tab);
                        utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_FOOD, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.FOOD_ITEMS_DIRECTORY);
                    });
                }).end();

        ZPFoodMedicineItems.fried_egg = regSupplier.register("fried_egg", () -> new ZPItemFood(new Item.Properties().stacksTo(16), ZPRegFood.FRIED_EGG, ZPRegFood.DEFAULT))
                .afterCreated((e, utils) -> {
                    ZPUtility.sides().onlyClient(() -> {
                        utils.items().addItemInTab(e, ZPTabs.zp_food_tab);
                        utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_FOOD, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.FOOD_ITEMS_DIRECTORY);
                    });
                }).end();

        ZPFoodMedicineItems.soda = regSupplier.register("soda", () -> new ZPItemFood(new Item.Properties().stacksTo(16), ZPRegFood.SODA, ZPRegFood.DRINK_FAST))
                .afterCreated((e, utils) -> {
                    ZPUtility.sides().onlyClient(() -> {
                        utils.items().addItemInTab(e, ZPTabs.zp_food_tab);
                        utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_FOOD, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.FOOD_ITEMS_DIRECTORY);
                    });
                }).end();

        ZPFoodMedicineItems.sprats = regSupplier.register("sprats", () -> new ZPItemFood(new Item.Properties().stacksTo(16), ZPRegFood.SPRATS, ZPRegFood.DEFAULT))
                .afterCreated((e, utils) -> {
                    ZPUtility.sides().onlyClient(() -> {
                        utils.items().addItemInTab(e, ZPTabs.zp_food_tab);
                        utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_FOOD, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.FOOD_ITEMS_DIRECTORY);
                    });
                }).end();

        ZPFoodMedicineItems.minecake = regSupplier.register("minecake", () -> new ZPItemFood(new Item.Properties().stacksTo(16), ZPRegFood.MINECAKE, ZPRegFood.DEFAULT))
                .afterCreated((e, utils) -> {
                    ZPUtility.sides().onlyClient(() -> {
                        utils.items().addItemInTab(e, ZPTabs.zp_food_tab);
                        utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_FOOD, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.FOOD_ITEMS_DIRECTORY);
                    });
                }).end();

        ZPFoodMedicineItems.chocolate = regSupplier.register("chocolate", () -> new ZPItemFood(new Item.Properties().stacksTo(16), ZPRegFood.CHOCOLATE, ZPRegFood.FAST_EAT))
                .afterCreated((e, utils) -> {
                    ZPUtility.sides().onlyClient(() -> {
                        utils.items().addItemInTab(e, ZPTabs.zp_food_tab);
                        utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_FOOD, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.FOOD_ITEMS_DIRECTORY);
                    });
                }).end();

        ZPFoodMedicineItems.rotten_apple = regSupplier.register("rotten_apple", () -> new ZPItemFood(new Item.Properties().stacksTo(16), ZPRegFood.ROTTEN_APPLE, ZPRegFood.DEFAULT))
                .afterCreated((e, utils) -> {
                    ZPUtility.sides().onlyClient(() -> {
                        utils.items().addItemInTab(e, ZPTabs.zp_food_tab);
                        utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_FOOD, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.FOOD_ITEMS_DIRECTORY);
                    });
                }).end();

        ZPFoodMedicineItems.water = regSupplier.register("water", () -> new ZPItemFood(new Item.Properties().stacksTo(16), ZPRegFood.WATER, ZPRegFood.DRINK))
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
