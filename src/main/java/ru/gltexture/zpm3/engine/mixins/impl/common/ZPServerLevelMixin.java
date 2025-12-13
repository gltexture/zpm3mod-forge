package ru.gltexture.zpm3.engine.mixins.impl.common;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.storage.ServerLevelData;
import net.minecraft.world.level.storage.WritableLevelData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.gltexture.zpm3.assets.common.global.ZPConstants;
import ru.gltexture.zpm3.engine.world.GlobalBlocksDestroyMemory;
import ru.gltexture.zpm3.engine.mixins.ext.IZPLevelExt;

import java.util.function.BooleanSupplier;

@Mixin(ServerLevel.class)
public abstract class ZPServerLevelMixin implements IZPLevelExt {
    @Shadow @Final private boolean tickTime;
    @Shadow public abstract ServerLevel getLevel();
    @Shadow @Final private ServerLevelData serverLevelData;
    @Shadow @Final private MinecraftServer server;

    @Shadow
    public abstract void setDayTime(long pTime);

    @Unique private final GlobalBlocksDestroyMemory globalBlocksDestroyMemory = new GlobalBlocksDestroyMemory();

    @Unique private int zTick;

    @Inject(method = "tick", at = @At("TAIL"))
    private void tickPost(BooleanSupplier pHasTimeLeft, CallbackInfo ci) {
        this.getGlobalBlocksDestroyMemory().refreshMemory(this.getLevel());
    }

    @Inject(method = "tickTime", at = @At("HEAD"), cancellable = true)
    private void tickTime(CallbackInfo ci) {
        boolean isNight = this.getLevel().isNight();
        if (this.zTick++ >= ((isNight ? ZPConstants.WORLD_NIGHT_TIME_SLOWDOWN_CYCLE_TICKING : ZPConstants.WORLD_DAY_TIME_SLOWDOWN_CYCLE_TICKING) - 1)) {
            if (this.tickTime) {
                long i = this.getLevel().getLevelData().getGameTime() + 1L;
                this.serverLevelData.setGameTime(i);
                this.serverLevelData.getScheduledEvents().tick(this.server, i);
                if (this.getLevel().getLevelData().getGameRules().getBoolean(GameRules.RULE_DAYLIGHT)) {
                    this.setDayTime(this.getLevel().getLevelData().getDayTime() + 1L);
                }
            }
            this.zTick = 0;
        }
        ci.cancel();
    }

    @Override
    public GlobalBlocksDestroyMemory getGlobalBlocksDestroyMemory() {
        return this.globalBlocksDestroyMemory;
    }
}
