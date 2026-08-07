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

import ru.gltexture.zpm3.engine.core.config.builtin.ZPZombieConfig;
import ru.gltexture.zpm3.engine.registry.ZPCommonRegistry;
import ru.gltexture.zpm3.modules.common.init.ZPSounds;
import ru.gltexture.zpm3.modules.common.init.ZPTabs;
import ru.gltexture.zpm3.modules.food_medicine.init.ZPFoodMedicineItems;
import ru.gltexture.zpm3.modules.mob_effects.init.ZPMobEffects;
import ru.gltexture.zpm3.engine.helpers.gen.ZPDataGenHelper;
import ru.gltexture.zpm3.engine.helpers.gen.data.ZPGenTextureData;
import ru.gltexture.zpm3.engine.instances.items.ZPItemMedicine;
import ru.gltexture.zpm3.engine.service.ZPUtility;

public abstract class ZPRegMedicine {
    public static final ZPItemMedicine.ZPMedicineProperties VODKA = new ZPItemMedicine.ZPMedicineProperties(ZPRegMedicine.DEFAULT_MEDICINE()
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 2400, 0), 1.0F)
            .effect(() -> new MobEffectInstance(ZPMobEffects.adrenaline.get(), 900), 1.0F)
            .effect(() -> new MobEffectInstance(ZPMobEffects.radiation_protection.get(), 600), 1.0F)
            .build())
                .setMedicineAnim(ZPItemMedicine.MedicineAnim.DRINK)
                .setIntoxication(80);

    public static final ZPItemMedicine.ZPMedicineProperties WHISKEY = new ZPItemMedicine.ZPMedicineProperties(ZPRegMedicine.DEFAULT_MEDICINE()
            .nutrition(1).saturationMod(0.05f)
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 3000, 0), 1.0F)
            .effect(() -> new MobEffectInstance(ZPMobEffects.adrenaline.get(), 1800), 1.0F)
            .effect(() -> new MobEffectInstance(ZPMobEffects.radiation_protection.get(), 900), 1.0F)
            .build())
                .setMedicineAnim(ZPItemMedicine.MedicineAnim.DRINK)
                .setIntoxication(96);

    public static final ZPItemMedicine.ZPMedicineProperties RADIOPROTECTION = new ZPItemMedicine.ZPMedicineProperties(ZPRegMedicine.DEFAULT_MEDICINE()
            .effect(() -> new MobEffectInstance(ZPMobEffects.radiation_protection.get(), 4800), 1.0F)
            .build())
                .setMedicineAnim(ZPItemMedicine.MedicineAnim.EAT);

    public static final ZPItemMedicine.ZPMedicineProperties ADRENALINE = new ZPItemMedicine.ZPMedicineProperties(ZPRegMedicine.DEFAULT_MEDICINE()
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1200, 0), 1.0F)
            .effect(() -> new MobEffectInstance(ZPMobEffects.adrenaline.get(), 6000), 1.0F).build())
                .setMedicineAnim(ZPItemMedicine.MedicineAnim.BLOCK)
                .setCanBeAffectedOnOther(true)
                .setSoundToPlayOnConsume(() -> ZPSounds.syringe.get());

    public static final ZPItemMedicine.ZPMedicineProperties MORPHINE = new ZPItemMedicine.ZPMedicineProperties(ZPRegMedicine.DEFAULT_MEDICINE()
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 400), 1.0F)
            .effect(() -> new MobEffectInstance(ZPMobEffects.fracture.get(), -10), 1.0F)
            .build())
                 .setMedicineAnim(ZPItemMedicine.MedicineAnim.BLOCK)
                 .setCanBeAffectedOnOther(true)
                 .setSoundToPlayOnConsume(() -> ZPSounds.syringe.get())
                 .setIntoxication(100);

    public static final ZPItemMedicine.ZPMedicineProperties AID_KIT = new ZPItemMedicine.ZPMedicineProperties(ZPRegMedicine.DEFAULT_MEDICINE()
            .effect(() -> new MobEffectInstance(MobEffects.POISON, -10), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, -10), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.HUNGER, -10), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, -10), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 300, 1), 1.0F)
            .effect(() -> new MobEffectInstance(ZPMobEffects.bleeding.get(), -10), 1.0F)
            .build())
                .setMedicineAnim(ZPItemMedicine.MedicineAnim.BLOCK)
                .setEatTime(128)
                .setSoundToPlayOnConsume(() -> ZPSounds.bandage.get());

    public static final ZPItemMedicine.ZPMedicineProperties ANTI_HEADACHE = new ZPItemMedicine.ZPMedicineProperties(ZPRegMedicine.DEFAULT_MEDICINE()
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, -10), 1.0F)
            .build())
                .setMedicineAnim(ZPItemMedicine.MedicineAnim.EAT);

    public static final ZPItemMedicine.ZPMedicineProperties ANTI_HUNGER = new ZPItemMedicine.ZPMedicineProperties(ZPRegMedicine.DEFAULT_MEDICINE()
            .effect(() -> new MobEffectInstance(MobEffects.HUNGER, -10), 1.0F)
            .nutrition(1).saturationMod(4.0f)
            .build())
                .setMedicineAnim(ZPItemMedicine.MedicineAnim.EAT);

    public static final ZPItemMedicine.ZPMedicineProperties ANTI_POISON = new ZPItemMedicine.ZPMedicineProperties(ZPRegMedicine.DEFAULT_MEDICINE()
            .effect(() -> new MobEffectInstance(MobEffects.POISON, -10), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.HUNGER, -10), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, -10), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.DIG_SLOWDOWN, -10), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.WEAKNESS, -10), 1.0F)
            .effect(() -> new MobEffectInstance(ZPMobEffects.immune.get(), 800), 1.0F)
            .build())
                .setMedicineAnim(ZPItemMedicine.MedicineAnim.EAT);

    public static final ZPItemMedicine.ZPMedicineProperties ANTI_ZPLAGUE = new ZPItemMedicine.ZPMedicineProperties(ZPRegMedicine.DEFAULT_MEDICINE()
            .effect(() -> new MobEffectInstance(ZPMobEffects.zombie_plague.get(), -10), 1.0F)
            .build())
                .setMedicineAnim(ZPItemMedicine.MedicineAnim.BLOCK)
                .setCanBeAffectedOnOther(true)
                .setSoundToPlayOnConsume(() -> ZPSounds.syringe.get());

    public static final ZPItemMedicine.ZPMedicineProperties ZPLAGUE = new ZPItemMedicine.ZPMedicineProperties(ZPRegMedicine.DEFAULT_MEDICINE()
            .effect(() -> new MobEffectInstance(ZPMobEffects.zombie_plague.get(), ZPZombieConfig.ZOMBIE_PLAGUE_VIRUS_EFFECT_TIME_TICKS.getVar()), 1.0F)
            .build())
                .setMedicineAnim(ZPItemMedicine.MedicineAnim.BLOCK)
                .setCanBeAffectedOnOther(true)
                .setSoundToPlayOnConsume(() -> ZPSounds.syringe.get());

    public static final ZPItemMedicine.ZPMedicineProperties ANTIBIOTICS = new ZPItemMedicine.ZPMedicineProperties(ZPRegMedicine.DEFAULT_MEDICINE()
            .effect(() -> new MobEffectInstance(MobEffects.POISON, -10), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, -10), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.HUNGER, -10), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, -10), 1.0F)
            .effect(() -> new MobEffectInstance(ZPMobEffects.immune.get(), 1200), 1.0F)
            .build())
                .setMedicineAnim(ZPItemMedicine.MedicineAnim.BLOCK)
                .setCanBeAffectedOnOther(true)
                .setSoundToPlayOnConsume(() -> ZPSounds.syringe.get());

    public static final ZPItemMedicine.ZPMedicineProperties BANDAGE = new ZPItemMedicine.ZPMedicineProperties(ZPRegMedicine.DEFAULT_MEDICINE()
            .effect(() -> new MobEffectInstance(ZPMobEffects.bleeding.get(), -1, -1), 1.0F)
            .build())
                .setMedicineAnim(ZPItemMedicine.MedicineAnim.BLOCK)
                .setSoundToPlayOnConsume(() -> ZPSounds.bandage.get());

    public static final ZPItemMedicine.ZPMedicineProperties BETTER_VISION = new ZPItemMedicine.ZPMedicineProperties(ZPRegMedicine.DEFAULT_MEDICINE()
            .effect(() -> new MobEffectInstance(ZPMobEffects.better_vision.get(), 6000), 1.0F)
            .build())
                .setMedicineAnim(ZPItemMedicine.MedicineAnim.EAT);

    public static final ZPItemMedicine.ZPMedicineProperties VITAMIN = new ZPItemMedicine.ZPMedicineProperties(ZPRegMedicine.DEFAULT_MEDICINE()
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 600), 1.0F)
            .effect(() -> new MobEffectInstance(ZPMobEffects.immune.get(), 4800), 1.0F)
            .build())
                .setMedicineAnim(ZPItemMedicine.MedicineAnim.EAT);

    public static final ZPItemMedicine.ZPMedicineProperties DRUGS = new ZPItemMedicine.ZPMedicineProperties(ZPRegMedicine.DEFAULT_MEDICINE()
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 6000), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.HUNGER, 600), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.POISON, 200), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 2400), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1200), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1200), 1.0F)
            .effect(() -> new MobEffectInstance(ZPMobEffects.adrenaline.get(), 6000), 1.0F)
            .build())
                .setMedicineAnim(ZPItemMedicine.MedicineAnim.EAT)
                .setIntoxication(200);

    public static final ZPItemMedicine.ZPMedicineProperties SPLINT = new ZPItemMedicine.ZPMedicineProperties(ZPRegMedicine.DEFAULT_MEDICINE()
            .effect(() -> new MobEffectInstance(ZPMobEffects.fracture.get(), -1), 1.0F)
            .build())
                .setMedicineAnim(ZPItemMedicine.MedicineAnim.BLOCK)
                .setSoundToPlayOnConsume(() -> ZPSounds.bandage.get());

    private static FoodProperties.Builder DEFAULT_MEDICINE() {
        return (new FoodProperties.Builder()).nutrition(0).saturationMod(0.0F);
    }

    public static void init(ZPFoodMedicineItems zpItems, @NotNull ZPCommonRegistry.ZPRegSupplier<Item> regSupplier) {
        zpItems.initInstanceCollecting("medicine");

        ZPFoodMedicineItems.adrenaline_syringe = regSupplier.register("adrenaline_syringe", () -> new ZPItemMedicine(new Item.Properties().stacksTo(4), ZPRegMedicine.ADRENALINE
                )).afterCreated((e, utils) -> {
                    ZPUtility.sides().onlyClient(() -> {
                        utils.items().addItemInTab(e, ZPTabs.zp_medicine_tab);
                        utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_FOOD, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.MEDICINE_ITEMS_DIRECTORY);
                    });
        }).end();

        ZPFoodMedicineItems.morphine_syringe = regSupplier.register("morphine_syringe", () -> new ZPItemMedicine(new Item.Properties().stacksTo(4), ZPRegMedicine.MORPHINE
        )).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.items().addItemInTab(e, ZPTabs.zp_medicine_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_FOOD, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.MEDICINE_ITEMS_DIRECTORY);
            });
        }).end();

        ZPFoodMedicineItems.bandage = regSupplier.register("bandage", () -> new ZPItemMedicine(new Item.Properties().durability(4), ZPRegMedicine.BANDAGE
        )).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.items().addItemInTab(e, ZPTabs.zp_medicine_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_FOOD, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.MEDICINE_ITEMS_DIRECTORY);
            });
        }).end();

        ZPFoodMedicineItems.aid_kit = regSupplier.register("aid_kit", () -> new ZPItemMedicine(new Item.Properties().durability(2), ZPRegMedicine.AID_KIT
        )).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.items().addItemInTab(e, ZPTabs.zp_medicine_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_FOOD, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.MEDICINE_ITEMS_DIRECTORY);
            });
        }).end();

        ZPFoodMedicineItems.anti_headache_pill = regSupplier.register("anti_headache_pill", () -> new ZPItemMedicine(new Item.Properties().stacksTo(16), ZPRegMedicine.ANTI_HEADACHE
        )).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.items().addItemInTab(e, ZPTabs.zp_medicine_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_FOOD, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.MEDICINE_ITEMS_DIRECTORY);
            });
        }).end();

        ZPFoodMedicineItems.anti_hunger_pill = regSupplier.register("anti_hunger_pill", () -> new ZPItemMedicine(new Item.Properties().stacksTo(16), ZPRegMedicine.ANTI_HUNGER
        )).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.items().addItemInTab(e, ZPTabs.zp_medicine_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_FOOD, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.MEDICINE_ITEMS_DIRECTORY);
            });
        }).end();

        ZPFoodMedicineItems.anti_poison_pill = regSupplier.register("anti_poison_pill", () -> new ZPItemMedicine(new Item.Properties().stacksTo(16), ZPRegMedicine.ANTI_POISON
        )).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.items().addItemInTab(e, ZPTabs.zp_medicine_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_FOOD, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.MEDICINE_ITEMS_DIRECTORY);
            });
        }).end();

        ZPFoodMedicineItems.zplague_syringe = regSupplier.register("zplague_syringe", () -> new ZPItemMedicine(new Item.Properties().stacksTo(1), ZPRegMedicine.ZPLAGUE
        )).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.items().addItemInTab(e, ZPTabs.zp_medicine_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_FOOD, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.MEDICINE_ITEMS_DIRECTORY);
            });
        }).end();

        ZPFoodMedicineItems.anti_zplague_syringe = regSupplier.register("anti_zplague_syringe", () -> new ZPItemMedicine(new Item.Properties().stacksTo(1), ZPRegMedicine.ANTI_ZPLAGUE
        )).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.items().addItemInTab(e, ZPTabs.zp_medicine_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_FOOD, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.MEDICINE_ITEMS_DIRECTORY);
            });
        }).end();

        ZPFoodMedicineItems.antibiotics_syringe = regSupplier.register("antibiotics_syringe", () -> new ZPItemMedicine(new Item.Properties().stacksTo(4), ZPRegMedicine.ANTIBIOTICS
        )).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.items().addItemInTab(e, ZPTabs.zp_medicine_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_FOOD, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.MEDICINE_ITEMS_DIRECTORY);
            });
        }).end();

        ZPFoodMedicineItems.better_vision_pill = regSupplier.register("better_vision_pill", () -> new ZPItemMedicine(new Item.Properties().stacksTo(16), ZPRegMedicine.BETTER_VISION
        )).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.items().addItemInTab(e, ZPTabs.zp_medicine_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_FOOD, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.MEDICINE_ITEMS_DIRECTORY);
            });
        }).end();

        ZPFoodMedicineItems.drugs = regSupplier.register("drugs", () -> new ZPItemMedicine(new Item.Properties().stacksTo(16), ZPRegMedicine.DRUGS
        )).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.items().addItemInTab(e, ZPTabs.zp_medicine_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_FOOD, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.MEDICINE_ITEMS_DIRECTORY);
            });
        }).end();

        ZPFoodMedicineItems.vitamin_pill = regSupplier.register("vitamin_pill", () -> new ZPItemMedicine(new Item.Properties().stacksTo(16), ZPRegMedicine.VITAMIN
        )).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.items().addItemInTab(e, ZPTabs.zp_medicine_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_FOOD, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.MEDICINE_ITEMS_DIRECTORY);
            });
        }).end();

        ZPFoodMedicineItems.splint = regSupplier.register("splint", () -> new ZPItemMedicine(new Item.Properties().stacksTo(1), ZPRegMedicine.SPLINT
        )).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.items().addItemInTab(e, ZPTabs.zp_medicine_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_FOOD, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.MEDICINE_ITEMS_DIRECTORY);
            });
        }).end();

        ZPFoodMedicineItems.vodka_medicine = regSupplier.register("vodka_medicine", () -> new ZPItemMedicine(new Item.Properties().stacksTo(8), ZPRegMedicine.VODKA
        )).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.items().addItemInTab(e, ZPTabs.zp_medicine_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_FOOD, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.MEDICINE_ITEMS_DIRECTORY);
            });
        }).end();

        ZPFoodMedicineItems.whiskey_medicine = regSupplier.register("whiskey_medicine", () -> new ZPItemMedicine(new Item.Properties().stacksTo(8), ZPRegMedicine.WHISKEY
        )).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.items().addItemInTab(e, ZPTabs.zp_medicine_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_FOOD, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.MEDICINE_ITEMS_DIRECTORY);
            });
        }).end();

        ZPFoodMedicineItems.radiation_protection_pill = regSupplier.register("radiation_protection_pill", () -> new ZPItemMedicine(new Item.Properties().stacksTo(16), ZPRegMedicine.RADIOPROTECTION
        )).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.items().addItemInTab(e, ZPTabs.zp_medicine_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_FOOD, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.MEDICINE_ITEMS_DIRECTORY);
            });
        }).end();

        zpItems.stopInstanceCollecting();
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
