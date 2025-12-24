package ru.gltexture.zpm3.assets.guns.rendering;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
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
import ru.gltexture.zpm3.assets.common.global.ZPConstants;
import ru.gltexture.zpm3.assets.debug.imgui.DearUITRSInterface;
import ru.gltexture.zpm3.assets.guns.item.ZPBaseGun;
import ru.gltexture.zpm3.assets.guns.rendering.fx.ZPGunFXGlobalData;
import ru.gltexture.zpm3.assets.guns.rendering.transforms.AbstractGunTransforms;
import ru.gltexture.zpm3.engine.client.callbacking.ZPClientCallbacks;
import ru.gltexture.zpm3.engine.client.rendering.ZPRenderHelper;
import ru.gltexture.zpm3.engine.exceptions.ZPNullException;

import java.util.Objects;

public class ZPDefaultPistolRenderer extends ZPAbstractGunRenderer {
    protected ZPDefaultPistolRenderer() {
        super();
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
                return new Vector3f(1.0f);
            }

            @Override
            public Vector3f translationGunRight() {
                return new Vector3f(0.245f, -0.05f, -1.1f);
            }

            @Override
            public Vector3f rotationGunRight() {
                return new Vector3f(-6.5f, 10.0f, 1.5f);
            }

            @Override
            public Vector3f translationArmRight() {
                return new Vector3f(-0.22f, -0.37f, 0.71f);
            }

            @Override
            public Vector3f rotationArmRight() {
                return new Vector3f(-57.0f, 173.1f, -6.0f);
            }

            @Override
            public Vector3f translationGunLeft() {
                return new Vector3f(-0.44f, 0.16f, -0.91f);
            }

            @Override
            public Vector3f rotationGunLeft() {
                return new Vector3f(-58.5f, 183.5f, 9.0f);
            }

            @Override
            public Vector3f translationArmLeft() {
                return new Vector3f(-0.358f, -0.66f, 0.04f);
            }

            @Override
            public Vector3f rotationArmLeft() {
                return new Vector3f(9.87f, 1.63f, 7.0f);
            }

            @Override
            public Vector3f translationMuzzleflash3PRight() {
                return new Vector3f(-0.17f, 0.47f, 0.06f);
            }

            @Override
            public Vector3f translationMuzzleflash3PLeft() {
                return this.translationMuzzleflash3PRight();
            }

            @Override
            public Vector3f translationMuzzleflash1PRight() {
                return new Vector3f(0.04f, 0.42f, -0.23f);
            }

            @Override
            public Vector3f translationMuzzleflash1PLeft() {
                return new Vector3f(-0.09f, 0.58f, 0.1f);
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
                return new Vector3f(0.0f, 0.0f, 0.0f);
            }

            @Override
            public Vector3f rotationArmReloadingRight() {
                return new Vector3f(0.0f, 0.0f, 0.0f);
            }

            @Override
            public Vector3f translationArmReloadingLeft() {
                return new Vector3f(0.0f, 0.0f, 0.0f);
            }

            @Override
            public Vector3f rotationArmReloadingLeft() {
                return new Vector3f(0.0f, 0.0f, 0.0f);
            }

            @Override
            public Vector3f scaleArmLeft() {
                return new Vector3f(1.0f);
            }

            @Override
            public Vector3f scaleArmRight() {
                return new Vector3f(1.0f);
            }
        };
    }

    static ZPDefaultPistolRenderer create() {
        return new ZPDefaultPistolRenderer();
    }

    @Override
    public void onRenderItem3Person(@NotNull ItemInHandRenderer itemInHandRenderer, float deltaTicks, @NotNull EntityModel<?> entityModel, LivingEntity pLivingEntity, ItemStack pItemStack, ItemDisplayContext pDisplayContext, HumanoidArm pArm, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        PoseStack poseStack = new PoseStack();
        poseStack.setIdentity();
        poseStack.mulPoseMatrix(pPoseStack.last().pose());
        super.onDefaultRenderItem3Person(itemInHandRenderer, deltaTicks, entityModel, pLivingEntity, pItemStack, pDisplayContext, pArm, poseStack, pBuffer, pPackedLight);
    }

    @Override
    public void onRenderItem1Person(AbstractClientPlayer pPlayer, float deltaTicks, float pPartialTicks, float pPitch, InteractionHand pHand, float pSwingProgress, ItemStack pStack, float pEquippedProgress, PoseStack oldPoseStack, MultiBufferSource pBuffer, int pCombinedLight) {
        try {
            if (pStack.getItem() instanceof ZPBaseGun baseGun) {
                PoseStack pPoseStack = new PoseStack();
                pPoseStack.pushPose();
                final boolean isRightHanded = pHand == InteractionHand.MAIN_HAND;
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
                        .scale(Objects.requireNonNull(this.gunTransforms().scalingGun1P()).add(new Vector3f(DearUITRSInterface.trsGun.scale).sub(new Vector3f(1.0f))));
                pPoseStack.pushTransformation(new Transformation(transformation));

                if (ZPConstants.FIRST_PERSON_RENDER_SPACE_SCALE_BY_FOV) {
                    double f1 = ZPRenderHelper.fovItemOffset(Minecraft.getInstance().gameRenderer.getMainCamera(), pPartialTicks, pPoseStack);
                    pPoseStack.translate(0.0f, f1 * -0.0625f, f1 * 0.25f);
                }

                @Nullable Matrix4f reloading = ZPDefaultGunRenderers.defaultReloadingFXUniversal.getCurrentGunReloadingTransformation(isRightHanded, pPartialTicks);
                if (reloading != null) {
                    pPoseStack.pushTransformation(new Transformation(reloading));
                }

                final Matrix4f matrixGun = new Matrix4f(pPoseStack.last().pose());
                @Nullable Matrix4f recoil = ZPDefaultGunRenderers.defaultRecoilFXUniversal.getCurrentRecoilTransformation(pPlayer, baseGun, pStack, isRightHanded, pPartialTicks);
                if (recoil != null) {
                    pPoseStack.pushTransformation(new Transformation(recoil));
                }

                RenderSystem.backupProjectionMatrix();
               // RenderSystem.setProjectionMatrix(new Matrix4f().identity(), VertexSorting.ORTHOGRAPHIC_Z);
                this.renderItem(Minecraft.getInstance().getItemRenderer(), pPlayer, pStack, isRightHanded ? ItemDisplayContext.FIRST_PERSON_RIGHT_HAND : ItemDisplayContext.FIRST_PERSON_LEFT_HAND, !isRightHanded, pPoseStack, pBuffer, pCombinedLight);
                RenderSystem.restoreProjectionMatrix();
                this.renderPlayerArm(pPlayer, pPartialTicks, pPoseStack, pBuffer, pCombinedLight, pEquippedProgress, pSwingProgress, isRightHanded);
                final Matrix4f matrixArm = new Matrix4f(pPoseStack.last().pose());

                final @NotNull Matrix4f muzzleflashTransformationTarget = new Matrix4f().identity();
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

                    muzzleflashTransformationTarget
                            .translate(mflashTranslation)
                            .rotateX((float) Math.toRadians(mflashRotation.x))
                            .rotateY((float) Math.toRadians(mflashRotation.y))
                            .rotateZ((float) Math.toRadians(mflashRotation.z))
                            .scale(mflashScale);

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
                            .rotateZ((float) Math.toRadians((reloadingArmRotation).z));
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
