package ru.gltexture.zpm3.engine.world;

import com.google.common.base.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3i;
import ru.gltexture.zpm3.assets.common.global.ZPConstants;

import java.util.*;

public class GlobalBlocksDestroyMemory {
    private final Map<Vector3i, BlockMemory> memory;

    public GlobalBlocksDestroyMemory() {
        this.memory = new HashMap<>();
    }

    public void addNewEntryShortMem(@NotNull Level level, @NotNull BlockPos blockPos, float progressInc) {
        this.addNewEntry(level, blockPos, progressInc, ZPConstants.TIME_TO_CLEAR_SHARED_ZOMBIE_MINING_SHORT_MEM);
    }

    public void addNewEntryLongMem(@NotNull Level level, @NotNull BlockPos blockPos, float progressInc) {
        this.addNewEntry(level, blockPos, progressInc, ZPConstants.TIME_TO_CLEAR_SHARED_ZOMBIE_MINING_LONG_MEM);
    }

    private void addNewEntry(@NotNull Level level, @NotNull BlockPos blockPos, float progressInc, int memTicks) {
        Vector3i pos = new Vector3i(blockPos.getX(), blockPos.getY(), blockPos.getZ());
        BlockMemory blockMemory = this.memory.get(new Vector3i(blockPos.getX(), blockPos.getY(), blockPos.getZ()));
        final BlockState blockState = level.getBlockState(blockPos);
        float blockHardness = blockState.getDestroySpeed(level, blockPos) * ZPConstants.ZOMBIE_MINING_BLOCK_HARDNESS_MULTIPLIER;
        if (blockHardness < 0) {
            return;
        }
        if (blockMemory == null) {
            level.destroyBlockProgress(this.getFakeId(pos), blockPos, this.getVisualProgress(progressInc, blockHardness));
            blockMemory = new BlockMemory(level.getBlockState(blockPos), pos, level.getGameTime(), 0.0f, memTicks);
            this.memory.put(pos, blockMemory);
        }
        blockMemory.timeStamp = level.getGameTime();
        blockMemory.progress += progressInc;
    }

    public void resetTicks(@NotNull Level level, @NotNull Vector3i pos) {
        if (this.memory.containsKey(pos)) {
            BlockMemory memory1 = this.memory.get(pos);
            memory1.timeStamp = level.getGameTime();
        }
    }

    public float getBlockProgressFromMemOrNegative(@NotNull Vector3i pos) {
        if (this.memory.containsKey(pos)) {
            return this.memory.get(pos).progress;
        }
        return -1.0f;
    }

    protected int getFakeId(@NotNull Vector3i vector3i) {
        return -vector3i.hashCode();
    }

    private int getVisualProgress(float timeSpent, float totalTime) {
        float ratio = timeSpent / totalTime;
        return Mth.clamp((int) (ratio * 10.0f), 0, 9);
    }

    public void refreshMemory(@NotNull Level level) {
        Iterator<Map.Entry<Vector3i, BlockMemory>> iterator = this.memory.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Vector3i, BlockMemory> entry = iterator.next();
            final BlockPos blockPos = new BlockPos(entry.getValue().blockPos.x, entry.getValue().blockPos.y, entry.getValue().blockPos.z);
            if ((level.getGameTime() - entry.getValue().timeStamp) > entry.getValue().getMemTicks()) {
                level.destroyBlockProgress(this.getFakeId(entry.getKey()), blockPos, -1);
                iterator.remove();
                continue;
            }
            final BlockState blockState = level.getBlockState(blockPos);
            final float blockHardness = blockState.getDestroySpeed(level, blockPos) * ZPConstants.ZOMBIE_MINING_BLOCK_HARDNESS_MULTIPLIER;
            final int visualProgress = this.getVisualProgress(entry.getValue().progress, blockHardness);
            {
                if (level.getGameTime() % 60 == 0 || entry.getValue().getPrevVisualProgress() != visualProgress) {
                    level.destroyBlockProgress(this.getFakeId(entry.getKey()), blockPos, visualProgress);
                }
                entry.getValue().setPrevVisualProgress(visualProgress);
            }
            if (entry.getValue().blockState.getBlock() != blockState.getBlock()) {
                level.destroyBlockProgress(this.getFakeId(entry.getKey()), blockPos, -1);
                iterator.remove();
                continue;
            }
            if (entry.getValue().progress >= blockHardness) {
                level.destroyBlockProgress(this.getFakeId(entry.getKey()), blockPos, -1);
                level.destroyBlock(blockPos, false);
                iterator.remove();
            }
        }
    }

    public static class BlockMemory {
        private final @NotNull BlockState blockState;
        private final @NotNull Vector3i blockPos;
        private long timeStamp;
        private float progress;
        private final int memTicks;
        private int prevVisualProgress;

        public BlockMemory(@NotNull BlockState blockState, @NotNull Vector3i blockPos, long timeStamp, float progress, int memTicks) {
            this.blockState = blockState;
            this.blockPos = blockPos;
            this.timeStamp = timeStamp;
            this.progress = progress;
            this.memTicks = memTicks;
            this.prevVisualProgress = 0;
        }

        public int getPrevVisualProgress() {
            return this.prevVisualProgress;
        }

        public BlockMemory setPrevVisualProgress(int prevVisualProgress) {
            this.prevVisualProgress = prevVisualProgress;
            return this;
        }

        public @NotNull BlockState getBlockState() {
            return this.blockState;
        }

        public @NotNull Vector3i getBlockPos() {
            return this.blockPos;
        }

        public long getTimeStamp() {
            return this.timeStamp;
        }

        public float getProgress() {
            return this.progress;
        }

        public int getMemTicks() {
            return this.memTicks;
        }
    }
}
