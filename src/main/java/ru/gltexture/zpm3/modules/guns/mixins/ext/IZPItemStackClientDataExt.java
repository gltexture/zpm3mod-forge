package ru.gltexture.zpm3.modules.guns.mixins.ext;

import net.minecraft.world.item.ItemStack;
import ru.gltexture.zpm3.engine.nbt.ZPAbstractNBTClass;

public interface IZPItemStackClientDataExt {
    ZPAbstractNBTClass.ZPSimpleNBTClass<ItemStack> zpm3forge$getClientData();
    void zpm3forge$setClientData(ZPAbstractNBTClass.ZPSimpleNBTClass<ItemStack> clientData);
}
