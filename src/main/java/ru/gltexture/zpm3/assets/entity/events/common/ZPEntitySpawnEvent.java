package ru.gltexture.zpm3.assets.entity.events.common;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.events.ZPEventClass;
import ru.gltexture.zpm3.engine.nbt.entity.ZPEntityNBT;

public class ZPEntitySpawnEvent implements ZPEventClass {
    @SubscribeEvent
    public void exec(@NotNull EntityJoinLevelEvent event) {
        ZPEntitySpawnEvent.registerNBT(event.getEntity());
    }

    public static void registerNBT(Entity entity) {
        CompoundTag persistentData = entity.getPersistentData();
        if (!persistentData.contains(ZPEntityNBT.PERSISTED_NBT_TAG)) {
            CompoundTag persisted = new CompoundTag();
            persistentData.put(ZPEntityNBT.PERSISTED_NBT_TAG, persisted);
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
