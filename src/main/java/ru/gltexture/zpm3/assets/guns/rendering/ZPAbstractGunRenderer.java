package ru.gltexture.zpm3.assets.guns.rendering;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.math.Axis;
import com.mojang.math.Transformation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL46;
import ru.gltexture.zpm3.assets.debug.imgui.DearUITRSInterface;
import ru.gltexture.zpm3.assets.guns.ZPGunsAsset;
import ru.gltexture.zpm3.assets.guns.rendering.basic.ZPDefaultGunMuzzleflashFX;
import ru.gltexture.zpm3.assets.guns.rendering.fx.ZPGunFXGlobalData;
import ru.gltexture.zpm3.assets.guns.rendering.transforms.AbstractGunTransforms;
import ru.gltexture.zpm3.engine.client.callbacking.ZPClientCallbacks;
import ru.gltexture.zpm3.engine.client.rendering.hooks.ZPRenderHooks;
import ru.gltexture.zpm3.engine.exceptions.ZPNullException;

import java.util.Objects;

public abstract class ZPAbstractGunRenderer implements ZPRenderHooks.ZPItemRendering1PersonHook, ZPRenderHooks.ZPItemRendering3PersonHook, ZPClientCallbacks.ZPClientTickCallback, ZPClientCallbacks.ZPGunShotCallback, ZPClientCallbacks.ZPGunReloadStartCallback, ZPClientCallbacks.ZPClientResourceDependentObject {
    protected ZPAbstractGunRenderer() {
    }

    protected void renderItem(ItemRenderer itemRenderer, LivingEntity pEntity, ItemStack pItemStack, ItemDisplayContext pDisplayContext, boolean pLeftHand, PoseStack pPoseStack, MultiBufferSource pBuffer, int pSeed) {
        if (!pItemStack.isEmpty()) {
            pPoseStack.pushPose();
            itemRenderer.renderStatic(pEntity, pItemStack, pDisplayContext, pLeftHand, pPoseStack, pBuffer, pEntity.level(), pSeed, OverlayTexture.NO_OVERLAY, pEntity.getId() + pDisplayContext.ordinal());
            pPoseStack.popPose();
        }
    }

    protected abstract AbstractGunTransforms gunTransforms();

    protected void onDefaultRenderItem3Person(@NotNull ItemInHandRenderer itemInHandRenderer, float deltaTicks, @NotNull EntityModel<?> entityModel, LivingEntity pLivingEntity, ItemStack pItemStack, ItemDisplayContext pDisplayContext, HumanoidArm pArm, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        try {
            pPoseStack.pushPose();
            ((ArmedModel) entityModel).translateToHand(pArm, pPoseStack);
            pPoseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
            pPoseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
            boolean flag = pArm == HumanoidArm.LEFT;
            pPoseStack.translate(flag ? 0.04f : -0.04f, 0.125F, -0.625F);

            final Matrix4f transformation = new Matrix4f().identity();

            final Vector3f startTranslation = new Vector3f(!flag ? (0.12f * Objects.requireNonNull(this.gunTransforms().scalingGun3P()).x) : 0.0f, -0.13f, 0.09f);
            startTranslation.add(DearUITRSInterface.trsGun3d.position);

            final Vector3f startRotation = new Vector3f(0.0f, -90.0f, 45.0f);
            startRotation.add(DearUITRSInterface.trsGun3d.rotation);

            final Vector3f startScale = Objects.requireNonNull(this.gunTransforms().scalingGun3P()).add(new Vector3f(DearUITRSInterface.trsGun3d.scale).sub(new Vector3f(1.0f)));

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
                MultiBufferSource.BufferSource bufferSource = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());
                itemInHandRenderer.renderItem(pLivingEntity, pItemStack, right ? ItemDisplayContext.THIRD_PERSON_RIGHT_HAND : ItemDisplayContext.THIRD_PERSON_LEFT_HAND, right, pPoseStack, bufferSource, pPackedLight);
                bufferSource.endBatch();
            } else {
                itemInHandRenderer.renderItem(pLivingEntity, pItemStack, right ? ItemDisplayContext.THIRD_PERSON_RIGHT_HAND : ItemDisplayContext.THIRD_PERSON_LEFT_HAND, right, pPoseStack, pBuffer, pPackedLight);
            }
            matrix.translate(right ? this.gunTransforms().translationMuzzleflash3PRight() : this.gunTransforms().translationMuzzleflash3PLeft());
            matrix.scale(Objects.requireNonNull(this.gunTransforms().muzzleflashScale()));
            ZPGunFXGlobalData.getGunData(right).setMflash3dpTransformationTarget(matrix);
            if (ZPDefaultGunMuzzleflashFX.useFancyRendering3person()) {
                GL46.glDisable(GL46.GL_STENCIL_TEST);
            }

            pPoseStack.popPose();
        } catch (NullPointerException e) {
            throw new ZPNullException(e);
        }
    }


    protected void translateStack(PoseStack pPoseStack, float pPartialTicks) {
        if (Minecraft.getInstance().player != null) {
            ZPAbstractGunRenderer.breathEffect(pPartialTicks, pPoseStack);
        }

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

    @OnlyIn(Dist.CLIENT)
    public static void breathEffect(float partialTicks, @NotNull PoseStack poseStack) {
        Player player = Minecraft.getInstance().player;
        if (player != null) {
            float f = player.tickCount + partialTicks;
            float f2 = Mth.cos(f * 0.05f) * 0.15f;
            poseStack.mulPose(Axis.XP.rotationDegrees((float) (Math.PI * f2)));
            poseStack.mulPose(Axis.YP.rotationDegrees((float) (Math.PI * f2 * 0.3f)));
        }
    }

    protected void renderPlayerArm(AbstractClientPlayer pPlayer, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pCombinedLight, float pEquippedProgress, float pSwingProgress, boolean isRightHanded) {
        try {
            pPoseStack.pushPose();
            final Matrix4f transformation = new Matrix4f().identity();

            final Vector3f startTranslation = new Vector3f(Objects.requireNonNull(isRightHanded ? this.gunTransforms().translationArmRight() : this.gunTransforms().translationArmLeft()));
            startTranslation.add(DearUITRSInterface.trsArm.position);

            final Vector3f startRotation = new Vector3f(Objects.requireNonNull(isRightHanded ? this.gunTransforms().rotationArmRight() : this.gunTransforms().rotationArmLeft()));
            startRotation.add(DearUITRSInterface.trsArm.rotation);

            final Vector3f startScale = new Vector3f(Objects.requireNonNull(isRightHanded ? this.gunTransforms().scaleArmRight() : this.gunTransforms().scaleArmLeft()));
            startScale.add(new Vector3f(DearUITRSInterface.trsArm.scale).sub(new Vector3f(1.0f)));

            transformation
                    .translate(startTranslation)
                    .rotateX((float) Math.toRadians(startRotation.x))
                    .rotateY((float) Math.toRadians(startRotation.y))
                    .rotateZ((float) Math.toRadians(startRotation.z))
                    .scale(startScale);

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
            pPoseStack.popPose();
        } catch (NullPointerException e) {
            throw new ZPNullException(e);
        }
    }

    protected void bobHurt(PoseStack pPoseStack, float pPartialTicks) {
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

    protected void bobView(PoseStack pPoseStack, float pPartialTicks) {
        if (Minecraft.getInstance().getCameraEntity() instanceof Player player) {
            float f = player.walkDist - player.walkDistO;
            float f1 = -(player.walkDist + f * pPartialTicks);
            float f2 = Mth.lerp(pPartialTicks, player.oBob, player.bob);
            pPoseStack.translate(Mth.sin(f1 * (float) Math.PI) * f2 * 0.5F, -Math.abs(Mth.cos(f1 * (float) Math.PI) * f2), 0.0F);
            pPoseStack.mulPose(Axis.ZP.rotationDegrees(Mth.sin(f1 * (float) Math.PI) * f2 * 3.0F));
            pPoseStack.mulPose(Axis.XP.rotationDegrees(Math.abs(Mth.cos(f1 * (float) Math.PI - 0.2F) * f2) * 5.0F));
        }
    }

    @Override
    public void onTick(TickEvent.@NotNull Phase phase) {

    }

    @Override
    public void setupResources(@NotNull Window window) {

    }

    @Override
    public void onWindowResizeAction(long descriptor, int width, int height) {

    }

    @Override
    public void destroyResources(@NotNull Window window) {

    }
}