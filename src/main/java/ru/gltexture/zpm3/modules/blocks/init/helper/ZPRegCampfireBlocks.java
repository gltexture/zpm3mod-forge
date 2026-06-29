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
                utils.blocks().setBlockItemModelExecutor(e, DefaultBlockModelExecutors.getDefaultCampfire(), DefaultBlockItemModelExecutors.getDefaultItemAs2DTexture("zpm3:item/blocks/campfire"));
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
