package ru.gltexture.zpm3.engine.core;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.gltexture.zpm3.engine.core.asset.ZPAsset;
import ru.gltexture.zpm3.engine.core.asset.ZPAssetData;
import ru.gltexture.zpm3.engine.events.client.ZPClientMod;
import ru.gltexture.zpm3.engine.events.server.ZPServerMod;
import ru.gltexture.zpm3.engine.exceptions.ZPIOException;
import ru.gltexture.zpm3.engine.exceptions.ZPRuntimeException;
import ru.gltexture.zpm3.engine.events.ZPEvent;
import ru.gltexture.zpm3.engine.registry.base.ZPRegistry;
import ru.gltexture.zpm3.engine.utils.ZPUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@Mod(ZombiePlague3.MOD_ID)
public final class ZombiePlague3 {
    public static final String MOD_ID = "zpm3";
    static final Logger LOGGER = LoggerFactory.getLogger(ZombiePlague3.MOD_ID);
    private static final Project MOD_INFO = new Project("ZombiePlague3Engine", ZombiePlague3.MOD_ID, "In Development");
    private final ZPRegistryConveyor zpRegistryConveyor;
    private final List<ZPAsset> assets;

    public ZombiePlague3() {
        this.assets = new ArrayList<>();
        this.zpRegistryConveyor = new ZPRegistryConveyor();
        this.init();
    }

    private void init() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
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
            this.getZpRegistryConveyor().launch(assetEntry.getRegistrySet());

            for (Class<? extends ZPEvent<? extends Event>> clazz : assetEntry.getEventClasses()) {
                try {
                    Method getDistMethod = clazz.getDeclaredMethod("getDist");
                    ZPEvent<?> instance = clazz.getDeclaredConstructor().newInstance();
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
        }

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            final ZPClientMod zpClientMod = new ZPClientMod();
            clientEvents.forEach(e -> zpClientMod.addNew(e.getEventType(), e));
            MinecraftForge.EVENT_BUS.register(zpClientMod);
        });

        DistExecutor.unsafeRunWhenOn(Dist.DEDICATED_SERVER, () -> () -> {
            final ZPServerMod zpServerMod = new ZPServerMod();
            serverEvents.forEach(e -> zpServerMod.addNew(e.getEventType(), e));
            MinecraftForge.EVENT_BUS.register(zpServerMod);
        });
    }

    private void readAssetsJSON(List<ZPAsset> assets) {
        String jsonRaw = null;
        try {
            jsonRaw = ZPUtils.readTextFromJar("zpm3.asset.json");
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

    private void commonSetup(final FMLCommonSetupEvent event) {
        ZPLogger.info(this + " Common setup");
        this.initAssets();
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

    public interface IAssetEntry {
        void addRegistryClass(Class<? extends ZPRegistry<?>> zpRegistryProcessorClass);
        void addEventClass(Class<? extends ZPEvent<? extends Event>> clazz);
    }

    public static class AssetEntry implements IAssetEntry {
        private final Set<Class<? extends ZPRegistry<?>>> registrySet;
        private final Set<Class<? extends ZPEvent<? extends Event>>> eventClasses;

        public AssetEntry() {
            this.registrySet = new HashSet<>();
            this.eventClasses = new HashSet<>();
        }

        @Override
        public final void addRegistryClass(@NotNull Class<? extends ZPRegistry<?>> zpRegistryClass) {
            this.getRegistrySet().add(zpRegistryClass);
        }

        @Override
        public final void addEventClass(@NotNull Class<? extends ZPEvent<? extends Event>> clazz) {
            this.getEventClasses().add(clazz);
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