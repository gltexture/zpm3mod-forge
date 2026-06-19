package ru.gltexture.zpm3.modules.guns.mixins.impl.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.gltexture.zpm3.modules.guns.mixins.client.ZPHumanoidArmTransformations;

@Mixin(LivingEntityRenderer.class)
public abstract class ZPReanimateLivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>> {
    @Inject(method = "setupRotations*", at = @At("TAIL"))
    private void setupRotationsZp(T pEntityLiving, PoseStack pPoseStack, float pAgeInTicks, float pRotationYaw, float pPartialTicks, CallbackInfo ci) {
        LivingEntityRenderer<?, ?> self = (LivingEntityRenderer<?, ?>)(Object)this;
        ZPHumanoidArmTransformations.setupRotations(pEntityLiving, self, pPoseStack, pAgeInTicks, pRotationYaw, pPartialTicks);
    }
}
