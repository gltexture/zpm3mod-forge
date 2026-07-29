/*
 *
 *  * zpm3forge
 *  * Copyright (C) 2026 gltexture
 *  *
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program. If not, see <https://www.gnu.org/licenses/>.
 *
 */

package ru.gltexture.zpm3.modules.blocks.mixins.impl.common.waterlog;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.gltexture.zpm3.modules.blocks.mixins.impl.common.ZPBlockStateRegAccessor;

@Mixin(BrewingStandBlock.class)
public class ZPBrewingStandWaterloggedMixin extends Block implements SimpleWaterloggedBlock {
    @Unique
    private static final BooleanProperty zpm3forge$WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public ZPBrewingStandWaterloggedMixin(Properties pProperties) {
        super(pProperties);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void zp$onInit(Properties pProperties, CallbackInfo ci) {
        ((ZPBlockStateRegAccessor) this).zp$registerDefaultState(((Block) (Object) this).getStateDefinition().any()
                .setValue(BrewingStandBlock.HAS_BOTTLE[0], Boolean.FALSE)
                .setValue(BrewingStandBlock.HAS_BOTTLE[1], Boolean.FALSE)
                .setValue(BrewingStandBlock.HAS_BOTTLE[2], Boolean.FALSE)
                .setValue(ZPBrewingStandWaterloggedMixin.zpm3forge$WATERLOGGED, false));

    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        LevelAccessor levelaccessor = pContext.getLevel();
        BlockPos blockpos = pContext.getClickedPos();
        return this.defaultBlockState().setValue(zpm3forge$WATERLOGGED, levelaccessor.getFluidState(blockpos).getType() == Fluids.WATER);
    }

    @Inject(method = "createBlockStateDefinition", at = @At("TAIL"))
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder, CallbackInfo ci) {
        pBuilder.add(ZPBrewingStandWaterloggedMixin.zpm3forge$WATERLOGGED);
    }

    @Override
    @SuppressWarnings("all")
    public @NotNull BlockState updateShape(BlockState pState, @NotNull Direction pFacing, @NotNull BlockState pFacingState, @NotNull LevelAccessor pLevel, @NotNull BlockPos pCurrentPos, @NotNull BlockPos pFacingPos) {
        if (pState.getValue(zpm3forge$WATERLOGGED)) {
            pLevel.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }
        return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }

    @Override
    @SuppressWarnings("all")
    public @NotNull FluidState getFluidState(BlockState pState) {
        return pState.getValue(ZPBrewingStandWaterloggedMixin.zpm3forge$WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }
}
