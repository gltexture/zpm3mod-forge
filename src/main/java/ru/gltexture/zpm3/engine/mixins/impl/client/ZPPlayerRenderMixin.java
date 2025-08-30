package ru.gltexture.zpm3.engine.mixins.impl.client;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.gltexture.zpm3.engine.mixins.inst.HumanoidArmorLayerOnArm;

import java.util.Map;

@Mixin(PlayerRenderer.class)
@OnlyIn(Dist.CLIENT)
public abstract class ZPPlayerRenderMixin {
    @Unique
    private static final Map<String, ResourceLocation> ARMOR_LOCATION_CACHE = Maps.newHashMap();
    @Unique
    @SuppressWarnings("rawtypes")
    private HumanoidArmorLayerOnArm humanoidArmorLayer = null;

    @Inject(method = "<init>", at = @At("TAIL"))
    @SuppressWarnings("rawtypes")
    private void onInit(EntityRendererProvider.Context pContext, boolean pUseSlimModel, CallbackInfo ci) {
        this.humanoidArmorLayer = new HumanoidArmorLayerOnArm<>( (PlayerRenderer) (Object) this, new HumanoidArmorModel(pContext.bakeLayer(pUseSlimModel ? ModelLayers.PLAYER_SLIM_OUTER_ARMOR : ModelLayers.PLAYER_OUTER_ARMOR)), pContext.getModelManager());
    }

    @Inject(method = "renderRightHand", at = @At("TAIL"))
    @SuppressWarnings("unchecked")
    private void renderRightHand(PoseStack pPoseStack, MultiBufferSource pBuffer, int pCombinedLight, AbstractClientPlayer pPlayer, CallbackInfo ci) {
        this.humanoidArmorLayer.renderArmorPiece(pPoseStack, pBuffer, true, pPlayer, pCombinedLight);
    }

    @Inject(method = "renderLeftHand", at = @At("TAIL"))
    @SuppressWarnings("unchecked")
    private void renderLeftHand(PoseStack pPoseStack, MultiBufferSource pBuffer, int pCombinedLight, AbstractClientPlayer pPlayer, CallbackInfo ci) {
        this.humanoidArmorLayer.renderArmorPiece(pPoseStack, pBuffer, false, pPlayer, pCombinedLight);
    }
}
