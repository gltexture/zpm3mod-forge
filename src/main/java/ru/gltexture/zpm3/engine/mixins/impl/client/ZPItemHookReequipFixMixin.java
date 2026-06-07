package ru.gltexture.zpm3.engine.mixins.impl.client;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import ru.gltexture.zpm3.engine.mixins.ext.IZPItemExt;

@OnlyIn(Dist.CLIENT)
@Mixin(Item.class)
public class ZPItemHookReequipFixMixin implements IZPItemExt {
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }
}