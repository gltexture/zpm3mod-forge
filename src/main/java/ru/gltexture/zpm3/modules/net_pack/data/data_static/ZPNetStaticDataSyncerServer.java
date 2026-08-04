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
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.exceptions.ZPRuntimeException;
import ru.gltexture.zpm3.modules.net_pack.data.ZPAccessorsNetStaticSyncUtil;
import ru.gltexture.zpm3.modules.net_pack.data.accessors.ZPNetDataAccessor;
import ru.gltexture.zpm3.modules.net_pack.data.data_ent.ZPNetDataVar;
import ru.gltexture.zpm3.modules.net_pack.packets.S2C.ZPSyncStaticAllDataPacket_S2C;
import ru.gltexture.zpm3.modules.net_pack.packets.S2C.ZPSyncStaticDataPacket_S2C;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class ZPNetStaticDataSyncerServer implements IZPNetStaticDataSyncerServer {
    private final ZPNetStaticDataRegistry FROM_CLIENTS;
    private final ZPNetStaticDataRegistry LOCAL;

    private final ZPNetStaticDataPack packServerLocalData;
    private final Map<ServerPlayer, ZPNetStaticDataPack> clientPacks;

    public ZPNetStaticDataSyncerServer() {
        this.FROM_CLIENTS = new ZPNetStaticDataRegistry();
        this.LOCAL = new ZPNetStaticDataRegistry();
        this.clientPacks = new HashMap<>();
        this.packServerLocalData = new ZPNetStaticDataPack(this::broadcastValue);
    }

    public void applyDecodedData(@NotNull ServerPlayer player, @NotNull Map<ZPNetDataAccessor<?>, ZPNetDataVar<?>> vars) {
        final ZPNetStaticDataPack pack = this.clientPacks.get(player);
        if (pack == null) {
            throw new ZPRuntimeException("No static data pack for player: " + player.getGameProfile().getName());
        }
        vars.forEach(pack::setValueUnsafe);
    }

    public void broadcastAll(@NotNull ServerPlayer player) {
        ZombiePlague3.netServer().sendToPlayer(new ZPSyncStaticAllDataPacket_S2C(), player);
    }

    public void broadcastAll() {
        ZombiePlague3.netServer().sendToAll(new ZPSyncStaticAllDataPacket_S2C());
    }

    public <E> void broadcastValue(@NotNull ZPNetDataAccessor<E> accessor, @NotNull ZPNetDataVar<E> value) {
        ZombiePlague3.netServer().sendToAll(new ZPSyncStaticDataPacket_S2C(accessor, value));
    }

    @Override
    public <E> @NotNull Optional<ZPNetDataVar<E>> getValueOnPlayer(@NotNull ServerPlayer player, @NotNull ZPNetDataAccessor<E> accessor) {
        final ZPNetStaticDataPack pack = this.clientPacks.get(player);
        if (pack == null) {
            return Optional.empty();
        }
        return pack.getVar(accessor);
    }

    public <E> void setValueOnPlayer(@NotNull ServerPlayer player, @NotNull ZPNetDataAccessor<E> accessor, @NotNull ZPNetDataVar<E> value) {
        final ZPNetStaticDataPack pack = this.clientPacks.get(player);
        if (pack == null) {
            throw new ZPRuntimeException("No static data pack for player: " + player.getGameProfile().getName());
        }
        pack.setValue(accessor, value, true);
    }

    @Override
    public <E> @NotNull Optional<ZPNetDataVar<E>> getValue(@NotNull ZPNetDataAccessor<E> accessor) {
        return this.packServerLocalData.getVar(accessor);
    }

    public <E> void setValue(@NotNull ZPNetDataAccessor<E> accessor, @NotNull ZPNetDataVar<E> value) {
        this.packServerLocalData.setValue(accessor, value, true);
    }

    public <E> void setValues(@NotNull Map<ZPNetDataAccessor<E>, ZPNetDataVar<E>> values) {
        values.forEach((k, v) -> this.packServerLocalData.setValue(k, v, false));
    }

    public <R> void ENCODE(@NotNull ZPNetDataVar<R> var, int accessorId, @NotNull FriendlyByteBuf buffer) {
        ZPAccessorsNetStaticSyncUtil.ENCODE(var, accessorId, buffer, this.LOCAL::getAccessorUnsafe);
    }

    public <R> ZPNetDataVar<R> DECODE(int accessorId, @NotNull FriendlyByteBuf buffer) {
        return ZPAccessorsNetStaticSyncUtil.DECODE(accessorId, buffer, this.FROM_CLIENTS::getAccessorUnsafe);
    }

    public void ENCODE_ALL(@NotNull Map<ZPNetDataAccessor<?>, ZPNetDataVar<?>> varMap, @NotNull FriendlyByteBuf buffer) {
        ZPAccessorsNetStaticSyncUtil.ENCODE_ALL(varMap, buffer, this.LOCAL::getAccessorUnsafe);
    }

    public @NotNull Map<ZPNetDataAccessor<?>, ZPNetDataVar<?>> DECODE_ALL(@NotNull FriendlyByteBuf buffer) {
        return ZPAccessorsNetStaticSyncUtil.DECODE_ALL(buffer, this.FROM_CLIENTS::getAccessorUnsafe);
    }

    public <E> void defineFromClientAccessor(@NotNull ZPNetDataAccessor<E> accessor, @NotNull ZPNetDataVar<E> defaultValue) {
        this.FROM_CLIENTS.defineStaticAccessor(accessor, defaultValue);
    }

    public <E> void defineServerAccessor(@NotNull ZPNetDataAccessor<E> accessor, @NotNull ZPNetDataVar<E> defaultValue) {
        this.LOCAL.defineStaticAccessor(accessor, defaultValue);
        this.packServerLocalData.init(accessor, defaultValue);
    }

    public ZPNetStaticDataRegistry REG_FROM_CLIENTS() {
        return this.FROM_CLIENTS;
    }

    public ZPNetStaticDataRegistry REG_LOCAL() {
        return this.LOCAL;
    }

    public ZPNetStaticDataPack getPackServerLocalData() {
        return this.packServerLocalData;
    }

    public boolean check(@NotNull ServerPlayer player) {
        return this.clientPacks.containsKey(player);
    }

    public Optional<ZPNetStaticDataPack> getPack(@NotNull ServerPlayer player) {
        return Optional.ofNullable(this.clientPacks.get(player));
    }

    public void add(@NotNull ServerPlayer player) {
        this.clientPacks.put(player, ZPNetStaticDataPack.of(this.FROM_CLIENTS.getDefaultVarsRegistry(), null));
    }

    public void remove(@NotNull ServerPlayer player) {
        this.clientPacks.remove(player);
    }
}