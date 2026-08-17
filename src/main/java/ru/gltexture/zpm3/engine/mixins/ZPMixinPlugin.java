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

package ru.gltexture.zpm3.engine.mixins;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class ZPMixinPlugin implements IMixinConfigPlugin {
    public static final String pathToMixinsCfg = "zpm3/mixins/";
    /*

    private static final List<String> mixins = new ArrayList<>();

    private static void readModulesJSON(List<ZPModule> assets) {
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
                final Class<?> zpAssetClass = Class.forName(pathToClass);
                try {
                    @SuppressWarnings("unchecked")
                    Constructor<ZPModule> constructor = (Constructor<ZPModule>) zpAssetClass.getDeclaredConstructor();
                    constructor.setAccessible(true);
                    ZPModule obj = constructor.newInstance();
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

    private static void registerMixinConfigs() {
        List<ZPModule> assets = new ArrayList<>();
        ZPMixinPlugin.readModulesJSON(assets);
        for (ZPModule zpModule : assets) {
            zpModule.setupMixins((mixinConfig, classes) -> {
                ZPMixinPlugin.mixins.add(mixinConfig.name());
                ZPMixinConfigsProvider.addNewMixinData(mixinConfig, classes);
            });
        }
    }

    public static void initLibs() {
        ZPMixinPlugin.registerMixinConfigs();
        if (!ZPUtility.isDataGen()) {
            ZPMixinPlugin.mixins.forEach(e -> {
                final String path = ZPMixinPlugin.pathToMixinsCfg + e + ".json";
                try (InputStream ignored = ZPMixinPlugin.class.getResourceAsStream(path)) {
                    ZPLogger.info("Got mixin config: " + path);
                } catch (IOException ex) {
                    throw new ZPRuntimeException(ex);
                }
                Mixins.addConfiguration(path);
            });
        }
    }

    static {
    }

    @Override
    public void onLoad(String mixinPackage) {
        //ZPMixinPlugin.initLibs();
    }
    */

    @Override
    public void onLoad(String mixinPackage) {

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