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

package ru.gltexture.zpm3.modules.player.mixins.impl.common;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import ru.gltexture.zpm3.engine.core.config.builtin.ZPNetworkConfig;
import ru.gltexture.zpm3.modules.common.init.ZPDamageTypes;
import ru.gltexture.zpm3.modules.net_pack.data.ZPNetSyncDataPack;
import ru.gltexture.zpm3.modules.net_pack.packets.ZPNetCheckPacket;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.modules.player.mixins.ext.IZPPlayerMixinExt;

@Mixin(Player.class)
public abstract class ZPPlayerMixin implements IZPPlayerMixinExt {
    @Shadow(remap = false)
    public abstract void setForcedPose(@Nullable Pose pose);

    @Unique
    private int zpm3forge$ping;

    @Unique
    private long zpm3forge$lastSentTime = 0;

    @Unique
    private boolean zpm3forge$waitingResponse = false;

    @Unique
    private int zpm3forge$pingTickTime = 0;

    @Unique
    private boolean zpm3forge$playerLying = false;

    @Unique
    private ZPNetSyncDataPack zpm3forge$zpNetDataPack_fromClient;

    @Override
    public ZPNetSyncDataPack zpm3forge$zpNetDataPack_fromClient() {
        if (this.zpm3forge$zpNetDataPack_fromClient == null) {
            this.zpm3forge$zpNetDataPack_fromClient = ZombiePlague3.net().createdNetSyncDataPack_CtoS();
        }
        return this.zpm3forge$zpNetDataPack_fromClient;
    }

    //@Inject(method = "jumpFromGround", at = @At("HEAD"), cancellable = true)
    //public void jumpFromGround(CallbackInfo ci) {
    //    if (this.zpm3forge$isLying()) {
    //        ci.cancel();
    //    }
    //}

    @Inject(method = "tick", at = @At("TAIL"))
    private void onTick(CallbackInfo ci) {
        if ((Object) this instanceof ServerPlayer) {
           // if (this.pingTickTime++ >= ZPPlayerAngryZombiesTrackerMixin.updateConst * 3) {
           //     this.gotPackets = 0;
           //     this.pingTickTime = 0;
           //     this.ping = 0;
           // }
            return;
        }
        long now = System.currentTimeMillis();
        if (((now - this.zpm3forge$lastSentTime)) >= 5000) {
            this.zpm3forge$waitingResponse = false;
        }
        if (!this.zpm3forge$waitingResponse) {
            if (this.zpm3forge$pingTickTime++ >= ZPNetworkConfig.PLAYER_PING_PACKET_FREQ.getVar()) {
                ZombiePlague3.net().sendToServer(new ZPNetCheckPacket(((Player) (Object) this).getId()));
                this.zpm3forge$lastSentTime = System.currentTimeMillis();
                this.zpm3forge$waitingResponse = true;
                this.zpm3forge$pingTickTime = 0;
            }
        }
    }

    @Inject(method = "updatePlayerPose", at = @At("TAIL"), cancellable = true)
    protected void updatePlayerPose(CallbackInfo ci) {
        Player player = (Player) (Object) this;
        if (this.zpm3forge$isLying()) {
            if (IZPPlayerMixinExt.checkIfPlayerCanLieOnGround(player)) {
                player.setPose(Pose.SWIMMING);
                ci.cancel();
            }
        }
    }

    @Inject(method = "hurtArmor", at = @At("HEAD"), cancellable = true)
    public void hurtArmor(DamageSource pDamageSource, float pDamage, CallbackInfo ci) {
        if (pDamageSource.type().equals(ZPDamageTypes.getDamageType((ServerLevel) ((Player) (Object) this).level(), ZPDamageTypes.zp_bleeding).get())) {
            ci.cancel();
        }
    }

    @Inject(method = "hurtHelmet", at = @At("HEAD"), cancellable = true)
    public void hurtHelmet(DamageSource pDamageSource, float pDamageAmount, CallbackInfo ci) {
        if (pDamageSource.type().equals(ZPDamageTypes.getDamageType((ServerLevel) ((Player) (Object) this).level(), ZPDamageTypes.zp_bleeding).get())) {
            ci.cancel();
        }
    }

    @Override
    @Unique
    public boolean zpm3forge$isLying() {
        return this.zpm3forge$playerLying;
    }

    @Override
    @Unique
    public void zpm3forge$setLying(boolean zpm3forge$playerLying) {
        this.zpm3forge$playerLying = zpm3forge$playerLying;
    }

    @Override
    public void zpm3forge$getResponseNetCheckFromServer() {
        long now = System.currentTimeMillis();
        this.zpm3forge$ping = (int) (now - this.zpm3forge$lastSentTime);
        this.zpm3forge$waitingResponse = false;
    }

    @Override
    public void zpm3forge$getResponseNetCheckFromClient() {
        if ((Object) this instanceof ServerPlayer serverPlayer) {
            long now = System.currentTimeMillis();
            this.zpm3forge$ping = (int) (now - this.zpm3forge$lastSentTime);
            this.zpm3forge$ping -= (ZPNetworkConfig.PLAYER_PING_PACKET_FREQ.getVar() / 20) * 1000;
            ZombiePlague3.net().sendToPlayer(new ZPNetCheckPacket(((Player) (Object) this).getId()), serverPlayer);
            this.zpm3forge$lastSentTime = now;
           // this.gotPackets++;
            //this.ping += (int) (now - this.lastSentTime);
        }
    }

    @Override
    public int zpm3forge$getPing() {
        return this.zpm3forge$ping;
    }
}
