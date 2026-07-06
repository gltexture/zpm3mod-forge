package ru.gltexture.zpm3.modules.net_pack.data;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import org.joml.Vector3i;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.zones.ZPZoneManager;
import ru.gltexture.zpm3.modules.net_pack.packets.ZPSendAllZones_StoC_Packet;
import ru.gltexture.zpm3.modules.net_pack.packets.ZPSendTheOnlyZone_StoC_Packet;

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
            ZombiePlague3.net().sendToDimension(new ZPSendTheOnlyZone_StoC_Packet(removed ? new ZPZoneManager.Zone(ZPSendTheOnlyZone_StoC_Packet.REM_FLAG + zone.uniqueId(), new Vector3i(), new Vector3i(), new HashSet<>()) : zone), serverLevel.dimension());
        } catch (final Exception ignored) {
        }
    }
}
