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

package ru.gltexture.zpm3.engine.core;

import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.api.events.ZPEventDef;
import ru.gltexture.zpm3.engine.core.api.events.ZombiePlagueEvent;
import ru.gltexture.zpm3.engine.exceptions.ZPAPIException;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

public class ZP_EventsManager {
    private final HashMap<Class<ZPEventDef.IEvent>, TreeSet<PriorityMethod>> eventMap;

    public ZP_EventsManager() {
        this.eventMap = new HashMap<>();
    }

    public static void pushEvent(@NotNull ZPEventDef.IEvent event) {
        ZombiePlague3.ZP_EVENTS.exec(event);
    }

    private void exec(ZPEventDef.IEvent event) {
        if (!this.eventMap.containsKey(event.getClass())) {
            return;
        }
        for (PriorityMethod priorityMethod : this.eventMap.get(event.getClass())) {
            ZPEventInvoker method = priorityMethod.method();
            method.invoke(event);
        }
    }

    @SuppressWarnings("all")
    void initEventBus(Class<?>... APIEventsClasses) {
        {
            final Class<?>[] eventClasses = Arrays.stream(APIEventsClasses).flatMap(clazz -> Arrays.stream(clazz.getClasses())).toArray(Class<?>[]::new);
            for (Class<?> cl : eventClasses) {
                final Class<?>[] interfaces = cl.getInterfaces();
                if (interfaces.length == 1 && ZPEventDef.IEvent.class.isAssignableFrom(interfaces[0])) {
                    if (!Modifier.isFinal(cl.getModifiers())) {
                        ZombiePlague3.LOGGER.error(cl.getName() + " should be final class");
                        continue;
                    }
                    if (!Modifier.isPublic(cl.getModifiers())) {
                        ZombiePlague3.LOGGER.error(cl.getName() + " should be public class");
                        continue;
                    }
                    if (!Modifier.isStatic(cl.getModifiers())) {
                        ZombiePlague3.LOGGER.error(cl.getName() + " should be static class");
                        continue;
                    }
                    this.eventMap.put((Class<ZPEventDef.IEvent>) cl, new TreeSet<PriorityMethod>(Comparator.comparingInt(PriorityMethod::priority).thenComparingInt(System::identityHashCode)));
                    ZombiePlague3.LOGGER.debug("Created API ClassEvent: " + cl.getName());
                }
            }
        }
    }

    @SuppressWarnings("all")
    void registerEvents(Class<?>... classes) throws ZPAPIException {
        this.registerEvents(Arrays.stream(classes).collect(Collectors.toSet()));
    }

    @SuppressWarnings("all")
    void registerEvents(Set<Class<?>> classSet) throws ZPAPIException {
        {
            try {
                for (Class<?> clazz : classSet) {
                    final Method[] methods = clazz.getDeclaredMethods();
                    for (Method method : methods) {
                        if (method.isAnnotationPresent(ZombiePlagueEvent.class)) {
                            final Class<?>[] parameters = method.getParameterTypes();
                            if (parameters.length != 1) {
                                ZombiePlague3.LOGGER.error("Method has more(or less) than 1 argument(? -> IEvent): " + method.getName() + " - Skip");
                                continue;
                            }
                            if (parameters.length != 1 || !ZPEventDef.IEvent.class.isAssignableFrom(parameters[0])) {
                                ZombiePlague3.LOGGER.error("Method has wrong argument(? -> IEvent): " + method.getName());
                                continue;
                            }
                            final TreeSet<PriorityMethod> priorityMethods = this.eventMap.get(parameters[0]);
                            if (priorityMethods == null) {
                                ZombiePlague3.LOGGER.error("Couldn't find event in class: " + clazz.getName());
                                continue;
                            }
                            final ZombiePlagueEvent subscribeEvent = method.getAnnotation(ZombiePlagueEvent.class);
                            final MethodHandle methodHandle = MethodHandles.lookup().unreflect(method);
                            final ZPEventInvoker invoker = event -> {
                                try {
                                    methodHandle.invoke(event);
                                } catch (Throwable e) {
                                    throw new RuntimeException(e);
                                }
                            };
                            priorityMethods.add(new PriorityMethod(invoker, subscribeEvent.priority()));
                            ZPLogger.info("Registered ZP3-Event Method " + method.getName());
                        }
                    }
                }
            } catch (IllegalAccessException | RuntimeException e) {
                throw new ZPAPIException(e);
            }
        }
    }

    private record PriorityMethod(ZPEventInvoker method, int priority) { ; }

    @FunctionalInterface
    public interface ZPEventInvoker {
        void invoke(ZPEventDef.IEvent event);
    }
}
