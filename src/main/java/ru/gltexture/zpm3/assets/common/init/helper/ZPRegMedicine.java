package ru.gltexture.zpm3.assets.common.init.helper;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.global.ZPConstants;
import ru.gltexture.zpm3.assets.common.init.ZPItems;
import ru.gltexture.zpm3.assets.common.init.ZPSounds;
import ru.gltexture.zpm3.assets.common.init.ZPTabs;
import ru.gltexture.zpm3.assets.mob_effects.init.ZPMobEffects;
import ru.gltexture.zpm3.engine.helpers.gen.ZPDataGenHelper;
import ru.gltexture.zpm3.engine.helpers.gen.data.ZPGenTextureData;
import ru.gltexture.zpm3.engine.instances.items.ZPItemMedicine;
import ru.gltexture.zpm3.engine.registry.ZPRegistry;
import ru.gltexture.zpm3.engine.service.ZPUtility;

public abstract class ZPRegMedicine {
    public static final FoodProperties ADRENALINE = ZPRegMedicine.DEFAULT_MEDICINE()
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1200, 1), 1.0F)
            .effect(() -> new MobEffectInstance(ZPMobEffects.adrenaline.get(), 1200), 1.0F)
            .build();

    public static final FoodProperties MORPHINE = ZPRegMedicine.DEFAULT_MEDICINE()
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 200), 1.0F)
            .effect(() -> new MobEffectInstance(ZPMobEffects.fracture.get(), -10), 1.0F)
            .build();

    public static final FoodProperties AID_KIT = ZPRegMedicine.DEFAULT_MEDICINE()
            .effect(() -> new MobEffectInstance(MobEffects.POISON, -10), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, -10), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.HUNGER, -10), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, -10), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 200, 1), 1.0F)
            .effect(() -> new MobEffectInstance(ZPMobEffects.bleeding.get(), -10), 1.0F)
            .build();

    public static final FoodProperties ANTI_HEADACHE = ZPRegMedicine.DEFAULT_MEDICINE()
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, -10), 1.0F)
            .build();

    public static final FoodProperties ANTI_HUNGER = ZPRegMedicine.DEFAULT_MEDICINE()
            .effect(() -> new MobEffectInstance(MobEffects.HUNGER, -10), 1.0F)
            .build();

    public static final FoodProperties ANTI_POISON = ZPRegMedicine.DEFAULT_MEDICINE()
            .effect(() -> new MobEffectInstance(MobEffects.POISON, -10), 1.0F)
            .build();

    public static final FoodProperties ANTI_ZPLAGUE = ZPRegMedicine.DEFAULT_MEDICINE()
            .effect(() -> new MobEffectInstance(ZPMobEffects.zombie_plague.get(), -10), 1.0F)
            .build();

    public static final FoodProperties ZPLAGUE = ZPRegMedicine.DEFAULT_MEDICINE()
            .effect(() -> new MobEffectInstance(ZPMobEffects.zombie_plague.get(), ZPConstants.ZOMBIE_PLAGUE_VIRUS_EFFECT_TIME_TICKS), 1.0F)
            .build();

    public static final FoodProperties ANTIBIOTICS = ZPRegMedicine.DEFAULT_MEDICINE()
            .effect(() -> new MobEffectInstance(MobEffects.POISON, -10), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, -10), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.HUNGER, -10), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, -10), 1.0F)
            .build();

    public static final FoodProperties BANDAGE = ZPRegMedicine.DEFAULT_MEDICINE()
            .effect(() -> new MobEffectInstance(ZPMobEffects.bleeding.get(), -1), 1.0F)
            .build();

    public static final FoodProperties BETTER_VISION = ZPRegMedicine.DEFAULT_MEDICINE()
            .effect(() -> new MobEffectInstance(ZPMobEffects.better_vision.get(), 6000), 1.0F)
            .build();

    public static final FoodProperties HEALING = ZPRegMedicine.DEFAULT_MEDICINE()
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 600), 1.0F)
            .build();

    public static final FoodProperties METH = ZPRegMedicine.DEFAULT_MEDICINE()
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 1800), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 2400), 1.0F)
            .effect(() -> new MobEffectInstance(ZPMobEffects.adrenaline.get(), 3600), 1.0F)
            .build();

    public static final FoodProperties TIRE = ZPRegMedicine.DEFAULT_MEDICINE()
            .effect(() -> new MobEffectInstance(ZPMobEffects.fracture.get(), -1), 1.0F)
            .build();

    private static FoodProperties.Builder DEFAULT_MEDICINE() {
        return (new FoodProperties.Builder()).nutrition(0).saturationMod(0.0F);
    }

    public static void init(ZPItems zpItems, @NotNull ZPRegistry.ZPRegSupplier<Item> regSupplier) {
        zpItems.initInstanceCollecting("medicine");

        ZPItems.adrenaline_syringe = regSupplier.register("adrenaline_syringe", () -> new ZPItemMedicine(new Item.Properties().stacksTo(4), ZPRegMedicine.ADRENALINE, new ZPItemMedicine.ZPMedicineProperties()
                .setMedicineAnim(ZPItemMedicine.MedicineAnim.BLOCK)
                .setCanBeAffectedOnOther(true)
                .setSoundToPlayOnConsume(() -> ZPSounds.syringe.get())
        )).afterCreated((e, utils) -> {
                    ZPUtility.sides().onlyClient(() -> {
                        utils.items().addItemInTab(e, ZPTabs.zp_medicine_tab);
                        utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_FOOD, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.MEDICINE_ITEMS_DIRECTORY);
                    });
        }).end();

        ZPItems.morphine_syringe = regSupplier.register("morphine_syringe", () -> new ZPItemMedicine(new Item.Properties().stacksTo(4), ZPRegMedicine.MORPHINE, new ZPItemMedicine.ZPMedicineProperties()
                .setMedicineAnim(ZPItemMedicine.MedicineAnim.BLOCK)
                .setCanBeAffectedOnOther(true)
                .setSoundToPlayOnConsume(() -> ZPSounds.syringe.get())
        )).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.items().addItemInTab(e, ZPTabs.zp_medicine_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_FOOD, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.MEDICINE_ITEMS_DIRECTORY);
            });
        }).end();

        ZPItems.bandage = regSupplier.register("bandage", () -> new ZPItemMedicine(new Item.Properties().durability(2), ZPRegMedicine.BANDAGE, new ZPItemMedicine.ZPMedicineProperties()
                .setMedicineAnim(ZPItemMedicine.MedicineAnim.BLOCK)
                .setSoundToPlayOnConsume(() -> ZPSounds.bandage.get())
        )).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.items().addItemInTab(e, ZPTabs.zp_medicine_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_FOOD, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.MEDICINE_ITEMS_DIRECTORY);
            });
        }).end();

        ZPItems.aid_kit = regSupplier.register("aid_kit", () -> new ZPItemMedicine(new Item.Properties().durability(2), ZPRegMedicine.AID_KIT, new ZPItemMedicine.ZPMedicineProperties()
                .setMedicineAnim(ZPItemMedicine.MedicineAnim.BLOCK)
                .setSoundToPlayOnConsume(() -> ZPSounds.bandage.get())
        )).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.items().addItemInTab(e, ZPTabs.zp_medicine_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_FOOD, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.MEDICINE_ITEMS_DIRECTORY);
            });
        }).end();

        ZPItems.anti_headache_pill = regSupplier.register("anti_headache_pill", () -> new ZPItemMedicine(new Item.Properties().stacksTo(16), ZPRegMedicine.ANTI_HEADACHE, new ZPItemMedicine.ZPMedicineProperties()
                .setMedicineAnim(ZPItemMedicine.MedicineAnim.EAT)
        )).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.items().addItemInTab(e, ZPTabs.zp_medicine_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_FOOD, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.MEDICINE_ITEMS_DIRECTORY);
            });
        }).end();

        ZPItems.anti_hunger_pill = regSupplier.register("anti_hunger_pill", () -> new ZPItemMedicine(new Item.Properties().stacksTo(16), ZPRegMedicine.ANTI_HUNGER, new ZPItemMedicine.ZPMedicineProperties()
                .setMedicineAnim(ZPItemMedicine.MedicineAnim.EAT)
        )).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.items().addItemInTab(e, ZPTabs.zp_medicine_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_FOOD, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.MEDICINE_ITEMS_DIRECTORY);
            });
        }).end();

        ZPItems.anti_poison_pill = regSupplier.register("anti_poison_pill", () -> new ZPItemMedicine(new Item.Properties().stacksTo(16), ZPRegMedicine.ANTI_POISON, new ZPItemMedicine.ZPMedicineProperties()
                .setMedicineAnim(ZPItemMedicine.MedicineAnim.EAT)
        )).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.items().addItemInTab(e, ZPTabs.zp_medicine_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_FOOD, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.MEDICINE_ITEMS_DIRECTORY);
            });
        }).end();

        ZPItems.zplague_syringe = regSupplier.register("zplague_syringe", () -> new ZPItemMedicine(new Item.Properties().stacksTo(1), ZPRegMedicine.ZPLAGUE, new ZPItemMedicine.ZPMedicineProperties()
                .setMedicineAnim(ZPItemMedicine.MedicineAnim.BLOCK)
                .setCanBeAffectedOnOther(true)
                .setSoundToPlayOnConsume(() -> ZPSounds.syringe.get())
        )).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.items().addItemInTab(e, ZPTabs.zp_medicine_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_FOOD, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.MEDICINE_ITEMS_DIRECTORY);
            });
        }).end();

        ZPItems.anti_zplague_syringe = regSupplier.register("anti_zplague_syringe", () -> new ZPItemMedicine(new Item.Properties().stacksTo(1), ZPRegMedicine.ANTI_ZPLAGUE, new ZPItemMedicine.ZPMedicineProperties()
                .setMedicineAnim(ZPItemMedicine.MedicineAnim.BLOCK)
                .setCanBeAffectedOnOther(true)
                .setSoundToPlayOnConsume(() -> ZPSounds.syringe.get())
        )).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.items().addItemInTab(e, ZPTabs.zp_medicine_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_FOOD, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.MEDICINE_ITEMS_DIRECTORY);
            });
        }).end();

        ZPItems.antibiotics_syringe = regSupplier.register("antibiotics_syringe", () -> new ZPItemMedicine(new Item.Properties().stacksTo(4), ZPRegMedicine.ANTIBIOTICS, new ZPItemMedicine.ZPMedicineProperties()
                .setMedicineAnim(ZPItemMedicine.MedicineAnim.BLOCK)
                .setCanBeAffectedOnOther(true)
                .setSoundToPlayOnConsume(() -> ZPSounds.syringe.get())
        )).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.items().addItemInTab(e, ZPTabs.zp_medicine_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_FOOD, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.MEDICINE_ITEMS_DIRECTORY);
            });
        }).end();

        ZPItems.better_vision_pill = regSupplier.register("better_vision_pill", () -> new ZPItemMedicine(new Item.Properties().stacksTo(16), ZPRegMedicine.BETTER_VISION, new ZPItemMedicine.ZPMedicineProperties()
                .setMedicineAnim(ZPItemMedicine.MedicineAnim.EAT)
        )).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.items().addItemInTab(e, ZPTabs.zp_medicine_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_FOOD, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.MEDICINE_ITEMS_DIRECTORY);
            });
        }).end();

        ZPItems.meth_pill = regSupplier.register("meth_pill", () -> new ZPItemMedicine(new Item.Properties().stacksTo(16), ZPRegMedicine.METH, new ZPItemMedicine.ZPMedicineProperties()
                .setMedicineAnim(ZPItemMedicine.MedicineAnim.EAT)
        )).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.items().addItemInTab(e, ZPTabs.zp_medicine_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_FOOD, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.MEDICINE_ITEMS_DIRECTORY);
            });
        }).end();

        ZPItems.healing_pill = regSupplier.register("healing_pill", () -> new ZPItemMedicine(new Item.Properties().stacksTo(16), ZPRegMedicine.HEALING, new ZPItemMedicine.ZPMedicineProperties()
                .setMedicineAnim(ZPItemMedicine.MedicineAnim.EAT)
        )).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.items().addItemInTab(e, ZPTabs.zp_medicine_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_FOOD, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.MEDICINE_ITEMS_DIRECTORY);
            });
        }).end();

        ZPItems.military_bandage = regSupplier.register("military_bandage", () -> new ZPItemMedicine(new Item.Properties().durability(6), ZPRegMedicine.BANDAGE, new ZPItemMedicine.ZPMedicineProperties()
                .setMedicineAnim(ZPItemMedicine.MedicineAnim.BLOCK)
                .setSoundToPlayOnConsume(() -> ZPSounds.bandage.get())
        )).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.items().addItemInTab(e, ZPTabs.zp_medicine_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_FOOD, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.MEDICINE_ITEMS_DIRECTORY);
            });
        }).end();

        ZPItems.tire = regSupplier.register("tire", () -> new ZPItemMedicine(new Item.Properties().stacksTo(1), ZPRegMedicine.TIRE, new ZPItemMedicine.ZPMedicineProperties()
                .setMedicineAnim(ZPItemMedicine.MedicineAnim.BLOCK)
                .setSoundToPlayOnConsume(() -> ZPSounds.bandage.get())
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
