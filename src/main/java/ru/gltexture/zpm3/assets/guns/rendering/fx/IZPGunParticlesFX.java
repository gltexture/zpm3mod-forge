package ru.gltexture.zpm3.assets.guns.rendering.fx;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.guns.rendering.basic.ZPDefaultGunParticlesFX;
import ru.gltexture.zpm3.engine.client.callbacking.ZPClientCallbacks;
import ru.gltexture.zpm3.assets.guns.item.ZPBaseGun;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;

public interface IZPGunParticlesFX extends ZPClientCallbacks.ZPClientTickCallback {
    void triggerParticles(@NotNull Player player, @NotNull ZPBaseGun baseGun, @NotNull ItemStack itemStack, ZPClientCallbacks.ZPGunShotCallback.@NotNull GunFXData gunFXData);

    ParticlesEmitter DEFAULT_PARTICLES_EMITTER = ((player, baseGun, itemStack, gunFXData, shotTicksAccumulator) -> {
        ZPDefaultGunParticlesFX.emmitParticleShell(gunFXData.isRightHand(), player);
        for (int i = 0; i < Math.max(gunFXData.recoilStrength() / 3.0f, 1.0f) + ZPRandom.getRandom().nextInt(2); i++) {
            ZPDefaultGunParticlesFX.emmitParticleSmoke(gunFXData.isRightHand(), player, false);
        }
    });

    ParticlesEmitter DEFAULT_PARTICLES_EMITTER_NO_SHELL = ((player, baseGun, itemStack, gunFXData, shotTicksAccumulator) -> {
        for (int i = 0; i < Math.max(gunFXData.recoilStrength() / 3.0f, 1.0f) + ZPRandom.getRandom().nextInt(2); i++) {
            ZPDefaultGunParticlesFX.emmitParticleSmoke(gunFXData.isRightHand(), player, false);
        }
    });

    ParticlesEmitter DEFAULT_PARTICLES_EMITTER_NO_SMOKE = ((player, baseGun, itemStack, gunFXData, shotTicksAccumulator) -> {
        ZPDefaultGunParticlesFX.emmitParticleShell(gunFXData.isRightHand(), player);
    });

    ParticlesEmitter DEFAULT_PARTICLES_EMITTER_SUPER_SMOKY_NO_SHELL = ((player, baseGun, itemStack, gunFXData, shotTicksAccumulator) -> {
        for (int i = 0; i < gunFXData.recoilStrength() + ZPRandom.getRandom().nextInt(9) + 8; i++) {
            ZPDefaultGunParticlesFX.emmitParticleSmoke(gunFXData.isRightHand(), player, true);
        }
    });

    @FunctionalInterface
    interface ParticlesEmitter {
        void emmit(@NotNull Player player, @NotNull ZPBaseGun baseGun, @NotNull ItemStack itemStack, ZPClientCallbacks.ZPGunShotCallback.@NotNull GunFXData gunFXData, float[] shotTicksAccumulator);
    }
}
