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
import ru.gltexture.zpm3.engine.zones.ZPZonesRegistry;
import ru.gltexture.zpm3.engine.zones.vars.ZPZoneIntVar;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.*;
import java.util.stream.Collectors;

public class ZPSendTheOnlyZone_StoC_Packet implements ZPNetwork.ZPPacket {
    public static final String SEPARATOR = ";";
    public static final String REM_FLAG = "$NULL$";
    private final ZPZoneManager.Zone zone;

    public ZPSendTheOnlyZone_StoC_Packet(ZPZoneManager.Zone zone) {
        this.zone = zone;
    }

    public ZPSendTheOnlyZone_StoC_Packet(FriendlyByteBuf buf) {
        String uniqueId = buf.readUtf();
        final Vector3i start = new Vector3i(buf.readInt(), buf.readInt(), buf.readInt());
        final Vector3i end = new Vector3i(buf.readInt(), buf.readInt(), buf.readInt());
        Set<ZPZoneFlag> flags = new HashSet<>();
        int flagCount = buf.readVarInt();
        for (int i = 0; i < flagCount; i++) {
            ZPZoneFlag flag = ZPZonesRegistry.flagValueOf(buf.readUtf());
            if (flag != null) {
                flags.add(flag);
            }
        }
        Map<String, ZPZoneIntVar> vars = new HashMap<>();
        int varCount = buf.readVarInt();
        for (int i = 0; i < varCount; i++) {
            String id = buf.readUtf();
            int value = buf.readInt();
            final ZPZoneIntVar registered = ZPZonesRegistry.int_variableValueOf(id);
            if (registered != null) {
                vars.put(id, new ZPZoneIntVar(id, value, registered.getMin(), registered.getMax()));
            }
        }
        this.zone = new ZPZoneManager.Zone(uniqueId, start, end, flags, vars);
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

            buf.writeVarInt(z.flags().size());
            for (ZPZoneFlag flag : z.flags()) {
                buf.writeUtf(flag.id());
            }

            buf.writeVarInt(z.int_vars().size());
            for (ZPZoneIntVar var : z.int_vars().values()) {
                buf.writeUtf(var.getVariableId());
                buf.writeInt(var.getValue());
            }
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