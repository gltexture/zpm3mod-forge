package ru.gltexture.zpm3.engine.core;

import ru.gltexture.zpm3.engine.core.api.events.ZombiePlagueEvent;
import ru.gltexture.zpm3.engine.core.api.events.ZPEventBus;
import ru.gltexture.zpm3.engine.exceptions.ZPRuntimeException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

public class Zp_SYS_EventsManager {
    private final HashMap<Class<ZPEventBus.IEvent>, TreeSet<PriorityMethod>> eventMap;

    public Zp_SYS_EventsManager() {
        this.eventMap = new HashMap<>();
    }

    public static void pushEvent(ZPEventBus.IEvent event) {
        ZombiePlague3.ZP_EVENTS.exec(event);
    }

    private void exec(ZPEventBus.IEvent event) {
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
                method.invoke(ZPEventBus.IEvent.class, event);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new ZPRuntimeException(e);
            }
        }
    }

    @SuppressWarnings("all")
    void initEvents(Class<?> APIEventsClass, Set<Class<?>> classSet) {
        {
            Class<?>[] eventClasses = APIEventsClass.getClasses();
            for (Class<?> cl : eventClasses) {
                Class<?>[] interfaces = cl.getInterfaces();
                if (interfaces.length == 1 && ZPEventBus.IEvent.class.isAssignableFrom(interfaces[0])) {
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
                    this.eventMap.put((Class<ZPEventBus.IEvent>) cl, new TreeSet<PriorityMethod>(Comparator.comparingInt(PriorityMethod::priority).thenComparingInt(System::identityHashCode)));
                    ZombiePlague3.LOGGER.debug("Created API ClassEvent: " + cl.getName());
                }
            }
        }
        {
            for (Class<?> clazz : classSet) {
                Method[] methods = clazz.getDeclaredMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(ZombiePlagueEvent.class)) {
                        Class<?>[] parameters = method.getParameterTypes();
                        if (parameters.length != 1) {
                            ZombiePlague3.LOGGER.error("Method has more(or less) than 1 argument(? -> IEvent): " + method.getName() + " - Skip");
                            continue;
                        }
                        if (parameters.length != 1 || !ZPEventBus.IEvent.class.isAssignableFrom(parameters[0])) {
                            ZombiePlague3.LOGGER.error("Method has wrong argument(? -> IEvent): " + method.getName());
                            continue;
                        }
                        TreeSet<PriorityMethod> priorityMethods = this.eventMap.get(parameters[0]);
                        if (priorityMethods == null) {
                            ZombiePlague3.LOGGER.error("Couldn't find event in class: " + clazz.getName());
                            continue;
                        }
                        ZombiePlagueEvent subscribeEvent = method.getAnnotation(ZombiePlagueEvent.class);
                        priorityMethods.add(new PriorityMethod(method, subscribeEvent.priority()));
                    }
                }
            }
        }
    }

    private record PriorityMethod(Method method, int priority) {
    }
}
