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

package ru.gltexture.zpm3.modules.entity.events.common;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.config.builtin.ZPZombieConfig;
import ru.gltexture.zpm3.engine.zones.ZPZoneChecks;

import ru.gltexture.zpm3.modules.entity.instances.mobs.ai.ZPZombieMiningGoal;
import ru.gltexture.zpm3.modules.net_pack.packets.ZPBlockCrackPacket;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;
import ru.gltexture.zpm3.engine.events.ZPEventClass;
import ru.gltexture.zpm3.engine.service.Pair;

import java.util.*;

public class ZPWorldTickEvent implements ZPEventClass {
    public static int MAX_ZOMBIES_IN_CHUNK = ZPZombieConfig.MAX_ZOMBIES_SPAWN_IN_CHUNK.getVar();
    private static Map<ResourceKey<Level>, Integer> ticks = new HashMap<>();

    public ZPWorldTickEvent() {
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void exec1(@NotNull BlockEvent.BreakEvent event) {
        if (event.getLevel() instanceof ServerLevel serverLevel) {
            if (!event.getPlayer().isCreative()) {
                if (ZPZoneChecks.INSTANCE.isNoBlocksDestruction(serverLevel, event.getPos())) {
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void exec11(@NotNull BlockEvent.EntityPlaceEvent event) {
        if (event.getLevel() instanceof ServerLevel serverLevel) {
            if (event.getEntity() instanceof Player player && !player.isCreative()) {
                if (ZPZoneChecks.INSTANCE.isNoBlocksDestruction(serverLevel, event.getPos())) {
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void exec2(@NotNull LivingDamageEvent event) {
        if (event.getEntity().level() instanceof ServerLevel serverLevel) {
            if (event.getEntity() instanceof Player attacked) {
                if (ZPZoneChecks.INSTANCE.isNoPlayersDamage(serverLevel, attacked)) {
                    event.setCanceled(true);
                }
                if (event.getSource().getEntity() instanceof Player attacker) {
                    if (ZPZoneChecks.INSTANCE.isNoPlayersPvp(serverLevel, attacked)) {
                        event.setCanceled(true);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void tick(TickEvent.LevelTickEvent event) {
        if (event.level.isClientSide() || event.phase != TickEvent.Phase.END) {
            return;
        }
        if (event.level.getDifficulty() == Difficulty.HARD) {
            ZPWorldTickEvent.MAX_ZOMBIES_IN_CHUNK = ZPZombieConfig.MAX_ZOMBIES_SPAWN_IN_CHUNK.getVar();
        } else if (event.level.getDifficulty() == Difficulty.NORMAL) {
            ZPWorldTickEvent.MAX_ZOMBIES_IN_CHUNK = (int) (ZPZombieConfig.MAX_ZOMBIES_SPAWN_IN_CHUNK.getVar() * 0.8f);
        } else if (event.level.getDifficulty() == Difficulty.EASY) {
            ZPWorldTickEvent.MAX_ZOMBIES_IN_CHUNK = (int) (ZPZombieConfig.MAX_ZOMBIES_SPAWN_IN_CHUNK.getVar() * 0.6f);
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
                    ZombiePlague3.net().sendToDimensionRadius(new ZPBlockCrackPacket(strength / updTicks, pos.getX(), pos.getY(), pos.getZ()), dim, pos.getCenter(), 64.0f);
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
