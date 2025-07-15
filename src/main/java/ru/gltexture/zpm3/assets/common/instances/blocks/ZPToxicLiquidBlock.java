package ru.gltexture.zpm3.assets.common.instances.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import ru.gltexture.zpm3.assets.common.utils.ZPCommonClientUtils;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;
import ru.gltexture.zpm3.engine.instances.blocks.IHotLiquid;
import ru.gltexture.zpm3.engine.instances.blocks.ZPLiquidBlock;

import java.util.function.Supplier;

public class ZPToxicLiquidBlock extends ZPLiquidBlock {
    public ZPToxicLiquidBlock(@NotNull Supplier<? extends FlowingFluid> pFluid, @NotNull Properties pProperties) {
        super(pFluid, pProperties);
    }

    @Override
    public void animateTick(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (level.isClientSide) {
            this.acidParticles(state, level, pos, random);
        }
    }

    @OnlyIn(Dist.CLIENT)
    private void acidParticles(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (!level.isClientSide) {
            return;
        }

        BlockPos offsetPos = pos.relative(Direction.UP);
        BlockState adjacentState = level.getBlockState(offsetPos);

        FluidState fluidState = level.getFluidState(pos);
        float fluidHeight = fluidState.getOwnHeight();

        if (!adjacentState.isSolidRender(level, offsetPos)) {
            if (random.nextFloat() < 0.01f) {
                Vector3f spawnPos = ZPCommonClientUtils.getParticleSpawnPositionBlockDir(pos, Direction.UP, random, new Vector3f(1.0f, fluidHeight, 1.0f));
                Vector3f motion = ZPRandom.instance.randomVector3f(0.05f, new Vector3f(0.1f));
                ZPCommonClientUtils.emmitToxicParticle(1.15f, spawnPos, motion);
            }
        }
    }
}
