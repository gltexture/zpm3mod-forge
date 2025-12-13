package ru.gltexture.zpm3.assets.debug.events;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.Input;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.events.ZPEventClass;

public class ZPFreeCameraEvents implements ZPEventClass {
    public static boolean enabled = false;
    public static Vec3 freecamPos = new Vec3(0, 70, 0);
    public static float yaw = 0f;
    public static float pitch = 0f;

    private static final float rotateSpeed = 2f;
    private static final float moveSpeed = 0.4f;

    public ZPFreeCameraEvents() {
    }

    @Override
    public @NotNull ZPSide getSide() {
        return ZPSide.CLIENT;
    }

    @Override
    public @NotNull Mod.EventBusSubscriber.Bus getBus() {
        return Mod.EventBusSubscriber.Bus.FORGE;
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (ZombiePlague3.isDevEnvironment() && event.getKey() == GLFW.GLFW_KEY_F6 && event.getAction() == GLFW.GLFW_PRESS) {
            ZPFreeCameraEvents.enabled = !ZPFreeCameraEvents.enabled;
            Minecraft mc = Minecraft.getInstance();
            if (enabled && mc.player != null) {
                ZPFreeCameraEvents.freecamPos = mc.player.position();
                ZPFreeCameraEvents.yaw = mc.player.getYRot();
                ZPFreeCameraEvents.pitch = mc.player.getXRot();
            }
        }
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (!enabled || event.phase != TickEvent.Phase.END) {
            return;
        }

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) {
            return;
        }

        long window = mc.getWindow().getWindow();
        if (InputConstants.isKeyDown(window, GLFW.GLFW_KEY_LEFT)) {
            yaw -= ZPFreeCameraEvents.rotateSpeed;
        }
        if (InputConstants.isKeyDown(window, GLFW.GLFW_KEY_RIGHT)) {
            yaw += ZPFreeCameraEvents.rotateSpeed;
        }
        if (InputConstants.isKeyDown(window, GLFW.GLFW_KEY_UP)) {
            pitch -= ZPFreeCameraEvents.rotateSpeed;
        }
        if (InputConstants.isKeyDown(window, GLFW.GLFW_KEY_DOWN)) {
            pitch += ZPFreeCameraEvents.rotateSpeed;
        }

        if (pitch > 90) {
            pitch = 90;
        }
        if (pitch < -90) {
            pitch = -90;
        }

        Vec3 forward = Vec3.directionFromRotation(pitch, yaw).normalize();
        Vec3 left = Vec3.directionFromRotation(0, yaw - 90).normalize();

        if (mc.options.keyUp.isDown()) {
            ZPFreeCameraEvents.freecamPos = ZPFreeCameraEvents.freecamPos.add(forward.scale(moveSpeed));
        }
        if (mc.options.keyDown.isDown()) {
            ZPFreeCameraEvents.freecamPos = ZPFreeCameraEvents.freecamPos.add(forward.scale(-moveSpeed));
        }
        if (mc.options.keyLeft.isDown()) {
            ZPFreeCameraEvents.freecamPos = ZPFreeCameraEvents.freecamPos.add(left.scale(moveSpeed));
        }
        if (mc.options.keyRight.isDown()) {
            ZPFreeCameraEvents.freecamPos = ZPFreeCameraEvents.freecamPos.add(left.scale(-moveSpeed));
        }
        if (mc.options.keyJump.isDown()) {
            ZPFreeCameraEvents.freecamPos = ZPFreeCameraEvents.freecamPos.add(0, moveSpeed, 0);
        }
        if (mc.options.keyShift.isDown()) {
            ZPFreeCameraEvents.freecamPos = ZPFreeCameraEvents.freecamPos.add(0, -moveSpeed, 0);
        }
    }
}