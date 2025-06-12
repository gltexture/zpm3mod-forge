package ru.gltexture.zpm3.engine.registry.base;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZPRegistryConveyor;

import java.util.function.Supplier;

public abstract class ZPRegistry<T> {
    private final DeferredRegister<T> deferredRegister;

    public ZPRegistry() {
        this.deferredRegister = this.createDeferredRegister();
    }

    protected abstract void runRegister(@NotNull ZPRegistry.ZPRegSupplier<T> regSupplier);

    public void runRegister() {
        this.runRegister(this.getSupplier());
    }

    protected abstract @NotNull DeferredRegister<T> createDeferredRegister();
    public abstract int priority();
    public abstract @NotNull ZPRegistryConveyor.Target getTarget();
    public abstract @NotNull String getID();

    private ZPRegSupplier<T> getSupplier() {
        return this.deferredRegister::register;
    }

    @FunctionalInterface
    public interface ZPRegSupplier<R> {
        RegistryObject<R> register(String name, Supplier<R> supplier);
    }

    @Override
    public String toString() {
        return this.getID();
    }
}
