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
import ru.gltexture.zpm3.engine.instances.blocks.ZPTorchBlock;

import java.util.function.Supplier;

public class ZPFadingTorchBlock extends ZPTorchBlock implements EntityBlock, IFadingBlock {
    private final Supplier<Block> turnInto;

    public ZPFadingTorchBlock(@NotNull Properties pProperties, @Nullable ParticleOptions pFlameParticle, float particleRate, @Nullable Supplier<Block> turnInto) {
        super(pProperties, pFlameParticle, particleRate);
        this.turnInto = turnInto;
    }

    @Override
    public @Nullable Supplier<Block> getTurnInto() {
        return this.turnInto;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onPlace(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState oldState, boolean isMoving) {
        super.onPlace(state, level, pos, oldState, isMoving);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return !ZPConstants.FADING_TORCHES ? null : ZPFadingTorchBlock.createTickerHelper(type, ZPBlockEntities.fading_block_entity.get(), ZPFadingBlockEntity::tick);
    }

    @SuppressWarnings("all")
    public static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<E> createTickerHelper(BlockEntityType<E> given, BlockEntityType<A> expected, BlockEntityTicker<? super A> ticker) {
        return given == expected ? (BlockEntityTicker<E>) ticker : null;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return !ZPConstants.FADING_TORCHES ? null : new ZPFadingBlockEntity(pPos, pState, ZPConstants.TORCH_FADING_TIME, true);
    }
}
