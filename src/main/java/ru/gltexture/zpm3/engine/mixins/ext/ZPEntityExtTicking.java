package ru.gltexture.zpm3.engine.mixins.ext;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.commands.zones.ZPZoneChecks;
import ru.gltexture.zpm3.assets.common.global.ZPConstants;
import ru.gltexture.zpm3.assets.common.utils.ZPCommonServerUtils;
import ru.gltexture.zpm3.assets.entity.instances.mobs.zombies.ZPAbstractZombie;
import ru.gltexture.zpm3.engine.client.utils.ClientRenderFunctions;

public abstract class ZPEntityExtTicking {
    public static void serverEntityTickPre(@NotNull Entity entity, @NotNull IZPEntityExt izpEntityExt) {
        if (entity instanceof ItemEntity itemEntity) {
            if (izpEntityExt.getAcidLevel() > 120) {
                itemEntity.kill();
            }
        }
        if (!ZPZoneChecks.INSTANCE.isNoAcidAffection((ServerLevel) entity.level(), entity.getOnPos())) {
            if (izpEntityExt.touchesAcidBlock() && entity.tickCount % 2 == 0) {
                izpEntityExt.addAcidLevel(1);
            }
            if (izpEntityExt.getAcidLevel() > 3000) {
                if (entity instanceof LivingEntity livingEntity && izpEntityExt.touchesAcidBlock()) {
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.POISON, 60));
                }
            }
            if (izpEntityExt.getAcidLevel() > 0) {
                ZPEntityExtTicking.damageEveryTick(entity);
            }
        }
    }

    public static void serverEntityTickPost(@NotNull Entity entity, @NotNull IZPEntityExt izpEntityExt) {
        if (!izpEntityExt.touchesAcidBlock()) {
            if (izpEntityExt.getAcidLevel() > 0) {
                izpEntityExt.addAcidLevel(-1);
            }
        }
        if (!izpEntityExt.touchesToxicBlock()) {
            if (izpEntityExt.getIntoxicationLevel() > 0) {
                izpEntityExt.addIntoxicationLevel(-1);
            }
        }
    }

    //************************************************************************************************


    public static void clientEntityTickPre(@NotNull Entity entity, @NotNull IZPEntityExt izpEntityExt) {
        if (izpEntityExt.getAcidLevel() > 0) {
            ClientRenderFunctions.addAcidParticles(izpEntityExt.getAcidLevel(), entity);
            if (entity.tickCount % 3 == 0) {
                entity.level().playLocalSound(entity.getOnPos(), SoundEvents.FIRE_EXTINGUISH, SoundSource.MASTER, 0.375f, 1.15f, false);
            }
        }
    }

    public static void clientEntityTickPost(@NotNull Entity entity, @NotNull IZPEntityExt izpEntityExt) {

    }


    //************************************************************************************************


    private static void damageEveryTick(Entity entity) {
        if (entity.tickCount % ZPConstants.ACID_DAMAGE_TICK_RATE != 0) {
            return;
        }

        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.hurt(livingEntity.damageSources().generic(), livingEntity instanceof ZPAbstractZombie ? 8.0f : 3.0f);

            for (ItemStack stack : livingEntity.getHandSlots()) {
                if (stack.isDamageableItem()) {
                    stack.hurtAndBreak(1, livingEntity, e -> {
                        e.broadcastBreakEvent(EquipmentSlot.MAINHAND);
                    });
                }
            }

            if (livingEntity instanceof Player player) {
                for (ItemStack stack : player.getInventory().items) {
                    if (stack.isDamageableItem()) {
                        stack.hurtAndBreak(1, player, e -> {
                            e.broadcastBreakEvent(EquipmentSlot.MAINHAND);
                        });
                    }
                }

                for (int i = 0; i < player.getInventory().armor.size(); i++) {
                    ItemStack stack = player.getInventory().armor.get(i);
                    if (stack.isDamageableItem()) {
                        EquipmentSlot finalSlot = ZPCommonServerUtils.getEquipmentSlot(i);
                        stack.hurtAndBreak(1, player, e -> {
                            e.broadcastBreakEvent(finalSlot);
                        });
                    }
                }
            }
        }
    }
}
