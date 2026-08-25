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

package ru.gltexture.zpm3.engine.nbt.itemstack;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.engine.nbt.ZPAbstractNBTClass;

import java.util.Objects;

public final class ZPItemStackNBT extends ZPAbstractNBTClass<ItemStack> {
    private final @Nullable String innerCompound;

    private ZPItemStackNBT(ItemStack itemStack, @Nullable String innerCompound, CompoundTag compoundTag) {
        super(itemStack);
        this.innerCompound = innerCompound;
        itemStack.setTag(compoundTag.copy());
    }

    public ZPItemStackNBT(ItemStack itemStack) {
        super(itemStack);
        this.innerCompound = null;
    }

    public ZPItemStackNBT(ItemStack itemStack, @Nullable String innerCompound) {
        super(itemStack);
        this.innerCompound = innerCompound;
    }

    public CompoundTag createTag(@Nullable CompoundTag mainTag) {
        if (this.innerCompound != null) {
            CompoundTag zpTag = Objects.requireNonNull(mainTag).getCompound(this.innerCompound);
            if (!mainTag.contains(this.innerCompound, Tag.TAG_COMPOUND)) {
                zpTag = new CompoundTag();
                mainTag.put(this.innerCompound, zpTag);
            }
            return zpTag;
        }
        return mainTag;
    }

    public CompoundTag getTagOrLazyCreateTag() {
        return this.createTag(this.t.getOrCreateTag());
    }

    public ZPItemStackNBT copy() {
        return new ZPItemStackNBT(this.t, this.innerCompound, this.getTagOrLazyCreateTag().copy());
    }
}
