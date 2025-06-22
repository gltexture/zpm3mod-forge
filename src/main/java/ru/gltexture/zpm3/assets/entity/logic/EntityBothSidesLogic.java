package ru.gltexture.zpm3.assets.entity.logic;

import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.nbt.ZPEntityTag;
import ru.gltexture.zpm3.engine.nbt.ZPEntityNBT;

import java.util.Collection;

public class EntityBothSidesLogic implements EntityTickEventLogic {
    @Override
    public void onTickEntity(@NotNull Entity entity) {
        this.decrementTags(entity, ZPEntityTag.TAGS_TO_DECREMENT_EACH_TICK);
    }

    protected void decrementTags(Entity entity, Collection<ZPEntityTag> tags) {
        ZPEntityNBT playerNBT = new ZPEntityNBT(entity);
        tags.forEach(e -> {
            if (playerNBT.has(e) && playerNBT.getTagInt(e) > 0) {
                playerNBT.decrementInt(e);
            }
        });
    }
}
