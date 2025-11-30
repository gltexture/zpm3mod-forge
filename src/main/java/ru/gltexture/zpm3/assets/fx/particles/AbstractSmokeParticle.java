package ru.gltexture.zpm3.assets.fx.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
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