package ru.gltexture.zpm3.assets.mob_effects.utils;

import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.mob_effects.init.ZPMobEffects;

public abstract class ZPEffectUtils {
    public static boolean isBleeding(@NotNull LivingEntity livingEntity) {
        return livingEntity.hasEffect(ZPMobEffects.bleeding.get());
    }

    public static boolean isZombiePlagued(@NotNull LivingEntity livingEntity) {
        return livingEntity.hasEffect(ZPMobEffects.zombie_plague.get());
    }

    public static boolean isFractured(@NotNull LivingEntity livingEntity) {
        return livingEntity.hasEffect(ZPMobEffects.fracture.get());
    }

    public static boolean isBetterVisioned(@NotNull LivingEntity livingEntity) {
        return livingEntity.hasEffect(ZPMobEffects.better_vision.get());
    }

    public static boolean isAdrenalined(@NotNull LivingEntity livingEntity) {
        return livingEntity.hasEffect(ZPMobEffects.adrenaline.get());
    }
}
