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

package ru.gltexture.zpm3.modules.mob_effects.instances;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
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

import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.config.builtin.ZPZombieConfig;
import ru.gltexture.zpm3.modules.entity.instances.mobs.zombies.ZPCommonZombie;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;
import ru.gltexture.zpm3.modules.entity.util.ZPEntityUtil;

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
            final int duration = Objects.requireNonNull(entity.getEffect(this)).getDuration();
            final float progress = ZPEntityUtil.getEntityPlaguePercentage(entity);

            if (entity instanceof ServerPlayer player) {
                if (progress >= 0.3f) {
                    SoundEvent soundevent = SoundEvents.ZOMBIE_AMBIENT;
                    if (player.tickCount % 40 == 0 && ZPRandom.getRandom().nextFloat() <= 0.25f) {
                        player.playSound(soundevent, 1.0f, (ZPRandom.getRandom().nextFloat() - ZPRandom.getRandom().nextFloat()) * 0.2F + 1.0F);
                    }
                }
                if (progress >= 0.75f) {
                    player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 300, 0, false, false));
                }
                if (progress >= 0.50f) {
                    player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 300, 0, false, false));
                }
                if (progress >= 0.25f) {
                    player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 300, 0, false, false));
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
        zombie.moveTo(pos.getX(), pos.getY(), pos.getZ(), player.getYRot(), player.getXRot());
        level.addFreshEntity(zombie);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void initializeClient(@NotNull Consumer<IClientMobEffectExtensions> consumer) {
        consumer.accept(new DefaultZPEffectClientExtension(true, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "textures/mob_effects/zombie_plague.png")));
    }
}
