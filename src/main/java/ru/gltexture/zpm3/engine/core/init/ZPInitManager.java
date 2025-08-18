package ru.gltexture.zpm3.engine.core.init;

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
        this.setInit.forEach(e -> e.run(window));
        this.clearInit();
    }

    public void runDestroy(@NotNull Window window) {
        this.setDestroy.forEach(e -> e.run(window));
        this.clearInit();
    }

    public void clearInit() {
        this.setInit = null;
    }

    public void clearDestroy() {
        this.setDestroy = null;
    }

    public Set<InitRun> getSetInit() {
        return this.setInit;
    }

    public Set<InitRun> getSetDestroy() {
        return this.setDestroy;
    }

    @FunctionalInterface
    public interface InitRun {
        void run(@NotNull Window window);
    }
}
