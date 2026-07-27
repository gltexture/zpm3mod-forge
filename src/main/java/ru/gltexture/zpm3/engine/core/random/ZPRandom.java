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

package ru.gltexture.zpm3.engine.core.random;

import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.Random;

public final class ZPRandom {
    public static ZPRandom instance = null;
    private final Random random;

    static {
        ZPRandom.instance = new ZPRandom(0L);
    }

    public void init(long seed) {
        ZPRandom.instance = new ZPRandom(seed);
    }

    private ZPRandom(long seed) {
        this.random = new Random(seed);
    }

    public Vector3f randomVector3f(Vector3f defaultValue, Vector3f mask) {
        return new Vector3f(defaultValue).sub(new Vector3f(ZPRandom.getRandom().nextFloat(), ZPRandom.getRandom().nextFloat(), ZPRandom.getRandom().nextFloat()).mul(mask));
    }

    public Vector3f randomVector3f(float defaultValue, Vector3f mask) {
        return new Vector3f(defaultValue).sub(new Vector3f(ZPRandom.getRandom().nextFloat(), ZPRandom.getRandom().nextFloat(), ZPRandom.getRandom().nextFloat()).mul(mask));
    }

    public Vector2f randomVector2f(float defaultValue, Vector2f mask) {
        return new Vector2f(defaultValue).sub(new Vector2f(ZPRandom.getRandom().nextFloat(), ZPRandom.getRandom().nextFloat()).mul(mask));
    }

    public Vector3f randomVector3f(float bound) {
        return new Vector3f(this.randomFloat(bound), this.randomFloat(bound), this.randomFloat(bound));
    }

    public Vector2f randomVector2f(float bound) {
        return new Vector2f(this.randomFloat(bound), this.randomFloat(bound));
    }

    public float randomFloat(float range) {
        return range == 0.0f ? 0.0f : ZPRandom.getRandom().nextFloat(range);
    }

    public float randomFloatDuo(float range) {
        return range == 0.0f ? 0.0f : ZPRandom.getRandom().nextFloat(range * 2.0f) - range;
    }

    public float randomInt(int from, int to) {
        return ZPRandom.getRandom().nextInt(from, to);
    }

    public boolean randomBoolean() {
        return ZPRandom.getRandom().nextBoolean();
    }

    public static Random getRandom() {
        return ZPRandom.instance.random;
    }
}