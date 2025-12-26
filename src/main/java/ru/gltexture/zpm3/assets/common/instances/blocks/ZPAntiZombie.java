package ru.gltexture.zpm3.assets.common.instances.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BarrierBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import ru.gltexture.zpm3.assets.entity.instances.mobs.zombies.ZPAbstractZombie;
import ru.gltexture.zpm3.assets.fx.init.ZPParticles;
import ru.gltexture.zpm3.assets.fx.particles.options.ColoredDefaultParticleOptions;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;
import ru.gltexture.zpm3.engine.instances.blocks.ZPInvisibleBlock;

public class ZPAntiZombie extends ZPInvisibleBlock {
    @Override
    @SuppressWarnings("all")
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (level.isClientSide) {
            return;
        }
        if (entity instanceof ZPAbstractZombie zombie) {
            ZPAntiZombie.eraseMob(zombie);
        }
    }

    public static void eraseMob(@NotNull LivingEntity entity) {
        AABB box = entity.getBoundingBox();
        for (int i = 0; i < 6; i++) {
            double x = Mth.lerp(entity.level().random.nextDouble(), box.minX, box.maxX);
            double y = Mth.lerp(entity.level().random.nextDouble(), box.minY + 0.15, box.maxY - 0.15);
            double z = Mth.lerp(entity.level().random.nextDouble(), box.minZ, box.maxZ);
            ((ServerLevel) entity.level()).sendParticles(new ColoredDefaultParticleOptions(ZPParticles.colored_cloud.get(), ZPRandom.instance.randomVector3f(new Vector3f(0.75f, 0.85f, 1.0f), new Vector3f(0.1f)), 1.25f + ZPRandom.getRandom().nextFloat(), 40 + ZPRandom.getRandom().nextInt(10)), x, y, z, 12, 0.15, 0.15, 0.15, 0.02);
        }
        entity.level().playSound(null, entity.getX() + 0.5, entity.getY() + 0.5, entity.getZ() + 0.5, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 1.25F);
        entity.discard();
    }
}