package ru.gltexture.zpm3.modules.common.mixins.impl.common;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import ru.gltexture.zpm3.engine.core.config.builtin.ZPWorldConfig;
import ru.gltexture.zpm3.modules.common.init.ZPBlockEntities;
import ru.gltexture.zpm3.modules.common.instances.block_entities.ZPFadingBlockEntity;
import ru.gltexture.zpm3.modules.common.instances.blocks.fading.IFadingBlock;
import ru.gltexture.zpm3.modules.common.instances.blocks.torch.ZPFadingTorchBlock;
import ru.gltexture.zpm3.engine.instances.blocks.IHotLiquid;

import java.util.function.Supplier;

@Mixin(LiquidBlock.class)
public abstract class ZPFadingLavaMixin implements EntityBlock, IFadingBlock, IHotLiquid {
    @Shadow(remap = false) public abstract FlowingFluid getFluid();
    @Shadow public abstract FluidState getFluidState(BlockState pState);

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return !ZPWorldConfig.FADING_LAVAS.getVar() ? null : new ZPFadingBlockEntity(pPos, pState, ZPWorldConfig.LAVA_FADING_TIME.getVar(), false);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level pLevel, @NotNull BlockState pState, @NotNull BlockEntityType<T> pBlockEntityType) {
        if (!pState.getBlock().equals(Blocks.LAVA) || !this.getFluid().isSource(this.getFluidState(pState))) {
            return null;
        }
        return !ZPWorldConfig.FADING_LAVAS.getVar() ? null : ZPFadingTorchBlock.createTickerHelper(pBlockEntityType, ZPBlockEntities.fading_block_entity.get(), ZPFadingBlockEntity::tick);
    }

    @Inject(method = "randomTick", at = @At("HEAD"))
    private void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom, CallbackInfo ci) {
    }

    @SuppressWarnings("all")
    @Override
    public float bucketFillingChance() {
        return ((Object) this) == Blocks.LAVA ? 0.25f : 1.0f;
    }

    @Override
    public @Nullable Supplier<Block> zpm3forge$getTurnInto() {
        return () -> Blocks.COBBLESTONE;
    }
}