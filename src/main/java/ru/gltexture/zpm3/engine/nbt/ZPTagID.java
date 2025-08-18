package ru.gltexture.zpm3.engine.nbt;

import java.util.ArrayList;
import java.util.List;

public record ZPTagID(String id) {
    // ITEMS
    public static ZPTagID GUN_SHOOT_COOLDOWN_TAG = new ZPTagID("GUN_SHOOT_COOLDOWN_TAG");
    public static ZPTagID GUN_RELOAD_COOLDOWN_TAG = new ZPTagID("GUN_RELOAD_COOLDOWN_TAG");
    public static ZPTagID GUN_AMMO_INSIDE_TAG = new ZPTagID("GUN_AMMO_INSIDE_TAG");
    public static ZPTagID GUN_IS_UNLOADING_TAG = new ZPTagID("GUN_IS_UNLOADING_TAG");
    public static ZPTagID GUN_IS_RELOADING_TAG = new ZPTagID("GUN_IS_UNLOADING_TAG");

    // ENTITIES
    public static List<ZPTagID> ENTITY_TAGS_TO_DECREMENT_EACH_TICK = new ArrayList<>();
}
