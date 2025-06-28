package ru.gltexture.zpm3.engine.service;

import org.jetbrains.annotations.NotNull;

public record Pair<K, V>(K first, V second) {
    @SuppressWarnings("all")
    @SafeVarargs
    public static <K, V> Pair<K, V>[] get(Pair<K, V>... pairs) {
        Pair[] kvPair = new Pair[pairs.length];
        System.arraycopy(pairs, 0, kvPair, 0, kvPair.length);
        return kvPair;
    }

    public static <K, V> Pair <K, V> of(K k, V v) {
        return new Pair<>(k, v);
    }

    @Override
    public @NotNull String toString() {
        return this.first + " + " + this.second;
    }
}