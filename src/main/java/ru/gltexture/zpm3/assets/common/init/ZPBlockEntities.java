package ru.gltexture.zpm3.assets.common.init;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.instances.block_entities.ZPBarbaredWireBlockEntity;
import ru.gltexture.zpm3.assets.common.instances.block_entities.ZPFadingBlockEntity;
import ru.gltexture.zpm3.assets.loot_cases.registry.ZPLootTablesCollection;
import ru.gltexture.zpm3.engine.core.ZPRegistryConveyor;
import ru.gltexture.zpm3.engine.instances.blocks.ZPBlock;
import ru.gltexture.zpm3.engine.instances.blocks.ZPLiquidBlock;
import ru.gltexture.zpm3.engine.registry.ZPRegistry;
import ru.gltexture.zpm3.engine.registry.ZPRegistryCollections;
import ru.gltexture.zpm3.engine.service.Pair;

import java.util.Arrays;
import java.util.stream.Stream;

public class ZPBlockEntities extends ZPRegistry<BlockEntityType<?>> {
    public static RegistryObject<BlockEntityType<ZPFadingBlockEntity>> fading_block_entity;
    public static RegistryObject<BlockEntityType<ZPBarbaredWireBlockEntity>> barbared_wire_block_entity;

    public ZPBlockEntities() {
        super(ZPRegistryConveyor.Target.BLOCK_ENTITY_TYPE);
    }

    @Override
    @SuppressWarnings("all")
    protected void runRegister(@NotNull ZPRegSupplier<BlockEntityType<?>> regSupplier) {
        ZPBlockEntities.fading_block_entity = regSupplier.register("fading_torch_block_entity",
                () -> {
                    Block[] zpBlocks = ZPRegistryCollections.getCollectionById(Pair.of(ZPTorchBlocks.class, "torches"), Pair.of(ZPBlocks.class, "fadingLiquids"))
                            .stream()
                            .map(e -> (Block) e.get())
                            .toArray(Block[]::new);
                    Block[] allBlocks = Stream.concat(Arrays.stream(zpBlocks), Stream.of(Blocks.TORCH, Blocks.WALL_TORCH, Blocks.JACK_O_LANTERN, Blocks.LAVA)).toArray(Block[]::new);
                    return Builder.of(ZPFadingBlockEntity::new, allBlocks).build(null);
                }).end();

        ZPBlockEntities.barbared_wire_block_entity = regSupplier.register("barbared_wire_block_entity",
                () -> {
                    return Builder.of(ZPBarbaredWireBlockEntity::new, new Block[] {ZPBlocks.barbared_wire.get()}).build(null);
                }).end();
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