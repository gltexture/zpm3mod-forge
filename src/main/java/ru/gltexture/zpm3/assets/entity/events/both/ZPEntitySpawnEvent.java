package ru.gltexture.zpm3.assets.entity.events.both;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.events.ZPEvent;
import ru.gltexture.zpm3.engine.nbt.ZPEntityNBT;

public class ZPEntitySpawnEvent implements ZPEvent<EntityJoinLevelEvent> {
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
    public @NotNull Side getSide() {
        return Side.BOTH;
    }
}
