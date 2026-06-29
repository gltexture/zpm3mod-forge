package ru.gltexture.zpm3.modules.armor.instances.armor;

import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.instances.armor.ZPArmorMaterial;

import java.util.EnumMap;

public abstract class ZPArmorMaterialsList {
    public static final ZPArmorMaterial NIGHT_VIS = new ZPArmorMaterial(ZombiePlague3.MOD_ID + ":night_vision_goggles", 15, Util.make(new EnumMap<>(ArmorItem.Type.class), (p_266656_) -> {
        p_266656_.put(ArmorItem.Type.BOOTS, 2);
        p_266656_.put(ArmorItem.Type.LEGGINGS, 5);
        p_266656_.put(ArmorItem.Type.CHESTPLATE, 6);
        p_266656_.put(ArmorItem.Type.HELMET, 2);
    }), 9, SoundEvents.ARMOR_EQUIP_TURTLE, 0.0F, 0.0F, () -> null);
}
