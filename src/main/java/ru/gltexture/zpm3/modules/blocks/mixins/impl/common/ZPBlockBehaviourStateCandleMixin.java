package ru.gltexture.zpm3.modules.blocks.mixins.impl.common;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.AbstractCandleBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.gltexture.zpm3.engine.core.config.builtin.ZPWorldConfig;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class ZPBlockBehaviourStateCandleMixin {
    @Shadow
    public abstract Block getBlock();

    @Inject(method = "randomTick", at = @At("HEAD"))
    private void randomTick(ServerLevel pLevel, BlockPos pPos, RandomSource pRandom, CallbackInfo ci) {
        if (this.getBlock() instanceof AbstractCandleBlock abstractCandleBlock && pLevel.getBlockState(pPos).getValue(AbstractCandleBlock.LIT)) {
            float chance = ZPWorldConfig.CANDLE_EACH_TICK_RANDOM_EXTINGUISH_CONST.getVar();
            if(pLevel.isRainingAt(pPos)) {
                chance *= 10.0f;
            }
            if (ZPRandom.getRandom().nextFloat() <= chance) {
                AbstractCandleBlock.extinguish(null, pLevel.getBlockState(pPos), pLevel, pPos);
            }
        }
    }

    @Inject(method = "isRandomlyTicking", at = @At("HEAD"), cancellable = true)
    public void randTickBool(CallbackInfoReturnable<Boolean> cir) {
        if (ZPWorldConfig.CANDLE_EACH_TICK_RANDOM_EXTINGUISH_CONST.getVar() > 0.0f) {
            if (this.getBlock() instanceof AbstractCandleBlock abstractCandleBlock) {
                cir.setReturnValue(true);
            }
        }
    }
}