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

package ru.gltexture.zpm3.modules.commands.events.common;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.piston.PistonStructureResolver;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.level.ExplosionEvent;
import net.minecraftforge.event.level.PistonEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.events.ZPForgeEventHandlerClass;
import ru.gltexture.zpm3.modules.commands.zones.ZPZoneChecks;

public class ZPZoneProtectionEvents implements ZPForgeEventHandlerClass {
    public ZPZoneProtectionEvents() {
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onBlockPlace(@NotNull BlockEvent.EntityPlaceEvent event) {
        if (!(event.getEntity() instanceof Player player) || player.isCreative()) {
            return;
        }

        if (event.getLevel() instanceof Level level && ZPZoneChecks.INSTANCE.isBlockPlacementProtected(level, event.getPos())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onBlockBreak(@NotNull BlockEvent.BreakEvent event) {
        if (event.getPlayer().isCreative()) {
            return;
        }

        if (event.getLevel() instanceof Level level && ZPZoneChecks.INSTANCE.isBlockDestructionProtected(level, event.getPos())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onBlockUse(@NotNull PlayerInteractEvent.RightClickBlock event) {
        if (event.getEntity().isCreative()) {
            return;
        }

        if (ZPZoneChecks.INSTANCE.isUsageProtected(event.getLevel(), event.getPos())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onExplosionDetonate(@NotNull ExplosionEvent.Detonate event) {
        event.getAffectedBlocks().removeIf(pos -> ZPZoneChecks.INSTANCE.isBlockDestructionProtected(event.getLevel(), pos));
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onBlockToolModification(@NotNull BlockEvent.BlockToolModificationEvent event) {
        if (event.getPlayer() != null && event.getPlayer().isCreative()) {
            return;
        }
        if (event.getLevel() instanceof Level level && ZPZoneChecks.INSTANCE.isUsageProtected(level, event.getPos())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onBucketUse(@NotNull PlayerInteractEvent.RightClickBlock event) {
        if (event.getEntity().isCreative()) {
            return;
        }
        if (!(event.getItemStack().getItem() instanceof BucketItem)) {
            return;
        }
        if (event.getFace() != null) {
            final Level level = event.getLevel();
            final BlockPos clickedPos = event.getPos();
            final BlockPos targetPos = clickedPos.relative(event.getFace());
            if (ZPZoneChecks.INSTANCE.isBlockPlacementProtected(level, clickedPos) || ZPZoneChecks.INSTANCE.isBlockPlacementProtected(level, targetPos)) {
                event.setCancellationResult(InteractionResult.FAIL);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onFireNeighborNotify(@NotNull BlockEvent.NeighborNotifyEvent event) {
        if (!(event.getLevel() instanceof Level level)) {
            return;
        }
        final BlockPos pos = event.getPos();
        if (level.getBlockState(pos).is(Blocks.FIRE) && ZPZoneChecks.INSTANCE.isBlockDestructionProtected(level, pos)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onPistonPre(@NotNull PistonEvent.Pre event) {
        if (!(event.getLevel() instanceof Level level)) {
            return;
        }
        final PistonStructureResolver resolver = event.getStructureHelper();
        if (resolver == null || !resolver.resolve()) {
            if (event.getPistonMoveType() == PistonEvent.PistonMoveType.RETRACT) {
                if (ZPZoneChecks.INSTANCE.isBlockPistonsProtected(level, event.getPos().relative(event.getDirection(), 2))) {
                    event.setCanceled(true);
                }
            }
            return;
        }
        final Direction moveDirection = event.getPistonMoveType() == PistonEvent.PistonMoveType.EXTEND ? event.getDirection() : event.getDirection().getOpposite();
        for (BlockPos sourcePos : resolver.getToPush()) {
            final BlockPos targetPos = sourcePos.relative(moveDirection);
            if (ZPZoneChecks.INSTANCE.isBlockPistonsProtected(level, sourcePos) || ZPZoneChecks.INSTANCE.isBlockPistonsProtected(level, targetPos)) {
                event.setCanceled(true);
                return;
            }
        }
        for (BlockPos pos : resolver.getToDestroy()) {
            if (ZPZoneChecks.INSTANCE.isBlockPistonsProtected(level, pos)) {
                event.setCanceled(true);
                return;
            }
        }
    }

    @Override
    public @NotNull ZPSide getSide() {
        return ZPSide.COMMON;
    }

    @Override
    public @NotNull Mod.EventBusSubscriber.Bus getBus() {
        return Mod.EventBusSubscriber.Bus.FORGE;
    }
}
