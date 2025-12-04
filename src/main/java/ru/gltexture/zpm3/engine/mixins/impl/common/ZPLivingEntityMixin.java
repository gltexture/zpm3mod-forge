package ru.gltexture.zpm3.engine.mixins.impl.common;


import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.gltexture.zpm3.assets.guns.item.ZPBaseGun;

@Mixin(LivingEntity.class)
public abstract class ZPLivingEntityMixin {
    @Shadow public abstract ItemStack getMainHandItem();

    @Inject(method = "swing(Lnet/minecraft/world/InteractionHand;Z)V", at = @At("HEAD"))
    public void swing(InteractionHand pHand, boolean pUpdateSelf, CallbackInfo ci) {
        if (pHand == InteractionHand.MAIN_HAND) {
            ItemStack itemStack = this.getMainHandItem();
            if (itemStack.getItem() instanceof ZPBaseGun) {
                ci.cancel();
            }
        }
    }
}
