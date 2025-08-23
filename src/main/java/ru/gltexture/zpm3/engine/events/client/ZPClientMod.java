package ru.gltexture.zpm3.engine.events.client;

import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ru.gltexture.zpm3.engine.client.rendering.shaders.ZPShaderReloader;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.events.ZPAbstractEventMod;
import ru.gltexture.zpm3.engine.helpers.*;
import ru.gltexture.zpm3.engine.helpers.gen.providers.*;
import ru.gltexture.zpm3.engine.helpers.gen.sub_providers.ZPBlocksSubProvider;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

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
    public static void onRegisterBindings(RegisterKeyMappingsEvent event) {
        ZPKeyBindingsRegistryHelper.getAllKeyBindings().forEach(e -> {
            e.init();
            e.getKeyMappingList().forEach(event::register);
        });
        ZPKeyBindingsRegistryHelper.clear();
    }

    @SubscribeEvent
    public static void onRegisterEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        for (ZPEntityRenderMatchHelper.EntityRenderPair<?> pair : ZPEntityRenderMatchHelper.getEntityRendererPairs()) {
            ZPClientMod.registerEntityRender(event, pair);
        }
        ZPEntityRenderMatchHelper.clear();
    }

    @SubscribeEvent
    public static void onRegisterParticleRenderers(RegisterParticleProvidersEvent event) {
        for (ZPParticleRenderHelper.ParticleRenderPairSet<?> pair : ZPParticleRenderHelper.getParticleRendererPairSets()) {
            ZPClientMod.registerParticleRenderSet(event, pair);
        }
        ZPParticleRenderHelper.clear();
    }

    public static <R extends Entity> void registerEntityRender(EntityRenderersEvent.RegisterRenderers event, ZPEntityRenderMatchHelper.EntityRenderPair<R> pair) {
        event.registerEntityRenderer(pair.registryObject().get(), pair.provider());
    }

    public static <R extends ParticleOptions> void registerParticleRenderSet(RegisterParticleProvidersEvent event, ZPParticleRenderHelper.ParticleRenderPairSet<R> pair) {
        event.registerSpriteSet(pair.type().get(), (s) -> pair.particleProvider().apply(s));
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
        CompletableFuture<HolderLookup.Provider> lookup = event.getLookupProvider();

        generator.addProvider(event.includeClient(), new ZPItemModelProvider(output, helper));
        generator.addProvider(event.includeClient(), new ZPBlockModelProvider(output, helper));
        generator.addProvider(event.includeClient(), new ZPParticleTextureProvider(generator, ZombiePlague3.MOD_ID));
        generator.addProvider(event.includeServer(), new ZPBlockTagsProvider(output, lookup, ZombiePlague3.MOD_ID, helper));
        generator.addProvider(event.includeServer(), new ZPFluidTagsProvider(output, lookup, ZombiePlague3.MOD_ID, helper));
        generator.addProvider(event.includeServer(), new ZPSoundListProvider(generator, ZombiePlague3.MOD_ID));
        generator.addProvider(event.includeServer(), new ZPMixinConfigsProvider(generator, ZombiePlague3.MOD_ID));

        {
            ZPBlocksSubProvider subProvider1 = new ZPBlocksSubProvider(Collections.emptySet(), FeatureFlags.REGISTRY.allFlags(), ZPLootTableHelper.getLootPoolsToCreate());
            generator.addProvider(event.includeServer(), new ZPLootTableProvider(output, Collections.emptySet(), List.of(new LootTableProvider.SubProviderEntry(() -> subProvider1, LootContextParamSets.BLOCK))));
        }
    }

    @SubscribeEvent
    public static void onRegisterReloadListeners(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(new ZPShaderReloader());
    }
}
