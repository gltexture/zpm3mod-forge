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

package ru.gltexture.zpm3.modules.player.util;

import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.modules.player.mixins.ext.IZPPlayerMixinExt;

public enum ZPPlayerStat {
    SEASICKNESS {
        @Override
        public int get(@NotNull Player player) {
            return ((IZPPlayerMixinExt) player).zpm3forge$getSeasicknessLevel();
        }

        @Override
        public void set(@NotNull Player player, int value) {
            ((IZPPlayerMixinExt) player).zpm3forge$setSeasicknessLevel(value);
        }
    };

    public abstract int get(@NotNull Player player);

    public abstract void set(@NotNull Player player, int value);

    public final void add(@NotNull Player player, int value) {
        this.set(player, this.get(player) + value);
    }

    public final void decrease(@NotNull Player player, int value) {
        this.set(player, this.get(player) - value);
    }
}