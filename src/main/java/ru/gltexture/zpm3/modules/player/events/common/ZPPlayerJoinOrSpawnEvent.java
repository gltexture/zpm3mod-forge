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
import ru.gltexture.zpm3.engine.core.ZPNetworkHandler;
import ru.gltexture.zpm3.engine.core.config.builtin.ZPCombatConfig;

import ru.gltexture.zpm3.modules.net_pack.data.ZPClientZonesHelper;
import ru.gltexture.zpm3.modules.net_pack.packets.ZPSyncConfigSettingsPacket;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.events.ZPForgeEventHandlerClass;
import ru.gltexture.zpm3.engine.service.ZPUtility;
import ru.gltexture.zpm3.modules.net_pack.packets.ZPValidateModePacket;

import java.util.Objects;

public class ZPPlayerJoinOrSpawnEvent implements ZPForgeEventHandlerClass {
    public ZPPlayerJoinOrSpawnEvent() {
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onPlayerSpawn(EntityJoinLevelEvent event) {
        ZPUtility.sides().onlyClient(() -> {
            if (event.getEntity() instanceof LocalPlayer) {
                ZombiePlague3.net().sendToServer(new ZPSyncConfigSettingsPacket(ZombiePlague3.net().createdNetSyncDataPack_CtoS()));
            }
        });
        if (event.getEntity() instanceof ServerPlayer sp) {
            ZPClientZonesHelper.sendAllZonesTo(sp);
            if (sp.getAttribute(ForgeMod.ENTITY_REACH.get()) != null) {
                Objects.requireNonNull(sp.getAttribute(ForgeMod.ENTITY_REACH.get())).setBaseValue(ZPCombatConfig.PLAYER_DEFAULT_HAND_REACH_DISTANCE.getVar());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer sp) {
            ZombiePlague3.net().sendToPlayer(new ZPValidateModePacket(false, false), sp);
            ZombiePlague3.net().sendToPlayer(new ZPSyncConfigSettingsPacket(ZPNetworkHandler.getNetDataPack_FromServer()), sp);
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
