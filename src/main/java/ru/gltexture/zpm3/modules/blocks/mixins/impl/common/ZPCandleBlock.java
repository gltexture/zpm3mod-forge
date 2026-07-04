package ru.gltexture.zpm3.modules.blocks.mixins.impl.common;

import net.minecraft.world.level.block.AbstractCandleBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractCandleBlock.class)
public abstract class ZPCandleBlock {
    @Inject(method = "<init>", at = @At("TAIL"))
    private void onInit(BlockBehaviour.Properties pProperties, CallbackInfo ci) {
    }
}
