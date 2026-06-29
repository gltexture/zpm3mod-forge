package ru.gltexture.zpm3.modules.common;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import ru.gltexture.zpm3.modules.common.init.*;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.module.ZPModule;
import ru.gltexture.zpm3.engine.core.module.ZPModuleData;
import ru.gltexture.zpm3.engine.service.ZPUtility;

public class ZPCommonModule extends ZPModule {
    public ZPCommonModule(@NotNull ZPModuleData zpModuleData) {
        super(zpModuleData);
    }

    public ZPCommonModule() {
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
    //    mixinEntry.addMixinConfigData(new ZombiePlague3.IMixinEntry.MixinConfig("common", "ru.gltexture.zpm3.modules.common.mixins.impl"),
    //            new ZombiePlague3.IMixinEntry.MixinClass("common.ZPFadingTorchMixin", ZPSide.COMMON),
    //            new ZombiePlague3.IMixinEntry.MixinClass("common.ZPWallTorchMixin", ZPSide.COMMON),
    //            new ZombiePlague3.IMixinEntry.MixinClass("common.ZPFadingPumpkinMixin", ZPSide.COMMON),
    //            new ZombiePlague3.IMixinEntry.MixinClass("common.ZPFadingLavaMixin", ZPSide.COMMON),
    //            new ZombiePlague3.IMixinEntry.MixinClass("common.ZPFluidPlacedFadingBlockMixin", ZPSide.COMMON),
    //            new ZombiePlague3.IMixinEntry.MixinClass("common.ZPMobCategoryIncreaseSpawnMixin", ZPSide.COMMON),
    //            new ZombiePlague3.IMixinEntry.MixinClass("common.ZPMilkDisableMixin", ZPSide.COMMON)
    //    );
    //}

    @Override
    public void initialize(ZombiePlague3.@NotNull IModuleEntry moduleEntry) {
        moduleEntry.addMinecraftRegistryClass(ZPSounds.class);
        moduleEntry.addMinecraftRegistryClass(ZPDamageTypes.class);
        ZPUtility.sides().onlyClient(() -> {
            moduleEntry.addMinecraftRegistryClass(ZPTabs.class);
        });
    }

    @Override
    public void preInitialize() {
    }

    @Override
    public void postInitialize() {
    }
}
