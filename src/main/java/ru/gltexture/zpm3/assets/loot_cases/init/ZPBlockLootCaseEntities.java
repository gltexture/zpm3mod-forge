package ru.gltexture.zpm3.assets.loot_cases.init;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.loot_cases.instances.block_entities.ZPLootCaseBlockEntity;
import ru.gltexture.zpm3.assets.loot_cases.instances.blocks.ZPDefaultBlockLootCase;
import ru.gltexture.zpm3.assets.loot_cases.rendering.ZPLootCaseRenderer;
import ru.gltexture.zpm3.engine.core.ZPRegistryConveyor;
import ru.gltexture.zpm3.engine.registry.ZPRegistry;
import ru.gltexture.zpm3.engine.registry.ZPRegistryCollections;

import java.util.Arrays;

public class ZPBlockLootCaseEntities extends ZPRegistry<BlockEntityType<?>> {
    public static RegistryObject<BlockEntityType<ZPLootCaseBlockEntity>> loot_case_block_entity;

    public ZPBlockLootCaseEntities() {
        super(ZPRegistryConveyor.Target.BLOCK_ENTITY_TYPE);
    }

    @Override
    @SuppressWarnings("DataFlowIssue")
    protected void runRegister(@NotNull ZPRegSupplier<BlockEntityType<?>> regSupplier) {
        ZPBlockLootCaseEntities.loot_case_block_entity = regSupplier.register("loot_case_block_entity",
                () -> {
                    ZPDefaultBlockLootCase[] zpBlocks = ZPRegistryCollections.getCollectionById(ZPLootCases.class, "lootCases")
                            .stream()
                            .map(RegistryObject::get)
                            .toArray(ZPDefaultBlockLootCase[]::new);
                    return Builder.of(ZPLootCaseBlockEntity::new, Arrays.stream(zpBlocks).toArray(Block[]::new)).build(null);
                }).afterObjectCreated(Dist.CLIENT, (e, utils) -> {
            utils.blockEntities().matchBlockEntityRendering(e, ZPLootCaseRenderer::new);
        }).end();
    }

    @Override
    protected void postRegister(String name, RegistryObject<BlockEntityType<?>> object) {
        super.postRegister(name, object);
    }

    @Override
    public int priority() {
        return 0;
    }

    @Override
    public @NotNull String getID() {
        return this.getClass().getSimpleName();
    }
}