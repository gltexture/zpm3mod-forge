package ru.gltexture.zpm3.assets.guns.rendering.basic;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import ru.gltexture.zpm3.assets.guns.item.ZPBaseGun;
import ru.gltexture.zpm3.assets.guns.rendering.ZPDefaultGunRenderers;
import ru.gltexture.zpm3.assets.guns.rendering.fx.IZPGunGunShutterFX;
import ru.gltexture.zpm3.engine.service.Pair;
import ru.gltexture.zpm3.engine.service.ZPUtility;

public class ZPDefaultGunShutterFX implements IZPGunGunShutterFX {
    private final float[] shutterProgression;
    private final boolean[] playedAnimation;
    private static final Matrix4f IDENT_MAT = new Matrix4f().identity();

    protected ZPDefaultGunShutterFX() {
        this.shutterProgression = new float[] {1.0f, 1.0f};
        this.playedAnimation = new boolean[] {false, false};
    }

    public static ZPDefaultGunShutterFX create() {
        return new ZPDefaultGunShutterFX();
    }

    @Override
    public void onTrigger(@NotNull Player player, @NotNull ZPBaseGun baseGun, @NotNull ItemStack itemStack, boolean isRightHand) {
        if (!player.equals(Minecraft.getInstance().player)) {
            return;
        }

        final int id = isRightHand ? 1 : 0;
        this.shutterProgression[id] = 0.0f;
        this.playedAnimation[id] = false;
    }

    @Override
    public @Nullable Pair<Matrix4f, Matrix4f> getCurrentShutterTransformationGunArm(@NotNull Player player, @NotNull ZPBaseGun baseGun, @NotNull ItemStack itemStack, boolean rightHand, float deltaTicks) {
        final int id = rightHand ? 1 : 0;

        if (this.shutterProgression[id] < 0.0f) {
            return null;
        }

        final boolean altAnim = !rightHand || !player.getOffhandItem().isEmpty();
        final float speed = altAnim ? 2.0f : baseGun.getGunProperties().getAnimationData().getShutterAnimationSpeed();
        this.shutterProgression[id] += deltaTicks;
        float reloadingStage = this.shutterProgression[id] * speed;
        if (reloadingStage > 1.0f) {
            this.shutterProgression[id] = -1.0f;
            return null;
        }

        if (reloadingStage > 0.5f) {
            reloadingStage = 1.0f - reloadingStage;
            if (!this.playedAnimation[id]) {
                if (baseGun.getGunProperties().getAnimationData().getShutterSound() != null) {
                    ZPUtility.sounds().play(SimpleSoundInstance.forLocalAmbience(baseGun.getGunProperties().getAnimationData().getShutterSound(), 1.0f, 1.0F));
                }
                ZPDefaultGunRenderers.defaultParticlesFXUniversal.onEmmitShell(player, baseGun, itemStack, rightHand);
                this.playedAnimation[id] = true;
            }
        }
        final Matrix4f matrix4fGun = new Matrix4f().identity();
        final Matrix4f matrix4fArm = new Matrix4f().identity();

        if (altAnim) {
            final Vector3f translateGun = new Vector3f(0.0f, -0.56f, 0.00f);
            final Vector3f rotateGunRight = new Vector3f(-50.0f, 30.0f, -30.0f);
            final Vector3f rotateGunLeft = new Vector3f(30.0f, 0.0f, 30.0f);
            matrix4fGun.translate(translateGun)
                    .rotateX((float) Math.toRadians(rightHand ? rotateGunRight.x : rotateGunLeft.x))
                    .rotateY((float) Math.toRadians(rightHand ? rotateGunRight.y : rotateGunLeft.y))
                    .rotateZ((float) Math.toRadians(rightHand ? rotateGunRight.z : rotateGunLeft.z));
        } else {
            final boolean isShotgunAnim = baseGun.getGunProperties().getAnimationData().getShutterAnimationType().equals(ZPBaseGun.GunProperties.AnimationData.ShutterAnimationType.SHOTGUN);
            final Vector3f translateGun = isShotgunAnim ? new Vector3f(0.0f, 0.0f, 0.05f) : new Vector3f(0.0f, -0.25f, 0.25f);
            matrix4fGun.translate(translateGun)
                    .rotateX((float) Math.toRadians(isShotgunAnim ? 20.0f : -40.0f))
                    .rotateY((float) Math.toRadians(0.0f))
                    .rotateZ((float) Math.toRadians(!isShotgunAnim ? 40.0f : 0.0f));

            if (isShotgunAnim) {
                matrix4fArm.translate(new Vector3f(translateGun).mul(4.0f));
            }
        }

        return new Pair<>(
                ZPDefaultGunShutterFX.IDENT_MAT.lerp(matrix4fGun, reloadingStage, new Matrix4f()),
                ZPDefaultGunShutterFX.IDENT_MAT.lerp(matrix4fArm, reloadingStage, new Matrix4f()));
    }
}
