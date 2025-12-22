package ru.gltexture.zpm3.assets.player;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.player.events.client.ZPMenuPatchEvent;
import ru.gltexture.zpm3.assets.player.events.client.ZPPlayerItemToolTips;
import ru.gltexture.zpm3.assets.player.events.client.ZPRenderGuiEvent;
import ru.gltexture.zpm3.assets.player.events.common.*;
import ru.gltexture.zpm3.assets.player.keybind.ZPPickUpKeyBindings;
import ru.gltexture.zpm3.assets.player.events.client.ZPRenderWorldEventWithPickUpCheck;
import ru.gltexture.zpm3.assets.player.events.server.ZPPlayerFillBucketEvent;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.asset.ZPAsset;
import ru.gltexture.zpm3.engine.core.asset.ZPAssetData;
import ru.gltexture.zpm3.engine.service.ZPUtility;

public class ZPPlayerAsset extends ZPAsset {
    public ZPPlayerAsset(@NotNull ZPAssetData zpAssetData) {
        super(zpAssetData);
    }

    public ZPPlayerAsset() {
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

    //@Override
    //public void initMixins(ZombiePlague3.@NotNull IMixinEntry mixinEntry) {
    //    mixinEntry.addMixinConfigData(new ZombiePlague3.IMixinEntry.MixinConfig("player", "ru.gltexture.zpm3.assets.player.mixins.impl"),
    //            new ZombiePlague3.IMixinEntry.MixinClass("common.PlayerFeaturesMixin", ZPSide.COMMON),
    //            new ZombiePlague3.IMixinEntry.MixinClass("client.CPlayerFeaturesMixin", ZPSide.CLIENT),
    //            new ZombiePlague3.IMixinEntry.MixinClass("client.PlayerItemMixin", ZPSide.CLIENT));
    //}

    @Override
    public void initializeAsset(ZombiePlague3.@NotNull IAssetEntry assetEntry) {
        ZPUtility.sides().onlyClient(() -> {
            assetEntry.addEventClass(ZPRenderWorldEventWithPickUpCheck.class);
            assetEntry.addEventClass(ZPPlayerItemToolTips.class);
            assetEntry.addEventClass(ZPMenuPatchEvent.class);
            assetEntry.addEventClass(ZPRenderGuiEvent.class);
        });

        assetEntry.addEventClass(ZPPlayerEntityItemEvent.class);
        assetEntry.addEventClass(ZPPlayerTickEvent.class);
        assetEntry.addEventClass(ZPPlayerFillBucketEvent.class);
        assetEntry.addEventClass(ZPPlaceLiquidEvent.class);
        assetEntry.addEventClass(ZPPlayerEatFoodEvent.class);
        assetEntry.addEventClass(ZPPlayerJoinOrSpawnEvent.class);
    }

    @Override
    public void preCommonInitializeAsset() {
        ZPUtility.sides().onlyClient(() -> {
            ZombiePlague3.registerKeyBindings(new ZPPickUpKeyBindings());
        });
    }

    @Override
    public void postCommonInitializeAsset() {

    }
}
