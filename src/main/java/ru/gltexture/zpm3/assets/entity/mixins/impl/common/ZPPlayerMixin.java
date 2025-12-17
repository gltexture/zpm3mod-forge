package ru.gltexture.zpm3.assets.entity.mixins.impl.common;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.gltexture.zpm3.assets.common.global.ZPConstants;
import ru.gltexture.zpm3.assets.entity.instances.mobs.zombies.ZPAbstractZombie;
import ru.gltexture.zpm3.assets.entity.mixins.ext.IPlayerZmTargetsExt;
import ru.gltexture.zpm3.assets.net_pack.packets.ZPNetCheckPacket;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.mixins.ext.IZPPlayerMixinExt;

import java.util.Comparator;
import java.util.TreeSet;

@Mixin(ServerPlayer.class)
public abstract class ZPPlayerMixin implements IPlayerZmTargetsExt {
    @Unique TreeSet<ZPAbstractZombie> angryZombies = new TreeSet<>(Comparator.comparingDouble(e -> ((ZPAbstractZombie) (e)).distanceTo((Entity) (Object) this)));

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        this.filter();
    }

    @Override
    public TreeSet<ZPAbstractZombie> angryZombies() {
        return this.angryZombies;
    }
}
