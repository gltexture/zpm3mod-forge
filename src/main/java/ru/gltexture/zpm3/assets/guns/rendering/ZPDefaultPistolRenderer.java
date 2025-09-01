package ru.gltexture.zpm3.assets.guns.rendering;

import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import com.mojang.math.Transformation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.util.Mth;
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
import ru.gltexture.zpm3.assets.guns.rendering.basic.ZPDefaultGunMuzzleflashFX;
import ru.gltexture.zpm3.assets.guns.rendering.fx.ZPGunFXGlobalData;

import java.util.Objects;

public class ZPDefaultPistolRenderer extends ZPAbstractGunRenderer {
    private static final Vector3f translationGunRight = new Vector3f(0.245f, -0.05f, -1.15f);
    private static final Vector3f rotationGunRight = new Vector3f(-10.0f, 10.0f, 1.5f);
    private static final Vector3f translationArmRight = new Vector3f(-0.22f, -0.39f, 0.71f);
    private static final Vector3f rotationArmRight = new Vector3f(-57.0f, 173.1f, -6.0f);

    private static final Vector3f translationGunLeft = new Vector3f(-0.44f, 0.18f, -0.96f);
    private static final Vector3f rotationGunLeft = new Vector3f(-61.8f, 188.6f, 9.0f);
    private static final Vector3f translationArmLeft = new Vector3f(-0.358f, -0.67f, 0.2f);
    private static final Vector3f rotationArmLeft = new Vector3f(-2.7f, 1.63f, 7.0f);

    private static final Vector3f translationMuzzleflash3PRight = new Vector3f(-0.17f, 0.44f, 0.05f);
    private static final Vector3f translationMuzzleflash3PLeft = translationMuzzleflash3PRight;

    private static final Vector3f translationMuzzleflash1PRight = new Vector3f(0.06f, 0.41f, -0.23f);
    private static final Vector3f translationMuzzleflash1PLeft = new Vector3f(-0.08f, 0.57f, 0.1f);
    private static final float muzzleflashScale = 0.75f;

    private static final Vector3f translationGunReloadingRight = new Vector3f(0.0f, -0.15f, 0.2f);
    private static final Vector3f rotationGunReloadingRight = new Vector3f(0.0f, 24.0f, -24.0f);

    private static final Vector3f translationGunReloadingLeft = new Vector3f(0.02f, -0.25f, 0.17f);
    private static final Vector3f rotationGunReloadingLeft = new Vector3f(5.0f, -75.0f, 4.0f);

    private static final Vector3f translationArmReloadingRight = new Vector3f(0.0f, 0.0f, 0.0f);
    private static final Vector3f rotationArmReloadingRight = new Vector3f(0.0f, 0.0f, 0.0f);

    private static final Vector3f translationArmReloadingLeft = new Vector3f(0.0f, 0.0f, 0.0f);
    private static final Vector3f rotationArmReloadingLeft = new Vector3f(0.0f, 0.0f, 0.0f);

    protected ZPDefaultPistolRenderer() {
        super();
    }

    static ZPDefaultPistolRenderer create() {
        return new ZPDefaultPistolRenderer();
    }

    @Override
    public void onRenderItem3Person(@NotNull ItemInHandRenderer itemInHandRenderer, float deltaTicks, @NotNull EntityModel<?> entityModel, LivingEntity pLivingEntity, ItemStack pItemStack, ItemDisplayContext pDisplayContext, HumanoidArm pArm, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        pPoseStack.pushPose();
        ((ArmedModel) entityModel).translateToHand(pArm, pPoseStack);
        pPoseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
        pPoseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
        boolean flag = pArm == HumanoidArm.LEFT;
        pPoseStack.translate((float) (flag ? -1 : 1) / 16.0F, 0.125F, -0.625F);

        final Matrix4f transformation = new Matrix4f().identity();

        final Vector3f startTranslation = new Vector3f(0.04f, -0.13f, 0.09f);
        startTranslation.add(DearUITRSInterface.trsGun3d.position);

        final Vector3f startRotation = new Vector3f(0.0f, -90.0f, 45.0f);
        startRotation.add(DearUITRSInterface.trsGun3d.rotation);

        final Vector3f startScale = new Vector3f(DearUITRSInterface.trsGun3d.scale);

        transformation
                .translate(startTranslation)
                .rotateX((float) Math.toRadians(startRotation.x))
                .rotateY((float) Math.toRadians(startRotation.y))
                .rotateZ((float) Math.toRadians(startRotation.z))
                .scale(startScale);
        pPoseStack.mulPoseMatrix(transformation);
        final Matrix4f matrix = new Matrix4f(pPoseStack.last().pose());
        boolean right = !flag;

        if (ZPDefaultGunMuzzleflashFX.useFancyRendering3person()) {
            int ref = flag ? 0x01 : 0x02;
            GL46.glEnable(GL46.GL_STENCIL_TEST);
            GL46.glStencilFunc(GL46.GL_ALWAYS, ref, 0xFF);
            GL46.glStencilOp(GL46.GL_REPLACE, GL46.GL_KEEP, GL46.GL_REPLACE);
            GL46.glDrawBuffers(new int[]{GL46.GL_COLOR_ATTACHMENT0});
            MultiBufferSource.BufferSource bufferSource = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());
            itemInHandRenderer.renderItem(pLivingEntity, pItemStack, right ? ItemDisplayContext.THIRD_PERSON_RIGHT_HAND : ItemDisplayContext.THIRD_PERSON_LEFT_HAND, right, pPoseStack, bufferSource, pPackedLight);
            bufferSource.endBatch();
        } else {
            itemInHandRenderer.renderItem(pLivingEntity, pItemStack, right ? ItemDisplayContext.THIRD_PERSON_RIGHT_HAND : ItemDisplayContext.THIRD_PERSON_LEFT_HAND, right, pPoseStack, pBuffer, pPackedLight);
        }
        matrix.translate(right ? ZPDefaultPistolRenderer.translationMuzzleflash3PRight : ZPDefaultPistolRenderer.translationMuzzleflash3PLeft);
        matrix.scale(ZPDefaultPistolRenderer.muzzleflashScale);
        ZPGunFXGlobalData.getGunData(right).setMflash3dpTransformationTarget(matrix);
        if (ZPDefaultGunMuzzleflashFX.useFancyRendering3person()) {
            GL46.glDisable(GL46.GL_STENCIL_TEST);
        }

        pPoseStack.popPose();
    }

    @Override
    public void onRenderItem1Person(AbstractClientPlayer pPlayer, float deltaTicks, float pPartialTicks, float pPitch, InteractionHand pHand, float pSwingProgress, ItemStack pStack, float pEquippedProgress, PoseStack pPoseStack, MultiBufferSource pBuffer, int pCombinedLight) {
        pPoseStack.pushPose();
        final boolean isRightHanded = pHand == InteractionHand.MAIN_HAND;
        final float equippedConst = -0.6F + pEquippedProgress * -0.6F;
        final Matrix4f transformation = new Matrix4f().identity();

        final Vector3f startTranslation = new Vector3f(isRightHanded ? ZPDefaultPistolRenderer.translationGunRight : ZPDefaultPistolRenderer.translationGunLeft);
        startTranslation.add(0.0f, equippedConst, 0.0f);
        startTranslation.add(DearUITRSInterface.trsGun.position);

        final Vector3f startRotation = new Vector3f(isRightHanded ? ZPDefaultPistolRenderer.rotationGunRight : ZPDefaultPistolRenderer.rotationGunLeft);
        startRotation.add(DearUITRSInterface.trsGun.rotation);

        pPoseStack.setIdentity();
        this.translateStack(pPoseStack, pPartialTicks);
        transformation
                .translate(startTranslation)
                .rotateX((float) Math.toRadians(startRotation.x))
                .rotateY((float) Math.toRadians(startRotation.y))
                .rotateZ((float) Math.toRadians(startRotation.z))
                .scale(new Vector3f(DearUITRSInterface.trsGun.scale));
        pPoseStack.pushTransformation(new Transformation(transformation));

        @Nullable Matrix4f reloading = ZPDefaultGunRenderers.defaultReloadingFXUniversal.getCurrentGunReloadingTransformation(isRightHanded, pPartialTicks);
        if (reloading != null) {
            pPoseStack.pushTransformation(new Transformation(reloading));
        }

        final Matrix4f matrixGun = new Matrix4f(pPoseStack.last().pose());
        @Nullable Matrix4f recoil = ZPDefaultGunRenderers.defaultRecoilFXUniversal.getCurrentRecoilTransformation(isRightHanded, pPartialTicks);
        if (recoil != null) {
            pPoseStack.pushTransformation(new Transformation(recoil));
        }

        this.renderItem(Minecraft.getInstance().getItemRenderer(), pPlayer, pStack, isRightHanded ? ItemDisplayContext.FIRST_PERSON_RIGHT_HAND : ItemDisplayContext.FIRST_PERSON_LEFT_HAND, !isRightHanded, pPoseStack, pBuffer, pCombinedLight);
        this.renderPlayerArm(pPlayer, pPartialTicks, pPoseStack, pBuffer, pCombinedLight, pEquippedProgress, pSwingProgress, isRightHanded);
        final Matrix4f matrixArm = new Matrix4f(pPoseStack.last().pose());

        final @NotNull Matrix4f muzzleflashTransformationTarget = new Matrix4f().identity();
        final @NotNull Matrix4f reloadingGunTarget = new Matrix4f().identity();
        final @NotNull Matrix4f reloadingArmTarget = new Matrix4f().identity();

        {
            final Vector3f mflashTranslation = new Vector3f(isRightHanded ? ZPDefaultPistolRenderer.translationMuzzleflash1PRight : ZPDefaultPistolRenderer.translationMuzzleflash1PLeft);
            mflashTranslation.add(DearUITRSInterface.trsMFlash.position);

            final Vector3f mflashRotation = new Vector3f(0.0f);
            mflashRotation.add(0.0f, 180.0f, 0.0f);
            mflashRotation.add(DearUITRSInterface.trsMFlash.rotation);

            final Vector3f mflashScale = new Vector3f(ZPDefaultPistolRenderer.muzzleflashScale, ZPDefaultPistolRenderer.muzzleflashScale, 1.0f);
            mflashScale.add(new Vector3f(DearUITRSInterface.trsMFlash.scale).sub(new Vector3f(1.0f)));

            muzzleflashTransformationTarget
                    .translate(mflashTranslation)
                    .rotateX((float) Math.toRadians(mflashRotation.x))
                    .rotateY((float) Math.toRadians(mflashRotation.y))
                    .rotateZ((float) Math.toRadians(mflashRotation.z))
                    .scale(mflashScale);

            final Vector3f reloadingGunTranslation = new Vector3f(isRightHanded ? ZPDefaultPistolRenderer.translationGunReloadingRight : ZPDefaultPistolRenderer.translationGunReloadingLeft);
            reloadingGunTranslation.add(DearUITRSInterface.trsReloadingGun.position);
            final Vector3f reloadingGunRotation = new Vector3f(0.0f);
            reloadingGunRotation.add(isRightHanded ? ZPDefaultPistolRenderer.rotationGunReloadingRight : ZPDefaultPistolRenderer.rotationGunReloadingLeft);
            reloadingGunRotation.add(DearUITRSInterface.trsReloadingGun.rotation);

            final Vector3f reloadingArmTranslation = new Vector3f(isRightHanded ? ZPDefaultPistolRenderer.translationArmReloadingRight : ZPDefaultPistolRenderer.translationArmReloadingLeft);
            reloadingArmTranslation.add(DearUITRSInterface.trsReloadingArm.position);
            final Vector3f reloadingArmRotation = new Vector3f(0.0f);
            reloadingArmRotation.add(isRightHanded ? ZPDefaultPistolRenderer.rotationArmReloadingRight : ZPDefaultPistolRenderer.rotationArmReloadingLeft);
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

    /*
    -XX:HotswapAgent=fatjar
-XX:+AllowEnhancedClassRedefinition
     */

    private void translateStack(PoseStack pPoseStack, float pPartialTicks) {
        LocalPlayer localPlayer = Objects.requireNonNull(Minecraft.getInstance().player);
        final float f2 = Mth.lerp(pPartialTicks, localPlayer.xBobO, localPlayer.xBob);
        final float f3 = Mth.lerp(pPartialTicks, localPlayer.yBobO, localPlayer.yBob);
        pPoseStack.mulPose(Axis.XP.rotationDegrees((localPlayer.getViewXRot(pPartialTicks) - f2) * 0.1F));
        pPoseStack.mulPose(Axis.YP.rotationDegrees((localPlayer.getViewYRot(pPartialTicks) - f3) * 0.1F));

        this.bobHurt(pPoseStack, pPartialTicks);
        if (Minecraft.getInstance().options.bobView().get()) {
            this.bobView(pPoseStack, pPartialTicks);
        }
    }

    private void renderPlayerArm(AbstractClientPlayer pPlayer, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pCombinedLight, float pEquippedProgress, float pSwingProgress, boolean isRightHanded) {
        final Matrix4f transformation = new Matrix4f().identity();

        final Vector3f startTranslation = new Vector3f(isRightHanded ? ZPDefaultPistolRenderer.translationArmRight : translationArmLeft);
        startTranslation.add(DearUITRSInterface.trsArm.position);

        final Vector3f startRotation = new Vector3f(isRightHanded ? ZPDefaultPistolRenderer.rotationArmRight : rotationArmLeft);
        startRotation.add(DearUITRSInterface.trsArm.rotation);

        transformation
                .translate(startTranslation)
                .rotateX((float) Math.toRadians(startRotation.x))
                .rotateY((float) Math.toRadians(startRotation.y))
                .rotateZ((float) Math.toRadians(startRotation.z))
                .scale(new Vector3f(DearUITRSInterface.trsArm.scale));

        pPoseStack.mulPoseMatrix(transformation);
        @Nullable Matrix4f reloading = ZPDefaultGunRenderers.defaultReloadingFXUniversal.getCurrentArmReloadingTransformation(isRightHanded, pPartialTicks);
        if (reloading != null) {
            pPoseStack.pushTransformation(new Transformation(reloading));
        }

        PlayerRenderer playerrenderer = (PlayerRenderer) Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(pPlayer);
        if (isRightHanded) {
            playerrenderer.renderRightHand(pPoseStack, pBuffer, pCombinedLight, pPlayer);
        } else {
            playerrenderer.renderLeftHand(pPoseStack, pBuffer, pCombinedLight, pPlayer);
        }
    }

    private void bobHurt(PoseStack pPoseStack, float pPartialTicks) {
        if (Minecraft.getInstance().getCameraEntity() instanceof LivingEntity livingentity) {
            float f = (float) livingentity.hurtTime - pPartialTicks;
            if (livingentity.isDeadOrDying()) {
                float f1 = Math.min((float) livingentity.deathTime + pPartialTicks, 20.0F);
                pPoseStack.mulPose(Axis.ZP.rotationDegrees(40.0F - 8000.0F / (f1 + 200.0F)));
            }

            if (f < 0.0F) {
                return;
            }

            f /= (float) livingentity.hurtDuration;
            f = Mth.sin(f * f * f * f * (float) Math.PI);
            float f3 = livingentity.getHurtDir();
            pPoseStack.mulPose(Axis.YP.rotationDegrees(-f3));
            float f2 = (float) ((double) (-f) * 14.0D * Minecraft.getInstance().options.damageTiltStrength().get());
            pPoseStack.mulPose(Axis.ZP.rotationDegrees(f2));
            pPoseStack.mulPose(Axis.YP.rotationDegrees(f3));
        }
    }

    private void bobView(PoseStack pPoseStack, float pPartialTicks) {
        if (Minecraft.getInstance().getCameraEntity() instanceof Player player) {
            float f = player.walkDist - player.walkDistO;
            float f1 = -(player.walkDist + f * pPartialTicks);
            float f2 = Mth.lerp(pPartialTicks, player.oBob, player.bob);
            pPoseStack.translate(Mth.sin(f1 * (float) Math.PI) * f2 * 0.5F, -Math.abs(Mth.cos(f1 * (float) Math.PI) * f2), 0.0F);
            pPoseStack.mulPose(Axis.ZP.rotationDegrees(Mth.sin(f1 * (float) Math.PI) * f2 * 3.0F));
            pPoseStack.mulPose(Axis.XP.rotationDegrees(Math.abs(Mth.cos(f1 * (float) Math.PI - 0.2F) * f2) * 5.0F));
        }
    }
}
