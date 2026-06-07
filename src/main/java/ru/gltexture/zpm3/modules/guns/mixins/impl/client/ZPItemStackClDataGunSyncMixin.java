package ru.gltexture.zpm3.modules.guns.mixins.impl.client;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import ru.gltexture.zpm3.modules.guns.item.ZPBaseGun;
import ru.gltexture.zpm3.modules.guns.mixins.ext.IZPItemStackClientDataExt;
import ru.gltexture.zpm3.engine.nbt.ZPAbstractNBTClass;
import ru.gltexture.zpm3.engine.service.ZPUtility;

@OnlyIn(Dist.CLIENT)
@Mixin(ItemStack.class)
public abstract class ZPItemStackClDataGunSyncMixin implements IZPItemStackClientDataExt {
    @Shadow public abstract Item getItem();

    @Shadow public abstract boolean is(TagKey<Item> pTag);

    @Unique
    private ZPAbstractNBTClass.ZPSimpleNBTClass<ItemStack> zpm3forge$clientData;

    @Override
    public ZPAbstractNBTClass.ZPSimpleNBTClass<ItemStack> zpm3forge$getClientData() {
        if (this.zpm3forge$clientData == null) {
            this.zpm3forge$clientData = new ZPAbstractNBTClass.ZPSimpleNBTClass<>(new CompoundTag());
            if (this.getItem() instanceof ZPBaseGun baseGun) {
                ZPUtility.sides().onlyClient(() -> {
                    baseGun.makeHardSync((ItemStack) (Object) this);
                });
            }
        }
        return this.zpm3forge$clientData;
    }

    @Override
    public void zpm3forge$setClientData(ZPAbstractNBTClass.ZPSimpleNBTClass<ItemStack> clientData) {
        this.zpm3forge$clientData = clientData;
    }
}