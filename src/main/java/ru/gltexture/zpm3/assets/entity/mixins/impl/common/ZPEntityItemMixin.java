package ru.gltexture.zpm3.assets.entity.mixins.impl.common;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.gltexture.zpm3.assets.common.global.ZPConstants;

@Mixin(ItemEntity.class)
public abstract class ZPEntityItemMixin {
    @Shadow
    public abstract void setNeverPickUp();

    @Shadow
    private int age;

    @Shadow
    public abstract ItemStack getItem();

    @Shadow
    public int lifespan;

    @Inject(method = "<init>*", at = @At("TAIL"))
    private void tick(CallbackInfo ci) {
        this.lifespan = ZPConstants.ENTITY_ITEM_LIFESPAN;
    }

    @Inject(method = "makeFakeItem", at = @At("HEAD"), cancellable = true)
    public void makeFakeItem(CallbackInfo ci) {
        this.setNeverPickUp();
        this.age = ZPConstants.ENTITY_ITEM_LIFESPAN - 1;
        ci.cancel();
    }
}
