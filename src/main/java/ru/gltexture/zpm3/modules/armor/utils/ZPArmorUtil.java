package ru.gltexture.zpm3.modules.armor.utils;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.instances.armor.ZPArmorItem;
import ru.gltexture.zpm3.engine.instances.armor.ZPArmorMaterial;
import ru.gltexture.zpm3.modules.armor.init.ZPArmorItems;

import java.util.function.Predicate;

public class ZPArmorUtil {
    public static boolean isEntityHasSpecialMaskForBreathEffect(@NotNull LivingEntity entity) {
        ItemStack helmet = entity.getItemBySlot(EquipmentSlot.HEAD);
        if (!helmet.isEmpty()) {
            return helmet.getItem().equals(ZPArmorItems.acid_costume_helmet.get()) || helmet.getItem().equals(ZPArmorItems.radiation_costume_helmet.get());
        }
        return false;
    }

    public static boolean isEntityHasNightVisionGoggles(@NotNull LivingEntity entity) {
        ItemStack helmet = entity.getItemBySlot(EquipmentSlot.HEAD);
        if (!helmet.isEmpty()) {
            return helmet.getItem().equals(ZPArmorItems.night_vision_goggles.get());
        }
        return false;
    }

    //-1 = 0 factor
    public static int getRadiationIncTickSlowdown(@NotNull LivingEntity entity, int radiationAffLevel) {
        int pieces = 0;
        if (entity.getItemBySlot(EquipmentSlot.HEAD).is(ZPArmorItems.radiation_costume_helmet.get())) {
            pieces++;
        }
        if (entity.getItemBySlot(EquipmentSlot.CHEST).is(ZPArmorItems.radiation_costume_chestplate.get())) {
            pieces++;
        }
        if (entity.getItemBySlot(EquipmentSlot.LEGS).is(ZPArmorItems.radiation_costume_leggings.get())) {
            pieces++;
        }
        if (entity.getItemBySlot(EquipmentSlot.FEET).is(ZPArmorItems.radiation_costume_boots.get())) {
            pieces++;
        }
        return pieces == 4 ? -1 : pieces * 8;
    }

    //-1 = 0 factor
    public static int getAcidIncTickSlowdown(@NotNull LivingEntity entity, int acidAffLevel) {
        int pieces = 0;
        if (entity.getItemBySlot(EquipmentSlot.HEAD).is(ZPArmorItems.acid_costume_helmet.get())) {
            pieces++;
        }
        if (entity.getItemBySlot(EquipmentSlot.CHEST).is(ZPArmorItems.acid_costume_chestplate.get())) {
            pieces++;
        }
        if (entity.getItemBySlot(EquipmentSlot.LEGS).is(ZPArmorItems.acid_costume_leggings.get())) {
            pieces++;
        }
        if (entity.getItemBySlot(EquipmentSlot.FEET).is(ZPArmorItems.acid_costume_boots.get())) {
            pieces++;
        }
        return pieces == 4 ? -1 : 0;
    }

    //-1 = 0 factor
    public static int getToxicIncTickSlowdown(@NotNull LivingEntity entity, int toxicAddLevel) {
        int pieces = 0;
        if (entity.getItemBySlot(EquipmentSlot.HEAD).is(ZPArmorItems.acid_costume_helmet.get())) {
            pieces++;
        }
        if (entity.getItemBySlot(EquipmentSlot.CHEST).is(ZPArmorItems.acid_costume_chestplate.get())) {
            pieces++;
        }
        if (entity.getItemBySlot(EquipmentSlot.LEGS).is(ZPArmorItems.acid_costume_leggings.get())) {
            pieces++;
        }
        if (entity.getItemBySlot(EquipmentSlot.FEET).is(ZPArmorItems.acid_costume_boots.get())) {
            pieces++;
        }
        return pieces == 4 ? -1 : pieces * 2;
    }

    public static double getReductionForArmorPeaceOnEntity(@NotNull LivingEntity entity) {
        double reduction = 0.0;
        reduction += ZPArmorUtil.getReduction(entity.getItemBySlot(EquipmentSlot.HEAD), entity, 0);
        reduction += ZPArmorUtil.getReduction(entity.getItemBySlot(EquipmentSlot.CHEST), entity, 1);
        reduction += ZPArmorUtil.getReduction(entity.getItemBySlot(EquipmentSlot.LEGS), entity, 2);
        reduction += ZPArmorUtil.getReduction(entity.getItemBySlot(EquipmentSlot.FEET), entity, 3);
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
