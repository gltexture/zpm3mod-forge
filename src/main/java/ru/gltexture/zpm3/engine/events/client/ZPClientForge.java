package ru.gltexture.zpm3.engine.events.client;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
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
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ru.gltexture.zpm3.engine.client.rendering.ZPRenderHelper;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.events.ZPAbstractEventMod;
import ru.gltexture.zpm3.engine.helpers.ZPEntityRenderMatchHelper;
import ru.gltexture.zpm3.engine.helpers.ZPItemTabAddHelper;
import ru.gltexture.zpm3.engine.helpers.ZPLootTableHelper;
import ru.gltexture.zpm3.engine.helpers.ZPParticleRenderHelper;
import ru.gltexture.zpm3.engine.helpers.gen.providers.*;
import ru.gltexture.zpm3.engine.helpers.gen.sub_providers.ZPBlocksSubProvider;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = ZombiePlague3.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public final class ZPClientForge extends ZPAbstractEventMod {
    public ZPClientForge() {
        super();
    }

    @Override
    @SubscribeEvent
    public void onAnyZPEvent(Event event) {
        super.defaultExec(event);
    }

    //***************************************

    @SubscribeEvent
    public static void onRenderGuiOverlay(RenderGuiOverlayEvent.Post event) {
    }
}
