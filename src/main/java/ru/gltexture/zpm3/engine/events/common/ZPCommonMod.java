package ru.gltexture.zpm3.engine.events.common;

import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;

public class ZPCommonMod {
    @SubscribeEvent(priority = EventPriority.LOW)
    public void populationControl(SpawnPlacementRegisterEvent event) {
        ZombiePlague3.getPopulationController().eventPopulation(event);
    }

}
