package ru.gltexture.zpm3.assets.common.instances.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
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
import ru.gltexture.zpm3.assets.commands.zones.ZPZoneChecks;
import ru.gltexture.zpm3.assets.common.global.ZPConstants;
import ru.gltexture.zpm3.assets.common.init.ZPBlockEntities;
import ru.gltexture.zpm3.assets.common.init.ZPFluidTypes;
import ru.gltexture.zpm3.assets.common.init.ZPFluids;
import ru.gltexture.zpm3.assets.common.instances.block_entities.ZPFadingBlockEntity;
import ru.gltexture.zpm3.assets.common.instances.blocks.torch.IFadingBlock;
import ru.gltexture.zpm3.assets.common.instances.blocks.torch.ZPFadingTorchBlock;
import ru.gltexture.zpm3.assets.common.utils.ZPCommonClientUtils;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;
import ru.gltexture.zpm3.engine.fake.ZPFakePlayer;
import ru.gltexture.zpm3.engine.instances.blocks.IHotLiquid;
import ru.gltexture.zpm3.engine.instances.blocks.ZPLiquidBlock;
import ru.gltexture.zpm3.engine.mixins.ext.IZPLevelExt;
import ru.gltexture.zpm3.engine.world.GlobalBlocksDestroyMemory;

import java.util.function.Supplier;

public class ZPAcidLiquidBlock extends ZPLiquidBlock implements EntityBlock, IHotLiquid, IFadingBlock {
    public ZPAcidLiquidBlock(@NotNull Supplier<? extends FlowingFluid> pFluid, @NotNull Properties pProperties) {
        super(pFluid, pProperties.randomTicks());
    }

    @Override
    public void animateTick(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (level.isClientSide) {
            this.acidParticles(state, level, pos, random);
        }
    }

    @Override
    public boolean isRandomlyTicking(@NotNull BlockState pState) {
        return true;
    }

    private boolean isAcidFallTooHigh(Level level, BlockPos pos) {
        final int maxHeight = ZPConstants.ACID_BLOCK_DESTRUCTION_CONSTRAINT;
        BlockPos.MutableBlockPos checkPos = pos.mutable();
        int height = 0;

        while (height < maxHeight) {
            checkPos.move(Direction.UP);
            FluidState fsUp = level.getFluidState(checkPos);

            if (fsUp.isSource()) {
                return false;
            }

            if (!(fsUp.getType() instanceof FlowingFluid)) {
                return false;
            }

            if (fsUp.getValue(FlowingFluid.LEVEL) != 8) {
                return false;
            }

            height++;
        }

        return true;
    }


    @Override
    public void randomTick(@NotNull BlockState pState, @NotNull ServerLevel pLevel, @NotNull BlockPos pPos, @NotNull RandomSource pRandom) {
        if (pState.getFluidState().isRandomlyTicking()) {
            super.randomTick(pState, pLevel, pPos, pRandom);
        }
        if (ZPConstants.ALLOW_ACID_LIQUID_DESTROY_BLOCKS) {
            if (this.isAcidFallTooHigh(pLevel, pPos)) {
                return;
            }
            for (Direction dir : Direction.values()) {
                if (ZPRandom.getRandom().nextFloat() <= 0.2f || dir == Direction.UP) {
                    continue;
                }
                BlockPos blockPos = pPos.relative(dir);
                final BlockState state1 = pLevel.getBlockState(blockPos);
                final Block block = state1.getBlock();
                //level.setBlock(blockPos, Blocks.DIAMOND_BLOCK.defaultBlockState(), Block.UPDATE_ALL);
                if (state1.isAir()) {
                    continue;
                }
                if (state1.getFluidState().isSource() || !state1.getFluidState().isEmpty()) {
                    continue;
                }
                if (state1.getDestroySpeed(pLevel, blockPos) < 0) {
                    continue;
                }
                boolean flagIsGlass = (!(block instanceof IceBlock)) && block.soundType == SoundType.GLASS;
                if (!flagIsGlass) {
                    if (pLevel instanceof IZPLevelExt ext) {
                        if (ZPFakePlayer.canBreakBlock(pLevel, blockPos) && !ZPZoneChecks.INSTANCE.isNoAcidAffection(pLevel, blockPos) && !ZPZoneChecks.INSTANCE.isNoAcidBlockDestruction(pLevel, blockPos)) {
                            ext.getGlobalBlocksDestroyMemory().addNewEntryLongMem(pLevel, blockPos, ZPConstants.ACID_BLOCK_BASE_BLOCK_DAMAGE + ZPRandom.getRandom().nextFloat() * 0.35f);
                            GlobalBlocksDestroyMemory.spawnBlockCrackParticles(pLevel, blockPos);
                        }
                    }
                }
            }
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
            if (random.nextFloat() < 0.005f) {
                level.playLocalSound(pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.MASTER, 0.5f, 1.0f + ZPRandom.getRandom().nextFloat(0.15f), false);
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
