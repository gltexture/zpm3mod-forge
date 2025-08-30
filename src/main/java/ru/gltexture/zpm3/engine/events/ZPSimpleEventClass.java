package ru.gltexture.zpm3.engine.events;

import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

public interface ZPSimpleEventClass<T extends Event> extends ZPEventClass {
    void exec(@NotNull T t);
    @NotNull Class<T> getEventType();
    @NotNull Mod.EventBusSubscriber.Bus getBus();
}
