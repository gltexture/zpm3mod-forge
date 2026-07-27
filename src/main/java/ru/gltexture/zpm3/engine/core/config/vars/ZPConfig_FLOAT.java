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

public class ZPConfig_FLOAT extends ZPConfigVar<Float> {
    private final float min;
    private final float max;

    public ZPConfig_FLOAT(Float var) {
        super(var, ZPConfigVar.FLOAT);
        this.min = -127000.0f;
        this.max = 127000.0f;
    }

    public ZPConfig_FLOAT(Float var, float min, float max) {
        super(var, ZPConfigVar.FLOAT);
        this.min = min;
        this.max = max;
    }

    @Override
    public @Nullable String additionInfo() {
        return "min=" + min + ", max=" + max;
    }

    @Override
    public void setVar(Float var) {
        super.setVar(Mth.clamp(var, min, max));
    }

    public float getMin() {
        return this.min;
    }

    public float getMax() {
        return this.max;
    }
}
