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

package ru.gltexture.zpm3.modules.mob_effects.client;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class ZPFakeClientEffectInstance extends MobEffectInstance {
    protected int amplifier;
    protected final ZPFakeClientEffect effect;

    public ZPFakeClientEffectInstance(ZPFakeClientEffect effect, int pAmplifier) {
        super(effect, Integer.MAX_VALUE, 0, false, true, true);
        this.amplifier = pAmplifier;
        this.effect = effect;
    }

    @Override
    public boolean isAmbient() {
        return true;
    }

    @Override
    public @NotNull MobEffect getEffect() {
        return this.effect;
    }

    @Override
    public boolean tick(@NotNull LivingEntity pEntity, @NotNull Runnable pOnExpirationRunnable) {
        return false;
    }

    public void setAmplifier(int amplifier) {
        this.amplifier = amplifier;
    }

    @Override
    public int getAmplifier() {
        return this.amplifier;
    }
}
