package ru.gltexture.zpm3.engine.events;

import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZPSide;

public interface ZPEventClass {
    @NotNull ZPSide getSide();
    @NotNull Mod.EventBusSubscriber.Bus getBus();
}
