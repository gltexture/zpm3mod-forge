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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import ru.gltexture.zpm3.engine.client.rendering.util.ZPCommonClientUtils;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;

public class ZPUraniumBlock extends Block {
    public ZPUraniumBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void animateTick(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (level.isClientSide) {
            ZPUraniumBlock.uraniumParticles(state, level, pos, random);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void uraniumParticles(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (!level.isClientSide) {
            return;
        }

        for (Direction direction : Direction.values()) {
            BlockPos offsetPos = pos.relative(direction);
            BlockState adjacentState = level.getBlockState(offsetPos);

            if (!adjacentState.isSolidRender(level, offsetPos)) {
                if (random.nextFloat() < 0.15f) {
                    Vector3f spawnPos = ZPCommonClientUtils.getParticleSpawnPositionBlockDir(pos, direction, random, new Vector3f(1.0f));
                    Vector3f motion = ZPRandom.instance.randomVector3f(0.05f, new Vector3f(0.1f));
                    ZPCommonClientUtils.emmitUraniumParticle(0.5f, spawnPos, motion);
                }
            }
        }
    }
}
