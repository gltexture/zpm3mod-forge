package ru.gltexture.zpm3.engine.registry.base;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZPRegistryConveyor;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;

import java.util.function.Supplier;

public abstract class ZPRegistry<T> {
    private final DeferredRegister<T> deferredRegister;
    private final ZPRegistryConveyor.Target target;

    public ZPRegistry(@NotNull IForgeRegistry<T> registry, @NotNull ZPRegistryConveyor.Target target) {
        this.deferredRegister = this.createDeferredRegister(registry);
        this.target = target;
    }

    protected abstract void runRegister(@NotNull ZPRegistry.ZPRegSupplier<T> regSupplier);

    public void runRegister() {
        this.runRegister(this.getSupplier());
    }

    protected @NotNull DeferredRegister<T> createDeferredRegister(IForgeRegistry<T> registry) {
        return DeferredRegister.create(registry, ZombiePlague3.MOD_ID());
    }

    public abstract int priority();
    public abstract @NotNull String getID();

    public ZPRegistryConveyor.Target getTarget() {
        return this.target;
    }

    public DeferredRegister<T> getDeferredRegister() {
        return this.deferredRegister;
    }

    protected void preRegister() {
    }

    protected void postRegister(RegistryObject<T> object) {
    }

    private ZPRegSupplier<T> getSupplier() {
        return ((name, supplier) -> {
            this.preRegister();
            RegistryObject<T> object = this.getDeferredRegister().register(name, supplier);
            this.postRegister(object);
            return object;
        });
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
