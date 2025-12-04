package ru.gltexture.zpm3.assets.entity.events.common;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.entity.ZPEntityAsset;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.events.ZPEventClass;
import ru.gltexture.zpm3.engine.nbt.ZPTagID;
import ru.gltexture.zpm3.engine.nbt.entity.ZPEntityNBT;

public class ZPEntityTickEvent implements ZPEventClass {
    public ZPEntityTickEvent() {
    }

    @SubscribeEvent
    public static void exec(LivingEvent.@NotNull LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        Level level = entity.level();

        if (!level.isClientSide()) {
            ZPEntityNBT playerNBT = new ZPEntityNBT(entity);
            ZPTagID.ENTITY_TAGS_TO_DECREMENT_EACH_TICK.forEach(e -> {
                if (playerNBT.has(e) && playerNBT.getTagInt(e) > 0) {
                    playerNBT.decrementInt(null, e);
                }
            });
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
