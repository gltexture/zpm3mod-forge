package ru.gltexture.zpm3.modules.blocks.instances.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.instances.blocks.ZPDoorBlock;

import javax.annotation.Nullable;

public class ZPRustyDoor extends ZPDoorBlock {
    public ZPRustyDoor(Properties pProperties, BlockSetType pType) {
        super(pProperties, pType);
    }


    @Override
    public void neighborChanged(@NotNull BlockState pState, Level pLevel, @NotNull BlockPos pPos, @NotNull Block pBlock, @NotNull BlockPos pFromPos, boolean pIsMoving) {

    }

    @Override
    public void setOpen(@Nullable Entity pEntity, @NotNull Level pLevel, @NotNull BlockState pState, @NotNull BlockPos pPos, boolean pOpen) {

    }
}
