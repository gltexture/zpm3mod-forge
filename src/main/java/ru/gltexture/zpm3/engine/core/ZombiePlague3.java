package ru.gltexture.zpm3.engine.core;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.gltexture.zpm3.engine.core.config.ZPConfigConstantsClass;
import ru.gltexture.zpm3.engine.core.config.ZPConfigManager;
import ru.gltexture.zpm3.engine.core.config.builtin.*;
import ru.gltexture.zpm3.modules.entity.population.ZPSetupPopulation;
import ru.gltexture.zpm3.modules.loot_cases.registry.ZPLootTablesRegistry;
import ru.gltexture.zpm3.engine.client.rendering.shaders.ZPDefaultShaders;
import ru.gltexture.zpm3.engine.core.module.ZPModule;
import ru.gltexture.zpm3.engine.core.module.ZPModuleData;
import ru.gltexture.zpm3.engine.helpers.ZPTiersRegistryHelper;
import ru.gltexture.zpm3.engine.instances.items.tier.ZPTier;
import ru.gltexture.zpm3.engine.population.ZPPopulationController;
import ru.gltexture.zpm3.engine.core.init.ZPSystemInit;
import ru.gltexture.zpm3.engine.events.ZPEventClass;
import ru.gltexture.zpm3.engine.events.common.ZPCommonForge;
import ru.gltexture.zpm3.engine.events.common.ZPCommonMod;
import ru.gltexture.zpm3.engine.events.client.ZPClientForge;
import ru.gltexture.zpm3.engine.events.client.ZPClientMod;
import ru.gltexture.zpm3.engine.events.server.ZPServerForge;
import ru.gltexture.zpm3.engine.events.server.ZPServerMod;
import ru.gltexture.zpm3.engine.exceptions.ZPIOException;
import ru.gltexture.zpm3.engine.exceptions.ZPRuntimeException;
import ru.gltexture.zpm3.engine.helpers.ZPBlocksRenderLayerHelper;
import ru.gltexture.zpm3.engine.helpers.ZPDispenseProjectileHelper;
import ru.gltexture.zpm3.engine.helpers.ZPKeyBindingsRegistryHelper;
import ru.gltexture.zpm3.engine.keybind.ZPKeyBindingsManager;
import ru.gltexture.zpm3.engine.network.ZPNetwork;
import ru.gltexture.zpm3.engine.instances.items.tier.ZPTierData;
import ru.gltexture.zpm3.engine.recipes.ZPRecipesController;
import ru.gltexture.zpm3.engine.recipes.ZPRecipesRegistry;
import ru.gltexture.zpm3.engine.registry.ZPRegistry;
import ru.gltexture.zpm3.engine.registry.ZPRegistryCollections;
import ru.gltexture.zpm3.engine.service.ZPPath;
import ru.gltexture.zpm3.engine.service.ZPUtility;
import ru.gltexture.zpm3.modules.net_pack.data.ZPNetSyncDataPack;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@Mod(ZombiePlague3.MOD_ID)
public final class ZombiePlague3 {
    public static final String ZP_MAIN_DIR = "zpm3_files";

    public static boolean DEV_PREVIEW = true;

    public static final String assetsJsonPath = "zpm3.modules.json";
    public static final String MOD_ID = "zpm3";
    static final Logger LOGGER = LoggerFactory.getLogger(ZombiePlague3.MOD_ID);
    private static final ZPProject MOD_INFO = new ZPProject("ZombiePlague3Mod", ZombiePlague3.MOD_ID, "0.2.0a");
    private final ZPRegistryConveyor zpRegistryConveyor;
    private final List<ZPModule> assets;
    private ZPNetwork zpNetwork;
    private static ZPPopulationController populationController;
    private static ZPRecipesController recipesController;
    private static ZPConfigManager zpConfigManager;

    @OnlyIn(Dist.CLIENT) private static ZPNetSyncDataPack client_netSyncDataPack;

    static {
        ZombiePlague3.populationController = new ZPPopulationController();
        ZombiePlague3.recipesController = new ZPRecipesController();
        ZombiePlague3.zpConfigManager = new ZPConfigManager();
    }

    private static boolean commonInitSwitch = true;
    private static boolean clientInitSwitch = true;

    public ZombiePlague3() {
        this.assets = new ArrayList<>();
        this.zpRegistryConveyor = new ZPRegistryConveyor();
        this.init();
    }

    public static boolean isDevEnvironment() {
        return !FMLLoader.isProduction();
    }

    @SuppressWarnings("all")
    private static IEventBus getModEventBus() {
        return FMLJavaModLoadingContext.get().getModEventBus();
    }

    private void createNet() {
        ZPLogger.info(this + " INIT-NETWORK");
        this.zpNetwork = new ZPNetwork(ZombiePlague3.NETWORK_CHANNEL(), ZombiePlague3.NETWORK_PROTO_VER());
        ZombiePlague3.net().setNetwork(this.zpNetwork);
    }

    private void init() {
        ZPLogger.info(this + " INIT");
        final IEventBus modEventBus = ZombiePlague3.getModEventBus();
        this.createNet();

        {
            ZombiePlague3.processCoreConfiguration();
            ZombiePlague3.processDefaultConfigurations();
        }

        this.initModules();
        this.registerTiers();
        modEventBus.addListener(this::fml_commonSetupEvent);
        modEventBus.addListener(this::completeSetup);
        ZPUtility.sides().onlyClient(() -> {
            modEventBus.addListener(this::fml_clientSetupEvent);
            MinecraftForge.EVENT_BUS.register(new ZPDevOverlay());
        });
        ZPLogger.info(this + " END INIT");
    }
    public static void registerKeyBindings(@NotNull ZPKeyBindingsManager keyBindingsManager) {
        ZPKeyBindingsRegistryHelper.addNewKeybinding(keyBindingsManager);
    }

    public static void registerTier(@NotNull ZPTierData tier) {
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

    public static void processConfiguration(ZPModule zpModule, Class<ZPConfigConstantsClass> clazz) {
        try {
            ZombiePlague3.zpConfigManager.processConfigConstants(new ZPPath(FMLPaths.GAMEDIR.get().toString(), ZombiePlague3.ZP_MAIN_DIR), zpModule.getModuleData().name(), clazz);
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
                String text = "ZP3 Dev Preview" + " | " + ZombiePlague3.MOD_VERSION();
                if (!ZombiePlague3.DEV_PREVIEW) {
                    text = "ZP3" + " | " + ZombiePlague3.MOD_VERSION();
                }
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
            zpModule.preInitialize();
        }

        for (ZPModule zpModule : this.assets) {
            ZPLogger.info("Init module: " + zpModule);
            ModuleEntry moduleEntry = new ModuleEntry();
            zpModule.initialize(moduleEntry);
            this.getZpNetwork().register(moduleEntry.getPacketDataSet());
            this.getZpRegistryConveyor().launch(moduleEntry.getRegistrySet());
            
            moduleEntry.getEventClasses().forEach(e -> {
                try {
                    Method getDistMethod = e.getDeclaredMethod("getSide");
                    Method getBusMethod = e.getDeclaredMethod("getBus");
                    ZPEventClass instance = e.getDeclaredConstructor().newInstance();
                    ZPSide result = (ZPSide) getDistMethod.invoke(instance);
                    Mod.EventBusSubscriber.Bus result2 = (Mod.EventBusSubscriber.Bus) getBusMethod.invoke(instance);
                    this.registerSomeEvents(e, result2, result);
                } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException ex) {
                    throw new ZPRuntimeException(ex);
                }
            });

            moduleEntry.getEventClassObjects().forEach(e -> {
                this.registerSomeEvents(e, e.getBus(), e.getSide());
            });

            if (moduleEntry.getZpLootTablesRegistry() != null) {
                ZPLootTablesRegistry.REG(moduleEntry.getZpLootTablesRegistry());
            }
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
            zpModule.postInitialize();
        }
    }

    private void registerSomeEvents(Object eventClass, Mod.EventBusSubscriber.Bus bus, ZPSide side) {
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
    
    private void readModulesJSON(List<ZPModule> assets) {
        String jsonRaw = null;
        try {
            jsonRaw = ZPUtility.files().readTextFromJar(new ZPPath(ZombiePlague3.assetsJsonPath));
        } catch (IOException e) {
            throw new ZPIOException(e);
        }

        JsonObject jsonObject = GsonHelper.parse(jsonRaw);
        JsonArray jsonElements = jsonObject.getAsJsonArray("modules");
        for (int i = 0; i < jsonElements.size(); i++) {
            JsonObject asset = jsonElements.get(i).getAsJsonObject();

            try {
                final String pathToClass = asset.get("class").getAsString();
                Class<?> zpAssetClass = Class.forName(pathToClass);
                try {
                    final String name = asset.get("name").getAsString();
                    ZPModule obj = (ZPModule) zpAssetClass.getDeclaredConstructor(ZPModuleData.class).newInstance(new ZPModuleData(name));
                    assets.add(obj);
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
        ZPLogger.info("Client resources setup");
        Runtime.getRuntime().addShutdownHook(new Thread(this::clientShutDown));
        RenderSystem.recordRenderCall(() -> {
            ZPSystemInit.client();
            ZPDefaultShaders.init();
            for (ZPModule zpModule : this.assets) {
                zpModule.fml_clientSetupEvent();
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
    }

    private void clientShutDown() {
        ZPLogger.info("Client resources destroy");
        RenderSystem.recordRenderCall(() -> {
            for (ZPModule zpModule : this.assets) {
                zpModule.clientShutDown();
            }
            ZPSystemInit.clientRunDestroy(Minecraft.getInstance().getWindow());
            ZPRegistryCollections.clearAll();
        });
    }

    private void completeSetup(final FMLLoadCompleteEvent event) {
        this.getZpRegistryConveyor().launchLaterList();
    }

    private void fml_commonSetupEvent(final FMLCommonSetupEvent event) {
        ZPRegistry.execLaterConsumers();
        for (ZPModule zpModule : this.assets) {
            zpModule.fml_commonSetupEvent();
        }
        this.initDispenserData();

        {
            ZombiePlague3.commonInitSwitch = false;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static ZPNetSyncDataPack getClient_netSyncDataPack() {
        if (ZombiePlague3.client_netSyncDataPack == null) {
            ZombiePlague3.client_netSyncDataPack = ZombiePlague3.net().createdNetSyncDataPack_StoC();
        }
        return ZombiePlague3.client_netSyncDataPack;
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
        for (Map.Entry<RegistryObject<? extends Item>, ZPDispenseProjectileHelper.ProjectileData> entry : ZPDispenseProjectileHelper.getDispenserMap().entrySet()) {
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
    }

    public static ZPPopulationController getPopulationController() {
        return ZombiePlague3.populationController;
    }

    public static ZPRecipesController getRecipesController() {
        return recipesController;
    }

    public static ZPNetworkHandler net() {
        return ZPNetworkHandler.instance;
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
        return "0.2a";
    }

    public interface IMixinEntry {
        void addMixinConfigData(@NotNull MixinConfig mixinConfig, @NotNull MixinClass... classes);

        record MixinClass(@NotNull String name, @NotNull ZPSide side) { ; }
        record MixinConfig(@NotNull String name, @NotNull String packagePath) { ; }
    }

    public interface IModuleEntry {
        void addRegistryClass(@NotNull Class<? extends ZPRegistry<?>> zpRegistryProcessorClass);
        void addEventClass(@NotNull Class<? extends ZPEventClass> clazz);
        void addEventClassObject(@NotNull ZPEventClass object);
        void addNetworkPacket(@NotNull ZPNetwork.PacketData<?> packetData);
        void registerNetSyncedConfigData_ClientToServer(@NotNull ZPNetworkHandler.NetSyncDataFabric.Builder zpNetSyncDataPackBuilder);
        void registerNetSyncedConfigData_ServerToClient(@NotNull ZPNetworkHandler.NetSyncDataFabric.Builder zpNetSyncDataPackBuilder);
        void setLootTablesRegistry(@NotNull ZPLootTablesRegistry object);
        void addRecipesRegistry(@NotNull ZPRecipesRegistry... recipesRegistries);
        void setPopulationSetup(@NotNull ZPSetupPopulation setup);
        void addTier(@NotNull ZPTier[] tier);

        default void registerTier(@NotNull ZPTierData tier) {
            ZombiePlague3.registerTier(tier);
        }
    }

    public static class ModuleEntry implements IModuleEntry {
        private final Set<Class<? extends ZPRegistry<?>>> registrySet;
        private final Set<Class<? extends ZPEventClass>> eventClasses;
        private final Set<ZPEventClass> eventClassObjects;
        private final List<ZPNetwork.PacketData<?>> packetDataSet;
        private @Nullable ZPLootTablesRegistry zpLootTablesRegistry;

        public ModuleEntry() {
            this.registrySet = new HashSet<>();
            this.eventClasses = new HashSet<>();
            this.eventClassObjects = new HashSet<>();
            this.packetDataSet = new ArrayList<>();
            this.zpLootTablesRegistry = null;
        }

        @Override
        public final void addRegistryClass(@NotNull Class<? extends ZPRegistry<?>> zpRegistryClass) {
            this.getRegistrySet().add(zpRegistryClass);
        }

        @Override
        public final void addEventClass(@NotNull Class<? extends ZPEventClass> clazz) {
            this.getEventClasses().add(clazz);
        }

        @Override
        public final void addEventClassObject(@NotNull ZPEventClass object) {
            this.getEventClassObjects().add(object);
        }

        @Override
        public void addNetworkPacket(ZPNetwork.@NotNull PacketData<?> packetData) {
            this.getPacketDataSet().add(packetData);
        }

        @Override
        public void registerNetSyncedConfigData_ClientToServer(@NotNull ZPNetworkHandler.NetSyncDataFabric.Builder zpNetSyncDataPackBuilder) {
            ZPNetworkHandler.instance.registerSyncedData__Client_to_Server(zpNetSyncDataPackBuilder);
        }

        @Override
        public void registerNetSyncedConfigData_ServerToClient(@NotNull ZPNetworkHandler.NetSyncDataFabric.Builder zpNetSyncDataPackBuilder) {
            ZPNetworkHandler.instance.registerSyncedData__Server_to_Client(zpNetSyncDataPackBuilder);
        }

        @Override
        public void setLootTablesRegistry(@NotNull ZPLootTablesRegistry object) {
            this.zpLootTablesRegistry = object;
        }

        @Override
        public void addRecipesRegistry(ZPRecipesRegistry... recipesRegistries) {
            ZombiePlague3.recipesController.getRegistries().addAll(List.of(recipesRegistries));
        }

        @Override
        public void setPopulationSetup(@NotNull ZPSetupPopulation setup) {
            setup.setup(ZombiePlague3.getPopulationController());
        }

        @Override
        public void addTier(ZPTier[] tier) {
            ZPTiersRegistryHelper.addToRegister(tier);
        }

        public @Nullable ZPLootTablesRegistry getZpLootTablesRegistry() {
            return this.zpLootTablesRegistry;
        }

        public List<ZPNetwork.PacketData<?>> getPacketDataSet() {
            return this.packetDataSet;
        }

        public Set<Class<? extends ZPRegistry<?>>> getRegistrySet() {
            return this.registrySet;
        }

        public Set<Class<? extends ZPEventClass>> getEventClasses() {
            return this.eventClasses;
        }

        public Set<ZPEventClass> getEventClassObjects() {
            return this.eventClassObjects;
        }
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