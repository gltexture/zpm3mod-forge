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

package ru.gltexture.zpm3.modules.entity.util;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.engine.core.config.builtin.ZPCombatConfig;
import ru.gltexture.zpm3.engine.core.config.builtin.ZPEntityConfig;
import ru.gltexture.zpm3.engine.core.config.builtin.ZPZombieConfig;
import ru.gltexture.zpm3.engine.zones.ZPZoneChecks;
import ru.gltexture.zpm3.modules.armor.utils.ZPArmorUtil;
import ru.gltexture.zpm3.modules.blocks.init.ZPBlocks;
import ru.gltexture.zpm3.modules.common.init.ZPTags;
import ru.gltexture.zpm3.modules.common.utils.ZPCommonServerUtils;
import ru.gltexture.zpm3.modules.entity.instances.mobs.zombies.ZPAbstractZombie;
import ru.gltexture.zpm3.modules.mob_effects.init.ZPMobEffects;
import ru.gltexture.zpm3.modules.mob_effects.utils.ZPEffectUtils;

import java.util.Objects;

public class ZPEntityUtil {
    public static boolean isUsingShield(@NotNull LivingEntity entity) {
        return entity.isUsingItem() && entity.getUseItem().getItem() instanceof ShieldItem;
    }
    public static @Nullable ItemStack getOxygenStackInHand(LivingEntity entity) {
        return entity.getMainHandItem().is(ZPTags.I_AQUALUNG_O2_ITEM) ? entity.getMainHandItem() : entity.getOffhandItem().is(ZPTags.I_AQUALUNG_O2_ITEM) ? entity.getOffhandItem() : null;
    }

    public static boolean hasOxygenInHands(LivingEntity entity) {
        return entity.getMainHandItem().is(ZPTags.I_AQUALUNG_O2_ITEM) || entity.getOffhandItem().is(ZPTags.I_AQUALUNG_O2_ITEM);
    }

    public static float getEntityPlaguePercentage(@NotNull LivingEntity entity) {
        if (!ZPEffectUtils.isZombiePlagued(entity)) {
            return -1.0f;
        }
        final int duration = Objects.requireNonNull(entity.getEffect(ZPMobEffects.zombie_plague.get())).getDuration();
        final float percentLeft = duration / (float) ZPZombieConfig.ZOMBIE_PLAGUE_VIRUS_EFFECT_TIME_TICKS.getVar();
        return  1.0f - percentLeft;
    }

    // < 0 = NO
    public static int getEntityAcidAffectionTickRate(@NotNull final Entity entity) {
        final int getEntityAcidIncMultiplier = ZPEntityUtil.getEntityAcidIncMultiplier(entity);
        final int acidAffectionSlowdownTicks = ((entity instanceof LivingEntity livingEntity) ? ZPArmorUtil.getAcidIncTickSlowdown(livingEntity, getEntityAcidIncMultiplier) : 0);
        if (acidAffectionSlowdownTicks < 0 || getEntityAcidIncMultiplier <= 0) {
            return -1;
        }
        return (ZPEntityConfig.ADD_ACID_FACTOR_PER_TICK.getVar() / getEntityAcidIncMultiplier) + acidAffectionSlowdownTicks;
    }

    // < 0 = NO
    public static int getEntityToxicAffectionTickRate(@NotNull final Entity entity) {
        if (entity instanceof LivingEntity livingEntity && ZPEffectUtils.isImmune(livingEntity)) {
            return -1;
        }
        final int getEntityToxicIncMultiplier = ZPEntityUtil.getEntityToxicIncMultiplier(entity);
        final int toxicAffectionSlowdownTicks = ((entity instanceof LivingEntity livingEntity) ? ZPArmorUtil.getAcidIncTickSlowdown(livingEntity, getEntityToxicIncMultiplier) : 0);
        if (toxicAffectionSlowdownTicks < 0 || getEntityToxicIncMultiplier <= 0) {
            return -1;
        }
        return (ZPEntityConfig.ADD_TOXIC_FACTOR_PER_TICK.getVar() / getEntityToxicIncMultiplier) + toxicAffectionSlowdownTicks;
    }

    // < 0 = NO
    public static int getEntityRadAffectionTickRate(@NotNull final LivingEntity entity) {
        if (entity instanceof ZPAbstractZombie) {
            return -1;
        }
        final int getEntityRadIncMultiplier = ZPEntityUtil.getLivingEntityRadiationIncMultiplier(entity);
        final int radAffectionSlowdownTicks = ZPArmorUtil.getRadiationIncTickSlowdown(entity, getEntityRadIncMultiplier);
        if (radAffectionSlowdownTicks < 0 || getEntityRadIncMultiplier <= 0) {
            return -1;
        }
        return (ZPEntityConfig.ADD_RAD_PER_TICK.getVar() / getEntityRadIncMultiplier) + radAffectionSlowdownTicks;
    }

    //0 = N0
    public static int getEntityToxicIncMultiplier(final Entity entity) {
        if (ZPZoneChecks.INSTANCE.isNoToxicAffection(entity.level(), entity.blockPosition())) {
            return 0;
        }
        if (ZPEntityUtil.isCollidingWithFluid(entity, ZPTags.F_TOXIC_PROPERTIES)) {
            return 2;
        }
        if (ZPZoneChecks.INSTANCE.isInToxicCloud(entity.level(), entity)) {
            return 1;
        }
        return 0;
    }

    //0 = N0
    public static int getEntityAcidIncMultiplier(final Entity entity) {
        if (ZPZoneChecks.INSTANCE.isNoAcidAffection(entity.level(), entity.blockPosition())) {
            return 0;
        }
        if (ZPEntityUtil.isCollidingWithFluid(entity, ZPTags.F_ACID_PROPERTIES)) {
            return 2;
        }
        if (ZPZoneChecks.INSTANCE.isInAcidCloud(entity.level(), entity)) {
            return 1;
        }
        return 0;
    }

    // 0 = NO; 1 = LVL1; 2 = LVL2
    public static int getLivingEntityRadiationIncMultiplier(@NotNull LivingEntity livingEntity) {
        if (ZPEffectUtils.isRadiationProtected(livingEntity)) {
            return 0;
        }
        if (ZPZoneChecks.INSTANCE.isNoRadiationAffection(livingEntity.level(), livingEntity.blockPosition())) {
            return 0;
        }
        if (ZPZoneChecks.INSTANCE.isInRadLVL2(livingEntity.level(), livingEntity)) {
            return 2;
        }
        if (ZPZoneChecks.INSTANCE.isInRadLVL1(livingEntity.level(), livingEntity)) {
            return 1;
        }
        return 0;
    }

    public static void applyRadiationEffects(LivingEntity entity, int rad) {
        if (rad >= 10) {
            entity.addEffect(new MobEffectInstance(MobEffects.HUNGER, 600, 0, true, true));
            entity.removeEffect(ZPMobEffects.immune.get());
        }

        if (rad >= 20) {
            entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 600, 0, true, true));
        }

        if (rad >= 40) {
            entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 1200, 1, true, true));
        }

        if (!ZPEffectUtils.isZombiePlagued(entity) && rad >= 50) {
            entity.addEffect(new MobEffectInstance(ZPMobEffects.zombie_plague.get(), ZPZombieConfig.ZOMBIE_PLAGUE_VIRUS_EFFECT_TIME_TICKS.getVar(), 0, false, false));
        }

        if (rad >= 60) {
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 1200, 1, true, true));
        }

        if (rad >= 80) {
            entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 200, 0, true, true));
            entity.addEffect(new MobEffectInstance(MobEffects.POISON, 1200, 1, true, true));
            entity.addEffect(new MobEffectInstance(MobEffects.HUNGER, 3600, 2, true, true));
        }

        if (rad >= 95) {
            entity.addEffect(new MobEffectInstance(MobEffects.WITHER, 400, 1, true, true));
        }

        if (rad >= 100) {
            entity.hurt(entity.damageSources().wither(), 6.0F);
        }
    }

    public static void applyIntoxicationEffects(LivingEntity entity, int level) {
        if (level > 260) {
            entity.addEffect(new MobEffectInstance(MobEffects.HUNGER, 1200, 0, false, true));
            entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 1200, 0, false, true));
        }

        if (level > 360) {
            entity.addEffect(new MobEffectInstance(MobEffects.HUNGER, 1200, 1, false, true));
        }
    }

    public static void applySeasicknessEffectsOnPlayer(Player entity, int level) {
        if (level >= 240) {
            entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 600, 0, true, true));
            entity.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 600, 0, true, true));
        }

        if (level >= 300) {
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 600, 0, true, true));
            entity.addEffect(new MobEffectInstance(MobEffects.HUNGER, 600, 0, true, true));
        }

        if (level >= 340) {
            entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 600, 0, true, true));
        }
    }

    public static void damageEntityAndPossiblyEquipment(Entity entity) {
        if (entity.tickCount % ZPCombatConfig.ACID_DAMAGE_TICK_RATE.getVar() != 0) {
            return;
        }

        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.hurt(livingEntity.damageSources().generic(), livingEntity instanceof ZPAbstractZombie ? 6.0f : 1.0f);

            if (livingEntity instanceof Player player) {
                for (ItemStack stack : player.getInventory().items) {
                    if (stack.isDamageableItem()) {
                        stack.hurtAndBreak(ZPCombatConfig.ACID_INVENTORY_DAMAGE.getVar(), player, e -> {
                            e.broadcastBreakEvent(EquipmentSlot.MAINHAND);
                        });
                    }
                }

                for (int i = 0; i < player.getInventory().armor.size(); i++) {
                    ItemStack stack = player.getInventory().armor.get(i);
                    if (stack.isDamageableItem()) {
                        EquipmentSlot finalSlot = ZPCommonServerUtils.getEquipmentSlot(i);
                        stack.hurtAndBreak(ZPCombatConfig.ACID_INVENTORY_DAMAGE.getVar(), player, e -> {
                            e.broadcastBreakEvent(finalSlot);
                        });
                    }
                }
            } else {
                for (ItemStack stack : livingEntity.getHandSlots()) {
                    if (stack.isDamageableItem()) {
                        stack.hurtAndBreak(ZPCombatConfig.ACID_INVENTORY_DAMAGE.getVar(), livingEntity, e -> {
                            e.broadcastBreakEvent(EquipmentSlot.MAINHAND);
                        });
                    }
                }
            }
        }
    }

    public static int consumeItemFromInventory(@NotNull Inventory inventory, @NotNull Item item, int amount) {
        int toRemove = amount;
        int removed = 0;

        for (int i = 0; i < inventory.items.size(); i++) {
            ItemStack stack = inventory.items.get(i);
            if (stack.getItem().equals(item)) {
                int stackSize = Math.min(stack.getCount(), toRemove);
                inventory.removeItem(i, stackSize);
                removed += stackSize;
                toRemove -= stackSize;
                if (toRemove <= 0) {
                    break;
                }
            }
        }

        return removed;
    }

    public static boolean isCollidingWithFluid(@NotNull Entity entity, @NotNull TagKey<Fluid> targetFluid) {
        AABB box = entity.getBoundingBox();
        final double s = 1.0e-7;
        BlockPos min = BlockPos.containing(box.minX, box.minY, box.minZ);
        BlockPos max = BlockPos.containing(box.maxX - s, box.maxY - s, box.maxZ - s);
        for (BlockPos pos : BlockPos.betweenClosed(min, max)) {
            FluidState state = entity.level().getFluidState(pos);
            if (!state.isEmpty() && state.is(targetFluid)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isCollidingWithBlock(@NotNull Entity entity, @NotNull Block targetBlock) {
        AABB box = entity.getBoundingBox();

        final float s = 1.0e-7f;

        BlockPos min = BlockPos.containing(box.minX, box.minY, box.minZ);
        BlockPos max = BlockPos.containing(box.maxX - s, box.maxY - s, box.maxZ - s);

        for (BlockPos pos : BlockPos.betweenClosed(min, max)) {
            BlockState state = entity.level().getBlockState(pos);

            if (state.is(targetBlock)) {
                return true;
            }
        }

        return false;
    }
}
