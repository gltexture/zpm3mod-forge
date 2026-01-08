package ru.gltexture.zpm3.engine.mixins.impl.common;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.npc.WanderingTraderSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WanderingTraderSpawner.class)
public class ZPTraderSpawnerDisablerMixin {
    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    public void tick(ServerLevel pLevel, boolean pSpawnHostiles, boolean pSpawnPassives, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(0);
    }
}
