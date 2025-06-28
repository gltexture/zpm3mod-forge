package ru.gltexture.zpm3.engine.helpers.gen.providers;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.helpers.gen.ZPDataGenHelper;
import ru.gltexture.zpm3.engine.helpers.gen.block_exec.DefaultBlockModelExecutors;
import ru.gltexture.zpm3.engine.helpers.gen.data.ZPGenTextureData;
import ru.gltexture.zpm3.engine.objects.blocks.base.ZPSlabBlock;
import ru.gltexture.zpm3.engine.objects.blocks.base.ZPStairsBlock;

import java.util.Objects;
import java.util.*;
import java.util.function.Supplier;

public class ZPBlockModelProvider extends BlockStateProvider {
    private static final Map<RegistryObject<? extends Block>, Supplier<ZPGenTextureData>> blocksWithDefaultModel = new LinkedHashMap<>();
    private static final Map<Class<? extends Block>, BlockModelExecutor> classExecutors = new HashMap<>();
    private static final Map<RegistryObject<? extends Block>, BlockModelExecutor> blockExecutors = new HashMap<>();
    private static final Map<RegistryObject<? extends Block>, String> renderTypeMap = new HashMap<>();

    static {
        //DEFAULT
        ZPBlockModelProvider.classExecutors.put(null, DefaultBlockModelExecutors.DEFAULT_BLOCK_EXEC_PAIR);
        ZPBlockModelProvider.classExecutors.put(ZPSlabBlock.class, DefaultBlockModelExecutors.SLAB_BLOCK_EXEC_PAIR);
        ZPBlockModelProvider.classExecutors.put(ZPStairsBlock.class, DefaultBlockModelExecutors.STAIR_BLOCK_EXEC_PAIR);
    }

    public static <R extends Block> void addBlockModelExecutor(@NotNull Class<R> clazz, @NotNull ZPBlockModelProvider.BlockModelExecutor blockModelExecutor) {
        ZPBlockModelProvider.classExecutors.put(clazz, blockModelExecutor);
    }

    public static void addBlockModelExecutor(@NotNull RegistryObject<? extends Block> block, @NotNull ZPBlockModelProvider.BlockModelExecutor blockModelExecutor) {
        ZPBlockModelProvider.blockExecutors.put(block, blockModelExecutor);
    }

    public static void setBlockRenderType(@NotNull RegistryObject<? extends Block> block, @NotNull String renderType) {
        ZPBlockModelProvider.renderTypeMap.put(block, renderType);
    }

    public ZPBlockModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ZombiePlague3.MOD_ID, existingFileHelper);
    }

    public static void addNewObject(RegistryObject<? extends Block> block, Supplier<ZPGenTextureData> data) {
        ZPBlockModelProvider.blocksWithDefaultModel.put(block, data);
    }

    public static void clearMaps() {
        ZPBlockModelProvider.blocksWithDefaultModel.clear();
        ZPBlockModelProvider.classExecutors.clear();
    }

    @Override
    protected void registerStatesAndModels() {
        final Map<Block, BlockModelExecutor> modelExecutorMap = ZPBlockModelProvider.reconstructMap(ZPBlockModelProvider.blockExecutors);
        final Map<Block, String> renderTypesMap = ZPBlockModelProvider.reconstructMap(ZPBlockModelProvider.renderTypeMap);
        ZPBlockModelProvider.blockExecutors.clear();
        ZPBlockModelProvider.renderTypeMap.clear();

        ZPBlockModelProvider.blocksWithDefaultModel.forEach((blockRef, textureDataSupplier) -> {
            final Block block = blockRef.get();
            final String name = Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath();
            final ZPGenTextureData textureData = textureDataSupplier.get();
            @NotNull final String renderType = renderTypesMap.getOrDefault(block, ZPDataGenHelper.DEFAULT_RENDER_TYPE);

            BlockModelExecutor.Pair pair = modelExecutorMap.containsKey(block) ? modelExecutorMap.get(block).createPair() : null;
            if (pair == null) {
                for (Class<? extends Block> clazz : ZPBlockModelProvider.classExecutors.keySet()) {
                    if (clazz == null) {
                        continue;
                    }
                    if (clazz.isAssignableFrom(block.getClass())) {
                        pair = ZPBlockModelProvider.classExecutors.get(clazz).createPair();
                    }
                }
                if (pair == null) {
                    pair = ZPBlockModelProvider.classExecutors.get(null).createPair();
                }
            }

            pair.block().exec(this, block, renderType, name, textureData);
            pair.item().exec(this, block, name, textureData);
        });

        ZPBlockModelProvider.clearMaps();
    }

    private static <T> Map<Block, T> reconstructMap(Map<RegistryObject<? extends Block>, T> oldMap) {
        final Map<Block, T> reconstructMap = new HashMap<>();
        oldMap.forEach((k, v) -> reconstructMap.put(k.get(), v));
        return reconstructMap;
    }

    public static Supplier<ZPGenTextureData> getTextureData(@NotNull RegistryObject<? extends Block> registryObject) {
        return ZPBlockModelProvider.blocksWithDefaultModel.get(registryObject);
    }

    @FunctionalInterface
    public interface BlockModelExecutor  {
        Pair createPair();

        record Pair (@NotNull EBlock<? extends Block> block, @NotNull EItem<? extends Block> item) { }

        @FunctionalInterface
        interface EItem<R extends Block> {
            void exec(@NotNull BlockStateProvider blockStateProvider, @NotNull R block, @NotNull String name, @NotNull ZPGenTextureData textureData);

            @SuppressWarnings("all")
            default void exec(@NotNull BlockStateProvider blockStateProvider, @NotNull Object block, @NotNull String name, @NotNull ZPGenTextureData textureData) {
                this.exec(blockStateProvider, (R) block, name, textureData);
            }
        }

        @FunctionalInterface
        interface EBlock <R extends Block> {
            void exec(@NotNull BlockStateProvider blockStateProvider, R block, @NotNull String renderType, @NotNull String name, @NotNull ZPGenTextureData textureData);

            @SuppressWarnings("all")
            default void exec(@NotNull BlockStateProvider blockStateProvider, @NotNull Object block, @NotNull String renderType, @NotNull String name, @NotNull ZPGenTextureData textureData) {
                this.exec(blockStateProvider, (R) block, renderType, name, textureData);
            }
        }
    }
}