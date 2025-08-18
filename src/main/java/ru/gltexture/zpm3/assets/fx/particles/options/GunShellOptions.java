package ru.gltexture.zpm3.assets.fx.particles.options;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public record GunShellOptions(@NotNull ParticleType<GunShellOptions> particleType, @NotNull Vector3f color) implements ParticleOptions {
    @Override
    public @NotNull ParticleType<?> getType() {
        return this.particleType();
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf pBuffer) {
        pBuffer.writeFloat(this.color().x());
        pBuffer.writeFloat(this.color().y());
        pBuffer.writeFloat(this.color().z());
    }

    @Override
    public @NotNull String writeToString() {
        return String.format("%f %f %f", color.x(), color.y(), color.z());
    }
}
