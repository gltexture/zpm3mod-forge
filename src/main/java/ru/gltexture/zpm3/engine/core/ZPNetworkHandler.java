package ru.gltexture.zpm3.engine.core;

import java.util.*;

import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.network.ZPNetwork;
import ru.gltexture.zpm3.modules.net_pack.data.ZPNetSyncDataPack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class ZPNetworkHandler {
    static final NetSyncDataFabric CtoS_DataPackConstructor = new NetSyncDataFabric();
    static final NetSyncDataFabric StoC_DataPackConstructor = new NetSyncDataFabric();

    static final ZPNetworkHandler instance = new ZPNetworkHandler();
    private ZPNetwork network;

    ZPNetworkHandler() {
        this.network = null;
    }

    public void registerSyncedData__Client_to_Server(@NotNull NetSyncDataFabric.Builder zpNetSyncDataPackBuilder) {
        ZPNetworkHandler.CtoS_DataPackConstructor.addBuilder(zpNetSyncDataPackBuilder);
    }

    public void registerSyncedData__Server_to_Client(@NotNull NetSyncDataFabric.Builder zpNetSyncDataPackBuilder) {
        ZPNetworkHandler.StoC_DataPackConstructor.addBuilder(zpNetSyncDataPackBuilder);
    }

    public ZPNetSyncDataPack createdNetSyncDataPack_CtoS() {
        return ZPNetworkHandler.CtoS_DataPackConstructor.construct();
    }

    public ZPNetSyncDataPack createdNetSyncDataPack_StoC() {
        return ZPNetworkHandler.StoC_DataPackConstructor.construct();
    }

    public void setNetwork(@NotNull ZPNetwork zpNetwork) {
        this.network = zpNetwork;
    }

    public void sendToServer(ZPNetwork.ZPPacket packet) {
        this.network.getMainChannel().sendToServer(packet);
    }

    public void sendToPlayer(ZPNetwork.ZPPacket packet, ServerPlayer player) {
        this.network.getMainChannel().send(PacketDistributor.PLAYER.with(() -> player), packet);
    }

    public void sendToAll(ZPNetwork.ZPPacket packet) {
        this.network.getMainChannel().send(PacketDistributor.ALL.noArg(), packet);
    }

    public void sendToPlayersIf(ZPNetwork.ZPPacket packet, Level level, Predicate<ServerPlayer> ifFun) {
        for (Player player : level.players()) {
            if (player instanceof ServerPlayer serverPlayer) {
                if (ifFun.test(serverPlayer)) {
                    this.sendToPlayer(packet, serverPlayer);
                }
            }
        }
    }

    public void sendToRadius(ZPNetwork.ZPPacket packet, Level level, Vec3 center, double radius) {
        for (Player player : level.players()) {
            ServerPlayer serverPlayer = (ServerPlayer) player;
            if (center.distanceTo(serverPlayer.position()) <= radius) {
                this.sendToPlayer(packet, serverPlayer);
            }
        }
    }

    public void sendToDimension(ZPNetwork.ZPPacket packet, ResourceKey<Level> dimension) {
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

    public void sendToDimensionRadius(ZPNetwork.ZPPacket packet, ResourceKey<Level> dimension, Vec3 center, double radius) {
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

    public boolean isValid() {
        return this.network != null;
    }

    public static class NetSyncDataFabric {
        private final List<Builder> builders;

        public NetSyncDataFabric() {
            this.builders = new ArrayList<>();
        }

        public ZPNetSyncDataPack construct() {
            ZPNetSyncDataPack zpNetSyncDataPack = new ZPNetSyncDataPack();
            this.builders.forEach(builder -> {
                builder.supplierMap.forEach((k, v) -> {
                    zpNetSyncDataPack.set(k, v.get());
                });
            });
            return zpNetSyncDataPack;
        }

        public void addBuilder(NetSyncDataFabric.Builder zpNetSyncDataPackBuilder) {
            this.builders.add(zpNetSyncDataPackBuilder);
        }

        public static class Builder {
            private final Map<String, Supplier<Object>> supplierMap;

            public Builder() {
                this.supplierMap = new HashMap<>();
            }

            public Builder addInt(String key, Supplier<Integer> supplier) {
                this.supplierMap.put(key, supplier::get);
                return this;
            }

            public Builder addFloat(String key, Supplier<Float> supplier) {
                this.supplierMap.put(key, supplier::get);
                return this;
            }

            public Builder addDouble(String key, Supplier<Double> supplier) {
                this.supplierMap.put(key, supplier::get);
                return this;
            }

            public Builder addBoolean(String key, Supplier<Boolean> supplier) {
                this.supplierMap.put(key, supplier::get);
                return this;
            }

            Map<String, Supplier<Object>> getSupplierMap() {
                return this.supplierMap;
            }
        }
    }
}
