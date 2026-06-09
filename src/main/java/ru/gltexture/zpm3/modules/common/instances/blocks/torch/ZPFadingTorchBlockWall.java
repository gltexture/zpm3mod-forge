package ru.gltexture.zpm3.modules.common.instances.blocks.torch;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import ru.gltexture.zpm3.engine.core.config.builtin.ZPWorldConfig;
import ru.gltexture.zpm3.modules.common.init.ZPBlockEntities;
import ru.gltexture.zpm3.modules.common.instances.block_entities.ZPFadingBlockEntity;
import ru.gltexture.zpm3.engine.instances.blocks.ZPWallTorchBlock;

import java.util.function.Supplier;

public class ZPFadingTorchBlockWall extends ZPWallTorchBlock implements EntityBlock, IFadingBlock {
    private final Supplier<Block> turnInto;

    public ZPFadingTorchBlockWall(@NotNull Properties pProperties, @Nullable ParticleOptions pFlameParticle, float particleRate, @Nullable Supplier<Block> turnInto) {
        super(pProperties, pFlameParticle, particleRate);
        this.turnInto = turnInto;
    }

    @Override
    public @Nullable Supplier<Block> zpm3forge$getTurnInto() {
        return this.turnInto;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return !ZPWorldConfig.FADING_TORCHES.getVar() ? null : ZPFadingTorchBlock.createTickerHelper(type, ZPBlockEntities.fading_block_entity.get(), ZPFadingBlockEntity::tick);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return !ZPWorldConfig.FADING_TORCHES.getVar() ? null : new ZPFadingBlockEntity(pPos, pState, ZPWorldConfig.TORCH_FADING_TIME.getVar(), true);
    }

    @Override
    public void setPlacedBy(@NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull BlockState pState, @Nullable LivingEntity pPlacer, @NotNull ItemStack pStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
        ZPFadingTorchBlock.activationCheck(pLevel, pPos, pState, pPlacer, pStack);
    }
}
