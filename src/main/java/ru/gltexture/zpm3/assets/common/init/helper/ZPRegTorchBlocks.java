package ru.gltexture.zpm3.assets.common.init.helper;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.api.distmarker.Dist;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.init.ZPTorchBlocks;
import ru.gltexture.zpm3.assets.common.instances.blocks.torch.ZPFadingTorchBlock;
import ru.gltexture.zpm3.assets.common.instances.blocks.torch.ZPFadingTorchBlockWall;
import ru.gltexture.zpm3.engine.helpers.gen.ZPDataGenHelper;
import ru.gltexture.zpm3.engine.helpers.gen.block_exec.DefaultBlockItemModelExecutors;
import ru.gltexture.zpm3.engine.helpers.gen.block_exec.DefaultBlockModelExecutors;
import ru.gltexture.zpm3.engine.instances.blocks.*;
import ru.gltexture.zpm3.engine.registry.ZPRegistry;

public abstract class ZPRegTorchBlocks {
    public static void init(ZPTorchBlocks torchBlocks, @NotNull ZPRegistry.ZPRegSupplier<Block> regSupplier) {
        torchBlocks.pushInstanceCollecting("torches");
        
        ZPTorchBlocks.torch2 = regSupplier.register("torch2", () -> new ZPFadingTorchBlock(BlockBehaviour.Properties.of().noCollission().instabreak().lightLevel((p_50755_) -> 12).sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY), ParticleTypes.FLAME, 1.0f, () -> ZPTorchBlocks.torch3.get())
        ).afterObjectCreated(Dist.DEDICATED_SERVER, (e, utils) -> {
            utils.loot().addSelfDropLootTable(e);
        }).afterObjectCreated(Dist.CLIENT, (e, utils) -> {
            utils.blocks().setBlockModelExecutor(e, DefaultBlockModelExecutors.getDefaultTorch());
            utils.blocks().setBlockItemModelExecutor(e, DefaultBlockItemModelExecutors.getDefaultItemAsItem());
            utils.blocks().addBlockModelSimpleOneTexture(e, ZPDataGenHelper.DEFAULT_TORCH, "torch", ZPDataGenHelper.TORCH_BLOCKS_DIRECTORY);
            utils.blocks().setBlockRenderType(e, ZPDataGenHelper.CUTOUT_RENDER_TYPE);
        }).end();

        ZPTorchBlocks.torch2_wall = regSupplier.register("torch2_wall", () -> new ZPFadingTorchBlockWall(BlockBehaviour.Properties.of().noCollission().instabreak().lightLevel((p_50755_) -> 12).sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY), ParticleTypes.FLAME, 1.0f, () -> ZPTorchBlocks.torch3_wall.get())
        ).afterObjectCreated(Dist.DEDICATED_SERVER, (e, utils) -> {
            utils.loot().addSelfDropLootTable(e);
        }).afterObjectCreated(Dist.CLIENT, (e, utils) -> {
            utils.blocks().setBlockModelExecutor(e, DefaultBlockModelExecutors.getDefaultWallTorch());
            utils.blocks().addBlockModelWithCopiedTexture(e, ZPDataGenHelper.DEFAULT_TORCH_WALL, ZPTorchBlocks.torch2);
            utils.blocks().setBlockRenderType(e, ZPDataGenHelper.CUTOUT_RENDER_TYPE);
        }).end();

        ZPTorchBlocks.torch3 = regSupplier.register("torch3", () -> new ZPFadingTorchBlock(BlockBehaviour.Properties.of().noCollission().instabreak().lightLevel((p_50755_) -> 9).sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY), ParticleTypes.FLAME, 0.45f, () -> ZPTorchBlocks.torch4.get())
        ).afterObjectCreated(Dist.DEDICATED_SERVER, (e, utils) -> {
            utils.loot().addSelfDropLootTable(e);
        }).afterObjectCreated(Dist.CLIENT, (e, utils) -> {
            utils.blocks().setBlockModelExecutor(e, DefaultBlockModelExecutors.getDefaultTorch());
            utils.blocks().setBlockItemModelExecutor(e, DefaultBlockItemModelExecutors.getDefaultItemAsItem());
            utils.blocks().addBlockModelSimpleOneTexture(e, ZPDataGenHelper.DEFAULT_TORCH, "torch", ZPDataGenHelper.TORCH_BLOCKS_DIRECTORY);
            utils.blocks().setBlockRenderType(e, ZPDataGenHelper.CUTOUT_RENDER_TYPE);
        }).end();

        ZPTorchBlocks.torch3_wall = regSupplier.register("torch3_wall", () -> new ZPFadingTorchBlockWall(BlockBehaviour.Properties.of().noCollission().instabreak().lightLevel((p_50755_) -> 9).sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY), ParticleTypes.FLAME, 0.45f, () -> ZPTorchBlocks.torch4_wall.get())
        ).afterObjectCreated(Dist.DEDICATED_SERVER, (e, utils) -> {
            utils.loot().addSelfDropLootTable(e);
        }).afterObjectCreated(Dist.CLIENT, (e, utils) -> {
            utils.blocks().setBlockModelExecutor(e, DefaultBlockModelExecutors.getDefaultWallTorch());
            utils.blocks().addBlockModelWithCopiedTexture(e, ZPDataGenHelper.DEFAULT_TORCH_WALL, ZPTorchBlocks.torch3);
            utils.blocks().setBlockRenderType(e, ZPDataGenHelper.CUTOUT_RENDER_TYPE);
        }).end();

        ZPTorchBlocks.torch4 = regSupplier.register("torch4", () -> new ZPFadingTorchBlock(BlockBehaviour.Properties.of().noCollission().instabreak().lightLevel((p_50755_) -> 6).sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY), ParticleTypes.FLAME, 0.15f, () -> ZPTorchBlocks.torch5.get())
        ).afterObjectCreated(Dist.DEDICATED_SERVER, (e, utils) -> {
        }).afterObjectCreated(Dist.CLIENT, (e, utils) -> {
            utils.blocks().setBlockModelExecutor(e, DefaultBlockModelExecutors.getDefaultTorch());
            utils.blocks().setBlockItemModelExecutor(e, DefaultBlockItemModelExecutors.getDefaultItemAsItem());
            utils.blocks().addBlockModelSimpleOneTexture(e, ZPDataGenHelper.DEFAULT_TORCH, "torch", ZPDataGenHelper.TORCH_BLOCKS_DIRECTORY);
            utils.blocks().setBlockRenderType(e, ZPDataGenHelper.CUTOUT_RENDER_TYPE);
        }).end();

        ZPTorchBlocks.torch4_wall = regSupplier.register("torch4_wall", () -> new ZPFadingTorchBlockWall(BlockBehaviour.Properties.of().noCollission().instabreak().lightLevel((p_50755_) -> 6).sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY), ParticleTypes.FLAME, 0.15f, () -> ZPTorchBlocks.torch5_wall.get())
        ).afterObjectCreated(Dist.DEDICATED_SERVER, (e, utils) -> {
        }).afterObjectCreated(Dist.CLIENT, (e, utils) -> {
            utils.blocks().setBlockModelExecutor(e, DefaultBlockModelExecutors.getDefaultWallTorch());
            utils.blocks().addBlockModelWithCopiedTexture(e, ZPDataGenHelper.DEFAULT_TORCH_WALL, ZPTorchBlocks.torch4);
            utils.blocks().setBlockRenderType(e, ZPDataGenHelper.CUTOUT_RENDER_TYPE);
        }).end();

        ZPTorchBlocks.torch5 = regSupplier.register("torch5", () -> new ZPFadingTorchBlock(BlockBehaviour.Properties.of().noCollission().instabreak().lightLevel((p_50755_) -> 2).sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY), null, 0.0f, null)
        ).afterObjectCreated(Dist.DEDICATED_SERVER, (e, utils) -> {
        }).afterObjectCreated(Dist.CLIENT, (e, utils) -> {
            utils.blocks().setBlockModelExecutor(e, DefaultBlockModelExecutors.getDefaultTorch());
            utils.blocks().setBlockItemModelExecutor(e, DefaultBlockItemModelExecutors.getDefaultItemAsItem());
            utils.blocks().addBlockModelSimpleOneTexture(e, ZPDataGenHelper.DEFAULT_TORCH, "torch", ZPDataGenHelper.TORCH_BLOCKS_DIRECTORY);
            utils.blocks().setBlockRenderType(e, ZPDataGenHelper.CUTOUT_RENDER_TYPE);
        }).end();

        ZPTorchBlocks.torch5_wall = regSupplier.register("torch5_wall", () -> new ZPFadingTorchBlockWall(BlockBehaviour.Properties.of().noCollission().instabreak().lightLevel((p_50755_) -> 2).sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY), null, 0.0f, null)
        ).afterObjectCreated(Dist.DEDICATED_SERVER, (e, utils) -> {
        }).afterObjectCreated(Dist.CLIENT, (e, utils) -> {
            utils.blocks().setBlockModelExecutor(e, DefaultBlockModelExecutors.getDefaultWallTorch());
            utils.blocks().addBlockModelWithCopiedTexture(e, ZPDataGenHelper.DEFAULT_TORCH_WALL, ZPTorchBlocks.torch5);
            utils.blocks().setBlockRenderType(e, ZPDataGenHelper.CUTOUT_RENDER_TYPE);
        }).end();

        torchBlocks.stopCollecting();

        ZPTorchBlocks.wall_lamp = regSupplier.register("wall_lamp", () -> new ZPTorchBlock(BlockBehaviour.Properties.of().noCollission().instabreak().lightLevel((p_50755_) -> 15).sound(SoundType.GLASS).pushReaction(PushReaction.DESTROY), null, 0.0f) //ParticleTypes.FLAME
        ).afterObjectCreated(Dist.DEDICATED_SERVER, (e, utils) -> {
        }).afterObjectCreated(Dist.CLIENT, (e, utils) -> {
            utils.blocks().setBlockModelExecutor(e, DefaultBlockModelExecutors.getDefaultTorch());
            utils.blocks().setBlockItemModelExecutor(e, DefaultBlockItemModelExecutors.getDefaultItemAsItem());
            utils.blocks().addBlockModelSimpleOneTexture(e, ZPDataGenHelper.DEFAULT_TORCH, "torch", ZPDataGenHelper.TORCH_BLOCKS_DIRECTORY);
            utils.blocks().setBlockRenderType(e, ZPDataGenHelper.CUTOUT_RENDER_TYPE);
        }).end();

        ZPTorchBlocks.wall_lamp_wall = regSupplier.register("wall_lamp_wall", () -> new ZPWallTorchBlock(BlockBehaviour.Properties.of().noCollission().instabreak().lightLevel((p_50755_) -> 15).sound(SoundType.GLASS).pushReaction(PushReaction.DESTROY), null, 0.0f) //ParticleTypes.FLAME
        ).afterObjectCreated(Dist.DEDICATED_SERVER, (e, utils) -> {
        }).afterObjectCreated(Dist.CLIENT, (e, utils) -> {
            utils.blocks().setBlockModelExecutor(e, DefaultBlockModelExecutors.getDefaultWallTorch());
            utils.blocks().addBlockModelWithCopiedTexture(e, ZPDataGenHelper.DEFAULT_TORCH_WALL, ZPTorchBlocks.wall_lamp);
            utils.blocks().setBlockRenderType(e, ZPDataGenHelper.CUTOUT_RENDER_TYPE);
        }).end();

        ZPTorchBlocks.wall_lamp_off = regSupplier.register("wall_lamp_off", () -> new ZPTorchBlock(BlockBehaviour.Properties.of().noCollission().instabreak().sound(SoundType.GLASS).pushReaction(PushReaction.DESTROY), null, 0.0f) //ParticleTypes.FLAME
        ).afterObjectCreated(Dist.DEDICATED_SERVER, (e, utils) -> {
        }).afterObjectCreated(Dist.CLIENT, (e, utils) -> {
            utils.blocks().setBlockModelExecutor(e, DefaultBlockModelExecutors.getDefaultTorch());
            utils.blocks().setBlockItemModelExecutor(e, DefaultBlockItemModelExecutors.getDefaultItemAsItem());
            utils.blocks().addBlockModelSimpleOneTexture(e, ZPDataGenHelper.DEFAULT_TORCH, "torch", ZPDataGenHelper.TORCH_BLOCKS_DIRECTORY);
            utils.blocks().setBlockRenderType(e, ZPDataGenHelper.CUTOUT_RENDER_TYPE);
        }).end();

        ZPTorchBlocks.wall_lamp_off_wall = regSupplier.register("wall_lamp_off_wall", () -> new ZPWallTorchBlock(BlockBehaviour.Properties.of().noCollission().instabreak().sound(SoundType.GLASS).pushReaction(PushReaction.DESTROY), null, 0.0f) //ParticleTypes.FLAME
        ).afterObjectCreated(Dist.DEDICATED_SERVER, (e, utils) -> {
        }).afterObjectCreated(Dist.CLIENT, (e, utils) -> {
            utils.blocks().setBlockModelExecutor(e, DefaultBlockModelExecutors.getDefaultWallTorch());
            utils.blocks().addBlockModelWithCopiedTexture(e, ZPDataGenHelper.DEFAULT_TORCH_WALL, ZPTorchBlocks.wall_lamp);
            utils.blocks().setBlockRenderType(e, ZPDataGenHelper.CUTOUT_RENDER_TYPE);
        }).end();
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
