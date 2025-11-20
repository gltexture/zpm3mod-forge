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