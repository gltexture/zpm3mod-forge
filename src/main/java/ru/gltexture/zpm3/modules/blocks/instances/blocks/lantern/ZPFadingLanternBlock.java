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

package ru.gltexture.zpm3.modules.blocks.instances.blocks.lantern;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.engine.core.config.builtin.ZPWorldConfig;
import ru.gltexture.zpm3.modules.blocks.init.ZPBlockEntities;
import ru.gltexture.zpm3.modules.blocks.instances.block_entities.ZPFadingBlockEntity;
import ru.gltexture.zpm3.modules.blocks.instances.blocks.IFadingBlock;
import ru.gltexture.zpm3.modules.blocks.instances.blocks.torch.ZPFadingTorchBlock;

import java.util.function.Supplier;

public class ZPFadingLanternBlock extends ZPLanternBlock implements EntityBlock, IFadingBlock {
    private final Supplier<Block> turnInto;

    public ZPFadingLanternBlock(Properties pProperties, Supplier<Block> turnInto) {
        super(pProperties);
        this.turnInto = turnInto;
    }

    @Override
    public @Nullable Supplier<Block> zpm3forge$getTurnInto() {
        return this.turnInto;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return !ZPWorldConfig.FADING_LANTERNS.getVar() ? null : ZPFadingTorchBlock.createTickerHelper(type, ZPBlockEntities.fading_block_entity.get(), ZPFadingBlockEntity::tick);
    }

    @SuppressWarnings("all")
    public static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<E> createTickerHelper(BlockEntityType<E> given, BlockEntityType<A> expected, BlockEntityTicker<? super A> ticker) {
        return given == expected ? (BlockEntityTicker<E>) ticker : null;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return !ZPWorldConfig.FADING_LANTERNS.getVar() ? null : new ZPFadingBlockEntity(pPos, pState, ZPWorldConfig.LANTERN_FADING_TIME.getVar(), true);
    }

    @Override
    public void setPlacedBy(@NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull BlockState pState, @Nullable LivingEntity pPlacer, @NotNull ItemStack pStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
        //ZPFadingTorchBlock.activationCheck(pLevel, pPos, pState, pPlacer, pStack);
    }

    @SuppressWarnings("all")
    @Override
    public void onPlace(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull BlockState pOldState, boolean pMovedByPiston) {
        super.onPlace(pState, pLevel, pPos, pOldState, pMovedByPiston);
    }
}
