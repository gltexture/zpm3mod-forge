package ru.gltexture.zpm3.engine.helpers;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import org.apache.commons.compress.compressors.lz77support.LZ77Compressor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public abstract class ZPBlocksRenderLayerHelper {
    public static final Set<BlockPair> blockPairSet = new HashSet<>();
    public static final Set<LiquidPair> liquidPairs = new HashSet<>();

    @Deprecated
    public static void addBlockRenderLayerData(@NotNull BlockPair blockPair) {
        ZPBlocksRenderLayerHelper.blockPairSet.add(blockPair);
    }

    public static void addLiquidRenderLayerData(@NotNull LiquidPair blockPair) {
        ZPBlocksRenderLayerHelper.liquidPairs.add(blockPair);
    }

    public static void clearAll() {
        ZPBlocksRenderLayerHelper.blockPairSet.clear();
        ZPBlocksRenderLayerHelper.liquidPairs.clear();
    }

    @Deprecated
    public record BlockPair(@NotNull Supplier<Block> fluid, @NotNull RenderType type) { ; }
    public record LiquidPair(@NotNull Supplier<Fluid> fluid, @NotNull RenderType type) { ; }
}
