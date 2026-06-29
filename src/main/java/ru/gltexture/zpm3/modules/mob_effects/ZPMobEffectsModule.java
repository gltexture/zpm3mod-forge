package ru.gltexture.zpm3.modules.mob_effects;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.module.ZPModule;
import ru.gltexture.zpm3.modules.mob_effects.events.common.ZPEntityEffectActionsEvent;
import ru.gltexture.zpm3.modules.mob_effects.init.ZPMobEffects;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.module.ZPModuleData;

public class ZPMobEffectsModule extends ZPModule {
    public ZPMobEffectsModule(@NotNull ZPModuleData zpModuleData) {
        super(zpModuleData);
    }

    public ZPMobEffectsModule() {
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
    //    mixinEntry.addMixinConfigData(new ZombiePlague3.IMixinEntry.MixinConfig("mob_effects", "ru.gltexture.zpm3.modules.mob_effects.mixins.impl"),
    //            new ZombiePlague3.IMixinEntry.MixinClass("common.ZPEntityFracturedSprintMixin", ZPSide.COMMON),
    //            new ZombiePlague3.IMixinEntry.MixinClass("common.ZPLivingEntityFracturedSprintMixin", ZPSide.COMMON),
    //            new ZombiePlague3.IMixinEntry.MixinClass("common.ZPPlayerSoundsThenPlaguedMixin", ZPSide.COMMON)
    //    );
    //}

    @Override
    public void initialize(ZombiePlague3.@NotNull IModuleEntry moduleEntry) {
        moduleEntry.addMinecraftRegistryClass(ZPMobEffects.class);
        moduleEntry.addMinecraftEventClass(ZPEntityEffectActionsEvent.class);
    }

    @Override
    public void preInitialize() {

    }

    @Override
    public void postInitialize() {

    }
}
