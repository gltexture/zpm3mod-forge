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
import ru.gltexture.zpm3.engine.network.handler.ZPNetworkHandlerClient;
import ru.gltexture.zpm3.engine.core.config.builtin.ZPWorldConfig;
import ru.gltexture.zpm3.modules.net_pack.ZPNetPackModule;
import ru.gltexture.zpm3.modules.net_pack.data.vars.ZPNetDataInt;


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
        final int nightSlowDownTime = ZombiePlague3.netClient().getNetStaticDataSyncer().getVar(ZPNetPackModule.StoC__NIGHT_TIME_CYCLE_TICKS_FREEZE).orElse(new ZPNetDataInt(ZPWorldConfig.WORLD_NIGHT_SLOWDOWN_CYCLE_TICKING.getVar())).getValue();
        final int daySlowDownTime = ZombiePlague3.netClient().getNetStaticDataSyncer().getVar(ZPNetPackModule.StoC__DAY_TIME_CYCLE_TICKS_FREEZE).orElse(new ZPNetDataInt(ZPWorldConfig.WORLD_DAY_SLOWDOWN_CYCLE_TICKING.getVar())).getValue();

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
