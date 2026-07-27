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

package ru.gltexture.zpm3.modules.entity.rendering.entities.zombies.base;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.modules.entity.instances.mobs.zombies.ZPAbstractZombie;
import ru.gltexture.zpm3.modules.entity.rendering.entities.models.ZPCommonZombieModel;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;

import java.util.ArrayList;
import java.util.List;

public abstract class ZPDefaultZombieRender <R extends ZPAbstractZombie> extends ZPAbstractZombieRenderer<R, ZPCommonZombieModel<R>> {
    private List<ResourceLocation> ZOMBIE_LOCATION;

    public ZPDefaultZombieRender(EntityRendererProvider.Context p_174456_, @NotNull String path, int maxTextures) {
        this(p_174456_, ModelLayers.ZOMBIE, ModelLayers.ZOMBIE_INNER_ARMOR, ModelLayers.ZOMBIE_OUTER_ARMOR);
        this.ZOMBIE_LOCATION = new ArrayList<>();
        for (int i = 0; i < maxTextures; i++) {
            this.ZOMBIE_LOCATION.add(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), String.format(path, i)));
        }
    }

    public ZPDefaultZombieRender(EntityRendererProvider.Context pContext, ModelLayerLocation pZombieLayer, ModelLayerLocation pInnerArmor, ModelLayerLocation pOuterArmor) {
        super(pContext, new ZPCommonZombieModel<>(pContext.bakeLayer(pZombieLayer)), new ZPCommonZombieModel<>(pContext.bakeLayer(pInnerArmor)), new ZPCommonZombieModel<>(pContext.bakeLayer(pOuterArmor)));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull R pEntity) {
        return this.ZOMBIE_LOCATION.get(pEntity.getSkinID());
    }
}
