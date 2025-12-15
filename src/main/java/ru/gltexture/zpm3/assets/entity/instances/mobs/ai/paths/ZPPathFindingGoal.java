package ru.gltexture.zpm3.assets.entity.instances.mobs.ai.paths;

import java.util.EnumSet;

import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Path;
import org.joml.Vector3f;
import org.joml.Vector3i;
import ru.gltexture.zpm3.assets.common.global.ZPConstants;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;

public abstract class ZPPathFindingGoal extends Goal {
    private static final int MAX_NOT_FOUND_PUNISHMENT = 60;
    private Vector3f cachedTargetPos;
    protected final PathfinderMob mob;
    private final double speedModifier;
    private Path path;
    private final boolean followEvenNotSeeTarget;
    private int ticksUntilNextAttack;
    private int timeBeforeNextRecalculation;
    private int notFoundPathPunishment;

    public ZPPathFindingGoal(PathfinderMob pMob, double speedModifier, boolean followEvenNotSeeTarget) {
        this.mob = pMob;
        this.speedModifier = speedModifier;
        this.followEvenNotSeeTarget = followEvenNotSeeTarget;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    public boolean canUse() {
        return this.canContinueToUse();
    }

    public boolean canContinueToUse() {
        LivingEntity livingentity = this.mob.getTarget();
        if (livingentity == null || !livingentity.isAlive()) {
            return false;
        } else if (!this.followEvenNotSeeTarget) {
            return !this.mob.getNavigation().isDone();
        } else if (!this.mob.isWithinRestriction(livingentity.blockPosition())) {
            return false;
        } else {
            return !(livingentity instanceof Player player) || (!player.isSpectator() && !player.isCreative());
        }
    }

    protected void setTimeBeforeNextRecalculation(int time) {
        this.timeBeforeNextRecalculation = time + ZPRandom.getRandom().nextInt(20);
    }

    public void start() {
        this.cachedTargetPos = null;
        this.ticksUntilNextAttack = 0;
        this.timeBeforeNextRecalculation = ZPRandom.getRandom().nextInt(20);
        this.mob.setAggressive(true);
    }

    public void stop() {
        LivingEntity livingentity = this.mob.getTarget();
        if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(livingentity)) {
            this.mob.setTarget(null);
        }
        this.mob.setAggressive(false);
        this.mob.getNavigation().stop();
    }

    public boolean requiresUpdateEveryTick() {
        return true;
    }

    public void tick() {
        LivingEntity livingentity = this.mob.getTarget();
        if (livingentity != null) {
            this.mob.getLookControl().setLookAt(livingentity, 30.0F, 30.0F);
            final double distanceInBlocksToEntity = this.mob.distanceTo(livingentity);
            int updTicks = (int) (0.5f * Math.pow(distanceInBlocksToEntity, 1.5f));
            if (this.timeBeforeNextRecalculation-- <= 0) {
                if (!this.followEvenNotSeeTarget && !this.mob.getSensing().hasLineOfSight(livingentity)) {
                    this.timeBeforeNextRecalculation = ZPRandom.getRandom().nextInt(60);
                    return;
                }
                this.path = this.mob.getNavigation().createPath(livingentity, 0);
                if (this.path == null || this.path.getEndNode() == null || !this.path.canReach() || !this.mob.getNavigation().moveTo(this.path, this.speedModifier)) {
                    this.cachedTargetPos = new Vector3f(livingentity.position().toVector3f());
                    this.notFoundPathPunishment = Math.min(this.notFoundPathPunishment + 10, ZPPathFindingGoal.MAX_NOT_FOUND_PUNISHMENT);
                } else {
                    this.cachedTargetPos = new Vector3f(this.path.getEndNode().asVec3().toVector3f());
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
            if (this.cachedTargetPos != null && this.path != null && this.path.getEndNode() != null) {
                System.out.println("F " + this.cachedTargetPos.distance(livingentity.position().toVector3f()) + " " + this.mob.position().distanceTo(this.path.getEndNode().asVec3()) * 0.5f);
                if (this.cachedTargetPos.distance(livingentity.position().toVector3f()) >= Math.min(this.mob.position().distanceTo(this.path.getEndNode().asVec3()) * 0.25f, 1.0f)) {
                    this.timeBeforeNextRecalculation = ZPRandom.getRandom().nextInt(10);
                    this.path = null;
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
