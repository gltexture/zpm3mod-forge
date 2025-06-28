package ru.gltexture.zpm3.assets.common.instances.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import ru.gltexture.zpm3.assets.common.utils.ZPCommonClientUtils;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;

public class ZPUraniumBlock extends Block {
    public ZPUraniumBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void animateTick(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (!level.isClientSide) {
            return;
        }

        for (Direction direction : Direction.values()) {
            BlockPos offsetPos = pos.relative(direction);
            BlockState adjacentState = level.getBlockState(offsetPos);

            if (!adjacentState.isSolidRender(level, offsetPos)) {
                if (random.nextFloat() < 0.15f) {
                    Vector3f spawnPos = ZPCommonClientUtils.getParticleSpawnPositionBlockDir(pos, direction, random);
                    Vector3f motion = ZPRandom.instance.randomVector3f(0.05f, new Vector3f(0.1f));
                    ZPCommonClientUtils.emmitUraniumParticle(0.5f, spawnPos, motion);
                }
            }
        }
    }
}
