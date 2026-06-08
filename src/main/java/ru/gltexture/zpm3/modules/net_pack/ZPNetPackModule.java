package ru.gltexture.zpm3.modules.net_pack;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZPNetworkHandler;
import ru.gltexture.zpm3.engine.service.ZPUtility;
import ru.gltexture.zpm3.modules.common.global.ZPConstants;
import ru.gltexture.zpm3.modules.net_pack.data.DefaultDataKeys;
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
        moduleEntry.addNetworkPacket(new ZPNetwork.PacketData<>(5, ZPBlockCrack.class, ZPBlockCrack.encoder(), ZPBlockCrack.decoder()));
        //moduleEntry.addNetworkPacket(new ZPNetwork.PacketData<>(6, ZPSendGlobalSettings_StoC.class, ZPSendGlobalSettings_StoC.encoder(), ZPSendGlobalSettings_StoC.decoder()));
        moduleEntry.addNetworkPacket(new ZPNetwork.PacketData<>(7, ZPBloodPainFXPacket.class, ZPBloodPainFXPacket.encoder(), ZPBloodPainFXPacket.decoder()));
        moduleEntry.addNetworkPacket(new ZPNetwork.PacketData<>(8, ZPSyncConfigSettings.class, ZPSyncConfigSettings.encoder(), ZPSyncConfigSettings.decoder()));
        moduleEntry.addNetworkPacket(new ZPNetwork.PacketData<>(9, ZPPlayerWantToPickUpItem.class, ZPPlayerWantToPickUpItem.encoder(), ZPPlayerWantToPickUpItem.decoder()));
        moduleEntry.addNetworkPacket(new ZPNetwork.PacketData<>(10, ZPBulletBloodFXPacket.class, ZPBulletBloodFXPacket.encoder(), ZPBulletBloodFXPacket.decoder()));

        ZPUtility.sides().onlyClient(() -> {
            moduleEntry.registerNetSyncedConfigData_ClientToServer(
                    new ZPNetworkHandler.NetSyncDataFabric.Builder()
                            .addBoolean(DefaultDataKeys.CtoS__PICK_UP_ON_KEY, () -> ZPConstants.PICK_UP_ON_F)
            );
        });

        moduleEntry.registerNetSyncedConfigData_ServerToClient(
                new ZPNetworkHandler.NetSyncDataFabric.Builder()
                        .addBoolean(DefaultDataKeys.StoC__DARKNESS_ENABLED, () -> ZPConstants.ENABLE_HARDCORE_DARKNESS_SERVER_SIDE)
                        .addBoolean(DefaultDataKeys.StoC__SERVER_PICK_UP_ON_F, () -> ZPConstants.PICK_UP_ON_F)
                        .addInt(DefaultDataKeys.StoC__DAY_TIME_CYCLE_TICKS_FREEZE, () -> ZPConstants.WORLD_DAY_SLOWDOWN_CYCLE_TICKING)
                        .addInt(DefaultDataKeys.StoC__NIGHT_TIME_CYCLE_TICKS_FREEZE, () -> ZPConstants.WORLD_NIGHT_SLOWDOWN_CYCLE_TICKING)
                        .addFloat(DefaultDataKeys.StoC__DARKNESS_FACTOR, () -> ZPConstants.DARKNESS_GAMMA_STATIC_FACTOR_SERVER_SIDE)
        );
    }

    @Override
    public void preInitialize() {
    }

    @Override
    public void postInitialize() {
    }
}