package ru.gltexture.zpm3.engine.events.mod;

import net.minecraftforge.eventbus.api.Event;
import ru.gltexture.zpm3.engine.core.ZPLogger;
import ru.gltexture.zpm3.engine.events.ZPSimpleEventClass;

import java.util.HashMap;
import java.util.Map;

public abstract class ZPAbstractEventMod implements ZPEventMod {
    private final Map<Class<? extends Event>, ZPSimpleEventClass<? extends Event>> zpEventMap;

    public ZPAbstractEventMod() {
        this.zpEventMap = new HashMap<>();
    }

    protected void defaultExec(Event event) {
        ZPSimpleEventClass<? extends Event> eventToExec = this.getHandlersMap().get(event.getClass());
        if (eventToExec == null) {
            return;
        }
        this.dispatch(eventToExec, event);
    }

    @SuppressWarnings("unchecked")
    protected <T extends Event> void dispatch(ZPSimpleEventClass<T> handler, Event event) {
        handler.exec((T) event);
    }

    public void addNew(Class<? extends Event> clazz, ZPSimpleEventClass<? extends Event> zpSimpleEventClass) {
        ZPLogger.info("Added event-handler: " + zpSimpleEventClass.getClass().getSimpleName() + " in " + this.getClass().getSimpleName());
        this.zpEventMap.put(clazz, zpSimpleEventClass);
    }

    @Override
    public Map<Class<? extends Event>, ZPSimpleEventClass<? extends Event>> getHandlersMap() {
        return this.zpEventMap;
    }
}
