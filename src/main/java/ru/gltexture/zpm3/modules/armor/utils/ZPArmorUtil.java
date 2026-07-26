package ru.gltexture.zpm3.modules.armor.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.instances.armor.ZPArmorItem;
import ru.gltexture.zpm3.engine.instances.armor.ZPArmorMaterial;
import ru.gltexture.zpm3.engine.service.ZPUtility;
import ru.gltexture.zpm3.modules.armor.init.ZPArmorItems;
import ru.gltexture.zpm3.modules.blocks.init.ZPBlocks;
import ru.gltexture.zpm3.modules.common.init.ZPTags;
import ru.gltexture.zpm3.modules.entity.mixins.ext.IZPEntityExt;
import ru.gltexture.zpm3.modules.entity.util.ZPEntityUtil;

import java.util.function.Predicate;

public class ZPArmorUtil {
    public static boolean isEntityHasSpecialMaskForBreathEffect(@NotNull LivingEntity entity) {
        ItemStack helmet = entity.getItemBySlot(EquipmentSlot.HEAD);
        if (!helmet.isEmpty()) {
            return helmet.is(ZPTags.I_ARMOR_VIGNETTE);
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

    public static boolean isArmorShouldHideName(LivingEntity entity) {
        if (entity.getItemBySlot(EquipmentSlot.HEAD).is(ZPTags.I_ARMOR_CAMO_FOREST)) {
            return true;
        }
        if (entity.getItemBySlot(EquipmentSlot.HEAD).is(ZPTags.I_ARMOR_CAMO_SAND)) {
            return true;
        }
        if (entity.getItemBySlot(EquipmentSlot.HEAD).is(ZPTags.I_ARMOR_CAMO_WINTER)) {
            return true;
        }
        return false;
    }

    public static boolean isFullAqualungBreathingRightNow(LivingEntity entity) {
        final FluidState fluid = entity.level().getFluidState(BlockPos.containing(entity.getEyePosition()));
        if (!fluid.is(FluidTags.WATER)) {
            return false;
        }
        if (!ZPEntityUtil.hasOxygenInHands(entity)) {
            return false;
        }
        if (ZPArmorUtil.canEntityBreathUnderWaterViaAqualung(entity)) {
            if (ZPArmorUtil.getAcidIncTickSlowdown(entity, 0) < 0) {
                if (fluid.is(ZPTags.F_TOXIC_PROPERTIES) || fluid.is(ZPTags.F_ACID_PROPERTIES)) {
                    return fluid.is(ZPTags.F_ACID_COSTUME_BREATHABLE);
                } else {
                    return false;
                }
            }
            return fluid.is(ZPTags.F_AQUALUNG_COSTUME_BREATHABLE);
        }
        return false;
    }

    public static boolean canEntityBreathUnderWaterViaAqualung(final LivingEntity entity) {
        int pieces = 0;
        if (entity.getItemBySlot(EquipmentSlot.HEAD).is(ZPTags.I_ARMOR_AQUALUNG)) {
            pieces++;
        }
        if (entity.getItemBySlot(EquipmentSlot.CHEST).is(ZPTags.I_ARMOR_AQUALUNG)) {
            pieces++;
        }
        if (entity.getItemBySlot(EquipmentSlot.LEGS).is(ZPTags.I_ARMOR_AQUALUNG)) {
            pieces++;
        }
        if (entity.getItemBySlot(EquipmentSlot.FEET).is(ZPTags.I_ARMOR_AQUALUNG)) {
            pieces++;
        }
        return pieces == 4;
    }

    //-1 = 0 factor
    public static int getRadiationIncTickSlowdown(@NotNull LivingEntity entity, int radiationAffLevel) {
        int pieces = 0;
        if (entity.getItemBySlot(EquipmentSlot.HEAD).is(ZPTags.I_ARMOR_RADIOPROTECTION)) {
            pieces++;
        }
        if (entity.getItemBySlot(EquipmentSlot.CHEST).is(ZPTags.I_ARMOR_RADIOPROTECTION)) {
            pieces++;
        }
        if (entity.getItemBySlot(EquipmentSlot.LEGS).is(ZPTags.I_ARMOR_RADIOPROTECTION)) {
            pieces++;
        }
        if (entity.getItemBySlot(EquipmentSlot.FEET).is(ZPTags.I_ARMOR_RADIOPROTECTION)) {
            pieces++;
        }
        return pieces == 4 ? -1 : pieces * 8;
    }

    //-1 = 0 factor
    public static int getAcidIncTickSlowdown(@NotNull LivingEntity entity, int acidAffLevel) {
        int pieces = 0;
        if (entity.getItemBySlot(EquipmentSlot.HEAD).is(ZPTags.I_ARMOR_ACID_PROTECTION)) {
            pieces++;
        }
        if (entity.getItemBySlot(EquipmentSlot.CHEST).is(ZPTags.I_ARMOR_ACID_PROTECTION)) {
            pieces++;
        }
        if (entity.getItemBySlot(EquipmentSlot.LEGS).is(ZPTags.I_ARMOR_ACID_PROTECTION)) {
            pieces++;
        }
        if (entity.getItemBySlot(EquipmentSlot.FEET).is(ZPTags.I_ARMOR_ACID_PROTECTION)) {
            pieces++;
        }
        return pieces == 4 ? -1 : 0;
    }

    //-1 = 0 factor
    public static int getToxicIncTickSlowdown(@NotNull LivingEntity entity, int toxicAddLevel) {
        int pieces = 0;
        if (entity.getItemBySlot(EquipmentSlot.HEAD).is(ZPTags.I_ARMOR_ACID_PROTECTION)) {
            pieces++;
        }
        if (entity.getItemBySlot(EquipmentSlot.CHEST).is(ZPTags.I_ARMOR_ACID_PROTECTION)) {
            pieces++;
        }
        if (entity.getItemBySlot(EquipmentSlot.LEGS).is(ZPTags.I_ARMOR_ACID_PROTECTION)) {
            pieces++;
        }
        if (entity.getItemBySlot(EquipmentSlot.FEET).is(ZPTags.I_ARMOR_ACID_PROTECTION)) {
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
