package ru.gltexture.zpm3.modules.debug;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.modules.debug.events.ZPFreeCameraEvents;
import ru.gltexture.zpm3.modules.debug.events.ZPRenderStuffEvent;
import ru.gltexture.zpm3.modules.debug.imgui.DearUITRSInterface;
import ru.gltexture.zpm3.engine.client.rendering.ZPRenderHelper;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.asset.ZPModule;
import ru.gltexture.zpm3.engine.core.asset.ZPModuleData;
import ru.gltexture.zpm3.engine.service.ZPUtility;

public class ZPDebugModule extends ZPModule {
    public ZPDebugModule(@NotNull ZPModuleData zpModuleData) {
        super(zpModuleData);
    }

    public ZPDebugModule() {
    }

    @Override
    public void commonSetup() {
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void clientSetup() {
        if (ZPRenderHelper.INSTANCE.getDearUIRenderer() != null) {
            ZPRenderHelper.INSTANCE.getDearUIRenderer().getInterfacesManager().addActiveInterface(new DearUITRSInterface());
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void clientDestroy() {

    }

    //@Override
    //public void initMixins(ZombiePlague3.@NotNull IMixinEntry mixinEntry) {
    //    mixinEntry.addMixinConfigData(new ZombiePlague3.IMixinEntry.MixinConfig("debug", "ru.gltexture.zpm3.modules.debug.mixins.impl"),
    //            new ZombiePlague3.IMixinEntry.MixinClass("client.ZPCameraMixin", ZPSide.CLIENT),
    //            new ZombiePlague3.IMixinEntry.MixinClass("client.ZPInputMixin", ZPSide.CLIENT));
    //}

    public static void addNewLineToDraw(@NotNull ZPRenderStuffEvent.LineRequest lineRequest) {
        ZPRenderStuffEvent.addNewLineToDraw(lineRequest);
    }

    @Override
    public void initializeModule(ZombiePlague3.@NotNull IModuleEntry assetEntry) {
        ZPUtility.sides().onlyClient(() -> {
            assetEntry.addEventClass(ZPFreeCameraEvents.class);
            assetEntry.addEventClass(ZPRenderStuffEvent.class);
        });
    }

    @Override
    public void preCommonInitialize() {

    }

    @Override
    public void postCommonInitialize() {

    }
}
