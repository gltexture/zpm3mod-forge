package ru.gltexture.zpm3.assets.player;

import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.player.events.client.ZPClientJoinEvent;
import ru.gltexture.zpm3.assets.player.events.server.ZPPlayerLoggedInEvent;
import ru.gltexture.zpm3.assets.player.events.both.ZPPlayerTickEvent;
import ru.gltexture.zpm3.assets.player.logic.PlayerBothSidesLogic;
import ru.gltexture.zpm3.assets.player.logic.PlayerClientSideLogic;
import ru.gltexture.zpm3.assets.player.logic.PlayerServerSideLogic;
import ru.gltexture.zpm3.assets.player.logic.PlayerTickEventLogic;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.asset.ZPAsset;
import ru.gltexture.zpm3.engine.core.asset.ZPAssetData;

public class ZPPlayerAsset extends ZPAsset {
    public static final @NotNull PlayerTickEventLogic bothSidesLogic = new PlayerBothSidesLogic();
    public static final @NotNull PlayerTickEventLogic clientSideLogic = new PlayerClientSideLogic();
    public static final @NotNull PlayerTickEventLogic serverSideLogic = new PlayerServerSideLogic();

    public ZPPlayerAsset(ZPAssetData zpAssetData) {
        super(zpAssetData);
    }

    @Override
    public void commonSetup() {

    }

    @Override
    public void initAsset(ZombiePlague3.@NotNull IAssetEntry assetEntry) {
        assetEntry.addEventClass(ZPPlayerLoggedInEvent.class);
        assetEntry.addEventClass(ZPPlayerTickEvent.class);
        assetEntry.addEventClass(ZPClientJoinEvent.class);
    }
}
