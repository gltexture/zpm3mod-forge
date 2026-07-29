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
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BedPart;
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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.gltexture.zpm3.modules.blocks.mixins.impl.common.ZPBlockStateRegAccessor;

import java.util.Objects;

@Mixin(BedBlock.class)
public class ZPBedWaterloggedMixin extends Block implements SimpleWaterloggedBlock {
    @Unique
    private static final BooleanProperty zpm3forge$WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public ZPBedWaterloggedMixin(Properties pProperties) {
        super(pProperties);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void zp$onInit(DyeColor pColor, Properties pProperties, CallbackInfo ci) {
        ((ZPBlockStateRegAccessor) this).zp$registerDefaultState(((Block) (Object) this).getStateDefinition().any()
                .setValue(BedBlock.PART, BedPart.FOOT)
                .setValue(BedBlock.OCCUPIED, Boolean.FALSE)
                .setValue(ZPBedWaterloggedMixin.zpm3forge$WATERLOGGED, false));

    }

    @Inject(method = "getStateForPlacement", at = @At("RETURN"), cancellable = true)
    private void zp$getStateForPlacement(BlockPlaceContext pContext, CallbackInfoReturnable<BlockState> cir) {
        BlockState state = cir.getReturnValue();
        final boolean waterlogged = pContext.getLevel().getFluidState(pContext.getClickedPos()).getType() == Fluids.WATER;
        cir.setReturnValue(Objects.requireNonNullElseGet(state, this::defaultBlockState).setValue(BlockStateProperties.WATERLOGGED, waterlogged));
    }

    @Inject(method = "createBlockStateDefinition", at = @At("TAIL"))
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder, CallbackInfo ci) {
        pBuilder.add(ZPBedWaterloggedMixin.zpm3forge$WATERLOGGED);
    }

    @Inject(method = "updateShape", at = @At("HEAD"))
    public void updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos, CallbackInfoReturnable<BlockState> cir) {
        if (pState.getValue(ZPBedWaterloggedMixin.zpm3forge$WATERLOGGED)) {
            pLevel.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }
    }

    @Override
    @SuppressWarnings("all")
    public @NotNull FluidState getFluidState(BlockState pState) {
        return pState.getValue(ZPBedWaterloggedMixin.zpm3forge$WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }
}
