package ru.gltexture.zpm3.modules.blocks.mixins.impl.common;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.gltexture.zpm3.modules.blocks.instances.blocks.campfire.ZPCampfireBlock;
import ru.gltexture.zpm3.modules.blocks.mixins.ext.ITorchPlayerExt;

@Mixin(CampfireBlock.class)
public abstract class ZPCampfireMixin implements EntityBlock, ITorchPlayerExt {
    @Inject(method = "placeLiquid", at = @At("HEAD"), cancellable = true)
    public void placeLiquid(LevelAccessor pLevel, BlockPos pPos, BlockState pState, FluidState pFluidState, CallbackInfoReturnable<Boolean> cir) {
        if (!pState.getValue(BlockStateProperties.WATERLOGGED) && pFluidState.getType() == Fluids.WATER) {
            boolean flag = pState.getValue(CampfireBlock.LIT);
            if (flag) {
                pLevel.setBlock(pPos, pState.setValue(CampfireBlock.LIT, Boolean.FALSE), 3);
                if (!pLevel.isClientSide()) {
                    pLevel.playSound(null, pPos, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.BLOCKS, 1.0F, 1.0F);
                }
                CampfireBlock.dowse(null, pLevel, pPos, pState);
            } else {
                pLevel.setBlock(pPos, pState.setValue(CampfireBlock.WATERLOGGED, Boolean.TRUE), 3);
                pLevel.scheduleTick(pPos, pFluidState.getType(), pFluidState.getType().getTickDelay(pLevel));
            }
            cir.setReturnValue(true);
        } else {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "getStateForPlacement", at = @At("HEAD"), cancellable = true)
    public void getStateForPlacement(BlockPlaceContext pContext, CallbackInfoReturnable<BlockState> cir) {
        CampfireBlock block = (CampfireBlock)(Object)this;
      //((BlockInvoker) this).invokeRegisterDefaultState((
      //        block.getStateDefinition().any()
      //                .setValue(CampfireBlock.LIT, Boolean.FALSE)
      //                .setValue(CampfireBlock.SIGNAL_FIRE, Boolean.FALSE)
      //                .setValue(CampfireBlock.WATERLOGGED, Boolean.FALSE)
      //                .setValue(CampfireBlock.FACING, Direction.NORTH))
      //);

        LevelAccessor levelaccessor = pContext.getLevel();
        BlockPos blockpos = pContext.getClickedPos();
        boolean flag = levelaccessor.getFluidState(blockpos).getType() == Fluids.WATER;
        cir.setReturnValue(block.defaultBlockState()
                .setValue(CampfireBlock.WATERLOGGED, flag)
                .setValue(CampfireBlock.SIGNAL_FIRE, Boolean.TRUE)
                .setValue(CampfireBlock.LIT, Boolean.FALSE)
                .setValue(CampfireBlock.FACING, pContext.getHorizontalDirection())
        );
    }

    @Override
    public void zpm3forge$setPlacedBy(@NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull BlockState pState, @Nullable LivingEntity pPlacer, @NotNull ItemStack pStack) {
        ZPCampfireBlock.activationCheck(pLevel, pPos, pState, pPlacer, pStack);
    }
}
