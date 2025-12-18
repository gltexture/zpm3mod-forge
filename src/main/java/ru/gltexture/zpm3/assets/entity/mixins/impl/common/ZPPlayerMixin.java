package ru.gltexture.zpm3.assets.entity.mixins.impl.common;

import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.gltexture.zpm3.assets.entity.instances.mobs.zombies.ZPAbstractZombie;
import ru.gltexture.zpm3.assets.entity.mixins.ext.IPlayerZmTargetsExt;

import java.util.*;

@Mixin(ServerPlayer.class)
public abstract class ZPPlayerMixin implements IPlayerZmTargetsExt {
    @Unique List<ZPAbstractZombie> angryZombies = new ArrayList<>();
    @Unique Set<ZPAbstractZombie> angryZombiesRegistry = new HashSet<>();

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        this.filter();
    }

    @Override
    public List<ZPAbstractZombie> angryZombies() {
        return this.angryZombies;
    }

    @Override
    public Set<ZPAbstractZombie> angryZombiesRegistrySet() {
        return this.angryZombiesRegistry;
    }
}
