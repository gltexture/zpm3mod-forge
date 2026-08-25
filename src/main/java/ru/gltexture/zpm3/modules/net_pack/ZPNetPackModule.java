/*
 *
 *  * zpm3forge
 *  * Copyright (C) 2026 gltexture
 *  *
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program. If not, see <https://www.gnu.org/licenses/>.
 *
 */

package ru.gltexture.zpm3.modules.net_pack;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.api.modules.context.IModuleClientSetupContext;
import ru.gltexture.zpm3.engine.core.api.modules.context.IModuleInitContext;
import ru.gltexture.zpm3.engine.core.api.modules.context.IModulePostInitContext;
import ru.gltexture.zpm3.engine.core.api.modules.context.IModulePreInitContext;
import ru.gltexture.zpm3.engine.core.config.builtin.ZPClientConfig;
import ru.gltexture.zpm3.engine.core.config.builtin.ZPWorldConfig;

import ru.gltexture.zpm3.modules.net_pack.data.accessors.*;
import ru.gltexture.zpm3.modules.net_pack.data.events.ZPNetSyncEvents;
import ru.gltexture.zpm3.modules.net_pack.data.vars.ZPNetDataBoolean;
import ru.gltexture.zpm3.modules.net_pack.data.vars.ZPNetDataFloat;
import ru.gltexture.zpm3.modules.net_pack.data.vars.ZPNetDataInt;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.api.modules.ZPModule;
import ru.gltexture.zpm3.engine.core.api.modules.ZPModuleData;
import ru.gltexture.zpm3.engine.network.ZPNetwork;
import ru.gltexture.zpm3.modules.net_pack.packets.C2S.ZPLyingStatePacket;
import ru.gltexture.zpm3.modules.net_pack.packets.C2S.ZPPlayerWantToPickUpItemPacket;
import ru.gltexture.zpm3.modules.net_pack.packets.C2S.ZPSyncStaticAllDataPacket_C2S;
import ru.gltexture.zpm3.modules.net_pack.packets.C2S.ZPSyncStaticDataPacket_C2S;
import ru.gltexture.zpm3.modules.net_pack.packets.MIXED.ZPGunActionPacket;
import ru.gltexture.zpm3.modules.net_pack.packets.MIXED.ZPNetCheckPacket;
import ru.gltexture.zpm3.modules.net_pack.packets.MIXED.ZPValidateAccessorsPacket;
import ru.gltexture.zpm3.modules.net_pack.packets.MIXED.ZPValidateModePacket;
import ru.gltexture.zpm3.modules.net_pack.packets.S2C.*;

public class ZPNetPackModule extends ZPModule {
    public static final ZPNetDataIntAccessor SEASICKNESS = new ZPNetDataIntAccessor(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "seasickness"));
    public static final ZPNetDataIntAccessor ACID = new ZPNetDataIntAccessor(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "acid"));
    public static final ZPNetDataIntAccessor INTOXICATION = new ZPNetDataIntAccessor(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "intoxication"));
    public static final ZPNetDataIntAccessor RADIATION = new ZPNetDataIntAccessor(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "radiation"));

    public static final ZPNetDataBooleanAccessor CtoS__PICK_UP_ON_KEY = new ZPNetDataBooleanAccessor(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "pick_up_on_key"));
    public static final ZPNetDataIntAccessor StoC__DAY_TIME_CYCLE_TICKS_FREEZE = new ZPNetDataIntAccessor(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "day_time_cycle_ticks_freeze"));
    public static final ZPNetDataIntAccessor StoC__NIGHT_TIME_CYCLE_TICKS_FREEZE = new ZPNetDataIntAccessor(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "night_time_cycle_ticks_freeze"));
    public static final ZPNetDataBooleanAccessor StoC__DARKNESS_ENABLED = new ZPNetDataBooleanAccessor(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "darkness_enabled"));
    public static final ZPNetDataBooleanAccessor StoC__SERVER_PICK_UP_ON_KEY = new ZPNetDataBooleanAccessor(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "server_pick_up_on_key"));
    public static final ZPNetDataFloatAccessor StoC__DARKNESS_FACTOR = new ZPNetDataFloatAccessor(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "darkness_factor"));

    public ZPNetPackModule(@NotNull ZPModuleData zpModuleData) {
        super(zpModuleData);}

    public ZPNetPackModule() {
    }

    @Override
    public void commonSetup() {

    }

    @Override
    public void commonShutdown() {

    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void clientSetup(@NotNull IModuleClientSetupContext context) {

    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void clientShutDown() {
    }

    @Override
    public void initialize(@NotNull IModuleInitContext context) {
        context.registerForgeEventHandlerClass(ZPNetSyncEvents.class);
        int i = 0;
        context.registerNetworkPacket(new ZPNetwork.PacketData<>(i++, ZPAcidSpreadPacket.class, ZPAcidSpreadPacket.encoder(), ZPAcidSpreadPacket.decoder()));
        context.registerNetworkPacket(new ZPNetwork.PacketData<>(i++, ZPGunActionPacket.class, ZPGunActionPacket.encoder(), ZPGunActionPacket.decoder()));
        context.registerNetworkPacket(new ZPNetwork.PacketData<>(i++, ZPBulletHitPacket.class, ZPBulletHitPacket.encoder(), ZPBulletHitPacket.decoder()));
        context.registerNetworkPacket(new ZPNetwork.PacketData<>(i++, ZPBulletTracePacket.class, ZPBulletTracePacket.encoder(), ZPBulletTracePacket.decoder()));
        context.registerNetworkPacket(new ZPNetwork.PacketData<>(i++, ZPNetCheckPacket.class, ZPNetCheckPacket.encoder(), ZPNetCheckPacket.decoder()));
        context.registerNetworkPacket(new ZPNetwork.PacketData<>(i++, ZPBlockCrackPacket.class, ZPBlockCrackPacket.encoder(), ZPBlockCrackPacket.decoder()));
        //moduleEntry.addNetworkPacket(new ZPNetwork.PacketData<>(6, ZPSendGlobalSettings_StoC.class, ZPSendGlobalSettings_StoC.encoder(), ZPSendGlobalSettings_StoC.decoder()));
        context.registerNetworkPacket(new ZPNetwork.PacketData<>(i++, ZPBloodPainFXPacket.class, ZPBloodPainFXPacket.encoder(), ZPBloodPainFXPacket.decoder()));
        //moduleEntry.registerNetworkPacket(new ZPNetwork.PacketData<>(8, ZPSyncConfigSettingsPacket.class, ZPSyncConfigSettingsPacket.encoder(), ZPSyncConfigSettingsPacket.decoder()));
        context.registerNetworkPacket(new ZPNetwork.PacketData<>(i++, ZPPlayerWantToPickUpItemPacket.class, ZPPlayerWantToPickUpItemPacket.encoder(), ZPPlayerWantToPickUpItemPacket.decoder()));
        context.registerNetworkPacket(new ZPNetwork.PacketData<>(i++, ZPBulletBloodFXPacket.class, ZPBulletBloodFXPacket.encoder(), ZPBulletBloodFXPacket.decoder()));
        context.registerNetworkPacket(new ZPNetwork.PacketData<>(i++, ZPValidateModePacket.class, ZPValidateModePacket.encoder(), ZPValidateModePacket.decoder()));
        context.registerNetworkPacket(new ZPNetwork.PacketData<>(i++, ZPLyingStatePacket.class, ZPLyingStatePacket.encoder(), ZPLyingStatePacket.decoder()));
        context.registerNetworkPacket(new ZPNetwork.PacketData<>(i++, ZPSendTheOnlyZone_Packet.class, ZPSendTheOnlyZone_Packet.encoder(), ZPSendTheOnlyZone_Packet.decoder()));
        context.registerNetworkPacket(new ZPNetwork.PacketData<>(i++, ZPSendAllZones_Packet.class, ZPSendAllZones_Packet.encoder(), ZPSendAllZones_Packet.decoder()));
        context.registerNetworkPacket(new ZPNetwork.PacketData<>(i++, ZPSyncEntityDataVarPacket.class, ZPSyncEntityDataVarPacket.encoder(), ZPSyncEntityDataVarPacket.decoder()));
        context.registerNetworkPacket(new ZPNetwork.PacketData<>(i++, ZPSyncEntityDataAllVarsPacket.class, ZPSyncEntityDataAllVarsPacket.encoder(), ZPSyncEntityDataAllVarsPacket.decoder()));
        context.registerNetworkPacket(new ZPNetwork.PacketData<>(i++, ZPSyncGroupOfEntitiesDataVarsPacket.class, ZPSyncGroupOfEntitiesDataVarsPacket.encoder(), ZPSyncGroupOfEntitiesDataVarsPacket.decoder()));
        context.registerNetworkPacket(new ZPNetwork.PacketData<>(i++, ZPValidateAccessorsPacket.class, ZPValidateAccessorsPacket.encoder(), ZPValidateAccessorsPacket.decoder()));
        context.registerNetworkPacket(new ZPNetwork.PacketData<>(i++, ZPSyncStaticDataPacket_C2S.class, ZPSyncStaticDataPacket_C2S.encoder(), ZPSyncStaticDataPacket_C2S.decoder()));
        context.registerNetworkPacket(new ZPNetwork.PacketData<>(i++, ZPSyncStaticDataPacket_S2C.class, ZPSyncStaticDataPacket_S2C.encoder(), ZPSyncStaticDataPacket_S2C.decoder()));
        context.registerNetworkPacket(new ZPNetwork.PacketData<>(i++, ZPSyncStaticAllDataPacket_S2C.class, ZPSyncStaticAllDataPacket_S2C.encoder(), ZPSyncStaticAllDataPacket_S2C.decoder()));
        context.registerNetworkPacket(new ZPNetwork.PacketData<>(i++, ZPSyncStaticAllDataPacket_C2S.class, ZPSyncStaticAllDataPacket_C2S.encoder(), ZPSyncStaticAllDataPacket_C2S.decoder()));

        context.defineNetAccessorOnEntity(Player.class, ZPNetPackModule.SEASICKNESS);
        context.defineNetAccessorOnEntity(Entity.class, ZPNetPackModule.ACID);
        context.defineNetAccessorOnEntity(LivingEntity.class, ZPNetPackModule.INTOXICATION);
        context.defineNetAccessorOnEntity(LivingEntity.class, ZPNetPackModule.RADIATION);

        context.defineStaticNetAccessor_ForServer(ZPNetPackModule.StoC__DARKNESS_ENABLED, new ZPNetDataBoolean(ZPWorldConfig.ENABLE_HARDCORE_DARKNESS_SERVER_SIDE.getVar()));
        context.defineStaticNetAccessor_ForServer(ZPNetPackModule.StoC__SERVER_PICK_UP_ON_KEY, new ZPNetDataBoolean(ZPWorldConfig.ALLOW_ITEMS_PICKUP_ON_KEY.getVar()));
        context.defineStaticNetAccessor_ForServer(ZPNetPackModule.StoC__DAY_TIME_CYCLE_TICKS_FREEZE, new ZPNetDataInt(ZPWorldConfig.WORLD_DAY_SLOWDOWN_CYCLE_TICKING.getVar()));
        context.defineStaticNetAccessor_ForServer(ZPNetPackModule.StoC__NIGHT_TIME_CYCLE_TICKS_FREEZE, new ZPNetDataInt(ZPWorldConfig.WORLD_NIGHT_SLOWDOWN_CYCLE_TICKING.getVar()));
        context.defineStaticNetAccessor_ForServer(ZPNetPackModule.StoC__DARKNESS_FACTOR, new ZPNetDataFloat(ZPWorldConfig.DARKNESS_GAMMA_STATIC_FACTOR_SERVER_SIDE.getVar()));
        context.defineStaticNetAccessor_ForClient(ZPNetPackModule.CtoS__PICK_UP_ON_KEY, new ZPNetDataBoolean(ZPClientConfig.PICK_UP_ON_KEY.getVar()));
    }

    @Override
    public void preInitialize(@NotNull IModulePreInitContext context) {
    }

    @Override
    public void postInitialize(@NotNull IModulePostInitContext context) {
    }
}