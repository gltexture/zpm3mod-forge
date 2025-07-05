package ru.gltexture.zpm3.engine.core.asset;

import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;

public abstract class ZPAsset {
    private final ZPAssetData zpAssetData;

    public ZPAsset(@NotNull ZPAssetData zpAssetData) {
        this.zpAssetData = zpAssetData;
    }

    protected ZPAsset() {
        this.zpAssetData = null;
    }

    public abstract void commonSetup();
    public abstract void initMixins(@NotNull ZombiePlague3.IMixinEntry mixinEntry);
    public abstract void initAsset(@NotNull ZombiePlague3.IAssetEntry assetEntry);

    public ZPAssetData getZpAssetData() {
        return this.zpAssetData;
    }

    @Override
    public String toString() {
        return "ZPAsset: " + this.getZpAssetData();
    }
}
