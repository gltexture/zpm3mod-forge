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
            utils.sounds().addNewSound(new ZPSoundListProvider.ZPSoundEvent("syringe", false, "sounds.zpm3.syringe", List.of(new ZPSoundListProvider.SoundData("syringe"))));
        }).registryObject();

        ZPSounds.pills = regSupplier.register("pills", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "pills"))).postConsume(Dist.CLIENT, (e, utils) -> {
            utils.sounds().addNewSound(new ZPSoundListProvider.ZPSoundEvent("pills", false, "sounds.zpm3.pills", List.of(new ZPSoundListProvider.SoundData("pills"))));
        }).registryObject();

        ZPSounds.bandage = regSupplier.register("bandage", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "bandage"))).postConsume(Dist.CLIENT, (e, utils) -> {
            utils.sounds().addNewSound(new ZPSoundListProvider.ZPSoundEvent("bandage", false, "sounds.zpm3.bandage", List.of(new ZPSoundListProvider.SoundData("bandage"))));
        }).registryObject();
    }
}
