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

package ru.gltexture.zpm3.modules.loot_cases.rendering;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.modules.loot_cases.instances.block_entities.ZPLootCaseBlockEntity;

public class ZPLootCaseItemRenderer extends BlockEntityWithoutLevelRenderer {
    private final ZPLootCaseBlockEntity be;

    public ZPLootCaseItemRenderer(BlockEntityRenderDispatcher dispatcher, EntityModelSet models, Block block) {
        super(dispatcher, models);
        this.be = new ZPLootCaseBlockEntity(BlockPos.ZERO, block.defaultBlockState());
    }

    @Override
    public void renderByItem(@NotNull ItemStack stack, @NotNull ItemDisplayContext ctx, @NotNull PoseStack pose, @NotNull MultiBufferSource buffer, int light, int overlay) {
        Minecraft.getInstance().getBlockEntityRenderDispatcher().renderItem(be, pose, buffer, light, overlay);
    }
}