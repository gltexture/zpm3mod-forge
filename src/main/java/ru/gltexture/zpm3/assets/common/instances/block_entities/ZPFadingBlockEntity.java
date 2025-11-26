package ru.gltexture.zpm3.assets.common.instances.block_entities;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.global.ZPConstants;
import ru.gltexture.zpm3.assets.common.init.ZPBlockEntities;
import ru.gltexture.zpm3.assets.common.instances.blocks.torch.IFadingBlock;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;
import ru.gltexture.zpm3.engine.instances.block_entities.ZPBlockEntity;
import ru.gltexture.zpm3.engine.service.ZPUtility;

public class ZPFadingBlockEntity extends ZPBlockEntity {
    public static final String NBT_TIMELOCK = "timeLock";
    public static final String NBT_ACTIVE = "active";
    public static final String NBT_FADING_TIME = "fadingTime";
    private long timeLock;
    private boolean active;
    private int fadingTime;

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
                if (fadingBlock.getTurnInto() != null) {
                    if (blockEntity.timeLock <= 0L) {
                        blockEntity.setTime(level, blockEntity.fadingTime, blockEntity.fadingTime / 4);
                        return;
                    }
                    boolean flag = (ZPUtility.blocks().isRainingOnBlock(level, pos) && level.getGameTime() % 40 == 0);
                    if (level.getGameTime() >= blockEntity.timeLock) {
                        flag = true;
                    }
                    if (flag) {
                        BlockState newState = fadingBlock.getTurnInto().get().defaultBlockState();
                        newState = ZPUtility.blocks().copyProperties(state, newState);
                        level.setBlock(pos, newState, Block.UPDATE_ALL);
                        level.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 1.0F);
                        blockEntity.setTime(level, blockEntity.fadingTime, blockEntity.fadingTime / 4);
                    }
                }
            }
        }
    }

    public boolean isActive() {
        return this.active;
    }

    public ZPFadingBlockEntity setActive(boolean active) {
        this.active = active;
        return this;
    }

    public void setTime(@NotNull Level level, long exact, long salt) {
        this.timeLock = level.getGameTime() + (long) (exact + ZPRandom.instance.randomFloatDuo(salt));
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