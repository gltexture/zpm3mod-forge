package ru.gltexture.zpm3.assets.player.misc;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public abstract class ZPDefaultItemsHandReach {
    private static final Map<Class<?>, Float> itemsHandReachBonusC = new HashMap<>();
    private static final Map<String, Float> itemsHandReachBonusI = new HashMap<>();

    //DEFAULT
    static {
        ZPDefaultItemsHandReach.SET(AxeItem.class, 0.15f);
        ZPDefaultItemsHandReach.SET(PickaxeItem.class, 0.1f);
        ZPDefaultItemsHandReach.SET(ShovelItem.class, -0.05f);
    }

    // o == Class || o == <Item>
    public static void SET(@NotNull Object o, Float f) {
        if (o instanceof Class<?> c) {
            ZPDefaultItemsHandReach.itemsHandReachBonusC.put(c, f);
        } else if (o instanceof ResourceLocation i) {
            ZPDefaultItemsHandReach.itemsHandReachBonusI.put(i.getPath(), f);
        }
    }

    public static float get(@Nullable Item o) {
        if (o == null) {
            return 0.0f;
        }
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(o);
        if (id != null && ZPDefaultItemsHandReach.itemsHandReachBonusI.containsKey(id.getPath())) {
            return ZPDefaultItemsHandReach.itemsHandReachBonusI.get(id.getPath());
        }
        return ZPDefaultItemsHandReach.itemsHandReachBonusC.entrySet().stream()
                .filter(e -> e.getKey().isAssignableFrom(o.getClass()))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(0.0f);
    }
}