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
        ZPSounds.zm_miner_hurt = regSupplier.register("zm_miner_hurt", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "zm_miner_hurt")))
                .afterCreated((e, utils) -> {
            utils.sounds().addNewSound(new ZPSoundListProvider.ZPSoundEvent("zm_miner_hurt", false, "sounds.zpm3.zm_miner_hurt", List.of(
                    new ZPSoundListProvider.SoundData("zombie/miner/hurt1"),
                    new ZPSoundListProvider.SoundData("zombie/miner/hurt2"),
                    new ZPSoundListProvider.SoundData("zombie/miner/hurt3"),
                    new ZPSoundListProvider.SoundData("zombie/miner/hurt4")
            )));
        }).end();

        ZPSounds.headshot = regSupplier.register("headshot", () -> SoundEvent.createVariableRangeEvent(
                        ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "headshot")))
                .afterCreated((e, utils) -> {
                    utils.sounds().addNewSound(new ZPSoundListProvider.ZPSoundEvent("headshot", false, "sounds.zpm3.headshot",
                            List.of(
                                    new ZPSoundListProvider.SoundData("effects/headshot2"),
                                    new ZPSoundListProvider.SoundData("effects/headshot3")
                            )
                    ));
                }).end();

        ZPSounds.syringe = regSupplier.register("syringe", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "syringe")))
                .afterCreated((e, utils) -> {
            utils.sounds().addNewSound(new ZPSoundListProvider.ZPSoundEvent("syringe", false, "sounds.zpm3.syringe", List.of(new ZPSoundListProvider.SoundData("medicine/syringe"))));
        }).end();

        ZPSounds.pills = regSupplier.register("pills", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "pills")))
                .afterCreated((e, utils) -> {
            utils.sounds().addNewSound(new ZPSoundListProvider.ZPSoundEvent("pills", false, "sounds.zpm3.pills", List.of(new ZPSoundListProvider.SoundData("medicine/pills"))));
        }).end();

        ZPSounds.bandage = regSupplier.register("bandage", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "bandage")))
                .afterCreated((e, utils) -> {
            utils.sounds().addNewSound(new ZPSoundListProvider.ZPSoundEvent("bandage", false, "sounds.zpm3.bandage", List.of(new ZPSoundListProvider.SoundData("medicine/bandage"))));
        }).end();


        ZPSounds.makarov_fire = regSupplier.register("makarov_fire", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "makarov_fire")))
                .afterCreated((e, utils) -> {
            utils.sounds().addNewSound(new ZPSoundListProvider.ZPSoundEvent("makarov_fire", false, "sounds.zpm3.makarov_fire", List.of(new ZPSoundListProvider.SoundData("guns/makarov_fire"))));
        }).end();

        ZPSounds.makarov_reload = regSupplier.register("makarov_reload", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "makarov_reload")))
                .afterCreated((e, utils) -> {
            utils.sounds().addNewSound(new ZPSoundListProvider.ZPSoundEvent("makarov_reload", false, "sounds.zpm3.makarov_reload", List.of(new ZPSoundListProvider.SoundData("guns/makarov_reload"))));
        }).end();


        ZPSounds.handmade_pistol_fire = regSupplier.register("handmade_pistol_fire", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "handmade_pistol_fire")))
                .afterCreated((e, utils) -> {
            utils.sounds().addNewSound(new ZPSoundListProvider.ZPSoundEvent("handmade_pistol_fire", false, "sounds.zpm3.handmade_pistol_fire", List.of(new ZPSoundListProvider.SoundData("guns/handmade_pistol_fire"))));
        }).end();

        ZPSounds.handmade_pistol_reload = regSupplier.register("handmade_pistol_reload", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "handmade_pistol_reload")))
                .afterCreated((e, utils) -> {
            utils.sounds().addNewSound(new ZPSoundListProvider.ZPSoundEvent("handmade_pistol_reload", false, "sounds.zpm3.handmade_pistol_reload", List.of(new ZPSoundListProvider.SoundData("guns/handmade_pistol_reload"))));
        }).end();


        ZPSounds.deagle_fire = regSupplier.register("deagle_fire", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "deagle_fire")))
                .afterCreated((e, utils) -> {
            utils.sounds().addNewSound(new ZPSoundListProvider.ZPSoundEvent("deagle_fire", false, "sounds.zpm3.deagle_fire", List.of(new ZPSoundListProvider.SoundData("guns/deagle_fire"))));
        }).end();

        ZPSounds.deagle_reload = regSupplier.register("deagle_reload", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "deagle_reload")))
                .afterCreated((e, utils) -> {
            utils.sounds().addNewSound(new ZPSoundListProvider.ZPSoundEvent("deagle_reload", false, "sounds.zpm3.deagle_reload", List.of(new ZPSoundListProvider.SoundData("guns/deagle_reload"))));
        }).end();


        ZPSounds.m1911_fire = regSupplier.register("m1911_fire", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "m1911_fire")))
                .afterCreated((e, utils) -> {
            utils.sounds().addNewSound(new ZPSoundListProvider.ZPSoundEvent("m1911_fire", false, "sounds.zpm3.m1911_fire", List.of(new ZPSoundListProvider.SoundData("guns/m1911_fire"))));
        }).end();

        ZPSounds.m1911_reload = regSupplier.register("m1911_reload", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "m1911_reload")))
                .afterCreated((e, utils) -> {
            utils.sounds().addNewSound(new ZPSoundListProvider.ZPSoundEvent("m1911_reload", false, "sounds.zpm3.m1911_reload", List.of(new ZPSoundListProvider.SoundData("guns/m1911_reload"))));
        }).end();


        ZPSounds.usp_fire = regSupplier.register("usp_fire", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "usp_fire")))
                .afterCreated((e, utils) -> {
            utils.sounds().addNewSound(new ZPSoundListProvider.ZPSoundEvent("usp_fire", false, "sounds.zpm3.usp_fire", List.of(new ZPSoundListProvider.SoundData("guns/usp_fire"))));
        }).end();

        ZPSounds.usp_reload = regSupplier.register("usp_reload", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "usp_reload")))
                .afterCreated((e, utils) -> {
            utils.sounds().addNewSound(new ZPSoundListProvider.ZPSoundEvent("usp_reload", false, "sounds.zpm3.usp_reload", List.of(new ZPSoundListProvider.SoundData("guns/usp_reload"))));
        }).end();


        ZPSounds.uzi_fire = regSupplier.register("uzi_fire", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "uzi_fire")))
                .afterCreated((e, utils) -> {
            utils.sounds().addNewSound(new ZPSoundListProvider.ZPSoundEvent("uzi_fire", false, "sounds.zpm3.uzi_fire", List.of(new ZPSoundListProvider.SoundData("guns/uzi_fire"))));
        }).end();

        ZPSounds.uzi_reload = regSupplier.register("uzi_reload", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "uzi_reload")))
                .afterCreated((e, utils) -> {
            utils.sounds().addNewSound(new ZPSoundListProvider.ZPSoundEvent("uzi_reload", false, "sounds.zpm3.uzi_reload", List.of(new ZPSoundListProvider.SoundData("guns/uzi_reload"))));
        }).end();


        ZPSounds.colt_fire = regSupplier.register("colt_fire", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "colt_fire")))
                .afterCreated((e, utils) -> {
            utils.sounds().addNewSound(new ZPSoundListProvider.ZPSoundEvent("colt_fire", false, "sounds.zpm3.colt_fire", List.of(new ZPSoundListProvider.SoundData("guns/colt_fire"))));
        }).end();

        ZPSounds.colt_reload = regSupplier.register("colt_reload", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "colt_reload")))
                .afterCreated((e, utils) -> {
            utils.sounds().addNewSound(new ZPSoundListProvider.ZPSoundEvent("colt_reload", false, "sounds.zpm3.colt_reload", List.of(new ZPSoundListProvider.SoundData("guns/colt_reload"))));
        }).end();


        ZPSounds.empty = regSupplier.register("empty", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "empty")))
                .afterCreated((e, utils) -> {
            utils.sounds().addNewSound(new ZPSoundListProvider.ZPSoundEvent("empty", false, "sounds.zpm3.empty", List.of(new ZPSoundListProvider.SoundData("guns/empty"))));
        }).end();


        ZPSounds.shotgun_reload = regSupplier.register("shotgun_reload", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "shotgun_reload")))
                .afterCreated((e, utils) -> {
            utils.sounds().addNewSound(new ZPSoundListProvider.ZPSoundEvent("shotgun_reload", false, "sounds.zpm3.shotgun_reload", List.of(new ZPSoundListProvider.SoundData("guns/shotgun_reload"))));
        }).end();

        ZPSounds.shotgun_fire = regSupplier.register("shotgun_fire", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "shotgun_fire")))
                .afterCreated((e, utils) -> {
            utils.sounds().addNewSound(new ZPSoundListProvider.ZPSoundEvent("shotgun_fire", false, "sounds.zpm3.shotgun_fire", List.of(new ZPSoundListProvider.SoundData("guns/shotgun_fire"))));
        }).end();

        ZPSounds.shell_insert = regSupplier.register("shell_insert", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "shell_insert")))
                .afterCreated((e, utils) -> {
            utils.sounds().addNewSound(new ZPSoundListProvider.ZPSoundEvent("shell_insert", false, "sounds.zpm3.shell_insert", List.of(new ZPSoundListProvider.SoundData("guns/shell_insert"))));
        }).end();

        ZPSounds.shotgun_shutter = regSupplier.register("shotgun_shutter", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "shotgun_shutter")))
                .afterCreated((e, utils) -> {
            utils.sounds().addNewSound(new ZPSoundListProvider.ZPSoundEvent("shotgun_shutter", false, "sounds.zpm3.shotgun_shutter", List.of(new ZPSoundListProvider.SoundData("guns/shotgun_shutter"))));
        }).end();

        ZPSounds.shell_insert2 = regSupplier.register("shell_insert2", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "shell_insert2")))
                .afterCreated((e, utils) -> {
            utils.sounds().addNewSound(new ZPSoundListProvider.ZPSoundEvent("shell_insert2", false, "sounds.zpm3.shell_insert2", List.of(new ZPSoundListProvider.SoundData("guns/shell_insert2"))));
        }).end();

        ZPSounds.rifle_shutter = regSupplier.register("rifle_shutter", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "rifle_shutter")))
                .afterCreated((e, utils) -> {
            utils.sounds().addNewSound(new ZPSoundListProvider.ZPSoundEvent("rifle_shutter", false, "sounds.zpm3.rifle_shutter", List.of(new ZPSoundListProvider.SoundData("guns/rifle_shutter"))));
        }).end();

        ZPSounds.akm_fire = regSupplier.register("akm_fire", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "akm_fire")))
                .afterCreated((e, utils) -> {
            utils.sounds().addNewSound(new ZPSoundListProvider.ZPSoundEvent("akm_fire", false, "sounds.zpm3.akm_fire", List.of(new ZPSoundListProvider.SoundData("guns/akm_fire"))));
        }).end();

        ZPSounds.akm_reload = regSupplier.register("akm_reload", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "akm_reload")))
                .afterCreated((e, utils) -> {
            utils.sounds().addNewSound(new ZPSoundListProvider.ZPSoundEvent("akm_reload", false, "sounds.zpm3.akm_reload", List.of(new ZPSoundListProvider.SoundData("guns/akm_reload"))));
        }).end();

        ZPSounds.mosin_fire = regSupplier.register("mosin_fire", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "mosin_fire")))
                .afterCreated((e, utils) -> {
            utils.sounds().addNewSound(new ZPSoundListProvider.ZPSoundEvent("mosin_fire", false, "sounds.zpm3.mosin_fire", List.of(new ZPSoundListProvider.SoundData("guns/mosin_fire"))));
        }).end();

        ZPSounds.impactmeat = regSupplier.register("impactmeat", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "impactmeat")))
                .afterCreated((e, utils) -> {
            utils.sounds().addNewSound(new ZPSoundListProvider.ZPSoundEvent("impactmeat", false, "sounds.zpm3.impactmeat", List.of(new ZPSoundListProvider.SoundData("effects/impactmeat"))));
        }).end();
    }
}
