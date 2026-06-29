package ru.gltexture.zpm3.modules.fluids;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.module.ZPModule;
import ru.gltexture.zpm3.engine.core.module.ZPModuleData;
import ru.gltexture.zpm3.engine.service.ZPUtility;
import ru.gltexture.zpm3.modules.debug.events.ZPRenderStuffEvent;
import ru.gltexture.zpm3.modules.fluids.init.ZPFluidTypes;
import ru.gltexture.zpm3.modules.fluids.init.ZPFluids;

public class ZPFluidsModule extends ZPModule {
    public ZPFluidsModule(@NotNull ZPModuleData zpModuleData) {
        super(zpModuleData);
    }

    public ZPFluidsModule() {
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

    public static void addNewLineToDraw(@NotNull ZPRenderStuffEvent.LineRequest lineRequest) {
        ZPRenderStuffEvent.addNewLineToDraw(lineRequest);
    }

    @Override
    public void initialize(ZombiePlague3.@NotNull IModuleEntry moduleEntry) {
        moduleEntry.addMinecraftRegistryClass(ZPFluids.class);
        moduleEntry.addMinecraftRegistryClass(ZPFluidTypes.class);

        ZPUtility.sides().onlyClient(() -> {
        });
    }

    @Override
    public void preInitialize() {

    }

    @Override
    public void postInitialize() {

    }
}
