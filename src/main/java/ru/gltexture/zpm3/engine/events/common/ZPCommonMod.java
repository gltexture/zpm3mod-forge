package ru.gltexture.zpm3.engine.events.common;

import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;

@Mod.EventBusSubscriber(modid = ZombiePlague3.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ZPCommonMod {
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void populationControl(SpawnPlacementRegisterEvent event) {
        ZombiePlague3.getPopulationController().eventPopulation(event);
    }
}
