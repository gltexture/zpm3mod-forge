package ru.gltexture.zpm3.assets.entity.instances.mobs.ai.paths;

import java.util.EnumSet;

import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import ru.gltexture.zpm3.assets.common.global.ZPConstants;
import ru.gltexture.zpm3.assets.entity.instances.mobs.zombies.ZPAbstractZombie;
import ru.gltexture.zpm3.assets.entity.instances.mobs.zombies.ZPCommonZombie;
import ru.gltexture.zpm3.assets.entity.mixins.ext.IPlayerZmTargetsExt;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;

public abstract class ZPPathFindingGoal extends Goal {
    private static final int MAX_NOT_FOUND_PUNISHMENT = 60;
    protected final PathfinderMob mob;
    private final double speedModifier;
    private Path path;
    private final boolean followEvenNotSeeTarget;
    private int ticksUntilNextAttack;
    private int timeBeforeNextRecalculation;
    private int timeBeforeNextFollowingRecalculation;
    private int notFoundPathPunishment;
    protected TargetingConditions closePlaceConditions;
    private LivingEntity following;

    public ZPPathFindingGoal(PathfinderMob pMob, double speedModifier, boolean followEvenNotSeeTarget) {
        this.mob = pMob;
        this.speedModifier = speedModifier;
        this.followEvenNotSeeTarget = followEvenNotSeeTarget;
        this.closePlaceConditions = TargetingConditions.forCombat().range(4.0f);
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    public boolean canUse() {
        return this.canContinueToUse();
    }

    protected double getFollowDistance() {
        return this.mob.getAttributeValue(Attributes.FOLLOW_RANGE);
    }

    public boolean canContinueToUse() {
        LivingEntity livingentity = this.mob.getTarget();
        if (livingentity == null || !livingentity.isAlive()) {
            return false;
        } else {
            if (livingentity instanceof IPlayerZmTargetsExt ext) {
                if (!ext.test((ZPAbstractZombie) this.mob)) {
                    this.stop();
                    return false;
                }
            }
            if (livingentity.distanceTo(this.mob) > this.getFollowDistance()) {
                return false;
            } else if (!this.followEvenNotSeeTarget) {
                return this.path == null || !this.mob.getNavigation().isDone();
            } else if (!this.mob.isWithinRestriction(livingentity.blockPosition())) {
                return false;
            } else {
                return !(livingentity instanceof Player player) || (!player.isSpectator() && !player.isCreative());
            }
        }
    }

    public void start() {
        this.ticksUntilNextAttack = 0;
        this.timeBeforeNextRecalculation = ZPRandom.getRandom().nextInt(20);
        this.mob.setAggressive(true);
    }

    public void stop() {
        this.mob.setTarget(null);
        this.mob.setAggressive(false);
        this.mob.getNavigation().stop();
    }

    public boolean requiresUpdateEveryTick() {
        return true;
    }

    protected AABB getTargetSearchArea(double pTargetDistance) {
        return this.mob.getBoundingBox().inflate(pTargetDistance, 2.0f, pTargetDistance);
    }

    protected LivingEntity checkForCloseZombies() {
        LivingEntity livingEntity;
        livingEntity = this.mob.level().getNearestEntity(this.mob.level().getEntitiesOfClass(LivingEntity.class, this.getTargetSearchArea(4.0f), (p) -> p instanceof ZPCommonZombie), this.closePlaceConditions, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
        return livingEntity;
    }

    public void tick() {
        LivingEntity livingentity = this.mob.getTarget();
        if (livingentity != null) {
            this.mob.getLookControl().setLookAt(livingentity, 30.0F, 30.0F);
            final double distanceInBlocksToEntity = this.mob.distanceTo(livingentity);
            boolean flag = false;

            if (false) {
                if (this.following != null) {
                    flag = true;
                    boolean isMeCloserThanClosest = this.mob.distanceTo(livingentity) < this.following.distanceTo(livingentity);
                    if (distanceInBlocksToEntity > 8 || isMeCloserThanClosest || this.mob.getNavigation().isDone() || this.following.distanceTo(this.mob) >= 4) {
                        this.following = null;
                        this.timeBeforeNextFollowingRecalculation = 0;
                        flag = false;
                    }
                }
                if (this.following == null) {
                    this.following = this.checkForCloseZombies();
                    if (this.following != null) {
                        boolean isMeCloserThanClosest = this.mob.distanceTo(livingentity) < this.following.distanceTo(livingentity);
                        if (!isMeCloserThanClosest) {
                            if (this.timeBeforeNextFollowingRecalculation-- <= 0) {
                                this.mob.getNavigation().moveTo(this.following, this.speedModifier);
                                this.timeBeforeNextFollowingRecalculation = 100;
                            }
                            flag = true;
                        } else {
                            this.following = null;
                            this.timeBeforeNextFollowingRecalculation = 0;
                            flag = false;
                        }
                    }
                }
            }

            if (!flag) {
                int updTicks = (int) (0.5f * Math.pow(distanceInBlocksToEntity, ZPConstants.ZOMBIE_PATH_UPDATE_COOLDOWN_PUNISHMENT_GRADE));
                updTicks *= (int) (1.0f / this.speedModifier);
                if (this.timeBeforeNextRecalculation-- <= 0) {
                    if (!this.followEvenNotSeeTarget && !this.mob.getSensing().hasLineOfSight(livingentity)) {
                        this.timeBeforeNextRecalculation = ZPRandom.getRandom().nextInt(60);
                        return;
                    }
                    this.path = this.mob.getNavigation().createPath(livingentity, 0);
                    if (this.path == null || this.path.getEndNode() == null || !this.path.canReach() || !this.mob.getNavigation().moveTo(this.path, this.speedModifier)) {
                        this.notFoundPathPunishment = Math.min(this.notFoundPathPunishment + 10, ZPPathFindingGoal.MAX_NOT_FOUND_PUNISHMENT);
                    } else {
                    }
                    this.timeBeforeNextRecalculation = updTicks + ZPRandom.getRandom().nextInt((int) Mth.clamp(updTicks * 0.5f, 2, 20));
                } else {
                    if (this.path != null) {
                        if (this.mob.getNavigation().isDone()) {
                            this.timeBeforeNextRecalculation = 0;
                            this.path = null;
                        }
                    }
                }
            }
            this.checkAndPerformAttack(livingentity, this.mob.getPerceivedTargetDistanceSquareForMeleeAttack(livingentity));
        }
    }

    protected void checkAndPerformAttack(LivingEntity pEnemy, double pDistToEnemySqr) {
        if (this.ticksUntilNextAttack-- <= 0) {
            double d0 = this.getAttackReachSqr(pEnemy);
            if (pDistToEnemySqr <= d0) {
                if (this.mob.hasLineOfSight(pEnemy)) {
                    this.ticksUntilNextAttack = 20;
                    this.mob.swing(InteractionHand.MAIN_HAND);
                    this.mob.doHurtTarget(pEnemy);
                }
            }
        }
    }

    protected int getTicksUntilNextAttack() {
        return this.ticksUntilNextAttack;
    }

    protected int getAttackInterval() {
        return this.adjustedTickDelay(20);
    }

    protected double getAttackReachSqr(LivingEntity pAttackTarget) {
        return this.mob.getBbWidth() * 2.0F * this.mob.getBbWidth() * 2.0F + pAttackTarget.getBbWidth();
    }
}
