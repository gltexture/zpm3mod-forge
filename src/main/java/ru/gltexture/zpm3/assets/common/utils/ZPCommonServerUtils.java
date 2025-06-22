package ru.gltexture.zpm3.assets.common.utils;

import net.minecraft.world.entity.EquipmentSlot;
import org.jetbrains.annotations.NotNull;

public abstract class ZPCommonServerUtils {
    public static @NotNull EquipmentSlot getEquipmentSlot(int i) {
        EquipmentSlot slot = null;
        switch (i) {
            case 0 -> {
                slot= EquipmentSlot.HEAD;
            }
            case 2 -> {
                slot= EquipmentSlot.LEGS;
            }
            case 3 -> {
                slot= EquipmentSlot.FEET;
            }
            default -> {
                slot = EquipmentSlot.CHEST;
            }
        }
        return slot;
    }
}
