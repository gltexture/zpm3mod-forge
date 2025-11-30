package ru.gltexture.zpm3.assets.fx.particles.options;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import ru.gltexture.zpm3.assets.fx.particles.types.ColoredDefaultParticleType;

public record ColoredDefaultParticleOptions(@NotNull ParticleType<ColoredDefaultParticleOptions> particleType, @NotNull Vector3f color, float scale, int lifeTime, float gravity) implements ParticleOptions {
    public ColoredDefaultParticleOptions(@NotNull ColoredDefaultParticleType coloredDefaultParticleType, Vector3f color, float scale, int lifetime) {
        this(coloredDefaultParticleType, color, scale, lifetime, -0.01f);
    }

    @Override
    public @NotNull ParticleType<?> getType() {
        return this.particleType();
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf pBuffer) {
        pBuffer.writeFloat(this.color().x());
        pBuffer.writeFloat(this.color().y());
        pBuffer.writeFloat(this.color().z());
        pBuffer.writeFloat(this.scale());
        pBuffer.writeInt(this.lifeTime());
        pBuffer.writeFloat(this.gravity());
    }

    @Override
    public @NotNull String writeToString() {
        return String.format("%f %f %f %f %d %f", color.x(), color.y(), color.z(), scale, lifeTime, gravity);
    }
}
