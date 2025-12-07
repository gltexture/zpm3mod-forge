package ru.gltexture.zpm3.engine.mixins.impl.common;

import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.gltexture.zpm3.assets.common.global.ZPConstants;

@Mixin(AbstractCookingRecipe.class)
public class ZPCookingRecipeMixin {
    @Final @Shadow protected int cookingTime;

    @Inject(method = "getCookingTime", at = @At("HEAD"), cancellable = true)
    private void getCookingTime(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue((int) (this.cookingTime * ZPConstants.ZP_COOKING_TIME_ALL_CRAFTING_BLOCKS_MULTIPLIER));
    }
}
