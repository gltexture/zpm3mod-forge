package ru.gltexture.zpm3.engine.core.config.vars;

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

public abstract class ZPConfigVar <T extends Serializable> {
    public static final String STRING = "string";
    public static final String INT = "int";
    public static final String BOOL = "bool";
    public static final String FLOAT = "float";
    public static final String DOUBLE = "double";

    private T var;
    private final String type;

    ZPConfigVar(T var, String type) {
        this.var = var;
        this.type = type;
    }

    public @Nullable String additionInfo() {
        return null;
    }

    public void setVar(T var) {
        this.var = var;
    }

    @SuppressWarnings("unchecked")
    public void setVarUnsafe(Object var) {
        this.var = (T) var;
    }

    @Override
    public String toString() {
        return String.valueOf(this.var);
    }

    public String getType() {
        return this.type;
    }

    public T getVar() {
        return this.var;
    }
}
