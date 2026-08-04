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

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.exceptions.ZPRuntimeException;
import ru.gltexture.zpm3.engine.network.ZPNetwork;
import ru.gltexture.zpm3.modules.net_pack.data.accessors.ZPNetDataAccessor;
import ru.gltexture.zpm3.modules.net_pack.data.data_ent.ZPNetDataVar;

public class ZPSyncStaticDataPacket_S2C implements ZPNetwork.ZPPacket {
    private final int accessorId;
    private final ZPNetDataVar<?> value;

    public <E> ZPSyncStaticDataPacket_S2C(@NotNull ZPNetDataAccessor<E> accessor, @NotNull ZPNetDataVar<E> value) {
        this.accessorId = accessor.getGlobalId();
        this.value = value.copy();
    }

    public ZPSyncStaticDataPacket_S2C(@NotNull FriendlyByteBuf buf) {
        this.accessorId = buf.readVarInt();
        this.value = ZombiePlague3.netClient().getNetStaticDataSyncer().DECODE(this.accessorId, buf);
    }


    public static Encoder<ZPSyncStaticDataPacket_S2C> encoder() {
        return (packet, buf) -> {
            buf.writeVarInt(packet.accessorId);
            ZombiePlague3.netServer().getNetStaticDataSyncer().ENCODE(packet.value, packet.accessorId, buf);
        };
    }

    public static Decoder<ZPSyncStaticDataPacket_S2C> decoder() {
        return ZPSyncStaticDataPacket_S2C::new;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void onClient(@NotNull Player localPlayer) {
        final ZPNetDataAccessor<?> accessor = ZombiePlague3.netClient().getNetStaticDataSyncer().REG_FROM_SERVER().getAccessor(this.accessorId).orElseThrow(() -> new ZPRuntimeException("Unknown accessor id " + this.accessorId));
        this.applyClient(accessor);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void applyClient(@NotNull ZPNetDataAccessor accessor) {
        ZombiePlague3.netClient().getNetStaticDataSyncer().setValue(accessor, this.value);
    }

    @Override
    public void onServer(@NotNull Player sender, @NotNull ServerLevel level) {
        throw new ZPRuntimeException("ZPSyncStaticDataPacket_S2C cannot be received on server");
    }
}