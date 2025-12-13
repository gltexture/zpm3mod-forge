package ru.gltexture.zpm3.engine.fake;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.level.BlockEvent;

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
