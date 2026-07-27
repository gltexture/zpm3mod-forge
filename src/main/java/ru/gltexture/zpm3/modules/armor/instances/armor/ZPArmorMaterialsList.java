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
    public static final float CAM_REDUCTION_ZM_LOOK_HELMET = 6.0f;
    public static final float CAM_REDUCTION_ZM_LOOK_CHESTPLATE = 12.0f;
    public static final float CAM_REDUCTION_ZM_LOOK_LEGGINGS = 12.0f;
    public static final float CAM_REDUCTION_ZM_LOOK_BOOTS = 6.0f;

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

    public static final ZPArmorMaterial ACID_COSTUME = new ZPArmorMaterial(ZombiePlague3.MOD_ID + ":acid_costume",
            new ZPArmorMaterial.ZPArmorProperties(),
            17, Util.make(new EnumMap<>(ArmorItem.Type.class), (e) -> {
        e.put(ArmorItem.Type.BOOTS, 2);
        e.put(ArmorItem.Type.LEGGINGS, 2);
        e.put(ArmorItem.Type.CHESTPLATE, 3);
        e.put(ArmorItem.Type.HELMET, 2);
    }), 9, SoundEvents.ARMOR_EQUIP_ELYTRA, 0.0F, 0.0F, () -> null);

    public static final ZPArmorMaterial RADIATION_COSTUME = new ZPArmorMaterial(ZombiePlague3.MOD_ID + ":radiation_costume",
            new ZPArmorMaterial.ZPArmorProperties(),
            17, Util.make(new EnumMap<>(ArmorItem.Type.class), (e) -> {
        e.put(ArmorItem.Type.BOOTS, 2);
        e.put(ArmorItem.Type.LEGGINGS, 2);
        e.put(ArmorItem.Type.CHESTPLATE, 3);
        e.put(ArmorItem.Type.HELMET, 2);
    }), 9, SoundEvents.ARMOR_EQUIP_ELYTRA, 0.0F, 0.0F, () -> null);

    public static final ZPArmorMaterial AQUALUNG_COSTUME = new ZPArmorMaterial(ZombiePlague3.MOD_ID + ":aqualung_costume",
            new ZPArmorMaterial.ZPArmorProperties(),
            14, Util.make(new EnumMap<>(ArmorItem.Type.class), (e) -> {
        e.put(ArmorItem.Type.BOOTS, 2);
        e.put(ArmorItem.Type.LEGGINGS, 2);
        e.put(ArmorItem.Type.CHESTPLATE, 3);
        e.put(ArmorItem.Type.HELMET, 2);
    }), 9, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, () -> null);
}
