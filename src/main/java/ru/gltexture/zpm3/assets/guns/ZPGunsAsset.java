package ru.gltexture.zpm3.assets.guns;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL46;
import ru.gltexture.zpm3.assets.guns.events.ZPGunPostRender;
import ru.gltexture.zpm3.assets.guns.events.ZPGunsUI;
import ru.gltexture.zpm3.assets.guns.processing.input.ZPClientGunInputProcessing;
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
        ZPClientCallbacksManager.INSTANCE.addClientTickCallback(e -> {
            if (e == TickEvent.Phase.START) {
                ZPClientGunInputProcessing.INSTANCE.tick(Minecraft.getInstance());
            }
        });
        ZPRenderHooksManager.INSTANCE.addSceneRenderingHook(((renderStage, partialTicks, deltaTime, pNanoTime, pRenderLevel) -> {
            if (renderStage == ZPRenderHelper.RenderStage.PRE) {
                ZPDefaultGunMuzzleflashFX.muzzleflash3dpFBO.bindFBO();
                GL46.glDrawBuffers(new int[] {GL46.GL_COLOR_ATTACHMENT0, GL46.GL_COLOR_ATTACHMENT1});
                GL46.glClear(GL46.GL_COLOR_BUFFER_BIT | GL46.GL_DEPTH_BUFFER_BIT | GL46.GL_STENCIL_BUFFER_BIT);
                ZPDefaultGunMuzzleflashFX.muzzleflash3dpFBO.unBindFBO();
                ZPClientGunInputProcessing.INSTANCE.process(Minecraft.getInstance());
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
                new ZombiePlague3.IMixinEntry.MixinClass("client.ZPHumanoidArmMixin", ZPSide.CLIENT)
        );
    }

    @Override
    public void initAsset(ZombiePlague3.@NotNull IAssetEntry assetEntry) {
        assetEntry.addEventClass(ZPGunsUI.class);
        assetEntry.addEventClass(ZPGunPostRender.class);
    }
}
