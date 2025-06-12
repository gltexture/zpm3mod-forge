package ru.gltexture.zpm3.engine.events;


import net.minecraftforge.eventbus.api.Event;
import java.util.*;

public interface ZPEventMod {
    void onAnyZPEvent(Event event);
    Map<Class<? extends Event>, ZPEvent<? extends Event>> getHandlersMap();
}
