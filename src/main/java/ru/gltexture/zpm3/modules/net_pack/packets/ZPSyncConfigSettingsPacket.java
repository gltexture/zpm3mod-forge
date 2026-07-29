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
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZPLogger;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.exceptions.ZPRuntimeException;
import ru.gltexture.zpm3.modules.player.mixins.ext.IZPPlayerMixinExt;
import ru.gltexture.zpm3.engine.network.ZPNetwork;
import ru.gltexture.zpm3.modules.net_pack.data.ZPNetSyncDataPack;

public class ZPSyncConfigSettingsPacket implements ZPNetwork.ZPPacket {
    private final ZPNetSyncDataPack zpNetSyncDataPack;

    public ZPSyncConfigSettingsPacket(ZPNetSyncDataPack zpNetSyncDataPack) {
        this.zpNetSyncDataPack = zpNetSyncDataPack;
    }

    public ZPSyncConfigSettingsPacket(FriendlyByteBuf buf) {
        this.zpNetSyncDataPack = new ZPNetSyncDataPack(buf.readMap(
                FriendlyByteBuf::readUtf,
                b -> {
                    byte type = b.readByte();
                    return switch (type) {
                        case 0 -> b.readInt();
                        case 1 -> b.readDouble();
                        case 2 -> b.readBoolean();
                        case 3 -> b.readFloat();
                        default -> throw new ZPRuntimeException("Unknown type: " + type);
                    };
                }
        ));
    }

    public static Encoder<ZPSyncConfigSettingsPacket> encoder() {
        return (packet, buf) -> {
            buf.writeMap(
                    packet.zpNetSyncDataPack.dataPack(),
                    FriendlyByteBuf::writeUtf,
                    (b, value) -> {
                        if (value instanceof Integer i) {
                            b.writeByte(0);
                            b.writeInt(i);
                        } else if (value instanceof Double d) {
                            b.writeByte(1);
                            b.writeDouble(d);
                        } else if (value instanceof Boolean bool) {
                            b.writeByte(2);
                            b.writeBoolean(bool);
                        } else if (value instanceof Float f) {
                            b.writeByte(3);
                            b.writeFloat(f);
                        }
                    }
            );
        };
    }

    public static Decoder<ZPSyncConfigSettingsPacket> decoder() {
        return ZPSyncConfigSettingsPacket::new;
    }

    @Override
    public void onServer(@NotNull Player sender, @NotNull ServerLevel level) {
        if (sender instanceof IZPPlayerMixinExt ext) {
            ext.zpm3forge$zpNetDataPack_fromClient().replace(this.zpNetSyncDataPack);
            ZPLogger.info(sender.getDisplayName() + " : ZPSyncConfigSettingsPacket onServer : " + this.zpNetSyncDataPack);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void onClient(@NotNull Player localPlayer) {
        ZPNetworkHandler.getNetDataPack_FromServer().replace(this.zpNetSyncDataPack);
        ZPLogger.info("ZPSyncConfigSettingsPacket onClient : " + this.zpNetSyncDataPack);
    }
}