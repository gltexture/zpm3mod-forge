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

package ru.gltexture.zpm3.modules.entity.util;

import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.modules.entity.mixins.ext.IZPLivingEntityExt;
import ru.gltexture.zpm3.modules.mob_effects.utils.ZPEffectUtils;

public enum ZPLivingStat {
    RADIATION {
        @Override
        public int get(@NotNull LivingEntity entity) {
            return ((IZPLivingEntityExt) entity).zpm3forge$getRadiationLevel();
        }

        @Override
        public void set(@NotNull LivingEntity entity, int value, boolean doNotAffectIfHasImmune) {
            if (doNotAffectIfHasImmune && ZPEffectUtils.isRadiationProtected(entity)) {
                return;
            }
            ((IZPLivingEntityExt) entity).zpm3forge$setRadiationLevel(value);
        }
    },

    INTOXICATION {
        @Override
        public int get(@NotNull LivingEntity entity) {
            return ((IZPLivingEntityExt) entity).zpm3forge$getIntoxicationLevel();
        }

        @Override
        public void set(@NotNull LivingEntity entity, int value, boolean doNotAffectIfHasImmune) {
            if (doNotAffectIfHasImmune && ZPEffectUtils.isImmune(entity)) {
                return;
            }
            ((IZPLivingEntityExt) entity).zpm3forge$setIntoxicationLevel(value);
        }
    };

    public abstract int get(@NotNull LivingEntity entity);

    public abstract void set(@NotNull LivingEntity entity, int value, boolean doNotAffectIfHasImmune);

    public final void add(@NotNull LivingEntity entity, int value, boolean doNotAffectIfHasImmune) {
        this.set(entity, this.get(entity) + value,  doNotAffectIfHasImmune);
    }

    public final void decrease(@NotNull LivingEntity entity, int value, boolean doNotAffectIfHasImmune) {
        this.set(entity, this.get(entity) - value,  doNotAffectIfHasImmune);
    }
}