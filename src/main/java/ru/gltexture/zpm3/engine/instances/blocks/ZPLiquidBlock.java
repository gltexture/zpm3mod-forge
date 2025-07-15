package ru.gltexture.zpm3.engine.instances.blocks;

import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.FlowingFluid;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class ZPLiquidBlock extends LiquidBlock {
    public ZPLiquidBlock(@NotNull Supplier<? extends FlowingFluid> pFluid, @NotNull Properties pProperties) {
        super(pFluid, pProperties);
    }
}
