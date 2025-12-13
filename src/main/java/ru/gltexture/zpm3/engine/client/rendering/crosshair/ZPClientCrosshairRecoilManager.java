package ru.gltexture.zpm3.engine.client.rendering.crosshair;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import ru.gltexture.zpm3.assets.debug.events.ZPFreeCameraEvents;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;

import java.util.Objects;

@OnlyIn(Dist.CLIENT)
public class ZPClientCrosshairRecoilManager {
    private static Vector3f cameraTransform;
    private static Vector3f cameraTransformPrev;

    static {
        ZPClientCrosshairRecoilManager.cameraTransform = new Vector3f(0.0f);
        ZPClientCrosshairRecoilManager.cameraTransformPrev = new Vector3f(0.0f);
    }

    public static float recoilDecaySpeed = 0.3f;

    public static void onClientTick(@NotNull Minecraft minecraft) {
        if (minecraft.player == null || minecraft.isPaused()) {
            return;
        }

        ZPClientCrosshairRecoilManager.cameraTransformPrev.set(ZPClientCrosshairRecoilManager.cameraTransform);
        ZPClientCrosshairRecoilManager.cameraTransform.mul(ZPClientCrosshairRecoilManager.recoilDecaySpeed);
    }

    public static void onRenderTick(double deltaTicks, @NotNull Minecraft minecraft) {
    }

    public static Vector3f getCameraTransform() {
        return ZPClientCrosshairRecoilManager.cameraTransform;
    }

    public static Vector3f getCameraTransformPrev() {
        return ZPClientCrosshairRecoilManager.cameraTransformPrev;
    }

    public static float getRecoilDecaySpeed() {
        return ZPClientCrosshairRecoilManager.recoilDecaySpeed;
    }

    public static float setVerticalRecoil(float recoilPitch) {
        if (ZPFreeCameraEvents.enabled) {
            return 0.0f;
        }
        final int i = ZPRandom.getRandom().nextBoolean() ? 1 : -1;

        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.getCameraEntity() == null || minecraft.player == null || minecraft.isPaused()) {
            return 0.0f;
        }

        final float nPitch = recoilPitch;
        final float nYaw = recoilPitch * 0.2f * i;
        final float nRoll = Math.min(recoilPitch * 0.675f, 8.0f);

        minecraft.getCameraEntity().setXRot(minecraft.getCameraEntity().getXRot() - nPitch);
        minecraft.getCameraEntity().setYRot(minecraft.getCameraEntity().getYRot() - nYaw);

        Objects.requireNonNull(Minecraft.getInstance().player).bob = Math.min(recoilPitch * 0.01f, 0.2f);

        ZPClientCrosshairRecoilManager.cameraTransform.set(nPitch * 2.0f, nYaw * 0.5f, nRoll * -i);
        return nPitch;
    }
}
