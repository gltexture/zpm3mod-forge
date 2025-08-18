package ru.gltexture.zpm3.engine.nbt.itemstack;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.nbt.ZPAbstractNBTClass;
import ru.gltexture.zpm3.engine.service.Pair;

public final class ZPItemStackNBT extends ZPAbstractNBTClass<ItemStack> {
    public ZPItemStackNBT(ItemStack itemStack) {
        super(itemStack);
    }

    public CompoundTag getTag() {
        return this.t.getOrCreateTag();
    }
}
