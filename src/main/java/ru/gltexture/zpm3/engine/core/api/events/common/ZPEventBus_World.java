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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.engine.core.api.events.ZPEventDef;
import ru.gltexture.zpm3.modules.guns.item.ZPBaseGun;
import ru.gltexture.zpm3.modules.guns.processing.logic.ZPDefaultGunLogicFunctions;

public abstract class ZPEventBus_World {
    public static final class ZombieMiningShortMemAddEntryEvent extends ZPEventDef.Cancellable implements ZPEventDef.IEvent {
        private final Entity reason;
        private final Level level;
        private final BlockPos blockPos;
        private float progressInc;

        public ZombieMiningShortMemAddEntryEvent(@Nullable Entity reason, @NotNull Level level, @NotNull BlockPos blockPos, float progressInc) {
            this.reason = reason;
            this.level = level;
            this.blockPos = blockPos;
            this.progressInc = progressInc;
        }

        public @Nullable Entity getReason() {
            return this.reason;
        }

        public @NotNull Level getLevel() {
            return this.level;
        }

        public @NotNull BlockPos getBlockPos() {
            return this.blockPos;
        }

        public float getProgressInc() {
            return this.progressInc;
        }

        public void setProgressInc(float progressInc) {
            this.progressInc = progressInc;
        }
    }

    public static final class ZombieMiningLongMemAddEntryEvent extends ZPEventDef.Cancellable implements ZPEventDef.IEvent {
        private final Entity reason;
        private final Level level;
        private final BlockPos blockPos;
        private float progressInc;

        public ZombieMiningLongMemAddEntryEvent(@Nullable Entity reason, @NotNull Level level, @NotNull BlockPos blockPos, float progressInc) {
            this.reason = reason;
            this.level = level;
            this.blockPos = blockPos;
            this.progressInc = progressInc;
        }

        public @Nullable Entity getReason() {
            return this.reason;
        }

        public @NotNull Level getLevel() {
            return this.level;
        }

        public @NotNull BlockPos getBlockPos() {
            return this.blockPos;
        }

        public float getProgressInc() {
            return this.progressInc;
        }

        public void setProgressInc(float progressInc) {
            this.progressInc = progressInc;
        }
    }
}
/*
EventLauncher.pushEvent(new ZPEventBus_ClientRendering.RenderOGLSceneEvent(this, frameTicking, ZPEventBus_ClientRendering.Run.POST, toRenderObjects, toRenderLiquids), TODO);

    public static final class Class123 implements IEvent {

    }
 */