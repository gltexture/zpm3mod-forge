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

package ru.gltexture.zpm3.modules.net_pack.data.events;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.network.handler.ZPNetworkHandlerClient;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.events.ZPForgeEventHandlerClass;
import ru.gltexture.zpm3.engine.service.ZPUtility;
import ru.gltexture.zpm3.modules.net_pack.packets.MIXED.ZPValidateAccessorsPacket;
import ru.gltexture.zpm3.modules.net_pack.packets.MIXED.ZPValidateModePacket;

public class ZPNetSyncEvents implements ZPForgeEventHandlerClass {
    @SubscribeEvent
    public static void onEntitySpawn(EntityJoinLevelEvent event) {
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        ZombiePlague3.netServer().getNetEntDataSyncer().syncDirtyValues();
    }

    @SubscribeEvent
    public static void onStartTracking(PlayerEvent.StartTracking event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }
        Entity target = event.getTarget();
        ZombiePlague3.netServer().getNetEntDataSyncer().markEntityDirty(target, player);
    }

    @SubscribeEvent
    public static void onClientLogout(ClientPlayerNetworkEvent.LoggingOut event) {
        ZPUtility.sides().onlyClient(() -> {
            ZombiePlague3.netClient().getNetEntDataSyncer().clearAll();
        });
    }

    @SubscribeEvent
    public static void onClientLogin(ClientPlayerNetworkEvent.LoggingIn event) {
        ZPUtility.sides().onlyClient(() -> {
            ZombiePlague3.netClient().getNetStaticDataSyncer().broadcastAll();
        });
    }

    @SubscribeEvent
    public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getEntity() instanceof ServerPlayer sp) {
            ZombiePlague3.netServer().getNetStaticDataSyncer().remove(sp);
        }
    }

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer sp) {
            ZombiePlague3.netServer().getNetStaticDataSyncer().add(sp);
            ZombiePlague3.netServer().getNetStaticDataSyncer().broadcastAll(sp);
            ZombiePlague3.netServer().getNetEntDataSyncer().syncPlayerHimSelf(sp);
            ZombiePlague3.netServer().sendToPlayer(new ZPValidateModePacket(false, false), sp);
            ZombiePlague3.netServer().sendToPlayer(new ZPValidateAccessorsPacket("", ""), sp);
        }
    }


    /*
    @SubscribeEvent
public static void onConstruct(EntityEvent.EntityConstructing event) {
}

@SubscribeEvent
public static void onLeave(EntityLeaveLevelEvent event) {
}

@SubscribeEvent
public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
    ZombiePlague3.netServer().getNetDataSyncer().clearEntity(event.getEntity());
}
*/


    @Override
    public @NotNull ZPSide getSide() {
        return ZPSide.COMMON;
    }

    @Override
    public @NotNull Mod.EventBusSubscriber.Bus getBus() {
        return Mod.EventBusSubscriber.Bus.FORGE;
    }
}
