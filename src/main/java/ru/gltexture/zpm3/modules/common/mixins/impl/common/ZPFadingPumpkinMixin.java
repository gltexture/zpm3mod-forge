package ru.gltexture.zpm3.modules.common.mixins.impl.common;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

import ru.gltexture.zpm3.engine.core.config.builtin.ZPWorldConfig;
import ru.gltexture.zpm3.modules.common.init.ZPBlockEntities;
import ru.gltexture.zpm3.modules.common.instances.block_entities.ZPFadingBlockEntity;
import ru.gltexture.zpm3.modules.common.instances.blocks.torch.IFadingBlock;
import ru.gltexture.zpm3.modules.common.instances.blocks.torch.ZPFadingTorchBlock;
import ru.gltexture.zpm3.modules.common.mixins.ext.ITorchPlayerExt;

import java.util.function.Supplier;

@Mixin(CarvedPumpkinBlock.class)
public class ZPFadingPumpkinMixin implements EntityBlock, IFadingBlock, ITorchPlayerExt {
    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return !ZPWorldConfig.FADING_PUMPKINS.getVar() ? null : new ZPFadingBlockEntity(pPos, pState, ZPWorldConfig.PUMPKIN_FADING_TIME.getVar(), true);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level pLevel, @NotNull BlockState pState, @NotNull BlockEntityType<T> pBlockEntityType) {
        if (!pState.getBlock().equals(Blocks.JACK_O_LANTERN)) {
            return null;
        }
        return !ZPWorldConfig.FADING_PUMPKINS.getVar() ? null : ZPFadingTorchBlock.createTickerHelper(pBlockEntityType, ZPBlockEntities.fading_block_entity.get(), ZPFadingBlockEntity::tick);
    }

    @Override
    public @Nullable Supplier<Block> zpm3forge$getTurnInto() {
        return () -> Blocks.CARVED_PUMPKIN;
    }

    @Override
    public void zpm3forge$setPlacedBy(@NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull BlockState pState, @Nullable LivingEntity pPlacer, @NotNull ItemStack pStack) {
        ZPFadingTorchBlock.activationCheck(pLevel, pPos, pState, pPlacer, pStack);
    }
}