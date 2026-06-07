package ru.gltexture.zpm3.engine.core.asset;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;

public abstract class ZPModule {
    private final ZPModuleData zpModuleData;

    public ZPModule(@NotNull ZPModuleData zpModuleData) {
        this.zpModuleData = zpModuleData;
    }

    protected ZPModule() {
        this.zpModuleData = null;
    }

    public abstract void commonSetup();

    @OnlyIn(Dist.CLIENT)
    public abstract void clientSetup();

    @OnlyIn(Dist.CLIENT)
    public abstract void clientDestroy();

    @Deprecated
    public void initMixins(@NotNull ZombiePlague3.IMixinEntry mixinEntry) { }
    public abstract void initializeModule(@NotNull ZombiePlague3.IModuleEntry assetEntry);
    public abstract void preCommonInitialize();
    public abstract void postCommonInitialize();

    public ZPModuleData getZpAssetData() {
        return this.zpModuleData;
    }

    @Override
    public String toString() {
        return "Module: " + this.getZpAssetData();
    }
}
