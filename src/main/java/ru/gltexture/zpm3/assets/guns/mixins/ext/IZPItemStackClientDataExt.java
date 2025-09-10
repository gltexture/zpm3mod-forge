package ru.gltexture.zpm3.assets.guns.mixins.ext;

import net.minecraft.world.item.ItemStack;
import ru.gltexture.zpm3.engine.nbt.ZPAbstractNBTClass;

import java.util.UUID;

public interface IZPItemStackClientDataExt {
    ZPAbstractNBTClass.ZPSimpleNBTClass<ItemStack> getClientData();
    void setClientData(ZPAbstractNBTClass.ZPSimpleNBTClass<ItemStack> clientData);
}
