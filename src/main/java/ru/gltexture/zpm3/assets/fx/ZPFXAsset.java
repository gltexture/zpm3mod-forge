package ru.gltexture.zpm3.assets.fx;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
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

    @OnlyIn(Dist.CLIENT)
    @Override
    public void clientSetup() {

    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void clientDestroy() {

    }

    @Override
    public void initMixins(ZombiePlague3.@NotNull IMixinEntry mixinEntry) {

    }

    @Override
    public void initializeAsset(ZombiePlague3.@NotNull IAssetEntry assetEntry) {
        assetEntry.addZP3RegistryClass(ZPParticles.class);
    }

    @Override
    public void preCommonInitializeAsset() {

    }

    @Override
    public void postCommonInitializeAsset() {

    }
}
