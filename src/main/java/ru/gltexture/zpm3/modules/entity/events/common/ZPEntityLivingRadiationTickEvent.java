package ru.gltexture.zpm3.modules.entity.events.common;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.core.config.builtin.ZPEntityConfig;
import ru.gltexture.zpm3.engine.events.ZPEventClass;
import ru.gltexture.zpm3.engine.nbt.ZPTagID;
import ru.gltexture.zpm3.engine.nbt.entity.ZPEntityNBT;
import ru.gltexture.zpm3.modules.armor.utils.ZPArmorUtil;
import ru.gltexture.zpm3.modules.entity.mixins.ext.IZPLivingEntityExt;
import ru.gltexture.zpm3.modules.entity.util.ZPEntityUtil;

public class ZPEntityLivingRadiationTickEvent implements ZPEventClass {
    public ZPEntityLivingRadiationTickEvent() {
    }

    @SubscribeEvent
    public static void exec(LivingEvent.@NotNull LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        Level level = entity.level();

        if (!level.isClientSide()) {
            if (entity instanceof IZPLivingEntityExt izpLivingEntityExt) {
                if (entity.tickCount % 20 == 0) {
                    ZPEntityUtil.applyRadiationEffects(entity, izpLivingEntityExt.zpm3forge$getRadiationLevel());
                }
                final int radTickRate = ZPEntityUtil.getEntityRadAffectionTickRate(entity);
                if (radTickRate > 0) {
                    if (izpLivingEntityExt.zpm3forge$getRadiationLevel() < 100) {
                        if (entity.tickCount % radTickRate == 0) {
                            izpLivingEntityExt.zpm3forge$addRadiationLevel(1);
                            //System.out.println(izpLivingEntityExt.zpm3forge$getRadiationLevel());
                        }
                    }
                } else {
                    if (izpLivingEntityExt.zpm3forge$getRadiationLevel() > 0) {
                        if (entity.tickCount % 10 == 0) {
                            izpLivingEntityExt.zpm3forge$decreaseRadiationLevel(1);
                            //System.out.println(izpLivingEntityExt.zpm3forge$getRadiationLevel());
                        }
                    }
                }
            }
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
