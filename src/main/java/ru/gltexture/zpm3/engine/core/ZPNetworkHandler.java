package ru.gltexture.zpm3.engine.core;

import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.network.ZPNetwork;

import java.util.function.Predicate;

public final class ZPNetworkHandler {
    static final ZPNetworkHandler instance = new ZPNetworkHandler();

    private ZPNetwork network;

    ZPNetworkHandler() {
        this.network = null;
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

    public boolean isValid() {
        return this.network != null;
    }
}
