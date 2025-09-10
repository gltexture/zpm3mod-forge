package ru.gltexture.zpm3.assets.guns.rendering.basic;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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
    public void triggerParticles(@NotNull Player player, @NotNull ZPBaseGun baseGun, @NotNull ItemStack itemStack, ZPClientCallbacks.ZPGunShotCallback.@NotNull GunFXData gunFXData) {
        if (ZPDefaultGunMuzzleflashFX.quality() <= 1) {
            return;
        }
        if (gunFXData.muzzleflashTime() < 0.0f) {
            return;
        }
        if (baseGun.getGunProperties().getCustomShotParticlesEmitter() != null) {
            baseGun.getGunProperties().getCustomShotParticlesEmitter().emmit(player, baseGun, itemStack, gunFXData);
        } else {
            IZPGunParticlesFX.DEFAULT_PARTICLES_EMITTER.emmit(player, baseGun, itemStack, gunFXData);
        }
    }

    public static void emmitParticleSmoke(boolean isRightHand, Player player, boolean smoky) {
        final Minecraft mc = Minecraft.getInstance();

        Vector3f spawnPos = new Vector3f(0.0f);
        Vector3f motion;

        if (player == mc.player && mc.options.getCameraType().isFirstPerson()) {
            final Camera camera = mc.gameRenderer.getMainCamera();
            final Vector3f camPos = camera.getPosition().toVector3f();
            final Quaternionf camRot = camera.rotation();

            Vector4f muzzle = new Vector4f(0.0f, 0.0f, 0.0f, 1.0f);
            ZPGunFXGlobalData.getGunData(isRightHand).getMflash1spTransformationTarget().transform(muzzle);
            ZPGunFXGlobalData.getGunData(isRightHand).getCurrentGunItemMatrix().transform(muzzle);
            muzzle.mul(-1.0f, 1.0f, -1.0f, 1.0f);
            muzzle.add(0.0f, 0.05f, 0.0f, 0.0f);

            Vector3f itemSpace = new Vector3f(muzzle.x, muzzle.y, muzzle.z);
            Vector3f worldOffset = camRot.transform(itemSpace, new Vector3f());
            spawnPos = camPos.add(worldOffset);

            motion = player.getLookAngle().toVector3f().normalize().mul(0.3f);
        } else {
            spawnPos = ZPDefaultGunParticlesFX.pickSpawnPosFor3Person(player, 1.0f, isRightHand);
/*

            final Matrix4f transform = new Matrix4f().identity();
            final float yawRad = (float) Math.toRadians(-player.getYRot());
            final float pitchRad = (float) Math.toRadians(player.getXRot());

            Vector4f localPos = new Vector4f(DearUITRSInterface.trsMflash3d.position, 1.0f);
            Vector3f center = new Vector3f((float) player.getX(), (float) player.getY() + player.getBbHeight() * 0.85f, (float) player.getZ());
            center.add(isRightHand ? -0.325f : 0.325f, 0.0f, 0.0f);
            transform.translate(center);
            transform.rotateY(yawRad);
            transform.rotateX(pitchRad);
            transform.translate(localPos.x, localPos.y, localPos.z);
            localPos.mul(transform);
            spawnPos = new Vector3f(localPos.x, localPos.y, localPos.z);
 */

            motion = player.getLookAngle().toVector3f().mul(0.2f);
        }

        motion.mul(smoky ? 0.2f : 1.0f);
        motion.add(ZPRandom.instance.randomVector3f(smoky ? 0.02f : 0.05f, new Vector3f(smoky ? 0.04f : 0.1f)));

        final Vector3f color = new Vector3f(smoky ? 0.9f : 0.7f).add(ZPRandom.instance.randomVector3f(0.05f, new Vector3f(0.1f, 0.1f, 0.1f)));
        final int lifetime = (smoky ? 40 : 10) + ZPRandom.getRandom().nextInt(10);

        Objects.requireNonNull(mc.level).addParticle(new ColoredSmokeOptions(ZPParticles.colored_cloud.get(), color, smoky ? 0.9f : 0.8f, lifetime), false, spawnPos.x, spawnPos.y, spawnPos.z, motion.x(), motion.y() + (smoky ? 0.02f : 0.1f), motion.z());
    }

    public static void emmitParticleShell(boolean isRightHand, Player player) {
        if (!ZPDefaultGunMuzzleflashFX.useFancyRendering1person()) {
            return;
        }

        final Minecraft mc = Minecraft.getInstance();
        final Vector3f color = new Vector3f(0.7f, 0.5f, 0.3f).add(new Vector3f(ZPRandom.instance.randomFloat(0.05f)));

        boolean isFirstPersonSelf = (player == mc.player && mc.options.getCameraType().isFirstPerson());

        final Vector3f spawnPos;
        final Vector3f motion;

        if (isFirstPersonSelf) {
            final Camera camera = mc.gameRenderer.getMainCamera();
            final Vector3f camPos = camera.getPosition().toVector3f();
            final Quaternionf camRot = camera.rotation();

            float distanceToGun = ZPGunFXGlobalData.getGunData(isRightHand).getCurrentGunItemMatrix().getScale(new Vector3f()).z / 1.75f;
            Matrix4f trans = new Matrix4f().identity().translate(0.0f, 0.0f, -distanceToGun);

            Vector4f muzzle = new Vector4f(0.0f, 0.0f, 0.0f, 1.0f);
            ZPGunFXGlobalData.getGunData(isRightHand).getMflash1spTransformationTarget().transform(muzzle);
            ZPGunFXGlobalData.getGunData(isRightHand).getCurrentGunItemMatrix().transform(muzzle);

            muzzle.mul(-1.0f, 1.0f, -1.0f, 1.0f);
            muzzle.add(0.0f, 0.1f, 0.0f, 0.0f);
            trans.transform(muzzle);

            Vector3f itemSpace = new Vector3f(muzzle.x, muzzle.y, muzzle.z);
            Vector3f worldOffset = camRot.transform(itemSpace, new Vector3f());
            spawnPos = camPos.add(worldOffset);

            motion = player.getLookAngle().toVector3f().normalize().cross(camRot.transform(new Vector3f(0.0f, 1.0f, 0.0f)));
            if (!isRightHand) {
                motion.negate();
            }
            motion.add(ZPRandom.instance.randomVector3f(0.05f, new Vector3f(0.1f, 0.1f, 0.1f)));

        } else {
            float side = (isRightHand ? 1f : -1f);
            spawnPos = ZPDefaultGunParticlesFX.pickSpawnPosFor3Person(player, 0.675f, isRightHand);

            float yaw = -player.getYRot() * ((float) Math.PI / 180F);
            float pitch = player.getXRot() * ((float) Math.PI / 180F);

            final Matrix4f space = new Matrix4f().identity();
            space.rotate(Axis.YP.rotation(yaw));
            space.rotate(Axis.XP.rotation(pitch));

            Vector4f auxPoint = new Vector4f(0.75f * -side, 0.0f, 0.0f, 1.0f);
            space.transform(auxPoint);
            motion = new Vector3f(auxPoint.x, auxPoint.y, auxPoint.z);

            motion.add(ZPRandom.instance.randomVector3f(0.05f, new Vector3f(0.1f, 0.1f, 0.1f)));
        }

        Objects.requireNonNull(mc.level).addParticle(new GunShellOptions(ZPParticles.gun_shell.get(), color), false, spawnPos.x, spawnPos.y, spawnPos.z, motion.x(), motion.y() + 0.1f, motion.z());
    }

    private static Vector3f pickSpawnPosFor3Person(@NotNull Player player, float zOffset, boolean isRightHand) {
        EntityRenderer<?> renderer = Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(player);

        Vector3f spawnPos = new Vector3f(0.0f);
        if ((renderer instanceof PlayerRenderer playerRenderer)) {
            HumanoidModel<?> model = playerRenderer.getModel();
            ModelPart hand = isRightHand ? model.rightArm : model.leftArm;

            PoseStack poseStack = new PoseStack();
            poseStack.pushPose();

            float yaw = -player.getYRot() * ((float) Math.PI / 180F);
            float pitch = player.getXRot() * ((float) Math.PI / 180F);

            Vector3f center = new Vector3f((float) player.getX(), (float) player.getY() + player.getBbHeight() * 0.75f, (float) player.getZ());

            poseStack.translate(center.x, center.y, center.z);
            poseStack.mulPose(Axis.YP.rotation(yaw));
            poseStack.mulPose(Axis.XP.rotation(pitch));
            poseStack.translate(0.0f, 0.0f, zOffset);

            hand.translateAndRotate(poseStack);

            Matrix4f m = poseStack.last().pose();
            Vector4f p = new Vector4f(0, 0, 0, 1).mul(m);
            spawnPos = new Vector3f(p.x, p.y, p.z);

            poseStack.popPose();
        }

        return spawnPos;
    }
}