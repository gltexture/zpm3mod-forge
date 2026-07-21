package ru.gltexture.zpm3.modules.blocks.init;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.modules.blocks.init.helper.ZPRegAdminBlocks;
import ru.gltexture.zpm3.modules.blocks.init.helper.ZPRegColorBlocks;
import ru.gltexture.zpm3.modules.blocks.init.helper.ZPRegCommonBlocks;
import ru.gltexture.zpm3.modules.blocks.instances.blocks.*;
import ru.gltexture.zpm3.modules.fluids.init.helper.ZPRegFluidBlocks;
import ru.gltexture.zpm3.engine.instances.blocks.*;
import ru.gltexture.zpm3.engine.core.ZPRegistryConveyor;
import ru.gltexture.zpm3.engine.registry.ZPRegistry;
import ru.gltexture.zpm3.engine.registry.collection.IZPCollectRegistryObjects;

public class ZPBlocks extends ZPRegistry<Block> implements IZPCollectRegistryObjects {
    public static RegistryObject<ZPAntiZombie> anti_zombie;

    public static RegistryObject<ZPBlock> block_lamp;
    public static RegistryObject<ZPBlock> block_lamp_off;


    public static RegistryObject<ZPBlock> camo_forest;
    public static RegistryObject<ZPSlabBlock> camo_slab_forest;

    public static RegistryObject<ZPBlock> camo_snow;
    public static RegistryObject<ZPSlabBlock> camo_slab_snow;

    public static RegistryObject<ZPBlock> camo_sand;
    public static RegistryObject<ZPSlabBlock> camo_slab_sand;


    public static RegistryObject<ZPBlock> steel_black;
    public static RegistryObject<ZPBlock> steel_gray;
    public static RegistryObject<ZPBlock> steel_green;
    public static RegistryObject<ZPBlock> steel_hazard;
    public static RegistryObject<ZPBlock> steel_orange;
    public static RegistryObject<ZPBlock> steel_white;

    public static RegistryObject<ZPSlabBlock> steel_slab_black;
    public static RegistryObject<ZPSlabBlock> steel_slab_gray;
    public static RegistryObject<ZPSlabBlock> steel_slab_green;
    public static RegistryObject<ZPSlabBlock> steel_slab_hazard;
    public static RegistryObject<ZPSlabBlock> steel_slab_orange;
    public static RegistryObject<ZPSlabBlock> steel_slab_white;

    public static RegistryObject<ZPStairsBlock> steel_stairs_black;
    public static RegistryObject<ZPStairsBlock> steel_stairs_gray;
    public static RegistryObject<ZPStairsBlock> steel_stairs_green;
    public static RegistryObject<ZPStairsBlock> steel_stairs_hazard;
    public static RegistryObject<ZPStairsBlock> steel_stairs_orange;
    public static RegistryObject<ZPStairsBlock> steel_stairs_white;


    public static RegistryObject<ZPBlock> black_bricks;
    public static RegistryObject<ZPBlock> gray_bricks;
    public static RegistryObject<ZPBlock> green_bricks;
    public static RegistryObject<ZPBlock> ancient_bricks;

    public static RegistryObject<ZPSlabBlock> black_slab_bricks;
    public static RegistryObject<ZPSlabBlock> gray_slab_bricks;
    public static RegistryObject<ZPSlabBlock> green_slab_bricks;
    public static RegistryObject<ZPSlabBlock> ancient_slab_bricks;

    public static RegistryObject<ZPStairsBlock> black_stairs_bricks;
    public static RegistryObject<ZPStairsBlock> gray_stairs_bricks;
    public static RegistryObject<ZPStairsBlock> green_stairs_bricks;
    public static RegistryObject<ZPStairsBlock> ancient_stairs_bricks;

    public static RegistryObject<ZPBlock> lab_block;
    public static RegistryObject<ZPBlock> reactor_block;
    public static RegistryObject<ZPSlabBlock> lab_slab_block;
    public static RegistryObject<ZPSlabBlock> reactor_slab_block;
    public static RegistryObject<ZPStairsBlock> lab_stairs_block;
    public static RegistryObject<ZPStairsBlock> reactor_stairs_block;

    public static RegistryObject<ZPBlock> asphalt;
    public static RegistryObject<ZPSlabBlock> asphalt_slab;
    public static RegistryObject<ZPStairsBlock> asphalt_stairs;

    public static RegistryObject<ZPBlock> asphalt_marking;
    public static RegistryObject<ZPSlabBlock> asphalt_marking_slab;
    public static RegistryObject<ZPStairsBlock> asphalt_marking_stairs;


    public static RegistryObject<ZPIronBarsBlock> chain_link;

    public static RegistryObject<ZPBlock> armored_glass;

    public static RegistryObject<ZPFallingBlock> sandbag;
    public static RegistryObject<ZPBlock> scrap_block;
    public static RegistryObject<ZPSlabBlock> scrap_slab;
    public static RegistryObject<ZPStairsBlock> scrap_stairs;

    public static RegistryObject<ZPRustyTrapDoor> scrap_trapDoor;
    public static RegistryObject<ZPRustyDoor> scrap_door;

    public static RegistryObject<ZPUraniumBlock> uranium;
    public static RegistryObject<ZPBarbaredWireBlock> barbared_wire;


    public static RegistryObject<ZPBlock> empty_bookshelf1;
    public static RegistryObject<ZPBlock> empty_bookshelf2;
    public static RegistryObject<ZPBlock> empty_bookshelf3;

    public static RegistryObject<ZPPillarBlock> concrete_fence;

    public static RegistryObject<ZPAcidLiquidBlock> acid_block;
    public static RegistryObject<ZPToxicLiquidBlock> toxic_block;

    public static RegistryObject<ZPLayerBlock> sand_layer;
    public static RegistryObject<ZPLayerBlock> ash_layer;
    public static RegistryObject<ZPLayerBlock> gravel_layer;

    public static RegistryObject<ZPBlock> stone_white;
    public static RegistryObject<ZPBlock> stone_black;
    public static RegistryObject<ZPBlock> stone_blue;
    public static RegistryObject<ZPBlock> stone_brown;
    public static RegistryObject<ZPBlock> stone_cyan;
    public static RegistryObject<ZPBlock> stone_gray;
    public static RegistryObject<ZPBlock> stone_green;
    public static RegistryObject<ZPBlock> stone_light_blue;
    public static RegistryObject<ZPBlock> stone_light_gray;
    public static RegistryObject<ZPBlock> stone_lime;
    public static RegistryObject<ZPBlock> stone_magenta;
    public static RegistryObject<ZPBlock> stone_orange;
    public static RegistryObject<ZPBlock> stone_pink;
    public static RegistryObject<ZPBlock> stone_purple;
    public static RegistryObject<ZPBlock> stone_red;
    public static RegistryObject<ZPBlock> stone_yellow;

    public static RegistryObject<ZPStairsBlock> stone_stairs_white;
    public static RegistryObject<ZPStairsBlock> stone_stairs_black;
    public static RegistryObject<ZPStairsBlock> stone_stairs_blue;
    public static RegistryObject<ZPStairsBlock> stone_stairs_brown;
    public static RegistryObject<ZPStairsBlock> stone_stairs_cyan;
    public static RegistryObject<ZPStairsBlock> stone_stairs_gray;
    public static RegistryObject<ZPStairsBlock> stone_stairs_green;
    public static RegistryObject<ZPStairsBlock> stone_stairs_light_blue;
    public static RegistryObject<ZPStairsBlock> stone_stairs_light_gray;
    public static RegistryObject<ZPStairsBlock> stone_stairs_lime;
    public static RegistryObject<ZPStairsBlock> stone_stairs_magenta;
    public static RegistryObject<ZPStairsBlock> stone_stairs_orange;
    public static RegistryObject<ZPStairsBlock> stone_stairs_pink;
    public static RegistryObject<ZPStairsBlock> stone_stairs_purple;
    public static RegistryObject<ZPStairsBlock> stone_stairs_red;
    public static RegistryObject<ZPStairsBlock> stone_stairs_yellow;

    public static RegistryObject<ZPSlabBlock> stone_slab_white;
    public static RegistryObject<ZPSlabBlock> stone_slab_black;
    public static RegistryObject<ZPSlabBlock> stone_slab_blue;
    public static RegistryObject<ZPSlabBlock> stone_slab_brown;
    public static RegistryObject<ZPSlabBlock> stone_slab_cyan;
    public static RegistryObject<ZPSlabBlock> stone_slab_gray;
    public static RegistryObject<ZPSlabBlock> stone_slab_green;
    public static RegistryObject<ZPSlabBlock> stone_slab_light_blue;
    public static RegistryObject<ZPSlabBlock> stone_slab_light_gray;
    public static RegistryObject<ZPSlabBlock> stone_slab_lime;
    public static RegistryObject<ZPSlabBlock> stone_slab_magenta;
    public static RegistryObject<ZPSlabBlock> stone_slab_orange;
    public static RegistryObject<ZPSlabBlock> stone_slab_pink;
    public static RegistryObject<ZPSlabBlock> stone_slab_purple;
    public static RegistryObject<ZPSlabBlock> stone_slab_red;
    public static RegistryObject<ZPSlabBlock> stone_slab_yellow;

    /*
       public static final Block SNOW = register("snow", new SnowLayerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).replaceable().forceSolidOff().randomTicks().strength(0.1F).requiresCorrectToolForDrops().sound(SoundType.SNOW).isViewBlocking((p_187417_, p_187418_, p_187419_) -> {
      return p_187417_.getValue(SnowLayerBlock.LAYERS) >= 8;
   }).pushReaction(PushReaction.DESTROY)));
     */

    public ZPBlocks() {
        super(ZPRegistryConveyor.Target.BLOCK);
    }

    @Override
    protected void runRegister(@NotNull ZPRegSupplier<Block> regSupplier) {
        this.initInstanceCollecting("blocks");
        ZPRegAdminBlocks.init(regSupplier);
        ZPRegCommonBlocks.init(regSupplier);
        ZPRegColorBlocks.init(regSupplier);
        this.stopInstanceCollecting();
        ZPRegFluidBlocks.init(this, regSupplier);
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