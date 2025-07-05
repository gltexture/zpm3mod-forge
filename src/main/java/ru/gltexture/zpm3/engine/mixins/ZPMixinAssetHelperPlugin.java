package ru.gltexture.zpm3.engine.mixins;

import net.minecraftforge.fml.loading.FMLEnvironment;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import ru.gltexture.zpm3.engine.service.ZPUtility;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ZPMixinAssetHelperPlugin implements IMixinConfigPlugin {
    private static final List<String> bothSideMixins = new ArrayList<>();
    private static final List<String> clientSideMixins = new ArrayList<>();
    private static final List<String> serverSideMixins = new ArrayList<>();

    @Override
    public void onLoad(String mixinPackage) {
    }

    public static void addBothSidesMixin(@NotNull String name) {
        ZPMixinAssetHelperPlugin.bothSideMixins.add(name);
    }

    public static void addClientSideMixin(@NotNull String name) {
        ZPMixinAssetHelperPlugin.clientSideMixins.add(name);
    }

    public static void addServerSideMixin(@NotNull String name) {
        ZPMixinAssetHelperPlugin.serverSideMixins.add(name);
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
        List<String> all = new ArrayList<>(ZPMixinAssetHelperPlugin.bothSideMixins);
        ZPUtility.sides().onlyClient(() -> {
            all.addAll(ZPMixinAssetHelperPlugin.clientSideMixins);
        });
        ZPUtility.sides().onlyServer(() -> {
            all.addAll(ZPMixinAssetHelperPlugin.serverSideMixins);
        });

        ZPMixinAssetHelperPlugin.bothSideMixins.clear();
        ZPMixinAssetHelperPlugin.clientSideMixins.clear();
        ZPMixinAssetHelperPlugin.serverSideMixins.clear();

        return all;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}
