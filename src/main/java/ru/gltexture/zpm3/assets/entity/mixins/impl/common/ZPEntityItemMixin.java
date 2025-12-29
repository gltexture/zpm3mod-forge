package ru.gltexture.zpm3.assets.entity.mixins.impl.common;

import net.minecraft.world.entity.item.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.gltexture.zpm3.assets.common.global.ZPConstants;

@Mixin(ItemEntity.class)
public abstract class ZPEntityItemMixin {
    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        ItemEntity self = (ItemEntity) (Object) this;
        if (!self.level().isClientSide && self.tickCount == 1) {
            self.lifespan = ZPConstants.ENTITY_ITEM_LIFESPAN;
        }
    }
}
