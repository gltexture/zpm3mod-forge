package ru.gltexture.zpm3.assets.common.init.helper;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.init.ZPSounds;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.helpers.gen.providers.ZPSoundListProvider;
import ru.gltexture.zpm3.engine.registry.ZPRegistry;

import java.util.List;

public abstract class ZPRegSounds {
    public static void init(@NotNull ZPRegistry.ZPRegSupplier<SoundEvent> regSupplier) {
        ZPSounds.syringe = regSupplier.register("syringe", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "syringe"))).postConsume(Dist.CLIENT, (e, utils) -> {
            utils.sounds().addNewSound(new ZPSoundListProvider.ZPSoundEvent("syringe", false, "sounds.zpm3.syringe", List.of(new ZPSoundListProvider.SoundData("medicine/syringe"))));
        }).registryObject();

        ZPSounds.pills = regSupplier.register("pills", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "pills"))).postConsume(Dist.CLIENT, (e, utils) -> {
            utils.sounds().addNewSound(new ZPSoundListProvider.ZPSoundEvent("pills", false, "sounds.zpm3.pills", List.of(new ZPSoundListProvider.SoundData("medicine/pills"))));
        }).registryObject();

        ZPSounds.bandage = regSupplier.register("bandage", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "bandage"))).postConsume(Dist.CLIENT, (e, utils) -> {
            utils.sounds().addNewSound(new ZPSoundListProvider.ZPSoundEvent("bandage", false, "sounds.zpm3.bandage", List.of(new ZPSoundListProvider.SoundData("medicine/bandage"))));
        }).registryObject();


        ZPSounds.makarov_fire = regSupplier.register("makarov_fire", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "makarov_fire"))).postConsume(Dist.CLIENT, (e, utils) -> {
            utils.sounds().addNewSound(new ZPSoundListProvider.ZPSoundEvent("makarov_fire", false, "sounds.zpm3.makarov_fire", List.of(new ZPSoundListProvider.SoundData("guns/makarov_fire"))));
        }).registryObject();

        ZPSounds.makarov_reload = regSupplier.register("makarov_reload", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "makarov_reload"))).postConsume(Dist.CLIENT, (e, utils) -> {
            utils.sounds().addNewSound(new ZPSoundListProvider.ZPSoundEvent("makarov_reload", false, "sounds.zpm3.makarov_reload", List.of(new ZPSoundListProvider.SoundData("guns/makarov_reload"))));
        }).registryObject();


        ZPSounds.handmade_pistol_fire = regSupplier.register("handmade_pistol_fire", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "handmade_pistol_fire"))).postConsume(Dist.CLIENT, (e, utils) -> {
            utils.sounds().addNewSound(new ZPSoundListProvider.ZPSoundEvent("handmade_pistol_fire", false, "sounds.zpm3.handmade_pistol_fire", List.of(new ZPSoundListProvider.SoundData("guns/handmade_pistol_fire"))));
        }).registryObject();

        ZPSounds.handmade_pistol_reload = regSupplier.register("handmade_pistol_reload", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "handmade_pistol_reload"))).postConsume(Dist.CLIENT, (e, utils) -> {
            utils.sounds().addNewSound(new ZPSoundListProvider.ZPSoundEvent("handmade_pistol_reload", false, "sounds.zpm3.handmade_pistol_reload", List.of(new ZPSoundListProvider.SoundData("guns/handmade_pistol_reload"))));
        }).registryObject();


        ZPSounds.deagle_fire = regSupplier.register("deagle_fire", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "deagle_fire"))).postConsume(Dist.CLIENT, (e, utils) -> {
            utils.sounds().addNewSound(new ZPSoundListProvider.ZPSoundEvent("deagle_fire", false, "sounds.zpm3.deagle_fire", List.of(new ZPSoundListProvider.SoundData("guns/deagle_fire"))));
        }).registryObject();

        ZPSounds.deagle_reload = regSupplier.register("deagle_reload", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "deagle_reload"))).postConsume(Dist.CLIENT, (e, utils) -> {
            utils.sounds().addNewSound(new ZPSoundListProvider.ZPSoundEvent("deagle_reload", false, "sounds.zpm3.deagle_reload", List.of(new ZPSoundListProvider.SoundData("guns/deagle_reload"))));
        }).registryObject();


        ZPSounds.m1911_fire = regSupplier.register("m1911_fire", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "m1911_fire"))).postConsume(Dist.CLIENT, (e, utils) -> {
            utils.sounds().addNewSound(new ZPSoundListProvider.ZPSoundEvent("m1911_fire", false, "sounds.zpm3.m1911_fire", List.of(new ZPSoundListProvider.SoundData("guns/m1911_fire"))));
        }).registryObject();

        ZPSounds.m1911_reload = regSupplier.register("m1911_reload", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "m1911_reload"))).postConsume(Dist.CLIENT, (e, utils) -> {
            utils.sounds().addNewSound(new ZPSoundListProvider.ZPSoundEvent("m1911_reload", false, "sounds.zpm3.m1911_reload", List.of(new ZPSoundListProvider.SoundData("guns/m1911_reload"))));
        }).registryObject();


        ZPSounds.usp_fire = regSupplier.register("usp_fire", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "usp_fire"))).postConsume(Dist.CLIENT, (e, utils) -> {
            utils.sounds().addNewSound(new ZPSoundListProvider.ZPSoundEvent("usp_fire", false, "sounds.zpm3.usp_fire", List.of(new ZPSoundListProvider.SoundData("guns/usp_fire"))));
        }).registryObject();

        ZPSounds.usp_reload = regSupplier.register("usp_reload", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "usp_reload"))).postConsume(Dist.CLIENT, (e, utils) -> {
            utils.sounds().addNewSound(new ZPSoundListProvider.ZPSoundEvent("usp_reload", false, "sounds.zpm3.usp_reload", List.of(new ZPSoundListProvider.SoundData("guns/usp_reload"))));
        }).registryObject();


        ZPSounds.uzi_fire = regSupplier.register("uzi_fire", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "uzi_fire"))).postConsume(Dist.CLIENT, (e, utils) -> {
            utils.sounds().addNewSound(new ZPSoundListProvider.ZPSoundEvent("uzi_fire", false, "sounds.zpm3.uzi_fire", List.of(new ZPSoundListProvider.SoundData("guns/uzi_fire"))));
        }).registryObject();

        ZPSounds.uzi_reload = regSupplier.register("uzi_reload", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "uzi_reload"))).postConsume(Dist.CLIENT, (e, utils) -> {
            utils.sounds().addNewSound(new ZPSoundListProvider.ZPSoundEvent("uzi_reload", false, "sounds.zpm3.uzi_reload", List.of(new ZPSoundListProvider.SoundData("guns/uzi_reload"))));
        }).registryObject();


        ZPSounds.colt_fire = regSupplier.register("colt_fire", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "colt_fire"))).postConsume(Dist.CLIENT, (e, utils) -> {
            utils.sounds().addNewSound(new ZPSoundListProvider.ZPSoundEvent("colt_fire", false, "sounds.zpm3.colt_fire", List.of(new ZPSoundListProvider.SoundData("guns/colt_fire"))));
        }).registryObject();

        ZPSounds.colt_reload = regSupplier.register("colt_reload", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "colt_reload"))).postConsume(Dist.CLIENT, (e, utils) -> {
            utils.sounds().addNewSound(new ZPSoundListProvider.ZPSoundEvent("colt_reload", false, "sounds.zpm3.colt_reload", List.of(new ZPSoundListProvider.SoundData("guns/colt_reload"))));
        }).registryObject();


        ZPSounds.empty = regSupplier.register("empty", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "empty"))).postConsume(Dist.CLIENT, (e, utils) -> {
            utils.sounds().addNewSound(new ZPSoundListProvider.ZPSoundEvent("empty", false, "sounds.zpm3.empty", List.of(new ZPSoundListProvider.SoundData("guns/empty"))));
        }).registryObject();
    }
}
