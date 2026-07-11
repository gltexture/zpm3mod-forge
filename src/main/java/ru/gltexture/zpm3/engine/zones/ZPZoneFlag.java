package ru.gltexture.zpm3.engine.zones;

import java.util.*;

public final class ZPZoneFlag {
    private final String id;

    ZPZoneFlag(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object userObject) {
        if (!(userObject instanceof ZPZoneFlag that)) {
            return false;
        }
        return Objects.equals(id, that.id);
    }

    @Override
    public String toString() {
        return this.id();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public String id() {
        return this.id;
    }
}
