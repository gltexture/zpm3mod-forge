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

package ru.gltexture.zpm3.modules.loot_cases.instances.blocks;

import net.minecraft.client.resources.model.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.modules.loot_cases.init.ZPBlockLootCaseEntities;
import ru.gltexture.zpm3.modules.loot_cases.instances.block_entities.ZPLootCaseBlockEntity;
import ru.gltexture.zpm3.modules.loot_cases.loot_tables.ZPLootTable;
import ru.gltexture.zpm3.modules.loot_cases.rendering.ZPLootCaseRenderer;
import ru.gltexture.zpm3.engine.instances.blocks.ZPChestBlock;

public class ZPDefaultBlockLootCase extends ZPChestBlock {
    @OnlyIn(Dist.CLIENT)
    private LootCaseTextureMaterials lootCaseTextureMaterials;
    private final String blockTexture;
    private final ResourceLocation connectedLootTable;
    private final int lootRespawnTime;

    public ZPDefaultBlockLootCase(@NotNull Properties pProperties, @NotNull String blockTexture, @NotNull ResourceLocation connectedLootTable, int lootRespawnTime) {
        super(pProperties, () -> ZPBlockLootCaseEntities.loot_case_block_entity.get());
        this.blockTexture = blockTexture;
        this.connectedLootTable = connectedLootTable;
        this.lootRespawnTime = lootRespawnTime;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return ZPDefaultBlockLootCase.createTickerHelper(type, ZPBlockLootCaseEntities.loot_case_block_entity.get(), ZPLootCaseBlockEntity::tick);
    }

    @Override
    public @NotNull BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new ZPLootCaseBlockEntity(pPos, pState);
    }

    @OnlyIn(Dist.CLIENT)
    public LootCaseTextureMaterials getLootCaseTextureMaterials() {
        //LAZY
        if (this.lootCaseTextureMaterials == null) {
            this.lootCaseTextureMaterials = new LootCaseTextureMaterials(
                    ZPLootCaseRenderer.chestMaterial(this.blockTexture),
                    ZPLootCaseRenderer.chestMaterial(this.blockTexture + "_left"),
                    ZPLootCaseRenderer.chestMaterial(this.blockTexture + "_right")
            );
        }
        return this.lootCaseTextureMaterials;
    }

    public int getLootRespawnTime() {
        return this.lootRespawnTime;
    }

    public ResourceLocation getConnectedLootTable() {
        return this.connectedLootTable;
    }

    @OnlyIn(Dist.CLIENT)
    public record LootCaseTextureMaterials(Material LOOTCASE_LOCATION, Material LOOTCASE_LOCATION_LEFT, Material LOOTCASE_LOCATION_RIGHT) { };
}
