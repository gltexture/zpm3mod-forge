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

package ru.gltexture.zpm3.modules.net_pack.data;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZPLogger;
import ru.gltexture.zpm3.engine.exceptions.ZPRuntimeException;
import ru.gltexture.zpm3.modules.net_pack.data.data_ent.IZPNetEntDataSyncer;
import ru.gltexture.zpm3.modules.net_pack.data.accessors.ZPNetDataAccessor;
import ru.gltexture.zpm3.modules.net_pack.data.data_ent.ZPNetDataVar;

import java.util.function.Function;

public abstract class ZPAccessorsNetEntSyncUtil {
    @SuppressWarnings("all")
    public static <R> void ENCODE(@NotNull ZPNetDataVar<R> var, int accessorId, @NotNull FriendlyByteBuf buffer, @NotNull Function<Integer, ZPNetDataAccessor<?>> accessorGetterFunc) {
        final ZPNetDataAccessor<R> accessor = (ZPNetDataAccessor<R>) accessorGetterFunc.apply(accessorId);
        if (accessor == null) {
            ZPLogger.error("Tried to encode unknown accessor id: " + accessorId);
            throw new ZPRuntimeException("Unknown accessor id: " + accessorId);
        }
        accessor.getDataCodec().encode(buffer, var);
    }

    @SuppressWarnings("all")
    public static <R> ZPNetDataVar<R> DECODE(int accessorId, @NotNull FriendlyByteBuf buffer, @NotNull Function<Integer, ZPNetDataAccessor<?>> accessorGetterFunc) {
        final ZPNetDataAccessor<R> accessor = (ZPNetDataAccessor<R>) accessorGetterFunc.apply(accessorId);
        if (accessor == null) {
            throw new ZPRuntimeException("Unknown accessor id: " + accessorId);
        }
        return accessor.getDataCodec().decode(buffer);
    }

    @SuppressWarnings("all")
    public static void ENCODE_ALL(@NotNull IZPNetEntDataSyncer.ZPNetEntityData entityData, @NotNull FriendlyByteBuf buffer, @NotNull Function<Integer, ZPNetDataAccessor<?>> accessorGetterFunc) {
        buffer.writeVarInt(entityData.vars().size());
        for (Int2ObjectMap.Entry<ZPNetDataVar<?>> entry : entityData.vars().int2ObjectEntrySet()) {
            final int accessorId = entry.getIntKey();
            buffer.writeVarInt(accessorId);
            ZPAccessorsNetEntSyncUtil.ENCODE(entry.getValue(), accessorId, buffer, accessorGetterFunc);
        }
    }

    public static IZPNetEntDataSyncer.@NotNull ZPNetEntityData DECODE_ALL(@NotNull FriendlyByteBuf buffer, @NotNull Function<Integer, ZPNetDataAccessor<?>> accessorGetterFunc) {
        final int size = buffer.readVarInt();
        final Int2ObjectMap<ZPNetDataVar<?>> vars = new Int2ObjectOpenHashMap<>(size);
        for (int i = 0; i < size; i++) {
            final int accessorId = buffer.readVarInt();
            vars.put(accessorId, ZPAccessorsNetEntSyncUtil.DECODE(accessorId, buffer, accessorGetterFunc));
        }
        return new IZPNetEntDataSyncer.ZPNetEntityData(vars);
    }

    public static void ENCODE_ALL_ENTITIES(@NotNull Int2ObjectMap<IZPNetEntDataSyncer.ZPNetEntityData> entities, @NotNull FriendlyByteBuf buffer, @NotNull Function<Integer, ZPNetDataAccessor<?>> accessorGetterFunc) {
        buffer.writeVarInt(entities.size());
        for (Int2ObjectMap.Entry<IZPNetEntDataSyncer.ZPNetEntityData> entry : entities.int2ObjectEntrySet()) {
            buffer.writeVarInt(entry.getIntKey());
            ZPAccessorsNetEntSyncUtil.ENCODE_ALL(entry.getValue(), buffer, accessorGetterFunc);
        }
    }

    public static Int2ObjectMap<IZPNetEntDataSyncer.ZPNetEntityData> DECODE_ALL_ENTITIES(@NotNull FriendlyByteBuf buffer, @NotNull Function<Integer, ZPNetDataAccessor<?>> accessorGetterFunc) {
        int size = buffer.readVarInt();
        Int2ObjectMap<IZPNetEntDataSyncer.ZPNetEntityData> entities = new Int2ObjectOpenHashMap<>(size);
        for (int i = 0; i < size; i++) {
            int entityId = buffer.readVarInt();
            IZPNetEntDataSyncer.ZPNetEntityData data = ZPAccessorsNetEntSyncUtil.DECODE_ALL(buffer, accessorGetterFunc);
            entities.put(entityId, data);
        }
        return entities;
    }
}
