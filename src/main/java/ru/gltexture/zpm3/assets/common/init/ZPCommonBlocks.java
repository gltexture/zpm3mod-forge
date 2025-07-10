package ru.gltexture.zpm3.assets.common.init;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.init.helper.ZPRegCommonBlocks;
import ru.gltexture.zpm3.assets.common.init.helper.ZPRegMedicine;
import ru.gltexture.zpm3.assets.common.instances.blocks.ZPBarbaredWireBlock;
import ru.gltexture.zpm3.engine.objects.blocks.*;
import ru.gltexture.zpm3.assets.common.instances.blocks.ZPFallingBlock;
import ru.gltexture.zpm3.assets.common.instances.blocks.ZPUraniumBlock;
import ru.gltexture.zpm3.engine.helpers.gen.ZPDataGenHelper;
import ru.gltexture.zpm3.engine.helpers.gen.block_exec.DefaultBlockItemModelExecutors;
import ru.gltexture.zpm3.engine.helpers.gen.block_exec.DefaultBlockModelExecutors;
import ru.gltexture.zpm3.engine.helpers.gen.providers.ZPBlockModelProvider;
import ru.gltexture.zpm3.engine.core.ZPRegistryConveyor;
import ru.gltexture.zpm3.engine.registry.ZPRegistry;
import ru.gltexture.zpm3.engine.registry.collection.IZPCollectRegistryObjects;

public class ZPCommonBlocks extends ZPRegistry<Block> implements IZPCollectRegistryObjects {
    public static RegistryObject<ZPBlock> block_lamp;
    public static RegistryObject<ZPBlock> block_lamp_off;

    public static RegistryObject<ZPBlock> armor_green;
    public static RegistryObject<ZPBlock> armor_black;

    public static RegistryObject<ZPBlock> armored_glass;
    public static RegistryObject<ZPBlock> asphalt;
    public static RegistryObject<ZPSlabBlock> asphalt_slab;
    public static RegistryObject<ZPStairsBlock> asphalt_stairs;

    public static RegistryObject<ZPBlock> asphalt_marking;
    public static RegistryObject<ZPSlabBlock> asphalt_marking_slab;
    public static RegistryObject<ZPStairsBlock> asphalt_marking_stairs;

    public static RegistryObject<ZPFallingBlock> sandbag;
    public static RegistryObject<ZPBlock> scrap;
    public static RegistryObject<ZPUraniumBlock> uranium;
    public static RegistryObject<ZPBarbaredWireBlock> barbared_wire;

    public static RegistryObject<ZPPillarBlock> empty_bookshelf1;
    public static RegistryObject<ZPPillarBlock> empty_bookshelf2;
    public static RegistryObject<ZPPillarBlock> empty_bookshelf3;

    public static RegistryObject<ZPPillarBlock> concrete_fence;

    public ZPCommonBlocks() {
        super(ForgeRegistries.BLOCKS, ZPRegistryConveyor.Target.BLOCK);
    }

    @Override
    protected void runRegister(@NotNull ZPRegSupplier<Block> regSupplier) {
        ZPRegCommonBlocks.init(regSupplier);
    }

    @Override
    public void preProcessing() {
        super.preProcessing();
    }

    @Override
    protected void postRegister(String name, RegistryObject<Block> object) {
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