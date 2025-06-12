package ru.gltexture.zpm3.engine.events;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.event.IModBusEvent;
import org.jetbrains.annotations.NotNull;

public interface ZPEvent<T extends Event> {
    void exec(@NotNull T t);
    @NotNull Class<T> getEventType();
    @NotNull Dist getDist();
}
