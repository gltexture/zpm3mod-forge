package ru.gltexture.zpm3.assets.common.instances.blocks.torch;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.assets.common.global.ZPConstants;
import ru.gltexture.zpm3.assets.common.init.ZPBlockEntities;
import ru.gltexture.zpm3.assets.common.instances.block_entities.ZPFadingBlockEntity;
import ru.gltexture.zpm3.engine.objects.blocks.ZPWallTorchBlock;

import java.util.function.Supplier;

public class ZPFadingBlockWall extends ZPWallTorchBlock implements EntityBlock, IFadingBlock {
    private final Supplier<Block> turnInto;

    public ZPFadingBlockWall(@NotNull Properties pProperties, @Nullable ParticleOptions pFlameParticle, float particleRate, @Nullable Supplier<Block> turnInto) {
        super(pProperties, pFlameParticle, particleRate);
        this.turnInto = turnInto;
    }

    @Override
    public @Nullable Supplier<Block> getTurnInto() {
        return this.turnInto;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return !ZPConstants.FADING_TORCHES ? null : ZPFadingBlock.createTickerHelper(type, ZPBlockEntities.fading_block_entity.get(), ZPFadingBlockEntity::tick);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return !ZPConstants.FADING_TORCHES ? null : new ZPFadingBlockEntity(pPos, pState);
    }
}
