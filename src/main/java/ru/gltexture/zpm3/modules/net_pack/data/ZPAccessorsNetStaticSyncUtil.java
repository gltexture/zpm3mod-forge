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

import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZPLogger;
import ru.gltexture.zpm3.engine.exceptions.ZPRuntimeException;
import ru.gltexture.zpm3.modules.net_pack.data.accessors.ZPNetDataAccessor;
import ru.gltexture.zpm3.modules.net_pack.data.data_ent.ZPNetDataVar;

import java.util.*;
import java.util.function.Function;

public abstract class ZPAccessorsNetStaticSyncUtil {
    public static final int MAX_STATIC_VALUES = 512;

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
    public static <R> @NotNull ZPNetDataVar<R> DECODE(int accessorId, @NotNull FriendlyByteBuf buffer, @NotNull Function<Integer, ZPNetDataAccessor<?>> accessorGetterFunc) {
        final ZPNetDataAccessor<R> accessor = (ZPNetDataAccessor<R>) accessorGetterFunc.apply(accessorId);
        if (accessor == null) {
            throw new ZPRuntimeException("Unknown accessor id: " + accessorId);
        }
        return accessor.getDataCodec().decode(buffer);
    }

    public static void ENCODE_ALL(@NotNull Map<ZPNetDataAccessor<?>, ZPNetDataVar<?>> vars, @NotNull FriendlyByteBuf buffer, @NotNull Function<Integer, ZPNetDataAccessor<?>> accessorGetterFunc) {
        if (vars.size() > ZPAccessorsNetStaticSyncUtil.MAX_STATIC_VALUES) {
            ZPLogger.error("StaticNetValues max size: " + ZPAccessorsNetStaticSyncUtil.MAX_STATIC_VALUES);
        }
        buffer.writeVarInt(Math.min(ZPAccessorsNetStaticSyncUtil.MAX_STATIC_VALUES, vars.size()));
        int i = 0;
        for (Map.Entry<ZPNetDataAccessor<?>, ZPNetDataVar<?>> entry : vars.entrySet()) {
            if (i++ >= ZPAccessorsNetStaticSyncUtil.MAX_STATIC_VALUES) {
                break;
            }
            final ZPNetDataAccessor<?> accessor = entry.getKey();
            buffer.writeVarInt(accessor.getGlobalId());
            ZPAccessorsNetStaticSyncUtil.ENCODE(entry.getValue(), accessor.getGlobalId(), buffer, accessorGetterFunc);
        }
    }

    public static @NotNull Map<ZPNetDataAccessor<?>, ZPNetDataVar<?>> DECODE_ALL(@NotNull FriendlyByteBuf buffer, @NotNull Function<Integer, ZPNetDataAccessor<?>> accessorGetterFunc) {
        int size = buffer.readVarInt();
        if (size > ZPAccessorsNetStaticSyncUtil.MAX_STATIC_VALUES) {
            throw new ZPRuntimeException("StaticNetValues max size: " + size);
        }
        if (size < 0) {
            throw new ZPRuntimeException("StaticNetValues min size: " + 0);
        }
        final Map<ZPNetDataAccessor<?>, ZPNetDataVar<?>> vars = new HashMap<>(size);
        for (int i = 0; i < size; i++) {
            final int accessorId = buffer.readVarInt();
            final ZPNetDataAccessor<?> accessor = accessorGetterFunc.apply(accessorId);
            if (accessor != null) {
                vars.put(accessor, ZPAccessorsNetStaticSyncUtil.DECODE(accessorId, buffer, accessorGetterFunc));
            }
        }
        return vars;
    }
}