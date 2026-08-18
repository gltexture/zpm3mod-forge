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

package ru.gltexture.zpm3.engine.core.api.events.common;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.engine.core.api.events.ZPEventDef;
import ru.gltexture.zpm3.modules.loot_cases.instances.block_entities.ZPLootCaseBlockEntity;
import ru.gltexture.zpm3.modules.loot_cases.loot_tables.ZPLootTable;

public abstract class ZPEventBus_Blocks {
    public static final class LootCaseRespawnEvent extends ZPEventDef.Cancellable implements ZPEventDef.IEvent {
        private final Level level;
        private final BlockPos pos;
        private final Player player;
        private final ZPLootCaseBlockEntity lootCase;
        private final ZPLootTable lootTable;

        public LootCaseRespawnEvent(@NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull ZPLootCaseBlockEntity lootCase, @NotNull ZPLootTable lootTable) {
            this.level = level;
            this.pos = pos;
            this.player = player;
            this.lootCase = lootCase;
            this.lootTable = lootTable;
        }

        public @NotNull Level getLevel() {
            return this.level;
        }

        public @NotNull BlockPos getPos() {
            return this.pos;
        }

        public @NotNull Player getPlayer() {
            return this.player;
        }

        public @NotNull ZPLootCaseBlockEntity getLootCase() {
            return this.lootCase;
        }

        public @NotNull ZPLootTable getLootTable() {
            return this.lootTable;
        }
    }

    public static final class FadingBlockExtinguishEvent extends ZPEventDef.Cancellable implements ZPEventDef.IEvent {
        private final Level level;
        private final BlockPos blockPos;
        private final BlockState state;
        private BlockState newState;

        public FadingBlockExtinguishEvent(@NotNull Level level, @NotNull BlockPos blockPos, @NotNull BlockState state, @NotNull BlockState newState) {
            this.level = level;
            this.blockPos = blockPos;
            this.state = state;
            this.newState = newState;
        }

        public @NotNull Level getLevel() {
            return this.level;
        }

        public @NotNull BlockPos getBlockPos() {
            return this.blockPos;
        }

        public @NotNull BlockState getState() {
            return this.state;
        }

        public @NotNull BlockState getNewState() {
            return this.newState;
        }

        public void setNewState(@NotNull BlockState newState) {
            this.newState = newState;
        }
    }

    public static final class CandleExtinguishEvent extends ZPEventDef.Cancellable implements ZPEventDef.IEvent {
        private final ServerLevel level;
        private final BlockPos pos;
        private final BlockState state;

        public CandleExtinguishEvent(@NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull BlockState state) {
            this.level = level;
            this.pos = pos;
            this.state = state;
        }

        public @NotNull ServerLevel getLevel() {
            return this.level;
        }

        public @NotNull BlockPos getPos() {
            return this.pos;
        }

        public @NotNull BlockState getState() {
            return this.state;
        }
    }
}
/*
EventLauncher.pushEvent(new ZPEventBus_ClientRendering.RenderOGLSceneEvent(this, frameTicking, ZPEventBus_ClientRendering.Run.POST, toRenderObjects, toRenderLiquids), TODO);

    public static final class Class123 implements IEvent {

    }
 */