package ru.gltexture.zpm3.modules.worldgen;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.module.ZPModule;
import ru.gltexture.zpm3.engine.core.module.ZPModuleData;
import ru.gltexture.zpm3.engine.service.ZPPath;
import ru.gltexture.zpm3.engine.service.ZPUtility;
import ru.gltexture.zpm3.modules.worldgen.archiver.ZPMapArchivedRegistry;

public class ZPWorldGenModule extends ZPModule {
    public ZPWorldGenModule(@NotNull ZPModuleData zpModuleData) {
        super(zpModuleData);
    }

    public ZPWorldGenModule() {
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
           // moduleEntry.addMinecraftEventClass(ZPRenderWorldEventWithPickUpCheck.class);
        });

      //  moduleEntry.addMinecraftEventClass(ZPPlayerGunCancelInterEvent.class);
    }

    @Override
    public void preInitialize() {
        ZPUtility.sides().onlyClient(() -> {
            ZPMapArchivedRegistry.registerZpArchivedMap(ZombiePlague3.MOD_ID(), new ZPPath(ZPMapArchivedRegistry.MAPS_DIR, "zombie_city").getFullPath());
        });
      //  ZPUtility.sides().onlyClient(() -> {
      //      ZombiePlague3.registerKeyBindings(new ZPPickUpKeyBindings());
      //  });
    }

    @Override
    public void postInitialize() {
    }
}
