package ru.gltexture.zpm3.engine.events.client;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.events.ZPAbstractEventMod;
import ru.gltexture.zpm3.engine.helpers.ZPEntityRenderMatching;
import ru.gltexture.zpm3.engine.helpers.ZPItemTabAddHelper;
import ru.gltexture.zpm3.engine.helpers.models.ZPBlockModelProvider;
import ru.gltexture.zpm3.engine.helpers.models.ZPItemModelProvider;

@Mod.EventBusSubscriber(modid = ZombiePlague3.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ZPClientMod extends ZPAbstractEventMod {
    public ZPClientMod() {
        super();
    }

    @Override
    @SubscribeEvent
    public void onAnyZPEvent(Event event) {
        super.defaultExec(event);
    }

    //***************************************

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        for (ZPEntityRenderMatching.RenderPair<?> pair : ZPEntityRenderMatching.getEntityRendererPairs()) {
            ZPClientMod.register(event, pair);
        }
        ZPEntityRenderMatching.clear();
    }

    public static <R extends Entity> void register(EntityRenderersEvent.RegisterRenderers event, ZPEntityRenderMatching.RenderPair<R> pair) {
        event.registerEntityRenderer(pair.registryObject().get(), pair.provider());
    }

    @SubscribeEvent
    public static void onBuildContents(BuildCreativeModeTabContentsEvent event) {
        ZPItemTabAddHelper.onBuildContents(event);
    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper helper = event.getExistingFileHelper();
        generator.addProvider(event.includeClient(), new ZPItemModelProvider(output, helper));
        generator.addProvider(event.includeClient(), new ZPBlockModelProvider(output, helper));
    }
}
