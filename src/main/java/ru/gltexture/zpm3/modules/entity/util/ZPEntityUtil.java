package ru.gltexture.zpm3.modules.entity.util;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.config.builtin.ZPCombatConfig;
import ru.gltexture.zpm3.engine.service.ZPUtility;
import ru.gltexture.zpm3.engine.zones.ZPZoneChecks;
import ru.gltexture.zpm3.modules.blocks.init.ZPBlocks;
import ru.gltexture.zpm3.modules.common.utils.ZPCommonServerUtils;
import ru.gltexture.zpm3.modules.entity.instances.mobs.zombies.ZPAbstractZombie;

public class ZPEntityUtil {
    //0 = N0
    public static int getEntityToxicIncMultiplier(final Entity entity) {
        if (ZPZoneChecks.INSTANCE.isNoToxicAffection(entity.level(), entity.getOnPos())) {
            return 0;
        }
        if (ZPUtility.entity().isCollidingWithBlock(entity, ZPBlocks.toxic_block.get())) {
            return 2;
        }
        if (ZPZoneChecks.INSTANCE.isInToxicCloud(entity.level(), entity)) {
            return 1;
        }
        return 0;
    }

    //0 = N0
    public static int getEntityAcidIncMultiplier(final Entity entity) {
        if (ZPZoneChecks.INSTANCE.isNoAcidAffection(entity.level(), entity.getOnPos())) {
            return 0;
        }
        if (ZPUtility.entity().isCollidingWithBlock(entity, ZPBlocks.acid_block.get())) {
            return 2;
        }
        if (ZPZoneChecks.INSTANCE.isInAcidCloud(entity.level(), entity)) {
            return 1;
        }
        return 0;
    }

    // 0 = NO; 1 = LVL1; 2 = LVL2
    public static int getLivingEntityRadiationIncMultiplier(@NotNull LivingEntity livingEntity) {
        if (ZPZoneChecks.INSTANCE.isInRadLVL2(livingEntity.level(), livingEntity)) {
            return 2;
        }
        if (ZPZoneChecks.INSTANCE.isInRadLVL1(livingEntity.level(), livingEntity)) {
            return 1;
        }
        return 0;
    }

    public static void applyRadiationEffects(LivingEntity entity, int rad) {
        if (rad <= 0) {
            entity.removeEffect(MobEffects.WITHER);
            entity.removeEffect(MobEffects.POISON);
            return;
        }
        if (rad >= 10) {
            entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0, true, true));
        }

        if (rad >= 20) {
            entity.addEffect(new MobEffectInstance(MobEffects.HUNGER, 600, 0, true, true));
        }

        if (rad >= 40) {
            entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 1200, 1, true, true));
        }

        if (rad >= 60) {
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 1200, 1, true, true));
        }

        if (rad >= 80) {
            entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 200, 0, true, true));
            entity.addEffect(new MobEffectInstance(MobEffects.POISON, 1200, 1, true, true));
        }

        if (rad >= 95) {
            entity.addEffect(new MobEffectInstance(MobEffects.WITHER, 400, 1, true, true));
        }

        if (rad >= 100) {
            entity.hurt(entity.damageSources().wither(), 6.0F);
        }
    }

    public static void damageEntityAndPossiblyEquipment(Entity entity) {
        if (entity.tickCount % ZPCombatConfig.ACID_DAMAGE_TICK_RATE.getVar() != 0) {
            return;
        }

        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.hurt(livingEntity.damageSources().generic(), livingEntity instanceof ZPAbstractZombie ? 8.0f : 1.25f);

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
}
