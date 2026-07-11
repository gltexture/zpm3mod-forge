package ru.gltexture.zpm3.modules.blocks.mixins.impl.common;

import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.gltexture.zpm3.engine.instances.blocks.ZPTorchBlock;

@Mixin(WallBlock.class)
public class ZPWallBlockMixin {
    @Inject(method = "shouldRaisePost", at = @At("RETURN"), cancellable = true)
    private void shouldRaisePost(BlockState state, BlockState neighbour, VoxelShape shape, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValue() && neighbour.getBlock() instanceof ZPTorchBlock) {
            cir.setReturnValue(true);
        }
    }
}
