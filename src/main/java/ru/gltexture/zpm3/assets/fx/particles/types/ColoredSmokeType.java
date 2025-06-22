package ru.gltexture.zpm3.assets.fx.particles.types;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import ru.gltexture.zpm3.assets.fx.particles.options.ColoredSmokeOptions;

public class ColoredSmokeType extends ParticleType<ColoredSmokeOptions> {
    public static final Codec<Vector3f> VECTOR3F_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.FLOAT.fieldOf("x").forGetter(Vector3f::x),
            Codec.FLOAT.fieldOf("y").forGetter(Vector3f::y),
            Codec.FLOAT.fieldOf("z").forGetter(Vector3f::z)
    ).apply(instance, Vector3f::new));

    public final Codec<ColoredSmokeOptions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ColoredSmokeType.VECTOR3F_CODEC.fieldOf("color").forGetter(ColoredSmokeOptions::color),
            Codec.FLOAT.fieldOf("scale").forGetter(ColoredSmokeOptions::scale),
            Codec.INT.fieldOf("lifeTime").forGetter(ColoredSmokeOptions::lifeTime)
    ).apply(instance, (color, scale, lifeTime) -> new ColoredSmokeOptions(this, color, scale, lifeTime)));

    @SuppressWarnings("deprecation")
    public static final ParticleOptions.Deserializer<ColoredSmokeOptions> DESERIALIZER = new ParticleOptions.Deserializer<>() {
        public @NotNull ColoredSmokeOptions fromCommand(@NotNull ParticleType<ColoredSmokeOptions> type, StringReader reader) throws CommandSyntaxException {
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
            return new ColoredSmokeOptions(type, new Vector3f(r, g, b), scale, life);
        }

        public @NotNull ColoredSmokeOptions fromNetwork(@NotNull ParticleType<ColoredSmokeOptions> type, FriendlyByteBuf buffer) {
            float r = buffer.readFloat();
            float g = buffer.readFloat();
            float b = buffer.readFloat();
            float scale = buffer.readFloat();
            int life = buffer.readInt();
            return new ColoredSmokeOptions(type, new Vector3f(r, g, b), scale, life);
        }
    };

    public ColoredSmokeType(boolean pOverrideLimiter) {
        super(pOverrideLimiter, ColoredSmokeType.DESERIALIZER);
    }

    @Override
    public @NotNull Codec<ColoredSmokeOptions> codec() {
        return this.CODEC;
    }
}
