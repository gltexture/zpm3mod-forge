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

import java.util.*;

public class ZPSyncStaticAllDataPacket_S2C implements ZPNetwork.ZPPacket {
    private final Map<ZPNetDataAccessor<?>, ZPNetDataVar<?>> values;

    public ZPSyncStaticAllDataPacket_S2C() {
        this.values = ZombiePlague3.netServer().getNetStaticDataSyncer().getPackServerLocalData().getVars();
    }

    public ZPSyncStaticAllDataPacket_S2C(@NotNull FriendlyByteBuf buf) {
        this.values = ZombiePlague3.netClient().getNetStaticDataSyncer().DECODE_ALL(buf);
    }

    public static Encoder<ZPSyncStaticAllDataPacket_S2C> encoder() {
        return (packet, buf) -> {
            ZombiePlague3.netServer().getNetStaticDataSyncer().ENCODE_ALL(packet.values, buf);
        };
    }

    public static Decoder<ZPSyncStaticAllDataPacket_S2C> decoder() {
        return ZPSyncStaticAllDataPacket_S2C::new;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void onClient(@NotNull Player localPlayer) {
        ZombiePlague3.netClient().getNetStaticDataSyncer().applyDecodedData(this.values);
    }

    @Override
    public void onServer(@NotNull Player sender, @NotNull ServerLevel level) {
        throw new ZPRuntimeException("ZPSyncStaticAllDataPacket_S2C cannot be received on server");
    }
}