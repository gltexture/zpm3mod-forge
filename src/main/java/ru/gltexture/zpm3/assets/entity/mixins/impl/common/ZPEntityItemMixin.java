package ru.gltexture.zpm3.assets.entity.mixins.impl.common;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.gltexture.zpm3.assets.common.global.ZPConstants;

@Mixin(ItemEntity.class)
public class ZPEntityItemMixin {
    @Shadow public int lifespan;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onInit(EntityType<? extends ItemEntity> pEntityType, Level pLevel, CallbackInfo ci) {
        this.lifespan = ZPConstants.ENTITY_ITEM_LIFESPAN;
    }
}
