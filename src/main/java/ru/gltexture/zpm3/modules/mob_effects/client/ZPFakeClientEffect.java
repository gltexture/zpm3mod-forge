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

import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientMobEffectExtensions;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.modules.mob_effects.instances.ZPDefaultMobEffect;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class ZPFakeClientEffect extends MobEffect {
    protected final Supplier<ResourceLocation> icon;
    protected final ResourceLocation id;
    protected Object effectRenderer;
    @Nullable protected String descriptionId;

    public ZPFakeClientEffect(@NotNull MobEffectCategory mobEffectCategory, @NotNull ResourceLocation id, @NotNull Supplier<ResourceLocation> icon, int color) {
        super(mobEffectCategory, color);
        this.icon = icon;
        this.id = id;
        this.initClientLater();
    }

    public @NotNull Object getEffectRendererInternal() {
        return this.effectRenderer;
    }

    private void initClientLater() {
        if (net.minecraftforge.fml.loading.FMLEnvironment.dist == net.minecraftforge.api.distmarker.Dist.CLIENT && !net.minecraftforge.fml.loading.FMLLoader.getLaunchHandler().isData()) {
            initializeClient2(properties -> {
                this.effectRenderer = properties;
            });
        }
    }

    @Override
    protected @NotNull String getOrCreateDescriptionId() {
        if (this.descriptionId == null) {
            this.descriptionId = Util.makeDescriptionId("fake_effect", this.getId());
        }
        return this.descriptionId;
    }

    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public boolean equals(Object userObject) {
        if (!(userObject instanceof ZPFakeClientEffect that)) {
            return false;
        }
        return Objects.equals(this.id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.id);
    }

    public void initializeClient2(@NotNull Consumer<IClientMobEffectExtensions> consumer) {
        consumer.accept(new ZPDefaultMobEffect.DefaultZPEffectClientExtension(true, this.icon.get()));
    }

    @Override
    public void initializeClient(@NotNull Consumer<IClientMobEffectExtensions> consumer) {
    }
}
