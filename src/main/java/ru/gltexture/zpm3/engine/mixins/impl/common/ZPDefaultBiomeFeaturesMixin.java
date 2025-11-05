package ru.gltexture.zpm3.engine.mixins.impl.common;

import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.world.level.biome.MobSpawnSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;

@Mixin(value = BiomeDefaultFeatures.class)
public class ZPDefaultBiomeFeaturesMixin {
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
