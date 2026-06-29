package ru.gltexture.zpm3.modules.entity.mixins.impl.common;

import net.minecraft.world.entity.MobCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.gltexture.zpm3.modules.entity.events.common.ZPWorldTickEvent;

@Mixin(MobCategory.class)
public class ZPMobCategoryIncreaseSpawnMixin {
    @Inject(method = "getMaxInstancesPerChunk", at = @At("HEAD"), cancellable = true)
    public void getMaxInstancesPerChunk(CallbackInfoReturnable<Integer> cir) {
        if ((Object) this == MobCategory.MONSTER) {
            cir.setReturnValue(ZPWorldTickEvent.MAX_ZOMBIES_IN_CHUNK);
        }
    }
}
