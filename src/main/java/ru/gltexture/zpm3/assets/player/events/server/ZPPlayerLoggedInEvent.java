package ru.gltexture.zpm3.assets.player.events.server;

import net.minecraftforge.event.entity.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.events.ZPEvent;

public class ZPPlayerLoggedInEvent implements ZPEvent<PlayerEvent.PlayerLoggedInEvent> {
    @Override
    public void exec(PlayerEvent.@NotNull PlayerLoggedInEvent event) {
    }

    @Override
    public @NotNull Class<PlayerEvent.PlayerLoggedInEvent> getEventType() {
        return PlayerEvent.PlayerLoggedInEvent.class;
    }

    @Override
    public @NotNull Side getSide() {
        return Side.SERVER;
    }
}
