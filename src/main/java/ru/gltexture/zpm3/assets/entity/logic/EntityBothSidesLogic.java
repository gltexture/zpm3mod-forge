package ru.gltexture.zpm3.assets.entity.logic;

import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.nbt.ZPTagID;
import ru.gltexture.zpm3.engine.nbt.entity.ZPEntityNBT;

import java.util.Collection;

public class EntityBothSidesLogic implements EntityTickEventLogic {
    @Override
    public void onTickEntity(@NotNull Entity entity) {
        this.decrementTags(entity, ZPTagID.ENTITY_TAGS_TO_DECREMENT_EACH_TICK);
    }

    protected void decrementTags(Entity entity, Collection<ZPTagID> tags) {
        ZPEntityNBT playerNBT = new ZPEntityNBT(entity);
        tags.forEach(e -> {
            if (playerNBT.has(e) && playerNBT.getTagInt(e) > 0) {
                playerNBT.decrementInt(null, e);
            }
        });
    }
}
