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

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.network.ZPNetwork;
import ru.gltexture.zpm3.modules.player.mixins.ext.IZPPlayerMixinExt;

public class ZPLyingStatePacket implements ZPNetwork.ZPPacket {
    private final boolean forceLying;

    public ZPLyingStatePacket(boolean forceLying) {
        this.forceLying = forceLying;
    }

    public ZPLyingStatePacket(FriendlyByteBuf buf) {
        this.forceLying = buf.readBoolean();
    }

    public static Encoder<ZPLyingStatePacket> encoder() {
        return (packet, buf) -> {
            buf.writeBoolean(packet.forceLying);
        };
    }

    public static Decoder<ZPLyingStatePacket> decoder() {
        return ZPLyingStatePacket::new;
    }

    @Override
    public void onServer(@NotNull Player sender, @NotNull ServerLevel serverLevel) {
        if (sender instanceof ServerPlayer serverPlayer) {
            if (sender instanceof IZPPlayerMixinExt izpPlayerMixinExt) {
                izpPlayerMixinExt.zpm3forge$setLying(this.forceLying);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void onClient(@NotNull Player localPlayer) {
    }
}