package ru.gltexture.zpm3.engine.helpers.gen.block_exec;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.exceptions.ZPRuntimeException;
import ru.gltexture.zpm3.engine.helpers.gen.ZPDataGenHelper;
import ru.gltexture.zpm3.engine.helpers.gen.data.ZPGenTextureData;
import ru.gltexture.zpm3.engine.helpers.gen.providers.ZPBlockModelProvider;
import ru.gltexture.zpm3.engine.service.ZPPath;

import java.util.function.Supplier;

public abstract class DefaultBlockModelExecutors {
    public static final @NotNull ZPBlockModelProvider.BlockModelExecutor DEFAULT_PILLAR_BLOCK_EXEC_PAIR = () -> new ZPBlockModelProvider.BlockModelExecutor.Pair(getDefaultPillarCube(), DefaultBlockItemModelExecutors.getDefaultItemAsBlock());
    public static final @NotNull ZPBlockModelProvider.BlockModelExecutor DEFAULT_BLOCK_EXEC_PAIR = () -> new ZPBlockModelProvider.BlockModelExecutor.Pair(getDefaultCube(), DefaultBlockItemModelExecutors.getDefaultItemAsBlock());
    public static final @NotNull ZPBlockModelProvider.BlockModelExecutor SLAB_BLOCK_EXEC_PAIR = () -> new ZPBlockModelProvider.BlockModelExecutor.Pair(getSlab(), DefaultBlockItemModelExecutors.getDefaultItemAsBlock());
    public static final @NotNull ZPBlockModelProvider.BlockModelExecutor STAIR_BLOCK_EXEC_PAIR = () -> new ZPBlockModelProvider.BlockModelExecutor.Pair(getStairs(), DefaultBlockItemModelExecutors.getDefaultItemAsBlock());

    public static @NotNull ZPBlockModelProvider.BlockModelExecutor.EBlock<? extends Block> getDefaultPillarCube() {
        return (blockStateProvider, block, renderType, name, textureData) -> {
            if (textureData.getVanillaModelReference() == null) {
                throw new ZPRuntimeException("Block's default cube model should be extended by vanilla model");
            }
            BlockModelBuilder modelBuilder = blockStateProvider.models().withExistingParent(name, textureData.getVanillaModelReference().reference());

            Supplier<ZPPath> sideTexture = textureData.getTextures().get("side");
            Supplier<ZPPath> endTexture = textureData.getTextures().get("end");

            if (sideTexture == null) {
                throw new ZPRuntimeException("'side' texture is required for pillar block model: " + name);
            }

            if (endTexture == null) {
                throw new ZPRuntimeException("'end' texture is required for pillar block model: " + name);
            }

            modelBuilder.texture("side", ZPDataGenHelper.locate(blockStateProvider, sideTexture.get().getFullPath()));
            modelBuilder.texture("end", ZPDataGenHelper.locate(blockStateProvider, endTexture.get().getFullPath()));
            modelBuilder.renderType(renderType);

            blockStateProvider.getVariantBuilder(block).partialState()
                    .with(RotatedPillarBlock.AXIS, Direction.Axis.Y).modelForState().modelFile(modelBuilder).addModel().partialState()
                    .with(RotatedPillarBlock.AXIS, Direction.Axis.X).modelForState().modelFile(modelBuilder).rotationX(90).rotationY(90).addModel().partialState()
                    .with(RotatedPillarBlock.AXIS, Direction.Axis.Z).modelForState().modelFile(modelBuilder).rotationX(90).addModel();
        };
    }

    public static @NotNull ZPBlockModelProvider.BlockModelExecutor.EBlock<? extends Block> getDefaultCube() {
        return (blockStateProvider, block, renderType, name, textureData) -> {
            if (textureData.getVanillaModelReference() == null) {
                throw new ZPRuntimeException("Block's default cube model should be extended by vanilla model");
            }
            final String vanillaModel = textureData.getVanillaModelReference().reference();
            BlockModelBuilder modelBuilder = blockStateProvider.models().withExistingParent(name, "minecraft:" + vanillaModel);

            textureData.getTextures().forEach((key, path) -> {
                if (key != null) {
                    modelBuilder.texture(key, ZPDataGenHelper.locate(blockStateProvider, path.get().getFullPath()));
                }
            });
            modelBuilder.renderType(renderType);

            blockStateProvider.simpleBlock(block, modelBuilder);
        };
    }

    public static @NotNull ZPBlockModelProvider.BlockModelExecutor.EBlock<StairBlock> getStairs() {
        return (blockStateProvider, block, renderType, name, textureData) -> {
            final String side;
            final String top;
            final String bottom;

            if (textureData.isHomogenousTextured()) {
                String all = textureData.getTextureByKey(ZPGenTextureData.ALL_KEY).get().getFullPath();
                side = top = bottom = all;
            } else {
                side = textureData.getTextureByKey(ZPGenTextureData.SIDE_KEY).get().getFullPath();
                top = textureData.getTextureByKey(ZPGenTextureData.TOP_KEY).get().getFullPath();
                bottom = textureData.getTextureByKey(ZPGenTextureData.BOTTOM_KEY).get().getFullPath();
            }

            final ModelFile stair = blockStateProvider.models().stairs(name, ZPDataGenHelper.locate(blockStateProvider, side), ZPDataGenHelper.locate(blockStateProvider, bottom), ZPDataGenHelper.locate(blockStateProvider, top)).renderType(renderType);
            final ModelFile stairInner = blockStateProvider.models().stairsInner(name + "_inner", ZPDataGenHelper.locate(blockStateProvider, side), ZPDataGenHelper.locate(blockStateProvider, bottom), ZPDataGenHelper.locate(blockStateProvider, top)).renderType(renderType);
            final ModelFile stairOuter = blockStateProvider.models().stairsOuter(name + "_outer", ZPDataGenHelper.locate(blockStateProvider, side), ZPDataGenHelper.locate(blockStateProvider, bottom), ZPDataGenHelper.locate(blockStateProvider, top)).renderType(renderType);

            blockStateProvider.stairsBlock(block, stairInner, stair, stairOuter);
        };
    }

    public static @NotNull ZPBlockModelProvider.BlockModelExecutor.EBlock<SlabBlock> getSlab() {
        return (blockStateProvider, block, renderType, name, textureData) -> {
            final String side;
            final String top;
            final String bottom;

            if (textureData.isHomogenousTextured()) {
                String all = textureData.getTextureByKey(ZPGenTextureData.ALL_KEY).get().getFullPath();
                side = top = bottom = all;
            } else {
                side = textureData.getTextureByKey(ZPGenTextureData.SIDE_KEY).get().getFullPath();
                top = textureData.getTextureByKey(ZPGenTextureData.TOP_KEY).get().getFullPath();
                bottom = textureData.getTextureByKey(ZPGenTextureData.BOTTOM_KEY).get().getFullPath();
            }

            final ModelFile slabBottom = blockStateProvider.models().slab(name, ZPDataGenHelper.locate(blockStateProvider, side), ZPDataGenHelper.locate(blockStateProvider, bottom), ZPDataGenHelper.locate(blockStateProvider, top)).renderType(renderType);
            final ModelFile slabDouble = blockStateProvider.models().cubeBottomTop(name + "_double", ZPDataGenHelper.locate(blockStateProvider, side), ZPDataGenHelper.locate(blockStateProvider, bottom), ZPDataGenHelper.locate(blockStateProvider, top)).renderType(renderType);
            final ModelFile slabTop = blockStateProvider.models().slabTop(name + "_top", ZPDataGenHelper.locate(blockStateProvider, side), ZPDataGenHelper.locate(blockStateProvider, bottom), ZPDataGenHelper.locate(blockStateProvider, top)).renderType(renderType);

            blockStateProvider.slabBlock(block, slabBottom, slabTop, slabDouble);
        };
    }
}
