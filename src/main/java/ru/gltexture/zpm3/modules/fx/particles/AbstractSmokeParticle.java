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
import net.minecraft.client.particle.*;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public abstract class AbstractSmokeParticle extends TextureSheetParticle {
    private final SpriteSet pSprites;

    protected AbstractSmokeParticle(ClientLevel level, @NotNull SpriteSet pSprites, Vector3f position, Vector3f velocity, Vector3f color, float scale, int lifeTime, float gravity) {
        super(level, position.x, position.y, position.z, velocity.x, velocity.y, velocity.z);

        this.pSprites = pSprites;
        this.setSpriteFromAge(this.pSprites);
        this.rCol = color.x;
        this.gCol = color.y;
        this.bCol = color.z;
        this.scale(scale);
        this.lifetime = lifeTime;
        this.gravity = gravity;
        this.hasPhysics = true;

        this.startVelocity(velocity, 0.1f);
    }

    @SuppressWarnings("all")
    protected void startVelocity(Vector3f velocity, final float multiplier) {
        this.xd = velocity.x;
        this.yd = velocity.y;
        this.zd = velocity.z;
    }

    @Override
    public void tick() {
        super.tick();

        this.setSpriteFromAge(this.pSprites);
        final float e2 = (float) Math.exp(4.0f);
        this.alpha = (float) (1.0f - Math.pow((double) this.age / this.lifetime, e2));
    }

    @Override
    public void setSpriteFromAge(@NotNull SpriteSet pSprite) {
        if (!this.removed) {
            this.setSprite(pSprite.get(this.age, this.lifetime));
        }
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }
}