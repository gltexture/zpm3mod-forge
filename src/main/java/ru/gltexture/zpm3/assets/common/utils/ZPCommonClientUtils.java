package ru.gltexture.zpm3.assets.common.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import ru.gltexture.zpm3.assets.fx.init.ZPParticles;
import ru.gltexture.zpm3.assets.fx.particles.options.ColoredSmokeOptions;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;

import java.util.Objects;

@OnlyIn(Dist.CLIENT)
public abstract class ZPCommonClientUtils {
    public static void emmitUraniumParticle(final float scale, final @NotNull Vector3f position, final @NotNull Vector3f velocity) {
        final Vector3f color = new Vector3f(0.6f, 0.9f, 0.6f).add(ZPRandom.instance.randomVector3f(0.0f, new Vector3f(0.1f, 0.1f, 0.1f)));
        final int lifetime = 30;
        final double x = position.x;
        final double y = position.y;
        final double z = position.z;
        Objects.requireNonNull(Minecraft.getInstance().level).addParticle(new ColoredSmokeOptions(ZPParticles.colored_cloud.get(), color, scale, lifetime), true, x, y, z, velocity.x(), velocity.y(), velocity.z());
    }

    public static void emmitAcidParticle(final float scale, final @NotNull Vector3f position, final @NotNull Vector3f velocity) {
        final Vector3f color = new Vector3f(0.8f, 0.9f, 0.8f).add(ZPRandom.instance.randomVector3f(0.0f, new Vector3f(0.2f, 0.0f, 0.2f)));
        final int lifetime = 40;
        final double x = position.x;
        final double y = position.y;
        final double z = position.z;
        Objects.requireNonNull(Minecraft.getInstance().level).addParticle(new ColoredSmokeOptions(ZPParticles.colored_cloud.get(), color, scale, lifetime), true, x, y, z, velocity.x(), velocity.y(), velocity.z());
    }

    public static void emmitItemBreakParticle(final @NotNull ItemStack item, final @NotNull Vector3f position, final @NotNull Vector3f entityMotion) {
        for (int i = 0; i < 16; ++i) {
            Vector3f velocity = new Vector3f(entityMotion).negate();
            velocity.mul(0.1f);
            velocity.add(ZPRandom.instance.randomVector3f(0.1f, new Vector3f(0.2f)));
            Objects.requireNonNull(Minecraft.getInstance().level).addParticle(new ItemParticleOption(ParticleTypes.ITEM, item), position.x, position.y, position.z, velocity.x, velocity.y, velocity.z);
        }
    }

    public static Vector3f getParticleSpawnPositionBlockDir(BlockPos pos, Direction face, RandomSource random) {
        float x = pos.getX() + 0.5f;
        float y = pos.getY() + 0.5f;
        float z = pos.getZ() + 0.5f;
        float spread = 0.3f;

        final float offset = 0.05f;

        switch (face) {
            case DOWN -> y = pos.getY() - offset;
            case UP -> y = pos.getY() + (offset + 1.0f);
            case NORTH -> z = pos.getZ() - offset;
            case SOUTH -> z = pos.getZ() + (offset + 1.0f);
            case WEST -> x = pos.getX() - offset;
            case EAST -> x = pos.getX() + (offset + 1.0f);
        }

        if (face.getAxis() != Direction.Axis.X) {
            x += (random.nextFloat() - 0.5f) * spread;
        }

        if (face.getAxis() != Direction.Axis.Y) {
            y += (random.nextFloat() - 0.5f) * spread;
        }

        if (face.getAxis() != Direction.Axis.Z) {
            z += (random.nextFloat() - 0.5f) * spread;
        }

        return new Vector3f(x, y, z);
    }
}
