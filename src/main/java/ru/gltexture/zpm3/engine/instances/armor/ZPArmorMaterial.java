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

package ru.gltexture.zpm3.engine.instances.armor;

import net.minecraft.Util;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class ZPArmorMaterial implements ArmorMaterial {
    private final String name;
    private final int durabilityMultiplier;
    private final EnumMap<ArmorItem.Type, Integer> protectionFunctionForType;
    private final int enchantmentValue;
    private final SoundEvent sound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredient;
    private final ZPArmorProperties zpArmorProperties;

    private static final EnumMap<ArmorItem.Type, Integer> HEALTH_FUNCTION_FOR_TYPE = Util.make(new EnumMap<>(ArmorItem.Type.class), (p_266653_) -> {
        p_266653_.put(ArmorItem.Type.BOOTS, 13);
        p_266653_.put(ArmorItem.Type.LEGGINGS, 15);
        p_266653_.put(ArmorItem.Type.CHESTPLATE, 16);
        p_266653_.put(ArmorItem.Type.HELMET, 11);
    });

    public ZPArmorMaterial(String pName, ZPArmorProperties zpArmorProperties, int pDurabilityMultiplier, EnumMap<ArmorItem.Type, Integer> pProtectionFunctionForType, int pEnchantmentValue, SoundEvent pSound, float pToughness, float pKnockbackResistance, Supplier<Ingredient> pRepairIngredient) {
        this.name = pName;
        this.zpArmorProperties = zpArmorProperties;
        this.durabilityMultiplier = pDurabilityMultiplier;
        this.protectionFunctionForType = pProtectionFunctionForType;
        this.enchantmentValue = pEnchantmentValue;
        this.sound = pSound;
        this.toughness = pToughness;
        this.knockbackResistance = pKnockbackResistance;
        this.repairIngredient = pRepairIngredient;
    }

    public ZPArmorProperties getZpArmorProperties() {
        return this.zpArmorProperties;
    }

    public int getDurabilityForType(ArmorItem.@NotNull Type pType) {
        return HEALTH_FUNCTION_FOR_TYPE.get(pType) * this.durabilityMultiplier;
    }

    public int getDefenseForType(ArmorItem.@NotNull Type pType) {
        return this.protectionFunctionForType.get(pType);
    }

    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    public @NotNull SoundEvent getEquipSound() {
        return this.sound;
    }

    public @NotNull Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    public @NotNull String getName() {
        return this.name;
    }

    public float getToughness() {
        return this.toughness;
    }

    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }

    public String getSerializedName() {
        return this.name;
    }

    public static class ZPArmorProperties {
        private float[] bonusZombieLookRadiusIfOnPlayer;
        private @Nullable Predicate<LivingEntity> bonusZombieLookRadiusPredicate;

        public ZPArmorProperties() {
            this.bonusZombieLookRadiusIfOnPlayer = new float[] {0.0f, 0.0f, 0.0f, 0.0f}; // EACH ARMOR SLOT
            this.bonusZombieLookRadiusPredicate = null;
        }

        public @Nullable Predicate<LivingEntity> getBonusZombieLookRadiusPredicate() {
            return this.bonusZombieLookRadiusPredicate;
        }

        public ZPArmorProperties setBonusZombieLookRadiusPredicate(@Nullable Predicate<LivingEntity> bonusZombieLookRadiusPredicate) {
            this.bonusZombieLookRadiusPredicate = bonusZombieLookRadiusPredicate;
            return this;
        }

        public float[] getReduceZombieLookRadiusIfOnEntity() {
            return this.bonusZombieLookRadiusIfOnPlayer;
        }

        public ZPArmorProperties setBonusZombieLookRadiusIfOnPlayer(float[] bonusZombieLookRadiusIfOnPlayer) {
            this.bonusZombieLookRadiusIfOnPlayer = bonusZombieLookRadiusIfOnPlayer;
            return this;
        }
    }
}
