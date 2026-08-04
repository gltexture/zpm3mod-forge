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

package ru.gltexture.zpm3.modules.net_pack.packets.C2S;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.exceptions.ZPRuntimeException;
import ru.gltexture.zpm3.engine.network.ZPNetwork;
import ru.gltexture.zpm3.modules.net_pack.data.accessors.ZPNetDataAccessor;
import ru.gltexture.zpm3.modules.net_pack.data.data_ent.ZPNetDataVar;

public class ZPSyncStaticDataPacket_C2S implements ZPNetwork.ZPPacket {
    private final int accessorId;
    private final ZPNetDataVar<?> value;

    public <E> ZPSyncStaticDataPacket_C2S(@NotNull ZPNetDataAccessor<E> accessor, @NotNull ZPNetDataVar<E> value) {
        this.accessorId = accessor.getGlobalId();
        this.value = value.copy();
    }

    public ZPSyncStaticDataPacket_C2S(@NotNull FriendlyByteBuf buf) {
        this.accessorId = buf.readVarInt();
        this.value = ZombiePlague3.netServer().getNetStaticDataSyncer().DECODE(this.accessorId, buf);
    }

    public static Encoder<ZPSyncStaticDataPacket_C2S> encoder() {
        return (packet, buf) -> {
            buf.writeVarInt(packet.accessorId);
            ZombiePlague3.netClient().getNetStaticDataSyncer().ENCODE(packet.value, packet.accessorId, buf);
        };
    }

    public static Decoder<ZPSyncStaticDataPacket_C2S> decoder() {
        return ZPSyncStaticDataPacket_C2S::new;
    }

    @Override
    public void onServer(@NotNull Player sender, @NotNull ServerLevel level) {
        final @Nullable ZPNetDataAccessor<?> accessor = ZombiePlague3.netServer().getNetStaticDataSyncer().REG_FROM_CLIENTS().getAccessor(this.accessorId).orElse(null);
        if (accessor != null) {
            this.applyServer((ServerPlayer) sender, accessor);
        } else {
            if (sender instanceof ServerPlayer serverPlayer) {
                serverPlayer.connection.disconnect(Component.literal("Client sent wrong accessor(static): " + this.accessorId));
            }
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void applyServer(@NotNull ServerPlayer player, @NotNull ZPNetDataAccessor accessor) {
        ZombiePlague3.netServer().getNetStaticDataSyncer().setValueOnPlayer(player, accessor, this.value);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void onClient(@NotNull Player localPlayer) {
        throw new ZPRuntimeException("ZPSyncStaticDataPacket_C2S cannot be received on client");
    }
}