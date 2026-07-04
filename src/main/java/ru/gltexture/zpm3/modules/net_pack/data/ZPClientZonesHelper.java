package ru.gltexture.zpm3.modules.net_pack.data;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.zones.ZPFlagZones;
import ru.gltexture.zpm3.modules.net_pack.packets.ZPSendAllZones_StoC_Packet;
import ru.gltexture.zpm3.modules.net_pack.packets.ZPSendTheOnlyZone_StoC_Packet;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

public class ZPClientZonesHelper {
    public static Collection<ZPClientZonesData.ZoneData> CONVERT(Collection<ZPFlagZones.Zone> zones) {
        return zones.stream().map(e -> new ZPClientZonesData.ZoneData(e.uniqueId(), e.min(), e.max(), e.flags().stream().map(Enum::name).collect(Collectors.joining(";")))).collect(Collectors.toList());
    }

    public static ZPClientZonesData.ZoneData CONVERT(ZPFlagZones.Zone e, boolean removed) {
        return new ZPClientZonesData.ZoneData(e.uniqueId(), e.min(), e.max(), removed ? ZPSendTheOnlyZone_StoC_Packet.REM_FLAG : e.flags().stream().map(Enum::name).collect(Collectors.joining(";")));
    }

    public static void sendAllZonesTo(ServerPlayer serverPlayer) {
        try {
            ZombiePlague3.net().sendToPlayer(new ZPSendAllZones_StoC_Packet(ZPClientZonesHelper.CONVERT(Objects.requireNonNull(ZPFlagZones.INSTANCE.getAllZonesOnLevel(serverPlayer.serverLevel())))), serverPlayer);
        } catch (final Exception ignored) {
        }
    }

    public static void sendZoneToAll(ZPFlagZones.Zone zone, ServerLevel serverLevel, boolean removed) {
        try {
            ZombiePlague3.net().sendToDimension(new ZPSendTheOnlyZone_StoC_Packet(ZPClientZonesHelper.CONVERT(zone, removed)), serverLevel.dimension());
        } catch (final Exception ignored) {
        }
    }
}
