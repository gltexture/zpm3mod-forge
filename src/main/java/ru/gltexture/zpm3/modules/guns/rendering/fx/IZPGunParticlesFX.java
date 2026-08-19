/*
 *
 *  * zpm3forge
 *  * Copyright (C) 2026 gltexture
 *  *
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program. If not, see <https://www.gnu.org/licenses/>.
 *
 */

package ru.gltexture.zpm3.modules.guns.rendering.fx;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.engine.client.rendering.callbacks.ZPClientCallbacks;
import ru.gltexture.zpm3.modules.guns.item.ZPBaseGun;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;

@Deprecated(forRemoval = true)
public interface IZPGunParticlesFX extends IZPGunFX, ZPClientCallbacks.ZPClientTickCallback, ZPClientCallbacks.ZPGunShotCallback {
    void onEmmitSmoke(@NotNull Player player, @NotNull ZPBaseGun baseGun, @NotNull ItemStack itemStack, boolean isRightHand);
    void onEmmitShell(@NotNull Player player, @NotNull ZPBaseGun baseGun, @NotNull ItemStack itemStack, boolean isRightHand);

    ParticlesEmitterPack DEFAULT_PARTICLES_EMITTER = new ParticlesEmitterPack() {
        @Override
        public @NotNull ParticlesEmitter smokeEmitter() {
            return ((player, baseGun, itemStack, isRightHand) -> {
                for (int i = 0; i < Math.max(baseGun.getGunProperties().getClientRecoil() / 3.0f, 1.0f) + ZPRandom.getRandom().nextInt(2); i++) {
                    ZPDefaultGunParticlesFX.emitParticleSmoke(isRightHand, player, false, baseGun);
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
                    ZPDefaultGunParticlesFX.emitParticleSmoke(isRightHand, player, false, baseGun);
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
                for (int i = 0; i < baseGun.getGunProperties().getClientRecoil() + ZPRandom.getRandom().nextInt(8) + 16; i++) {
                    ZPDefaultGunParticlesFX.emitParticleSmoke(isRightHand, player, true, baseGun);
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
        void emit(@NotNull Player player, @NotNull ZPBaseGun baseGun, @NotNull ItemStack itemStack, boolean isRightHand);
    }
}