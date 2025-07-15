package ru.gltexture.zpm3.engine.helpers.gen.block_exec;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.VariantBlockStateBuilder;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.exceptions.ZPRuntimeException;
import ru.gltexture.zpm3.engine.helpers.gen.ZPDataGenHelper;
import ru.gltexture.zpm3.engine.helpers.gen.data.ZPGenTextureData;
import ru.gltexture.zpm3.engine.helpers.gen.providers.ZPBlockModelProvider;
import ru.gltexture.zpm3.engine.service.ZPPath;

import java.util.function.Supplier;

public abstract class DefaultBlockModelExecutors {
    @BlockModelRequiresVanillaRef
    public static final @NotNull ZPBlockModelProvider.BlockModelExecutor DEFAULT_PILLAR_BLOCK_EXEC_PAIR = () -> new ZPBlockModelProvider.BlockModelExecutor.Pair(DefaultBlockModelExecutors.getDefaultPillarCube(), DefaultBlockItemModelExecutors.getDefaultItemAsBlock());

    @BlockModelRequiresVanillaRef
    public static final @NotNull ZPBlockModelProvider.BlockModelExecutor DEFAULT_BLOCK_EXEC_PAIR = () -> new ZPBlockModelProvider.BlockModelExecutor.Pair(DefaultBlockModelExecutors.getDefault(), DefaultBlockItemModelExecutors.getDefaultItemAsBlock());

    @BlockModelRequiresVanillaRef
    public static final @NotNull ZPBlockModelProvider.BlockModelExecutor DEFAULT_FLAT_ITEM_BLOCK_EXEC_PAIR = () -> new ZPBlockModelProvider.BlockModelExecutor.Pair(DefaultBlockModelExecutors.getDefault(), DefaultBlockItemModelExecutors.getDefaultItemAsItem());

    public static final @NotNull ZPBlockModelProvider.BlockModelExecutor SLAB_BLOCK_EXEC_PAIR = () -> new ZPBlockModelProvider.BlockModelExecutor.Pair(DefaultBlockModelExecutors.getSlab(), DefaultBlockItemModelExecutors.getDefaultItemAsBlock());

    public static final @NotNull ZPBlockModelProvider.BlockModelExecutor STAIR_BLOCK_EXEC_PAIR = () -> new ZPBlockModelProvider.BlockModelExecutor.Pair(DefaultBlockModelExecutors.getStairs(), DefaultBlockItemModelExecutors.getDefaultItemAsBlock());
    public static final @NotNull ZPBlockModelProvider.BlockModelExecutor TORCH_BLOCK_EXEC_PAIR = () -> new ZPBlockModelProvider.BlockModelExecutor.Pair(DefaultBlockModelExecutors.getDefaultTorch(), DefaultBlockItemModelExecutors.getDefaultItemAsItem());
    public static final @NotNull ZPBlockModelProvider.BlockModelExecutor TORCH_WALL_BLOCK_EXEC_PAIR = () -> new ZPBlockModelProvider.BlockModelExecutor.Pair(DefaultBlockModelExecutors.getDefaultWallTorch(), DefaultBlockItemModelExecutors.getDefaultItemAsItem());

    public static @NotNull ZPBlockModelProvider.BlockModelExecutor.EBlock<? extends Block> getDefaultTorch() {
        return (blockStateProvider, block, renderType, name, textureData) -> {
            String texture = textureData.getTextureByKey("torch").get().getFullPath();
            BlockModelBuilder standing = blockStateProvider.models().torch(name, ZPDataGenHelper.locate(blockStateProvider, texture)).renderType(renderType);
            VariantBlockStateBuilder builder = blockStateProvider.getVariantBuilder(block);
            builder.partialState().addModels(new ConfiguredModel(standing));
        };
    }

    public static @NotNull ZPBlockModelProvider.BlockModelExecutor.EBlock<? extends Block> getDefaultWallTorch() {
        return (blockStateProvider, block, renderType, name, textureData) -> {
            String texture = textureData.getTextureByKey("torch").get().getFullPath();
            BlockModelBuilder wall = blockStateProvider.models().torchWall(name, ZPDataGenHelper.locate(blockStateProvider, texture)).renderType(renderType);

            VariantBlockStateBuilder builder = blockStateProvider.getVariantBuilder(block);
            builder.forAllStates(state -> {
                Direction facing = state.getValue(HorizontalDirectionalBlock.FACING);

                int rotationY = switch (facing) {
                    case NORTH -> 270;
                    case SOUTH -> 90;
                    case WEST -> 180;
                    default -> 0;
                };

                return ConfiguredModel.builder().modelFile(wall).rotationY(rotationY).build();
            });
        };
    }

    @BlockModelRequiresVanillaRef
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

    @BlockModelRequiresVanillaRef
    public static @NotNull ZPBlockModelProvider.BlockModelExecutor.EBlock<? extends Block> getDefault() {
        return (blockStateProvider, block, renderType, name, textureData) -> {
            if (textureData.getVanillaModelReference() == null) {
                throw new ZPRuntimeException("Block's default cube model should be extended by vanilla model: " + name);
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

            blockStateProvider.stairsBlock(block, stair, stairInner, stairOuter);
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
