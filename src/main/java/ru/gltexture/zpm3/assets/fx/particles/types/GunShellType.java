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
import ru.gltexture.zpm3.assets.fx.particles.options.GunShellOptions;

public class GunShellType extends ParticleType<GunShellOptions> {
    public static final Codec<Vector3f> VECTOR3F_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.FLOAT.fieldOf("x").forGetter(Vector3f::x),
            Codec.FLOAT.fieldOf("y").forGetter(Vector3f::y),
            Codec.FLOAT.fieldOf("z").forGetter(Vector3f::z)
    ).apply(instance, Vector3f::new));

    public final Codec<GunShellOptions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            GunShellType.VECTOR3F_CODEC.fieldOf("color").forGetter(GunShellOptions::color)
    ).apply(instance, (color) -> new GunShellOptions(this, color)));

    @SuppressWarnings("deprecation")
    public static final ParticleOptions.Deserializer<GunShellOptions> DESERIALIZER = new ParticleOptions.Deserializer<>() {
        public @NotNull GunShellOptions fromCommand(@NotNull ParticleType<GunShellOptions> type, StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            float r = reader.readFloat();
            reader.expect(' ');
            float g = reader.readFloat();
            reader.expect(' ');
            float b = reader.readFloat();
            return new GunShellOptions(type, new Vector3f(r, g, b));
        }

        public @NotNull GunShellOptions fromNetwork(@NotNull ParticleType<GunShellOptions> type, FriendlyByteBuf buffer) {
            float r = buffer.readFloat();
            float g = buffer.readFloat();
            float b = buffer.readFloat();
            float scale = buffer.readFloat();
            int life = buffer.readInt();
            return new GunShellOptions(type, new Vector3f(r, g, b));
        }
    };

    public GunShellType(boolean pOverrideLimiter) {
        super(pOverrideLimiter, GunShellType.DESERIALIZER);
    }

    @Override
    public @NotNull Codec<GunShellOptions> codec() {
        return this.CODEC;
    }
}
