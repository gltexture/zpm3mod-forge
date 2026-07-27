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

package ru.gltexture.zpm3.modules.fx.particles.types;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import ru.gltexture.zpm3.modules.fx.particles.options.ColoredDefaultParticleOptions;

public class ColoredDefaultParticleType extends ParticleType<ColoredDefaultParticleOptions> {
    public static final Codec<Vector3f> VECTOR3F_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.FLOAT.fieldOf("x").forGetter(Vector3f::x),
            Codec.FLOAT.fieldOf("y").forGetter(Vector3f::y),
            Codec.FLOAT.fieldOf("z").forGetter(Vector3f::z)
    ).apply(instance, Vector3f::new));

    public final Codec<ColoredDefaultParticleOptions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ColoredDefaultParticleType.VECTOR3F_CODEC.fieldOf("color").forGetter(ColoredDefaultParticleOptions::color),
            Codec.FLOAT.fieldOf("scale").forGetter(ColoredDefaultParticleOptions::scale),
            Codec.INT.fieldOf("lifeTime").forGetter(ColoredDefaultParticleOptions::lifeTime),
            Codec.FLOAT.fieldOf("gravity").forGetter(ColoredDefaultParticleOptions::gravity)
    ).apply(instance, (color, scale, lifeTime, gravity) -> new ColoredDefaultParticleOptions(this, color, scale, lifeTime, gravity)));

    @SuppressWarnings("deprecation")
    public static final ParticleOptions.Deserializer<ColoredDefaultParticleOptions> DESERIALIZER = new ParticleOptions.Deserializer<>() {
        public @NotNull ColoredDefaultParticleOptions fromCommand(@NotNull ParticleType<ColoredDefaultParticleOptions> type, StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            float r = reader.readFloat();
            reader.expect(' ');
            float g = reader.readFloat();
            reader.expect(' ');
            float b = reader.readFloat();
            reader.expect(' ');
            float scale = reader.readFloat();
            reader.expect(' ');
            int life = reader.readInt();
            reader.expect(' ');
            float gravity = reader.readFloat();
            return new ColoredDefaultParticleOptions(type, new Vector3f(r, g, b), scale, life, gravity);
        }

        public @NotNull ColoredDefaultParticleOptions fromNetwork(@NotNull ParticleType<ColoredDefaultParticleOptions> type, FriendlyByteBuf buffer) {
            float r = buffer.readFloat();
            float g = buffer.readFloat();
            float b = buffer.readFloat();
            float scale = buffer.readFloat();
            int life = buffer.readInt();
            float gravity = buffer.readFloat();
            return new ColoredDefaultParticleOptions(type, new Vector3f(r, g, b), scale, life, gravity);
        }
    };

    public ColoredDefaultParticleType(boolean pOverrideLimiter) {
        super(pOverrideLimiter, ColoredDefaultParticleType.DESERIALIZER);
    }

    @Override
    public @NotNull Codec<ColoredDefaultParticleOptions> codec() {
        return this.CODEC;
    }
}
