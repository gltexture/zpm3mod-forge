package ru.gltexture.zpm3.engine.mixins.impl.common;

import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.gltexture.zpm3.engine.world.GlobalBlocksDestroyMemory;
import ru.gltexture.zpm3.engine.mixins.ext.IZPLevelExt;

import java.util.function.BooleanSupplier;

@Mixin(ServerLevel.class)
public abstract class ZPServerLevelMixin implements IZPLevelExt {
    @Shadow
    public abstract ServerLevel getLevel();

    @Unique private final GlobalBlocksDestroyMemory globalBlocksDestroyMemory = new GlobalBlocksDestroyMemory();

    @Inject(method = "tick", at = @At("TAIL"))
    private void tickPost(BooleanSupplier pHasTimeLeft, CallbackInfo ci) {
        this.getGlobalBlocksDestroyMemory().refreshMemory(this.getLevel());
    }

    @Override
    public GlobalBlocksDestroyMemory getGlobalBlocksDestroyMemory() {
        return this.globalBlocksDestroyMemory;
    }
}
