package ru.gltexture.zpm3.assets.fx;

import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.fx.init.ZPParticles;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.asset.ZPAsset;
import ru.gltexture.zpm3.engine.core.asset.ZPAssetData;

public class ZPFXAsset extends ZPAsset {
    public ZPFXAsset(@NotNull ZPAssetData zpAssetData) {
        super(zpAssetData);
    }

    public ZPFXAsset() {
    }

    @Override
    public void commonSetup() {

    }

    @Override
    public void initMixins(ZombiePlague3.@NotNull IMixinEntry mixinEntry) {

    }

    @Override
    public void initAsset(ZombiePlague3.@NotNull IAssetEntry assetEntry) {
        assetEntry.addRegistryClass(ZPParticles.class);
    }
}
