package ru.gltexture.zpm3.engine.core.asset;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.config.ZPConfigurator;

public abstract class ZPAsset {
    private final ZPAssetData zpAssetData;

    public ZPAsset(@NotNull ZPAssetData zpAssetData) {
        this.zpAssetData = zpAssetData;
    }

    protected ZPAsset() {
        this.zpAssetData = null;
    }

    public abstract void commonSetup();

    @OnlyIn(Dist.CLIENT)
    public abstract void clientSetup();

    @OnlyIn(Dist.CLIENT)
    public abstract void clientDestroy();

    public abstract void initMixins(@NotNull ZombiePlague3.IMixinEntry mixinEntry);
    public abstract void initializeAsset(@NotNull ZombiePlague3.IAssetEntry assetEntry);
    public abstract void preCommonInitializeAsset();

    public ZPAssetData getZpAssetData() {
        return this.zpAssetData;
    }

    @Override
    public String toString() {
        return "ZPAsset: " + this.getZpAssetData();
    }
}
