package ru.gltexture.zpm3.assets.entity.mixins.impl.common;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import ru.gltexture.zpm3.assets.common.global.ZPConstants;
import ru.gltexture.zpm3.assets.entity.mixins.ext.IItemEntityExt;

@Mixin(Item.class)
public class ZPForgeItemMixin implements IItemEntityExt {
    @Override
    public int getEntityLifespan(ItemStack itemStack, Level level) {
        return ZPConstants.ENTITY_ITEM_LIFESPAN;
    }
}
