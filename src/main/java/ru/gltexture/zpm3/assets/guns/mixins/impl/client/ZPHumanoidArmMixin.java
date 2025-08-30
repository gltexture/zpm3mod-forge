package ru.gltexture.zpm3.assets.guns.mixins.impl.client;

import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.gltexture.zpm3.assets.guns.mixins.client.HumanoidArmTransformations;

@Mixin(HumanoidModel.class)
public abstract class ZPHumanoidArmMixin <T extends LivingEntity> extends AgeableListModel<T> implements ArmedModel, HeadedModel {
    @Inject(method = "setupAnim*", at = @At("TAIL"))
    private void setupAnimZp(@NotNull T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch, CallbackInfo ci) {
        HumanoidModel<?> self = (HumanoidModel<?>)(Object)this;
        HumanoidArmTransformations.setupAnimZp(self, pEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
    }
}
