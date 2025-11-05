package ru.gltexture.zpm3.assets.common.mixins.impl.common;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import ru.gltexture.zpm3.assets.common.global.ZPConstants;
import ru.gltexture.zpm3.assets.common.init.ZPBlockEntities;
import ru.gltexture.zpm3.assets.common.instances.block_entities.ZPFadingBlockEntity;
import ru.gltexture.zpm3.assets.common.instances.blocks.torch.IFadingBlock;
import ru.gltexture.zpm3.assets.common.instances.blocks.torch.ZPFadingBlock;

import java.util.function.Supplier;

@Mixin(CarvedPumpkinBlock.class)
public class PumpkinMixin implements EntityBlock, IFadingBlock {
    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return !ZPConstants.FADING_PUMPKINS ? null : new ZPFadingBlockEntity(pPos, pState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level pLevel, @NotNull BlockState pState, @NotNull BlockEntityType<T> pBlockEntityType) {
        if (!pState.getBlock().equals(Blocks.JACK_O_LANTERN)) {
            return null;
        }
        return !ZPConstants.FADING_PUMPKINS ? null : ZPFadingBlock.createTickerHelper(pBlockEntityType, ZPBlockEntities.fading_block_entity.get(), ZPFadingBlockEntity::tick);
    }

    @Override
    public @Nullable Supplier<Block> getTurnInto() {
        return () -> Blocks.CARVED_PUMPKIN;
    }
}