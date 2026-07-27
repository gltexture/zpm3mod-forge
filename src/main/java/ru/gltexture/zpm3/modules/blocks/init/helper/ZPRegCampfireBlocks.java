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
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.helpers.gen.ZPDataGenHelper;
import ru.gltexture.zpm3.engine.helpers.gen.block_exec.DefaultBlockItemModelExecutors;
import ru.gltexture.zpm3.engine.helpers.gen.block_exec.DefaultBlockModelExecutors;
import ru.gltexture.zpm3.engine.registry.ZPRegistry;
import ru.gltexture.zpm3.engine.service.Pair;
import ru.gltexture.zpm3.engine.service.ZPPath;
import ru.gltexture.zpm3.engine.service.ZPUtility;
import ru.gltexture.zpm3.modules.blocks.init.ZPCampfireBlocks;
import ru.gltexture.zpm3.modules.blocks.instances.blocks.campfire.ZPCampfireBlock;

public abstract class ZPRegCampfireBlocks {
    public static void init(ZPCampfireBlocks campfireBlocks, @NotNull ZPRegistry.ZPRegSupplier<Block> regSupplier) {
        campfireBlocks.initInstanceCollecting("campfires");
        
        ZPCampfireBlocks.campfire2 = regSupplier.register("campfire2", () -> new ZPCampfireBlock(
                true,
                1,
                BlockBehaviour.Properties.of().mapColor(MapColor.PODZOL)
                        .instrument(NoteBlockInstrument.BASS)
                        .strength(2.0F)
                        .sound(SoundType.WOOD)
                        .lightLevel((w) -> 10)
                        .noOcclusion()
                        .ignitedByLava())
        ).afterCreated((e, utils) -> {
            utils.loot().addSelfDropLootTable(e);
            ZPUtility.sides().onlyClient(() -> {
                utils.blocks().setBlockItemModelExecutor(e, DefaultBlockModelExecutors.getDefaultCampfire(), DefaultBlockItemModelExecutors.getDefaultItemAs2DTexture("item/blocks/campfire"));
                utils.blocks().addBlockModelKey_ValueArray(e, ZPDataGenHelper.DEFAULT_CAMPFIRE,
                        Pair.of("fire", () -> new ZPPath(ZPDataGenHelper.CAMPFIRE_BLOCKS_DIRECTORY, "campfire_fire2")),
                        Pair.of("lit_log", () -> new ZPPath(ZPDataGenHelper.CAMPFIRE_BLOCKS_DIRECTORY, "campfire_log_lit2")),
                        Pair.of("log", () -> new ZPPath(ZPDataGenHelper.CAMPFIRE_BLOCKS_DIRECTORY, "campfire_log2"))
                );
                   utils.blocks().setBlockRenderType(e, ZPDataGenHelper.CUTOUT_RENDER_TYPE);
            });
        }).end();

        campfireBlocks.stopInstanceCollecting();
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
