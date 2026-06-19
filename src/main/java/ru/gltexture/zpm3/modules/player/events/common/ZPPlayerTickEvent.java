package ru.gltexture.zpm3.modules.player.events.common;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.modules.guns.mixins.client.ZPHumanoidArmTransformations;
import ru.gltexture.zpm3.modules.player.events.client.ZPPlayerLyingClientCheckEvent;
import ru.gltexture.zpm3.modules.player.misc.ZPDefaultItemsHandReach;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.events.ZPEventClass;

import java.util.UUID;

public class ZPPlayerTickEvent implements ZPEventClass {
    public ZPPlayerTickEvent() {
    }

    private static final UUID REACH_BONUS_UUID = UUID.fromString("e3d6a3c0-4b73-4e72-9ad1-1c1c0a9c1111");

    @SubscribeEvent
    public static void exec(TickEvent.@NotNull PlayerTickEvent event) {
        Player player = event.player;
        if (event.phase == TickEvent.Phase.START) {
            if (!event.player.level().isClientSide()) {
                {
                    AttributeInstance attr = player.getAttribute(ForgeMod.ENTITY_REACH.get());
                    if (attr == null) {
                        return;
                    }
                    ItemStack stack = player.getMainHandItem();
                    float bonus = ZPDefaultItemsHandReach.get(stack.getItem());
                    AttributeModifier old = attr.getModifier(ZPPlayerTickEvent.REACH_BONUS_UUID);
                    if (old != null) {
                        attr.removeModifier(old);
                    }
                    if (bonus == 0.0f) {
                        return;
                    }
                    AttributeModifier mod = new AttributeModifier(ZPPlayerTickEvent.REACH_BONUS_UUID, "zp_reach_bonus", bonus, AttributeModifier.Operation.ADDITION);
                    attr.addPermanentModifier(mod);
                }
            }
        } else {
            final boolean swimAnim = player.getPose() == Pose.SWIMMING;
            final float bodyOffset = ZPPlayerTickEvent.getBodyRotationOffset(player);
            if (!event.player.level().isClientSide()) {
                if (player.getPose() == Pose.SWIMMING) {
                    ZPPlayerTickEvent.tickHeadTurn(player, player.getYRot());
                } else {
                    if (bodyOffset != 0.0f) {
                        ZPPlayerTickEvent.tickHeadTurn(player, (player.getYRot() + bodyOffset));
                    }
                }
            } else {
                if (swimAnim || ZPPlayerLyingClientCheckEvent.isLocalPlayerInLyingAnimationNow) {
                    ZPPlayerTickEvent.tickHeadTurn(player, player.getYRot());
                } else {
                    if (bodyOffset != 0.0f) {
                        ZPPlayerTickEvent.tickHeadTurn(player, (player.getYRot() + bodyOffset));
                    }
                }
            }
        }
    }

    public static float getBodyRotationOffset(Player player) {
        ItemStack mainHand = player.getMainHandItem();
        ItemStack offHand = player.getOffhandItem();
        boolean gunRight = ZPHumanoidArmTransformations.isGun(mainHand);
        boolean gunLeft = ZPHumanoidArmTransformations.isGun(offHand);
        boolean rifleRight = ZPHumanoidArmTransformations.isRifleType(mainHand);
        boolean rifleLeft = ZPHumanoidArmTransformations.isRifleType(offHand);
        if (gunRight && gunLeft) {
            return 0.0f;
        }
        if (rifleRight) {
            return 45.0f;
        }
        if (rifleLeft) {
            return -45.0f;
        }
        if (gunRight) {
            return -45.0f;
        }
        if (gunLeft) {
            return 45.0f;
        }
        return 0.0f;
    }

    private static void tickHeadTurn(LivingEntity livingEntity, float pYRot) {
        float f = Mth.wrapDegrees(pYRot - livingEntity.yBodyRot);
        livingEntity.yBodyRot += f * 0.3F;
        float f1 = Mth.wrapDegrees(livingEntity.getYRot() - livingEntity.yBodyRot);
        if (Math.abs(f1) > 50.0F) {
            livingEntity.yBodyRot += f1 - (float)(Mth.sign(f1) * 50);
        }
    }

    @Override
    public @NotNull ZPSide getSide() {
        return ZPSide.COMMON;
    }

    @Override
    public @NotNull Mod.EventBusSubscriber.Bus getBus() {
        return Mod.EventBusSubscriber.Bus.FORGE;
    }
}
