package ru.gltexture.zpm3.modules.guns.rendering.fx;

import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import ru.gltexture.zpm3.engine.client.callbacking.ZPClientCallbacks;

public interface IZPGunReloadingFX extends IZPGunFX, ZPClientCallbacks.ZPClientTickCallback, ZPClientCallbacks.ZPGunReloadStartCallback {
    @Nullable Matrix4f getCurrentGunReloadingTransformation(boolean rightHand, float partialTicks);
    @Nullable Matrix4f getCurrentArmReloadingTransformation(boolean rightHand, float partialTicks);
}