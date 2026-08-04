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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.network.ZPNetwork;
import ru.gltexture.zpm3.modules.net_pack.data.data_ent.ZPNetDataVar;

public class ZPSyncEntityDataVarPacket implements ZPNetwork.ZPPacket {
    private final int entityId;
    private final int dataAccessorId;
    private final ZPNetDataVar<?> dataVar;

    public ZPSyncEntityDataVarPacket(int entityId, int dataAccessorId, ZPNetDataVar<?> dataVar) {
        this.entityId = entityId;
        this.dataAccessorId = dataAccessorId;
        this.dataVar = dataVar;
    }

    public ZPSyncEntityDataVarPacket(FriendlyByteBuf buf) {
        this.entityId = buf.readInt();
        this.dataAccessorId = buf.readInt();
        this.dataVar = ZombiePlague3.netClient().getNetEntDataSyncer().DECODE(this.dataAccessorId, buf);
    }

    public static Encoder<ZPSyncEntityDataVarPacket> encoder() {
        return (packet, buf) -> {
            buf.writeInt(packet.entityId);
            buf.writeInt(packet.dataAccessorId);
            ZombiePlague3.netServer().getNetEntDataSyncer().ENCODE(packet.dataVar, packet.dataAccessorId, buf);
        };
    }

    public static Decoder<ZPSyncEntityDataVarPacket> decoder() {
        return ZPSyncEntityDataVarPacket::new;
    }

    @Override
    public void onServer(@NotNull Player sender, @NotNull ServerLevel level) {
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void onClient(@NotNull Player localPlayer) {
        final Entity entity = localPlayer.level().getEntity(entityId);
        if (entity != null) {
            ZombiePlague3.netClient().getNetEntDataSyncer().setVar(entity, ZombiePlague3.netServer().getNetEntDataSyncer().getAccessorUnsafe(this.dataAccessorId), this.dataVar);
        }
    }
}