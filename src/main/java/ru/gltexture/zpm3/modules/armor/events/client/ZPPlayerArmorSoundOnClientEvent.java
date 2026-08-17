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

package ru.gltexture.zpm3.modules.armor.events.client;

import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.core.config.builtin.ZPClientConfig;
import ru.gltexture.zpm3.engine.events.ZPForgeEventHandlerClass;
import ru.gltexture.zpm3.engine.sound.ZPLoopedSound;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class ZPPlayerArmorSoundOnClientEvent implements ZPForgeEventHandlerClass {
    private static final Set<TrackedSoundLauncher> trackedSoundLaunchers = new HashSet<>();

    public static void registerArmorSound(@NotNull TrackedSoundLauncher trackedSoundLauncher) {
        ZPPlayerArmorSoundOnClientEvent.trackedSoundLaunchers.add(trackedSoundLauncher);
    }

    public ZPPlayerArmorSoundOnClientEvent() {
    }

    @Override
    public @NotNull ZPSide getSide() {
        return ZPSide.CLIENT;
    }

    @Override
    public @NotNull Mod.EventBusSubscriber.Bus getBus() {
        return Mod.EventBusSubscriber.Bus.FORGE;
    }

    public static void clear() {
        ZPPlayerArmorSoundOnClientEvent.trackedSoundLaunchers.forEach(e -> {
            for (ZPLoopedSound sound : e.getSoundMap().values()) {
                sound.kill();
            }
            e.getSoundMap().clear();
        });
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void tick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        if (Minecraft.getInstance().player == null || !ZPClientConfig.ARMOR_LOOPED_SOUNDS.getVar()) {
            ZPPlayerArmorSoundOnClientEvent.clear();
        } else {
            ZPPlayerArmorSoundOnClientEvent.trackedSoundLaunchers.forEach(e -> {
                Iterator<Map.Entry<UUID, ZPLoopedSound>> iterator = e.getSoundMap().entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<UUID, ZPLoopedSound> entry = iterator.next();
                    ZPLoopedSound sound = entry.getValue();
                    LivingEntity entity = sound.getLivingEntity();
                    if (entity == null || !e.getEntityPredicate().test(entity)) {
                        sound.kill();
                        iterator.remove();
                    }
                }
            });
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void tick(LivingEvent.LivingTickEvent event) {
        if (Minecraft.getInstance().player != null && ZPClientConfig.ARMOR_LOOPED_SOUNDS.getVar()) {
            ZPPlayerArmorSoundOnClientEvent.trackedSoundLaunchers.forEach(e -> {
                if (e.getEntityPredicate().test(event.getEntity())) {
                    if (!e.getSoundMap().containsKey(event.getEntity().getUUID()) || e.getSoundMap().get(event.getEntity().getUUID()).isStopped()) {
                        ZPLoopedSound loopedSound = new ZPLoopedSound(e.getSoundEvent().get(), SoundSource.PLAYERS, e.volume(), e.pitch(), event.getEntity(), 0L);
                        e.getSoundMap().put(event.getEntity().getUUID(), loopedSound);
                        Minecraft.getInstance().getSoundManager().play(loopedSound);
                    }
                }
            });
        }
    }

    public static abstract class TrackedSoundLauncher implements ITrackedSoundLauncher {
        private final Map<UUID, ZPLoopedSound> soundMap;

        public TrackedSoundLauncher() {
            this.soundMap = new ConcurrentHashMap<>();
        }

        final Map<UUID, ZPLoopedSound> getSoundMap() {
            return this.soundMap;
        }
    }

    public interface ITrackedSoundLauncher {
        float pitch();
        float volume();
        @NotNull Supplier<SoundEvent> getSoundEvent();
        @NotNull Predicate<LivingEntity> getEntityPredicate();
    }
}