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

package ru.gltexture.zpm3.modules.entity.instances.mobs.ai;

import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class ZPFloatGoal extends Goal {
    private final Mob mob;

    public ZPFloatGoal(Mob pMob) {
        this.mob = pMob;
        this.setFlags(EnumSet.of(Flag.JUMP));
        pMob.getNavigation().setCanFloat(true);
    }

    public boolean canUse() {
        LivingEntity currentTarget = this.mob.getTarget();
        if (currentTarget == null || currentTarget.getY() < this.mob.getY()) {
            return false;
        }
        return this.mob.isInWater() && this.mob.getFluidHeight(FluidTags.WATER) > this.mob.getFluidJumpThreshold() || this.mob.isInLava() || this.mob.isInFluidType((fluidType, height) -> this.mob.canSwimInFluidType(fluidType) && height > this.mob.getFluidJumpThreshold());
    }

    @Override
    public boolean canContinueToUse() {
        return false;
    }

    public void tick() {
        if (this.mob.tickCount % 10 == 0) {
            this.mob.getJumpControl().jump();
        }
    }
}