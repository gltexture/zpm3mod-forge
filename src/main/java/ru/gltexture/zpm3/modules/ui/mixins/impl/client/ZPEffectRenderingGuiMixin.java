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

package ru.gltexture.zpm3.modules.ui.mixins.impl.client;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.world.effect.MobEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import ru.gltexture.zpm3.modules.mob_effects.client.ZPFakeClientEffectInstance;
import ru.gltexture.zpm3.modules.mob_effects.client.ZPLocalPlayerFakeEffectsManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@Mixin(Gui.class)
public abstract class  ZPEffectRenderingGuiMixin {
    @Unique
    private List<MobEffectInstance> zpm3forge$store_result;

    @ModifyVariable(method = "renderEffects", at = @At(value = "STORE"), ordinal = 0)
    private Collection<MobEffectInstance> zp$appendFakeEffects(Collection<MobEffectInstance> original) {
        if (ZPLocalPlayerFakeEffectsManager.INSTANCE.getEffects().isEmpty()) {
            return original;
        }
        List<MobEffectInstance> result = new ArrayList<>(original.size() + ZPLocalPlayerFakeEffectsManager.INSTANCE.getEffects().size());
        result.addAll(ZPLocalPlayerFakeEffectsManager.INSTANCE.getEffects().values());
        result.addAll(original);
        this.zpm3forge$store_result = result;
        return result;
    }
}