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

public class ZPConfig_INT extends ZPConfigVar<Integer> {
    private final int min;
    private final int max;

    public ZPConfig_INT(Integer var) {
        super(var, ZPConfigVar.INT);
        this.min = -127000;
        this.max = 127000;
    }

    public ZPConfig_INT(Integer var, int min, int max) {
        super(var, ZPConfigVar.INT);
        this.min = min;
        this.max = max;
    }

    @Override
    public @Nullable String additionInfo() {
        return "min=" + min + ", max=" + max;
    }

    @Override
    public void setVar(Integer var) {
        super.setVar(Mth.clamp(var, min, max));
    }

    public int getMin() {
        return this.min;
    }

    public int getMax() {
        return this.max;
    }
}
