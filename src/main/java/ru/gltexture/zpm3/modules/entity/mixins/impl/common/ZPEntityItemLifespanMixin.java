package ru.gltexture.zpm3.modules.entity.mixins.impl.common;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.gltexture.zpm3.engine.core.config.builtin.ZPWorldConfig;


@Mixin(ItemEntity.class)
public abstract class ZPEntityItemLifespanMixin {
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
        this.lifespan = ZPWorldConfig.ENTITY_ITEM_LIFESPAN.getVar();
    }

    @Inject(method = "makeFakeItem", at = @At("HEAD"), cancellable = true)
    public void makeFakeItem(CallbackInfo ci) {
        this.setNeverPickUp();
        this.age = ZPWorldConfig.ENTITY_ITEM_LIFESPAN.getVar() - 1;
        ci.cancel();
    }
}
