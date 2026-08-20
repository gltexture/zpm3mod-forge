/*
 *
 *  * zpm3forge
 *  * Copyright (C) 2026 gltexture
 *  *
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program. If not, see <https://www.gnu.org/licenses/>.
 *
 */

package ru.gltexture.zpm3.engine.core;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.TierSortingRegistry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.gltexture.zpm3.engine.client.rendering.IZPClientManager;
import ru.gltexture.zpm3.engine.client.rendering.ZPClientManager;
import ru.gltexture.zpm3.engine.client.rendering.callbacks.IZPClientCallbacksManager;
import ru.gltexture.zpm3.engine.client.rendering.hooks.IZPRenderHooksManager;
import ru.gltexture.zpm3.engine.client.rendering.hooks.ZPRenderHooksManager;
import ru.gltexture.zpm3.engine.client.rendering.imgui.interfaces.IZPImGuiInterface;
import ru.gltexture.zpm3.engine.core.api.addons.IZPAddonEntry;
import ru.gltexture.zpm3.engine.core.api.addons.context.IAddonClientSetupContext;
import ru.gltexture.zpm3.engine.core.api.addons.context.IAddonInitContext;
import ru.gltexture.zpm3.engine.core.api.addons.context.IAddonPostInitContext;
import ru.gltexture.zpm3.engine.core.api.addons.context.IAddonPreInitContext;
import ru.gltexture.zpm3.engine.core.api.events.ZP3EventHandlerClass;
import ru.gltexture.zpm3.engine.core.api.events.client.ZPEventBus_ClientInput;
import ru.gltexture.zpm3.engine.core.api.events.client.ZPEventBus_ClientRendering;
import ru.gltexture.zpm3.engine.core.api.events.client.ZPEventBus_ClientResources;
import ru.gltexture.zpm3.engine.core.api.events.common.ZPEventBus_Blocks;
import ru.gltexture.zpm3.engine.core.api.events.common.ZPEventBus_Guns;
import ru.gltexture.zpm3.engine.core.api.events.common.ZPEventBus_World;
import ru.gltexture.zpm3.engine.core.api.modules.context.IModuleClientSetupContext;
import ru.gltexture.zpm3.engine.core.api.modules.context.IModuleInitContext;
import ru.gltexture.zpm3.engine.core.api.modules.context.IModulePostInitContext;
import ru.gltexture.zpm3.engine.core.api.modules.context.IModulePreInitContext;
import ru.gltexture.zpm3.engine.core.config.ZPConfigConstantsClass;
import ru.gltexture.zpm3.engine.core.config.ZPConfigManager;
import ru.gltexture.zpm3.engine.core.config.builtin.*;
import ru.gltexture.zpm3.engine.events.client.ZPClientZp3;
import ru.gltexture.zpm3.engine.events.common.ZPCommonZp3;
import ru.gltexture.zpm3.engine.exceptions.ZPAPIException;
import ru.gltexture.zpm3.engine.helpers.*;
import ru.gltexture.zpm3.engine.network.handler.ZPNetworkHandler;
import ru.gltexture.zpm3.engine.network.handler.ZPNetworkHandlerClient;
import ru.gltexture.zpm3.engine.network.handler.ZPNetworkHandlerServer;
import ru.gltexture.zpm3.engine.registry.ZPCommonRegistry;
import ru.gltexture.zpm3.engine.zones.ZPZoneFlag;
import ru.gltexture.zpm3.engine.zones.ZPZonesRegistry;
import ru.gltexture.zpm3.engine.zones.vars.ZPZoneIntVar;
import ru.gltexture.zpm3.modules.armor.events.client.ZPPlayerArmorSoundOnClientEvent;
import ru.gltexture.zpm3.modules.commands.events.client.ZPRenderSpecialZoneEffectsOnClient;
import ru.gltexture.zpm3.modules.entity.population.ZPSetupPopulation;
import ru.gltexture.zpm3.modules.loot_cases.events.provider.ZPSyntheticLootCasesDataGenRegistry;
import ru.gltexture.zpm3.engine.client.rendering.shaders.ZPDefaultShaders;
import ru.gltexture.zpm3.engine.core.api.modules.ZPModule;
import ru.gltexture.zpm3.engine.core.api.modules.ZPModuleData;
import ru.gltexture.zpm3.engine.instances.items.tier.ZPTier;
import ru.gltexture.zpm3.engine.population.ZPPopulationController;
import ru.gltexture.zpm3.engine.client.init.ZPSystemInit;
import ru.gltexture.zpm3.engine.events.ZPForgeEventHandlerClass;
import ru.gltexture.zpm3.engine.events.common.ZPCommonForge;
import ru.gltexture.zpm3.engine.events.common.ZPCommonMod;
import ru.gltexture.zpm3.engine.events.client.ZPClientForge;
import ru.gltexture.zpm3.engine.events.client.ZPClientMod;
import ru.gltexture.zpm3.engine.events.server.ZPServerForge;
import ru.gltexture.zpm3.engine.events.server.ZPServerMod;
import ru.gltexture.zpm3.engine.exceptions.ZPIOException;
import ru.gltexture.zpm3.engine.exceptions.ZPRuntimeException;
import ru.gltexture.zpm3.engine.keybind.ZPKeyBindingsManager;
import ru.gltexture.zpm3.engine.network.ZPNetwork;
import ru.gltexture.zpm3.engine.instances.items.tier.ZPTierData;
import ru.gltexture.zpm3.engine.recipes.ZPRecipesController;
import ru.gltexture.zpm3.engine.recipes.ZPRecipesRegistry;
import ru.gltexture.zpm3.engine.registry.ZPRegistryCollections;
import ru.gltexture.zpm3.engine.service.ZPPath;
import ru.gltexture.zpm3.engine.service.ZPUtility;
import ru.gltexture.zpm3.modules.loot_cases.loot_tables.ZPLootTable;
import ru.gltexture.zpm3.modules.loot_cases.loot_tables.synthetic.ZPSyntheticLootCaseDescription;
import ru.gltexture.zpm3.modules.mob_effects.client.ZPFakeClientEffect;
import ru.gltexture.zpm3.modules.mob_effects.client.ZPLocalPlayerFakeEffectsManager;
import ru.gltexture.zpm3.modules.net_pack.data.accessors.ZPGlobalAccessorsRegistry;
import ru.gltexture.zpm3.modules.net_pack.data.accessors.ZPNetDataAccessor;
import ru.gltexture.zpm3.modules.net_pack.data.data_ent.ZPNetDataVar;
import ru.gltexture.zpm3.modules.worldgen.archiver.ZPMapArchivedRegistry;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Mod(ZombiePlague3.MOD_ID)
public final class ZombiePlague3 {
    public static final String ZP_MAIN_DIR = "zpm3_files";
    public static final String ZP_ADDONS_DIR = "zpm3_addons";

    public static final String assetsJsonPath = "zpm3.modules.json";
    public static final String MOD_ID = "zpm3";
    static final Logger LOGGER = LoggerFactory.getLogger(ZombiePlague3.MOD_ID);
    private static final ZPProject MOD_INFO = new ZPProject("ZombiePlague3Mod", ZombiePlague3.MOD_ID, "0.2.0a DEV");
    private final ZPRegistryConveyor zpRegistryConveyor;
    private final List<ZPModule> assets;
    private ZPNetwork zpNetwork;
    private static ZPPopulationController populationController;
    private static ZPRecipesController recipesController;
    private static ZPConfigManager zpConfigManager;
    static ZP_EventsManager ZP_EVENTS;

    @OnlyIn(Dist.CLIENT) private static IZPClientManager clientManager;

    static {
        ZombiePlague3.populationController = new ZPPopulationController();
        ZombiePlague3.recipesController = new ZPRecipesController();
        ZombiePlague3.zpConfigManager = new ZPConfigManager();
        ZombiePlague3.ZP_EVENTS = new ZP_EventsManager();
    }

    private static boolean commonInitSwitch = true;
    private static boolean clientInitSwitch = true;

    public ZombiePlague3() {
        ZPUtility.sides().onlyClient(() -> {
            ZombiePlague3.clientManager = new ZPClientManager();
        });
        this.assets = new ArrayList<>();
        this.zpRegistryConveyor = new ZPRegistryConveyor();
        this.onModInit();
    }

    public static boolean DEV_MODE() {
        return ZPCoreConfig.DEV_MODE.getVar();
    }

    public static boolean WIP_MODE() {
        return ZPCoreConfig.WIP_MODE.getVar();
    }

    public static boolean isDevEnvironment() {
        return !FMLLoader.isProduction() || ZPCoreConfig.DEV_MODE.getVar();
    }

    @SuppressWarnings("all")
    private static IEventBus getModEventBus() {
        return FMLJavaModLoadingContext.get().getModEventBus();
    }

    private void createNet() {
        ZPLogger.info(this + " INIT-NETWORK");
        this.zpNetwork = new ZPNetwork(ZombiePlague3.NETWORK_CHANNEL(), ZombiePlague3.NETWORK_PROTO_VER());
        {
            ZPNetworkHandlerServer.init();
            ZPNetworkHandler.server().setNetwork(this.zpNetwork);
        }
        {
            ZPUtility.sides().onlyClient(() -> {
                ZPNetworkHandlerClient.init();
                ZPNetworkHandler.client().setNetwork(this.zpNetwork);
            });
        }
    }

    private void onModInit() {
        ZPLogger.info(this + " INIT");
        final IEventBus modEventBus = ZombiePlague3.getModEventBus();
        this.createNet();

        if (!Files.exists(new ZPPath(FMLPaths.GAMEDIR.get().toString(), ZombiePlague3.ZP_MAIN_DIR).toPath())) {
            if (new ZPPath(FMLPaths.GAMEDIR.get().toString(), ZombiePlague3.ZP_MAIN_DIR).toFile().mkdirs()) {
                ZPLogger.info(this + "Created ZP3 Folder");
            }
        }

        {
            ZombiePlague3.processCoreConfiguration();
            ZombiePlague3.processDefaultConfigurations();
        }

        this.initModules();
        this.registerTiers();
        modEventBus.addListener(this::fml_commonSetupEvent);
        modEventBus.addListener(this::fml_completeSetup);
        ZPUtility.sides().onlyClient(() -> {
            modEventBus.addListener(this::fml_clientSetupEvent);
            MinecraftForge.EVENT_BUS.register(new ZPDevOverlay());
        });
        ZPLogger.info(this + " END INIT");
    }

    public static void RegisterMeAsAddon(@NotNull final IZPAddonEntry addonEntry) throws ZPAPIException {
        ZP_AddonsManager.INSTANCE.register(addonEntry);
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerKeyBindings(@NotNull ZPKeyBindingsManager keyBindingsManager) {
        ZPKeyBindingsRegistryHelper.addNewKeybinding(keyBindingsManager);
    }

    private static void registerTier(@NotNull ZPTierData tier) {
        TierSortingRegistry.registerTier(tier.tier(), ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), tier.name()), tier.after(), tier.before());
    }

    public static void registerDeferred(DeferredRegister<?> deferredRegister) {
        deferredRegister.register(ZombiePlague3.getModEventBus());
    }

    private static void processCoreConfiguration() {
        try {
            ZombiePlague3.zpConfigManager.processConfigConstants(new ZPPath(FMLPaths.GAMEDIR.get().toString(), ZombiePlague3.ZP_MAIN_DIR), "core", ZPCoreConfig.class);
        } catch (IllegalAccessException | IOException e) {
            throw new ZPIOException(e);
        }
    }

    private static void processDefaultConfigurations() {
        try {
            ZombiePlague3.zpConfigManager.processConfigConstants(new ZPPath(FMLPaths.GAMEDIR.get().toString(), ZombiePlague3.ZP_MAIN_DIR), "client", ZPClientConfig.class);
            ZombiePlague3.zpConfigManager.processConfigConstants(new ZPPath(FMLPaths.GAMEDIR.get().toString(), ZombiePlague3.ZP_MAIN_DIR), "combat", ZPCombatConfig.class);
            ZombiePlague3.zpConfigManager.processConfigConstants(new ZPPath(FMLPaths.GAMEDIR.get().toString(), ZombiePlague3.ZP_MAIN_DIR), "network", ZPNetworkConfig.class);
            ZombiePlague3.zpConfigManager.processConfigConstants(new ZPPath(FMLPaths.GAMEDIR.get().toString(), ZombiePlague3.ZP_MAIN_DIR), "world", ZPWorldConfig.class);
            ZombiePlague3.zpConfigManager.processConfigConstants(new ZPPath(FMLPaths.GAMEDIR.get().toString(), ZombiePlague3.ZP_MAIN_DIR), "experimental", ZPExperimentalConfig.class);
            ZombiePlague3.zpConfigManager.processConfigConstants(new ZPPath(FMLPaths.GAMEDIR.get().toString(), ZombiePlague3.ZP_MAIN_DIR), "entity", ZPEntityConfig.class);
            ZombiePlague3.zpConfigManager.processConfigConstants(new ZPPath(FMLPaths.GAMEDIR.get().toString(), ZombiePlague3.ZP_MAIN_DIR), "zombie", ZPZombieConfig.class);
        } catch (IllegalAccessException | IOException e) {
            throw new ZPIOException(e);
        }
    }

    public static void processModuleConfiguration(@NotNull ZPModule zpModule, @NotNull Class<? extends ZPConfigConstantsClass> clazz) {
        try {
            ZombiePlague3.zpConfigManager.processConfigConstants(new ZPPath(FMLPaths.GAMEDIR.get().toString(), ZombiePlague3.ZP_MAIN_DIR), zpModule.getModuleData().name(), clazz);
        } catch (IllegalAccessException | IOException e) {
            throw new ZPIOException(e);
        }
    }

    public static void processAddonConfiguration(@NotNull IZPAddonEntry zpAddon, @NotNull String confName, @NotNull Class<? extends ZPConfigConstantsClass> clazz) {
        try {
            final ZPPath path = new ZPPath(FMLPaths.GAMEDIR.get().toString(), ZombiePlague3.ZP_MAIN_DIR, ZP_AddonsManager.INSTANCE.getAddonId(zpAddon));
            if (!path.toFile().exists()) {
                if (!path.toFile().mkdirs()) {
                    throw new ZPIOException(path.toFile().getAbsolutePath() + " could not be created");
                }
            }
            ZombiePlague3.zpConfigManager.processConfigConstants(new ZPPath(FMLPaths.GAMEDIR.get().toString(), ZombiePlague3.ZP_MAIN_DIR, ZP_AddonsManager.INSTANCE.getAddonId(zpAddon)), confName, clazz);
        } catch (IllegalAccessException | IOException e) {
            throw new ZPIOException(e);
        }
    }

    public static class ZPDevOverlay {
        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent
        public void onRenderGui(RenderGuiOverlayEvent.Post event) {
            if (ZPClientConfig.SHOW_VERSION_INFO_ON_SCREEN.getVar()) {
                Minecraft mc = Minecraft.getInstance();
                String text = "ZP3" + " | " + ZombiePlague3.MOD_VERSION();
                GuiGraphics gg = event.getGuiGraphics();
                int screenWidth = event.getWindow().getGuiScaledWidth();
                int x = screenWidth - mc.font.width(text) - 6;
                int y = 6;
                gg.drawString(mc.font, text, x, y, 0xFF0000, true);
            }
        }
    }

    private void registerTiers() {
        ZPTiersRegistryHelper.tierSet.forEach(e -> Arrays.stream(e).forEach(s -> ZombiePlague3.registerTier(s.init())));
        ZPTiersRegistryHelper.clear();
    }

    private void initModules() {
        ZPLogger.info(this + " Modules setup");
        this.readModulesJSON(this.assets);

        for (ZPModule zpModule : this.assets) {
            zpModule.preInitialize(new ModulePreInitContext());
        }

        {
            ZPUtility.sides().onlyClient(() -> {
                ZombiePlague3.ZP_EVENTS.initEventBus(ZPEventBus_ClientRendering.class, ZPEventBus_ClientResources.class, ZPEventBus_ClientInput.class);
            });
            ZombiePlague3.ZP_EVENTS.initEventBus(ZPEventBus_Guns.class, ZPEventBus_World.class, ZPEventBus_Blocks.class);
            this.registerCommonZp3Events();
        }

        for (ZPModule zpModule : this.assets) {
            ZPLogger.info("Init module: " + zpModule);
            final ModuleInitContext moduleInitContext = new ModuleInitContext();
            zpModule.initialize(moduleInitContext);
            this.getZpNetwork().register(moduleInitContext.getPacketDataSet());
            this.getZpRegistryConveyor().launch(moduleInitContext.getRegistrySet());

            moduleInitContext.getEventClasses().forEach(e -> {
                try {
                    final Method getDistMethod = e.getDeclaredMethod("getSide");
                    final Method getBusMethod = e.getDeclaredMethod("getBus");
                    final ZPForgeEventHandlerClass instance = e.getDeclaredConstructor().newInstance();
                    final ZPSide result = (ZPSide) getDistMethod.invoke(instance);
                    final Mod.EventBusSubscriber.Bus result2 = (Mod.EventBusSubscriber.Bus) getBusMethod.invoke(instance);
                    this.registerForgeEvents(e, result2, result);
                } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException ex) {
                    throw new ZPRuntimeException(ex);
                }
            });

            moduleInitContext.getEventClassObjects().forEach(e -> {
                this.registerForgeEvents(e, e.getBus(), e.getSide());
            });
        }

        ZPUtility.sides().onlyClient(() -> {
            ZombiePlague3.getModEventBus().register(new ZPClientMod());
            MinecraftForge.EVENT_BUS.register(new ZPClientForge());
        });

        ZPUtility.sides().onlyDedicatedServer(() -> {
            ZombiePlague3.getModEventBus().register(new ZPServerMod());
            MinecraftForge.EVENT_BUS.register(new ZPServerForge());
        });

        {
            ZombiePlague3.getModEventBus().register(new ZPCommonMod());
            MinecraftForge.EVENT_BUS.register(new ZPCommonForge());
        }

        for (ZPModule zpModule : this.assets) {
            zpModule.postInitialize(new ModulePostInitContext());
        }
    }

    private void initAddons() {
        ZPLogger.info(this + " Addons setup");
        for (ZP_AddonsManager.ZPAddonInfo zpAddonInfo : ZP_AddonsManager.INSTANCE.getRegisteredAddons()) {
            ZPLogger.info("Init addon: " + zpAddonInfo.modId());
            {
                zpAddonInfo.zpAddon().ZP3AddonImpl().preInitialize(new AddonPreInitContext());
            }
            {
                final AddonInitContext addonInitContext = new AddonInitContext();
                zpAddonInfo.zpAddon().ZP3AddonImpl().initialize(addonInitContext);
            }
            {
                zpAddonInfo.zpAddon().ZP3AddonImpl().postInitialize(new AddonPostInitContext());
            }
        }
    }

    private void registerCommonZp3Events() {
        ZPUtility.sides().onlyClient(() -> {
            ZombiePlague3.ZP_EVENTS.registerEvents(ZPClientZp3.class);
        });

        {
            ZombiePlague3.ZP_EVENTS.registerEvents(ZPCommonZp3.class);
        }
    }

    private void registerZp3Events(Set<Class<?>> classesWithZp3Events) {
        ZombiePlague3.ZP_EVENTS.registerEvents(classesWithZp3Events);
    }

    private void registerForgeEvents(Object eventClass, Mod.EventBusSubscriber.Bus bus, ZPSide side) {
        switch (side) {
            case CLIENT -> {
                ZPUtility.sides().onlyClient(() -> {
                    switch (bus) {
                        case MOD -> ZombiePlague3.getModEventBus().register(eventClass);
                        case FORGE -> MinecraftForge.EVENT_BUS.register(eventClass);
                    }
                });
            }
            case DEDICATED_SERVER -> {
                ZPUtility.sides().onlyDedicatedServer(() -> {
                    switch (bus) {
                        case MOD -> ZombiePlague3.getModEventBus().register(eventClass);
                        case FORGE -> MinecraftForge.EVENT_BUS.register(eventClass);
                    }
                });
            }
            case COMMON -> {
                switch (bus) {
                    case MOD -> ZombiePlague3.getModEventBus().register(eventClass);
                    case FORGE -> MinecraftForge.EVENT_BUS.register(eventClass);
                }
            }
        }
    }
    
    private void readModulesJSON(List<ZPModule> modules) {
        String jsonRaw = null;
        try {
            jsonRaw = ZPUtility.files().readTextFromJar(new ZPPath(ZombiePlague3.assetsJsonPath));
        } catch (IOException e) {
            throw new ZPIOException(e);
        }

        JsonObject jsonObject = JsonParser.parseString(jsonRaw).getAsJsonObject();
        JsonArray jsonElements = jsonObject.getAsJsonArray("modules");
        for (int i = 0; i < jsonElements.size(); i++) {
            JsonObject asset = jsonElements.get(i).getAsJsonObject();

            try {
                final String pathToClass = asset.get("class").getAsString();
                final Class<?> zpAssetClass = Class.forName(pathToClass);
                try {
                    final String name = asset.get("name").getAsString();
                    final ZPModule obj = (ZPModule) zpAssetClass.getDeclaredConstructor(ZPModuleData.class).newInstance(new ZPModuleData(name));
                    modules.add(obj);
                } catch (ClassCastException e) {
                    ZPLogger.exception(e);
                } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
                    throw new ZPRuntimeException(e);
                }
            } catch (ClassNotFoundException e) {
                throw new ZPRuntimeException(e);
            }
        }
    }

    @SuppressWarnings("removal")
    @OnlyIn(Dist.CLIENT)
    private void fml_clientSetupEvent(final FMLClientSetupEvent event) {
        ZPLogger.info(this + " Client setup");
        Runtime.getRuntime().addShutdownHook(new Thread(this::clientShutDown, "ZP3-ClientShutdown"));
        RenderSystem.recordRenderCall(() -> {
            ZPSystemInit.client();
            ZPDefaultShaders.init();
            for (ZPModule zpModule : this.assets) {
                zpModule.clientSetup(new ModuleClientSetupContext());
            }
            {
                for (ZP_AddonsManager.ZPAddonInfo zpAddonInfo : ZP_AddonsManager.INSTANCE.getRegisteredAddons()) {
                    zpAddonInfo.zpAddon().ZP3AddonImpl().clientSetup(new AddonClientSetupContext());
                }
            }
            ZPSystemInit.clientRunSetup(Minecraft.getInstance().getWindow());
            {
                ZombiePlague3.clientInitSwitch = false;
            }
        });
        ZPBlocksRenderLayerHelper.liquidPairs.forEach(e -> {
            ItemBlockRenderTypes.setRenderLayer(e.fluid().get(), e.type());
        });
        ZPBlocksRenderLayerHelper.blockPairSet.forEach(e -> {
            ItemBlockRenderTypes.setRenderLayer(e.fluid().get(), e.type());
        });
        ZPBlocksRenderLayerHelper.clearAll();

        event.enqueueWork(() -> {
           try {
               ZPMapArchivedRegistry.registerAll();
           } catch (Exception e) {
               throw new ZPRuntimeException(e);
           }
        });
    }

    private void clientShutDown() {
        ZPLogger.info(this + " Client destroy");
        RenderSystem.recordRenderCall(() -> {
            for (ZPModule zpModule : this.assets) {
                zpModule.clientShutDown();
            }
            ZPSystemInit.clientRunDestroy(Minecraft.getInstance().getWindow());
        });
    }

    private void commonShutDown() {
        ZPLogger.info(this + " Common destroy");
        for (ZPModule zpModule : this.assets) {
            zpModule.commonShutdown();
        }
        for (ZP_AddonsManager.ZPAddonInfo zpAddonInfo : ZP_AddonsManager.INSTANCE.getRegisteredAddons()) {
            zpAddonInfo.zpAddon().ZP3AddonImpl().clientShutDown();
        }
        ZPRegistryCollections.clearAll();
        ZPZonesRegistry.clear();
    }

    private void fml_completeSetup(final FMLLoadCompleteEvent event) {
        this.getZpRegistryConveyor().launchLaterList();
    }

    private void fml_commonSetupEvent(final FMLCommonSetupEvent event) {
        ZPLogger.info(this + " Common setup");
        Runtime.getRuntime().addShutdownHook(new Thread(this::commonShutDown, "ZP3-CommonShutdown"));
        ZPCommonRegistry.execLaterConsumers();
        for (ZPModule zpModule : this.assets) {
            zpModule.commonSetup();
        }
        {
            this.initAddons();
        }
        this.initDispenserData();

        {
            ZPGlobalAccessorsRegistry.INSTANCE.buildIdAssignations();
            AddonInitContext.laterSetupNetAccessors.forEach(e -> e.accept(null));
            ModuleInitContext.laterSetupNetAccessors.forEach(e -> e.accept(null));
            AddonInitContext.laterSetupNetAccessors= null;
            ModuleInitContext.laterSetupNetAccessors= null;
        }

        {
            ZombiePlague3.commonInitSwitch = false;
        }
    }

    public static boolean isCommonInitEnded() {
        return !ZombiePlague3.commonInitSwitch;
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean isClientInitEnded() {
        return !ZombiePlague3.clientInitSwitch;
    }

    public static void commonInitValidation() throws ZPRuntimeException {
        if (ZombiePlague3.isCommonInitEnded()) {
            throw new ZPRuntimeException("Couldn't continue exec, because common init is ended");
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void clientInitValidation() throws ZPRuntimeException {
        if (ZombiePlague3.isClientInitEnded()) {
            throw new ZPRuntimeException("Couldn't continue exec, because client init is ended");
        }
    }

    private void initDispenserData() {
        ZPLogger.info(this + " Init dispensers data");
        for (final Map.Entry<RegistryObject<? extends Item>, ZPDispenseProjectileHelper.ProjectileData> entry : ZPDispenseProjectileHelper.getDispenserMap().entrySet()) {
            DispenserBlock.registerBehavior(entry.getKey().get(), new AbstractProjectileDispenseBehavior() {
                @Override
                protected @NotNull Projectile getProjectile(@NotNull Level pLevel, @NotNull Position pPosition, @NotNull ItemStack pStack) {
                    return entry.getValue().projectileFactory().getProjectile(pLevel, pPosition, pStack);
                }

                @Override
                protected float getUncertainty() {
                    return entry.getValue().inaccuracy();
                }

                @Override
                protected float getPower() {
                    return entry.getValue().power();
                }
            });
        }

        for (Map.Entry<Supplier<ItemLike>, DefaultDispenseItemBehavior> entry : ZPDispenseRegHelper.getDispenserMap().entrySet()) {
            DispenserBlock.registerBehavior(entry.getKey().get().asItem(), entry.getValue());
        }
        ZPDispenseRegHelper.clear();
    }

    public static ZPPopulationController getPopulationController() {
        return ZombiePlague3.populationController;
    }

    public static ZPRecipesController getRecipesController() {
        return ZombiePlague3.recipesController;
    }

   // public static ZPNetworkHandlerServer net() {
   //     return ZPNetworkHandlerServer.instance;
   // }

    public static <E extends ZPNetworkHandler> E net(boolean isServer) {
        return net(isServer ? ZPNetworkHandler.Side.SERVER : ZPNetworkHandler.Side.CLIENT);
    }

    @SuppressWarnings("all")
    public static <E extends ZPNetworkHandler> E net(@NotNull ZPNetworkHandler.Side side) {
        switch (side) {
            case CLIENT -> {
                return (E) ZPNetworkHandler.client();
            }
            case SERVER -> {
                return (E) ZPNetworkHandler.server();
            }
        }
        throw new ZPRuntimeException("Invalid side " + side);
    }

    @OnlyIn(Dist.CLIENT)
    public static IZPClientManager getClientManager() {
        return ZombiePlague3.clientManager;
    }

    @OnlyIn(Dist.CLIENT)
    public static ZPNetworkHandlerClient netClient() {
        return ZPNetworkHandlerClient.instance;
    }

    public static ZPNetworkHandlerServer netServer() {
        return ZPNetworkHandlerServer.instance;
    }

    public ZPNetwork getZpNetwork() {
        return this.zpNetwork;
    }

    public static ZPConfigManager getZpConfigManager() {
        return ZombiePlague3.zpConfigManager;
    }

    public ZPRegistryConveyor getZpRegistryConveyor() {
        return this.zpRegistryConveyor;
    }

    @Override
    public String toString() {
        return "[" + ZombiePlague3.MOD_NAME() + " - " + ZombiePlague3.MOD_VERSION() + "]";
    }

    public static String MOD_NAME() {
        return ZombiePlague3.MOD_INFO.MOD_NAME();
    }

    public static String MOD_ID() {
        return ZombiePlague3.MOD_INFO.MOD_ID();
    }

    public static String MOD_VERSION() {
        return ZombiePlague3.MOD_INFO.VERSION();
    }

    public static String NETWORK_CHANNEL() {
        return "zpm3main";
    }

    public static String NETWORK_PROTO_VER() {
        return ZombiePlague3.MOD_VERSION();
    }

    @Deprecated(forRemoval = true)
    public interface IMixinEntry {
        void addMixinConfigData(@NotNull MixinConfig mixinConfig, @NotNull MixinClass... classes);

        record MixinClass(@NotNull String name, @NotNull ZPSide side) { ; }
        record MixinConfig(@NotNull String name, @NotNull String packagePath) { ; }
    }

    @OnlyIn(Dist.CLIENT)
    public static final class ModuleClientSetupContext implements IModuleClientSetupContext {
        @Override
        public void registerImGuiInterface(@NotNull Supplier<IZPImGuiInterface> imGuiInterface) {
            if (this.isImGuiContextValid()) {
                Objects.requireNonNull(this.getClientManager().getImGuiInterfacesManager()).addRenderableInterface(imGuiInterface.get());
            }
        }

        @Override
        public void registerZpArchivedMap(@NotNull String modId, @NotNull String folder) {
            ZPMapArchivedRegistry.registerZpArchivedMap(modId, folder);
        }

        @Override
        public void createConditionToApplyFakeEffect(@NotNull ZPFakeClientEffect key, ZPLocalPlayerFakeEffectsManager.@NotNull ZPFakeEffectSetOnPlayerCondition condition) {
            ZPLocalPlayerFakeEffectsManager.INSTANCE.createConditionToApplyFakeEffect(key, condition);
        }

        @Override
        public void registerArmorSound(ZPPlayerArmorSoundOnClientEvent.@NotNull TrackedSoundLauncher trackedSoundLauncher) {
            ZPPlayerArmorSoundOnClientEvent.registerArmorSound(trackedSoundLauncher);
        }

        @Override
        public void registerZoneEffect(@NotNull ZPZoneFlag flag, ZPRenderSpecialZoneEffectsOnClient.@NotNull RenderZoneEffect effect) {
            ZPRenderSpecialZoneEffectsOnClient.registerZoneEffect(flag, effect);
        }

        @Override
        public @NotNull IZPClientCallbacksManager getClientCallbacksManager() {
            return this.getClientManager().getCallbacksManager();
        }

        @Override
        public @NotNull IZPRenderHooksManager getClientRenderHooksManager() {
            return ZPRenderHooksManager.INSTANCE;
        }

        @Override
        public @NotNull IZPClientManager getClientManager() {
            return ZombiePlague3.getClientManager();
        }
    }

    public static final class ModulePostInitContext implements IModulePostInitContext {

    }

    public static final class ModulePreInitContext implements IModulePreInitContext {
        @Override
        public void registerDispenserBehaviour(@NotNull Supplier<ItemLike> itemLikeSupplier, @NotNull DefaultDispenseItemBehavior dispenseItemBehavior) {
            ZPDispenseRegHelper.addDispenserData(itemLikeSupplier, dispenseItemBehavior);
        }

        @OnlyIn(Dist.CLIENT)
        @Override
        public void registerKeyBindings(@NotNull ZPKeyBindingsManager keyBindingsManager) {
            ZombiePlague3.registerKeyBindings(keyBindingsManager);
        }

        @Override
        public @NotNull ZPZoneFlag registerZoneFlag(@NotNull String flag) {
            return ZPZonesRegistry.RegisterFlag(flag);
        }

        @Override
        public @NotNull ZPZoneIntVar registerZoneIntVar(@NotNull String variableId, @NotNull Integer t, @NotNull Integer min, @NotNull Integer max) {
            return ZPZonesRegistry.RegisterIntVar(variableId, t, min, max);
        }
    }

    public static final class ModuleInitContext implements IModuleInitContext {
        static List<Consumer<Void>> laterSetupNetAccessors = new ArrayList<>();
        private final List<Class<? extends ZPCommonRegistry<?>>> registrySet;
        private final List<Class<? extends ZPForgeEventHandlerClass>> eventClasses;
        private final Set<ZPForgeEventHandlerClass> eventClassObjects;
        private final List<ZPNetwork.PacketData<?>> packetDataSet;

        public ModuleInitContext() {
            this.registrySet = new ArrayList<>();
            this.eventClasses = new ArrayList<>();
            this.eventClassObjects = new HashSet<>();
            this.packetDataSet = new ArrayList<>();
        }

        @Override
        public void addCommonZp3RegistryClass(@NotNull Class<? extends ZPCommonRegistry<?>> zpRegistryClass) {
            this.getRegistrySet().add(zpRegistryClass);
        }

        @Override
        public void registerForgeEventHandlerClass(@NotNull Class<? extends ZPForgeEventHandlerClass> clazz) {
            this.getEventClasses().add(clazz);
        }

        @Override
        public void registerZP3EventHandlerClass(@NotNull Class<? extends ZP3EventHandlerClass> clazz) {
            ZombiePlague3.ZP_EVENTS.registerEvents(clazz);
        }

        @Override
        public void registerEventHandlerInstance(@NotNull ZPForgeEventHandlerClass object) {
            this.getEventClassObjects().add(object);
        }

        @Override
        public void registerNetworkPacket(ZPNetwork.@NotNull PacketData<?> packetData) {
            this.getPacketDataSet().add(packetData);
        }

        @Override
        public void defineNetAccessorOnEntity(@NotNull Class<? extends Entity> clazz, @NotNull ZPNetDataAccessor<?> dataAccessor) {
            ModuleInitContext.laterSetupNetAccessors.add((ignore) -> {
                ZombiePlague3.netServer().getNetEntDataSyncer().defineAccessorOnEntity(clazz, dataAccessor);
                ZombiePlague3.netClient().getNetEntDataSyncer().defineAccessorOnEntity(clazz, dataAccessor);
            });
        }

        @Override
        public <E> void defineStaticNetAccessor_ForServer(@NotNull ZPNetDataAccessor<E> accessor, @NotNull ZPNetDataVar<E> defaultValue) {
            ModuleInitContext.laterSetupNetAccessors.add((ignore) -> {
                ZombiePlague3.netServer().getNetStaticDataSyncer().defineServerAccessor(accessor, defaultValue);
                ZombiePlague3.netClient().getNetStaticDataSyncer().defineFromServerAccessor(accessor, defaultValue);
            });
        }

        @Override
        public <E> void defineStaticNetAccessor_ForClient(@NotNull ZPNetDataAccessor<E> accessor, @NotNull ZPNetDataVar<E> defaultValue) {
            ModuleInitContext.laterSetupNetAccessors.add((ignore) -> {
                ZombiePlague3.netServer().getNetStaticDataSyncer().defineFromClientAccessor(accessor, defaultValue);
                ZombiePlague3.netClient().getNetStaticDataSyncer().defineClientAccessor(accessor, defaultValue);
            });
        }

        @Override
        public void registerSyntheticLootCase(@NotNull ZPSyntheticLootCaseDescription lootCase) {
            ZPSyntheticLootCasesDataGenRegistry.registerSyntheticLootCase(lootCase);
        }

        @Override
        public void registerSyntheticLootTable(@NotNull ZPLootTable lootTable) {
            ZPSyntheticLootCasesDataGenRegistry.registerSyntheticLootTable(lootTable);
        }

        @Override
        public void addRecipesRegistry(ZPRecipesRegistry... recipesRegistries) {
            ZombiePlague3.recipesController.getRegistries().addAll(List.of(recipesRegistries));
        }

        @Override
        public void runPopulationSetup(@NotNull ZPSetupPopulation setup) {
            setup.setup(ZombiePlague3.getPopulationController());
        }

        @Override
        public void addTier(@NotNull ZPTier[] tier) {
            ZPTiersRegistryHelper.addToRegister(tier);
        }

        @Override
        public void registerTier(@NotNull ZPTierData tier) {
            ZombiePlague3.registerTier(tier);
        }

        List<ZPNetwork.PacketData<?>> getPacketDataSet() {
            return this.packetDataSet;
        }

        List<Class<? extends ZPCommonRegistry<?>>> getRegistrySet() {
            return this.registrySet;
        }

        List<Class<? extends ZPForgeEventHandlerClass>> getEventClasses() {
            return this.eventClasses;
        }

        Set<ZPForgeEventHandlerClass> getEventClassObjects() {
            return this.eventClassObjects;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static final class AddonClientSetupContext implements IAddonClientSetupContext {
        @Override
        public void registerImGuiInterface(@NotNull Supplier<IZPImGuiInterface> imGuiInterface) {
            if (this.isImGuiContextValid()) {
                Objects.requireNonNull(this.getClientManager().getImGuiInterfacesManager()).addRenderableInterface(imGuiInterface.get());
            }
        }

        @Override
        public void createConditionToApplyFakeEffect(@NotNull ZPFakeClientEffect key, ZPLocalPlayerFakeEffectsManager.@NotNull ZPFakeEffectSetOnPlayerCondition condition) {
            ZPLocalPlayerFakeEffectsManager.INSTANCE.createConditionToApplyFakeEffect(key, condition);
        }

        @Override
        public void registerArmorSound(ZPPlayerArmorSoundOnClientEvent.@NotNull TrackedSoundLauncher trackedSoundLauncher) {
            ZPPlayerArmorSoundOnClientEvent.registerArmorSound(trackedSoundLauncher);
        }

        @Override
        public void registerZoneEffect(@NotNull ZPZoneFlag flag, ZPRenderSpecialZoneEffectsOnClient.@NotNull RenderZoneEffect effect) {
            ZPRenderSpecialZoneEffectsOnClient.registerZoneEffect(flag, effect);
        }

        @Override
        public void registerZpArchivedMap(@NotNull String modId, @NotNull String folder) {
            ZPMapArchivedRegistry.registerZpArchivedMap(modId, folder);
        }

        @Override
        public @NotNull IZPClientCallbacksManager getClientCallbacksManager() {
            return this.getClientManager().getCallbacksManager();
        }

        @Override
        public @NotNull IZPRenderHooksManager getClientRenderHooksManager() {
            return ZPRenderHooksManager.INSTANCE;
        }

        @Override
        public @NotNull IZPClientManager getClientManager() {
            return ZombiePlague3.getClientManager();
        }
    }

    public static final class AddonPreInitContext implements IAddonPreInitContext {
        @Override
        public @NotNull ZPZoneFlag registerZoneFlag(@NotNull String flag) {
            return ZPZonesRegistry.RegisterFlag(flag);
        }

        @Override
        public @NotNull ZPZoneIntVar registerZoneIntVar(@NotNull String variableId, @NotNull Integer t, @NotNull Integer min, @NotNull Integer max) {
            return ZPZonesRegistry.RegisterIntVar(variableId, t, min, max);
        }
    }

    public static final class AddonInitContext implements IAddonInitContext {
        static List<Consumer<Void>> laterSetupNetAccessors = new ArrayList<>();

        @Override
        public void registerZP3EventHandlerClass(@NotNull Class<? extends ZP3EventHandlerClass> clazz) {
            ZombiePlague3.ZP_EVENTS.registerEvents(clazz);
        }

        @Override
        public void defineNetAccessorOnEntity(@NotNull Class<? extends Entity> clazz, @NotNull ZPNetDataAccessor<?> dataAccessor) {
            AddonInitContext.laterSetupNetAccessors.add((ignore) -> {
                ZombiePlague3.netServer().getNetEntDataSyncer().defineAccessorOnEntity(clazz, dataAccessor);
                ZombiePlague3.netClient().getNetEntDataSyncer().defineAccessorOnEntity(clazz, dataAccessor);
            });
        }

        @Override
        public <E> void defineStaticNetAccessor_ForServer(@NotNull ZPNetDataAccessor<E> accessor, @NotNull ZPNetDataVar<E> defaultValue) {
            AddonInitContext.laterSetupNetAccessors.add((ignore) -> {
                ZombiePlague3.netServer().getNetStaticDataSyncer().defineServerAccessor(accessor, defaultValue);
                ZombiePlague3.netClient().getNetStaticDataSyncer().defineFromServerAccessor(accessor, defaultValue);
            });
        }

        @Override
        public <E> void defineStaticNetAccessor_ForClient(@NotNull ZPNetDataAccessor<E> accessor, @NotNull ZPNetDataVar<E> defaultValue) {
            AddonInitContext.laterSetupNetAccessors.add((ignore) -> {
                ZombiePlague3.netServer().getNetStaticDataSyncer().defineFromClientAccessor(accessor, defaultValue);
                ZombiePlague3.netClient().getNetStaticDataSyncer().defineClientAccessor(accessor, defaultValue);
            });
        }

        @Override
        public void runPopulationSetup(@NotNull ZPSetupPopulation setup) {
            setup.setup(ZombiePlague3.getPopulationController());
        }
    }

    public static final class AddonPostInitContext implements IAddonPostInitContext {
    }
}


/*
                Reflections reflections = new Reflections(pack.getName());
                @SuppressWarnings("rawtypes")
                Set<Class<? extends ZPSimpleEventClass>> classes = reflections.getSubTypesOf(ZPSimpleEventClass.class);

                for (@SuppressWarnings("rawtypes") Class<? extends ZPSimpleEventClass> clazz : classes) {
                    try {
                        Method getDistMethod = clazz.getDeclaredMethod("getDist");
                        Object instance = clazz.getDeclaredConstructor().newInstance();
                        Dist result = (Dist) getDistMethod.invoke(instance);
                        switch (result) {
                            case CLIENT -> {
                                clientEvents.add(instance);
                            }
                            case DEDICATED_SERVER -> {
                                serverEvents.add(instance);
                            }
                        }
                    } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                        throw new ZPRuntimeException(e);
                    }
                }
 */