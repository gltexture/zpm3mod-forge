package ru.gltexture.zpm3.assets.guns.rendering.basic;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.*;
import ru.gltexture.zpm3.assets.debug.imgui.DearUITRSInterface;
import ru.gltexture.zpm3.assets.fx.init.ZPParticles;
import ru.gltexture.zpm3.assets.fx.particles.options.ColoredDefaultParticleOptions;
import ru.gltexture.zpm3.assets.fx.particles.options.GunShellOptions;
import ru.gltexture.zpm3.engine.client.callbacking.ZPClientCallbacks;
import ru.gltexture.zpm3.assets.guns.rendering.fx.IZPGunParticlesFX;
import ru.gltexture.zpm3.assets.guns.rendering.fx.ZPGunFXGlobalData;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;
import ru.gltexture.zpm3.assets.guns.item.ZPBaseGun;

import java.lang.Math;
import java.util.Objects;

public class ZPDefaultGunParticlesFX implements IZPGunParticlesFX {
    private final float[] shotTicksAccumulator;

    protected ZPDefaultGunParticlesFX() {
        this.shotTicksAccumulator = new float[] {0.0f, 0.0f};
    }

    public static ZPDefaultGunParticlesFX create() {
        return new ZPDefaultGunParticlesFX();
    }

    @Override
    public void onShot(@NotNull Player player, @NotNull ZPBaseGun baseGun, @NotNull ItemStack itemStack, ZPClientCallbacks.ZPGunShotCallback.@NotNull GunFXData gunFXData) {
        if (gunFXData.recoilStrength() > 0.0f) {
            if (player.getItemInHand(gunFXData.isRightHand() ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND).equals(itemStack)) {
                this.onEmmitSmoke(player, baseGun, itemStack, gunFXData.isRightHand());
                if (!(player.equals(Minecraft.getInstance().player) && Minecraft.getInstance().options.getCameraType().isFirstPerson() && baseGun.getGunProperties().getAnimationData().isHasShutterAnimation())) {
                    this.onEmmitShell(player, baseGun, itemStack, gunFXData.isRightHand());
                }
            }
        }
    }

    @Override
    public void onEmmitSmoke(@NotNull Player player, @NotNull ZPBaseGun baseGun, @NotNull ItemStack itemStack, boolean isRightHand) {
        if (ZPDefaultGunMuzzleflashFX.minQuality() <= 1) {
            return;
        }
        final Minecraft mc = Minecraft.getInstance();
        if (player.equals(mc.player)) {
            this.shotTicksAccumulator[isRightHand ? 1 : 0] = Math.min(this.shotTicksAccumulator[isRightHand ? 1 : 0] + Math.max(baseGun.getGunProperties().getClientRecoil() / 1.5f, 0.75f), 8.0f);
        }
        if (baseGun.getGunProperties().getCustomShotParticlesEmitter() != null) {
            if (baseGun.getGunProperties().getCustomShotParticlesEmitter().smokeEmitter() != null) {
                Objects.requireNonNull(baseGun.getGunProperties().getCustomShotParticlesEmitter().smokeEmitter()).emmit(player, baseGun, itemStack, isRightHand);
            }
        } else {
            Objects.requireNonNull(IZPGunParticlesFX.DEFAULT_PARTICLES_EMITTER.smokeEmitter()).emmit(player, baseGun, itemStack, isRightHand);
        }
    }

    @Override
    public void onEmmitShell(@NotNull Player player, @NotNull ZPBaseGun baseGun, @NotNull ItemStack itemStack, boolean isRightHand) {
        if (baseGun.getGunProperties().getCustomShotParticlesEmitter() != null) {
            if (baseGun.getGunProperties().getCustomShotParticlesEmitter().shellsEmitter() != null) {
                Objects.requireNonNull(baseGun.getGunProperties().getCustomShotParticlesEmitter().shellsEmitter()).emmit(player, baseGun, itemStack, isRightHand);
            }
        } else {
            Objects.requireNonNull(IZPGunParticlesFX.DEFAULT_PARTICLES_EMITTER.shellsEmitter()).emmit(player, baseGun, itemStack, isRightHand);
        }
    }

    public static void emmitParticleSmoke(boolean isRightHand, Player player, boolean smoky, ZPBaseGun baseGun) {
        ZPDefaultGunParticlesFX.emmitParticleSmoke(isRightHand, player, smoky, false, baseGun);
    }

    public static void emmitParticleSmoke(boolean isRightHand, Player player, boolean smoky, boolean hotBarrel, ZPBaseGun baseGun) {
        final Minecraft mc = Minecraft.getInstance();

        Vector3f spawnPos = new Vector3f(0.0f);
        Vector3f motion;

        if (player == mc.player && mc.options.getCameraType().isFirstPerson()) {
            final Camera camera = mc.gameRenderer.getMainCamera();
            final Vector3f camPos = camera.getPosition().toVector3f();
            final Quaternionf camRot = camera.rotation();

            Vector4f muzzle = new Vector4f(0.0f, 0.0f, 0.0f, 1.0f);
            Objects.requireNonNull(ZPGunFXGlobalData.getGunData(isRightHand).getMflash1spTransformationTarget()).transform(muzzle);
            ZPGunFXGlobalData.getGunData(isRightHand).getCurrentGunItemMatrix().transform(muzzle);
            muzzle.mul(-1.0f, 1.0f, -1.0f, 1.0f);
            muzzle.add(0.0f, 0.05f, 0.0f, 0.0f);

            Vector3f itemSpace = new Vector3f(muzzle.x, muzzle.y, muzzle.z);
            Vector3f worldOffset = camRot.transform(itemSpace, new Vector3f());
            spawnPos = camPos.add(worldOffset);

            motion = player.getLookAngle().toVector3f().normalize().mul(0.3f);
        } else {
            spawnPos = ZPDefaultGunParticlesFX.pickSpawnPosFor3Person(player, 1.0f, isRightHand);
            motion = player.getLookAngle().toVector3f().mul(0.2f);
        }

        motion.mul(smoky ? 0.2f : 1.0f);
        motion.add(ZPRandom.instance.randomVector3f(smoky ? 0.02f : 0.05f, new Vector3f(smoky ? 0.04f : 0.1f)));

        if (hotBarrel) {
            motion.mul(0.0f).add(0.0f, 0.05f, 0.0f);
        } else {
            motion.add(0.0f, (smoky ? 0.02f : 0.1f), 0.0f);
        }

        final Vector3f color = new Vector3f(smoky ? 0.9f : 0.7f).add(ZPRandom.instance.randomVector3f(0.05f, new Vector3f(0.1f, 0.1f, 0.1f)));
        final int lifetime = (smoky ? 40 : 10) + ZPRandom.getRandom().nextInt(10);

        Objects.requireNonNull(mc.level).addParticle(new ColoredDefaultParticleOptions(ZPParticles.colored_cloud.get(), color, smoky ? 0.9f : 0.8f, lifetime), false, spawnPos.x, spawnPos.y, spawnPos.z, motion.x(), motion.y(), motion.z());
    }

    public static void emmitParticleShell(boolean isRightHand, Player player, ZPBaseGun baseGun) {
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
            final boolean isRifle = baseGun.getGunProperties().getHeldType().equals(ZPBaseGun.GunProperties.HeldType.RIFLE);
            final float xOff = isRifle ? 0.0f : 0.5f;
            float distanceToGun = ZPGunFXGlobalData.getGunData(isRightHand).getCurrentGunItemMatrix().getScale(new Vector3f()).z / (xOff + 2.0f);

            Vector4f muzzle = isRightHand ? new Vector4f(0.0f,  -distanceToGun / 4.0f, -distanceToGun, 1.0f) : new Vector4f(0.0f, -distanceToGun * 1.25f, distanceToGun / 1.75f, 1.0f);
            Objects.requireNonNull(ZPGunFXGlobalData.getGunData(isRightHand).getMflash1spTransformationTarget()).transform(muzzle);
            ZPGunFXGlobalData.getGunData(isRightHand).getCurrentGunItemMatrix().transform(muzzle);

            muzzle.mul(-1.0f, 1.0f, -1.0f, 1.0f);
            muzzle.add(0.0f, 0.0f, 0.0f, 0.0f);

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

        Objects.requireNonNull(mc.level).addParticle(new GunShellOptions(ZPParticles.gun_shell.get(), color), false, spawnPos.x, spawnPos.y, spawnPos.z, motion.x(), motion.y() + 1.0f, motion.z());
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

    @Override
    public void onTick(TickEvent.@NotNull Phase phase) {
        if (phase == TickEvent.Phase.START) {
            Player player = Minecraft.getInstance().player;
            if (player != null) {
                @Nullable final ItemStack itemStackInRightHand = player.getMainHandItem();
                @Nullable final ItemStack itemStackInLeftHand = player.getOffhandItem();

                if (itemStackInRightHand.getItem() instanceof ZPBaseGun baseGun) {
                    if (baseGun.getCurrentTimeBeforeShoot(player, itemStackInRightHand) > 0) {
                        this.shotTicksAccumulator[1] = 0.0f;
                    }
                    if (player.tickCount % 3 == 0) {
                        if (this.shotTicksAccumulator[1] >= 3.0f) {
                            ZPDefaultGunParticlesFX.emmitParticleSmoke(true, player, false, true, baseGun);
                        }
                    }
                    if (DearUITRSInterface.emmitShells) {
                        ZPDefaultGunParticlesFX.emmitParticleShell(true, Minecraft.getInstance().player, baseGun);
                    }
                    if (DearUITRSInterface.emmitSmoke) {
                        ZPDefaultGunParticlesFX.emmitParticleSmoke(true, Minecraft.getInstance().player, false, baseGun);
                    }
                } else {
                    this.shotTicksAccumulator[1] = 0.0f;
                }
                if (itemStackInLeftHand.getItem() instanceof ZPBaseGun baseGun) {
                    if (baseGun.getCurrentTimeBeforeShoot(player, itemStackInLeftHand) > 0) {
                        this.shotTicksAccumulator[0] = 0.0f;
                    }
                    if (player.tickCount % 3 == 0) {
                        if (this.shotTicksAccumulator[0] >= 3.0f) {
                            ZPDefaultGunParticlesFX.emmitParticleSmoke(false, player, false, true, baseGun);
                        }
                    }
                    if (DearUITRSInterface.emmitShells) {
                        ZPDefaultGunParticlesFX.emmitParticleShell(false, Minecraft.getInstance().player, baseGun);
                    }
                    if (DearUITRSInterface.emmitSmoke) {
                        ZPDefaultGunParticlesFX.emmitParticleSmoke(false, Minecraft.getInstance().player, false, baseGun);
                    }
                } else {
                    this.shotTicksAccumulator[0] = 0.0f;
                }

                final float tps = 0.2f;
                this.shotTicksAccumulator[0] = Math.max(this.shotTicksAccumulator[0] - tps, 0.0f);
                this.shotTicksAccumulator[1] = Math.max(this.shotTicksAccumulator[1] - tps, 0.0f);
            }
        }
    }
}