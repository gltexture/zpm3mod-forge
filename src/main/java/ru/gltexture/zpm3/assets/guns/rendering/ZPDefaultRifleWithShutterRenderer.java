package ru.gltexture.zpm3.assets.guns.rendering;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Transformation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import ru.gltexture.zpm3.assets.debug.imgui.DearUITRSInterface;
import ru.gltexture.zpm3.assets.guns.item.ZPBaseGun;
import ru.gltexture.zpm3.assets.guns.rendering.fx.ZPGunFXGlobalData;
import ru.gltexture.zpm3.assets.guns.rendering.transforms.AbstractGunTransforms;
import ru.gltexture.zpm3.engine.client.callbacking.ZPClientCallbacks;
import ru.gltexture.zpm3.engine.exceptions.ZPNullException;
import ru.gltexture.zpm3.engine.service.Pair;

import java.util.Objects;

public class ZPDefaultRifleWithShutterRenderer extends ZPAbstractGunRenderer {
    private final float[] timerShutter;

    public ZPDefaultRifleWithShutterRenderer() {
        this.timerShutter = new float[] {-1.0f, -1.0f};
    }

    @Override
    protected AbstractGunTransforms gunTransforms() {
        return new AbstractGunTransforms() {
            @Override
            public Vector3f scalingGun1P() {
                return new Vector3f(1.0f);
            }

            @Override
            public Vector3f scalingGun3P() {
                return new Vector3f(1.5f);
            }

            @Override
            public Vector3f translationGunRight() {
                return new Vector3f(0.213f, 0.139f, -0.607f);
            }

            @Override
            public Vector3f rotationGunRight() {
                return new Vector3f(-6.21f, 8.5f, 0.82f);
            }

            @Override
            public Vector3f translationArmRight() {
                return new Vector3f(ZPDefaultRifleRenderer.rightArmTranslationCurrent);
            }

            @Override
            public Vector3f rotationArmRight() {
                return new Vector3f(ZPDefaultRifleRenderer.rightArmRotationCurrent);
            }

            @Override
            public Vector3f translationGunLeft() {
                return new Vector3f(-0.35f, 0.3f, -0.4f);
            }

            @Override
            public Vector3f rotationGunLeft() {
                return new Vector3f(-55.0f, 185.0f, 6.2f);
            }

            @Override
            public Vector3f translationArmLeft() {
                return new Vector3f(ZPDefaultRifleRenderer.leftArmTranslationCurrent);
            }

            @Override
            public Vector3f rotationArmLeft() {
                return new Vector3f(ZPDefaultRifleRenderer.leftArmRotationCurrent);
            }

            @Override
            public Vector3f translationMuzzleflash3PRight() {
                return new Vector3f(-0.28f, 0.47f, 0.065f);
            }

            @Override
            public Vector3f translationMuzzleflash3PLeft() {
                return new Vector3f(-0.28f, 0.47f, 0.065f);
            }

            @Override
            public Vector3f translationMuzzleflash1PRight() {
                return new Vector3f(0.06f, 0.37f, -0.38f);
            }

            @Override
            public Vector3f translationMuzzleflash1PLeft() {
                return new Vector3f(-0.074f, 0.6666f, 0.242f);
            }

            @Override
            public Float muzzleflashScale() {
                return 0.75f;
            }

            @Override
            public Vector3f translationGunReloadingRight() {
                return new Vector3f(0.0f, -0.15f, 0.2f);
            }

            @Override
            public Vector3f rotationGunReloadingRight() {
                return new Vector3f(0.0f, 24.0f, -24.0f);
            }

            @Override
            public Vector3f translationGunReloadingLeft() {
                return new Vector3f(0.02f, -0.25f, 0.17f);
            }

            @Override
            public Vector3f rotationGunReloadingLeft() {
                return new Vector3f(5.0f, -75.0f, 4.0f);
            }

            @Override
            public Vector3f translationArmReloadingRight() {
                return new Vector3f(0.0f);
            }

            @Override
            public Vector3f rotationArmReloadingRight() {
                return new Vector3f(0.0f);
            }

            @Override
            public Vector3f translationArmReloadingLeft() {
                return new Vector3f(0.0f);
            }

            @Override
            public Vector3f rotationArmReloadingLeft() {
                return new Vector3f(0.0f);
            }

            @Override
            public Vector3f scaleArmLeft() {
                return new Vector3f(ZPDefaultRifleRenderer.leftArmCurrentScaling);
            }

            @Override
            public Vector3f scaleArmRight() {
                return new Vector3f(ZPDefaultRifleRenderer.rightArmCurrentScaling);
            }
        };
    }

    static ZPDefaultRifleWithShutterRenderer create() {
        return new ZPDefaultRifleWithShutterRenderer();
    }

    private int hand(boolean isRight) {
        return isRight ? 1 : 0;
    }

    @Override
    public void onRenderItem3Person(@NotNull ItemInHandRenderer itemInHandRenderer, float deltaTicks, @NotNull EntityModel<?> entityModel, LivingEntity pLivingEntity, ItemStack pItemStack, ItemDisplayContext pDisplayContext, HumanoidArm pArm, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.onDefaultRenderItem3Person(itemInHandRenderer, deltaTicks, entityModel, pLivingEntity, pItemStack, pDisplayContext, pArm, pPoseStack, pBuffer, pPackedLight);
        if (Objects.requireNonNull(Minecraft.getInstance().player).equals(pLivingEntity) && !Minecraft.getInstance().options.getCameraType().isFirstPerson()) {
            this.timerShutter[this.hand(pArm.equals(HumanoidArm.RIGHT))] = -1.0f;
        }
    }

    @Override
    public void onRenderItem1Person(AbstractClientPlayer pPlayer, float deltaTicks, float pPartialTicks, float pPitch, InteractionHand pHand, float pSwingProgress, ItemStack pStack, float pEquippedProgress, PoseStack pPoseStack, MultiBufferSource pBuffer, int pCombinedLight) {
        final boolean isRightHanded = pHand == InteractionHand.MAIN_HAND;
        if (pStack.getItem() instanceof ZPBaseGun baseGun) {
            final int id = this.hand(isRightHanded);
            if (baseGun.getCurrentTimeBeforeShoot(pPlayer, pStack) > 0) {
                this.timerShutter[id] = -1.0f;
            }
            if (this.timerShutter[id] >= 0.0f) {
                this.timerShutter[id] += deltaTicks;
                if (this.timerShutter[id] > (baseGun.getGunProperties().getShootCooldown() / 20.0f) / 2.0f) {
                    ZPDefaultGunRenderers.defaultShotgunShutterFXUniversal.onTrigger(pPlayer, baseGun, pStack, isRightHanded);
                    this.timerShutter[id] = -1.0f;
                }
            }
            ZPDefaultRifleRenderer.onRenderItem1PersonRifle(this, pPlayer, deltaTicks, pPartialTicks, pPitch, pHand, pSwingProgress, pStack, pEquippedProgress, pPoseStack, pBuffer, pCombinedLight);
        }
    }

    @Override
    public void onReloadStart(@NotNull Player player, @NotNull ZPBaseGun baseGun, @NotNull ItemStack itemStack, ZPClientCallbacks.ZPGunReloadStartCallback.@NotNull GunFXData gunFXData) {
    }

    @Override
    public void onShot(@NotNull Player player, @NotNull ZPBaseGun baseGun, @NotNull ItemStack itemStack, ZPClientCallbacks.ZPGunShotCallback.@NotNull GunFXData gunFXData) {
        if (baseGun.getCurrentShootCooldown(player, itemStack) > 4) {
            this.timerShutter[this.hand(gunFXData.isRightHand())] = 0.0f;
        }
    }
}
/*
@Override
    public void onRenderItem1Person(AbstractClientPlayer pPlayer, float deltaTicks, float pPartialTicks, float pPitch, InteractionHand pHand, float pSwingProgress, ItemStack pStack, float pEquippedProgress, PoseStack pPoseStack, MultiBufferSource pBuffer, int pCombinedLight) {
        try {
            final boolean isRightHanded = pHand == InteractionHand.MAIN_HAND;
            if (pStack.getItem() instanceof ZPBaseGun baseGun) {
                final int id = this.hand(isRightHanded);
                if (baseGun.getCurrentTimeBeforeShoot(pPlayer, pStack) > 0) {
                    this.timerShutter[id] = -1.0f;
                }
                if (this.timerShutter[id] >= 0.0f) {
                    this.timerShutter[id] += deltaTicks;
                    if (this.timerShutter[id] > (baseGun.getGunProperties().getShootCooldown() / 20.0f) / 2.0f) {
                        ZPDefaultGunRenderers.defaultShotgunShutterFXUniversal.onTrigger(pPlayer, baseGun, pStack, isRightHanded);
                        this.timerShutter[id] = -1.0f;
                    }
                }

                pPoseStack.pushPose();
                final float equippedConst = -0.6F + pEquippedProgress * -0.6F;
                final Matrix4f transformation = new Matrix4f().identity();

                final Vector3f startTranslation = Objects.requireNonNull(isRightHanded ? this.gunTransforms().translationGunRight() : this.gunTransforms().translationGunLeft());
                startTranslation.add(0.0f, equippedConst, 0.0f);
                startTranslation.add(DearUITRSInterface.trsGun.position);

                final Vector3f startRotation = Objects.requireNonNull(isRightHanded ? this.gunTransforms().rotationGunRight() : this.gunTransforms().rotationGunLeft());
                startRotation.add(DearUITRSInterface.trsGun.rotation);

                pPoseStack.setIdentity();
                this.translateStack(pPoseStack, pPartialTicks);
                transformation
                        .translate(startTranslation)
                        .rotateX((float) Math.toRadians(startRotation.x))
                        .rotateY((float) Math.toRadians(startRotation.y))
                        .rotateZ((float) Math.toRadians(startRotation.z))
                        .scale(Objects.requireNonNull(this.gunTransforms().scalingGun1P()));
                pPoseStack.pushTransformation(new Transformation(transformation));

                @Nullable Matrix4f reloading = ZPDefaultGunRenderers.defaultReloadingFXUniversal.getCurrentGunReloadingTransformation(isRightHanded, pPartialTicks);
                if (reloading != null) {
                    pPoseStack.pushTransformation(new Transformation(reloading));
                }

                @Nullable Pair<Matrix4f, Matrix4f> shutter = ZPDefaultGunRenderers.defaultShotgunShutterFXUniversal.getCurrentShutterTransformationGunArm(pPlayer, baseGun, pStack, isRightHanded, deltaTicks);
                if (shutter != null) {
                    pPoseStack.pushTransformation(new Transformation(shutter.first()));
                }
                final Matrix4f matrixGun = new Matrix4f(pPoseStack.last().pose());
                @Nullable Matrix4f recoil = ZPDefaultGunRenderers.defaultRecoilFXUniversal.getCurrentRecoilTransformation(isRightHanded, pPartialTicks);
                if (recoil != null) {
                    pPoseStack.pushTransformation(new Transformation(recoil));
                }

                this.renderItem(Minecraft.getInstance().getItemRenderer(), pPlayer, pStack, isRightHanded ? ItemDisplayContext.FIRST_PERSON_RIGHT_HAND : ItemDisplayContext.FIRST_PERSON_LEFT_HAND, !isRightHanded, pPoseStack, pBuffer, pCombinedLight);
                if (shutter != null) {
                    pPoseStack.pushTransformation(new Transformation(shutter.second()));
                }
                final Matrix4f matrixArm = new Matrix4f();
                if (isRightHanded) {
                    final boolean leftIsEmpty = pPlayer.getOffhandItem().isEmpty();
                    if (leftIsEmpty) {
                        ZPDefaultRifleWithShutterRenderer.leftArmCurrentScaling = ZPDefaultRifleWithShutterRenderer.armScaling2;
                        ZPDefaultRifleWithShutterRenderer.leftArmTranslationCurrent = ZPDefaultRifleWithShutterRenderer.leftArmTranslation1;
                        ZPDefaultRifleWithShutterRenderer.leftArmRotationCurrent = ZPDefaultRifleWithShutterRenderer.leftArmRotation1;
                    } else {
                        ZPDefaultRifleWithShutterRenderer.leftArmCurrentScaling = ZPDefaultRifleWithShutterRenderer.armScaling1;
                        ZPDefaultRifleWithShutterRenderer.leftArmTranslationCurrent = ZPDefaultRifleWithShutterRenderer.leftArmTranslation2;
                        ZPDefaultRifleWithShutterRenderer.leftArmRotationCurrent = ZPDefaultRifleWithShutterRenderer.leftArmRotation2;
                    }
                    ZPDefaultRifleWithShutterRenderer.rightArmCurrentScaling = ZPDefaultRifleWithShutterRenderer.armScaling1;
                    ZPDefaultRifleWithShutterRenderer.rightArmTranslationCurrent = ZPDefaultRifleWithShutterRenderer.rightArmTranslation2;
                    ZPDefaultRifleWithShutterRenderer.rightArmRotationCurrent = ZPDefaultRifleWithShutterRenderer.rightArmRotation2;
                    this.renderPlayerArm(pPlayer, pPartialTicks, pPoseStack, pBuffer, pCombinedLight, pEquippedProgress, pSwingProgress, true);
                    matrixArm.set(new Matrix4f(pPoseStack.last().pose()));
                    if (leftIsEmpty) {
                        this.renderPlayerArm(pPlayer, pPartialTicks, pPoseStack, pBuffer, pCombinedLight, pEquippedProgress, pSwingProgress, false);
                    }
                } else {
                    final boolean rightIsEmpty = pPlayer.getMainHandItem().isEmpty();
                    ZPDefaultRifleWithShutterRenderer.leftArmCurrentScaling = ZPDefaultRifleWithShutterRenderer.armScaling1;
                    ZPDefaultRifleWithShutterRenderer.leftArmTranslationCurrent = ZPDefaultRifleWithShutterRenderer.leftArmTranslation2;
                    ZPDefaultRifleWithShutterRenderer.leftArmRotationCurrent = ZPDefaultRifleWithShutterRenderer.leftArmRotation2;
                    this.renderPlayerArm(pPlayer, pPartialTicks, pPoseStack, pBuffer, pCombinedLight, pEquippedProgress, pSwingProgress, false);
                    matrixArm.set(new Matrix4f(pPoseStack.last().pose()));
                }

                final @Nullable Matrix4f muzzleflashTransformationTarget = new Matrix4f().identity();
                final @NotNull Matrix4f reloadingGunTarget = new Matrix4f().identity();
                final @NotNull Matrix4f reloadingArmTarget = new Matrix4f().identity();

                {
                    final Vector3f mflashTranslation = Objects.requireNonNull(isRightHanded ? this.gunTransforms().translationMuzzleflash1PRight() : this.gunTransforms().translationMuzzleflash1PLeft());
                    mflashTranslation.add(DearUITRSInterface.trsMFlash.position);

                    final Vector3f mflashRotation = new Vector3f(0.0f);
                    mflashRotation.add(0.0f, 180.0f, 0.0f);
                    mflashRotation.add(DearUITRSInterface.trsMFlash.rotation);

                    final Vector3f mflashScale = new Vector3f(Objects.requireNonNull(this.gunTransforms().muzzleflashScale()), Objects.requireNonNull(this.gunTransforms().muzzleflashScale()), 1.0f);
                    mflashScale.add(new Vector3f(DearUITRSInterface.trsMFlash.scale).sub(new Vector3f(1.0f)));

                    if (muzzleflashTransformationTarget != null) {
                        muzzleflashTransformationTarget
                                .translate(mflashTranslation)
                                .rotateX((float) Math.toRadians(mflashRotation.x))
                                .rotateY((float) Math.toRadians(mflashRotation.y))
                                .rotateZ((float) Math.toRadians(mflashRotation.z))
                                .scale(mflashScale);
                    }

                    final Vector3f reloadingGunTranslation = Objects.requireNonNull(isRightHanded ? this.gunTransforms().translationGunReloadingRight() : this.gunTransforms().translationGunReloadingLeft());
                    reloadingGunTranslation.add(DearUITRSInterface.trsReloadingGun.position);
                    final Vector3f reloadingGunRotation = new Vector3f(0.0f);
                    reloadingGunRotation.add(isRightHanded ? this.gunTransforms().rotationGunReloadingRight() : this.gunTransforms().rotationGunReloadingLeft());
                    reloadingGunRotation.add(DearUITRSInterface.trsReloadingGun.rotation);

                    final Vector3f reloadingArmTranslation = Objects.requireNonNull(isRightHanded ? this.gunTransforms().translationArmReloadingRight() : this.gunTransforms().translationArmReloadingLeft());
                    reloadingArmTranslation.add(DearUITRSInterface.trsReloadingArm.position);
                    final Vector3f reloadingArmRotation = new Vector3f(0.0f);
                    reloadingArmRotation.add(isRightHanded ? this.gunTransforms().rotationArmReloadingRight() : this.gunTransforms().rotationArmReloadingLeft());
                    reloadingArmRotation.add(DearUITRSInterface.trsReloadingArm.rotation);

                    reloadingGunTarget
                            .translate(reloadingGunTranslation)
                            .rotateX((float) Math.toRadians((reloadingGunRotation).x))
                            .rotateY((float) Math.toRadians((reloadingGunRotation).y))
                            .rotateZ((float) Math.toRadians((reloadingGunRotation).z));

                    reloadingArmTarget
                            .translate(reloadingArmTranslation)
                            .rotateX((float) Math.toRadians((reloadingArmRotation).x))
                            .rotateY((float) Math.toRadians((reloadingArmRotation).y))
                            .rotateZ((float) Math.toRadians((reloadingArmRotation).z))
                            .scale(0.8f);
                }

                ZPGunFXGlobalData.getGunData(isRightHanded)
                        .setCurrentGunItemMatrix(matrixGun)
                        .setCurrentArmMatrix(matrixArm)
                        .setArmReloadingTransformationTarget(reloadingArmTarget)
                        .setGunReloadingTransformationTarget(reloadingGunTarget)
                        .setMflash1spTransformationTarget(muzzleflashTransformationTarget);

                pPoseStack.popPose();
            }
        } catch (NullPointerException e) {
            throw new ZPNullException(e);
        }
    }
 */