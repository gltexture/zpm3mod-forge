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

package ru.gltexture.zpm3.modules.net_pack.data.data_static;

import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.modules.net_pack.data.ZPAccessorsNetStaticSyncUtil;
import ru.gltexture.zpm3.modules.net_pack.data.accessors.ZPNetDataAccessor;
import ru.gltexture.zpm3.modules.net_pack.data.data_ent.ZPNetDataVar;
import ru.gltexture.zpm3.modules.net_pack.packets.C2S.ZPSyncStaticAllDataPacket_C2S;
import ru.gltexture.zpm3.modules.net_pack.packets.C2S.ZPSyncStaticDataPacket_C2S;

import java.util.*;

public final class ZPNetStaticDataSyncerClient implements IZPNetStaticDataSyncerClient {
    private final ZPNetStaticDataRegistry FROM_SERVER;
    private final ZPNetStaticDataRegistry LOCAL;

    private final ZPNetStaticDataPack packClientLocalData;
    private final ZPNetStaticDataPack packServerData;

    public ZPNetStaticDataSyncerClient() {
        this.FROM_SERVER = new ZPNetStaticDataRegistry();
        this.LOCAL = new ZPNetStaticDataRegistry();
        this.packClientLocalData = new ZPNetStaticDataPack(this::broadcastValue);
        this.packServerData = new ZPNetStaticDataPack(null);
    }

    public void broadcastAll() {
        ZombiePlague3.netClient().sendToServer(new ZPSyncStaticAllDataPacket_C2S());
    }

    public <E> void broadcastValue(@NotNull ZPNetDataAccessor<E> accessor, @NotNull ZPNetDataVar<E> value) {
        ZombiePlague3.netClient().sendToServer(new ZPSyncStaticDataPacket_C2S(accessor, value));
    }

    public void applyDecodedData(@NotNull Map<ZPNetDataAccessor<?>, ZPNetDataVar<?>> vars) {
        vars.forEach(this.packServerData::setValueUnsafe);
    }

    public <R> void ENCODE(@NotNull ZPNetDataVar<R> var, int accessorId, @NotNull FriendlyByteBuf buffer) {
        ZPAccessorsNetStaticSyncUtil.ENCODE(var, accessorId, buffer, this.LOCAL::getAccessorUnsafe);
    }

    public <R> ZPNetDataVar<R> DECODE(int accessorId, @NotNull FriendlyByteBuf buffer) {
        return ZPAccessorsNetStaticSyncUtil.DECODE(accessorId, buffer, this.FROM_SERVER::getAccessorUnsafe);
    }

    public void ENCODE_ALL(@NotNull Map<ZPNetDataAccessor<?>, ZPNetDataVar<?>> varMap, @NotNull FriendlyByteBuf buffer) {
        ZPAccessorsNetStaticSyncUtil.ENCODE_ALL(varMap, buffer, this.LOCAL::getAccessorUnsafe);
    }

    public @NotNull Map<ZPNetDataAccessor<?>, ZPNetDataVar<?>> DECODE_ALL(@NotNull FriendlyByteBuf buffer) {
        return ZPAccessorsNetStaticSyncUtil.DECODE_ALL(buffer, this.FROM_SERVER::getAccessorUnsafe);
    }

    public <E> void defineFromServerAccessor(@NotNull ZPNetDataAccessor<E> accessor, @NotNull ZPNetDataVar<E> defaultValue) {
        this.FROM_SERVER.defineStaticAccessor(accessor, defaultValue);
        this.packServerData.init(accessor, defaultValue);
    }

    public <E> void defineClientAccessor(@NotNull ZPNetDataAccessor<E> accessor, @NotNull ZPNetDataVar<E> defaultValue) {
        this.LOCAL.defineStaticAccessor(accessor, defaultValue);
        this.packClientLocalData.init(accessor, defaultValue);
    }

    public ZPNetStaticDataRegistry REG_FROM_SERVER() {
        return this.FROM_SERVER;
    }

    public ZPNetStaticDataRegistry REG_LOCAL() {
        return this.LOCAL;
    }

    public ZPNetStaticDataPack getPackClientLocalData() {
        return this.packClientLocalData;
    }

    public ZPNetStaticDataPack getPackServerData() {
        return this.packServerData;
    }

    @Override
    public <E> Optional<ZPNetDataVar<E>> getVar(@NotNull ZPNetDataAccessor<E> accessor) {
        return this.packClientLocalData.getVar(accessor);
    }

    @Override
    public <E> void setValue(@NotNull ZPNetDataAccessor<E> accessor, @NotNull ZPNetDataVar<E> value) {
        this.packClientLocalData.setValue(accessor, value, true);
    }
}