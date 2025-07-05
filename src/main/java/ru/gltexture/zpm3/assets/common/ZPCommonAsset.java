package ru.gltexture.zpm3.assets.common;

import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.init.*;
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

    @Override
    public void initMixins(ZombiePlague3.@NotNull IMixinEntry mixinEntry) {
        mixinEntry.addMixinConfigName("common");
        mixinEntry.addMixinInAssetHelperPlugin("server.TorchPlaceMixin");
        mixinEntry.addMixinInAssetHelperPlugin("server.WallBlockPlaceMixin");
        mixinEntry.addMixinInAssetHelperPlugin("server.PumpkinPlaceMixin");
    }

    @Override
    public void initAsset(ZombiePlague3.@NotNull IAssetEntry assetEntry) {
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
