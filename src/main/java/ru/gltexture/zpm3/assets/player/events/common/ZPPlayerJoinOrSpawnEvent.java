package ru.gltexture.zpm3.assets.player.events.common;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.global.ZPConstants;
import ru.gltexture.zpm3.assets.net_pack.packets.ZPSendGlobalSettings_CtoS;
import ru.gltexture.zpm3.assets.net_pack.packets.ZPSendGlobalSettings_StoC;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.events.ZPEventClass;
import ru.gltexture.zpm3.engine.service.ZPUtility;

import java.util.Objects;

public class ZPPlayerJoinOrSpawnEvent implements ZPEventClass {
    public ZPPlayerJoinOrSpawnEvent() {
    }

    @SubscribeEvent
    public static void onPlayerSpawn(EntityJoinLevelEvent event) {
        ZPUtility.sides().onlyClient(() -> {
            if (event.getEntity() instanceof LocalPlayer) {
                ZombiePlague3.net().sendToServer(new ZPSendGlobalSettings_CtoS(ZPConstants.PICK_UP_ON_F));
            }
        });
    }

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer sp)) {
            return;
        }
        ZombiePlague3.net().sendToPlayer(ZPSendGlobalSettings_StoC.create(), sp);
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
