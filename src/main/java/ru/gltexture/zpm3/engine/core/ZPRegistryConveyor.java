package ru.gltexture.zpm3.engine.core;

import ru.gltexture.zpm3.engine.exceptions.ZPRuntimeException;
import ru.gltexture.zpm3.engine.registry.base.ZPRegistry;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public final class ZPRegistryConveyor {

    public ZPRegistryConveyor() {
    }

    void launch(Set<Class<? extends ZPRegistry<?>>> processorClasses) {
        List<ZPRegistry<?>> processors = new ArrayList<>();
        try {
            for (Class<? extends ZPRegistry<?>> zpRegistryProcessorClass : processorClasses) {
                processors.add(zpRegistryProcessorClass.getConstructor().newInstance());
            }
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            throw new ZPRuntimeException(e);
        }
        processors.sort(Comparator.comparing((ZPRegistry<?> p) -> p.getTarget().getOrder()).thenComparing(ZPRegistry::priority));

        for (ZPRegistry<?> zpRegistry : processors) {
            ZPLogger.info("Initializing ZP processor: " + zpRegistry);
            zpRegistry.runRegister();
        }
    }

    public enum Target {
        TAB(0),
        ITEM(1),
        BLOCK(2);

        private final int order;

        Target(int order) {
            this.order = order;
        }

        public int getOrder() {
            return this.order;
        }
    }
}
