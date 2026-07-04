package ru.gltexture.zpm3.modules.net_pack.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3i;
import ru.gltexture.zpm3.engine.network.ZPNetwork;
import ru.gltexture.zpm3.modules.net_pack.data.ZPClientZonesData;

public class ZPSendTheOnlyZone_StoC_Packet implements ZPNetwork.ZPPacket {
    public static final String REM_FLAG = "$NULL$";
    private final ZPClientZonesData.ZoneData zone;

    public ZPSendTheOnlyZone_StoC_Packet(ZPClientZonesData.ZoneData zone) {
        this.zone = zone;
    }

    public ZPSendTheOnlyZone_StoC_Packet(FriendlyByteBuf buf) {
        this.zone = new ZPClientZonesData.ZoneData(
                buf.readUtf(),
                new Vector3i(buf.readInt(), buf.readInt(), buf.readInt()),
                new Vector3i(buf.readInt(), buf.readInt(), buf.readInt()),
                buf.readUtf()
        );
    }

    public static Encoder<ZPSendTheOnlyZone_StoC_Packet> encoder() {
        return (packet, buf) -> {
            ZPClientZonesData.ZoneData z = packet.zone;

            buf.writeUtf(z.id());

            buf.writeInt(z.min().x());
            buf.writeInt(z.min().y());
            buf.writeInt(z.min().z());

            buf.writeInt(z.max().x());
            buf.writeInt(z.max().y());
            buf.writeInt(z.max().z());

            buf.writeUtf(z.flags());
        };
    }

    public static Decoder<ZPSendTheOnlyZone_StoC_Packet> decoder() {
        return ZPSendTheOnlyZone_StoC_Packet::new;
    }

    @Override
    public void onServer(@NotNull Player sender, @NotNull ServerLevel level) {
    }

    @Override
    public void onClient(@NotNull Player player) {
        if (this.zone.flags().equals(ZPSendTheOnlyZone_StoC_Packet.REM_FLAG)) {
            ZPClientZonesData.zoneDataList.remove(zone.id());
        } else {
            ZPClientZonesData.zoneDataList.put(zone.id(), zone);
        }
    }
}