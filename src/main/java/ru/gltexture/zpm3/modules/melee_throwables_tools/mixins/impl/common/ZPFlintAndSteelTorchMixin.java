/*
 *
 *  * zpm3forge
 *  * Copyright (C) 2026 gltexture
 *  *
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program. If not, see <https://www.gnu.org/licenses/>.
 *
 */

package ru.gltexture.zpm3.modules.melee_throwables_tools.mixins.impl.common;

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
import ru.gltexture.zpm3.modules.blocks.init.ZPCampfireBlocks;
import ru.gltexture.zpm3.modules.blocks.init.ZPLanternBlocks;
import ru.gltexture.zpm3.modules.blocks.init.ZPTorchBlocks;
import ru.gltexture.zpm3.modules.blocks.instances.block_entities.IFadingBlockEntity;

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