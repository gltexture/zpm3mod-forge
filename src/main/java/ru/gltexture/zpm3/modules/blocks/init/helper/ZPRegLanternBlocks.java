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

package ru.gltexture.zpm3.modules.blocks.init.helper;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.helpers.gen.ZPDataGenHelper;
import ru.gltexture.zpm3.engine.helpers.gen.block_exec.DefaultBlockItemModelExecutors;
import ru.gltexture.zpm3.engine.helpers.gen.block_exec.DefaultBlockModelExecutors;
import ru.gltexture.zpm3.engine.registry.ZPRegistry;
import ru.gltexture.zpm3.engine.service.ZPUtility;
import ru.gltexture.zpm3.modules.blocks.init.ZPLanternBlocks;
import ru.gltexture.zpm3.modules.blocks.instances.blocks.lantern.ZPFadingLanternBlock;

public abstract class ZPRegLanternBlocks {
    public static void init(ZPLanternBlocks lanternBLocks, @NotNull ZPRegistry.ZPRegSupplier<Block> regSupplier) {
        lanternBLocks.initInstanceCollecting("lanterns");
        
        ZPLanternBlocks.lantern2 = regSupplier.register("lantern2", () -> new ZPFadingLanternBlock(BlockBehaviour.Properties.of().lightLevel((p_50755_) -> 12).mapColor(MapColor.METAL).forceSolidOn().requiresCorrectToolForDrops().strength(3.5F).sound(SoundType.LANTERN), () -> ZPLanternBlocks.lantern3.get())
        ).afterCreated((e, utils) -> {
            utils.loot().addSelfDropLootTable(e);
            ZPUtility.sides().onlyClient(() -> {
                utils.blocks().setBlockItemModelExecutor(e, DefaultBlockModelExecutors.getDefaultLantern(), DefaultBlockItemModelExecutors.getDefaultItemAs2DTexture("item/blocks/lantern2"));
                utils.blocks().addBlockModelSimpleOneTexture(e, ZPDataGenHelper.DEFAULT_LANTERN, "lantern", ZPDataGenHelper.LANTERN_BLOCKS_DIRECTORY);
                utils.blocks().setBlockRenderType(e, ZPDataGenHelper.CUTOUT_RENDER_TYPE);
            });
        }).end();

        ZPLanternBlocks.lantern3 = regSupplier.register("lantern3", () -> new ZPFadingLanternBlock(BlockBehaviour.Properties.of().lightLevel((p_50755_) -> 9).mapColor(MapColor.METAL).forceSolidOn().requiresCorrectToolForDrops().strength(3.5F).sound(SoundType.LANTERN), () -> ZPLanternBlocks.lantern4.get())
        ).afterCreated((e, utils) -> {
            utils.loot().addSelfDropLootTable(e);
            ZPUtility.sides().onlyClient(() -> {
                utils.blocks().setBlockItemModelExecutor(e, DefaultBlockModelExecutors.getDefaultLantern(), DefaultBlockItemModelExecutors.getDefaultItemAs2DTexture("item/blocks/lantern3"));
                utils.blocks().addBlockModelSimpleOneTexture(e, ZPDataGenHelper.DEFAULT_LANTERN, "lantern", ZPDataGenHelper.LANTERN_BLOCKS_DIRECTORY);
                utils.blocks().setBlockRenderType(e, ZPDataGenHelper.CUTOUT_RENDER_TYPE);
            });
        }).end();

        ZPLanternBlocks.lantern4 = regSupplier.register("lantern4", () -> new ZPFadingLanternBlock(BlockBehaviour.Properties.of().lightLevel((p_50755_) -> 5).mapColor(MapColor.METAL).forceSolidOn().requiresCorrectToolForDrops().strength(3.5F).sound(SoundType.LANTERN), () -> ZPLanternBlocks.lantern5.get())
        ).afterCreated((e, utils) -> {
            utils.loot().addSelfDropLootTable(e);
            ZPUtility.sides().onlyClient(() -> {
                utils.blocks().setBlockItemModelExecutor(e, DefaultBlockModelExecutors.getDefaultLantern(), DefaultBlockItemModelExecutors.getDefaultItemAs2DTexture("item/blocks/lantern4"));
                utils.blocks().addBlockModelSimpleOneTexture(e, ZPDataGenHelper.DEFAULT_LANTERN, "lantern", ZPDataGenHelper.LANTERN_BLOCKS_DIRECTORY);
                utils.blocks().setBlockRenderType(e, ZPDataGenHelper.CUTOUT_RENDER_TYPE);
            });
        }).end();

        ZPLanternBlocks.lantern5 = regSupplier.register("lantern5", () -> new ZPFadingLanternBlock(BlockBehaviour.Properties.of().lightLevel((p_50755_) -> 0).mapColor(MapColor.METAL).forceSolidOn().requiresCorrectToolForDrops().strength(3.5F).sound(SoundType.LANTERN), null)
        ).afterCreated((e, utils) -> {
            utils.loot().addSelfDropLootTable(e);
            ZPUtility.sides().onlyClient(() -> {
                utils.blocks().setBlockItemModelExecutor(e, DefaultBlockModelExecutors.getDefaultLantern(), DefaultBlockItemModelExecutors.getDefaultItemAs2DTexture("item/blocks/lantern5"));
                utils.blocks().addBlockModelSimpleOneTexture(e, ZPDataGenHelper.DEFAULT_LANTERN, "lantern", ZPDataGenHelper.LANTERN_BLOCKS_DIRECTORY);
                utils.blocks().setBlockRenderType(e, ZPDataGenHelper.CUTOUT_RENDER_TYPE);
            });
        }).end();

        lanternBLocks.stopInstanceCollecting();
    }

    /*
       public static final Block TORCH = register("torch", new TorchBlock(BlockBehaviour.Properties.of().noCollission().instabreak().lightLevel((p_50755_) -> {
      return 14;
   }).sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY), ParticleTypes.FLAME));

   public static final Block WALL_TORCH = register("wall_torch", new WallTorchBlock(BlockBehaviour.Properties.of().noCollission().instabreak().lightLevel((p_50886_) -> {
      return 14;
   }).sound(SoundType.WOOD).dropsLike(TORCH).pushReaction(PushReaction.DESTROY), ParticleTypes.FLAME));

     */
}
