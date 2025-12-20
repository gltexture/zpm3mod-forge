package ru.gltexture.zpm3.assets.common.init;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.init.helper.ZPRegAdminBlocks;
import ru.gltexture.zpm3.assets.common.init.helper.ZPRegCommonBlocks;
import ru.gltexture.zpm3.assets.common.init.helper.ZPRegFluidBlocks;
import ru.gltexture.zpm3.assets.common.instances.blocks.ZPAntiZombie;
import ru.gltexture.zpm3.assets.common.instances.blocks.*;
import ru.gltexture.zpm3.engine.instances.blocks.*;
import ru.gltexture.zpm3.engine.core.ZPRegistryConveyor;
import ru.gltexture.zpm3.engine.registry.ZPRegistry;
import ru.gltexture.zpm3.engine.registry.collection.IZPCollectRegistryObjects;

public class ZPBlocks extends ZPRegistry<Block> implements IZPCollectRegistryObjects {
    public static RegistryObject<ZPAntiZombie> anti_zombie;

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

    public static RegistryObject<ZPAcidLiquidBlock> acid_block;
    public static RegistryObject<ZPToxicLiquidBlock> toxic_block;

    public ZPBlocks() {
        super(ZPRegistryConveyor.Target.BLOCK);
    }

    @Override
    protected void runRegister(@NotNull ZPRegSupplier<Block> regSupplier) {
        this.pushInstanceCollecting("blocks");
        ZPRegAdminBlocks.init(regSupplier);
        ZPRegCommonBlocks.init(regSupplier);
        this.stopCollecting();
        ZPRegFluidBlocks.init(regSupplier);
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