package ru.gltexture.zpm3.modules.net_pack;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.modules.net_pack.packets.*;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.asset.ZPModule;
import ru.gltexture.zpm3.engine.core.asset.ZPModuleData;
import ru.gltexture.zpm3.engine.network.ZPNetwork;

public class ZPNetPackModule extends ZPModule {
    public ZPNetPackModule(@NotNull ZPModuleData zpModuleData) {
        super(zpModuleData);
    }

    public ZPNetPackModule() {
    }

    @Override
    public void commonSetup() {

    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void clientSetup() {

    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void clientDestroy() {
    }

    @Override
    public void initializeModule(ZombiePlague3.@NotNull IModuleEntry assetEntry) {
        assetEntry.addNetworkPacket(new ZPNetwork.PacketData<>(0, ZPAcidSpreadPacket.class, ZPAcidSpreadPacket.encoder(), ZPAcidSpreadPacket.decoder()));
        assetEntry.addNetworkPacket(new ZPNetwork.PacketData<>(1, ZPGunActionPacket.class, ZPGunActionPacket.encoder(), ZPGunActionPacket.decoder()));
        assetEntry.addNetworkPacket(new ZPNetwork.PacketData<>(2, ZPBulletHitPacket.class, ZPBulletHitPacket.encoder(), ZPBulletHitPacket.decoder()));
        assetEntry.addNetworkPacket(new ZPNetwork.PacketData<>(3, ZPBulletTracePacket.class, ZPBulletTracePacket.encoder(), ZPBulletTracePacket.decoder()));
        assetEntry.addNetworkPacket(new ZPNetwork.PacketData<>(4, ZPNetCheckPacket.class, ZPNetCheckPacket.encoder(), ZPNetCheckPacket.decoder()));
        assetEntry.addNetworkPacket(new ZPNetwork.PacketData<>(5, ZPBlockCrack.class, ZPBlockCrack.encoder(), ZPBlockCrack.decoder()));
        assetEntry.addNetworkPacket(new ZPNetwork.PacketData<>(6, ZPSendGlobalSettings_StoC.class, ZPSendGlobalSettings_StoC.encoder(), ZPSendGlobalSettings_StoC.decoder()));
        assetEntry.addNetworkPacket(new ZPNetwork.PacketData<>(7, ZPBloodPainFXPacket.class, ZPBloodPainFXPacket.encoder(), ZPBloodPainFXPacket.decoder()));
        assetEntry.addNetworkPacket(new ZPNetwork.PacketData<>(8, ZPSendGlobalSettings_CtoS.class, ZPSendGlobalSettings_CtoS.encoder(), ZPSendGlobalSettings_CtoS.decoder()));
        assetEntry.addNetworkPacket(new ZPNetwork.PacketData<>(9, ZPPlayerWantToPickUpItem.class, ZPPlayerWantToPickUpItem.encoder(), ZPPlayerWantToPickUpItem.decoder()));
        assetEntry.addNetworkPacket(new ZPNetwork.PacketData<>(10, ZPBulletBloodFXPacket.class, ZPBulletBloodFXPacket.encoder(), ZPBulletBloodFXPacket.decoder()));
    }

    @Override
    public void preCommonInitialize() {

    }

    @Override
    public void postCommonInitialize() {

    }
}