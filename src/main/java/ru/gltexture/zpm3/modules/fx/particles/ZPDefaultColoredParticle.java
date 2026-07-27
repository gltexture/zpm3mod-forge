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

package ru.gltexture.zpm3.modules.fx.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import ru.gltexture.zpm3.modules.fx.particles.options.ColoredDefaultParticleOptions;

public class ZPDefaultColoredParticle extends AbstractSmokeParticle {
    protected ZPDefaultColoredParticle(ClientLevel level, SpriteSet pSprites, Vector3f position, Vector3f velocity, Vector3f color, float scale, int lifeTime, float gravity) {
        super(level, pSprites, position, velocity, color, scale, lifeTime, gravity);
    }

    public static class ColoredDefaultParticleProvider implements ParticleProvider<ColoredDefaultParticleOptions> {
        private final SpriteSet spriteSet;

        public ColoredDefaultParticleProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle createParticle(ColoredDefaultParticleOptions options, @NotNull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new ZPDefaultColoredParticle(level, this.spriteSet, new Vector3f((float) x, (float) y, (float) z), new Vector3f((float) xSpeed, (float) ySpeed, (float) zSpeed), options.color(), options.scale(), options.lifeTime(), options.gravity());
        }
    }
}