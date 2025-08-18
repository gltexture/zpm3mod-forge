package ru.gltexture.zpm3.engine.client.rendering.items.guns.fx;

import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.client.callbacking.ZPClientCallbacks;
import ru.gltexture.zpm3.engine.instances.guns.ZPBaseGun;

public interface IZPGunParticlesFX {
    void triggerSmoke(@NotNull Player player, @NotNull ZPBaseGun baseGun, ZPClientCallbacks.ZPGunShotCallback.@NotNull GunFXData gunFXData);
}
