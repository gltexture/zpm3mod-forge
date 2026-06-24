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
import ru.gltexture.zpm3.engine.helpers.gen.data.MinecraftModelParentReference;
import ru.gltexture.zpm3.engine.helpers.gen.data.ZPGenTextureData;
import ru.gltexture.zpm3.engine.helpers.gen.providers.ZPBlockModelProvider;
import ru.gltexture.zpm3.engine.service.ZPPath;

import java.util.Objects;
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

    public static @NotNull ZPBlockModelProvider.BlockModelExecutor.EBlock<SnowLayerBlock> getDefaultLayerBlock() {
        return (blockStateProvider, block, renderType, name, textureData) -> {
            MinecraftModelParentReference parentRef = Objects.requireNonNull(textureData.getVanillaModelReference());
            String texture = Objects.requireNonNull(textureData.getTextureByKey(ZPGenTextureData.ALL_KEY))
                    .get()
                    .getFullPath();
            VariantBlockStateBuilder builder = blockStateProvider.getVariantBuilder(block);
            for (int layers = 1; layers <= 7; layers++) {
                int height = layers * 2;
                String modelName = name + "_height" + height;
                blockStateProvider.models()
                        .withExistingParent(modelName, parentRef.mainBlockReference() + height)
                        .texture("texture", ZPDataGenHelper.locate(blockStateProvider, texture))
                        .texture("particle", ZPDataGenHelper.locate(blockStateProvider, texture))
                        .renderType(renderType);
                builder.partialState()
                        .with(SnowLayerBlock.LAYERS, layers)
                        .addModels(new ConfiguredModel(blockStateProvider.models().getExistingFile(blockStateProvider.modLoc("block/" + modelName))));
            }
            BlockModelBuilder fullBlock = blockStateProvider.models().cubeAll(name + "_full", ZPDataGenHelper.locate(blockStateProvider, texture)).renderType(renderType);
            builder.partialState().with(SnowLayerBlock.LAYERS, 8).addModels(new ConfiguredModel(fullBlock));
        };
    }

    @BlockModelRequiresVanillaRef
    public static @NotNull ZPBlockModelProvider.BlockModelExecutor.EBlock<CampfireBlock> getDefaultCampfire() {
        return (blockStateProvider, block, renderType, name, textureData) -> {
            MinecraftModelParentReference parentRef = Objects.requireNonNull(textureData.getVanillaModelReference());
            String fireTexture = Objects.requireNonNull(textureData.getTextureByKey("fire")).get().getFullPath();
            String litLogTexture = Objects.requireNonNull(textureData.getTextureByKey("lit_log")).get().getFullPath();
            String logTexture = Objects.requireNonNull(textureData.getTextureByKey("log")).get().getFullPath();
            String offParent = Objects.requireNonNull(parentRef.subReferences()).get("off");
            if (offParent == null) {
                throw new ZPRuntimeException("Campfire model requires sub-reference 'off'");
            }
            final BlockModelBuilder litModel = blockStateProvider.models()
                    .withExistingParent(name, parentRef.mainBlockReference())
                    .texture("fire", ZPDataGenHelper.locate(blockStateProvider, fireTexture))
                    .texture("lit_log", ZPDataGenHelper.locate(blockStateProvider, litLogTexture))
                    .texture("log", ZPDataGenHelper.locate(blockStateProvider, logTexture))
                    .renderType(renderType);
            final BlockModelBuilder offModel = blockStateProvider.models()
                    .withExistingParent(name + "_off", offParent)
                    .texture("log", ZPDataGenHelper.locate(blockStateProvider, logTexture))
                    .renderType(renderType);
            VariantBlockStateBuilder builder = blockStateProvider.getVariantBuilder(block);
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                int yRot = switch (direction) {
                    case WEST -> 90;
                    case NORTH -> 180;
                    case EAST -> 270;
                    default -> 0;
                };
                builder.partialState().with(CampfireBlock.FACING, direction).with(CampfireBlock.LIT, true).addModels(new ConfiguredModel(litModel, 0, yRot, false));
                builder.partialState().with(CampfireBlock.FACING, direction).with(CampfireBlock.LIT, false).addModels(new ConfiguredModel(offModel, 0, yRot, false));
            }
        };
    }

    @BlockModelRequiresVanillaRef
    public static @NotNull ZPBlockModelProvider.BlockModelExecutor.EBlock<LanternBlock> getDefaultLantern() {
        return (blockStateProvider, block, renderType, name, textureData) -> {
            MinecraftModelParentReference parentRef = Objects.requireNonNull(textureData.getVanillaModelReference());
            String texture = Objects.requireNonNull(textureData.getTextureByKey("lantern")).get().getFullPath();
            String hangingParent = Objects.requireNonNull(parentRef.subReferences()).get("hanging");
            if (hangingParent == null) {
                throw new ZPRuntimeException("Lantern model requires sub-reference 'hanging'");
            }
            BlockModelBuilder standingModel = blockStateProvider.models().withExistingParent(name, parentRef.mainBlockReference()).texture("lantern", ZPDataGenHelper.locate(blockStateProvider, texture)).renderType(renderType);
            BlockModelBuilder hangingModel = blockStateProvider.models().withExistingParent(name + "_hanging", hangingParent).texture("lantern", ZPDataGenHelper.locate(blockStateProvider, texture)).renderType(renderType);
            VariantBlockStateBuilder builder = blockStateProvider.getVariantBuilder(block);
            builder.partialState().with(LanternBlock.HANGING, false).addModels(new ConfiguredModel(standingModel));
            builder.partialState().with(LanternBlock.HANGING, true).addModels(new ConfiguredModel(hangingModel));
        };
    }

    public static @NotNull ZPBlockModelProvider.BlockModelExecutor.EBlock<? extends Block> getDefaultTorch() {
        return (blockStateProvider, block, renderType, name, textureData) -> {
            String texture = Objects.requireNonNull(textureData.getTextureByKey("torch")).get().getFullPath();
            BlockModelBuilder standing = blockStateProvider.models().torch(name, ZPDataGenHelper.locate(blockStateProvider, texture)).renderType(renderType);
            VariantBlockStateBuilder builder = blockStateProvider.getVariantBuilder(block);
            builder.partialState().addModels(new ConfiguredModel(standing));
        };
    }

    public static @NotNull ZPBlockModelProvider.BlockModelExecutor.EBlock<? extends Block> getDefaultWallTorch() {
        return (blockStateProvider, block, renderType, name, textureData) -> {
            String texture = Objects.requireNonNull(textureData.getTextureByKey("torch")).get().getFullPath();
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
            BlockModelBuilder modelBuilder = blockStateProvider.models().withExistingParent(name, textureData.getVanillaModelReference().mainBlockReference());

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
            final String vanillaModel = textureData.getVanillaModelReference().mainBlockReference();
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
                String all = Objects.requireNonNull(textureData.getTextureByKey(ZPGenTextureData.ALL_KEY)).get().getFullPath();
                side = top = bottom = all;
            } else {
                side = Objects.requireNonNull(textureData.getTextureByKey(ZPGenTextureData.SIDE_KEY)).get().getFullPath();
                top = Objects.requireNonNull(textureData.getTextureByKey(ZPGenTextureData.TOP_KEY)).get().getFullPath();
                bottom = Objects.requireNonNull(textureData.getTextureByKey(ZPGenTextureData.BOTTOM_KEY)).get().getFullPath();
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
                String all = Objects.requireNonNull(textureData.getTextureByKey(ZPGenTextureData.ALL_KEY)).get().getFullPath();
                side = top = bottom = all;
            } else {
                side = Objects.requireNonNull(textureData.getTextureByKey(ZPGenTextureData.SIDE_KEY)).get().getFullPath();
                top = Objects.requireNonNull(textureData.getTextureByKey(ZPGenTextureData.TOP_KEY)).get().getFullPath();
                bottom = Objects.requireNonNull(textureData.getTextureByKey(ZPGenTextureData.BOTTOM_KEY)).get().getFullPath();
            }

            final ModelFile slabBottom = blockStateProvider.models().slab(name, ZPDataGenHelper.locate(blockStateProvider, side), ZPDataGenHelper.locate(blockStateProvider, bottom), ZPDataGenHelper.locate(blockStateProvider, top)).renderType(renderType);
            final ModelFile slabDouble = blockStateProvider.models().cubeBottomTop(name + "_double", ZPDataGenHelper.locate(blockStateProvider, side), ZPDataGenHelper.locate(blockStateProvider, bottom), ZPDataGenHelper.locate(blockStateProvider, top)).renderType(renderType);
            final ModelFile slabTop = blockStateProvider.models().slabTop(name + "_top", ZPDataGenHelper.locate(blockStateProvider, side), ZPDataGenHelper.locate(blockStateProvider, bottom), ZPDataGenHelper.locate(blockStateProvider, top)).renderType(renderType);

            blockStateProvider.slabBlock(block, slabBottom, slabTop, slabDouble);
        };
    }
}
