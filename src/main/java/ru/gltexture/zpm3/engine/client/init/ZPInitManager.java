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

package ru.gltexture.zpm3.engine.client.init;

import com.mojang.blaze3d.platform.Window;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.exceptions.ZPRuntimeException;

import java.util.LinkedHashSet;
import java.util.Set;


abstract class ZPInitManager {
    private LinkedHashSet<InitRun> setInit;
    private LinkedHashSet<InitRun> setDestroy;

    public ZPInitManager() {
        this.setInit = new LinkedHashSet<>();
        this.setDestroy = new LinkedHashSet<>();
    }

    public void setupRunner(InitRun runnable) {
        if (this.setInit == null) {
            throw new ZPRuntimeException("Tried to init function too late");
        }
        this.setInit.add(runnable);
    }

    public void destroyRunner(InitRun runnable) {
        if (this.setDestroy == null) {
            throw new ZPRuntimeException("Tried to destroy function too late");
        }
        this.setDestroy.add(runnable);
    }

    public void runSetup(@NotNull Window window) {
        if (this.setInit == null) {
            return;
        }
        this.setInit.forEach(e -> e.run(window));
        this.clearInit();
    }

    public void runDestroy(@NotNull Window window) {
        if (this.setDestroy == null) {
            return;
        }
        this.setDestroy.forEach(e -> e.run(window));
        this.clearDestroy();
    }

    public void clearInit() {
        this.setInit = null;
    }

    public void clearDestroy() {
        this.setDestroy = null;
    }

    @FunctionalInterface
    public interface InitRun {
        void run(@NotNull Window window);
    }
}
