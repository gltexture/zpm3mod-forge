package ru.gltexture.zpm3.modules.guns;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.modules.guns.events.ZPGunPostRender;
import ru.gltexture.zpm3.modules.guns.events.ZPGunTossEvent;
import ru.gltexture.zpm3.modules.guns.events.ZPGunsUI;
import ru.gltexture.zpm3.modules.guns.keybind.ZPGunKeyBindings;
import ru.gltexture.zpm3.modules.guns.processing.input.ZPClientGunClientTickProcessing;
import ru.gltexture.zpm3.modules.guns.rendering.ZPAbstractGunRenderer;
import ru.gltexture.zpm3.modules.guns.rendering.ZPDefaultGunRenderers;
import ru.gltexture.zpm3.modules.guns.rendering.basic.ZPDefaultGunMuzzleflashFX;
import ru.gltexture.zpm3.engine.client.callbacking.ZPClientCallbacksManager;
import ru.gltexture.zpm3.engine.client.rendering.ZPRenderHelper;
import ru.gltexture.zpm3.engine.client.rendering.hooks.ZPRenderHooks;
import ru.gltexture.zpm3.engine.client.rendering.hooks.ZPRenderHooksManager;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.asset.ZPModule;
import ru.gltexture.zpm3.engine.core.asset.ZPModuleData;
import ru.gltexture.zpm3.engine.service.ZPUtility;

public class ZPGunsModule extends ZPModule {
    public ZPGunsModule(@NotNull ZPModuleData zpModuleData) {
        super(zpModuleData);
    }

    public ZPGunsModule() {
    }

    @Override
    public void commonSetup() {

    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void clientSetup() {
        ZPDefaultGunRenderers.init();
        ZPRenderHooksManager.INSTANCE.addItemSceneRendering1PersonHooks((ZPRenderHooks.ZPItemSceneRendering1PersonHooks) ZPDefaultGunRenderers.defaultMuzzleflashFXUniversal);
        ZPRenderHooksManager.INSTANCE.addItemSceneRendering3PersonHooks((ZPRenderHooks.ZPItemSceneRendering3PersonHooks) ZPDefaultGunRenderers.defaultMuzzleflashFXUniversal);
        ZPClientCallbacksManager.INSTANCE.addReloadGameResourcesCallback(((ZPDefaultGunMuzzleflashFX) ZPDefaultGunRenderers.defaultMuzzleflashFXUniversal).reloadGameResourcesCallback());
        ZPClientCallbacksManager.INSTANCE.addClientTickCallback(e -> {
            ZPClientGunClientTickProcessing.INSTANCE.tick(Minecraft.getInstance(), e);
        });
        ZPRenderHooksManager.INSTANCE.addItemSceneRendering1PersonHookPre(((deltaTicks, pPartialTicks, pPoseStack, pBuffer, pPlayerEntity, pCombinedLight) -> {
            ZPAbstractGunRenderer.breathEffect(pPartialTicks, pPoseStack);
        }));
        ZPRenderHooksManager.INSTANCE.addSceneRenderingHook(((renderStage, partialTicks, deltaTime, pNanoTime, pRenderLevel) -> {
            if (renderStage == ZPRenderHelper.RenderStage.PRE) {
                ZPClientGunClientTickProcessing.INSTANCE.process(Minecraft.getInstance());
            }
        }));
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void clientDestroy() {

    }

    //@Override
    //public void initMixins(ZombiePlague3.@NotNull IMixinEntry mixinEntry) {
    //    mixinEntry.addMixinConfigData(new ZombiePlague3.IMixinEntry.MixinConfig("guns", "ru.gltexture.zpm3.modules.guns.mixins.impl"),
    //            new ZombiePlague3.IMixinEntry.MixinClass("client.ZPReanimateArmMixin", ZPSide.CLIENT),
    //            new ZombiePlague3.IMixinEntry.MixinClass("client.ZPPlayerClientDataMuzzleflash3PMixin", ZPSide.CLIENT),
    //            new ZombiePlague3.IMixinEntry.MixinClass("client.ZPClientPacketStackNBTCopyMixin", ZPSide.CLIENT),
    //            new ZombiePlague3.IMixinEntry.MixinClass("common.ZPItemMixin", ZPSide.COMMON),
    //            new ZombiePlague3.IMixinEntry.MixinClass("client.ZPItemStackClDataGunSyncMixin", ZPSide.CLIENT)
    //    );
    //}

    @Override
    public void initializeModule(ZombiePlague3.@NotNull IModuleEntry assetEntry) {
        ZPUtility.sides().onlyClient(() -> {
            assetEntry.addEventClass(ZPGunsUI.class);
            assetEntry.addEventClass(ZPGunPostRender.class);
        });
        assetEntry.addEventClass(ZPGunTossEvent.class);
    }

    @Override
    public void preCommonInitialize() {
        ZPUtility.sides().onlyClient(() -> {
            ZombiePlague3.registerKeyBindings(new ZPGunKeyBindings());
        });
    }

    @Override
    public void postCommonInitialize() {

    }
}
