package ru.gltexture.zpm3.modules.fx;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.asset.ZPModule;
import ru.gltexture.zpm3.modules.fx.init.ZPParticles;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.asset.ZPModuleData;

public class ZPFXModule extends ZPModule {
    public ZPFXModule(@NotNull ZPModuleData zpModuleData) {
        super(zpModuleData);
    }

    public ZPFXModule() {
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

    @Override
    public void initializeModule(ZombiePlague3.@NotNull IModuleEntry assetEntry) {
        assetEntry.addZP3RegistryClass(ZPParticles.class);
    }

    @Override
    public void preCommonInitialize() {

    }

    @Override
    public void postCommonInitialize() {

    }
}
