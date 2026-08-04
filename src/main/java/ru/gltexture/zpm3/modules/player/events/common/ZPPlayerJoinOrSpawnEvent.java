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

package ru.gltexture.zpm3.modules.player.events.common;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.network.handler.ZPNetworkHandlerServer;
import ru.gltexture.zpm3.engine.core.config.builtin.ZPCombatConfig;

import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.events.ZPForgeEventHandlerClass;

import java.util.Objects;

public class ZPPlayerJoinOrSpawnEvent implements ZPForgeEventHandlerClass {
    public ZPPlayerJoinOrSpawnEvent() {
    }

    @SubscribeEvent
    public static void onPlayerSpawn(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof ServerPlayer sp) {
            ZPNetworkHandlerServer.sendAllZonesTo(sp);
            if (sp.getAttribute(ForgeMod.ENTITY_REACH.get()) != null) {
                Objects.requireNonNull(sp.getAttribute(ForgeMod.ENTITY_REACH.get())).setBaseValue(ZPCombatConfig.PLAYER_DEFAULT_HAND_REACH_DISTANCE.getVar());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer sp) {
        }
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
