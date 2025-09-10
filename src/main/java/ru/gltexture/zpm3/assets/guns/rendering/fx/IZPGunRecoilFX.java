package ru.gltexture.zpm3.assets.guns.rendering.fx;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import ru.gltexture.zpm3.engine.client.callbacking.ZPClientCallbacks;
import ru.gltexture.zpm3.assets.guns.item.ZPBaseGun;

public interface IZPGunRecoilFX extends ZPClientCallbacks.ZPClientTickCallback {
    void triggerRecoil(@NotNull Player player, @NotNull ZPBaseGun baseGun, @NotNull ItemStack itemStack, ZPClientCallbacks.ZPGunShotCallback.@NotNull GunFXData gunFXData);
    @Nullable Matrix4f getCurrentRecoilTransformation(boolean rightHand, float partialTicks);
}