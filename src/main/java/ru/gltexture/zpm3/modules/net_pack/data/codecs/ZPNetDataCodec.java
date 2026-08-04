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

package ru.gltexture.zpm3.modules.net_pack.data.codecs;

import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.modules.net_pack.data.data_ent.ZPNetDataVar;
import ru.gltexture.zpm3.modules.net_pack.data.vars.*;

import java.util.UUID;

public interface ZPNetDataCodec <E> {
    ZPNetDataCodec<Boolean> BOOLEAN = new ZPNetDataCodec<>() {
        @Override
        public ZPNetDataVar<Boolean> decode(@NotNull FriendlyByteBuf buffer) {
            return new ZPNetDataBoolean(buffer.readBoolean());
        }

        @Override
        public void encode(@NotNull FriendlyByteBuf buffer, @NotNull ZPNetDataVar<Boolean> value) {
            buffer.writeBoolean(value.getValue());
        }
    };

    ZPNetDataCodec<Byte> BYTE = new ZPNetDataCodec<>() {
        @Override
        public ZPNetDataVar<Byte> decode(@NotNull FriendlyByteBuf buffer) {
            return new ZPNetDataByte(buffer.readByte());
        }

        @Override
        public void encode(@NotNull FriendlyByteBuf buffer, @NotNull ZPNetDataVar<Byte> value) {
            buffer.writeByte(value.getValue());
        }
    };

    ZPNetDataCodec<Short> SHORT = new ZPNetDataCodec<>() {
        @Override
        public ZPNetDataVar<Short> decode(@NotNull FriendlyByteBuf buffer) {
            return new ZPNetDataShort(buffer.readShort());
        }

        @Override
        public void encode(@NotNull FriendlyByteBuf buffer, @NotNull ZPNetDataVar<Short> value) {
            buffer.writeShort(value.getValue());
        }
    };

    ZPNetDataCodec<Long> LONG = new ZPNetDataCodec<>() {
        @Override
        public ZPNetDataVar<Long> decode(@NotNull FriendlyByteBuf buffer) {
            return new ZPNetDataLong(buffer.readLong());
        }

        @Override
        public void encode(@NotNull FriendlyByteBuf buffer, @NotNull ZPNetDataVar<Long> value) {
            buffer.writeLong(value.getValue());
        }
    };

    ZPNetDataCodec<Integer> INTEGER = new ZPNetDataCodec<>() {
        @Override
        public ZPNetDataVar<Integer> decode(@NotNull FriendlyByteBuf buffer) {
            return new ZPNetDataInt(buffer.readInt());
        }

        @Override
        public void encode(@NotNull FriendlyByteBuf buffer, @NotNull ZPNetDataVar<Integer> value) {
            buffer.writeInt(value.getValue());
        }
    };

    ZPNetDataCodec<Float> FLOAT = new ZPNetDataCodec<>() {
        @Override
        public ZPNetDataVar<Float> decode(@NotNull FriendlyByteBuf buffer) {
            return new ZPNetDataFloat(buffer.readFloat());
        }

        @Override
        public void encode(@NotNull FriendlyByteBuf buffer, @NotNull ZPNetDataVar<Float> value) {
            buffer.writeFloat(value.getValue());
        }
    };

    ZPNetDataCodec<Double> DOUBLE = new ZPNetDataCodec<>() {
        @Override
        public ZPNetDataVar<Double> decode(@NotNull FriendlyByteBuf buffer) {
            return new ZPNetDataDouble(buffer.readDouble());
        }

        @Override
        public void encode(@NotNull FriendlyByteBuf buffer, @NotNull ZPNetDataVar<Double> value) {
            buffer.writeDouble(value.getValue());
        }
    };

    ZPNetDataCodec<String> STRING = new ZPNetDataCodec<>() {
        final int MAX_STR_LENGTH = 512;

        @Override
        public ZPNetDataVar<String> decode(@NotNull FriendlyByteBuf buffer) {
            return new ZPNetDataString(buffer.readUtf(MAX_STR_LENGTH));
        }

        @Override
        public void encode(@NotNull FriendlyByteBuf buffer, @NotNull ZPNetDataVar<String> value) {
            buffer.writeUtf(value.getValue(), MAX_STR_LENGTH);
        }
    };

    ZPNetDataCodec<UUID> UUID = new ZPNetDataCodec<>() {
        @Override
        public ZPNetDataVar<UUID> decode(@NotNull FriendlyByteBuf buffer) {
            return new ZPNetDataUUID(buffer.readUUID());
        }

        @Override
        public void encode(@NotNull FriendlyByteBuf buffer, @NotNull ZPNetDataVar<UUID> value) {
            buffer.writeUUID(value.getValue());
        }
    };
    
    ZPNetDataVar<E> decode(@NotNull FriendlyByteBuf buffer);
    void encode(@NotNull FriendlyByteBuf buffer, @NotNull ZPNetDataVar<E> value);
}
