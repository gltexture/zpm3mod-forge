package ru.gltexture.zpm3.modules.ui;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.module.ZPModule;
import ru.gltexture.zpm3.engine.core.module.ZPModuleData;
import ru.gltexture.zpm3.engine.service.ZPUtility;
import ru.gltexture.zpm3.modules.ui.events.client.ZPMenuPatchEvent;
import ru.gltexture.zpm3.modules.ui.events.client.ZPNewSettingsScreenEvent;

public class ZPUIModule extends ZPModule {
    public ZPUIModule(@NotNull ZPModuleData zpModuleData) {
        super(zpModuleData);
    }

    public ZPUIModule() {
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
        ZPUtility.sides().onlyClient(() -> {
            moduleEntry.addEventClass(ZPMenuPatchEvent.class);
            moduleEntry.addEventClass(ZPNewSettingsScreenEvent.class);
        });
    }

    @Override
    public void preInitialize() {
    }

    @Override
    public void postInitialize() {

    }
}
