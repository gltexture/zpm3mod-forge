package ru.gltexture.zpm3.assets.common;

import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.events.client.ZPGatherDataEvent;
import ru.gltexture.zpm3.assets.common.init.*;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.asset.ZPAsset;
import ru.gltexture.zpm3.engine.core.asset.ZPAssetData;
import ru.gltexture.zpm3.engine.service.ZPUtility;

public class ZPCommonAsset extends ZPAsset {
    public ZPCommonAsset(ZPAssetData zpAssetData) {
        super(zpAssetData);
    }

    @Override
    public void commonSetup() {

    }

    @Override
    public void initAsset(ZombiePlague3.@NotNull IAssetEntry assetEntry) {
        assetEntry.addRegistryClass(ZPItems.class);
        assetEntry.addRegistryClass(ZPBlockItems.class);
        assetEntry.addRegistryClass(ZPCommonBlocks.class);
        assetEntry.addRegistryClass(ZPEntities.class);

        ZPUtility.sides().onlyClient(() -> {
            assetEntry.addRegistryClass(ZPTabs.class);
        });

        assetEntry.addEventClass(ZPGatherDataEvent.class);
    }
}
