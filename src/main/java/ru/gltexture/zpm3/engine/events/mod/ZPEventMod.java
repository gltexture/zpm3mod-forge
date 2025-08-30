package ru.gltexture.zpm3.engine.events.mod;


import net.minecraftforge.eventbus.api.Event;
import ru.gltexture.zpm3.engine.events.ZPSimpleEventClass;

import java.util.*;

public interface ZPEventMod {
    void onAnyZPEvent(Event event);
    Map<Class<? extends Event>, ZPSimpleEventClass<? extends Event>> getHandlersMap();
}
