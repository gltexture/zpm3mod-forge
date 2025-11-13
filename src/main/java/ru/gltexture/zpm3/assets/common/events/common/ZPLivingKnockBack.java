package ru.gltexture.zpm3.assets.common.events.common;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.events.ZPEventClass;

public class ZPLivingKnockBack implements ZPEventClass {
    @SubscribeEvent
    public static void exec(@NotNull LivingKnockBackEvent event) {
        event.setStrength(0.25f);
        if (event.getEntity().getLastDamageSource() != null && "damage.zpm3.zp_bullet".equals(event.getEntity().getLastDamageSource().getMsgId())) {
            event.setStrength(0.1f);
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
