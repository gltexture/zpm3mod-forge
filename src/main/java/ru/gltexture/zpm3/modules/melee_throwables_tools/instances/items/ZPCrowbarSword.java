package ru.gltexture.zpm3.modules.melee_throwables_tools.instances.items;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.instances.items.ZPItemSword;
import ru.gltexture.zpm3.modules.common.init.ZPTags;

public class ZPCrowbarSword extends ZPItemSword {
    public ZPCrowbarSword(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    public boolean isCorrectToolForDrops(BlockState pBlock) {
        return pBlock.is(ZPTags.B_MINEABLE_WITH_CROWBAR);
    }

    public float getDestroySpeed(@NotNull ItemStack pStack, @NotNull BlockState pState) {
        return this.isCorrectToolForDrops(pState)? this.getTier().getSpeed() : 1.0F;
    }

    @Override
    public boolean mineBlock(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull BlockState pState, @NotNull BlockPos pPos, @NotNull LivingEntity pEntityLiving) {
        if (this.isCorrectToolForDrops(pStack, pState) && pState.getDestroySpeed(pLevel, pPos) != 0.0F) {
            pStack.hurtAndBreak(20, pEntityLiving, (p_43276_) -> {
                p_43276_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
        }
        return super.mineBlock(pStack, pLevel, pState, pPos, pEntityLiving);
    }

    @Override
    public boolean canPerformAction(@NotNull ItemStack stack, @NotNull ToolAction action) {
        return ToolActions.DEFAULT_PICKAXE_ACTIONS.contains(action) || super.canPerformAction(stack, action);
    }
}
