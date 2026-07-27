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

package ru.gltexture.zpm3.modules.fx.particles.options;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import ru.gltexture.zpm3.modules.fx.particles.types.ColoredDefaultParticleType;

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
