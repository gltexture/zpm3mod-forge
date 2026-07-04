package ru.gltexture.zpm3.modules.armor.instances.armor;

import net.minecraft.Util;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.instances.armor.ZPArmorMaterial;
import ru.gltexture.zpm3.modules.common.init.ZPTags;

import java.util.EnumMap;
import java.util.function.Predicate;

public abstract class ZPArmorMaterialsList {
    public static final float CAM_REDUCTION_ZM_LOOK_HELMET = 4.0f;
    public static final float CAM_REDUCTION_ZM_LOOK_CHESTPLATE = 6.0f;
    public static final float CAM_REDUCTION_ZM_LOOK_LEGGINGS = 6.0f;
    public static final float CAM_REDUCTION_ZM_LOOK_BOOTS = 4.0f;

    public static final Predicate<LivingEntity> FOREST_TEST = (livingEntity -> livingEntity.level().getBiome(livingEntity.blockPosition()).is(ZPTags.FOREST_BIOMES));
    public static final Predicate<LivingEntity> WINTER_TEST = (livingEntity -> livingEntity.level().getBiome(livingEntity.blockPosition()).is(ZPTags.WINTER_BIOMES));
    public static final Predicate<LivingEntity> SAND_TEST = (livingEntity -> livingEntity.level().getBiome(livingEntity.blockPosition()).is(ZPTags.SAND_BIOMES));

    public static final ZPArmorMaterial NIGHT_VIS = new ZPArmorMaterial(ZombiePlague3.MOD_ID + ":night_vision_goggles",
            new ZPArmorMaterial.ZPArmorProperties(),
            15, Util.make(new EnumMap<>(ArmorItem.Type.class), (e) -> {
        e.put(ArmorItem.Type.BOOTS, 2);
        e.put(ArmorItem.Type.LEGGINGS, 5);
        e.put(ArmorItem.Type.CHESTPLATE, 6);
        e.put(ArmorItem.Type.HELMET, 2);
    }), 9, SoundEvents.ARMOR_EQUIP_DIAMOND, 0.0F, 0.0F, () -> null);

    public static final ZPArmorMaterial FOREST_CAM = new ZPArmorMaterial(ZombiePlague3.MOD_ID + ":forest_cam",
            new ZPArmorMaterial.ZPArmorProperties()
                    .setBonusZombieLookRadiusIfOnPlayer(new float[] {CAM_REDUCTION_ZM_LOOK_HELMET, CAM_REDUCTION_ZM_LOOK_CHESTPLATE, CAM_REDUCTION_ZM_LOOK_LEGGINGS, CAM_REDUCTION_ZM_LOOK_BOOTS})
                    .setBonusZombieLookRadiusPredicate(FOREST_TEST),
            15, Util.make(new EnumMap<>(ArmorItem.Type.class), (e) -> {
        e.put(ArmorItem.Type.BOOTS, 1);
        e.put(ArmorItem.Type.LEGGINGS, 2);
        e.put(ArmorItem.Type.CHESTPLATE, 3);
        e.put(ArmorItem.Type.HELMET, 1);
    }), 9, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, () -> null);

    public static final ZPArmorMaterial WINTER_CAM = new ZPArmorMaterial(ZombiePlague3.MOD_ID + ":winter_cam",
            new ZPArmorMaterial.ZPArmorProperties()
                    .setBonusZombieLookRadiusIfOnPlayer(new float[] {CAM_REDUCTION_ZM_LOOK_HELMET, CAM_REDUCTION_ZM_LOOK_CHESTPLATE, CAM_REDUCTION_ZM_LOOK_LEGGINGS, CAM_REDUCTION_ZM_LOOK_BOOTS})
                    .setBonusZombieLookRadiusPredicate(WINTER_TEST),
            15, Util.make(new EnumMap<>(ArmorItem.Type.class), (e) -> {
        e.put(ArmorItem.Type.BOOTS, 1);
        e.put(ArmorItem.Type.LEGGINGS, 2);
        e.put(ArmorItem.Type.CHESTPLATE, 3);
        e.put(ArmorItem.Type.HELMET, 1);
    }), 9, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, () -> null);

    public static final ZPArmorMaterial SAND_CAM = new ZPArmorMaterial(ZombiePlague3.MOD_ID + ":sand_cam",
            new ZPArmorMaterial.ZPArmorProperties()
                    .setBonusZombieLookRadiusIfOnPlayer(new float[] {CAM_REDUCTION_ZM_LOOK_HELMET, CAM_REDUCTION_ZM_LOOK_CHESTPLATE, CAM_REDUCTION_ZM_LOOK_LEGGINGS, CAM_REDUCTION_ZM_LOOK_BOOTS})
                    .setBonusZombieLookRadiusPredicate(SAND_TEST),
            15, Util.make(new EnumMap<>(ArmorItem.Type.class), (e) -> {
        e.put(ArmorItem.Type.BOOTS, 1);
        e.put(ArmorItem.Type.LEGGINGS, 2);
        e.put(ArmorItem.Type.CHESTPLATE, 3);
        e.put(ArmorItem.Type.HELMET, 1);
    }), 9, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, () -> null);
}
