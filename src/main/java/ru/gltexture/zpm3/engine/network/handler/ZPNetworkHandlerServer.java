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

package ru.gltexture.zpm3.engine.network.handler;

import java.util.*;

import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3i;
import ru.gltexture.zpm3.engine.network.ZPNetwork;
import ru.gltexture.zpm3.modules.commands.zones.ZPZoneManager;
import ru.gltexture.zpm3.modules.net_pack.data.data_ent.ZPNetEntDataSyncer;
import ru.gltexture.zpm3.modules.net_pack.data.data_static.ZPNetStaticDataSyncerServer;
import ru.gltexture.zpm3.modules.net_pack.packets.S2C.ZPSendAllZones_Packet;
import ru.gltexture.zpm3.modules.net_pack.packets.S2C.ZPSendTheOnlyZone_Packet;

import java.util.function.Predicate;

public final class ZPNetworkHandlerServer extends ZPNetworkHandler {
    public static ZPNetworkHandlerServer instance = new ZPNetworkHandlerServer();

    public static void init() {
        ZPNetworkHandlerServer.instance = new ZPNetworkHandlerServer();
    }

    public static ZPNetworkHandlerServer get() {
        return ZPNetworkHandlerServer.instance;
    }

    ZPNetworkHandlerServer() {
        super(new ZPNetStaticDataSyncerServer(), new ZPNetEntDataSyncer(), Side.SERVER);
    }

    @Override
    public ZPNetEntDataSyncer getNetEntDataSyncer() {
        return (ZPNetEntDataSyncer) super.getNetEntDataSyncer();
    }

    @Override
    public ZPNetStaticDataSyncerServer getNetStaticDataSyncer() {
        return (ZPNetStaticDataSyncerServer) super.getNetStaticDataSyncer();
    }

    public static void sendAllZonesTo(@NotNull ServerPlayer serverPlayer) {
        try {
            ZPNetworkHandlerServer.instance.sendToPlayer(new ZPSendAllZones_Packet(ZPZoneManager.INSTANCE.getAllZonesOnLevel(serverPlayer.serverLevel())), serverPlayer);
        } catch (final Exception ignored) {
        }
    }

    public static void sendZoneToAll(@NotNull ZPZoneManager.Zone zone, @NotNull ServerLevel serverLevel, boolean removed) {
        try {
            ZPNetworkHandlerServer.instance.sendToDimension(new ZPSendTheOnlyZone_Packet(removed ? new ZPZoneManager.Zone(ZPSendTheOnlyZone_Packet.REM_FLAG + zone.uniqueId(), new Vector3i(), new Vector3i(), new HashSet<>(), new HashMap<>()) : zone), serverLevel.dimension());
        } catch (final Exception ignored) {
        }
    }

    @Override
    public boolean isServer() {
        return true;
    }

    public void sendToPlayer(@NotNull ZPNetwork.ZPPacket packet, @NotNull ServerPlayer player) {
        this.getNetwork().getMainChannel().send(PacketDistributor.PLAYER.with(() -> player), packet);
    }

    public void sendToAll(@NotNull ZPNetwork.ZPPacket packet) {
        this.getNetwork().getMainChannel().send(PacketDistributor.ALL.noArg(), packet);
    }

    public void sendToAllTracking(@NotNull Entity tracking, @NotNull ZPNetwork.ZPPacket packet) {
        this.getNetwork().getMainChannel().send(PacketDistributor.TRACKING_ENTITY.with(() -> tracking), packet);
    }

    public void sendToAllTrackingAndSelf(@NotNull ServerPlayer tracking, @NotNull ZPNetwork.ZPPacket packet) {
        this.getNetwork().getMainChannel().send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> tracking), packet);
    }

    public void sendToPlayersIf(@NotNull ZPNetwork.ZPPacket packet, @NotNull Level level, @NotNull Predicate<ServerPlayer> ifFun) {
        for (Player player : level.players()) {
            if (player instanceof ServerPlayer serverPlayer) {
                if (ifFun.test(serverPlayer)) {
                    this.sendToPlayer(packet, serverPlayer);
                }
            }
        }
    }

    public void sendToRadius(@NotNull ZPNetwork.ZPPacket packet, @NotNull Level level, @NotNull Vec3 center, double radius) {
        for (Player player : level.players()) {
            ServerPlayer serverPlayer = (ServerPlayer) player;
            if (center.distanceTo(serverPlayer.position()) <= radius) {
                this.sendToPlayer(packet, serverPlayer);
            }
        }
    }

    public void sendToDimension(@NotNull ZPNetwork.ZPPacket packet, @NotNull ResourceKey<Level> dimension) {
        final MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if (server == null) {
            return;
        }

        final ServerLevel level = server.getLevel(dimension);
        if (level == null) {
            return;
        }

        for (ServerPlayer player : level.players()) {
            this.sendToPlayer(packet, player);
        }
    }

    public void sendToDimensionRadius(@NotNull ZPNetwork.ZPPacket packet, @NotNull ResourceKey<Level> dimension, @NotNull Vec3 center, double radius) {
        final MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if (server == null) {
            return;
        }

        final ServerLevel level = server.getLevel(dimension);
        if (level == null) {
            return;
        }

        for (ServerPlayer player : level.players()) {
            if (center.distanceTo(player.position()) <= radius) {
                this.sendToPlayer(packet, player);
            }
        }
    }
}
