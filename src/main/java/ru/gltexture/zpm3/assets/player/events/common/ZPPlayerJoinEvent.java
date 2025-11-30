package ru.gltexture.zpm3.assets.player.events.common;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.extensions.IForgePlayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.global.ZPConstants;
import ru.gltexture.zpm3.assets.net_pack.packets.ZPSendGlobalSettingsToClients;
import ru.gltexture.zpm3.assets.player.ZPPlayerAsset;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.events.ZPEventClass;

import java.util.Objects;

public class ZPPlayerJoinEvent implements ZPEventClass {
    public ZPPlayerJoinEvent() {
    }

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer sp)) {
            return;
        }

        ZombiePlague3.net().sendToPlayer(new ZPSendGlobalSettingsToClients(ZPConstants.ENABLE_HARDCORE_DARKNESS_SERVER_SIDE, ZPConstants.DARKNESS_GAMMA_STATIC_FACTOR_SERVER_SIDE), sp);

        if (event.getEntity().getAttribute(ForgeMod.ENTITY_REACH.get()) != null) {
            Objects.requireNonNull(event.getEntity().getAttribute(ForgeMod.ENTITY_REACH.get())).setBaseValue(ZPConstants.PLAYER_DEFAULT_HAND_REACH);
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
