package ru.gltexture.zpm3.assets.player.events.client;

import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.events.ZPSimpleEventClass;

public class ZPClientJoinEvent implements ZPSimpleEventClass<ClientPlayerNetworkEvent.LoggingIn> {
    @Override
    public void exec(ClientPlayerNetworkEvent.@NotNull LoggingIn event) {
    }

    @Override
    public @NotNull Class<ClientPlayerNetworkEvent.LoggingIn> getEventType() {
        return ClientPlayerNetworkEvent.LoggingIn.class;
    }

    @Override
    public @NotNull ZPSide getSide() {
        return ZPSide.CLIENT;
    }

    @Override
    public Mod.EventBusSubscriber.@NotNull Bus getBus() {
        return Mod.EventBusSubscriber.Bus.MOD;
    }
}
