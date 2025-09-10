package ru.gltexture.zpm3.assets.common.init;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.init.helper.ZPRegTorchBlocks;
import ru.gltexture.zpm3.assets.common.instances.blocks.torch.ZPFadingBlock;
import ru.gltexture.zpm3.assets.common.instances.blocks.torch.ZPFadingBlockWall;
import ru.gltexture.zpm3.engine.core.ZPRegistryConveyor;
import ru.gltexture.zpm3.engine.instances.blocks.*;
import ru.gltexture.zpm3.engine.registry.ZPRegistry;
import ru.gltexture.zpm3.engine.registry.collection.IZPCollectRegistryObjects;

public class ZPTorchBlocks extends ZPRegistry<Block> implements IZPCollectRegistryObjects {
    public static RegistryObject<ZPTorchBlock> wall_lamp;
    public static RegistryObject<ZPWallTorchBlock> wall_lamp_wall;

    public static RegistryObject<ZPTorchBlock> wall_lamp_off;
    public static RegistryObject<ZPWallTorchBlock> wall_lamp_off_wall;

    public static RegistryObject<ZPFadingBlock> torch2;
    public static RegistryObject<ZPFadingBlockWall> torch2_wall;

    public static RegistryObject<ZPFadingBlock> torch3;
    public static RegistryObject<ZPFadingBlockWall> torch3_wall;

    public static RegistryObject<ZPFadingBlock> torch4;
    public static RegistryObject<ZPFadingBlockWall> torch4_wall;

    public static RegistryObject<ZPFadingBlock> torch5;
    public static RegistryObject<ZPFadingBlockWall> torch5_wall;

    public ZPTorchBlocks() {
        super(ZPRegistryConveyor.Target.BLOCK);
    }

    @Override
    protected void runRegister(@NotNull ZPRegSupplier<Block> regSupplier) {
        ZPRegTorchBlocks.init(this, regSupplier);
    }

    @Override
    public void preProcessing() {
        super.preProcessing();
    }

    @Override
    public void postProcessing() {
        super.postProcessing();
    }

    @Override
    protected void postRegister(String name, RegistryObject<Block> object) {
    }

    @Override
    public int priority() {
        return 1;
    }

    @Override
    public @NotNull String getID() {
        return this.getClass().getSimpleName();
    }
}