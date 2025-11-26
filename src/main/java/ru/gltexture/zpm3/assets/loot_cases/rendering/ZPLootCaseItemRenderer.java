package ru.gltexture.zpm3.assets.loot_cases.rendering;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.loot_cases.instances.block_entities.ZPLootCaseBlockEntity;

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