package ru.gltexture.zpm3.engine.nbt.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.nbt.ZPAbstractNBTClass;
import ru.gltexture.zpm3.engine.service.Pair;

public final class ZPEntityNBT extends ZPAbstractNBTClass<Entity> {
    public ZPEntityNBT(Entity entity) {
        super(entity);
    }

    public static final String PERSISTED_NBT_TAG = "ZPM3EntityPersisted";

    public CompoundTag getTag() {
        CompoundTag data = this.t.getPersistentData();
        return data.getCompound(ZPEntityNBT.PERSISTED_NBT_TAG);
    }
}
