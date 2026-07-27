/*
 *
 *  * zpm3forge
 *  * Copyright (C) 2026 gltexture
 *  *
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program. If not, see <https://www.gnu.org/licenses/>.
 *
 */

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
