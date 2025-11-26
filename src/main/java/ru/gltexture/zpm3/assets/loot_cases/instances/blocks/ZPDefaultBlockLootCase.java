package ru.gltexture.zpm3.assets.loot_cases.instances.blocks;

import net.minecraft.client.resources.model.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.assets.loot_cases.init.ZPBlockLootCaseEntities;
import ru.gltexture.zpm3.assets.loot_cases.instances.block_entities.ZPLootCaseBlockEntity;
import ru.gltexture.zpm3.assets.loot_cases.loot_tables.ZPLootTable;
import ru.gltexture.zpm3.assets.loot_cases.rendering.ZPLootCaseRenderer;
import ru.gltexture.zpm3.engine.instances.blocks.ZPChestBlock;

public class ZPDefaultBlockLootCase extends ZPChestBlock {
    @OnlyIn(Dist.CLIENT)
    private LootCaseTextureMaterials lootCaseTextureMaterials;
    private final String blockTexture;
    private final ZPLootTable connectedLootTable;
    private final int lootRespawnTime;

    public ZPDefaultBlockLootCase(@NotNull Properties pProperties, @NotNull String blockTexture, @NotNull ZPLootTable connectedLootTable, int lootRespawnTime) {
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

    public ZPLootTable getConnectedLootTable() {
        return this.connectedLootTable;
    }

    @OnlyIn(Dist.CLIENT)
    public record LootCaseTextureMaterials(Material LOOTCASE_LOCATION, Material LOOTCASE_LOCATION_LEFT, Material LOOTCASE_LOCATION_RIGHT) { };
}
