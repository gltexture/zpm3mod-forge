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

package ru.gltexture.zpm3.engine.client.rendering.crosshair;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import ru.gltexture.zpm3.modules.debug.events.ZPFreeCameraEvents;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;

import java.util.Objects;

@OnlyIn(Dist.CLIENT)
public class ZPClientCrosshairRecoilManager {
    private static final float DAMPING_MAX = 0.25f;

    private static Vector3f cameraTranslate;
    private static Vector3f cameraTranslatePrev;
    private static float recoilDamping;
    private static int dampCooldown;

    static {
        ZPClientCrosshairRecoilManager.cameraTranslate = new Vector3f(0.0f);
        ZPClientCrosshairRecoilManager.cameraTranslatePrev = new Vector3f(0.0f);
        ZPClientCrosshairRecoilManager.recoilDamping = 0.0f;
    }

    public static float recoilDecaySpeed = 0.3f;

    public static void onClientTick(@NotNull Minecraft minecraft) {
        if (minecraft.player == null || minecraft.isPaused()) {
            return;
        }

        ZPClientCrosshairRecoilManager.cameraTranslatePrev.set(ZPClientCrosshairRecoilManager.cameraTranslate);
        ZPClientCrosshairRecoilManager.cameraTranslate.mul(ZPClientCrosshairRecoilManager.recoilDecaySpeed);

        if (ZPClientCrosshairRecoilManager.dampCooldown-- <= 0) {
            ZPClientCrosshairRecoilManager.recoilDamping = Math.max(ZPClientCrosshairRecoilManager.recoilDamping - 0.1f, 0.0f);
        }
    }

    public static void onRenderTick(double deltaTicks, @NotNull Minecraft minecraft) {
    }

    public static Vector3f getCameraTranslate() {
        return ZPClientCrosshairRecoilManager.cameraTranslate;
    }

    public static Vector3f getCameraTranslatePrev() {
        return ZPClientCrosshairRecoilManager.cameraTranslatePrev;
    }

    public static float getRecoilDecaySpeed() {
        return ZPClientCrosshairRecoilManager.recoilDecaySpeed;
    }

    public static float applyVerticalRecoil(float recoilPitch) {
        if (ZPFreeCameraEvents.enabled) {
            return 0.0f;
        }
        recoilPitch *= (1.0f - ZPClientCrosshairRecoilManager.recoilDamping);
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

        ZPClientCrosshairRecoilManager.cameraTranslate.set(nPitch * 2.0f, nYaw * 0.5f, nRoll * -i);
        ZPClientCrosshairRecoilManager.recoilDamping = Math.min(ZPClientCrosshairRecoilManager.recoilDamping + (ZPClientCrosshairRecoilManager.DAMPING_MAX * 0.05f), ZPClientCrosshairRecoilManager.DAMPING_MAX);
        ZPClientCrosshairRecoilManager.dampCooldown = 3;
        return nPitch;
    }
}
