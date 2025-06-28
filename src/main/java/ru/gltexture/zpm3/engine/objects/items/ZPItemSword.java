package ru.gltexture.zpm3.engine.objects.items;

import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public class ZPItemSword extends SwordItem {
    public ZPItemSword(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }
}
