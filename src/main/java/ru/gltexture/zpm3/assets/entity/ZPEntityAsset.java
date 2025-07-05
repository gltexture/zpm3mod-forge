package ru.gltexture.zpm3.assets.entity;

import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.entity.events.both.ZPEntitySpawnEvent;
import ru.gltexture.zpm3.assets.entity.events.both.ZPEntityTickEvent;
import ru.gltexture.zpm3.assets.entity.logic.EntityBothSidesLogic;
import ru.gltexture.zpm3.assets.entity.logic.EntityClientSideLogic;
import ru.gltexture.zpm3.assets.entity.logic.EntityServerSideLogic;
import ru.gltexture.zpm3.assets.entity.logic.EntityTickEventLogic;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.asset.ZPAsset;
import ru.gltexture.zpm3.engine.core.asset.ZPAssetData;

public class ZPEntityAsset extends ZPAsset {
    public ZPEntityAsset(@NotNull ZPAssetData zpAssetData) {
        super(zpAssetData);
    }

    public ZPEntityAsset() {
    }

    public static final @NotNull EntityTickEventLogic bothSidesLogic = new EntityBothSidesLogic();
    public static final @NotNull EntityTickEventLogic clientSideLogic = new EntityClientSideLogic();
    public static final @NotNull EntityTickEventLogic serverSideLogic = new EntityServerSideLogic();

    @Override
    public void commonSetup() {

    }

    @Override
    public void initMixins(ZombiePlague3.@NotNull IMixinEntry mixinEntry) {

    }

    @Override
    public void initAsset(ZombiePlague3.@NotNull IAssetEntry assetEntry) {
        assetEntry.addEventClass(ZPEntitySpawnEvent.class);
        assetEntry.addEventClass(ZPEntityTickEvent.class);
    }
}
