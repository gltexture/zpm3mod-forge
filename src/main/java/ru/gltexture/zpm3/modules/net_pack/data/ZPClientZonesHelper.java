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

package ru.gltexture.zpm3.modules.net_pack.data;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import org.joml.Vector3i;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.zones.ZPZoneManager;
import ru.gltexture.zpm3.modules.net_pack.packets.ZPSendAllZones_StoC_Packet;
import ru.gltexture.zpm3.modules.net_pack.packets.ZPSendTheOnlyZone_StoC_Packet;

import java.util.HashMap;
import java.util.HashSet;

public class ZPClientZonesHelper {
    public static void sendAllZonesTo(ServerPlayer serverPlayer) {
        try {
            ZombiePlague3.net().sendToPlayer(new ZPSendAllZones_StoC_Packet(ZPZoneManager.INSTANCE.getAllZonesOnLevel(serverPlayer.serverLevel())), serverPlayer);
        } catch (final Exception ignored) {
        }
    }

    public static void sendZoneToAll(ZPZoneManager.Zone zone, ServerLevel serverLevel, boolean removed) {
        try {
            ZombiePlague3.net().sendToDimension(new ZPSendTheOnlyZone_StoC_Packet(removed ? new ZPZoneManager.Zone(ZPSendTheOnlyZone_StoC_Packet.REM_FLAG + zone.uniqueId(), new Vector3i(), new Vector3i(), new HashSet<>(), new HashMap<>()) : zone), serverLevel.dimension());
        } catch (final Exception ignored) {
        }
    }
}
