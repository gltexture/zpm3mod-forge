package ru.gltexture.zpm3.assets.fx.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import ru.gltexture.zpm3.assets.fx.particles.options.ColoredSmokeOptions;

public class ParticleColoredCloud extends AbstractSmokeParticle {
    protected ParticleColoredCloud(ClientLevel level, SpriteSet pSprites, Vector3f position, Vector3f velocity, Vector3f color, float scale, int lifeTime) {
        super(level, pSprites, position, velocity, color, scale, lifeTime);
    }

    public static class ColoredSmokeParticleProvider implements ParticleProvider<ColoredSmokeOptions> {
        private final SpriteSet spriteSet;

        public ColoredSmokeParticleProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle createParticle(ColoredSmokeOptions options, @NotNull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new ParticleColoredCloud(level, this.spriteSet, new Vector3f((float) x, (float) y, (float) z), new Vector3f((float) xSpeed, (float) ySpeed, (float) zSpeed), options.color(), options.scale(), options.lifeTime());
        }
    }
}
