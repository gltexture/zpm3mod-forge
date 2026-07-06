package ru.gltexture.zpm3.modules.net_pack.packets;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3i;
import ru.gltexture.zpm3.engine.network.ZPNetwork;
import ru.gltexture.zpm3.engine.zones.ZPZoneFlag;
import ru.gltexture.zpm3.engine.zones.ZPZoneManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class ZPSendAllZones_StoC_Packet implements ZPNetwork.ZPPacket {
    private final Collection<ZPZoneManager.Zone> zones;

    public ZPSendAllZones_StoC_Packet(Collection<ZPZoneManager.Zone> zones) {
        this.zones = zones;
    }

    public ZPSendAllZones_StoC_Packet(FriendlyByteBuf buf) {
        int size = buf.readInt();
        this.zones = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            zones.add(new ZPZoneManager.Zone(
                    buf.readUtf(),
                    new Vector3i(buf.readInt(), buf.readInt(), buf.readInt()),
                    new Vector3i(buf.readInt(), buf.readInt(), buf.readInt()),
                    Arrays.stream(buf.readUtf().split(ZPSendTheOnlyZone_StoC_Packet.SEPARATOR)).map(ZPZoneFlag::valueOf).collect(Collectors.toSet())
            ));
        }
    }

    public static Encoder<ZPSendAllZones_StoC_Packet> encoder() {
        return (packet, buf) -> {
            buf.writeInt(packet.zones.size());

            for (ZPZoneManager.Zone z : packet.zones) {
                buf.writeUtf(z.uniqueId());
                buf.writeInt(z.start().x());
                buf.writeInt(z.start().y());
                buf.writeInt(z.start().z());
                buf.writeInt(z.end().x());
                buf.writeInt(z.end().y());
                buf.writeInt(z.end().z());
                buf.writeUtf(z.flags().stream().map(ZPZoneFlag::toString).collect(Collectors.joining(ZPSendTheOnlyZone_StoC_Packet.SEPARATOR)));
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
        ZPZoneManager.INSTANCE.REPLACE_CLIENT_MAP((ClientLevel) player.level(), zones);
    }
}