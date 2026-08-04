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
import ru.gltexture.zpm3.modules.net_pack.data.data_ent.ZPNetEntDataSyncer;

public class ZPSyncEntityDataAllVarsPacket implements ZPNetwork.ZPPacket {
    private final int entityId;
    private final ZPNetEntDataSyncer.ZPNetEntityData entityData;

    public ZPSyncEntityDataAllVarsPacket(int entityId, ZPNetEntDataSyncer.ZPNetEntityData entityData) {
        this.entityId = entityId;
        this.entityData = entityData;
    }

    public ZPSyncEntityDataAllVarsPacket(FriendlyByteBuf buf) {
        this.entityId = buf.readInt();
        this.entityData = ZombiePlague3.netClient().getNetEntDataSyncer().DECODE_ALL(buf);
    }

    public static Encoder<ZPSyncEntityDataAllVarsPacket> encoder() {
        return (packet, buf) -> {
            buf.writeInt(packet.entityId);
            ZombiePlague3.netServer().getNetEntDataSyncer().ENCODE_ALL(packet.entityData, buf);
        };
    }

    public static Decoder<ZPSyncEntityDataAllVarsPacket> decoder() {
        return ZPSyncEntityDataAllVarsPacket::new;
    }

    @Override
    public void onServer(@NotNull Player sender, @NotNull ServerLevel level) {
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void onClient(@NotNull Player localPlayer) {
        Entity entity = localPlayer.level().getEntity(this.entityId);
        if (entity == null) {
            return;
        }
        ZombiePlague3.netClient().getNetEntDataSyncer().setDataOnEntity(entity, this.entityData);
    }
}