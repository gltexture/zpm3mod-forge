package ru.gltexture.zpm3.modules.blocks.instances.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.instances.blocks.ZPTrapDoor;

public class ZPRustyTrapDoor extends ZPTrapDoor {
    public ZPRustyTrapDoor(Properties pProperties, BlockSetType pType) {
        super(pProperties, pType);
    }

    @Override
    public void neighborChanged(@NotNull BlockState pState, Level pLevel, @NotNull BlockPos pPos, @NotNull Block pBlock, @NotNull BlockPos pFromPos, boolean pIsMoving) {
        if (!pLevel.isClientSide) {
            if (pState.getValue(WATERLOGGED)) {
                pLevel.scheduleTick(pPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
            }
        }
    }

}
