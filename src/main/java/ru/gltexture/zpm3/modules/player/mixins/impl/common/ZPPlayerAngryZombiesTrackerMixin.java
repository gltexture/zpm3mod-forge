package ru.gltexture.zpm3.modules.player.mixins.impl.common;

import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.gltexture.zpm3.modules.entity.instances.mobs.zombies.ZPAbstractZombie;
import ru.gltexture.zpm3.modules.entity.mixins.ext.IPlayerZmTargetsExt;

import java.util.*;

@Mixin(ServerPlayer.class)
public abstract class ZPPlayerAngryZombiesTrackerMixin implements IPlayerZmTargetsExt {
    @Unique List<ZPAbstractZombie> zpm3forge$angryZombies = new ArrayList<>();
    @Unique Set<ZPAbstractZombie> zpm3forge$angryZombiesRegistry = new HashSet<>();

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        this.filter();
    }

    @Override
    public List<ZPAbstractZombie> zpm3forge$angryZombies() {
        return this.zpm3forge$angryZombies;
    }

    @Override
    public Set<ZPAbstractZombie> zpm3forge$angryZombiesRegistrySet() {
        return this.zpm3forge$angryZombiesRegistry;
    }
}
