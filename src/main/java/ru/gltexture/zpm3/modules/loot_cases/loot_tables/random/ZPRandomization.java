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

package ru.gltexture.zpm3.modules.loot_cases.loot_tables.random;

import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;
import ru.gltexture.zpm3.modules.loot_cases.loot_tables.ZPLootTable;

public record ZPRandomization(@NotNull Type type, float parameter) {
    public enum Type {
        UNIFORM,
        POWER,
        EXPONENTIAL
    }

    public static ZPRandomization uniform() {
        return new ZPRandomization(Type.UNIFORM, 1.0f);
    }

    public static ZPRandomization power(float exponent) {
        return new ZPRandomization(Type.POWER, exponent);
    }

    public static ZPRandomization exponential(float exponent) {
        return new ZPRandomization(Type.EXPONENTIAL, exponent);
    }

    public float random() {
        final float r = ZPRandom.getRandom().nextFloat();
        return switch (this.type) {
            case UNIFORM -> r;
            case POWER -> (float) Math.pow(r, this.parameter);
            case EXPONENTIAL -> 1.0f - (float) Math.pow(1.0f - r, this.parameter);
        };
    }

    public float random(float min, float max) {
        return min + (max - min) * this.random();
    }

    public int random(int min, int max) {
        return (int) Math.floor(min + (max - min + 1) * this.random());
    }

    public long random(long min, long max) {
        return (long) Math.floor(min + (max - min + 1L) * this.random());
    }

    public double random(double min, double max) {
        final double r = this.random();
        return min + (max - min) * r;
    }
}
