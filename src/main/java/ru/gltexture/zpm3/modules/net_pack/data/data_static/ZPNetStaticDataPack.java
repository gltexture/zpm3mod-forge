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

package ru.gltexture.zpm3.modules.net_pack.data.data_static;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import ru.gltexture.zpm3.engine.core.ZPLogger;
import ru.gltexture.zpm3.modules.net_pack.data.accessors.ZPNetDataAccessor;
import ru.gltexture.zpm3.modules.net_pack.data.data_ent.ZPNetDataVar;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class ZPNetStaticDataPack implements IZPNetStaticDataPack {
    private final Map<ZPNetDataAccessor<?>, ZPNetDataVar<?>> vars;
    private final OnSetConsumer<?> onSetConsumer;

    public ZPNetStaticDataPack(@Nullable OnSetConsumer<?> onSetConsumer) {
        this.vars = new HashMap<>();
        this.onSetConsumer = onSetConsumer;
    }

    public static ZPNetStaticDataPack of(@NotNull Map<ZPNetDataAccessor<?>, ZPNetDataVar<?>> vars, @Nullable OnSetConsumer<?> onSetConsumer) {
        ZPNetStaticDataPack pack = new ZPNetStaticDataPack(onSetConsumer);
        pack.vars.putAll(vars);
        return pack;
    }

    void init(@NotNull ZPNetDataAccessor<?> accessor, @NotNull ZPNetDataVar<?> var) {
        this.vars.put(accessor, var);
    }

    @SuppressWarnings("all")
    public <E> Optional<ZPNetDataVar<E>> getVar(@NotNull ZPNetDataAccessor<E> accessor) {
        return Optional.ofNullable((ZPNetDataVar<E>) this.vars.get(accessor));
    }

    @SuppressWarnings("all")
    public <E> void setValueUnsafe(@NotNull ZPNetDataAccessor<?> accessor, @NotNull ZPNetDataVar<?> var) {
        try {
            this.setValue((ZPNetDataAccessor<E>) accessor, (ZPNetDataVar<E>) var, false);
        } catch (final Exception e) {
            ZPLogger.exception(e);
        }
    }

    public <E> void setValue(@NotNull ZPNetDataAccessor<E> accessor, @NotNull ZPNetDataVar<E> var, boolean broadcast) {
        if (!this.vars.containsKey(accessor)) {
            return;
        }
        if (this.vars.get(accessor).equals(var)) {
            return;
        }
        this.vars.put(accessor, var);
        if (this.onSetConsumer != null && broadcast) {
            this.<E>getOnSetConsumer().accept(accessor, var);
        }
    }

    public @Unmodifiable Map<ZPNetDataAccessor<?>, ZPNetDataVar<?>> getVars() {
        return Collections.unmodifiableMap(this.vars);
    }

    @SuppressWarnings("all")
    public <E> OnSetConsumer<E> getOnSetConsumer() {
        return (OnSetConsumer<E>) this.onSetConsumer;
    }

    @FunctionalInterface
    public interface OnSetConsumer <E> {
        void accept(@NotNull ZPNetDataAccessor<E> accessor, @NotNull ZPNetDataVar<E> var);
    }
}