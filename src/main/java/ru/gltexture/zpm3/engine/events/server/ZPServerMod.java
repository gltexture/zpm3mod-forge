package ru.gltexture.zpm3.engine.events.server;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.events.ZPAbstractEventMod;
import ru.gltexture.zpm3.engine.events.ZPEventMod;
import ru.gltexture.zpm3.engine.events.ZPEvent;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = ZombiePlague3.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.DEDICATED_SERVER)
public final class ZPServerMod extends ZPAbstractEventMod {
    public ZPServerMod() {
        super();
    }

    @Override
    @SubscribeEvent
    public void onAnyZPEvent(Event event) {
        super.defaultExec(event);
    }
}
