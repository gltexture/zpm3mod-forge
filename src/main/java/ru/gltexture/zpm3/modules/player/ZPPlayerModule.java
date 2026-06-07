package ru.gltexture.zpm3.modules.player;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.asset.ZPModule;
import ru.gltexture.zpm3.modules.player.events.client.*;
import ru.gltexture.zpm3.modules.player.events.common.*;
import ru.gltexture.zpm3.modules.player.keybind.ZPPickUpKeyBindings;
import ru.gltexture.zpm3.modules.player.events.server.ZPPlayerFillBucketEvent;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.asset.ZPModuleData;
import ru.gltexture.zpm3.engine.service.ZPUtility;

public class ZPPlayerModule extends ZPModule {
    public ZPPlayerModule(@NotNull ZPModuleData zpModuleData) {
        super(zpModuleData);
    }

    public ZPPlayerModule() {
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
    //    mixinEntry.addMixinConfigData(new ZombiePlague3.IMixinEntry.MixinConfig("player", "ru.gltexture.zpm3.modules.player.mixins.impl"),
    //            new ZombiePlague3.IMixinEntry.MixinClass("common.ZPSPlayerFeaturesMixin", ZPSide.COMMON),
    //            new ZombiePlague3.IMixinEntry.MixinClass("client.ZPCPlayerFeaturesMixin", ZPSide.CLIENT),
    //            new ZombiePlague3.IMixinEntry.MixinClass("client.ZPPlayerItemReanimateMixin", ZPSide.CLIENT));
    //}

    @Override
    public void initializeModule(ZombiePlague3.@NotNull IModuleEntry assetEntry) {
        ZPUtility.sides().onlyClient(() -> {
            assetEntry.addEventClass(ZPRenderWorldEventWithPickUpCheck.class);
            assetEntry.addEventClass(ZPPlayerItemToolTips.class);
            assetEntry.addEventClass(ZPMenuPatchEvent.class);
            assetEntry.addEventClass(ZPRenderGuiEvent.class);
            assetEntry.addEventClass(ZPResourcePackEvent.class);
        });

        assetEntry.addEventClass(ZPPlayerGunCancelInterEvent.class);
        assetEntry.addEventClass(ZPPlayerEntityItemEvent.class);
        assetEntry.addEventClass(ZPPlayerTickEvent.class);
        assetEntry.addEventClass(ZPPlayerFillBucketEvent.class);
        assetEntry.addEventClass(ZPPlaceLiquidEvent.class);
        assetEntry.addEventClass(ZPPlayerEatFoodEvent.class);
        assetEntry.addEventClass(ZPPlayerJoinOrSpawnEvent.class);
    }

    @Override
    public void preCommonInitialize() {
        ZPUtility.sides().onlyClient(() -> {
            ZombiePlague3.registerKeyBindings(new ZPPickUpKeyBindings());
        });
    }

    @Override
    public void postCommonInitialize() {

    }
}
