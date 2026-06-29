package ru.gltexture.zpm3.modules.blocks.init;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZPRegistryConveyor;
import ru.gltexture.zpm3.engine.registry.ZPRegistry;
import ru.gltexture.zpm3.engine.registry.collection.IZPCollectRegistryObjects;
import ru.gltexture.zpm3.modules.blocks.init.helper.ZPRegCampfireBlocks;
import ru.gltexture.zpm3.modules.blocks.instances.blocks.campfire.ZPCampfireBlock;

public class ZPCampfireBlocks extends ZPRegistry<Block> implements IZPCollectRegistryObjects {
    public static RegistryObject<ZPCampfireBlock> campfire2;


    public ZPCampfireBlocks() {
        super(ZPRegistryConveyor.Target.BLOCK);
    }

    @Override
    protected void runRegister(@NotNull ZPRegSupplier<Block> regSupplier) {
        ZPRegCampfireBlocks.init(this, regSupplier);
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