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

package ru.gltexture.zpm3.engine.fake;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.level.BlockEvent;

//@Deprecated
public class ZPFakePlayer {
    public static FakePlayer getFakePlayer(ServerLevel world) {
        return FakePlayerFactory.getMinecraft(world);
    }

    public static boolean canBreakBlock(ServerLevel world, BlockPos pos) {
        FakePlayer fakePlayer = getFakePlayer(world);
        if (!net.minecraftforge.common.ForgeHooks.canEntityDestroy(world, pos, fakePlayer)) {
            return false;
        }
        BlockState state = world.getBlockState(pos);
        BlockEvent.BreakEvent event = new BlockEvent.BreakEvent(world, pos, state, fakePlayer);
        MinecraftForge.EVENT_BUS.post(event);
        return !event.isCanceled();
    }
}
