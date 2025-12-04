package ru.gltexture.zpm3.assets.entity;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.entity.events.common.ZPEntitySpawnEvent;
import ru.gltexture.zpm3.assets.entity.events.common.ZPEntityTickEvent;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.asset.ZPAsset;
import ru.gltexture.zpm3.engine.core.asset.ZPAssetData;

public class ZPEntityAsset extends ZPAsset {
    public ZPEntityAsset(@NotNull ZPAssetData zpAssetData) {
        super(zpAssetData);
    }

    public ZPEntityAsset() {
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
        assetEntry.addEventClass(ZPEntitySpawnEvent.class);
        assetEntry.addEventClass(ZPEntityTickEvent.class);
    }

    @Override
    public void preCommonInitializeAsset() {

    }
}
