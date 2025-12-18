package ru.gltexture.zpm3.assets.entity.instances.mobs.ai;

import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.entity.instances.mobs.zombies.ZPAbstractZombie;
import ru.gltexture.zpm3.assets.entity.mixins.ext.IPlayerZmTargetsExt;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

public class ZPZombieNearestAttackableTargetPlayerGoal extends TargetGoal {
    private int ticksToUpdateTargetSearching;

    private final int searchUpdateInterval;

    protected @NotNull SpecialTargetConditions closePlaceConditions;
    protected @NotNull SpecialTargetConditions directViewConditions;
    protected @Nullable SpecialTargetConditions xRayViewConditions;

    private final float searchRangeMultiplier;

    public ZPZombieNearestAttackableTargetPlayerGoal(ZPAbstractZombie pMob, float searchRangeMultiplier, boolean canUseXRayView, int searchUpdateInterval, @Nullable Predicate<LivingEntity> pTargetPredicate) {
        super(pMob, false, false);
        this.setFlags(EnumSet.of(Flag.TARGET));
        this.ticksToUpdateTargetSearching = 0;
        this.searchUpdateInterval = searchUpdateInterval;
        this.closePlaceConditions = SpecialTargetConditions.forCombat().selector(pTargetPredicate);
        this.directViewConditions = SpecialTargetConditions.forCombat().selector(pTargetPredicate);
        this.xRayViewConditions = canUseXRayView ? SpecialTargetConditions.forCombat().ignoreLineOfSight().selector(pTargetPredicate) : null;
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
    public void stop() {
        this.targetMob = null;
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
        livingEntity = this.getNearestPlayer(this.closePlaceConditions, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
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
        livingEntity = this.getNearestPlayer(this.directViewConditions, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
        if (livingEntity == null) {
            livingEntity = this.xRayViewConditions == null ? null : this.getNearestPlayer(this.xRayViewConditions, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
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

    @Nullable
    private Player getNearestPlayer(SpecialTargetConditions pPredicate, @Nullable LivingEntity pTarget, double pX, double pY, double pZ) {
        List<? extends Player> pEntities = this.mob.level().players();
        double $$6 = -1.0F;
        Player $$7 = null;

        for(Player $$8 : pEntities) {
            if (pPredicate.test((ZPAbstractZombie) this.mob, $$8)) {
                double $$9 = $$8.distanceToSqr(pX, pY, pZ);
                if ($$6 == (double)-1.0F || $$9 < $$6) {
                    $$6 = $$9;
                    $$7 = $$8;
                }
            }
        }

        return $$7;
    }
    public static class SpecialTargetConditions {
        public static final SpecialTargetConditions DEFAULT = forCombat();
        private static final double MIN_VISIBILITY_DISTANCE_FOR_INVISIBLE_TARGET = (double)2.0F;
        private final boolean isCombat;
        private double range = (double)-1.0F;
        private boolean checkLineOfSight = true;
        private boolean testInvisible = true;
        @Nullable
        private Predicate<LivingEntity> selector;

        private SpecialTargetConditions(boolean pIsCombat) {
            this.isCombat = pIsCombat;
        }

        public static SpecialTargetConditions forCombat() {
            return new SpecialTargetConditions(true);
        }

        public static SpecialTargetConditions forNonCombat() {
            return new SpecialTargetConditions(false);
        }

        public SpecialTargetConditions copy() {
            SpecialTargetConditions $$0 = this.isCombat ? forCombat() : forNonCombat();
            $$0.range = this.range;
            $$0.checkLineOfSight = this.checkLineOfSight;
            $$0.testInvisible = this.testInvisible;
            $$0.selector = this.selector;
            return $$0;
        }

        public SpecialTargetConditions range(double pDistance) {
            this.range = pDistance;
            return this;
        }

        public SpecialTargetConditions ignoreLineOfSight() {
            this.checkLineOfSight = false;
            return this;
        }

        public SpecialTargetConditions ignoreInvisibilityTesting() {
            this.testInvisible = false;
            return this;
        }

        public SpecialTargetConditions selector(@Nullable Predicate<LivingEntity> pCustomPredicate) {
            this.selector = pCustomPredicate;
            return this;
        }

        private boolean ifPlayerThatCanBeNotTargeted(@Nullable ZPAbstractZombie pAttacker, LivingEntity pTarget) {
            if (pAttacker != null && pTarget instanceof IPlayerZmTargetsExt ext) {
                return ext.test(pAttacker);
            }
            return true;
        }

        public boolean test(@Nullable ZPAbstractZombie pAttacker, @NotNull LivingEntity pTarget) {
            if (pAttacker == pTarget) {
                return false;
            } else if (!pTarget.canBeSeenByAnyone()) {
                return false;
            } else if (this.selector != null && !this.selector.test(pTarget)) {
                return false;
            } else {
                if (pAttacker == null) {
                    return !this.isCombat || (pTarget.canBeSeenAsEnemy() && pTarget.level().getDifficulty() != Difficulty.PEACEFUL);
                } else {
                    if (!this.ifPlayerThatCanBeNotTargeted(pAttacker, pTarget)) {
                        return false;
                    }
                    if (this.isCombat && (!pAttacker.canAttack(pTarget) || !pAttacker.canAttackType(pTarget.getType()) || pAttacker.isAlliedTo(pTarget))) {
                        return false;
                    }
                    if (this.range > (double) 0.0F) {
                        double $$2 = this.testInvisible ? pTarget.getVisibilityPercent(pAttacker) : (double) 1.0F;
                        double $$3 = Math.max(this.range * $$2, 2.0F);
                        double $$4 = pAttacker.distanceToSqr(pTarget.getX(), pTarget.getY(), pTarget.getZ());
                        if ($$4 > $$3 * $$3) {
                            return false;
                        }
                    }
                    if (this.checkLineOfSight) {
                        return pAttacker.getSensing().hasLineOfSight(pTarget);
                    }
                }
                return true;
            }
        }
    }
}