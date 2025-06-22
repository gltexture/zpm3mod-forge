package ru.gltexture.zpm3.engine.nbt;

import java.util.ArrayList;
import java.util.List;

public record ZPEntityTag(String id) {
    public static List<ZPEntityTag> TAGS_TO_DECREMENT_EACH_TICK = new ArrayList<>();
}
