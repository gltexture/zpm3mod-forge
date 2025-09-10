package ru.gltexture.zpm3.assets.guns.mixins.impl.client;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import ru.gltexture.zpm3.assets.guns.item.ZPBaseGun;
import ru.gltexture.zpm3.assets.guns.mixins.ext.IZPItemStackClientDataExt;
import ru.gltexture.zpm3.engine.nbt.ZPAbstractNBTClass;

@OnlyIn(Dist.CLIENT)
@Mixin(ItemStack.class)
public abstract class ZPItemStackClDataMixin implements IZPItemStackClientDataExt {
    @Shadow public abstract Item getItem();

    @Shadow public abstract boolean is(TagKey<Item> pTag);

    @Unique
    private ZPAbstractNBTClass.ZPSimpleNBTClass<ItemStack> clientData;

    @Override
    public ZPAbstractNBTClass.ZPSimpleNBTClass<ItemStack> getClientData() {
        if (this.clientData == null) {
            this.clientData = new ZPAbstractNBTClass.ZPSimpleNBTClass<>(new CompoundTag());
            if (this.getItem() instanceof ZPBaseGun baseGun) {
                baseGun.makeHardSync((ItemStack) (Object) this);
            }
        }
        return this.clientData;
    }

    @Override
    public void setClientData(ZPAbstractNBTClass.ZPSimpleNBTClass<ItemStack> clientData) {
        this.clientData = clientData;
    }
}