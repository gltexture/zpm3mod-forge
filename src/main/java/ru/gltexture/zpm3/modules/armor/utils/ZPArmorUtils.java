package ru.gltexture.zpm3.modules.armor.utils;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.instances.armor.ZPArmorItem;
import ru.gltexture.zpm3.engine.instances.armor.ZPArmorMaterial;
import ru.gltexture.zpm3.modules.armor.init.ZPArmorItems;

import java.util.function.Predicate;

public class ZPArmorUtils {
    public static boolean isEntityHasNightVisionGoggles(@NotNull LivingEntity entity) {
        ItemStack helmet = entity.getItemBySlot(EquipmentSlot.HEAD);
        if (!helmet.isEmpty()) {
            return helmet.getItem().equals(ZPArmorItems.night_vision_goggles.get());
        }
        return false;
    }

    public static double getReductionForArmorPeaceOnEntity(@NotNull LivingEntity entity) {
        double reduction = 0.0;
        reduction += ZPArmorUtils.getReduction(entity.getItemBySlot(EquipmentSlot.HEAD), entity, 0);
        reduction += ZPArmorUtils.getReduction(entity.getItemBySlot(EquipmentSlot.CHEST), entity, 1);
        reduction += ZPArmorUtils.getReduction(entity.getItemBySlot(EquipmentSlot.LEGS), entity, 2);
        reduction += ZPArmorUtils.getReduction(entity.getItemBySlot(EquipmentSlot.FEET), entity, 3);
        return reduction;
    }

    private static double getReduction(@NotNull ItemStack stack, @NotNull LivingEntity entity, int slotIndex) {
        if (!(stack.getItem() instanceof ZPArmorItem armorItem)) {
            return 0.0;
        }
        if (!(armorItem.getMaterial() instanceof ZPArmorMaterial material)) {
            return 0.0;
        }
        ZPArmorMaterial.ZPArmorProperties properties = material.getZpArmorProperties();
        Predicate<LivingEntity> predicate = properties.getBonusZombieLookRadiusPredicate();
        if (predicate != null && !predicate.test(entity)) {
            return 0.0;
        }
        return properties.getReduceZombieLookRadiusIfOnEntity()[slotIndex];
    }
}
