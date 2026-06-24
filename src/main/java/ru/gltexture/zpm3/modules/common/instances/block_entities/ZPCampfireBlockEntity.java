package ru.gltexture.zpm3.modules.common.instances.block_entities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.modules.common.init.ZPBlockEntities;

public class ZPCampfireBlockEntity extends CampfireBlockEntity {
    public ZPCampfireBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(pPos, pBlockState);
    }

    @Override
    public @NotNull BlockEntityType<?> getType() {
        return ZPBlockEntities.campfire_block_entity.get();
    }
}
