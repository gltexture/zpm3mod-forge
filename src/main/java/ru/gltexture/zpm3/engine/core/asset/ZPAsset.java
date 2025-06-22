package ru.gltexture.zpm3.engine.core.asset;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;

public abstract class ZPAsset {
    private final ZPAssetData zpAssetData;

    public ZPAsset(@NotNull ZPAssetData zpAssetData) {
        this.zpAssetData = zpAssetData;
    }

    public abstract void commonSetup();

    public abstract void initAsset(@NotNull ZombiePlague3.IAssetEntry assetEntry);

    public ZPAssetData getZpAssetData() {
        return this.zpAssetData;
    }

    @Override
    public String toString() {
        return "ZPAsset: " + this.getZpAssetData();
    }
}
