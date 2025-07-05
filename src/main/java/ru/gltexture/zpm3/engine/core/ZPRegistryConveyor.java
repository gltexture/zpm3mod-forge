package ru.gltexture.zpm3.engine.core;

import ru.gltexture.zpm3.engine.exceptions.ZPRuntimeException;
import ru.gltexture.zpm3.engine.registry.ZPRegistry;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public final class ZPRegistryConveyor {

    public ZPRegistryConveyor() {
    }

    void launch(Set<Class<? extends ZPRegistry<?>>> registryClasses) {
        List<ZPRegistry<?>> registries = new ArrayList<>();
        try {
            for (Class<? extends ZPRegistry<?>> zpRegistryProcessorClass : registryClasses) {
                registries.add(zpRegistryProcessorClass.getConstructor().newInstance());
            }
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            throw new ZPRuntimeException(e);
        }
        registries.sort(Comparator.comparing((ZPRegistry<?> p) -> p.getTarget().getOrder()).thenComparing(ZPRegistry::priority));

        for (ZPRegistry<?> zpRegistry : registries) {
            ZPLogger.info("Initializing ZP registry: " + zpRegistry);
            ZombiePlague3.registerDeferred(zpRegistry.getDeferredRegister());
            zpRegistry.preProcessing();
            zpRegistry.runRegister();
            zpRegistry.postProcessing();
        }
    }

    public enum Target {
        FX(-1),
        TAB(0),
        BLOCK(1),
        ITEM(2),
        ENTITY(3),
        BLOCK_ENTITY(4);

        private final int order;

        Target(int order) {
            this.order = order;
        }

        public int getOrder() {
            return this.order;
        }
    }
}
