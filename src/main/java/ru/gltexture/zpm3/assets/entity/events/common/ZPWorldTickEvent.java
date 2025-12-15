package ru.gltexture.zpm3.assets.entity.events.common;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.entity.instances.mobs.ai.ZPZombieMiningGoal;
import ru.gltexture.zpm3.assets.net_pack.packets.ZPBlockCrack;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;
import ru.gltexture.zpm3.engine.events.ZPEventClass;
import ru.gltexture.zpm3.engine.service.Pair;

import java.util.*;

public class ZPWorldTickEvent implements ZPEventClass {
    private static Map<ResourceKey<Level>, Integer> ticks = new HashMap<>();

    public ZPWorldTickEvent() {
    }

    @SubscribeEvent
    public static void tick(TickEvent.LevelTickEvent event) {
        if (event.level.isClientSide() || event.phase != TickEvent.Phase.END) {
            return;
        }
        final ResourceKey<Level> dim = event.level.dimension();
        int t = ticks.getOrDefault(dim, 0) + 1;
        final int updTicks = 5;
        if (t >= updTicks) {
            if (event.level.dimension().equals(Level.OVERWORLD)) {
                //System.out.println(ZPZombieMiningGoal.affectedBlocks.size());
            }
            Iterator<Map.Entry<BlockPos, Pair<Integer, ResourceKey<Level>>>> it = ZPZombieMiningGoal.affectedBlocks.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<BlockPos, Pair<Integer, ResourceKey<Level>>> e = it.next();
                Pair<Integer, ResourceKey<Level>> data = e.getValue();
                if (data.second().equals(dim)) {
                    BlockPos pos = e.getKey();
                    int strength = data.first();
                    event.level.playSound(null, pos, event.level.getBlockState(pos).getSoundType().getBreakSound(), SoundSource.BLOCKS, Mth.clamp(0.5f + strength * 0.05f, 0.25f, 1.0f), 0.65f + ZPRandom.getRandom().nextFloat(0.25f));
                    ZombiePlague3.net().sendToDimensionRadius(new ZPBlockCrack(strength / updTicks, pos.getX(), pos.getY(), pos.getZ()), dim, pos.getCenter(), 64.0f);
                    it.remove();
                }
            }
            t = 0;
        }
        ticks.put(dim, t);
    }

    @Override
    public @NotNull ZPSide getSide() {
        return ZPSide.COMMON;
    }

    @Override
    public @NotNull Mod.EventBusSubscriber.Bus getBus() {
        return Mod.EventBusSubscriber.Bus.FORGE;
    }
}
