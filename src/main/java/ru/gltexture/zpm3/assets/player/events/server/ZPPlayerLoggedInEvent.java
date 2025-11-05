package ru.gltexture.zpm3.assets.player.events.server;

import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.events.ZPSimpleEventClass;

public class ZPPlayerLoggedInEvent implements ZPSimpleEventClass<PlayerEvent.PlayerLoggedInEvent> {
    @Override
    public void exec(PlayerEvent.@NotNull PlayerLoggedInEvent event) {
    }

    @Override
    public @NotNull Class<PlayerEvent.PlayerLoggedInEvent> getEventType() {
        return PlayerEvent.PlayerLoggedInEvent.class;
    }

    @Override
    public @NotNull ZPSide getSide() {
        return ZPSide.DEDICATED_SERVER;
    }

    @Override
    public Mod.EventBusSubscriber.@NotNull Bus getBus() {
        return Mod.EventBusSubscriber.Bus.MOD;
    }
}
