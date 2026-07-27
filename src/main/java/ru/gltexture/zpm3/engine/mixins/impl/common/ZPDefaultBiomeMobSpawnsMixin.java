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

package ru.gltexture.zpm3.engine.mixins.impl.common;

import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.world.level.biome.MobSpawnSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;

@Mixin(value = BiomeDefaultFeatures.class)
public class ZPDefaultBiomeMobSpawnsMixin {
    @Inject(method = "farmAnimals", at = @At("HEAD"), cancellable = true)
    private static void farmAnimals(MobSpawnSettings.Builder pBuilder, CallbackInfo ci) {
        if (ZombiePlague3.getPopulationController().getVanillaBiomePopulationManager().farmAnimals(pBuilder)) {
            ci.cancel();
        }
    }

    @Inject(method = "caveSpawns", at = @At("HEAD"), cancellable = true)
    private static void caveSpawns(MobSpawnSettings.Builder pBuilder, CallbackInfo ci) {
        if (ZombiePlague3.getPopulationController().getVanillaBiomePopulationManager().caveSpawns(pBuilder)) {
            ci.cancel();
        }
    }

    @Inject(method = "commonSpawns", at = @At("HEAD"), cancellable = true)
    private static void commonSpawns(MobSpawnSettings.Builder pBuilder, CallbackInfo ci) {
        if (ZombiePlague3.getPopulationController().getVanillaBiomePopulationManager().commonSpawns(pBuilder)) {
            ci.cancel();
        }
    }

    @Inject(method = "oceanSpawns", at = @At("HEAD"), cancellable = true)
    private static void oceanSpawns(MobSpawnSettings.Builder pBuilder, int pSquidWeight, int pSquidMaxCount, int pCodWeight, CallbackInfo ci) {
        if (ZombiePlague3.getPopulationController().getVanillaBiomePopulationManager().oceanSpawns(pBuilder, pSquidWeight, pSquidMaxCount, pCodWeight)) {
            ci.cancel();
        }
    }

    @Inject(method = "warmOceanSpawns", at = @At("HEAD"), cancellable = true)
    private static void warmOceanSpawns(MobSpawnSettings.Builder pBuilder, int pSquidWeight, int pSquidMinCount, CallbackInfo ci) {
        if (ZombiePlague3.getPopulationController().getVanillaBiomePopulationManager().warmOceanSpawns(pBuilder, pSquidWeight, pSquidMinCount)) {
            ci.cancel();
        }
    }

    @Inject(method = "plainsSpawns", at = @At("HEAD"), cancellable = true)
    private static void plainsSpawns(MobSpawnSettings.Builder pBuilder, CallbackInfo ci) {
        if (ZombiePlague3.getPopulationController().getVanillaBiomePopulationManager().plainsSpawns(pBuilder)) {
            ci.cancel();
        }
    }

    @Inject(method = "snowySpawns", at = @At("HEAD"), cancellable = true)
    private static void snowySpawns(MobSpawnSettings.Builder pBuilder, CallbackInfo ci) {
        if (ZombiePlague3.getPopulationController().getVanillaBiomePopulationManager().snowySpawns(pBuilder)) {
            ci.cancel();
        }
    }

    @Inject(method = "desertSpawns", at = @At("HEAD"), cancellable = true)
    private static void desertSpawns(MobSpawnSettings.Builder pBuilder, CallbackInfo ci) {
        if (ZombiePlague3.getPopulationController().getVanillaBiomePopulationManager().desertSpawns(pBuilder)) {
            ci.cancel();
        }
    }

    @Inject(method = "dripstoneCavesSpawns", at = @At("HEAD"), cancellable = true)
    private static void dripstoneCavesSpawns(MobSpawnSettings.Builder pBuilder, CallbackInfo ci) {
        if (ZombiePlague3.getPopulationController().getVanillaBiomePopulationManager().dripstoneCavesSpawns(pBuilder)) {
            ci.cancel();
        }
    }

    @Inject(method = "monsters", at = @At("HEAD"), cancellable = true)
    private static void monsters(MobSpawnSettings.Builder pBuilder, int pZombieWeight, int pZombieVillageWeight, int pSkeletonWeight, boolean pIsUnderwater, CallbackInfo ci) {
        if (ZombiePlague3.getPopulationController().getVanillaBiomePopulationManager().monsters(pBuilder, pZombieWeight, pZombieVillageWeight, pSkeletonWeight, pIsUnderwater)) {
            ci.cancel();
        }
    }

    @Inject(method = "mooshroomSpawns", at = @At("HEAD"), cancellable = true)
    private static void mooshroomSpawns(MobSpawnSettings.Builder pBuilder, CallbackInfo ci) {
        if (ZombiePlague3.getPopulationController().getVanillaBiomePopulationManager().mooshroomSpawns(pBuilder)) {
            ci.cancel();
        }
    }

    @Inject(method = "baseJungleSpawns", at = @At("HEAD"), cancellable = true)
    private static void baseJungleSpawns(MobSpawnSettings.Builder pBuilder, CallbackInfo ci) {
        if (ZombiePlague3.getPopulationController().getVanillaBiomePopulationManager().baseJungleSpawns(pBuilder)) {
            ci.cancel();
        }
    }

    @Inject(method = "endSpawns", at = @At("HEAD"), cancellable = true)
    private static void endSpawns(MobSpawnSettings.Builder pBuilder, CallbackInfo ci) {
        if (ZombiePlague3.getPopulationController().getVanillaBiomePopulationManager().endSpawns(pBuilder)) {
            ci.cancel();
        }
    }
}
