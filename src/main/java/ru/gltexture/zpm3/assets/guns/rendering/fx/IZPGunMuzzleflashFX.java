package ru.gltexture.zpm3.assets.guns.rendering.fx;

import net.minecraft.client.renderer.MultiBufferSource;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.client.callbacking.ZPClientCallbacks;
import ru.gltexture.zpm3.assets.guns.item.ZPBaseGun;

public interface IZPGunMuzzleflashFX extends ZPClientCallbacks.ZPClientResourceDependentObject {
    void triggerRecoil(@NotNull ZPBaseGun baseGun, ZPClientCallbacks.ZPGunShotCallback.@NotNull GunFXData gunFXData);
    void onClientTick();
    void render( @NotNull MultiBufferSource buffer, float partialTicks, float deltaTicks);
}
