package ru.gltexture.zpm3.engine.events.client;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.events.ZPAbstractEventMod;
import ru.gltexture.zpm3.engine.helpers.ZPEntityRenderMatchHelper;
import ru.gltexture.zpm3.engine.helpers.ZPItemTabAddHelper;
import ru.gltexture.zpm3.engine.helpers.ZPParticleRenderHelper;
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
    public static void onRegisterEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        for (ZPEntityRenderMatchHelper.EntityRenderPair<?> pair : ZPEntityRenderMatchHelper.getEntityRendererPairs()) {
            ZPClientMod.registerEntityRender(event, pair);
        }
        ZPEntityRenderMatchHelper.clear();
    }

    @SubscribeEvent
    public static void onRegisterParticleRenderers(RegisterParticleProvidersEvent event) {
        for (ZPParticleRenderHelper.ParticleRenderPair<?> pair : ZPParticleRenderHelper.getParticleRendererPairs()) {
            ZPClientMod.registerParticleRender(event, pair);
        }
        ZPParticleRenderHelper.clear();
    }

    public static <R extends Entity> void registerEntityRender(EntityRenderersEvent.RegisterRenderers event, ZPEntityRenderMatchHelper.EntityRenderPair<R> pair) {
        event.registerEntityRenderer(pair.registryObject().get(), pair.provider());
    }

    public static <R extends ParticleOptions> void registerParticleRender(RegisterParticleProvidersEvent event, ZPParticleRenderHelper.ParticleRenderPair<R> pair) {
        event.registerSpriteSet(pair.type().get(), (s) ->pair.particleProvider().apply(s));
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
