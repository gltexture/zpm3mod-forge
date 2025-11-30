package ru.gltexture.zpm3.assets.mob_effects.instances;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientMobEffectExtensions;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.global.ZPConstants;
import ru.gltexture.zpm3.assets.common.instances.entities.mobs.zombies.ZPCommonZombie;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;

import java.util.Objects;
import java.util.function.Consumer;

public class ZPZombiePlagueEffect extends ZPDefaultMobEffect {
    public ZPZombiePlagueEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return pDuration % 20 == 0;
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity entity, int amplifier) {
        if (entity.getEffect(this) != null) {
            int duration = Objects.requireNonNull(entity.getEffect(this)).getDuration();
            float percentLeft = duration / (float) ZPConstants.ZOMBIE_PLAGUE_VIRUS_EFFECT_TIME_TICKS;
            float progress = 1.0f - percentLeft;

            if (entity instanceof Player player && !entity.level().isClientSide()) {
                if (progress >= 0.5f) {
                    SoundEvent soundevent = SoundEvents.ZOMBIE_AMBIENT;
                    if (player.tickCount % 40 == 0 && ZPRandom.getRandom().nextFloat() <= 0.25f) {
                        player.playSound(soundevent, 1.0f, (ZPRandom.getRandom().nextFloat() - ZPRandom.getRandom().nextFloat()) * 0.2F + 1.0F);
                    }
                }
                if (progress >= 0.75f) {
                    player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 0, false, false));
                }
                if (progress >= 0.50f) {
                    player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 60, 0, false, false));
                }
                if (progress >= 0.25f) {
                    player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 80, 0, false, false));
                }
                if (duration <= 20) {
                    this.finishPlague(player);
                }
            }
        }
    }

    private void finishPlague(Player player) {
        Level level = player.level();
        BlockPos pos = player.blockPosition();
        player.kill();
        ZPCommonZombie zombie = new ZPCommonZombie(level);
        zombie.moveTo(pos.getX(), pos.getY(), pos.getZ(), 0, 0);
        level.addFreshEntity(zombie);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void initializeClient(@NotNull Consumer<IClientMobEffectExtensions> consumer) {
        consumer.accept(new DefaultZPEffectClientExtension(true, "zombie_plague.png"));
    }
}
