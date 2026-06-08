package ru.gltexture.zpm3.modules.fx;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.module.ZPModule;
import ru.gltexture.zpm3.modules.fx.init.ZPParticles;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.module.ZPModuleData;

public class ZPFXModule extends ZPModule {
    public ZPFXModule(@NotNull ZPModuleData zpModuleData) {
        super(zpModuleData);
    }

    public ZPFXModule() {
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

    @Override
    public void initialize(ZombiePlague3.@NotNull IModuleEntry moduleEntry) {
        moduleEntry.addRegistryClass(ZPParticles.class);
    }

    @Override
    public void preInitialize() {

    }

    @Override
    public void postInitialize() {

    }
}
