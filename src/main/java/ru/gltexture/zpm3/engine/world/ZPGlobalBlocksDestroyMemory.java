package ru.gltexture.zpm3.engine.world;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3i;
import ru.gltexture.zpm3.assets.common.global.ZPConstants;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;

import java.util.*;

public class ZPGlobalBlocksDestroyMemory {
    private final Map<Vector3i, BlockMemory> memory;
    private static List<String> blockBlackListToBreak;

    public ZPGlobalBlocksDestroyMemory() {
        this.memory = new HashMap<>();
    }

    public static void spawnBlockCrackParticles(ServerLevel serverLevel, BlockPos origin) {
        BlockState blockState = serverLevel.getBlockState(origin);

        for (Direction dir : Direction.values()) {
            int particlesPerFace = 3 + ZPRandom.getRandom().nextInt(3);
            for (int i = 0; i < particlesPerFace; i++) {
                double x = origin.getX() + (dir == Direction.WEST ? -0.2 : dir == Direction.EAST ? 1.2 : ZPRandom.getRandom().nextDouble());
                double y = origin.getY() + (dir == Direction.DOWN ? -0.2 : dir == Direction.UP ? 1.2 : ZPRandom.getRandom().nextDouble());
                double z = origin.getZ() + (dir == Direction.NORTH ? -0.2 : dir == Direction.SOUTH ? 1.2 : ZPRandom.getRandom().nextDouble());

                BlockPos checkPos = new BlockPos((int) Math.floor(x), (int) Math.floor(y), (int) Math.floor(z));
                BlockState checkState = serverLevel.getBlockState(checkPos);

                if (checkState.isSolid()) {
                    continue;
                }

                double vx = (ZPRandom.getRandom().nextDouble() - 0.5) * 0.05;
                double vy = (ZPRandom.getRandom().nextDouble() - 0.5) * 0.05;
                double vz = (ZPRandom.getRandom().nextDouble() - 0.5) * 0.05;

                serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, blockState), x, y, z, 3, vx, vy, vz, 0.0D);
            }
        }
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
        {
            if (ZPGlobalBlocksDestroyMemory.blockBlackListToBreak == null) {
                ZPGlobalBlocksDestroyMemory.blockBlackListToBreak = Arrays.stream(ZPConstants.GLOBAL_BLOCK_DAMAGE_MEMORY_BLACKLIST.split(";")).toList();
            }
            ResourceLocation id = BuiltInRegistries.BLOCK.getKey(blockState.getBlock());
            if (ZPGlobalBlocksDestroyMemory.blockBlackListToBreak.stream().anyMatch(e -> e.equals(id.toString()))) {
                return;
            }
        }
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
