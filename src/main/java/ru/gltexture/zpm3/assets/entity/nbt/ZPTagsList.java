package ru.gltexture.zpm3.assets.entity.nbt;

import ru.gltexture.zpm3.engine.nbt.ZPEntityTag;

public abstract class ZPTagsList {
    public static ZPEntityTag ACID_AFFECT_COOLDOWN = new ZPEntityTag("ACID_AFFECT_COOLDOWN");

    static {
        ZPEntityTag.TAGS_TO_DECREMENT_EACH_TICK.add(ZPTagsList.ACID_AFFECT_COOLDOWN);
    }
}
