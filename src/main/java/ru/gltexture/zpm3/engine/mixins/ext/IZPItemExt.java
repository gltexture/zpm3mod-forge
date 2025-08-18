package ru.gltexture.zpm3.engine.mixins.ext;

import net.minecraft.world.item.ItemStack;

public interface IZPItemExt {
    boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged);
}
