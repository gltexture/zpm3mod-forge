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

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;

import java.util.EnumSet;

public class ZPZombieAngryGoal extends Goal {
    protected final Mob mob;
    protected LivingEntity targetMob;

    private int ticksBeforeGetAngry;
    private int angryTicks;

    public ZPZombieAngryGoal(PathfinderMob pMob) {
        this.mob = pMob;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    public boolean canUse() {
        if (this.mob.tickCount < 600 || this.mob.getNavigation().getPath() == null || !this.mob.getNavigation().getPath().canReach()) {
            return false;
        }
        if (this.ticksBeforeGetAngry-- <= 0) {
            if (ZPRandom.getRandom().nextFloat() <= 0.3f) {
                this.targetMob = this.mob.getTarget();
                if (this.targetMob == null) {
                    return false;
                }
                this.ticksBeforeGetAngry = 400;
                return this.targetMob.distanceTo(this.mob) <= 8.0f;
            }
        }
        return false;
    }

    public void start() {
        super.start();
        this.angryTicks = 160;
    }

    @Override
    public boolean canContinueToUse() {
        return this.angryTicks-- > 0 && this.mob.getNavigation().getPath() != null;
    }

    @Override
    public void stop() {
        super.stop();
        this.mob.setSprinting(false);
    }

    @Override
    public void tick() {
        super.tick();
        this.mob.setSprinting(true);
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }
}