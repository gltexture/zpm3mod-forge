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

package ru.gltexture.zpm3.modules.mob_effects.events.common;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import ru.gltexture.zpm3.engine.core.config.builtin.ZPCombatConfig;
import ru.gltexture.zpm3.engine.core.config.builtin.ZPEntityConfig;
import ru.gltexture.zpm3.engine.network.handler.ZPNetworkHandler;
import ru.gltexture.zpm3.engine.network.handler.ZPNetworkHandlerServer;
import ru.gltexture.zpm3.modules.common.init.ZPDamageTypes;
import ru.gltexture.zpm3.modules.common.init.ZPSounds;
import ru.gltexture.zpm3.modules.entity.instances.mobs.zombies.ZPAbstractZombie;
import ru.gltexture.zpm3.modules.mob_effects.init.ZPMobEffects;
import ru.gltexture.zpm3.modules.mob_effects.utils.ZPEffectUtils;
import ru.gltexture.zpm3.modules.net_pack.packets.S2C.ZPBloodPainFXPacket;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;
import ru.gltexture.zpm3.engine.events.ZPForgeEventHandlerClass;

import java.util.Objects;

public class ZPEntityEffectActionsEvent implements ZPForgeEventHandlerClass {
    @SubscribeEvent
    public static void exec(@NotNull LivingEvent.LivingTickEvent event) {
        if (event.getEntity().level().isClientSide()) {
            if (ZPBloodPainFXPacket.hasBlood(event.getEntity()) && ZPEffectUtils.isBleeding(event.getEntity())) {
                if (event.getEntity().tickCount % 10 == 0) {
                    ZombiePlague3.netServer().sendToDimensionRadius(new ZPBloodPainFXPacket(event.getEntity().getId(), true), event.getEntity().getCommandSenderWorld().dimension(), event.getEntity().position(), 64.0f);
                }
            }
        }
    }

    @SubscribeEvent
    public static void exec(@NotNull LivingDamageEvent event) {
        if (event.getEntity().level() instanceof ServerLevel serverLevel) {
            if (ZPBloodPainFXPacket.hasBlood(event.getEntity())) {
                ZombiePlague3.netServer().sendToDimensionRadius(new ZPBloodPainFXPacket(event.getEntity().getId(), false), event.getEntity().getCommandSenderWorld().dimension(), event.getEntity().position(), 64.0f);
            }
            final LivingEntity entity = event.getEntity();
            if (!ZPEntityConfig.BLEEDING_ONLY_FOR_PLAYERS.getVar() || (entity instanceof Player)) {
                final DamageSource source = event.getSource();
                boolean canCauseBleeding =
                        source.getEntity() != null ||
                        source.getDirectEntity() instanceof Projectile;
                if (canCauseBleeding && event.getAmount() > 2.0f) {
                    if (!(entity instanceof ZPAbstractZombie)) {
                        float bleedingChance = event.getEntity().level().getDifficulty().equals(Difficulty.HARD) ? 0.75f : event.getEntity().level().getDifficulty().equals(Difficulty.NORMAL) ? 0.5f : 0.25f;
                        float damage = Math.min(event.getAmount() / 10.0f, 1.5f);
                        float armorMultiplier = 1.0f - (event.getEntity().getArmorValue() / 40.0f);
                        damage *= armorMultiplier;
                        bleedingChance *= damage;
                        bleedingChance *= ZPCombatConfig.BLEEDING_CHANCE_MULTIPLIER.getVar();
                        if (ZPEffectUtils.isBleeding(entity)) {
                            bleedingChance += 0.15f * Objects.requireNonNull(entity.getEffect(ZPMobEffects.bleeding.get())).getAmplifier();
                        }
                        int duration = (int) (1000 + ((2600 * damage) * armorMultiplier));
                        if (ZPRandom.getRandom().nextFloat() <= bleedingChance) {
                            if (ZPEffectUtils.isBleeding(entity)) {
                                int durationO = Objects.requireNonNull(entity.getEffect(ZPMobEffects.bleeding.get())).getDuration();
                                int ampO = Objects.requireNonNull(entity.getEffect(ZPMobEffects.bleeding.get())).getAmplifier();
                                entity.removeEffect(ZPMobEffects.bleeding.get());
                                entity.addEffect(new MobEffectInstance(ZPMobEffects.bleeding.get(), (int) ((durationO * 0.75f) + duration), Math.min(ampO + 1, 3)));
                            } else {
                                entity.addEffect(new MobEffectInstance(ZPMobEffects.bleeding.get(), duration));
                            }
                        }
                    }
                }
            }
            if (entity instanceof Player player) {
                if (event.getSource().type().equals(ZPDamageTypes.getDamageType(serverLevel, DamageTypes.FALL).get())) {
                    if (event.getAmount() >= 3.0f) {
                        float damNorm = event.getAmount() / 20.0f;
                        float fractureChance = (float) (4.0f * Math.pow(damNorm, 0.4f * Math.E)) * ZPCombatConfig.FRACTURE_CHANCE_MULTIPLIER.getVar();
                        if (ZPRandom.getRandom().nextFloat() <= fractureChance) {
                            if (ZPEffectUtils.isFractured(entity)) {
                                event.setAmount(event.getAmount() * 2.0f);
                            } else {
                                player.setSprinting(false);
                                float timeMultiplier = event.getAmount() / 3.0f;
                                entity.addEffect(new MobEffectInstance(ZPMobEffects.fracture.get(), (int) (7200 * timeMultiplier)));
                                player.level().playSound(null, player.getOnPos(), ZPSounds.fracture.get(), SoundSource.MASTER, 1.0f, 1.0f);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public @NotNull ZPSide getSide() {
        return ZPSide.COMMON;
    }

    @Override
    public @NotNull Mod.EventBusSubscriber.Bus getBus() {
        return Mod.EventBusSubscriber.Bus.FORGE;
    }
}