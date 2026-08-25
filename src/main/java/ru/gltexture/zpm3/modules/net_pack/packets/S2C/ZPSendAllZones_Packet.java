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

package ru.gltexture.zpm3.modules.net_pack.packets.S2C;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3i;
import ru.gltexture.zpm3.engine.network.ZPNetwork;
import ru.gltexture.zpm3.modules.commands.zones.ZPZoneFlag;
import ru.gltexture.zpm3.modules.commands.zones.ZPZoneManager;
import ru.gltexture.zpm3.modules.commands.zones.ZPZonesRegistry;
import ru.gltexture.zpm3.modules.commands.zones.vars.ZPZoneIntVar;

import java.util.*;

public class ZPSendAllZones_Packet implements ZPNetwork.ZPPacket {
    private final Collection<ZPZoneManager.Zone> zones;

    public ZPSendAllZones_Packet(Collection<ZPZoneManager.Zone> zones) {
        this.zones = zones;
    }

    public ZPSendAllZones_Packet(FriendlyByteBuf buf) {
        final int size = buf.readVarInt();
        this.zones = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            final String uniqueId = buf.readUtf();
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
            final int varCount = buf.readVarInt();
            for (int j = 0; j < varCount; j++) {
                final String id = buf.readUtf();
                final int value = buf.readInt();
                final ZPZoneIntVar registered = ZPZonesRegistry.int_variableValueOf(id);
                if (registered != null) {
                    vars.put(id, new ZPZoneIntVar(id, value, registered.getMin(), registered.getMax()));
                }
            }
            zones.add(new ZPZoneManager.Zone(uniqueId, start, end, flags, vars));
        }
    }

    public static Encoder<ZPSendAllZones_Packet> encoder() {
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

                buf.writeVarInt(z.int_vars() == null ? 0 : z.int_vars().size());
                if (z.int_vars() != null) {
                    for (ZPZoneIntVar var : z.int_vars().values()) {
                        buf.writeUtf(var.getVariableId());
                        buf.writeInt(var.getValue());
                    }
                }
            }
        };
    }

    public static Decoder<ZPSendAllZones_Packet> decoder() {
        return ZPSendAllZones_Packet::new;
    }

    @Override
    public void onServer(@NotNull Player sender, @NotNull ServerLevel level) {
    }

    @Override
    public void onClient(@NotNull Player player) {
        ZPZoneManager.INSTANCE.REPLACE_CLIENT_MAP((ClientLevel) player.level(), zones);
    }
}