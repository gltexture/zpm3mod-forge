package ru.gltexture.zpm3.modules.entity.events.common;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.events.ZPEventClass;
import ru.gltexture.zpm3.modules.entity.mixins.ext.IZPLivingEntityExt;
import ru.gltexture.zpm3.modules.mob_effects.init.ZPMobEffects;

public class ZPLivingApplyEffectEvent implements ZPEventClass {
    @SubscribeEvent
    public static void onEffectApplicable(MobEffectEvent.Applicable event) {
        LivingEntity entity = event.getEntity();

        if (entity instanceof IZPLivingEntityExt livingEntityExt) {
            if (event.getEffectInstance().getEffect() == ZPMobEffects.immune.get() && livingEntityExt.zpm3forge$getRadiationLevel() >= 10) {
                event.setResult(Event.Result.DENY);
            }
            final boolean badEffect = event.getEffectInstance().getEffect() == MobEffects.POISON || event.getEffectInstance().getEffect() == MobEffects.HUNGER || event.getEffectInstance().getEffect() == MobEffects.WEAKNESS;
            if (badEffect && entity.hasEffect(ZPMobEffects.immune.get())) {
                event.setResult(Event.Result.DENY);
            }
        }
    }

    @Override
    public @NotNull ZPSide getSide() {
        return ZPSide.COMMON;
    }

    @Override
    public @NotNull Mod.EventBusSubscriber.Bus getBus() {
        return Mod.EventBusSubscriber.Bus.FORGE;
    }
}