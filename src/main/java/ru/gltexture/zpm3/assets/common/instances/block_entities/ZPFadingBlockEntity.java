package ru.gltexture.zpm3.assets.common.instances.block_entities;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.global.ZPConstants;
import ru.gltexture.zpm3.assets.common.init.ZPBlockEntities;
import ru.gltexture.zpm3.assets.common.instances.blocks.torch.IFadingBlock;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;
import ru.gltexture.zpm3.engine.objects.block_entities.ZPBlockEntity;
import ru.gltexture.zpm3.engine.service.ZPUtility;

public class ZPFadingBlockEntity extends ZPBlockEntity {
    public static final String NBT_TIMELOCK = "timeLock";
    private long timeLock;

    public ZPFadingBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ZPBlockEntities.fading_block_entity.get(), pPos, pBlockState);
        this.timeLock = 0L;
    }

    public static void tick(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull ZPFadingBlockEntity blockEntity) {
        if (blockEntity.isServer()) {
            if (state.getBlock() instanceof IFadingBlock fadingBlock) {
                if (fadingBlock.getTurnInto() != null) {
                    if (blockEntity.timeLock <= 0L && blockEntity.isServer()) {
                        blockEntity.setTime(level, ZPConstants.TORCH_FADING_TIME, ZPConstants.TORCH_FADING_TIME_SALT);
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
                        blockEntity.setTime(level, ZPConstants.TORCH_FADING_TIME, ZPConstants.TORCH_FADING_TIME_SALT);
                    }
                }
            }
        }
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
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag pTag) {
        super.saveAdditional(pTag);
        if (this.isServer()) {
            pTag.putLong(ZPFadingBlockEntity.NBT_TIMELOCK, this.timeLock);
        }
    }

    public long getTimeLock() {
        return this.timeLock;
    }
}