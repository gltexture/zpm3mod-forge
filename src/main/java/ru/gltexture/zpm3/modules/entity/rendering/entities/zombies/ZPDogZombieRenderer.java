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

package ru.gltexture.zpm3.modules.entity.rendering.entities.zombies;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import ru.gltexture.zpm3.engine.core.config.builtin.ZPZombieConfig;
import ru.gltexture.zpm3.modules.entity.instances.mobs.zombies.ZPDogZombie;
import ru.gltexture.zpm3.modules.entity.rendering.entities.layers.ZPDogZombieItemLayer;
import ru.gltexture.zpm3.modules.entity.rendering.entities.models.ZPCommonDogZombieModel;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class ZPDogZombieRenderer extends MobRenderer<ZPDogZombie, ZPCommonDogZombieModel<ZPDogZombie>> {
    private final List<ResourceLocation> ZOMBIE_LOCATION;

    public ZPDogZombieRenderer(EntityRendererProvider.Context p_174452_) {
        super(p_174452_, new ZPCommonDogZombieModel<>(p_174452_.bakeLayer(ModelLayers.WOLF)), 0.5F);
        this.addLayer(new ZPDogZombieItemLayer(this, p_174452_.getItemInHandRenderer()));

        this.ZOMBIE_LOCATION = new ArrayList<>();
        for (int i = 0; i < ZPZombieConfig.TOTAL_COMMON_ZOMBIE_TEXTURES.getVar(); i++) {
            this.ZOMBIE_LOCATION.add(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), String.format("textures/entity/zombie_dog/wolf%d.png", i)));
        }
    }

    public void render(@NotNull ZPDogZombie pEntity, float pEntityYaw, float pPartialTicks, @NotNull PoseStack pPoseStack, @NotNull MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull ZPDogZombie pEntity) {
        return this.ZOMBIE_LOCATION.get(pEntity.getSkinID());
    }
}