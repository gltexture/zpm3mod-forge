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

package ru.gltexture.zpm3.modules.blocks.instances.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.engine.core.config.builtin.ZPEntityConfig;
import ru.gltexture.zpm3.engine.core.config.builtin.ZPWorldConfig;
import ru.gltexture.zpm3.modules.commands.zones.ZPZoneChecks;

import ru.gltexture.zpm3.modules.blocks.instances.block_entities.ZPBarbaredWireBlockEntity;
import ru.gltexture.zpm3.modules.mob_effects.init.ZPMobEffects;
import ru.gltexture.zpm3.modules.mob_effects.utils.ZPEffectUtils;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;

public class ZPBarbaredWireBlock extends Block implements EntityBlock, SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public ZPBarbaredWireBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false));
    }

    @SuppressWarnings("deprecation")
    @Override
    public void entityInside(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            entity.makeStuckInBlock(state, new Vec3(0.375D, 0.375D, 0.375D));
        }
        if (level instanceof ServerLevel serverLevel && !ZPZoneChecks.INSTANCE.isBarbaredWiresDisabled(serverLevel, pos)) {
            if (entity instanceof LivingEntity livingEntity) {
                entity.hurt(entity.damageSources().generic(), entity instanceof Player ? 1 : 2);
                if (entity.tickCount % 20 == 0 && ZPRandom.getRandom().nextFloat() <= 0.1f) {
                    if (!ZPEntityConfig.BLEEDING_ONLY_FOR_PLAYERS.getVar() || entity instanceof Player) {
                        if (!ZPEffectUtils.isBleeding(livingEntity)) {
                            livingEntity.addEffect(new MobEffectInstance(ZPMobEffects.bleeding.get(), 300));
                        }
                    }
                }
                if (entity.tickCount % 20 == 0) {
                    if (level.getBlockEntity(pos) instanceof ZPBarbaredWireBlockEntity blockEntity) {
                        blockEntity.setDamage(blockEntity.getDamage() + 1);
                        if (blockEntity.getDamage() >= ZPWorldConfig.MAX_BARBARED_WIRE_STRENGTH.getVar()) {
                            level.playSound(null, pos, SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 1.0F, 1.2F);
                            level.destroyBlock(pos, false);
                        }
                    }
                }
            }
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        LevelAccessor levelaccessor = pContext.getLevel();
        BlockPos blockpos = pContext.getClickedPos();
        return this.defaultBlockState().setValue(WATERLOGGED, levelaccessor.getFluidState(blockpos).getType() == Fluids.WATER);
    }

    @Override
    @SuppressWarnings("all")
    public @NotNull BlockState updateShape(BlockState pState, @NotNull Direction pFacing, @NotNull BlockState pFacingState, @NotNull LevelAccessor pLevel, @NotNull BlockPos pCurrentPos, @NotNull BlockPos pFacingPos) {
        if (pState.getValue(WATERLOGGED)) {
            pLevel.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }
        return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }

    @Override
    @SuppressWarnings("all")
    public @NotNull FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(WATERLOGGED);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new ZPBarbaredWireBlockEntity(pPos, pState);
    }
}
