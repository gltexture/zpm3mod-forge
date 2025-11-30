package ru.gltexture.zpm3.assets.mob_effects;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.mob_effects.events.common.ZPEntityEffectActionsEvent;
import ru.gltexture.zpm3.assets.mob_effects.init.ZPMobEffects;
import ru.gltexture.zpm3.assets.player.logic.PlayerBothSidesLogic;
import ru.gltexture.zpm3.assets.player.logic.PlayerClientSideLogic;
import ru.gltexture.zpm3.assets.player.logic.PlayerServerSideLogic;
import ru.gltexture.zpm3.assets.player.logic.PlayerTickEventLogic;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.asset.ZPAsset;
import ru.gltexture.zpm3.engine.core.asset.ZPAssetData;

public class ZPMobEffectsAsset extends ZPAsset {
    public ZPMobEffectsAsset(@NotNull ZPAssetData zpAssetData) {
        super(zpAssetData);
    }

    public ZPMobEffectsAsset() {
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
        mixinEntry.addMixinConfigData(new ZombiePlague3.IMixinEntry.MixinConfig("mob_effects", "ru.gltexture.zpm3.assets.mob_effects.mixins.impl"),
                new ZombiePlague3.IMixinEntry.MixinClass("common.ZPEntitySprintMixin", ZPSide.COMMON),
                new ZombiePlague3.IMixinEntry.MixinClass("common.ZPLivingEntitySprintMixin", ZPSide.COMMON),
                new ZombiePlague3.IMixinEntry.MixinClass("common.ZPPlayerSoundsMixin", ZPSide.COMMON)
        );
    }

    @Override
    public void initializeAsset(ZombiePlague3.@NotNull IAssetEntry assetEntry) {
        assetEntry.addZP3RegistryClass(ZPMobEffects.class);
        assetEntry.addEventClass(ZPEntityEffectActionsEvent.class);
    }
}
