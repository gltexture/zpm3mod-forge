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

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@OnlyIn(Dist.CLIENT)
public class ZPLocalPlayerFakeEffectsManager {
    public static final ZPLocalPlayerFakeEffectsManager INSTANCE =  new ZPLocalPlayerFakeEffectsManager();
    private final Map<ResourceLocation, ZPFakeClientEffectInstance> effects;
    private final Map<ZPFakeClientEffect, ZPFakeEffectSetOnPlayerCondition> conditions;

    public ZPLocalPlayerFakeEffectsManager() {
        this.effects = new HashMap<>();
        this.conditions = new HashMap<>();
    }

    public void tick(@NotNull LocalPlayer player) {
        this.conditions.forEach((effect, condition) -> {
            final ZPFakeEffectSetOnPlayerCondition.Data data = condition.returnDataIfShouldBeCreatedEffect(player).orElse(null);
            if (data == null) {
                this.effects.remove(effect.getId());
                return;
            }
            final ZPFakeClientEffectInstance existing = this.effects.get(effect.getId());
            if (existing == null) {
                this.effects.put(effect.getId(), new ZPFakeClientEffectInstance(effect, data.amplifier()));
            } else {
                existing.setAmplifier(data.amplifier());
            }
        });
    }

    public void createConditionToApplyFakeEffect(@NotNull ZPFakeClientEffect key, @NotNull ZPFakeEffectSetOnPlayerCondition condition) {
        this.conditions.put(key, condition);
    }

    public void put(@NotNull ZPFakeClientEffectInstance effect) {
        final ZPFakeClientEffect fake = (ZPFakeClientEffect) effect.getEffect();
        this.effects.put(fake.getId(), effect);
    }

    public void remove(@NotNull ResourceLocation id) {
        this.effects.remove(id);
    }

    public Optional<ZPFakeClientEffectInstance> get(@NotNull ResourceLocation id) {
        return Optional.ofNullable(this.effects.get(id));
    }

    public void clearAll() {
        this.effects.clear();
    }

    public @NotNull Map<ResourceLocation, ZPFakeClientEffectInstance> getEffects() {
        return Collections.unmodifiableMap(this.effects);
    }

    @FunctionalInterface
    public interface ZPFakeEffectSetOnPlayerCondition {
        Optional<Data> returnDataIfShouldBeCreatedEffect(@NotNull LocalPlayer localPlayer);
        record Data(int amplifier) { ; }
    }
}