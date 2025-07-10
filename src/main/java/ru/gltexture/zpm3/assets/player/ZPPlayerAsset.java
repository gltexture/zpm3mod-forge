package ru.gltexture.zpm3.assets.player;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.player.events.client.ZPClientJoinEvent;
import ru.gltexture.zpm3.assets.player.events.server.ZPPlayerLoggedInEvent;
import ru.gltexture.zpm3.assets.player.events.both.ZPPlayerTickEvent;
import ru.gltexture.zpm3.assets.player.logic.PlayerBothSidesLogic;
import ru.gltexture.zpm3.assets.player.logic.PlayerClientSideLogic;
import ru.gltexture.zpm3.assets.player.logic.PlayerServerSideLogic;
import ru.gltexture.zpm3.assets.player.logic.PlayerTickEventLogic;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.asset.ZPAsset;
import ru.gltexture.zpm3.engine.core.asset.ZPAssetData;

public class ZPPlayerAsset extends ZPAsset {
    public ZPPlayerAsset(@NotNull ZPAssetData zpAssetData) {
        super(zpAssetData);
    }

    public ZPPlayerAsset() {
    }

    public static final @NotNull PlayerTickEventLogic bothSidesLogic = new PlayerBothSidesLogic();
    public static final @NotNull PlayerTickEventLogic clientSideLogic = new PlayerClientSideLogic();
    public static final @NotNull PlayerTickEventLogic serverSideLogic = new PlayerServerSideLogic();

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
        mixinEntry.addMixinConfigData(new ZombiePlague3.IMixinEntry.MixinConfig("player", "ru.gltexture.zpm3.assets.player.mixins.impl"),
                new ZombiePlague3.IMixinEntry.MixinClass("server.SPlayerFeaturesMixin", ZPSide.SERVER),
                new ZombiePlague3.IMixinEntry.MixinClass("client.CPlayerFeaturesMixin", ZPSide.CLIENT),
                new ZombiePlague3.IMixinEntry.MixinClass("client.PlayerItemMixin", ZPSide.CLIENT));
    }

    @Override
    public void initAsset(ZombiePlague3.@NotNull IAssetEntry assetEntry) {
        assetEntry.addEventClass(ZPPlayerLoggedInEvent.class);
        assetEntry.addEventClass(ZPPlayerTickEvent.class);
        assetEntry.addEventClass(ZPClientJoinEvent.class);
    }
}
