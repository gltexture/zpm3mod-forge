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

package ru.gltexture.zpm3.modules.blocks.instances.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import ru.gltexture.zpm3.engine.client.rendering.util.ZPCommonClientUtils;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;
import ru.gltexture.zpm3.engine.instances.blocks.ZPLiquidBlock;

import java.util.function.Supplier;

public class ZPToxicLiquidBlock extends ZPLiquidBlock {
    public ZPToxicLiquidBlock(@NotNull Supplier<? extends FlowingFluid> pFluid, @NotNull Properties pProperties) {
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
            if (random.nextFloat() < 0.01f) {
                Vector3f spawnPos = ZPCommonClientUtils.getParticleSpawnPositionBlockDir(pos, Direction.UP, random, new Vector3f(1.0f, fluidHeight, 1.0f));
                Vector3f motion = ZPRandom.instance.randomVector3f(0.05f, new Vector3f(0.1f));
                ZPCommonClientUtils.emmitToxicParticle(1.15f, spawnPos, motion);
            }
        }
    }
}
