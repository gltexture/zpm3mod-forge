package ru.gltexture.zpm3.modules.player.events.common;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.mixins.ext.IZPPlayerMixinExt;
import ru.gltexture.zpm3.modules.common.global.ZPConstants;
import ru.gltexture.zpm3.modules.net_pack.packets.ZPSyncConfigSettings;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.events.ZPEventClass;
import ru.gltexture.zpm3.engine.service.ZPUtility;

import java.util.Objects;

public class ZPPlayerJoinOrSpawnEvent implements ZPEventClass {
    public ZPPlayerJoinOrSpawnEvent() {
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onPlayerSpawn(EntityJoinLevelEvent event) {
        ZPUtility.sides().onlyClient(() -> {
            if (event.getEntity() instanceof LocalPlayer) {
                ZombiePlague3.net().sendToServer(new ZPSyncConfigSettings(ZombiePlague3.net().createdNetSyncDataPack_CtoS()));
            }
        });
        if (event.getEntity() instanceof ServerPlayer sp) {
            if (sp.getAttribute(ForgeMod.ENTITY_REACH.get()) != null) {
                Objects.requireNonNull(sp.getAttribute(ForgeMod.ENTITY_REACH.get())).setBaseValue(ZPConstants.PLAYER_DEFAULT_HAND_REACH_DISTANCE);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer sp) {
            ZombiePlague3.net().sendToPlayer(new ZPSyncConfigSettings(ZombiePlague3.net().createdNetSyncDataPack_StoC()), sp);
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
