package ru.gltexture.zpm3.assets.guns.rendering.fx;

import net.minecraft.client.renderer.MultiBufferSource;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import ru.gltexture.zpm3.engine.client.callbacking.ZPClientCallbacks;
import ru.gltexture.zpm3.assets.guns.item.ZPBaseGun;

public interface IZPGunMuzzleflashFX extends ZPClientCallbacks.ZPClientResourceDependentObject {
    void triggerRecoil(@NotNull ZPBaseGun baseGun, ZPClientCallbacks.ZPGunShotCallback.@NotNull GunFXData gunFXData);
    void onClientTick();
    void render1Person(@NotNull MultiBufferSource buffer, float partialTicks, float deltaTicks);
    void render3Person(@NotNull MultiBufferSource buffer, float deltaTicks, @NotNull Matrix4f matrix);
}