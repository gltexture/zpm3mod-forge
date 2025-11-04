package ru.gltexture.zpm3.assets.guns.rendering;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Transformation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
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
import org.lwjgl.opengl.GL46;
import ru.gltexture.zpm3.assets.debug.imgui.DearUITRSInterface;
import ru.gltexture.zpm3.assets.guns.events.ZPGunPostRender;
import ru.gltexture.zpm3.assets.guns.item.ZPBaseGun;
import ru.gltexture.zpm3.assets.guns.rendering.fx.ZPGunFXGlobalData;
import ru.gltexture.zpm3.assets.guns.rendering.transforms.AbstractGunTransforms;
import ru.gltexture.zpm3.engine.client.callbacking.ZPClientCallbacks;
import ru.gltexture.zpm3.engine.exceptions.ZPNullException;
import ru.gltexture.zpm3.engine.service.Pair;

import java.util.Objects;

public class ZPDefaultRifleRenderer extends ZPAbstractGunRenderer {
    public static final float armScaling1 = 0.5f; // EMPTY
    public static final float armScaling2 = 1.25f; // NOT EMPTY

    public static final Vector3f leftArmTranslation1 = new Vector3f(-1.17f, 0.55f, -0.21f); //EMPTY
    public static final Vector3f leftArmRotation1 = new Vector3f(10.17f, 14.05f, -10.11f);
    public static final Vector3f leftArmTranslation2 = new Vector3f(-0.2f, -0.33f, 0.12f); //NOT EMPTY
    public static final Vector3f leftArmRotation2 = new Vector3f(-1.31f, -4.56f, 12.0f);

    public static final Vector3f rightArmTranslation2 = new Vector3f(0.22f, -0.12f, 0.48f); // NOT EMPTY
    public static final Vector3f rightArmRotation2 = new Vector3f(-60.0f, 0.0f, -9.0f);

    public static Vector3f leftArmTranslationCurrent = new Vector3f(0.0f);
    public static Vector3f leftArmRotationCurrent = new Vector3f(0.0f);
    public static Vector3f rightArmTranslationCurrent = new Vector3f(0.0f);
    public static Vector3f rightArmRotationCurrent = new Vector3f(0.0f);
    public static float leftArmCurrentScaling = 0.0f;
    public static float rightArmCurrentScaling = 0.0f;

    public ZPDefaultRifleRenderer() {
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

    static ZPDefaultRifleRenderer create() {
        return new ZPDefaultRifleRenderer();
    }

    @Override
    public void onRenderItem3Person(@NotNull ItemInHandRenderer itemInHandRenderer, float deltaTicks, @NotNull EntityModel<?> entityModel, LivingEntity pLivingEntity, ItemStack pItemStack, ItemDisplayContext pDisplayContext, HumanoidArm pArm, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        PoseStack poseStack = new PoseStack();
        poseStack.setIdentity();
        poseStack.mulPoseMatrix(pPoseStack.last().pose());
        super.onDefaultRenderItem3Person(itemInHandRenderer, deltaTicks, entityModel, pLivingEntity, pItemStack, pDisplayContext, pArm, poseStack, pBuffer, pPackedLight);
    }

    @Override
    public void onRenderItem1Person(AbstractClientPlayer pPlayer, float deltaTicks, float pPartialTicks, float pPitch, InteractionHand pHand, float pSwingProgress, ItemStack pStack, float pEquippedProgress, PoseStack pPoseStack, MultiBufferSource pBuffer, int pCombinedLight) {
        ZPDefaultRifleRenderer.onRenderItem1PersonRifle(this, pPlayer, deltaTicks, pPartialTicks, pPitch, pHand, pSwingProgress, pStack, pEquippedProgress, pPoseStack, pBuffer, pCombinedLight);
    }

    public static void onRenderItem1PersonRifle(ZPAbstractGunRenderer abstractGunRenderer, AbstractClientPlayer pPlayer, float deltaTicks, float pPartialTicks, float pPitch, InteractionHand pHand, float pSwingProgress, ItemStack pStack, float pEquippedProgress, PoseStack pPoseStack, MultiBufferSource pBuffer, int pCombinedLight) {
        try {
            final boolean isRightHanded = pHand == InteractionHand.MAIN_HAND;
            if (pStack.getItem() instanceof ZPBaseGun baseGun) {
                pPoseStack.pushPose();
                final float equippedConst = -0.6F + pEquippedProgress * -0.6F;
                final Matrix4f transformation = new Matrix4f().identity();

                final Vector3f startTranslation = Objects.requireNonNull(isRightHanded ? abstractGunRenderer.gunTransforms().translationGunRight() : abstractGunRenderer.gunTransforms().translationGunLeft());
                startTranslation.add(0.0f, equippedConst, 0.0f);
                startTranslation.add(DearUITRSInterface.trsGun.position);

                final Vector3f startRotation = Objects.requireNonNull(isRightHanded ? abstractGunRenderer.gunTransforms().rotationGunRight() : abstractGunRenderer.gunTransforms().rotationGunLeft());
                startRotation.add(DearUITRSInterface.trsGun.rotation);

                pPoseStack = new PoseStack();
                abstractGunRenderer.translateStack(pPoseStack, pPartialTicks);
                transformation
                        .translate(startTranslation)
                        .rotateX((float) Math.toRadians(startRotation.x))
                        .rotateY((float) Math.toRadians(startRotation.y))
                        .rotateZ((float) Math.toRadians(startRotation.z))
                        .scale(Objects.requireNonNull(abstractGunRenderer.gunTransforms().scalingGun1P()));
                pPoseStack.pushTransformation(new Transformation(transformation));

                @Nullable Matrix4f reloading = ZPDefaultGunRenderers.defaultReloadingFXUniversal.getCurrentGunReloadingTransformation(isRightHanded, pPartialTicks);
                if (reloading != null) {
                    pPoseStack.pushTransformation(new Transformation(reloading));
                }

                @Nullable Pair<Matrix4f, Matrix4f> shutter = null;
                if (baseGun.getGunProperties().getAnimationData().getShutterAnimationType() != null) {
                    shutter = ZPDefaultGunRenderers.defaultShotgunShutterFXUniversal.getCurrentShutterTransformationGunArm(pPlayer, baseGun, pStack, isRightHanded, deltaTicks);
                    if (shutter != null) {
                        pPoseStack.pushTransformation(new Transformation(shutter.first()));
                    }
                }
                final Matrix4f matrixGun = new Matrix4f(pPoseStack.last().pose());
                @Nullable Matrix4f recoil = ZPDefaultGunRenderers.defaultRecoilFXUniversal.getCurrentRecoilTransformation(pPlayer, baseGun, pStack, isRightHanded, pPartialTicks);
                if (recoil != null) {
                    pPoseStack.pushTransformation(new Transformation(recoil));
                }

                abstractGunRenderer.renderItem(Minecraft.getInstance().getItemRenderer(), pPlayer, pStack, isRightHanded ? ItemDisplayContext.FIRST_PERSON_RIGHT_HAND : ItemDisplayContext.FIRST_PERSON_LEFT_HAND, !isRightHanded, pPoseStack, pBuffer, pCombinedLight);
                if (shutter != null) {
                    pPoseStack.pushTransformation(new Transformation(shutter.second()));
                }
                final Matrix4f matrixArm = new Matrix4f();
                if (isRightHanded) {
                    final boolean leftIsEmpty = pPlayer.getOffhandItem().isEmpty();
                    if (leftIsEmpty) {
                        ZPDefaultRifleRenderer.leftArmCurrentScaling = ZPDefaultRifleRenderer.armScaling2;
                        ZPDefaultRifleRenderer.leftArmTranslationCurrent = ZPDefaultRifleRenderer.leftArmTranslation1;
                        ZPDefaultRifleRenderer.leftArmRotationCurrent = ZPDefaultRifleRenderer.leftArmRotation1;
                    } else {
                        ZPDefaultRifleRenderer.leftArmCurrentScaling = ZPDefaultRifleRenderer.armScaling1;
                        ZPDefaultRifleRenderer.leftArmTranslationCurrent = ZPDefaultRifleRenderer.leftArmTranslation2;
                        ZPDefaultRifleRenderer.leftArmRotationCurrent = ZPDefaultRifleRenderer.leftArmRotation2;
                    }
                    ZPDefaultRifleRenderer.rightArmCurrentScaling = ZPDefaultRifleRenderer.armScaling1;
                    ZPDefaultRifleRenderer.rightArmTranslationCurrent = ZPDefaultRifleRenderer.rightArmTranslation2;
                    ZPDefaultRifleRenderer.rightArmRotationCurrent = ZPDefaultRifleRenderer.rightArmRotation2;
                    abstractGunRenderer.renderPlayerArm(pPlayer, pPartialTicks, pPoseStack, pBuffer, pCombinedLight, pEquippedProgress, pSwingProgress, true);
                    matrixArm.set(new Matrix4f(pPoseStack.last().pose()));
                    if (leftIsEmpty) {
                        abstractGunRenderer.renderPlayerArm(pPlayer, pPartialTicks, pPoseStack, pBuffer, pCombinedLight, pEquippedProgress, pSwingProgress, false);
                    }
                } else {
                    final boolean rightIsEmpty = pPlayer.getMainHandItem().isEmpty();
                    ZPDefaultRifleRenderer.leftArmCurrentScaling = ZPDefaultRifleRenderer.armScaling1;
                    ZPDefaultRifleRenderer.leftArmTranslationCurrent = ZPDefaultRifleRenderer.leftArmTranslation2;
                    ZPDefaultRifleRenderer.leftArmRotationCurrent = ZPDefaultRifleRenderer.leftArmRotation2;
                    abstractGunRenderer.renderPlayerArm(pPlayer, pPartialTicks, pPoseStack, pBuffer, pCombinedLight, pEquippedProgress, pSwingProgress, false);
                    matrixArm.set(new Matrix4f(pPoseStack.last().pose()));
                }

                final @Nullable Matrix4f muzzleflashTransformationTarget = new Matrix4f().identity();
                final @NotNull Matrix4f reloadingGunTarget = new Matrix4f().identity();
                final @NotNull Matrix4f reloadingArmTarget = new Matrix4f().identity();

                {
                    final Vector3f mflashTranslation = Objects.requireNonNull(isRightHanded ? abstractGunRenderer.gunTransforms().translationMuzzleflash1PRight() : abstractGunRenderer.gunTransforms().translationMuzzleflash1PLeft());
                    mflashTranslation.add(DearUITRSInterface.trsMFlash.position);

                    final Vector3f mflashRotation = new Vector3f(0.0f);
                    mflashRotation.add(0.0f, 180.0f, 0.0f);
                    mflashRotation.add(DearUITRSInterface.trsMFlash.rotation);

                    final Vector3f mflashScale = new Vector3f(Objects.requireNonNull(abstractGunRenderer.gunTransforms().muzzleflashScale()), Objects.requireNonNull(abstractGunRenderer.gunTransforms().muzzleflashScale()), 1.0f);
                    mflashScale.add(new Vector3f(DearUITRSInterface.trsMFlash.scale).sub(new Vector3f(1.0f)));

                    if (muzzleflashTransformationTarget != null) {
                        muzzleflashTransformationTarget
                                .translate(mflashTranslation)
                                .rotateX((float) Math.toRadians(mflashRotation.x))
                                .rotateY((float) Math.toRadians(mflashRotation.y))
                                .rotateZ((float) Math.toRadians(mflashRotation.z))
                                .scale(mflashScale);
                    }

                    final Vector3f reloadingGunTranslation = Objects.requireNonNull(isRightHanded ? abstractGunRenderer.gunTransforms().translationGunReloadingRight() : abstractGunRenderer.gunTransforms().translationGunReloadingLeft());
                    reloadingGunTranslation.add(DearUITRSInterface.trsReloadingGun.position);
                    final Vector3f reloadingGunRotation = new Vector3f(0.0f);
                    reloadingGunRotation.add(isRightHanded ? abstractGunRenderer.gunTransforms().rotationGunReloadingRight() : abstractGunRenderer.gunTransforms().rotationGunReloadingLeft());
                    reloadingGunRotation.add(DearUITRSInterface.trsReloadingGun.rotation);

                    final Vector3f reloadingArmTranslation = Objects.requireNonNull(isRightHanded ? abstractGunRenderer.gunTransforms().translationArmReloadingRight() : abstractGunRenderer.gunTransforms().translationArmReloadingLeft());
                    reloadingArmTranslation.add(DearUITRSInterface.trsReloadingArm.position);
                    final Vector3f reloadingArmRotation = new Vector3f(0.0f);
                    reloadingArmRotation.add(isRightHanded ? abstractGunRenderer.gunTransforms().rotationArmReloadingRight() : abstractGunRenderer.gunTransforms().rotationArmReloadingLeft());
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

    @Override
    public void onReloadStart(@NotNull Player player, @NotNull ZPBaseGun baseGun, @NotNull ItemStack itemStack, ZPClientCallbacks.ZPGunReloadStartCallback.@NotNull GunFXData gunFXData) {

    }

    @Override
    public void onShot(@NotNull Player player, @NotNull ZPBaseGun baseGun, @NotNull ItemStack itemStack, ZPClientCallbacks.ZPGunShotCallback.@NotNull GunFXData gunFXData) {
    }
}