package ru.gltexture.zpm3.engine.core;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
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
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.TierSortingRegistry;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.gltexture.zpm3.engine.client.init.ZPClientInitManager;
import ru.gltexture.zpm3.engine.client.rendering.shaders.ZPDefaultShaders;
import ru.gltexture.zpm3.engine.core.asset.ZPAsset;
import ru.gltexture.zpm3.engine.core.asset.ZPAssetData;
import ru.gltexture.zpm3.engine.events.both.ZPBothForge;
import ru.gltexture.zpm3.engine.events.both.ZPBothMod;
import ru.gltexture.zpm3.engine.events.client.ZPClientForge;
import ru.gltexture.zpm3.engine.events.client.ZPClientMod;
import ru.gltexture.zpm3.engine.events.server.ZPServerForge;
import ru.gltexture.zpm3.engine.events.server.ZPServerMod;
import ru.gltexture.zpm3.engine.exceptions.ZPIOException;
import ru.gltexture.zpm3.engine.exceptions.ZPRuntimeException;
import ru.gltexture.zpm3.engine.events.ZPEvent;
import ru.gltexture.zpm3.engine.helpers.ZPDispenserHelper;
import ru.gltexture.zpm3.engine.network.ZPNetwork;
import ru.gltexture.zpm3.engine.objects.items.tier.ZPTierData;
import ru.gltexture.zpm3.engine.objects.items.tier.ZPTiers;
import ru.gltexture.zpm3.engine.registry.ZPRegistry;
import ru.gltexture.zpm3.engine.registry.ZPRegistryCollections;
import ru.gltexture.zpm3.engine.service.ZPPath;
import ru.gltexture.zpm3.engine.service.ZPUtility;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@Mod(ZombiePlague3.MOD_ID)
public final class ZombiePlague3 {
    public static final String assetsJsonPath = "zpm3.asset.json";
    public static final String MOD_ID = "zpm3";
    static final Logger LOGGER = LoggerFactory.getLogger(ZombiePlague3.MOD_ID);
    private static final ZPProject MOD_INFO = new ZPProject("ZombiePlague3Engine", ZombiePlague3.MOD_ID, "In Development");
    private final ZPRegistryConveyor zpRegistryConveyor;
    private final List<ZPAsset> assets;
    private ZPNetwork zpNetwork;

    public ZombiePlague3() {
        this.assets = new ArrayList<>();
        this.zpRegistryConveyor = new ZPRegistryConveyor();
        this.init();
    }

    @SuppressWarnings("all")
    private static IEventBus getModEventBus() {
        return FMLJavaModLoadingContext.get().getModEventBus();
    }

    private void createNet() {
        ZPLogger.info(this + " INIT-NETWORK");
        this.zpNetwork = new ZPNetwork();
        ZombiePlague3.net().setNetwork(this.zpNetwork);
    }

    private void init() {
        ZPLogger.info(this + " INIT");
        this.initDefaultTiers();
        final IEventBus modEventBus = ZombiePlague3.getModEventBus();
        this.createNet();
        this.initAssets();
        modEventBus.addListener(this::commonSetup);
        ZPUtility.sides().onlyClient(() -> {
            modEventBus.addListener(this::clientSetup);
        });
        ZPLogger.info(this + " END INIT");
    }

    private void initDefaultTiers() {
        ZPLogger.info(this + " INIT DEFAULT ZP TIERS");
        Arrays.stream(ZPTiers.values()).forEach(e -> e.init().forEach(ZombiePlague3::registerTier));
    }

    private void initAssets() {
        ZPLogger.info(this + " Assets setup");
        this.readAssetsJSON(this.assets);

        final Set<ZPEvent<?>> clientEvents = new HashSet<>();
        final Set<ZPEvent<?>> serverEvents = new HashSet<>();

        for (ZPAsset zpAsset : this.assets) {
            ZPLogger.info("Init asset: " + zpAsset);
            AssetEntry assetEntry = new AssetEntry();
            zpAsset.initAsset(assetEntry);
            this.getZpNetwork().register(assetEntry.getPacketDataSet());
            this.getZpRegistryConveyor().launch(assetEntry.getRegistrySet());

            for (Class<? extends ZPEvent<? extends Event>> clazz : assetEntry.getEventClasses()) {
                try {
                    Method getDistMethod = clazz.getDeclaredMethod("getSide");
                    ZPEvent<?> instance = clazz.getDeclaredConstructor().newInstance();
                    ZPSide result = (ZPSide) getDistMethod.invoke(instance);
                    switch (result) {
                        case CLIENT -> clientEvents.add(instance);
                        case SERVER -> serverEvents.add(instance);
                        case BOTH -> {
                            clientEvents.add(instance);
                            serverEvents.add(instance);
                        }
                    }
                } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                    throw new ZPRuntimeException(e);
                }
            }
        }

        ZPUtility.sides().onlyClient(() -> {
            final ZPClientMod zpClientMod = new ZPClientMod();
            final ZPClientForge zpClientForge = new ZPClientForge();
            clientEvents.forEach(e -> {
                switch (e.getBus()) {
                    case MOD -> zpClientMod.addNew(e.getEventType(), e);
                    case FORGE -> zpClientForge.addNew(e.getEventType(), e);
                }
            });
            MinecraftForge.EVENT_BUS.register(zpClientMod);
            MinecraftForge.EVENT_BUS.register(zpClientForge);
        });

        ZPUtility.sides().onlyServer(() -> {
            final ZPServerMod zpServerMod = new ZPServerMod();
            final ZPServerForge zpServerForge = new ZPServerForge();
            serverEvents.forEach(e -> {
                switch (e.getBus()) {
                    case MOD -> zpServerMod.addNew(e.getEventType(), e);
                    case FORGE -> zpServerForge.addNew(e.getEventType(), e);
                }
            });
            MinecraftForge.EVENT_BUS.register(zpServerMod);
            MinecraftForge.EVENT_BUS.register(zpServerForge);
        });

        MinecraftForge.EVENT_BUS.register(new ZPBothMod());
        MinecraftForge.EVENT_BUS.register(new ZPBothForge());
    }

    private void readAssetsJSON(List<ZPAsset> assets) {
        String jsonRaw = null;
        try {
            jsonRaw = ZPUtility.files().readTextFromJar(new ZPPath(ZombiePlague3.assetsJsonPath));
        } catch (IOException e) {
            throw new ZPIOException(e);
        }

        JsonObject jsonObject = GsonHelper.parse(jsonRaw);
        JsonArray jsonElements = jsonObject.getAsJsonArray("assets");
        for (int i = 0; i < jsonElements.size(); i++) {
            JsonObject asset = jsonElements.get(i).getAsJsonObject();

            try {
                final String pathToClass = asset.get("class").getAsString();
                Class<?> zpAssetClass = Class.forName(pathToClass);
                try {
                    final String name = asset.get("name").getAsString();
                    final String version = asset.get("version").getAsString();
                    ZPAsset obj = (ZPAsset) zpAssetClass.getDeclaredConstructor(ZPAssetData.class).newInstance(new ZPAssetData(name, version));
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

    public static void registerTier(@NotNull ZPTierData tier) {
        TierSortingRegistry.registerTier(tier.tier(), ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), tier.name()), tier.after(), tier.before());
    }

    public static void registerDeferred(DeferredRegister<?> deferredRegister) {
        deferredRegister.register(ZombiePlague3.getModEventBus());
    }

    @OnlyIn(Dist.CLIENT)
    private void clientSetup(final FMLClientSetupEvent event) {
        ZPLogger.info("Client resources setup");
        Runtime.getRuntime().addShutdownHook(new Thread(this::clientDestroy));
        RenderSystem.recordRenderCall(() -> {
            ZPDefaultShaders.init();
            ZPDefaultSysInit.initZPSystems();
            for (ZPAsset zpAsset : this.assets) {
                zpAsset.clientSetup();
            }
            ZPClientInitManager.getSetInit().forEach(e -> e.run(Minecraft.getInstance().getWindow()));
            ZPClientInitManager.clearInit();
        });
    }

    private void clientDestroy() {
        ZPLogger.info("Client resources destroy");
        RenderSystem.recordRenderCall(() -> {
            ZPDefaultSysInit.destroyZPSystems();
            for (ZPAsset zpAsset : this.assets) {
                zpAsset.clientDestroy();
            }
            ZPClientInitManager.getSetDestroy().forEach(e -> e.run(Minecraft.getInstance().getWindow()));
            ZPClientInitManager.clearDestroy();
            ZPRegistryCollections.clearAll();
        });
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        ZPDefaultSysInit.destroyZPSystems();
        for (ZPAsset zpAsset : this.assets) {
            zpAsset.commonSetup();
        }
        this.initDispenserData();
    }

    private void initDispenserData() {
        ZPLogger.info(this + " Init dispensers data");
        for (Map.Entry<RegistryObject<? extends Item>, ZPDispenserHelper.ProjectileData> entry : ZPDispenserHelper.getDispenserMap().entrySet()) {
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

    public static ZPNetworkHandler net() {
        return ZPNetworkHandler.instance;
    }

    public ZPNetwork getZpNetwork() {
        return this.zpNetwork;
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

    public interface IMixinEntry {
        void addMixinConfigData(@NotNull MixinConfig mixinConfig, @NotNull MixinClass... classes);

        record MixinClass(@NotNull String name, @NotNull ZPSide side) { ; }
        record MixinConfig(@NotNull String name, @NotNull String packagePath) { ; }
    }

    public interface IAssetEntry {
        void addRegistryClass(Class<? extends ZPRegistry<?>> zpRegistryProcessorClass);
        void addEventClass(Class<? extends ZPEvent<? extends Event>> clazz);
        void addNetworkPacket(ZPNetwork.PacketData<?> packetData);

        default void registerTier(@NotNull ZPTierData tier) {
            ZombiePlague3.registerTier(tier);
        }
    }

    public static class AssetEntry implements IAssetEntry {
        private final Set<Class<? extends ZPRegistry<?>>> registrySet;
        private final Set<Class<? extends ZPEvent<? extends Event>>> eventClasses;
        private final Set<ZPNetwork.PacketData<?>> packetDataSet;

        public AssetEntry() {
            this.registrySet = new HashSet<>();
            this.eventClasses = new HashSet<>();
            this.packetDataSet = new HashSet<>();
        }

        @Override
        public final void addRegistryClass(@NotNull Class<? extends ZPRegistry<?>> zpRegistryClass) {
            this.getRegistrySet().add(zpRegistryClass);
        }

        @Override
        public final void addEventClass(@NotNull Class<? extends ZPEvent<? extends Event>> clazz) {
            this.getEventClasses().add(clazz);
        }

        @Override
        public void addNetworkPacket(ZPNetwork.PacketData<?> packetData) {
            this.getPacketDataSet().add(packetData);
        }

        public Set<ZPNetwork.PacketData<?>> getPacketDataSet() {
            return this.packetDataSet;
        }

        public Set<Class<? extends ZPRegistry<?>>> getRegistrySet() {
            return this.registrySet;
        }

        public Set<Class<? extends ZPEvent<? extends Event>>> getEventClasses() {
            return this.eventClasses;
        }
    }
}


/*
                Reflections reflections = new Reflections(pack.getName());
                @SuppressWarnings("rawtypes")
                Set<Class<? extends ZPEvent>> classes = reflections.getSubTypesOf(ZPEvent.class);

                for (@SuppressWarnings("rawtypes") Class<? extends ZPEvent> clazz : classes) {
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