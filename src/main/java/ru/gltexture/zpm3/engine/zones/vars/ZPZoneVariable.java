package ru.gltexture.zpm3.engine.zones.vars;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

public abstract sealed class ZPZoneVariable<T extends Serializable> permits ZPZoneIntVar {
    private final String variableId;
    private final T t;

    public ZPZoneVariable(@NotNull String variableId, @NotNull T t) {
        this.variableId = variableId;
        this.t = t;
    }

    public abstract @Nullable String additionalChatMsh();

    public abstract String toString();

    public String getVariableId() {
        return this.variableId;
    }

    public T getValue() {
        return this.t;
    }
}
