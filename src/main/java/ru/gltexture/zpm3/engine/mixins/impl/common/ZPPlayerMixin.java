package ru.gltexture.zpm3.engine.mixins.impl.common;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.gltexture.zpm3.assets.common.global.ZPConstants;
import ru.gltexture.zpm3.assets.net_pack.packets.ZPNetCheckPacket;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.mixins.ext.IZPPlayerMixinExt;

@Mixin(Player.class)
public abstract class ZPPlayerMixin implements IZPPlayerMixinExt {
    private static final int updateConst = ZPConstants.PLAYER_PING_PACKET_FREQ;

    @Unique
    private int ping;

    @Unique
    private long lastSentTime = 0;

    @Unique
    private boolean waitingResponse = false;

    @Unique
    private int pingTickTime = 0;

    @Unique
    private int gotPackets = 0;

    @Unique
    private boolean enabledPickUpOnF;

    @Override
    public boolean enabledPickUpOnF() {
        return this.enabledPickUpOnF;
    }

    public void setEnabledPickUpOnF(boolean enabledPickUpOnF) {
        this.enabledPickUpOnF = enabledPickUpOnF;
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void onTick(CallbackInfo ci) {
        if ((Object) this instanceof ServerPlayer) {
           // if (this.pingTickTime++ >= ZPPlayerMixin.updateConst * 3) {
           //     this.gotPackets = 0;
           //     this.pingTickTime = 0;
           //     this.ping = 0;
           // }
            return;
        }
        long now = System.currentTimeMillis();
        if (((now - this.lastSentTime)) >= 5000) {
            this.waitingResponse = false;
        }
        if (!this.waitingResponse) {
            if (this.pingTickTime++ >= ZPPlayerMixin.updateConst) {
                ZombiePlague3.net().sendToServer(new ZPNetCheckPacket(((Player) (Object) this).getId()));
                this.lastSentTime = System.currentTimeMillis();
                this.waitingResponse = true;
                this.pingTickTime = 0;
            }
        }
    }

    @Override
    public void getResultFromServer() {
        long now = System.currentTimeMillis();
        this.ping = (int) (now - this.lastSentTime);
        this.waitingResponse = false;
    }

    @Override
    public void getResultFromClient() {
        if ((Object) this instanceof ServerPlayer serverPlayer) {
            long now = System.currentTimeMillis();
            this.ping = (int) (now - this.lastSentTime);
            this.ping -= (ZPPlayerMixin.updateConst / 20) * 1000;
            ZombiePlague3.net().sendToPlayer(new ZPNetCheckPacket(((Player) (Object) this).getId()), serverPlayer);
            this.lastSentTime = now;
           // this.gotPackets++;
            //this.ping += (int) (now - this.lastSentTime);
        }
    }

    @Override
    public int getPing() {
        return this.ping;
    }
}
