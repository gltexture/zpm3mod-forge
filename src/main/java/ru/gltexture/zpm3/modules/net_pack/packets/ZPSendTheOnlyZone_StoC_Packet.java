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

import java.util.Arrays;
import java.util.stream.Collectors;

public class ZPSendTheOnlyZone_StoC_Packet implements ZPNetwork.ZPPacket {
    public static final String SEPARATOR = ";";
    public static final String REM_FLAG = "$NULL$";
    private final ZPZoneManager.Zone zone;

    public ZPSendTheOnlyZone_StoC_Packet(ZPZoneManager.Zone zone) {
        this.zone = zone;
    }

    public ZPSendTheOnlyZone_StoC_Packet(FriendlyByteBuf buf) {
        this.zone = new ZPZoneManager.Zone(
                buf.readUtf(),
                new Vector3i(buf.readInt(), buf.readInt(), buf.readInt()),
                new Vector3i(buf.readInt(), buf.readInt(), buf.readInt()),
                Arrays.stream(buf.readUtf().split(ZPSendTheOnlyZone_StoC_Packet.SEPARATOR)).map(ZPZoneFlag::valueOf).collect(Collectors.toSet())
        );
    }

    public static Encoder<ZPSendTheOnlyZone_StoC_Packet> encoder() {
        return (packet, buf) -> {
            ZPZoneManager.Zone z = packet.zone;

            buf.writeUtf(z.uniqueId());

            buf.writeInt(z.start().x());
            buf.writeInt(z.start().y());
            buf.writeInt(z.start().z());

            buf.writeInt(z.end().x());
            buf.writeInt(z.end().y());
            buf.writeInt(z.end().z());

            buf.writeUtf(z.flags().stream().map(ZPZoneFlag::toString).collect(Collectors.joining(ZPSendTheOnlyZone_StoC_Packet.SEPARATOR)));
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
        if (this.zone.uniqueId().startsWith(ZPSendTheOnlyZone_StoC_Packet.REM_FLAG)) {
            ZPZoneManager.INSTANCE.REMOVE_ZONE_FROM_CLIENT_MAP((ClientLevel) player.level(), this.zone.uniqueId().substring(ZPSendTheOnlyZone_StoC_Packet.REM_FLAG.length()));
        } else {
            ZPZoneManager.INSTANCE.ADD_ZONE_IN_CLIENT_MAP((ClientLevel) player.level(), this.zone);
        }
    }
}