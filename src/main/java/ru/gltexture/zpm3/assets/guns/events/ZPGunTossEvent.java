package ru.gltexture.zpm3.assets.guns.events;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.guns.item.ZPBaseGun;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.events.ZPSimpleEventClass;

public class ZPGunTossEvent implements ZPSimpleEventClass<ItemTossEvent> {
    @Override
    public void exec(@NotNull ItemTossEvent itemTossEvent) {
        ItemEntity entity = itemTossEvent.getEntity();
        ItemStack stack = entity.getItem();

        if (stack.getItem() instanceof ZPBaseGun baseGun) {
            baseGun.setCurrentTimeBeforeReload(itemTossEvent.getPlayer(), stack, 10);
            baseGun.setReloading(itemTossEvent.getPlayer(), stack, false);
            baseGun.setUnloading(itemTossEvent.getPlayer(), stack, false);
            baseGun.setCurrentReloadCooldown(itemTossEvent.getPlayer(), stack, 0);
        }
    }

    @Override
    public @NotNull Class<ItemTossEvent> getEventType() {
        return ItemTossEvent.class;
    }

    @Override
    public Mod.EventBusSubscriber.@NotNull Bus getBus() {
        return Mod.EventBusSubscriber.Bus.FORGE;
    }

    @Override
    public @NotNull ZPSide getSide() {
        return ZPSide.COMMON;
    }
}
