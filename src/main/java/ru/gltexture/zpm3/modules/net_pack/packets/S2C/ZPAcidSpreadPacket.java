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

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZPLogger;
import ru.gltexture.zpm3.engine.network.ZPNetwork;

import java.util.Objects;

public class ZPAcidSpreadPacket implements ZPNetwork.ZPPacket {
    private final int entityId;
    private final int acidLevel;

    public ZPAcidSpreadPacket(int entityId, int acidLevel) {
        this.entityId = entityId;
        this.acidLevel = acidLevel;
    }

    public ZPAcidSpreadPacket(FriendlyByteBuf buf) {
        this.entityId = buf.readVarInt();
        this.acidLevel = buf.readVarInt();
    }

    public static Encoder<ZPAcidSpreadPacket> encoder() {
        return (packet, buf) -> {
            buf.writeVarInt(packet.entityId);
            buf.writeVarInt(packet.acidLevel);
        };
    }

    public static Decoder<ZPAcidSpreadPacket> decoder() {
        return ZPAcidSpreadPacket::new;
    }

    @Override
    public void onServer(@NotNull Player sender, @NotNull ServerLevel serverLevel) {

    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void onClient(@NotNull Player localPlayer) {
        ClientLevel clientLevel = Objects.requireNonNull(Minecraft.getInstance().level);
        Entity entity = Objects.requireNonNull(Minecraft.getInstance().level).getEntity(this.entityId);
        if (entity != null) {
            // new ZPItemStackNBT(entity).incrementInt(ZPEntityTagsList.ACID_AFFECT_COOLDOWN, this.acidLevel);
        } else {
            ZPLogger.warn("Received entity-id: " + this.entityId + ", but entity is NULL");
        }
    }
}
