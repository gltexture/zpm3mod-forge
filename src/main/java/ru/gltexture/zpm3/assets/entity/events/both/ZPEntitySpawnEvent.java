package ru.gltexture.zpm3.assets.entity.events.both;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.events.ZPSimpleEventClass;
import ru.gltexture.zpm3.engine.nbt.entity.ZPEntityNBT;

public class ZPEntitySpawnEvent implements ZPSimpleEventClass<EntityJoinLevelEvent> {
    @Override
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
    public @NotNull Class<EntityJoinLevelEvent> getEventType() {
        return EntityJoinLevelEvent.class;
    }

    @Override
    public @NotNull ZPSide getSide() {
        return ZPSide.BOTH;
    }

    @Override
    public Mod.EventBusSubscriber.@NotNull Bus getBus() {
        return Mod.EventBusSubscriber.Bus.MOD;
    }
}
