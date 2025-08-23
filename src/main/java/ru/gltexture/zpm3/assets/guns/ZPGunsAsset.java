package ru.gltexture.zpm3.assets.guns;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.guns.events.ZPGunsUI;
import ru.gltexture.zpm3.assets.guns.processing.input.ZPClientGunInputProcessing;
import ru.gltexture.zpm3.assets.guns.rendering.ZPDefaultGunRenderers;
import ru.gltexture.zpm3.assets.player.events.both.ZPPlayerTickEvent;
import ru.gltexture.zpm3.assets.player.events.client.ZPClientJoinEvent;
import ru.gltexture.zpm3.assets.player.events.server.ZPPlayerFillBucketEvent;
import ru.gltexture.zpm3.assets.player.events.server.ZPPlayerLoggedInEvent;
import ru.gltexture.zpm3.assets.player.logic.PlayerBothSidesLogic;
import ru.gltexture.zpm3.assets.player.logic.PlayerClientSideLogic;
import ru.gltexture.zpm3.assets.player.logic.PlayerServerSideLogic;
import ru.gltexture.zpm3.assets.player.logic.PlayerTickEventLogic;
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
        ZPRenderHooksManager.INSTANCE.addItemSceneRenderingHooks((ZPRenderHooks.ZPItemSceneRenderingHooks) ZPDefaultGunRenderers.defaultMuzzleflashFXUniversal);
        ZPClientCallbacksManager.INSTANCE.addClientTickCallback(e -> {
            if (e == TickEvent.Phase.START) {
                ZPClientGunInputProcessing.INSTANCE.tick(Minecraft.getInstance());
            }
        });
        ZPRenderHooksManager.INSTANCE.addSceneRenderingHook(((renderStage, partialTicks, deltaTime, pNanoTime, pRenderLevel) -> {
            if (renderStage == ZPRenderHelper.RenderStage.PRE) {
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
    }

    @Override
    public void initAsset(ZombiePlague3.@NotNull IAssetEntry assetEntry) {
        assetEntry.addEventClass(ZPGunsUI.class);
    }
}
