package ru.gltexture.zpm3.assets.guns.rendering.basic;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.joml.*;
import ru.gltexture.zpm3.assets.debug.imgui.DearUITRSInterface;
import ru.gltexture.zpm3.assets.fx.init.ZPParticles;
import ru.gltexture.zpm3.assets.fx.particles.options.ColoredSmokeOptions;
import ru.gltexture.zpm3.assets.fx.particles.options.GunShellOptions;
import ru.gltexture.zpm3.engine.client.callbacking.ZPClientCallbacks;
import ru.gltexture.zpm3.assets.guns.rendering.fx.IZPGunParticlesFX;
import ru.gltexture.zpm3.assets.guns.rendering.fx.ZPGunFXGlobalData;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;
import ru.gltexture.zpm3.assets.guns.item.ZPBaseGun;

import java.lang.Math;
import java.util.Objects;

public class ZPDefaultGunParticlesFX implements IZPGunParticlesFX {
    protected ZPDefaultGunParticlesFX() {
    }

    public static ZPDefaultGunParticlesFX create() {
        return new ZPDefaultGunParticlesFX();
    }

    @Override
    public void triggerSmoke(@NotNull Player player, @NotNull ZPBaseGun baseGun, ZPClientCallbacks.ZPGunShotCallback.@NotNull GunFXData gunFXData) {
        ZPDefaultGunParticlesFX.emmitParticleShell(gunFXData.isRightHand(), player);
        for (int i = 0; i < Math.max(gunFXData.recoilStrength() / 3.0f, 1.0f) + ZPRandom.getRandom().nextInt(2); i++) {
            ZPDefaultGunParticlesFX.emmitParticleSmoke(gunFXData.isRightHand(), player);
        }
    }

    public static void emmitParticleSmoke(boolean isRightHand, Player player) {
        final Minecraft mc = Minecraft.getInstance();
        final Camera camera = mc.gameRenderer.getMainCamera();

        final Vector3f camPos = camera.getPosition().toVector3f();
        final Quaternionf camRot = camera.rotation();

        Vector4f muzzle = new Vector4f(0, 0, 0, 1);
        ZPGunFXGlobalData.getGunData(isRightHand).getMflash1spTransformationTarget().transform(muzzle);
        ZPGunFXGlobalData.getGunData(isRightHand).getCurrentGunItemMatrix().transform(muzzle);
        muzzle.mul(-1.0f, 1.0f, -1.0f, 1.0f);
        muzzle.add(0.0f, 0.05f, 0.0f, 0.0f);

        Vector3f itemSpace = new Vector3f(muzzle.x, muzzle.y, muzzle.z);
        Vector3f worldOffset = camRot.transform(itemSpace, new Vector3f());
        Vector3f spawnPos = camPos.add(worldOffset);

        final Vector3f motion = player.getLookAngle().toVector3f().normalize().mul(0.3f);
        motion.add(ZPRandom.instance.randomVector3f(0.05f, new Vector3f(0.1f, 0.1f, 0.1f)));

        final Vector3f color = new Vector3f(0.7f, 0.7f, 0.7f).add(ZPRandom.instance.randomVector3f(0.05f, new Vector3f(0.1f, 0.1f, 0.1f)));
        final int lifetime = 10 + ZPRandom.getRandom().nextInt(10);
        Objects.requireNonNull(mc.level).addParticle(new ColoredSmokeOptions(ZPParticles.colored_cloud.get(), color, 0.8f, lifetime), false, spawnPos.x, spawnPos.y, spawnPos.z, motion.x(), motion.y() + 0.1f, motion.z());
    }

    public static void emmitParticleShell(boolean isRightHand, Player player) {
        if (DearUITRSInterface.muzzleflashRenderingMode < 3) {
            return;
        }

        final Minecraft mc = Minecraft.getInstance();
        final Vector3f color = new Vector3f(0.7f, 0.5f, 0.3f).add(new Vector3f(ZPRandom.instance.randomFloat(0.05f)));

        final Camera camera = mc.gameRenderer.getMainCamera();

        final Vector3f camPos = camera.getPosition().toVector3f();
        final Quaternionf camRot = camera.rotation();

        float distanceToGun = ZPGunFXGlobalData.getGunData(isRightHand).getCurrentGunItemMatrix().getScale(new Vector3f()).z / 1.75f;
        Matrix4f trans = new Matrix4f().identity().translate(0.0f, 0.0f, -distanceToGun);

        Vector4f muzzle = new Vector4f(0, 0, 0, 1);
        ZPGunFXGlobalData.getGunData(isRightHand).getMflash1spTransformationTarget().transform(muzzle);
        ZPGunFXGlobalData.getGunData(isRightHand).getCurrentGunItemMatrix().transform(muzzle);

        muzzle.mul(-1.0f, 1.0f, -1.0f, 1.0f);
        muzzle.add(0.0f, 0.1f, 0.0f, 0.0f);
        trans.transform(muzzle);

        Vector3f itemSpace = new Vector3f(muzzle.x, muzzle.y, muzzle.z);
        Vector3f worldOffset = camRot.transform(itemSpace, new Vector3f());
        Vector3f spawnPos = camPos.add(worldOffset);

        final Vector3f motion = player.getLookAngle().toVector3f().normalize().cross(camRot.transform(new Vector3f(0.0f, 1.0f, 0.0f)));
        if (!isRightHand) {
            motion.negate();
        }
        motion.add(ZPRandom.instance.randomVector3f(0.05f, new Vector3f(0.1f, 0.1f, 0.1f)));

        Objects.requireNonNull(mc.level).addParticle(new GunShellOptions(ZPParticles.gun_shell.get(), color), false, spawnPos.x, spawnPos.y, spawnPos.z, motion.x(), motion.y() + 0.1f, motion.z());
    }
}