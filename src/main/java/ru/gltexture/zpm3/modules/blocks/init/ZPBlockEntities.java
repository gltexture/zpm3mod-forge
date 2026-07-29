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

package ru.gltexture.zpm3.modules.blocks.init;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.helpers.ZPBlockEntityRenderMatchHelper;
import ru.gltexture.zpm3.engine.registry.ZPCommonRegistry;
import ru.gltexture.zpm3.engine.service.ZPUtility;
import ru.gltexture.zpm3.modules.blocks.instances.block_entities.ZPBarbaredWireBlockEntity;
import ru.gltexture.zpm3.modules.blocks.instances.block_entities.ZPCampfireBlockEntity;
import ru.gltexture.zpm3.modules.blocks.instances.block_entities.ZPFadingBlockEntity;
import ru.gltexture.zpm3.engine.core.ZPRegistryConveyor;
import ru.gltexture.zpm3.engine.registry.ZPRegistryCollections;
import ru.gltexture.zpm3.engine.service.Pair;
import ru.gltexture.zpm3.modules.blocks.rendering.ZPCampfireRenderer;

import java.util.Arrays;
import java.util.stream.Stream;

public class ZPBlockEntities extends ZPCommonRegistry<BlockEntityType<?>> {
    public static RegistryObject<BlockEntityType<ZPCampfireBlockEntity>> campfire_block_entity;
    public static RegistryObject<BlockEntityType<ZPFadingBlockEntity>> fading_block_entity;
    public static RegistryObject<BlockEntityType<ZPBarbaredWireBlockEntity>> barbared_wire_block_entity;

    public ZPBlockEntities() {
        super(ZPRegistryConveyor.Target.BLOCK_ENTITY_TYPE);
    }

    @Override
    @SuppressWarnings("all")
    protected void runRegister(@NotNull ZPRegSupplier<BlockEntityType<?>> regSupplier) {
        ZPBlockEntities.fading_block_entity = regSupplier.register("fading_block_entity",
                () -> {
                    Block[] zpBlocks = ZPRegistryCollections.getCollectionById(
                                    Pair.of(ZPLanternBlocks.class, "lanterns"),
                                    Pair.of(ZPTorchBlocks.class, "torches"),
                                    Pair.of(ZPBlocks.class, "fadingLiquids"),
                                    Pair.of(ZPCampfireBlocks.class, "campfires")
                            )
                            .stream()
                            .map(e -> (Block) e.get())
                            .toArray(Block[]::new);
                    Block[] allBlocks = Stream.concat(Arrays.stream(zpBlocks), Stream.of(Blocks.TORCH, Blocks.WALL_TORCH, Blocks.JACK_O_LANTERN, Blocks.LAVA, Blocks.LANTERN)).toArray(Block[]::new);
                    return Builder.of(ZPFadingBlockEntity::new, allBlocks).build(null);
                }).end();

        ZPBlockEntities.barbared_wire_block_entity = regSupplier.register("barbared_wire_block_entity",
                () -> {
                    return Builder.of(ZPBarbaredWireBlockEntity::new, new Block[] {ZPBlocks.barbared_wire.get()}).build(null);
                }).end();

        ZPBlockEntities.campfire_block_entity = regSupplier.register("campfire_block_entity",
                () -> {
                    return Builder.of(ZPCampfireBlockEntity::new, new Block[] {ZPCampfireBlocks.campfire2.get()}).build(null);
                }).end();

        ZPUtility.sides().onlyClient(() -> {
            ZPBlockEntityRenderMatchHelper.matchBlockEntityRendering(ZPBlockEntities.campfire_block_entity, ZPCampfireRenderer::new);
        });
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