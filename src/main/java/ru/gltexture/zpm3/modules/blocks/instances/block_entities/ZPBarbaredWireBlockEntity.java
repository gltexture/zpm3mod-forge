package ru.gltexture.zpm3.modules.blocks.instances.block_entities;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.modules.blocks.init.ZPBlockEntities;
import ru.gltexture.zpm3.engine.instances.block_entities.ZPBlockEntity;

public class ZPBarbaredWireBlockEntity extends ZPBlockEntity {
    public static final String NBT_DAMAGE = "damage";

    private int damage;

    public ZPBarbaredWireBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ZPBlockEntities.barbared_wire_block_entity.get(), pPos, pBlockState);
    }

    public int getDamage() {
        return this.damage;
    }

    public ZPBarbaredWireBlockEntity setDamage(int damage) {
        this.damage = damage;
        return this;
    }

    @Override
    public void load(@NotNull CompoundTag pTag) {
        super.load(pTag);
        if (pTag.contains(ZPBarbaredWireBlockEntity.NBT_DAMAGE)) {
            this.damage = pTag.getInt(ZPBarbaredWireBlockEntity.NBT_DAMAGE);
        }
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag pTag) {
        super.saveAdditional(pTag);
        if (this.isServer()) {
            pTag.putInt(ZPBarbaredWireBlockEntity.NBT_DAMAGE, this.damage);
        }
    }
}