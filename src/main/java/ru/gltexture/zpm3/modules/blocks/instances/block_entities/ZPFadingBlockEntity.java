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

package ru.gltexture.zpm3.modules.blocks.instances.block_entities;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZP_EventsManager;
import ru.gltexture.zpm3.engine.core.api.events.common.ZPEventBus_Blocks;
import ru.gltexture.zpm3.engine.core.api.events.common.ZPEventBus_World;
import ru.gltexture.zpm3.modules.blocks.init.ZPBlockEntities;
import ru.gltexture.zpm3.modules.blocks.instances.blocks.IFadingBlock;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;
import ru.gltexture.zpm3.engine.instances.block_entities.ZPBlockEntity;
import ru.gltexture.zpm3.engine.instances.blocks.ZPTorchBlock;
import ru.gltexture.zpm3.engine.service.ZPUtility;

import java.util.Objects;

public class ZPFadingBlockEntity extends ZPBlockEntity implements IFadingBlockEntity {
    public static final String NBT_TIMELOCK = "timeLock";
    public static final String NBT_ACTIVE = "active";
    public static final String NBT_FADING_TIME = "fadingTime";
    private long timeLock;
    private boolean active;
    private int fadingTime;

    public ZPFadingBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState, int fadingTime, boolean active) {
        super(pType, pPos, pBlockState);
        this.timeLock = 0L;
        this.active = active;
        this.fadingTime = fadingTime;
    }

    public ZPFadingBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
        this.active = false;
        this.fadingTime = 1;
    }

    public ZPFadingBlockEntity(BlockPos pPos, BlockState pBlockState, int fadingTime, boolean active) {
        super(ZPBlockEntities.fading_block_entity.get(), pPos, pBlockState);
        this.timeLock = 0L;
        this.active = active;
        this.fadingTime = fadingTime;
    }

    public ZPFadingBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ZPBlockEntities.fading_block_entity.get(), pPos, pBlockState);
        this.active = false;
        this.fadingTime = 1;
    }

    public static void tick(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull ZPFadingBlockEntity blockEntity) {
        if (blockEntity.isServer()) {
            if (state.getBlock() instanceof IFadingBlock fadingBlock) {
                if (!blockEntity.active) {
                    return;
                }
                if (fadingBlock.zpm3forge$getTurnInto() != null) {
                    if (blockEntity.timeLock <= 0L) {
                        blockEntity.setTimeLock(level, (long) (level.getGameTime() + blockEntity.fadingTime + ZPRandom.instance.randomFloat(blockEntity.fadingTime * 0.25f)));
                        return;
                    }
                    boolean flag = (state.getBlock() instanceof TorchBlock || state.getBlock() instanceof ZPTorchBlock) && (ZPUtility.blocks().isRainingOnBlock(level, pos) && level.getGameTime() % 40 == 0);
                    if (level.getGameTime() >= blockEntity.timeLock) {
                        flag = true;
                    }
                    if (flag) {
                        BlockState newState = Objects.requireNonNull(fadingBlock.zpm3forge$getTurnInto()).get().defaultBlockState();
                        newState = ZPUtility.blocks().copyProperties(state, newState);
                        final ZPEventBus_Blocks.FadingBlockExtinguishEvent event = new ZPEventBus_Blocks.FadingBlockExtinguishEvent(level, pos, state, newState);
                        ZP_EventsManager.pushEvent(event);
                        if (event.isCancelled()) {
                            return;
                        }
                        newState = event.getNewState();
                        level.setBlock(pos, newState, Block.UPDATE_ALL);
                        if (level.getBlockEntity(pos) instanceof ZPFadingBlockEntity fadingBlockEntity) {
                            fadingBlockEntity.setActive(true);
                            fadingBlockEntity.setTimeLock(level, (long) (blockEntity.timeLock + fadingBlockEntity.fadingTime + ZPRandom.instance.randomFloat(fadingBlockEntity.fadingTime * 0.25f)));
                        }
                        level.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 1.0F);
                    }
                }
            }
        }
    }

    public boolean zpm3forge$isActive() {
        return this.active;
    }

    public ZPFadingBlockEntity setActive(boolean active) {
        this.active = active;
        return this;
    }

    public void setTimeLock(@NotNull Level level, long lock) {
        this.timeLock = lock;
    }

    @Override
    public void load(@NotNull CompoundTag pTag) {
        super.load(pTag);
        if (pTag.contains(ZPFadingBlockEntity.NBT_TIMELOCK)) {
            this.timeLock = pTag.getLong(ZPFadingBlockEntity.NBT_TIMELOCK);
        }
        if (pTag.contains(ZPFadingBlockEntity.NBT_ACTIVE)) {
            this.active = pTag.getBoolean(ZPFadingBlockEntity.NBT_ACTIVE);
        }
        if (pTag.contains(ZPFadingBlockEntity.NBT_FADING_TIME)) {
            this.fadingTime = pTag.getInt(ZPFadingBlockEntity.NBT_FADING_TIME);
        }
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag pTag) {
        super.saveAdditional(pTag);
        if (this.isServer()) {
            pTag.putLong(ZPFadingBlockEntity.NBT_TIMELOCK, this.timeLock);
            pTag.putBoolean(ZPFadingBlockEntity.NBT_ACTIVE, this.active);
            pTag.putInt(ZPFadingBlockEntity.NBT_FADING_TIME, this.fadingTime);
        }
    }

    public long getTimeLock() {
        return this.timeLock;
    }
}