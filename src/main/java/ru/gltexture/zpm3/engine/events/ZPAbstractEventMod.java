package ru.gltexture.zpm3.engine.events;

import net.minecraftforge.eventbus.api.Event;
import ru.gltexture.zpm3.engine.core.ZPLogger;

import java.util.HashMap;
import java.util.Map;

public abstract class ZPAbstractEventMod implements ZPEventMod {
    private final Map<Class<? extends Event>, ZPEvent<? extends Event>> zpEventMap;

    public ZPAbstractEventMod() {
        this.zpEventMap = new HashMap<>();
    }

    protected void defaultExec(Event event) {
        ZPEvent<? extends Event> eventToExec = this.getHandlersMap().get(event.getClass());
        if (eventToExec == null) {
            return;
        }
        this.dispatch(eventToExec, event);
    }

    @SuppressWarnings("unchecked")
    protected <T extends Event> void dispatch(ZPEvent<T> handler, Event event) {
        handler.exec((T) event);
    }

    public void addNew(Class<? extends Event> clazz, ZPEvent<? extends Event> zpEvent) {
        ZPLogger.info("Added event-handler: " + zpEvent.getClass().getSimpleName() + " in " + this.getClass().getSimpleName());
        this.zpEventMap.put(clazz, zpEvent);
    }

    @Override
    public Map<Class<? extends Event>, ZPEvent<? extends Event>> getHandlersMap() {
        return this.zpEventMap;
    }
}
