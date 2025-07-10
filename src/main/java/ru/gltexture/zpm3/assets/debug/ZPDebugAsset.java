package ru.gltexture.zpm3.assets.debug;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.init.*;
import ru.gltexture.zpm3.assets.debug.imgui.DearUITRSInterface;
import ru.gltexture.zpm3.engine.client.init.ZPClientInitManager;
import ru.gltexture.zpm3.engine.client.rendering.ZPRenderHelper;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.asset.ZPAsset;
import ru.gltexture.zpm3.engine.core.asset.ZPAssetData;
import ru.gltexture.zpm3.engine.service.ZPUtility;

public class ZPDebugAsset extends ZPAsset {
    public ZPDebugAsset(@NotNull ZPAssetData zpAssetData) {
        super(zpAssetData);
    }

    public ZPDebugAsset() {
    }

    @Override
    public void commonSetup() {
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void clientSetup() {
        ZPClientInitManager.setupRunner((w) -> ZPRenderHelper.INSTANCE.getDearUIRenderer().getInterfacesManager().addActiveInterface(new DearUITRSInterface()));
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
    }
}
