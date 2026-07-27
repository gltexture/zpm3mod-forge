/*
 *
 *  * zpm3forge
 *  * Copyright (C) 2026 gltexture
 *  *
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program. If not, see <https://www.gnu.org/licenses/>.
 *
 */

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