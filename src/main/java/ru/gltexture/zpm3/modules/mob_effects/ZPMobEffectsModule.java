package ru.gltexture.zpm3.modules.mob_effects;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.asset.ZPModule;
import ru.gltexture.zpm3.modules.mob_effects.events.common.ZPEntityEffectActionsEvent;
import ru.gltexture.zpm3.modules.mob_effects.init.ZPMobEffects;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.asset.ZPModuleData;

public class ZPMobEffectsModule extends ZPModule {
    public ZPMobEffectsModule(@NotNull ZPModuleData zpModuleData) {
        super(zpModuleData);
    }

    public ZPMobEffectsModule() {
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
    //    mixinEntry.addMixinConfigData(new ZombiePlague3.IMixinEntry.MixinConfig("mob_effects", "ru.gltexture.zpm3.modules.mob_effects.mixins.impl"),
    //            new ZombiePlague3.IMixinEntry.MixinClass("common.ZPEntityFracturedSprintMixin", ZPSide.COMMON),
    //            new ZombiePlague3.IMixinEntry.MixinClass("common.ZPLivingEntityFracturedSprintMixin", ZPSide.COMMON),
    //            new ZombiePlague3.IMixinEntry.MixinClass("common.ZPPlayerSoundsThenPlaguedMixin", ZPSide.COMMON)
    //    );
    //}

    @Override
    public void initializeModule(ZombiePlague3.@NotNull IModuleEntry assetEntry) {
        assetEntry.addZP3RegistryClass(ZPMobEffects.class);
        assetEntry.addEventClass(ZPEntityEffectActionsEvent.class);
    }

    @Override
    public void preCommonInitialize() {

    }

    @Override
    public void postCommonInitialize() {

    }
}
