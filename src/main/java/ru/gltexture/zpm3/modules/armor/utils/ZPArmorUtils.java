package ru.gltexture.zpm3.modules.armor.utils;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.modules.armor.init.ZPArmorItems;

public class ZPArmorUtils {
    public static boolean isEntityHasNightVisionGoggles(@NotNull LivingEntity entity) {
        ItemStack helmet = entity.getItemBySlot(EquipmentSlot.HEAD);
        if (!helmet.isEmpty()) {
            return helmet.getItem().equals(ZPArmorItems.night_vision_goggles.get());
        }
        return false;
    }
}
