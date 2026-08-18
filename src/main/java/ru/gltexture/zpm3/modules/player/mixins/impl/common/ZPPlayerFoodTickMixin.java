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

import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.gltexture.zpm3.modules.mob_effects.init.ZPMobEffects;
import ru.gltexture.zpm3.modules.mob_effects.utils.ZPEffectUtils;

@Mixin(FoodData.class)
public abstract class ZPPlayerFoodTickMixin {
    @Shadow
    private int lastFoodLevel;

    @Shadow
    private int foodLevel;

    @Shadow
    private float exhaustionLevel;

    @Shadow
    private float saturationLevel;

    @Shadow
    private int tickTimer;

    @Shadow
    public abstract void addExhaustion(float pExhaustion);

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    public void tick(Player pPlayer, CallbackInfo ci) {
        Difficulty difficulty = pPlayer.level().getDifficulty();
        this.lastFoodLevel = this.foodLevel;
        if (this.exhaustionLevel > 4.0F) {
            this.exhaustionLevel -= 4.0F;
            if (this.saturationLevel > 0.0F) {
                this.saturationLevel = Math.max(this.saturationLevel - 1.0F, 0.0F);
            } else if (difficulty != Difficulty.PEACEFUL) {
                this.foodLevel = Math.max(this.foodLevel - 1, 0);
            }
        }

        final boolean bleeding = ZPEffectUtils.isBleeding(pPlayer);
        final boolean flag = pPlayer.level().getGameRules().getBoolean(GameRules.RULE_NATURAL_REGENERATION);
        if (flag && this.saturationLevel > 0.0F && pPlayer.isHurt() && this.foodLevel >= 20) {
            ++this.tickTimer;
            if (this.tickTimer >= 10) {
                float f = Math.min(this.saturationLevel, 6.0F);
                if (!bleeding) {
                    pPlayer.heal(f / 6.0F);
                }
                this.addExhaustion(f);
                this.tickTimer = 0;
            }
        } else if (flag && this.foodLevel >= 18 && pPlayer.isHurt()) {
            ++this.tickTimer;
            if (this.tickTimer >= 80) {
                if (!bleeding) {
                    pPlayer.heal(1.0F);
                }
                this.addExhaustion(6.0F);
                this.tickTimer = 0;
            }
        } else if (this.foodLevel <= 0) {
            ++this.tickTimer;
            if (this.tickTimer >= 80) {
                if (pPlayer.getHealth() > 10.0F || difficulty == Difficulty.HARD || pPlayer.getHealth() > 1.0F && difficulty == Difficulty.NORMAL) {
                    pPlayer.hurt(pPlayer.damageSources().starve(), 1.0F);
                }

                this.tickTimer = 0;
            }
        } else {
            this.tickTimer = 0;
        }
        ci.cancel();
    }
}
