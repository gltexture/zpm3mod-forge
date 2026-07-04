package ru.gltexture.zpm3.modules.net_pack;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZPNetworkHandler;
import ru.gltexture.zpm3.engine.core.config.builtin.ZPWorldConfig;
import ru.gltexture.zpm3.engine.service.ZPUtility;

import ru.gltexture.zpm3.modules.net_pack.data.ZPDefaultDataKeys;
import ru.gltexture.zpm3.modules.net_pack.packets.*;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.module.ZPModule;
import ru.gltexture.zpm3.engine.core.module.ZPModuleData;
import ru.gltexture.zpm3.engine.network.ZPNetwork;

public class ZPNetPackModule extends ZPModule {
    public ZPNetPackModule(@NotNull ZPModuleData zpModuleData) {
        super(zpModuleData);
    }

    public ZPNetPackModule() {
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
        moduleEntry.addNetworkPacket(new ZPNetwork.PacketData<>(0, ZPAcidSpreadPacket.class, ZPAcidSpreadPacket.encoder(), ZPAcidSpreadPacket.decoder()));
        moduleEntry.addNetworkPacket(new ZPNetwork.PacketData<>(1, ZPGunActionPacket.class, ZPGunActionPacket.encoder(), ZPGunActionPacket.decoder()));
        moduleEntry.addNetworkPacket(new ZPNetwork.PacketData<>(2, ZPBulletHitPacket.class, ZPBulletHitPacket.encoder(), ZPBulletHitPacket.decoder()));
        moduleEntry.addNetworkPacket(new ZPNetwork.PacketData<>(3, ZPBulletTracePacket.class, ZPBulletTracePacket.encoder(), ZPBulletTracePacket.decoder()));
        moduleEntry.addNetworkPacket(new ZPNetwork.PacketData<>(4, ZPNetCheckPacket.class, ZPNetCheckPacket.encoder(), ZPNetCheckPacket.decoder()));
        moduleEntry.addNetworkPacket(new ZPNetwork.PacketData<>(5, ZPBlockCrackPacket.class, ZPBlockCrackPacket.encoder(), ZPBlockCrackPacket.decoder()));
        //moduleEntry.addNetworkPacket(new ZPNetwork.PacketData<>(6, ZPSendGlobalSettings_StoC.class, ZPSendGlobalSettings_StoC.encoder(), ZPSendGlobalSettings_StoC.decoder()));
        moduleEntry.addNetworkPacket(new ZPNetwork.PacketData<>(7, ZPBloodPainFXPacket.class, ZPBloodPainFXPacket.encoder(), ZPBloodPainFXPacket.decoder()));
        moduleEntry.addNetworkPacket(new ZPNetwork.PacketData<>(8, ZPSyncConfigSettingsPacket.class, ZPSyncConfigSettingsPacket.encoder(), ZPSyncConfigSettingsPacket.decoder()));
        moduleEntry.addNetworkPacket(new ZPNetwork.PacketData<>(9, ZPPlayerWantToPickUpItemPacket.class, ZPPlayerWantToPickUpItemPacket.encoder(), ZPPlayerWantToPickUpItemPacket.decoder()));
        moduleEntry.addNetworkPacket(new ZPNetwork.PacketData<>(10, ZPBulletBloodFXPacket.class, ZPBulletBloodFXPacket.encoder(), ZPBulletBloodFXPacket.decoder()));
        moduleEntry.addNetworkPacket(new ZPNetwork.PacketData<>(11, ZPValidateModePacket.class, ZPValidateModePacket.encoder(), ZPValidateModePacket.decoder()));
        moduleEntry.addNetworkPacket(new ZPNetwork.PacketData<>(12, ZPLyingStatePacket.class, ZPLyingStatePacket.encoder(), ZPLyingStatePacket.decoder()));
        moduleEntry.addNetworkPacket(new ZPNetwork.PacketData<>(13, ZPSendTheOnlyZone_StoC_Packet.class, ZPSendTheOnlyZone_StoC_Packet.encoder(), ZPSendTheOnlyZone_StoC_Packet.decoder()));
        moduleEntry.addNetworkPacket(new ZPNetwork.PacketData<>(14, ZPSendAllZones_StoC_Packet.class, ZPSendAllZones_StoC_Packet.encoder(), ZPSendAllZones_StoC_Packet.decoder()));

        ZPUtility.sides().onlyClient(() -> moduleEntry.registerNetSyncedConfigData_ClientToServer(
                new ZPNetworkHandler.NetSyncDataFabric.Builder()
                        .addBoolean(ZPDefaultDataKeys.CtoS__PICK_UP_ON_KEY, ZPWorldConfig.ALLOW_ITEMS_PICKING_ON_KEY::getVar)
        ));

        moduleEntry.registerNetSyncedConfigData_ServerToClient(
                new ZPNetworkHandler.NetSyncDataFabric.Builder()
                        .addBoolean(ZPDefaultDataKeys.StoC__DARKNESS_ENABLED, ZPWorldConfig.ENABLE_HARDCORE_DARKNESS_SERVER_SIDE::getVar)
                        .addBoolean(ZPDefaultDataKeys.StoC__SERVER_PICK_UP_ON_KEY, ZPWorldConfig.ALLOW_ITEMS_PICKING_ON_KEY::getVar)
                        .addInt(ZPDefaultDataKeys.StoC__DAY_TIME_CYCLE_TICKS_FREEZE, ZPWorldConfig.WORLD_DAY_SLOWDOWN_CYCLE_TICKING::getVar)
                        .addInt(ZPDefaultDataKeys.StoC__NIGHT_TIME_CYCLE_TICKS_FREEZE, ZPWorldConfig.WORLD_NIGHT_SLOWDOWN_CYCLE_TICKING::getVar)
                        .addFloat(ZPDefaultDataKeys.StoC__DARKNESS_FACTOR, ZPWorldConfig.DARKNESS_GAMMA_STATIC_FACTOR_SERVER_SIDE::getVar)
        );
    }

    @Override
    public void preInitialize() {
    }

    @Override
    public void postInitialize() {
    }
}