package ru.gltexture.zpm3.modules.player.events.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.events.ZPEventClass;
import ru.gltexture.zpm3.modules.net_pack.packets.ZPLyingStateEvent;
import ru.gltexture.zpm3.modules.player.mixins.ext.IZPPlayerMixinExt;

@OnlyIn(Dist.CLIENT)
public class ZPPlayerLyingClientCheckEvent implements ZPEventClass {
    @OnlyIn(Dist.CLIENT) public static boolean isLocalPlayerInLyingAnimationNow;
    @OnlyIn(Dist.CLIENT) public static boolean liePrevTick;
    @OnlyIn(Dist.CLIENT) public static int lieCooldown;

    public ZPPlayerLyingClientCheckEvent() {
    }

    @Override
    public @NotNull ZPSide getSide() {
        return ZPSide.CLIENT;
    }

    @Override
    public @NotNull Mod.EventBusSubscriber.Bus getBus() {
        return Mod.EventBusSubscriber.Bus.FORGE;
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onPlayerSpawn(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof LocalPlayer) {
            ZPPlayerLyingClientCheckEvent.syncLyingState();
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void tick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            if (Minecraft.getInstance().player != null) {
                if (Minecraft.getInstance().screen == null) {
                    ZPPlayerLyingClientCheckEvent.isLocalPlayerInLyingAnimationNow = ZPPlayerLyingClientCheckEvent.lieCheck() && IZPPlayerMixinExt.checkIfPlayerCanLieOnGround(Minecraft.getInstance().player);
                    if (!ZPPlayerLyingClientCheckEvent.liePrevTick && ZPPlayerLyingClientCheckEvent.lieCooldown-- > 0) {
                        ZPPlayerLyingClientCheckEvent.isLocalPlayerInLyingAnimationNow = false;
                    }
                    if (!ZPPlayerLyingClientCheckEvent.liePrevTick && isLocalPlayerInLyingAnimationNow) {
                        ZPPlayerLyingClientCheckEvent.lieCooldown = 20;
                        ZPPlayerLyingClientCheckEvent.syncLyingState();
                    }
                    if (ZPPlayerLyingClientCheckEvent.liePrevTick && !isLocalPlayerInLyingAnimationNow) {
                        ZPPlayerLyingClientCheckEvent.syncLyingState();
                    }
                    ZPPlayerLyingClientCheckEvent.liePrevTick = isLocalPlayerInLyingAnimationNow;
                } else {
                    ZPPlayerLyingClientCheckEvent.liePrevTick = false;
                    ZPPlayerLyingClientCheckEvent.isLocalPlayerInLyingAnimationNow = false;
                    ZPPlayerLyingClientCheckEvent.syncLyingState();
                }
            } else {
                ZPPlayerLyingClientCheckEvent.liePrevTick = false;
                ZPPlayerLyingClientCheckEvent.isLocalPlayerInLyingAnimationNow = false;
            }
        } else {
           // if (ZPPlayerLyingClientCheckEvent.lieNow) {
           //     if (Minecraft.getInstance().player != null) {
           //         Minecraft.getInstance().player.setPose(Pose.SWIMMING);
           //     }
           // }
        }
    }

    public static void syncLyingState() {
        ZombiePlague3.net().sendToServer(new ZPLyingStateEvent(ZPPlayerLyingClientCheckEvent.isLocalPlayerInLyingAnimationNow));
    }

    private static boolean lieCheck() {
        final boolean shift = InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_LEFT_SHIFT);
        final boolean ctrl = InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_LEFT_CONTROL);
        return shift && ctrl || (ZPPlayerLyingClientCheckEvent.isLocalPlayerInLyingAnimationNow && (ctrl || shift));
    }
}
