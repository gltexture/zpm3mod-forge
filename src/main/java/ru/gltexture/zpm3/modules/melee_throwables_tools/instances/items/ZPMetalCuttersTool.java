package ru.gltexture.zpm3.modules.melee_throwables_tools.instances.items;

import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import ru.gltexture.zpm3.modules.common.init.ZPTags;

public class ZPMetalCuttersTool extends DiggerItem {
    public ZPMetalCuttersTool(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super((float)pAttackDamageModifier, pAttackSpeedModifier, pTier, ZPTags.B_MINEABLE_WITH_METAL_CUTTERS, pProperties);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, net.minecraftforge.common.ToolAction toolAction) {
        return net.minecraftforge.common.ToolActions.DEFAULT_PICKAXE_ACTIONS.contains(toolAction);
    }
}