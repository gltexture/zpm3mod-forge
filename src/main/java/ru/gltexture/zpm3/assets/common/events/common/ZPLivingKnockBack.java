package ru.gltexture.zpm3.assets.common.events.common;

import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.events.ZPSimpleEventClass;

public class ZPLivingKnockBack implements ZPSimpleEventClass<LivingKnockBackEvent> {
    @Override
    public void exec(@NotNull LivingKnockBackEvent event) {
        event.setStrength(0.25f);
        if (event.getEntity().getLastDamageSource() != null && "damage.zpm3.zp_bullet".equals(event.getEntity().getLastDamageSource().getMsgId())) {
            event.setStrength(0.1f);
        }
    }

    @Override
    public @NotNull Class<LivingKnockBackEvent> getEventType() {
        return LivingKnockBackEvent.class;
    }

    @Override
    public @NotNull ZPSide getSide() {
        return ZPSide.COMMON;
    }

    @Override
    public Mod.EventBusSubscriber.@NotNull Bus getBus() {
        return Mod.EventBusSubscriber.Bus.FORGE;
    }
}
