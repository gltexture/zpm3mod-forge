package ru.gltexture.zpm3.engine.mixins.impl.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraftforge.common.ForgeMod;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public class ZPEntityRendererMixin {
    @Shadow @Final protected EntityRenderDispatcher entityRenderDispatcher;
    @Shadow @Final private Font font;

    private static boolean isNameplateInRenderDistanceZP(Entity entity, double squareDistance) {
        if (entity instanceof LivingEntity) {
            final AttributeInstance attribute = ((LivingEntity) entity).getAttribute(ForgeMod.NAMETAG_DISTANCE.get());
            if (attribute != null) {
                return !(squareDistance > (attribute.getValue() * attribute.getValue()));
            }
        }
        return !(squareDistance > 64);
    }

    @Inject(method = "renderNameTag", at = @At("HEAD"))
    protected void renderNameTag(Entity pEntity, Component pDisplayName, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, CallbackInfo ci) {
        double d0 = this.entityRenderDispatcher.distanceToSqr(pEntity);
        if (ZPEntityRendererMixin.isNameplateInRenderDistanceZP(pEntity, d0)) {
            boolean flag = !pEntity.isDiscrete();
            float f = pEntity.getNameTagOffsetY();
            int i = "deadmau5".equals(pDisplayName.getString()) ? -10 : 0;
            pPoseStack.pushPose();
            pPoseStack.translate(0.0F, f, 0.0F);
            pPoseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
            pPoseStack.scale(-0.025F, -0.025F, 0.025F);
            Matrix4f matrix4f = pPoseStack.last().pose();
            Font font = this.font;
            float f2 = (float)(-font.width(pDisplayName) / 2);
            font.drawInBatch(pDisplayName, f2, (float)i, pDisplayName.getString().equals("gltexture") ? 0xff0000 : 0x00ff00, false, matrix4f, pBuffer, Font.DisplayMode.NORMAL, 0, pPackedLight);
            pPoseStack.popPose();
        }
    }
}
