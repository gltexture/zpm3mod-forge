package ru.gltexture.zpm3.assets.common.instances.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import ru.gltexture.zpm3.assets.common.global.ZPConstants;
import ru.gltexture.zpm3.assets.common.init.ZPBlockEntities;
import ru.gltexture.zpm3.assets.common.instances.block_entities.ZPFadingBlockEntity;
import ru.gltexture.zpm3.assets.common.instances.blocks.torch.IFadingBlock;
import ru.gltexture.zpm3.assets.common.instances.blocks.torch.ZPFadingTorchBlock;
import ru.gltexture.zpm3.assets.common.utils.ZPCommonClientUtils;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;
import ru.gltexture.zpm3.engine.instances.blocks.IHotLiquid;
import ru.gltexture.zpm3.engine.instances.blocks.ZPLiquidBlock;

import java.util.function.Supplier;

public class ZPAcidLiquidBlock extends ZPLiquidBlock implements EntityBlock, IHotLiquid, IFadingBlock {
    public ZPAcidLiquidBlock(@NotNull Supplier<? extends FlowingFluid> pFluid, @NotNull Properties pProperties) {
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
            if (random.nextFloat() < 0.05f) {
                Vector3f spawnPos = ZPCommonClientUtils.getParticleSpawnPositionBlockDir(pos, Direction.UP, random, new Vector3f(1.0f, fluidHeight, 1.0f));
                Vector3f motion = ZPRandom.instance.randomVector3f(0.05f, new Vector3f(0.1f));
                ZPCommonClientUtils.emmitAcidParticle(0.85f, spawnPos, motion);
            }
        }
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return !ZPConstants.FADING_ACIDS ? null : new ZPFadingBlockEntity(pPos, pState, ZPConstants.ACID_FADING_TIME, false);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level pLevel, @NotNull BlockState pState, @NotNull BlockEntityType<T> pBlockEntityType) {
        return !ZPConstants.FADING_ACIDS ? null : ZPFadingTorchBlock.createTickerHelper(pBlockEntityType, ZPBlockEntities.fading_block_entity.get(), ZPFadingBlockEntity::tick);
    }

    @Override
    public float bucketFillingChance() {
        return 0.04f;
    }

    @Override
    public @Nullable Supplier<Block> getTurnInto() {
        return () -> Blocks.SAND;
    }
}
