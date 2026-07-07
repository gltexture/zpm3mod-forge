package ru.gltexture.zpm3.modules.blocks.mixins.impl.common;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.gltexture.zpm3.modules.food_medicine.init.ZPFoodMedicineItems;

@Mixin(CakeBlock.class)
public class ZPCakePieceMixin {
    @Inject(method = "eat", at = @At("HEAD"), cancellable = true)
    private static void eat(LevelAccessor pLevel, BlockPos pPos, BlockState pState, Player pPlayer, CallbackInfoReturnable<InteractionResult> cir) {
        if (!pPlayer.canEat(false)) {
            cir.setReturnValue(InteractionResult.PASS);
        } else {
            final ItemStack stack = new ItemStack(ZPFoodMedicineItems.minecake.get());
            if (!pLevel.isClientSide() && !pPlayer.addItem(stack)) {
                pPlayer.drop(stack, false);
            }
            int i = pState.getValue(CakeBlock.BITES);
            pLevel.gameEvent(pPlayer, GameEvent.EAT, pPos);
            if (i < 6) {
                pLevel.setBlock(pPos, pState.setValue(CakeBlock.BITES, i + 1), 3);
            } else {
                pLevel.removeBlock(pPos, false);
                pLevel.gameEvent(pPlayer, GameEvent.BLOCK_DESTROY, pPos);
            }
            cir.setReturnValue(InteractionResult.SUCCESS);
        }
    }
}
