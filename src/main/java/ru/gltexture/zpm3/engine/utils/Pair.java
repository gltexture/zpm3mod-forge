package ru.gltexture.zpm3.engine.utils;

public final class Pair<K, V> {
    private final K first;
    private final V second;

    public Pair(K first, V second) {
        this.first = first;
        this.second = second;
    }

    @SuppressWarnings("all")
    @SafeVarargs
    public static <K, V> Pair<K, V> [] get(Pair<K, V>... pairs) {
        Pair[] kvPair =  new Pair[pairs.length];
        System.arraycopy(pairs, 0, kvPair, 0, kvPair.length);
        return kvPair;
    }

    public K getFirst() {
        return this.first;
    }

    public V getSecond() {
        return this.second;
    }

    @Override
    public String toString() {
        return first + " + " + second;
    }
}