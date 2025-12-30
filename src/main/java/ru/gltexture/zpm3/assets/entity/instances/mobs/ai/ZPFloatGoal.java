package ru.gltexture.zpm3.assets.entity.instances.mobs.ai;

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