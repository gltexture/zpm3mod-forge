package ru.gltexture.zpm3.engine.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.engine.core.ZPRegistryConveyor;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.utils.ZPUtility;

import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class ZPRegistry<T> {
    private final DeferredRegister<T> deferredRegister;
    private final ZPRegistryConveyor.Target target;

    public ZPRegistry(@NotNull IForgeRegistry<T> registry, @NotNull ZPRegistryConveyor.Target target) {
        this.deferredRegister = this.createDeferredRegister(registry);
        this.target = target;
    }

    public ZPRegistry(@NotNull ResourceKey<? extends Registry<T>> registry, @NotNull ZPRegistryConveyor.Target target) {
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

    protected @NotNull DeferredRegister<T> createDeferredRegister(ResourceKey<? extends Registry<T>> registry) {
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

    protected void preRegister(String name) {
    }

    protected void postRegister(String name, RegistryObject<T> object) {
    }

    public void preProcessing() {
    }

    public void postProcessing() {
    }

    @SuppressWarnings("unchecked")
    private <I extends T> ZPRegSupplier<I> getSupplier() {
        return new ZPRegSupplier<I>() {
            @Override
            public <E extends I> ZPRegistryObject<E> register(@NotNull String name, @NotNull Supplier<E> supplier) {
                ZPRegistry.this.preRegister(name);
                RegistryObject<E> object = ZPRegistry.this.getDeferredRegister().register(name, supplier);
                ZPRegistry.this.postRegister(name, (RegistryObject<T>) object);
                return new ZPRegistryObject<>(object);
            }
        };
    }

    @FunctionalInterface
    public interface ZPRegSupplier<R> {
        <E extends R> ZPRegistryObject<E> register(@NotNull String name, @NotNull Supplier<E> supplier);
    }

    @Override
    public String toString() {
        return this.getID();
    }

    public record ZPRegistryObject<S>(RegistryObject<S> registryObject) {
        public ZPRegistryObject<S> postConsume(@Nullable Dist side, @NotNull Consumer<@NotNull RegistryObject<S>> registryObjectConsumer) {
            if (side == null) {
                registryObjectConsumer.accept(this.registryObject());
                return this;
            }
            switch (side) {
                case CLIENT -> ZPUtility.sides().onlyClient(() -> registryObjectConsumer.accept(this.registryObject()));
                case DEDICATED_SERVER -> ZPUtility.sides().onlyServer(() -> registryObjectConsumer.accept(this.registryObject()));
            }
            return this;
        }
    }
}
