package ru.gltexture.zpm3.modules.common.mixins.impl.common;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.gltexture.zpm3.engine.service.ZPUtility;
import ru.gltexture.zpm3.modules.common.init.ZPCampfireBlocks;
import ru.gltexture.zpm3.modules.common.init.ZPLanternBlocks;
import ru.gltexture.zpm3.modules.common.init.ZPTorchBlocks;
import ru.gltexture.zpm3.modules.common.instances.block_entities.IFadingBlockEntity;
import ru.gltexture.zpm3.modules.common.instances.block_entities.ZPFadingBlockEntity;
import ru.gltexture.zpm3.modules.common.instances.blocks.fading.IFadingBlock;

import java.util.*;

@Mixin(FlintAndSteelItem.class)
public class ZPFlintAndSteelTorchMixin {
    @Inject(method = "useOn", at = @At("HEAD"), cancellable = true)
    public void useOn(UseOnContext pContext, CallbackInfoReturnable<InteractionResult> cir) {
        final Map<Block, Block> RELIGHT_MAP = Map.of(
                ZPTorchBlocks.torch2.get(), Blocks.TORCH,
                ZPTorchBlocks.torch3.get(), Blocks.TORCH,
                ZPTorchBlocks.torch4.get(), Blocks.TORCH,
                ZPTorchBlocks.torch5.get(), Blocks.TORCH,
                ZPLanternBlocks.lantern2.get(), Blocks.LANTERN,
                ZPLanternBlocks.lantern3.get(), Blocks.LANTERN,
                ZPLanternBlocks.lantern4.get(), Blocks.LANTERN,
                ZPLanternBlocks.lantern5.get(), Blocks.LANTERN,
                ZPCampfireBlocks.campfire2.get(), ZPCampfireBlocks.campfire2.get()
        );

        Player player = pContext.getPlayer();
        Level level = pContext.getLevel();
        BlockPos blockPos = pContext.getClickedPos();
        Block block = level.getBlockState(blockPos).getBlock();
        ItemStack itemStack = pContext.getItemInHand();

        Block targetBlock = RELIGHT_MAP.get(block);
        if (targetBlock == null) {
            return;
        }
        if (block == ZPTorchBlocks.torch2.get() || block == ZPTorchBlocks.torch3.get() || block == ZPTorchBlocks.torch4.get() || block == ZPTorchBlocks.torch5.get()) {
            targetBlock = Blocks.TORCH;
        }
        if (block == ZPLanternBlocks.lantern2.get() || block == ZPLanternBlocks.lantern3.get() || block == ZPLanternBlocks.lantern4.get() || block == ZPLanternBlocks.lantern5.get()) {
            targetBlock = Blocks.LANTERN;
        }
        level.playSound(player, blockPos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
        if (player instanceof ServerPlayer) {
            level.gameEvent(player, GameEvent.BLOCK_CHANGE, blockPos);
            itemStack.hurtAndBreak(1, player, p -> {
                p.broadcastBreakEvent(pContext.getHand());
            });
            if (targetBlock instanceof CampfireBlock campfireBlock) {
                BlockState newState = targetBlock.defaultBlockState();
                newState = ZPUtility.blocks().copyProperties(level.getBlockState(blockPos), newState);
                level.setBlockAndUpdate(blockPos, newState.setValue(CampfireBlock.LIT, Boolean.TRUE));
            } else {
                level.setBlockAndUpdate(blockPos, targetBlock.defaultBlockState());
            }
            if (level.getBlockEntity(blockPos) instanceof IFadingBlockEntity fadingBlock) {
                fadingBlock.setActive(true);
            }
        }
        cir.setReturnValue(InteractionResult.SUCCESS);
    }
}