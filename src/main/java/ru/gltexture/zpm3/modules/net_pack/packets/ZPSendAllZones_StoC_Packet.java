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

import java.util.*;
import java.util.stream.Collectors;

public class ZPSendAllZones_StoC_Packet implements ZPNetwork.ZPPacket {
    private final Collection<ZPZoneManager.Zone> zones;

    public ZPSendAllZones_StoC_Packet(Collection<ZPZoneManager.Zone> zones) {
        this.zones = zones;
    }

    public ZPSendAllZones_StoC_Packet(FriendlyByteBuf buf) {
        int size = buf.readVarInt();
        this.zones = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            String uniqueId = buf.readUtf();
            final Vector3i start = new Vector3i(buf.readInt(), buf.readInt(), buf.readInt());
            final Vector3i end = new Vector3i(buf.readInt(), buf.readInt(), buf.readInt());
            Set<ZPZoneFlag> flags = new HashSet<>();
            int flagCount = buf.readVarInt();
            for (int j = 0; j < flagCount; j++) {
                ZPZoneFlag flag = ZPZonesRegistry.flagValueOf(buf.readUtf());
                if (flag != null) {
                    flags.add(flag);
                }
            }
            Map<String, ZPZoneIntVar> vars = new HashMap<>();
            int varCount = buf.readVarInt();
            for (int j = 0; j < varCount; j++) {
                String id = buf.readUtf();
                int value = buf.readInt();
                final ZPZoneIntVar registered = ZPZonesRegistry.int_variableValueOf(id);
                if (registered != null) {
                    vars.put(id, new ZPZoneIntVar(id, value, registered.getMin(), registered.getMax()));
                }
            }
            zones.add(new ZPZoneManager.Zone(uniqueId, start, end, flags, vars));
        }
    }

    public static Encoder<ZPSendAllZones_StoC_Packet> encoder() {
        return (packet, buf) -> {
            buf.writeVarInt(packet.zones.size());
            for (ZPZoneManager.Zone z : packet.zones) {
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