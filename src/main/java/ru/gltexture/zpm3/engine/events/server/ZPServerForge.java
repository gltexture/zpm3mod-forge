package ru.gltexture.zpm3.engine.events.server;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.events.mod.ZPAbstractEventMod;

@Mod.EventBusSubscriber(modid = ZombiePlague3.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.DEDICATED_SERVER)
public final class ZPServerForge extends ZPAbstractEventMod {
    public ZPServerForge() {
        super();
    }

    @Override
    @SubscribeEvent
    public void onAnyZPEvent(Event event) {
        super.defaultExec(event);
    }
}
