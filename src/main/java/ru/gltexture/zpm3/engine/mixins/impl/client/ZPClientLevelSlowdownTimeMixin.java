package ru.gltexture.zpm3.engine.mixins.impl.client;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.config.builtin.ZPWorldConfig;
import ru.gltexture.zpm3.modules.net_pack.data.DefaultDataKeys;


@Mixin(ClientLevel.class)
public abstract class ZPClientLevelSlowdownTimeMixin {
    @Shadow
    public abstract void setDayTime(long pTime);

    @Shadow
    public abstract ClientLevel.ClientLevelData getLevelData();

    @Shadow
    public abstract void setGameTime(long pTime);

    @Unique private int zpm3forge$zTick;

    @Inject(method = "tickTime", at = @At("HEAD"), cancellable = true)
    private void tickTime(CallbackInfo ci) {
        boolean isNight = ((Level) (Object) this).isNight();
        final int nightSlowDownTime = ZombiePlague3.getClient_netSyncDataPack().getInt(DefaultDataKeys.StoC__NIGHT_TIME_CYCLE_TICKS_FREEZE, ZPWorldConfig.WORLD_NIGHT_SLOWDOWN_CYCLE_TICKING.getVar());
        final int daySlowDownTime = ZombiePlague3.getClient_netSyncDataPack().getInt(DefaultDataKeys.StoC__DAY_TIME_CYCLE_TICKS_FREEZE, ZPWorldConfig.WORLD_DAY_SLOWDOWN_CYCLE_TICKING.getVar());

        if (this.zpm3forge$zTick++ >= ((isNight ? nightSlowDownTime : daySlowDownTime) - 1)) {
            this.setGameTime(this.getLevelData().getGameTime() + 1L);
            if (this.getLevelData().getGameRules().getBoolean(GameRules.RULE_DAYLIGHT)) {
                this.setDayTime(this.getLevelData().getDayTime() + 1L);
            }
            this.zpm3forge$zTick = 0;
        }
        ci.cancel();
    }
}
