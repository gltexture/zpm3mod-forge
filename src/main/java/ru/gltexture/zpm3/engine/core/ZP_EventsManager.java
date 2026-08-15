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
import ru.gltexture.zpm3.engine.exceptions.ZPRuntimeException;

import java.lang.reflect.InvocationTargetException;
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
            Method method = priorityMethod.method();
            if (method == null) {
                ZombiePlague3.LOGGER.warn("Couldn't find event " + event.getClass().getName() + " in API Container");
                return;
            }
            try {
                method.invoke(ZPEventDef.IEvent.class, event);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new ZPRuntimeException(e);
            }
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
    void initEvents(Class<?>... classes) {
        this.initEvents(Arrays.stream(classes).collect(Collectors.toSet()));
    }

    @SuppressWarnings("all")
    void initEvents(Set<Class<?>> classSet) {
        {
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
                        priorityMethods.add(new PriorityMethod(method, subscribeEvent.priority()));
                        ZPLogger.info("Registered ZP3-Event Method " + method.getName());
                    }
                }
            }
        }
    }

    private record PriorityMethod(Method method, int priority) { ; }
}
