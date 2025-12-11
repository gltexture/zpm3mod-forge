package ru.gltexture.zpm3.assets.entity.mixins.ext;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface IItemEntityExt {
    int getEntityLifespan(ItemStack itemStack, Level level);
}
