package ru.gltexture.zpm3.assets.common;

import ru.gltexture.zpm3.assets.common.events.client.ZPGatherDataEvent;
import ru.gltexture.zpm3.assets.common.init.ZPItems;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.asset.ZPAsset;
import ru.gltexture.zpm3.engine.core.asset.ZPAssetData;

public class ZPCommonAsset extends ZPAsset {
    public ZPCommonAsset(ZPAssetData zpAssetData) {
        super(zpAssetData);
    }

    @Override
    public void initAsset(ZombiePlague3.IAssetEntry assetEntry) {
        assetEntry.addRegistryClass(ZPItems.class);
        assetEntry.addEventClass(ZPGatherDataEvent.class);
        //assetEntry.addPackageWithEvents("ru.gltexture.zpm3.assets.common.events.server");
    }
}
