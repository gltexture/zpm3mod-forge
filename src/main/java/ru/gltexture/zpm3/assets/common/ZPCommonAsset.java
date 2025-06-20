package ru.gltexture.zpm3.assets.common;

import ru.gltexture.zpm3.assets.common.events.client.ZPGatherDataEvent;
import ru.gltexture.zpm3.assets.common.init.*;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.asset.ZPAsset;
import ru.gltexture.zpm3.engine.core.asset.ZPAssetData;

public class ZPCommonAsset extends ZPAsset {
    public ZPCommonAsset(ZPAssetData zpAssetData) {
        super(zpAssetData);
    }

    @Override
    public void commonSetup() {

    }

    @Override
    public void initAsset(ZombiePlague3.IAssetEntry assetEntry) {
        assetEntry.addRegistryClass(ZPItems.class);
        assetEntry.addRegistryClass(ZPBlockItems.class);
        assetEntry.addRegistryClass(ZPBlocks.class);
        assetEntry.addRegistryClass(ZPTabs.class);
        assetEntry.addRegistryClass(ZPEntities.class);
        assetEntry.addEventClass(ZPGatherDataEvent.class);
    }
}
