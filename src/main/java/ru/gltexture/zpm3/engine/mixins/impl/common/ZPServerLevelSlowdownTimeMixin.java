package ru.gltexture.zpm3.engine.mixins.impl.common;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.storage.ServerLevelData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import ru.gltexture.zpm3.engine.core.config.builtin.ZPWorldConfig;
import ru.gltexture.zpm3.engine.world.ZPGlobalBlocksDestroyMemory;
import ru.gltexture.zpm3.engine.mixins.ext.IZPLevelExt;

import java.util.function.BooleanSupplier;

@Mixin(ServerLevel.class)
public abstract class ZPServerLevelSlowdownTimeMixin implements IZPLevelExt {
    @Shadow @Final private boolean tickTime;
    @Shadow public abstract ServerLevel getLevel();
    @Shadow @Final private ServerLevelData serverLevelData;
    @Shadow @Final private MinecraftServer server;

    @Shadow
    public abstract void setDayTime(long pTime);

    @Unique private final ZPGlobalBlocksDestroyMemory zpm3forge$globalBlocksDestroyMemory = new ZPGlobalBlocksDestroyMemory();

    @Unique private int zpm3forge$zTick;

    @Inject(method = "tick", at = @At("TAIL"))
    private void tickPost(BooleanSupplier pHasTimeLeft, CallbackInfo ci) {
        this.zpm3forge$getGlobalBlocksDestroyMemory().refreshMemory(this.getLevel());
    }

    @Inject(method = "tickTime", at = @At("HEAD"), cancellable = true)
    private void tickTime(CallbackInfo ci) {
        boolean isNight = this.getLevel().isNight();
        final long i = this.getLevel().getLevelData().getGameTime() + 1L;
        if (this.zpm3forge$zTick++ >= ((isNight ? ZPWorldConfig.WORLD_NIGHT_SLOWDOWN_CYCLE_TICKING.getVar() : ZPWorldConfig.WORLD_DAY_SLOWDOWN_CYCLE_TICKING.getVar()) - 1)) {
            if (this.tickTime) {
                if (this.getLevel().getLevelData().getGameRules().getBoolean(GameRules.RULE_DAYLIGHT)) {
                    this.setDayTime(this.getLevel().getLevelData().getDayTime() + 1L);
                }
            }
            this.zpm3forge$zTick = 0;
        }
        this.serverLevelData.setGameTime(i);
        this.serverLevelData.getScheduledEvents().tick(this.server, i);
        ci.cancel();
    }

    @Override
    public ZPGlobalBlocksDestroyMemory zpm3forge$getGlobalBlocksDestroyMemory() {
        return this.zpm3forge$globalBlocksDestroyMemory;
    }
}
