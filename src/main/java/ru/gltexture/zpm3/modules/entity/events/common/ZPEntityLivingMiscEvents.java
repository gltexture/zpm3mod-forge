package ru.gltexture.zpm3.modules.entity.events.common;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.modules.armor.utils.ZPArmorUtil;
import ru.gltexture.zpm3.modules.entity.instances.mobs.zombies.ZPAbstractZombie;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.events.ZPEventClass;
import ru.gltexture.zpm3.modules.mob_effects.init.ZPMobEffects;

public class ZPEntityLivingMiscEvents implements ZPEventClass {
    @SubscribeEvent
    public static void exec(@NotNull LivingKnockBackEvent event) {
        event.setStrength(0.25f);
        if (event.getEntity().getLastDamageSource() != null && "damage.zpm3.zp_bullet".equals(event.getEntity().getLastDamageSource().getMsgId())) {
            event.setStrength(0.1f);
        }
    }

    @SubscribeEvent
    public static void onFall(LivingFallEvent event) {
        if (event.getEntity() instanceof ZPAbstractZombie) {
            float damageMultiplier = event.getDamageMultiplier();
            event.setDamageMultiplier(damageMultiplier * 0.5f);
        }
    }

    @SubscribeEvent
    public static void onTick(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        if (ZPArmorUtil.isFullAqualungBreathingRightNow(entity)) {
            event.getEntity().setAirSupply(entity.getMaxAirSupply());
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
