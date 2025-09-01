package ru.gltexture.zpm3.assets.guns.rendering.fx;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.client.callbacking.ZPClientCallbacks;
import ru.gltexture.zpm3.assets.guns.item.ZPBaseGun;

public interface IZPGunMuzzleflashFX extends ZPClientCallbacks.ZPClientResourceDependentObject {
    void triggerMuzzleflash(@NotNull ZPBaseGun baseGun, ZPClientCallbacks.ZPGunShotCallback.@NotNull GunFXData gunFXData);
    void onClientTick();
    void render1Person(@NotNull MultiBufferSource buffer, float partialTicks, float deltaTicks);
    void render3Person(@NotNull LivingEntity livingEntity, @NotNull MultiBufferSource buffer, float deltaTicks, boolean isRightHanded);
}