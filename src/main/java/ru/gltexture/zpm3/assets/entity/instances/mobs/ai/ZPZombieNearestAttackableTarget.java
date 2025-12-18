package ru.gltexture.zpm3.assets.entity.instances.mobs.ai;

import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.entity.instances.mobs.zombies.ZPAbstractZombie;
import ru.gltexture.zpm3.assets.entity.mixins.ext.IPlayerZmTargetsExt;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

public class ZPZombieNearestAttackableTarget extends Goal {
    protected final List<Class<? extends PathfinderMob>> targetTypes;
    private int ticksToUpdateTargetSearching;
    private final int searchUpdateInterval;
    private LivingEntity lastTargetedEntity;
    protected @NotNull SpecialTargetConditions closePlaceConditions;
    protected @NotNull SpecialTargetConditions directViewConditions;
    protected @Nullable SpecialTargetConditions xRayViewConditions;
    private final ZPAbstractZombie zombie;
    private final float searchRangeMultiplier;

    protected ZPZombieNearestAttackableTarget(ZPAbstractZombie pMob, List<Class<? extends PathfinderMob>> nonPlayersToFind, float searchRangeMultiplier, boolean canUseXRayView, int searchUpdateInterval, @Nullable Predicate<LivingEntity> pTargetPredicate) {
        this.setFlags(EnumSet.of(Flag.TARGET));
        this.zombie = pMob;
        this.targetTypes = nonPlayersToFind;
        this.ticksToUpdateTargetSearching = 0;
        this.searchUpdateInterval = searchUpdateInterval;
        this.closePlaceConditions = SpecialTargetConditions.forCombat().selector(pTargetPredicate);
        this.directViewConditions = SpecialTargetConditions.forCombat().selector(pTargetPredicate);
        this.xRayViewConditions = canUseXRayView ? SpecialTargetConditions.forCombat().ignoreLineOfSight().selector(pTargetPredicate) : null;
        this.searchRangeMultiplier = searchRangeMultiplier;
        this.lastTargetedEntity = null;
    }

    public static ZPZombieNearestAttackableTarget player(ZPAbstractZombie pMob, float searchRangeMultiplier, boolean canUseXRayView, int searchUpdateInterval, @Nullable Predicate<LivingEntity> pTargetPredicate) {
        return new ZPZombieNearestAttackableTarget(pMob, null, searchRangeMultiplier, canUseXRayView, searchUpdateInterval, pTargetPredicate);
    }

    public static ZPZombieNearestAttackableTarget nonPlayer(ZPAbstractZombie pMob, List<Class<? extends PathfinderMob>> nonPlayersToFind, float searchRangeMultiplier, boolean canUseXRayView, int searchUpdateInterval, @Nullable Predicate<LivingEntity> pTargetPredicate) {
        return new ZPZombieNearestAttackableTarget(pMob, nonPlayersToFind, searchRangeMultiplier, canUseXRayView, searchUpdateInterval, pTargetPredicate);
    }

    private void setRanges() {
        this.closePlaceConditions.range(6.0f);
        this.directViewConditions.range(this.getFollowDistance());
        if (this.xRayViewConditions != null) {
            this.xRayViewConditions.range(this.getReducedFollowDistanceForXRay());
        }
    }

    public boolean canUse() {
        this.setRanges();
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        return this.lastTargetedEntity != null;
    }

    @Override
    public void stop() {
        this.lastTargetedEntity = null;
    }

    @Override
    public void tick() {
        if (this.lastTargetedEntity != null && (!this.lastTargetedEntity.isAlive() || !this.lastTargetedEntity.equals(this.zombie.getTarget()))) {
            this.lastTargetedEntity = null;
            return;
        }
        if (this.ticksToUpdateTargetSearching-- <= 0) {
            this.ticksToUpdateTargetSearching = this.searchUpdateInterval;
            if (this.zombie.getTarget() != null) {
                this.ticksToUpdateTargetSearching += 20;
            }
            this.lastTargetedEntity = this.findTarget();
            if (this.lastTargetedEntity != null) {
                boolean flag = this.zombie.getTarget() == null || (!this.zombie.getTarget().equals(this.lastTargetedEntity) && this.zombie.distanceTo(this.lastTargetedEntity) <= this.zombie.distanceTo(this.zombie.getTarget()) * 0.5f);
                if (flag) {
                    this.zombie.setTarget(this.lastTargetedEntity);
                }
            }
        }
    }

    public void start() {
        this.ticksToUpdateTargetSearching = ZPRandom.getRandom().nextInt(6);
    }

    protected double getFollowDistance() {
        return (this.zombie.getAttributeValue(Attributes.FOLLOW_RANGE) * 0.75f) * this.searchRangeMultiplier;
    }

    protected LivingEntity findTarget() {
        if (this.targetTypes != null) {
            return this.closestEntity();
        }
        return this.closestPlayer();
    }

    protected AABB getTargetSearchArea(double pTargetDistance) {
        return this.zombie.getBoundingBox().inflate(pTargetDistance, pTargetDistance, pTargetDistance);
    }

    protected LivingEntity closestPlayer() {
        LivingEntity livingEntity = this.getNearestPlayer(this.directViewConditions, this.zombie, this.zombie.getX(), this.zombie.getEyeY(), this.zombie.getZ());
        if (livingEntity == null) {
            if (this.xRayViewConditions == null) {
                return null;
            }
            livingEntity = this.getNearestPlayer(this.xRayViewConditions, this.zombie, this.zombie.getX(), this.zombie.getEyeY(), this.zombie.getZ());
        }
        return livingEntity;
    }

    protected LivingEntity closestEntity() {
        final Predicate<LivingEntity> pFilter = (p) -> this.targetTypes.stream().anyMatch(e -> e.isAssignableFrom(p.getClass()));
        LivingEntity livingEntity = this.getNearestEntity(this.zombie.level().getEntitiesOfClass(LivingEntity.class, this.getTargetSearchArea(this.getFollowDistance()), pFilter), this.directViewConditions, this.zombie.getX(), this.zombie.getEyeY(), this.zombie.getZ());
        if (livingEntity == null) {
            if (this.xRayViewConditions == null) {
                return null;
            }
            livingEntity = this.getNearestEntity(this.zombie.level().getEntitiesOfClass(LivingEntity.class, this.getTargetSearchArea(this.getReducedFollowDistanceForXRay()), pFilter), this.xRayViewConditions, this.zombie.getX(), this.zombie.getEyeY(), this.zombie.getZ());
        }
        return livingEntity;
    }

    protected float getReducedFollowDistanceForXRay() {
        return (float) (this.getFollowDistance() * 0.5f);
    }

    @Nullable
    protected <T extends LivingEntity> T getNearestEntity(List<? extends T> pEntities, SpecialTargetConditions pPredicate, double pX, double pY, double pZ) {
        double $$6 = -1.0F;
        T $$7 = null;
        for(T $$8 : pEntities) {
            if (pPredicate.test(this.zombie, $$8)) {
                double $$9 = $$8.distanceToSqr(pX, pY, pZ);
                if ($$6 == (double)-1.0F || $$9 < $$6) {
                    $$6 = $$9;
                    $$7 = $$8;
                }
            }
        }
        return $$7;
    }

    @Nullable
    protected Player getNearestPlayer(SpecialTargetConditions pPredicate, @Nullable LivingEntity pTarget, double pX, double pY, double pZ) {
        List<? extends Player> pEntities = this.zombie.level().players();
        double $$6 = -1.0F;
        Player $$7 = null;

        for(Player $$8 : pEntities) {
            if (pPredicate.test(this.zombie, $$8)) {
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