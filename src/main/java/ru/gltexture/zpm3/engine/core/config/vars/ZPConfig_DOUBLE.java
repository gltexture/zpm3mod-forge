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

package ru.gltexture.zpm3.engine.core.config.vars;

import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;

public class ZPConfig_DOUBLE extends ZPConfigVar<Double> {
    private final double min;
    private final double max;

    public ZPConfig_DOUBLE(Double var) {
        super(var, ZPConfigVar.DOUBLE);
        this.min = -127000.0f;
        this.max = 127000.0f;
    }

    public ZPConfig_DOUBLE(Double var, double min, double max) {
        super(var, ZPConfigVar.DOUBLE);
        this.min = min;
        this.max = max;
    }

    @Override
    public @Nullable String additionInfo() {
        return "min=" + min + ", max=" + max;
    }

    @Override
    public void setVar(Double var) {
        super.setVar(Mth.clamp(var, min, max));
    }

    public double getMin() {
        return this.min;
    }

    public double getMax() {
        return this.max;
    }
}
