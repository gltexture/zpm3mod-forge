package ru.gltexture.zpm3.assets.common.init;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZPRegistryConveyor;
import ru.gltexture.zpm3.engine.helpers.ZPDefaultModelsHelper;
import ru.gltexture.zpm3.engine.helpers.ZPItemTabAddHelper;
import ru.gltexture.zpm3.engine.registry.base.ZPRegistry;

public class ZPBlocks extends ZPRegistry<Block> {
    public static RegistryObject<Block> block_lamp;

    public ZPBlocks() {
        super(ForgeRegistries.BLOCKS, ZPRegistryConveyor.Target.BLOCK);
    }

    @Override
    protected void runRegister(@NotNull ZPRegSupplier<Block> regSupplier) {
        ZPBlocks.block_lamp = regSupplier.register("block_lamp", () -> new Block(BlockBehaviour.Properties.of().strength(0.5f).sound(SoundType.GLASS).lightLevel((e) -> 15))).registryObject();
    }

    @Override
    protected void postRegister(String name, RegistryObject<Block> object) {
        ZPDefaultModelsHelper.addNewBlockWithDefaultModel(object, ZPDefaultModelsHelper.DEFAULT_BLOCK_CUBE);
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