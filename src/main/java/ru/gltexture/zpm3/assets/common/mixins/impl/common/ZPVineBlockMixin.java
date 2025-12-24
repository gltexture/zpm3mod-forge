package ru.gltexture.zpm3.assets.common.mixins.impl.common;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.gltexture.zpm3.assets.common.global.ZPConstants;
import ru.gltexture.zpm3.assets.common.instances.blocks.torch.IFadingBlock;

@Mixin(Entity.class)
public class ZPVineBlockMixin {
    @Inject(method = "isStateClimbable", at = @At("HEAD"), cancellable = true)
    private void isStateClimbable(BlockState pState, CallbackInfoReturnable<Boolean> cir) {
        if (ZPConstants.DISABLE_VINE_CLIMB && pState.getBlock().equals(Blocks.VINE)) {
            cir.setReturnValue(false);
        }
    }
}