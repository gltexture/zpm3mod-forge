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

package ru.gltexture.zpm3.modules.common.damage;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.modules.common.init.ZPDamageTypes;

public class ZPDamageSources {
    public static DamageSource bullet(@NotNull ServerLevel level, @NotNull Entity shooter) {
        return new DamageSource(ZPDamageTypes.getDamageType(level, ZPDamageTypes.zp_bullet), shooter, shooter);
    }

    public static DamageSource bleed(@NotNull ServerLevel level) {
        return new DamageSource(ZPDamageTypes.getDamageType(level, ZPDamageTypes.zp_bleeding));
    }
}
