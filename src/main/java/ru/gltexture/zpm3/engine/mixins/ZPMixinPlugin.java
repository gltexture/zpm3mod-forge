package ru.gltexture.zpm3.engine.mixins;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.util.GsonHelper;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import ru.gltexture.zpm3.engine.core.ZPLogger;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.asset.ZPAsset;
import ru.gltexture.zpm3.engine.exceptions.ZPIOException;
import ru.gltexture.zpm3.engine.exceptions.ZPRuntimeException;
import ru.gltexture.zpm3.engine.service.ZPPath;
import ru.gltexture.zpm3.engine.service.ZPUtility;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ZPMixinPlugin implements IMixinConfigPlugin {
    public static final String pathToMixinsCfg = "assets/zpm3/mixins/";

    private static final List<String> mixins = new ArrayList<>();

    private static void readAssetsJSON(List<ZPAsset> assets) {
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
                    @SuppressWarnings("unchecked")
                    Constructor<ZPAsset> constructor = (Constructor<ZPAsset>) zpAssetClass.getDeclaredConstructor();
                    constructor.setAccessible(true);
                    ZPAsset obj = constructor.newInstance();
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

    private void registerMixinConfigs() {
        List<ZPAsset> assets = new ArrayList<>();
        ZPMixinPlugin.readAssetsJSON(assets);
        for (ZPAsset zpAsset : assets) {
            zpAsset.initMixins(ZPMixinPlugin.mixins::add);
        }
    }

    @Override
    public void onLoad(String mixinPackage) {
        this.registerMixinConfigs();

        ZPMixinPlugin.mixins.forEach(e -> {
            final String path = ZPMixinPlugin.pathToMixinsCfg + e + ".json";
            try (InputStream ignored = this.getClass().getResourceAsStream(path)) {
                ZPLogger.info("Got mixin config: " + path);
            } catch (IOException ex) {
                throw new ZPRuntimeException(ex);
            }
            Mixins.addConfiguration(path);
        });
    }

    @Override
    public String getRefMapperConfig() {
        return "zpm3.refmap.json";
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return List.of();
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }
}