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
import ru.gltexture.zpm3.engine.core.config.builtin.ZPWorldConfig;


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
        if (this.zpm3forge$zTick++ >= ((isNight ? ZPWorldConfig.WORLD_NIGHT_SLOWDOWN_CYCLE_TICKING.getVar() : ZPWorldConfig.WORLD_DAY_SLOWDOWN_CYCLE_TICKING.getVar()) - 1)) { //TODO
            this.setGameTime(this.getLevelData().getGameTime() + 1L);
            if (this.getLevelData().getGameRules().getBoolean(GameRules.RULE_DAYLIGHT)) {
                this.setDayTime(this.getLevelData().getDayTime() + 1L);
            }
            this.zpm3forge$zTick = 0;
        }
        ci.cancel();
    }
}
