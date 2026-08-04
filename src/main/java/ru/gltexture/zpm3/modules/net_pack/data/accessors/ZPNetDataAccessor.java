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

package ru.gltexture.zpm3.modules.net_pack.data.accessors;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.exceptions.ZPRuntimeException;
import ru.gltexture.zpm3.modules.net_pack.data.codecs.ZPNetDataCodec;
import ru.gltexture.zpm3.modules.net_pack.data.data_ent.ZPNetDataVar;
import ru.gltexture.zpm3.modules.net_pack.data.vars.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.function.Supplier;

public abstract class ZPNetDataAccessor <T> {
    public static final ZPNetDataBoolean DEFAULT_BOOLEAN = new ZPNetDataBoolean(false);
    public static final ZPNetDataByte DEFAULT_BYTE = new ZPNetDataByte((byte) 0);
    public static final ZPNetDataShort DEFAULT_SHORT = new ZPNetDataShort((short) 0);
    public static final ZPNetDataInt DEFAULT_INT = new ZPNetDataInt(0);
    public static final ZPNetDataLong DEFAULT_LONG = new ZPNetDataLong(0L);
    public static final ZPNetDataFloat DEFAULT_FLOAT = new ZPNetDataFloat(0.0f);
    public static final ZPNetDataDouble DEFAULT_DOUBLE = new ZPNetDataDouble(0.0d);
    public static final ZPNetDataString DEFAULT_STRING = new ZPNetDataString("");
    public static final ZPNetDataUUID DEFAULT_UUID = new ZPNetDataUUID(new UUID(0L, 0L));

    private int globalId;
    private final ZPNetDataCodec<T> dataCodec;
    private final ResourceLocation id;

    public ZPNetDataAccessor(@NotNull ResourceLocation id, ZPNetDataCodec<T> dataCodec) {
        this.dataCodec = dataCodec;
        this.id = id;
        this.globalId = -1;
        ZPGlobalAccessorsRegistry.INSTANCE.register(this);
    }

    void setGlobalId(final int globalId) {
        if (this.globalId > 0) {
            throw new ZPRuntimeException("Cannot set global id twice: " + this.getResourceId());
        }
        this.globalId = globalId;
    }

    public final ResourceLocation getResourceId() {
        return this.id;
    }

    public final int getGlobalId() {
        return this.globalId;
    }

    @Override
    public boolean equals(Object userObject) {
        if (!(userObject instanceof ZPNetDataAccessor<?> that)) {
            return false;
        }
        return Objects.equals(this.id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.id);
    }

    public final ZPNetDataCodec<T> getDataCodec() {
        return this.dataCodec;
    }

    public abstract @NotNull Supplier<@NotNull ZPNetDataVar<T>> createDefault();

    public static @NotNull String buildAccessorsHash(@NotNull Collection<ZPNetDataAccessor<?>> v_accessors) {
        try {
            final MessageDigest digest = MessageDigest.getInstance("SHA-256");
            final ArrayList<ZPNetDataAccessor<?>> accessors = new ArrayList<>(v_accessors);
            accessors.sort(Comparator.comparingInt(ZPNetDataAccessor::getGlobalId));
            for (ZPNetDataAccessor<?> accessor : accessors) {
                digest.update(Integer.toString(accessor.getGlobalId()).getBytes(StandardCharsets.UTF_8));
                digest.update((byte) ':');
                digest.update(accessor.getResourceId().toString().getBytes(StandardCharsets.UTF_8));
                digest.update((byte) '\n');
            }
            return HexFormat.of().formatHex(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new ZPRuntimeException("Unable to create accessor hash.", e);
        }
    }
}