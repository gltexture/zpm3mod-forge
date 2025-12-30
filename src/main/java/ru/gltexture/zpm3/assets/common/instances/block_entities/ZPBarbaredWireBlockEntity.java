package ru.gltexture.zpm3.assets.common.instances.block_entities;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.init.ZPBlockEntities;
import ru.gltexture.zpm3.assets.common.instances.blocks.torch.IFadingBlock;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;
import ru.gltexture.zpm3.engine.instances.block_entities.ZPBlockEntity;
import ru.gltexture.zpm3.engine.instances.blocks.ZPTorchBlock;
import ru.gltexture.zpm3.engine.service.ZPUtility;

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