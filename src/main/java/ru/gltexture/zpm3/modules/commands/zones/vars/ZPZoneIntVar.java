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

package ru.gltexture.zpm3.modules.commands.zones.vars;

import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public non-sealed class ZPZoneIntVar extends ZPZoneVariable<Integer> {
    private final Integer min;
    private final Integer max;

    public ZPZoneIntVar(@NotNull String variableId, @NotNull Integer t, @NotNull Integer min, @NotNull Integer max) {
        super(variableId, t);
        this.min = min;
        this.max = max;
    }

    @Override
    public @Nullable String additionalChatMsh() {
        return "Value was clamped between " + this.min + " and " + this.max;
    }

    @Override
    public String toString() {
        return this.getVariableId() + "=" + this.getValue();
    }

    @Override
    public Integer getValue() {
        return Mth.clamp(super.getValue(), this.min, this.max);
    }

    public Integer getMin() {
        return this.min;
    }

    public Integer getMax() {
        return this.max;
    }
}