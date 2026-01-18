package ru.gltexture.zpm3.assets.common.mixins.impl.common;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SimpleWaterloggedBlock.class)
public class ZPWaterLoggedMixinFix {
    @Inject(method = "canPlaceLiquid", at = @At("HEAD"), cancellable = true)
    public void canPlaceLiquid(BlockGetter pLevel, BlockPos pPos, BlockState pState, Fluid pFluid, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(pFluid == Fluids.WATER);
    }
}
