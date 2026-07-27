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

package ru.gltexture.zpm3.modules.loot_cases.init;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.modules.loot_cases.instances.block_entities.ZPLootCaseBlockEntity;
import ru.gltexture.zpm3.modules.loot_cases.instances.blocks.ZPDefaultBlockLootCase;
import ru.gltexture.zpm3.modules.loot_cases.rendering.ZPLootCaseRenderer;
import ru.gltexture.zpm3.engine.core.ZPRegistryConveyor;
import ru.gltexture.zpm3.engine.registry.ZPRegistry;
import ru.gltexture.zpm3.engine.registry.ZPRegistryCollections;
import ru.gltexture.zpm3.engine.service.ZPUtility;

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
                }).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.blockEntities().matchBlockEntityRendering(e, ZPLootCaseRenderer::new);
            });
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