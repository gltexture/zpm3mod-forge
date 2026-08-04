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

package ru.gltexture.zpm3.modules.net_pack.packets.MIXED;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.network.ZPNetwork;

public class ZPValidateAccessorsPacket implements ZPNetwork.ZPPacket {
    private final String entityData_accessorsHash;
    private final String staticData_accessorsHash;

    public ZPValidateAccessorsPacket(@NotNull String staticData_accessorsHash, @NotNull String entityData_accessorsHash) {
        this.staticData_accessorsHash = staticData_accessorsHash;
        this.entityData_accessorsHash = entityData_accessorsHash;
    }

    public ZPValidateAccessorsPacket(@NotNull FriendlyByteBuf buf) {
        this.staticData_accessorsHash = buf.readUtf();
        this.entityData_accessorsHash = buf.readUtf();
    }

    public static Encoder<ZPValidateAccessorsPacket> encoder() {
        return (packet, buf) -> {
            buf.writeUtf(packet.staticData_accessorsHash);
            buf.writeUtf(packet.entityData_accessorsHash);
        };
    }

    public static Decoder<ZPValidateAccessorsPacket> decoder() {
        return ZPValidateAccessorsPacket::new;
    }

    @Override
    public void onServer(@NotNull Player sender, @NotNull ServerLevel serverLevel) {
        final String serverHashEnt = ZombiePlague3.netServer().getNetEntDataSyncer().buildAccessorsHash();
        final String serverHashSt = ZombiePlague3.netServer().getNetStaticDataSyncer().REG_FROM_CLIENTS().buildAccessorsHash();

        if (!serverHashEnt.equals(this.entityData_accessorsHash)) {
            if (sender instanceof ServerPlayer serverPlayer) {
                serverPlayer.connection.disconnect(Component.literal(
                        "(ENT) Client/Server ZPNetDataAccessor registry mismatch.\n" +
                        "Server: " + serverHashEnt + "\n" +
                        "Client: " + this.entityData_accessorsHash
                ));
            }
        }

        if (!serverHashSt.equals(this.staticData_accessorsHash)) {
            if (sender instanceof ServerPlayer serverPlayer) {
                serverPlayer.connection.disconnect(Component.literal(
                        "(STAT) Client/Server ZPNetDataAccessor registry mismatch.\n" +
                                "Server: " + serverHashSt + "\n" +
                                "Client: " + this.staticData_accessorsHash
                ));
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void onClient(@NotNull Player localPlayer) {
        ZombiePlague3.netClient().sendToServer(new ZPValidateAccessorsPacket(ZombiePlague3.netClient().getNetStaticDataSyncer().REG_LOCAL().buildAccessorsHash(), ZombiePlague3.netClient().getNetEntDataSyncer().buildAccessorsHash()));
    }
}