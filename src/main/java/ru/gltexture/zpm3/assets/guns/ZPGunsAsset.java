package ru.gltexture.zpm3.assets.guns;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.guns.events.ZPGunPostRender;
import ru.gltexture.zpm3.assets.guns.events.ZPGunTossEvent;
import ru.gltexture.zpm3.assets.guns.events.ZPGunsUI;
import ru.gltexture.zpm3.assets.guns.keybind.ZPGunKeyBindings;
import ru.gltexture.zpm3.assets.guns.processing.input.ZPClientGunClientTickProcessing;
import ru.gltexture.zpm3.assets.guns.rendering.ZPAbstractGunRenderer;
import ru.gltexture.zpm3.assets.guns.rendering.ZPDefaultGunRenderers;
import ru.gltexture.zpm3.assets.guns.rendering.basic.ZPDefaultGunMuzzleflashFX;
import ru.gltexture.zpm3.engine.client.callbacking.ZPClientCallbacksManager;
import ru.gltexture.zpm3.engine.client.rendering.ZPRenderHelper;
import ru.gltexture.zpm3.engine.client.rendering.hooks.ZPRenderHooks;
import ru.gltexture.zpm3.engine.client.rendering.hooks.ZPRenderHooksManager;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.asset.ZPAsset;
import ru.gltexture.zpm3.engine.core.asset.ZPAssetData;
import ru.gltexture.zpm3.engine.service.ZPUtility;

public class ZPGunsAsset extends ZPAsset {
    public ZPGunsAsset(@NotNull ZPAssetData zpAssetData) {
        super(zpAssetData);
    }

    public ZPGunsAsset() {
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

    @Override
    public void initMixins(ZombiePlague3.@NotNull IMixinEntry mixinEntry) {
        mixinEntry.addMixinConfigData(new ZombiePlague3.IMixinEntry.MixinConfig("guns", "ru.gltexture.zpm3.assets.guns.mixins.impl"),
                new ZombiePlague3.IMixinEntry.MixinClass("client.ZPHumanoidArmMixin", ZPSide.CLIENT),
                new ZombiePlague3.IMixinEntry.MixinClass("client.ZPPlayerClientDataMixin", ZPSide.CLIENT),
                new ZombiePlague3.IMixinEntry.MixinClass("client.ZPClientPacketListenerMixin", ZPSide.CLIENT),
                new ZombiePlague3.IMixinEntry.MixinClass("common.ZPItemMixin", ZPSide.COMMON),
                new ZombiePlague3.IMixinEntry.MixinClass("client.ZPItemStackClDataMixin", ZPSide.CLIENT)
        );
    }

    @Override
    public void initializeAsset(ZombiePlague3.@NotNull IAssetEntry assetEntry) {
        ZPUtility.sides().onlyClient(() -> {
            assetEntry.addEventClass(ZPGunsUI.class);
            assetEntry.addEventClass(ZPGunPostRender.class);
        });
        assetEntry.addEventClass(ZPGunTossEvent.class);
    }

    @Override
    public void preCommonInitializeAsset() {
        ZPUtility.sides().onlyClient(() -> {
            ZombiePlague3.registerKeyBindings(new ZPGunKeyBindings());
        });
    }

    @Override
    public void postCommonInitializeAsset() {

    }
}
