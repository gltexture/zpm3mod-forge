package ru.gltexture.zpm3.assets.loot_cases.rendering;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.loot_cases.instances.block_entities.ZPLootCaseBlockEntity;
import ru.gltexture.zpm3.assets.loot_cases.instances.blocks.ZPDefaultBlockLootCase;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;


public class ZPLootCaseRenderer<T extends ZPLootCaseBlockEntity> extends ChestRenderer<T> {
    public static Material DEFAULT_LOOTCASE_LOCATION;
    public static Material DEFAULT_LOOTCASE_LOCATION_LEFT;
    public static Material DEFAULT_LOOTCASE_LOCATION_RIGHT ;

    public ZPLootCaseRenderer(BlockEntityRendererProvider.Context pContext) {
        super(pContext);
        ZPLootCaseRenderer.DEFAULT_LOOTCASE_LOCATION = ZPLootCaseRenderer.chestMaterial("tier1");
        ZPLootCaseRenderer.DEFAULT_LOOTCASE_LOCATION_LEFT = ZPLootCaseRenderer.chestMaterial("tier1_left");
        ZPLootCaseRenderer.DEFAULT_LOOTCASE_LOCATION_RIGHT = ZPLootCaseRenderer.chestMaterial("tier1_right");
    }

    @Override
    public void render(@NotNull T pBlockEntity, float pPartialTick, @NotNull PoseStack pPoseStack, @NotNull MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        super.render(pBlockEntity, pPartialTick, pPoseStack, pBuffer, pPackedLight, pPackedOverlay);
    }

    private static Material chooseMaterial(ChestType pChestType, Material pDoubleMaterial, Material pLeftMaterial, Material pRightMaterial) {
        return switch (pChestType) {
            case LEFT -> pLeftMaterial;
            case RIGHT -> pRightMaterial;
            default -> pDoubleMaterial;
        };
    }

    public static Material chestMaterial(String pChestName) {
        return new Material(Sheets.CHEST_SHEET, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "entity/chest/" + pChestName));
    }

    protected @NotNull Material getMaterial(@NotNull T blockEntity, @NotNull ChestType chestType) {
        final BlockState blockState = blockEntity.getBlockState();
        final Block block = blockState.getBlock();
        if (block instanceof ZPDefaultBlockLootCase blockLootCase) {
            return ZPLootCaseRenderer.chooseMaterial(chestType,
                    blockLootCase.getLootCaseTextureMaterials().LOOTCASE_LOCATION(),
                    blockLootCase.getLootCaseTextureMaterials().LOOTCASE_LOCATION_LEFT(),
                    blockLootCase.getLootCaseTextureMaterials().LOOTCASE_LOCATION_RIGHT()
            );
        }
        return ZPLootCaseRenderer.chooseMaterial(chestType, ZPLootCaseRenderer.DEFAULT_LOOTCASE_LOCATION, ZPLootCaseRenderer.DEFAULT_LOOTCASE_LOCATION_LEFT, ZPLootCaseRenderer.DEFAULT_LOOTCASE_LOCATION_RIGHT);
    }
}