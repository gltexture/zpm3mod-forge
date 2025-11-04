package ru.gltexture.zpm3.assets.guns.rendering.fx;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.client.callbacking.ZPClientCallbacks;
import ru.gltexture.zpm3.assets.guns.item.ZPBaseGun;

public interface IZPGunMuzzleflashFX extends IZPGunFX, ZPClientCallbacks.ZPGunShotCallback, ZPClientCallbacks.ZPClientResourceDependentObject {
    void render1Person(@NotNull MultiBufferSource buffer, float partialTicks, float deltaTicks);
    void render3Person(@NotNull LivingEntity livingEntity, @NotNull MultiBufferSource buffer, float deltaTicks, boolean isRightHanded);
}