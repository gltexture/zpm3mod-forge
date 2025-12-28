package ru.gltexture.zpm3.assets.mob_effects.events.common;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.global.ZPConstants;
import ru.gltexture.zpm3.assets.common.init.ZPDamageTypes;
import ru.gltexture.zpm3.assets.common.init.ZPSounds;
import ru.gltexture.zpm3.assets.entity.instances.mobs.zombies.ZPAbstractZombie;
import ru.gltexture.zpm3.assets.mob_effects.init.ZPMobEffects;
import ru.gltexture.zpm3.assets.mob_effects.utils.ZPEffectUtils;
import ru.gltexture.zpm3.assets.net_pack.packets.ZPBloodPainFXPacket;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;
import ru.gltexture.zpm3.engine.events.ZPEventClass;

import java.util.Objects;

public class ZPEntityEffectActionsEvent implements ZPEventClass {
    @SubscribeEvent
    public static void exec(@NotNull LivingEvent.LivingTickEvent event) {
        if (event.getEntity().level().isClientSide()) {
            if (ZPBloodPainFXPacket.hasBlood(event.getEntity()) && ZPEffectUtils.isBleeding(event.getEntity())) {
                if (event.getEntity().tickCount % 10 == 0) {
                    ZombiePlague3.net().sendToDimensionRadius(new ZPBloodPainFXPacket(event.getEntity().getId(), true), event.getEntity().getCommandSenderWorld().dimension(), event.getEntity().position(), 64.0f);
                }
            }
        }
    }

    @SubscribeEvent
    public static void exec(@NotNull LivingDamageEvent event) {
        if (!event.getEntity().level().isClientSide()) {
            if (ZPBloodPainFXPacket.hasBlood(event.getEntity())) {
                ZombiePlague3.net().sendToDimensionRadius(new ZPBloodPainFXPacket(event.getEntity().getId(), false), event.getEntity().getCommandSenderWorld().dimension(), event.getEntity().position(), 64.0f);
            }

            LivingEntity entity = event.getEntity();
            if (!ZPConstants.BLEEDING_ONLY_FOR_PLAYERS || (entity instanceof Player)) {
                if (!(entity instanceof ZPAbstractZombie)) {
                    float bleedingChance = event.getEntity().level().getDifficulty().equals(Difficulty.HARD) ? 0.05f : 0.025f;
                    float damage = event.getAmount() / 20.0f;
                    float armorMultiplier = 1.0f - (event.getEntity().getArmorValue() / 24.0f);
                    damage *= armorMultiplier;
                    bleedingChance *= damage;
                    bleedingChance *= ZPConstants.BLEEDING_CHANCE_MULTIPLIER;
                    int duration = (int) (600 + (600 * damage * armorMultiplier));
                    if (ZPRandom.getRandom().nextFloat() <= bleedingChance) {
                        if (ZPEffectUtils.isBleeding(entity)) {
                            int durationO = Objects.requireNonNull(entity.getEffect(ZPMobEffects.bleeding.get())).getDuration();
                            int ampO = Objects.requireNonNull(entity.getEffect(ZPMobEffects.bleeding.get())).getAmplifier();
                            entity.removeEffect(ZPMobEffects.bleeding.get());
                            entity.addEffect(new MobEffectInstance(ZPMobEffects.bleeding.get(), (int) (durationO * 0.25f + duration), ampO + 1));
                        } else {
                            entity.addEffect(new MobEffectInstance(ZPMobEffects.bleeding.get(), duration));
                        }
                    }
                }
            }
            if (entity instanceof Player player) {
                if (event.getEntity().level() instanceof ServerLevel serverLevel && event.getSource().type().equals(ZPDamageTypes.getDamageType(serverLevel, DamageTypes.FALL).get())) {
                    if (event.getAmount() >= 3.0f) {
                        float damNorm = event.getAmount() / 20.0f;
                        float fractureChance = (float) (4.0f * Math.pow(damNorm, 0.4f * Math.E)) * ZPConstants.FRACTURE_CHANCE_MULTIPLIER;
                        if (ZPRandom.getRandom().nextFloat() <= fractureChance) {
                            if (ZPEffectUtils.isFractured(entity)) {
                                event.setAmount(event.getAmount() * 2.0f);
                            } else {
                                player.setSprinting(false);
                                float timeMultiplier = event.getAmount() / 3.0f;
                                entity.addEffect(new MobEffectInstance(ZPMobEffects.fracture.get(), (int) (6000 * timeMultiplier)));
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