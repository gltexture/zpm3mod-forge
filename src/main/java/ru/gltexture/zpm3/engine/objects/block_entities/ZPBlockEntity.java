package ru.gltexture.zpm3.engine.objects.block_entities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class ZPBlockEntity extends BlockEntity {
    public ZPBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    public boolean isServer() {
        return this.getLevel() != null && !this.getLevel().isClientSide();
    }

    public boolean isClient() {
        return this.getLevel() != null && this.getLevel().isClientSide();
    }
}
