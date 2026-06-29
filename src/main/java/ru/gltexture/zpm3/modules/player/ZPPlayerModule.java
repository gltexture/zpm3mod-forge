package ru.gltexture.zpm3.modules.player;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.module.ZPModule;
import ru.gltexture.zpm3.modules.player.events.client.*;
import ru.gltexture.zpm3.modules.player.events.common.*;
import ru.gltexture.zpm3.modules.player.keybind.ZPPickUpKeyBindings;
import ru.gltexture.zpm3.modules.player.events.server.ZPPlayerFillBucketEvent;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.module.ZPModuleData;
import ru.gltexture.zpm3.engine.service.ZPUtility;

public class ZPPlayerModule extends ZPModule {
    public ZPPlayerModule(@NotNull ZPModuleData zpModuleData) {
        super(zpModuleData);
    }

    public ZPPlayerModule() {
    }

    @Override
    public void fml_commonSetupEvent() {

    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void fml_clientSetupEvent() {

    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void clientShutDown() {

    }

    //@Override
    //public void initMixins(ZombiePlague3.@NotNull IMixinEntry mixinEntry) {
    //    mixinEntry.addMixinConfigData(new ZombiePlague3.IMixinEntry.MixinConfig("player", "ru.gltexture.zpm3.modules.player.mixins.impl"),
    //            new ZombiePlague3.IMixinEntry.MixinClass("common.ZPSPlayerFeaturesMixin", ZPSide.COMMON),
    //            new ZombiePlague3.IMixinEntry.MixinClass("client.ZPCPlayerFeaturesMixin", ZPSide.CLIENT),
    //            new ZombiePlague3.IMixinEntry.MixinClass("client.ZPPlayerItemReanimateMixin", ZPSide.CLIENT));
    //}

    @Override
    public void initialize(ZombiePlague3.@NotNull IModuleEntry moduleEntry) {
        ZPUtility.sides().onlyClient(() -> {
            moduleEntry.addMinecraftEventClass(ZPRenderWorldEventWithPickUpCheck.class);
            moduleEntry.addMinecraftEventClass(ZPPlayerItemToolTipsEvent.class);
            moduleEntry.addMinecraftEventClass(ZPRenderGuiEvent.class);
            moduleEntry.addMinecraftEventClass(ZPResourcePackEvent.class);
            moduleEntry.addMinecraftEventClass(ZPPlayerLyingClientCheckEvent.class);
        });

        moduleEntry.addMinecraftEventClass(ZPPlayerGunCancelInterEvent.class);
        moduleEntry.addMinecraftEventClass(ZPPlayerEntityItemEvent.class);
        moduleEntry.addMinecraftEventClass(ZPPlayerTickEvent.class);
        moduleEntry.addMinecraftEventClass(ZPPlayerFillBucketEvent.class);
        moduleEntry.addMinecraftEventClass(ZPPlaceLiquidEvent.class);
        moduleEntry.addMinecraftEventClass(ZPPlayerEatFoodEvent.class);
        moduleEntry.addMinecraftEventClass(ZPPlayerJoinOrSpawnEvent.class);
    }

    @Override
    public void preInitialize() {
        ZPUtility.sides().onlyClient(() -> {
            ZombiePlague3.registerKeyBindings(new ZPPickUpKeyBindings());
        });
    }

    @Override
    public void postInitialize() {

    }
}
