package ru.gltexture.zpm3.engine.core.module;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;

public abstract class ZPModule {
    private final ZPModuleData zpModuleData;

    public ZPModule(@NotNull ZPModuleData zpModuleData) {
        this.zpModuleData = zpModuleData;
    }

    protected ZPModule() {
        this.zpModuleData = null;
    }

    public abstract void fml_commonSetupEvent();

    @OnlyIn(Dist.CLIENT)
    public abstract void fml_clientSetupEvent();

    @OnlyIn(Dist.CLIENT)
    public abstract void clientShutDown();

    @Deprecated
    public void setupMixins(@NotNull ZombiePlague3.IMixinEntry mixinEntry) { }
    public abstract void initialize(@NotNull ZombiePlague3.IModuleEntry moduleEntry);
    public abstract void preInitialize();
    public abstract void postInitialize();

    public ZPModuleData getModuleData() {
        return this.zpModuleData;
    }

    @Override
    public String toString() {
        return "Module: " + this.getModuleData();
    }
}
