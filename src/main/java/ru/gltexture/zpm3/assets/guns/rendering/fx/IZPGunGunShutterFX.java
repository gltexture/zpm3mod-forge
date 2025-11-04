package ru.gltexture.zpm3.assets.guns.rendering.fx;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import ru.gltexture.zpm3.assets.guns.item.ZPBaseGun;
import ru.gltexture.zpm3.engine.service.Pair;

public interface IZPGunGunShutterFX extends IZPGunFX {
    void onTrigger(@NotNull Player player, @NotNull ZPBaseGun baseGun, @NotNull ItemStack itemStack, boolean isRightHand);
    @Nullable Pair<Matrix4f, Matrix4f> getCurrentShutterTransformationGunArm(@NotNull Player player, @NotNull ZPBaseGun baseGun, @NotNull ItemStack itemStack, boolean rightHand, float deltaTicks);
}