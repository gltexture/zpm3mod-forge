package ru.gltexture.zpm3.assets.common.init;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.instances.block_entities.ZPFadingBlockEntity;
import ru.gltexture.zpm3.engine.core.ZPRegistryConveyor;
import ru.gltexture.zpm3.engine.objects.blocks.ZPBlock;
import ru.gltexture.zpm3.engine.registry.ZPRegistry;
import ru.gltexture.zpm3.engine.registry.ZPRegistryCollections;

import java.util.Arrays;
import java.util.stream.Stream;

public class ZPBlockEntities extends ZPRegistry<BlockEntityType<?>> {
    public static RegistryObject<BlockEntityType<ZPFadingBlockEntity>> fading_block_entity;

    public ZPBlockEntities() {
        super(ForgeRegistries.BLOCK_ENTITY_TYPES, ZPRegistryConveyor.Target.BLOCK_ENTITY);
    }

    @Override
    @SuppressWarnings("DataFlowIssue")
    protected void runRegister(@NotNull ZPRegSupplier<BlockEntityType<?>> regSupplier) {
        ZPBlockEntities.fading_block_entity = regSupplier.register("fading_torch_block_entity",
                () -> {
                    ZPBlock[] zpBlocks = ZPRegistryCollections.getCollectionById(ZPTorchBlocks.class, "torches")
                            .stream()
                            .map(e -> (ZPBlock) e.get())
                            .toArray(ZPBlock[]::new);
                    Block[] allBlocks = Stream.concat(Arrays.stream(zpBlocks), Stream.of(Blocks.TORCH, Blocks.WALL_TORCH, Blocks.JACK_O_LANTERN)).toArray(Block[]::new);
                    return Builder.of(ZPFadingBlockEntity::new, allBlocks).build(null);
                }).registryObject();
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