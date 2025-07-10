package ru.gltexture.zpm3.engine.events;

import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZPSide;

public interface ZPEvent<T extends Event> {
    void exec(@NotNull T t);
    @NotNull Class<T> getEventType();
    @NotNull ZPSide getSide();
    @NotNull Mod.EventBusSubscriber.Bus getBus();
}
