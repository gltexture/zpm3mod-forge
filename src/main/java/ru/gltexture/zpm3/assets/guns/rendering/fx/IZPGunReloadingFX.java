package ru.gltexture.zpm3.assets.guns.rendering.fx;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import ru.gltexture.zpm3.engine.client.callbacking.ZPClientCallbacks;
import ru.gltexture.zpm3.assets.guns.item.ZPBaseGun;

public interface IZPGunReloadingFX extends ZPClientCallbacks.ZPClientTickCallback {
    void triggerReloadingStart(@NotNull ZPBaseGun baseGun, ZPClientCallbacks.ZPGunReloadStartCallback.@NotNull GunFXData gunFXData);
    @Nullable Matrix4f getCurrentGunReloadingTransformation(boolean rightHand, float partialTicks);
    @Nullable Matrix4f getCurrentArmReloadingTransformation(boolean rightHand, float partialTicks);
}