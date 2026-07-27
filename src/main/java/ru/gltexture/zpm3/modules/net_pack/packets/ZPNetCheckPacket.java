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

package ru.gltexture.zpm3.modules.net_pack.packets;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.modules.player.mixins.ext.IZPPlayerMixinExt;
import ru.gltexture.zpm3.engine.network.ZPNetwork;

import java.util.Objects;

public class ZPNetCheckPacket implements ZPNetwork.ZPPacket {
    private final int entityId;

    public ZPNetCheckPacket(int entityId) {
        this.entityId = entityId;
    }

    public ZPNetCheckPacket(FriendlyByteBuf buf) {
        this.entityId = buf.readVarInt();
    }

    public static Encoder<ZPNetCheckPacket> encoder() {
        return (packet, buf) -> {
            buf.writeVarInt(packet.entityId);
        };
    }

    public static Decoder<ZPNetCheckPacket> decoder() {
        return ZPNetCheckPacket::new;
    }

    @Override
    public void onServer(@NotNull Player sender, @NotNull ServerLevel serverLevel) {
        try {
            Entity entity = Objects.requireNonNull(serverLevel).getEntity(this.entityId);
            if (entity instanceof IZPPlayerMixinExt ext) {
                ext.zpm3forge$getResponseNetCheckFromClient();
            }
        } catch (Exception ignored) {
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void onClient(@NotNull Player localPlayer) {
        ClientLevel clientLevel = Objects.requireNonNull(Minecraft.getInstance().level);
        ((IZPPlayerMixinExt) localPlayer).zpm3forge$getResponseNetCheckFromServer();
    }
}