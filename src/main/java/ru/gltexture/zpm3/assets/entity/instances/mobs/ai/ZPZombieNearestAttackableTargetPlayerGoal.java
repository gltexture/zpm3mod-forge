package ru.gltexture.zpm3.assets.entity.instances.mobs.ai;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.function.Predicate;

public class ZPZombieNearestAttackableTargetPlayerGoal extends TargetGoal {
    private int ticksToUpdateTargetSearching;

    private final int searchUpdateInterval;

    protected @NotNull TargetingConditions closePlaceConditions;
    protected @NotNull TargetingConditions directViewConditions;
    protected @Nullable TargetingConditions xRayViewConditions;

    private final float searchRangeMultiplier;

    public ZPZombieNearestAttackableTargetPlayerGoal(Mob pMob, float searchRangeMultiplier, boolean canUseXRayView, int searchUpdateInterval, @Nullable Predicate<LivingEntity> pTargetPredicate) {
        super(pMob, false, false);
        this.setFlags(EnumSet.of(Flag.TARGET));
        this.ticksToUpdateTargetSearching = 0;
        this.searchUpdateInterval = searchUpdateInterval;
        this.closePlaceConditions = TargetingConditions.forCombat().selector(pTargetPredicate);
        this.directViewConditions = TargetingConditions.forCombat().selector(pTargetPredicate);
        this.xRayViewConditions = canUseXRayView ? TargetingConditions.forCombat().ignoreLineOfSight().selector(pTargetPredicate) : null;
        this.searchRangeMultiplier = searchRangeMultiplier;
    }

    private void setRanges() {
        this.closePlaceConditions.range(6.0f);
        this.directViewConditions.range(this.getFollowDistance());
        if (this.xRayViewConditions != null) {
            this.xRayViewConditions.range(this.getReducedFollowDistanceForXRay());
        }
    }

    public boolean canUse() {
        if (this.ticksToUpdateTargetSearching-- <= 0) {
            this.ticksToUpdateTargetSearching = this.searchUpdateInterval;
            return this.findTarget();
        }
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        LivingEntity closeEntity = this.checkForCloseEntities();
        if (closeEntity != null) {
            this.stop();
            this.ticksToUpdateTargetSearching = 0;
            if (this.canUse()) {
                this.start();
            }
        }
    }

    @Override
    protected double getFollowDistance() {
        return (super.getFollowDistance() * 0.75f) * this.searchRangeMultiplier;
    }

    protected LivingEntity checkForCloseEntities() {
        LivingEntity livingEntity = null;
        livingEntity = this.mob.level().getNearestPlayer(this.closePlaceConditions, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
        if (livingEntity != null && this.targetMob != null && this.targetMob.distanceTo(this.mob) * 2.0f < livingEntity.distanceTo(this.mob)) {
            return null;
        }
        return livingEntity;
    }

    protected AABB getTargetSearchArea(double pTargetDistance) {
        return this.mob.getBoundingBox().inflate(pTargetDistance, pTargetDistance, pTargetDistance);
    }

    protected boolean findTarget() {
        this.setRanges();
        LivingEntity livingEntity = null;
        livingEntity = this.mob.level().getNearestPlayer(this.directViewConditions, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
        if (livingEntity == null) {
            livingEntity = this.xRayViewConditions == null ? null : this.mob.level().getNearestPlayer(this.xRayViewConditions, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
        }
        if (livingEntity != null && this.mob.getTarget() != null && !this.mob.getTarget().equals(livingEntity)) {
            if (livingEntity.position().distanceTo(this.mob.position()) > 6.0f) {
                return false;
            }
        }
        this.targetMob = livingEntity;
        return livingEntity != null;
    }

    protected float getReducedFollowDistanceForXRay() {
        return (float) (this.getFollowDistance() * 0.5f);
    }

    public void start() {
        this.unseenMemoryTicks = 1200;
        this.mob.setTarget(this.targetMob);
        super.start();
    }

    public void setTarget(@Nullable LivingEntity pTarget) {
        this.targetMob = pTarget;
    }
}