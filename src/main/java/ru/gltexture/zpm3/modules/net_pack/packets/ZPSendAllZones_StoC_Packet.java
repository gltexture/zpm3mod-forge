package ru.gltexture.zpm3.modules.net_pack.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3i;
import ru.gltexture.zpm3.engine.network.ZPNetwork;
import ru.gltexture.zpm3.modules.net_pack.data.ZPClientZonesData;

import java.util.ArrayList;
import java.util.Collection;

public class ZPSendAllZones_StoC_Packet implements ZPNetwork.ZPPacket {
    private final Collection<ZPClientZonesData.ZoneData> zones;

    public ZPSendAllZones_StoC_Packet(Collection<ZPClientZonesData.ZoneData> zones) {
        this.zones = zones;
    }

    public ZPSendAllZones_StoC_Packet(FriendlyByteBuf buf) {
        int size = buf.readInt();
        this.zones = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            zones.add(new ZPClientZonesData.ZoneData(
                    buf.readUtf(),
                    new Vector3i(buf.readInt(), buf.readInt(), buf.readInt()),
                    new Vector3i(buf.readInt(), buf.readInt(), buf.readInt()),
                    buf.readUtf()
            ));
        }
    }

    public static Encoder<ZPSendAllZones_StoC_Packet> encoder() {
        return (packet, buf) -> {
            buf.writeInt(packet.zones.size());

            for (ZPClientZonesData.ZoneData z : packet.zones) {
                buf.writeUtf(z.id());
                buf.writeInt(z.min().x());
                buf.writeInt(z.min().y());
                buf.writeInt(z.min().z());
                buf.writeInt(z.max().x());
                buf.writeInt(z.max().y());
                buf.writeInt(z.max().z());
                buf.writeUtf(z.flags());
            }
        };
    }

    public static Decoder<ZPSendAllZones_StoC_Packet> decoder() {
        return ZPSendAllZones_StoC_Packet::new;
    }

    @Override
    public void onServer(@NotNull Player sender, @NotNull ServerLevel level) {
    }

    @Override
    public void onClient(@NotNull Player player) {
        ZPClientZonesData.zoneDataList.clear();
        for (ZPClientZonesData.ZoneData z : zones) {
            ZPClientZonesData.zoneDataList.put(z.id(), z);
        }
    }
}