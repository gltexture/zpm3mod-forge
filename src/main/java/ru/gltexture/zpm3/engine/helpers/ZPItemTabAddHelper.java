package ru.gltexture.zpm3.engine.helpers;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public abstract class ZPItemTabAddHelper {
    private static final Map<RegistryObject<CreativeModeTab>, Set<RegistryObject<? extends Item>>> itemMap = new HashMap<>();

    public static void matchTabItem(@NotNull RegistryObject<? extends Item> item, @NotNull RegistryObject<CreativeModeTab> creativeModeTab) {
        itemMap.computeIfAbsent(creativeModeTab, k -> new HashSet<>()).add(item);
    }

    public static void onBuildContents(BuildCreativeModeTabContentsEvent event) {
        for (Map.Entry<RegistryObject<CreativeModeTab>, Set<RegistryObject<? extends Item>>> entry : itemMap.entrySet()) {
            if (entry.getKey().isPresent() && entry.getKey().get() == event.getTab()) {
                for (RegistryObject<? extends Item> item : entry.getValue()) {
                    event.accept(item.get());
                }
            }
        }
    }

    public static Map<RegistryObject<CreativeModeTab>, Set<RegistryObject<? extends Item>>> getItemMap() {
        return ZPItemTabAddHelper.itemMap;
    }
}