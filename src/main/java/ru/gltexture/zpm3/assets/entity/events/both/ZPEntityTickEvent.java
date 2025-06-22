package ru.gltexture.zpm3.assets.entity.events.both;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.living.LivingEvent;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.entity.ZPEntityAsset;
import ru.gltexture.zpm3.engine.events.ZPEvent;

public class ZPEntityTickEvent implements ZPEvent<LivingEvent.LivingTickEvent> {
    public ZPEntityTickEvent() {
    }

    @Override
    public void exec(LivingEvent.@NotNull LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        Level level = entity.level();

        if (!level.isClientSide()) {
            ZPEntityAsset.serverSideLogic.onTickEntity(event.getEntity());
        } else {
            ZPEntityAsset.clientSideLogic.onTickEntity(event.getEntity());
        }

        ZPEntityAsset.bothSidesLogic.onTickEntity(event.getEntity());
    }

    @Override
    public @NotNull Class<LivingEvent.LivingTickEvent> getEventType() {
        return LivingEvent.LivingTickEvent.class;
    }

    @Override
    public @NotNull Side getSide() {
        return Side.BOTH;
    }
}
