package ru.gltexture.zpm3.assets.net_pack;

import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.net_pack.packets.AcidSpreadPacket;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.asset.ZPAsset;
import ru.gltexture.zpm3.engine.core.asset.ZPAssetData;
import ru.gltexture.zpm3.engine.network.ZPNetwork;

public class ZPNetPackAsset extends ZPAsset {
    public ZPNetPackAsset(@NotNull ZPAssetData zpAssetData) {
        super(zpAssetData);
    }

    public ZPNetPackAsset() {
    }

    @Override
    public void commonSetup() {

    }

    @Override
    public void initMixins(ZombiePlague3.@NotNull IMixinEntry mixinEntry) {

    }

    @Override
    public void initAsset(ZombiePlague3.@NotNull IAssetEntry assetEntry) {
        assetEntry.addNetworkPacket(new ZPNetwork.PacketData<>(AcidSpreadPacket.class, AcidSpreadPacket.encoder(), AcidSpreadPacket.decoder()));
    }
}