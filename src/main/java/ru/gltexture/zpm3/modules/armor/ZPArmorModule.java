package ru.gltexture.zpm3.modules.armor;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.module.ZPModule;
import ru.gltexture.zpm3.engine.core.module.ZPModuleData;
import ru.gltexture.zpm3.engine.service.ZPUtility;
import ru.gltexture.zpm3.modules.armor.events.client.ZPAdjustNightVisionGogglesLightMap;
import ru.gltexture.zpm3.modules.armor.events.client.ZPPlayerArmorSoundOnClientEvent;
import ru.gltexture.zpm3.modules.armor.init.ZPArmorItems;
import ru.gltexture.zpm3.modules.armor.utils.ZPArmorUtil;
import ru.gltexture.zpm3.modules.common.init.ZPSounds;
import ru.gltexture.zpm3.modules.debug.events.ZPRenderStuffEvent;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class ZPArmorModule extends ZPModule {
    public ZPArmorModule(@NotNull ZPModuleData zpModuleData) {
        super(zpModuleData);
    }

    public ZPArmorModule() {
    }

    @Override
    public void fml_commonSetupEvent() {
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void fml_clientSetupEvent() {
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void clientShutDown() {
    }

    public static void addNewLineToDraw(@NotNull ZPRenderStuffEvent.LineRequest lineRequest) {
        ZPRenderStuffEvent.addNewLineToDraw(lineRequest);
    }

    @Override
    public void initialize(ZombiePlague3.@NotNull IModuleEntry moduleEntry) {
        moduleEntry.addZP3EventClass(ZPAdjustNightVisionGogglesLightMap.class);
        moduleEntry.addMinecraftEventClass(ZPPlayerArmorSoundOnClientEvent.class);
        moduleEntry.addMinecraftRegistryClass(ZPArmorItems.class);
        ZPUtility.sides().onlyClient(() -> {
            ZPPlayerArmorSoundOnClientEvent.registerArmorSound(new ZPPlayerArmorSoundOnClientEvent.TrackedSoundLauncher() {
                @Override
                public @NotNull Supplier<SoundEvent> getSoundEvent() {
                    return () -> ZPSounds.nv_goggles.get();
                }

                @Override
                public float pitch() {
                    return 1.f;
                }

                @Override
                public float volume() {
                    return 1.f;
                }

                @Override
                public @NotNull Predicate<LivingEntity> getEntityPredicate() {
                    return ZPArmorUtil::isEntityHasNightVisionGoggles;
                }
            });


            /*
            ZPPlayerArmorSoundOnClientEvent.registerArmorSound(new ZPPlayerArmorSoundOnClientEvent.TrackedSoundLauncher() {
                @Override
                public @NotNull Supplier<SoundEvent> getSoundEvent() {
                    return () -> ZPSounds.breath.get();
                }

                @Override
                public float pitch() {
                    return 0.5f;
                }

                @Override
                public float volume() {
                    return 0.8f;
                }

                @Override
                public @NotNull Predicate<LivingEntity> getEntityPredicate() {
                    return e -> (e instanceof Player) && (ZPArmorUtil.isEntityHasSpecialMaskForBreathEffect(e));
                }
            });
            */
        });
    }

    @Override
    public void preInitialize() {

    }

    @Override
    public void postInitialize() {

    }
}
