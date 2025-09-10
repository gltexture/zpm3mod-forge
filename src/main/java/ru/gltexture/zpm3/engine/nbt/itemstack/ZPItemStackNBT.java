package ru.gltexture.zpm3.engine.nbt.itemstack;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.engine.nbt.ZPAbstractNBTClass;
import ru.gltexture.zpm3.engine.service.Pair;

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

    public CompoundTag getTag() {
        if (this.innerCompound != null) {
            CompoundTag mainTag = this.t.getOrCreateTag();
            CompoundTag zpTag = mainTag.getCompound(this.innerCompound);
            if (!mainTag.contains(this.innerCompound, Tag.TAG_COMPOUND)) {
                zpTag = new CompoundTag();
                mainTag.put(this.innerCompound, zpTag);
            }
            return zpTag;
        }
        return this.t.getOrCreateTag();
    }

    public ZPItemStackNBT copy() {
        return new ZPItemStackNBT(this.t, this.innerCompound, this.getTag().copy());
    }
}
