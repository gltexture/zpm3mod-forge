package ru.gltexture.zpm3.assets.common.instances.items;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.*;
import ru.gltexture.zpm3.assets.common.init.ZPTags;

public class ZPWrenchTool extends DiggerItem {
    public ZPWrenchTool(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Item.Properties pProperties) {
        super((float)pAttackDamageModifier, pAttackSpeedModifier, pTier, ZPTags.B_MINEABLE_WITH_WRENCH, pProperties);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, net.minecraftforge.common.ToolAction toolAction) {
        return net.minecraftforge.common.ToolActions.DEFAULT_PICKAXE_ACTIONS.contains(toolAction);
    }
}