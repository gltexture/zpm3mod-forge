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
