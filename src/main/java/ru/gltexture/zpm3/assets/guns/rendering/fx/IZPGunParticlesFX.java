package ru.gltexture.zpm3.assets.guns.rendering.fx;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.assets.guns.rendering.basic.ZPDefaultGunParticlesFX;
import ru.gltexture.zpm3.engine.client.callbacking.ZPClientCallbacks;
import ru.gltexture.zpm3.assets.guns.item.ZPBaseGun;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;

public interface IZPGunParticlesFX extends IZPGunFX, ZPClientCallbacks.ZPClientTickCallback, ZPClientCallbacks.ZPGunShotCallback {
    void onEmmitSmoke(@NotNull Player player, @NotNull ZPBaseGun baseGun, @NotNull ItemStack itemStack, boolean isRightHand);
    void onEmmitShell(@NotNull Player player, @NotNull ZPBaseGun baseGun, @NotNull ItemStack itemStack, boolean isRightHand);

    ParticlesEmitterPack DEFAULT_PARTICLES_EMITTER = new ParticlesEmitterPack() {
        @Override
        public @NotNull ParticlesEmitter smokeEmitter() {
            return ((player, baseGun, itemStack, isRightHand) -> {
                for (int i = 0; i < Math.max(baseGun.getGunProperties().getClientRecoil() / 3.0f, 1.0f) + ZPRandom.getRandom().nextInt(2); i++) {
                    ZPDefaultGunParticlesFX.emmitParticleSmoke(isRightHand, player, false, baseGun);
                }
            });
        }

        @Override
        public @NotNull ParticlesEmitter shellsEmitter() {
            return ((player, baseGun, itemStack, isRightHand) -> {
                ZPDefaultGunParticlesFX.emmitParticleShell(isRightHand, player, baseGun);
            });
        }
    };

    ParticlesEmitterPack DEFAULT_PARTICLES_EMITTER_NO_SHELL = new ParticlesEmitterPack() {
        @Override
        public @NotNull ParticlesEmitter smokeEmitter() {
            return ((player, baseGun, itemStack, isRightHand) -> {
                for (int i = 0; i < Math.max(baseGun.getGunProperties().getClientRecoil() / 3.0f, 1.0f) + ZPRandom.getRandom().nextInt(2); i++) {
                    ZPDefaultGunParticlesFX.emmitParticleSmoke(isRightHand, player, false, baseGun);
                }
            });
        }

        @Override
        public @Nullable ParticlesEmitter shellsEmitter() {
            return null;
        }
    };

    ParticlesEmitterPack DEFAULT_PARTICLES_EMITTER_NO_SMOKE = new ParticlesEmitterPack() {
        @Override
        public @Nullable ParticlesEmitter smokeEmitter() {
            return null;
        }

        @Override
        public @NotNull ParticlesEmitter shellsEmitter() {
            return ((player, baseGun, itemStack, isRightHand) -> {
                ZPDefaultGunParticlesFX.emmitParticleShell(isRightHand, player, baseGun);
            });
        }
    };

    ParticlesEmitterPack DEFAULT_PARTICLES_EMITTER_SUPER_SMOKY_NO_SHELL = new ParticlesEmitterPack() {
        @Override
        public @NotNull ParticlesEmitter smokeEmitter() {
            return ((player, baseGun, itemStack, isRightHand) -> {
                for (int i = 0; i < baseGun.getGunProperties().getClientRecoil() + ZPRandom.getRandom().nextInt(8) + 8; i++) {
                    ZPDefaultGunParticlesFX.emmitParticleSmoke(isRightHand, player, true, baseGun);
                }
            });
        }

        @Override
        public @Nullable ParticlesEmitter shellsEmitter() {
            return null;
        }
    };

    interface ParticlesEmitterPack {
        @Nullable ParticlesEmitter smokeEmitter();
        @Nullable ParticlesEmitter shellsEmitter();
    }

    @FunctionalInterface
    interface ParticlesEmitter {
        void emmit(@NotNull Player player, @NotNull ZPBaseGun baseGun, @NotNull ItemStack itemStack, boolean isRightHand);
    }
}
