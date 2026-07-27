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

package ru.gltexture.zpm3.modules.mob_effects.utils;

import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.modules.mob_effects.init.ZPMobEffects;

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
