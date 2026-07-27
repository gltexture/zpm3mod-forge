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

package ru.gltexture.zpm3.modules.fluids.mixins.impl.common;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import ru.gltexture.zpm3.engine.core.config.builtin.ZPWorldConfig;
import ru.gltexture.zpm3.modules.blocks.init.ZPBlockEntities;
import ru.gltexture.zpm3.modules.blocks.instances.block_entities.ZPFadingBlockEntity;
import ru.gltexture.zpm3.modules.blocks.instances.blocks.IFadingBlock;
import ru.gltexture.zpm3.modules.blocks.instances.blocks.torch.ZPFadingTorchBlock;
import ru.gltexture.zpm3.engine.instances.blocks.IHotLiquid;

import java.util.function.Supplier;

@Mixin(LiquidBlock.class)
public abstract class ZPFadingLavaMixin implements EntityBlock, IFadingBlock, IHotLiquid {
    @Shadow(remap = false) public abstract FlowingFluid getFluid();
    @Shadow public abstract FluidState getFluidState(BlockState pState);

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return !ZPWorldConfig.FADING_LAVAS.getVar() ? null : new ZPFadingBlockEntity(pPos, pState, ZPWorldConfig.LAVA_FADING_TIME.getVar(), false);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level pLevel, @NotNull BlockState pState, @NotNull BlockEntityType<T> pBlockEntityType) {
        if (!pState.getBlock().equals(Blocks.LAVA) || !this.getFluid().isSource(this.getFluidState(pState))) {
            return null;
        }
        return !ZPWorldConfig.FADING_LAVAS.getVar() ? null : ZPFadingTorchBlock.createTickerHelper(pBlockEntityType, ZPBlockEntities.fading_block_entity.get(), ZPFadingBlockEntity::tick);
    }

    @Inject(method = "randomTick", at = @At("HEAD"))
    private void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom, CallbackInfo ci) {
    }

    @SuppressWarnings("all")
    @Override
    public float bucketFillingChance() {
        return ((Object) this) == Blocks.LAVA ? ZPWorldConfig.BUCKET_LAVA_FILL_CHANCE.getVar() : 1.0f;
    }

    @Override
    public @Nullable Supplier<Block> zpm3forge$getTurnInto() {
        return () -> Blocks.COBBLESTONE;
    }
}