package ru.gltexture.zpm3.assets.common;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.init.*;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.asset.ZPAsset;
import ru.gltexture.zpm3.engine.core.asset.ZPAssetData;
import ru.gltexture.zpm3.engine.service.ZPUtility;

public class ZPCommonAsset extends ZPAsset {
    public ZPCommonAsset(@NotNull ZPAssetData zpAssetData) {
        super(zpAssetData);
    }

    public ZPCommonAsset() {
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
        mixinEntry.addMixinConfigData(new ZombiePlague3.IMixinEntry.MixinConfig("common", "ru.gltexture.zpm3.assets.common.mixins.impl"),
                new ZombiePlague3.IMixinEntry.MixinClass("server.TorchPlaceMixin", ZPSide.BOTH),
                new ZombiePlague3.IMixinEntry.MixinClass("server.WallTorchPlaceMixin", ZPSide.BOTH),
                new ZombiePlague3.IMixinEntry.MixinClass("server.PumpkinPlaceMixin", ZPSide.BOTH));
    }

    @Override
    public void initAsset(ZombiePlague3.@NotNull IAssetEntry assetEntry) {
        assetEntry.addRegistryClass(ZPSounds.class);
        assetEntry.addRegistryClass(ZPItems.class);
        assetEntry.addRegistryClass(ZPBlockItems.class);
        assetEntry.addRegistryClass(ZPCommonBlocks.class);
        assetEntry.addRegistryClass(ZPTorchBlocks.class);
        assetEntry.addRegistryClass(ZPEntities.class);
        assetEntry.addRegistryClass(ZPBlockEntities.class);

        ZPUtility.sides().onlyClient(() -> {
            assetEntry.addRegistryClass(ZPTabs.class);
        });
    }
}
