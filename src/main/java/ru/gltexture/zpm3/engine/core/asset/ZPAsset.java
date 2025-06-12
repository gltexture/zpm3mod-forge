package ru.gltexture.zpm3.engine.core.asset;

import ru.gltexture.zpm3.engine.core.ZombiePlague3;

public abstract class ZPAsset {
    private final ZPAssetData zpAssetData;

    public ZPAsset(ZPAssetData zpAssetData) {
        this.zpAssetData = zpAssetData;
    }

    public abstract void initAsset(ZombiePlague3.IAssetEntry assetEntry);

    public ZPAssetData getZpAssetData() {
        return this.zpAssetData;
    }

    @Override
    public String toString() {
        return "ZPAsset: " + this.getZpAssetData();
    }
}
