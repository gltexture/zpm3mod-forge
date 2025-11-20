package ru.gltexture.zpm3.assets.common.instances.entities.mobs.ai;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.pathfinder.Path;
import ru.gltexture.zpm3.assets.common.instances.entities.mobs.zombies.ZPAbstractZombie;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;

import java.util.EnumSet;
import java.util.List;

public class ZPZombieEatingGoal extends Goal {
    protected final ZPAbstractZombie mob;
    protected ItemEntity targetEntity;

    private int ticksToUpdateFoodSeek;

    public ZPZombieEatingGoal(ZPAbstractZombie pMob) {
        this.mob = pMob;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    public boolean canUse() {
        if (this.mob.isEating() || this.mob.getTarget() != null) {
            return false;
        }
        if (this.ticksToUpdateFoodSeek-- <= 0) {
            this.ticksToUpdateFoodSeek = 60 + ZPRandom.getRandom().nextInt(41);
            List<ItemEntity> items = this.mob.level().getEntitiesOfClass(ItemEntity.class, mob.getBoundingBox().inflate(8.0f + ZPRandom.getRandom().nextFloat(2.0f), 2.0f, 8.0f + ZPRandom.getRandom().nextFloat(2.0f)), (e) -> this.mob.isFood(e.getItem()));

            if (items.isEmpty()) {
                return false;
            }

            ItemEntity best = null;
            double bestDist = Double.MAX_VALUE;

            for (ItemEntity item : items) {
                Path path = mob.getNavigation().createPath(item, 0);
                if (path == null) {
                    continue;
                }

                double dist = mob.distanceToSqr(item);
                if (dist < bestDist) {
                    bestDist = dist;
                    best = item;
                }
            }

            if (best != null) {
                this.targetEntity = best;
                return true;
            }

            return true;
        }
        return false;
    }

    public void start() {
        super.start();
        if (this.targetEntity != null) {
            mob.getNavigation().moveTo(this.targetEntity, 1.15);
        }
    }

    @Override
    public boolean canContinueToUse() {
        if (this.targetEntity == null || !this.targetEntity.isAlive() || this.mob.getNavigation().isDone()) {
            return false;
        }
        return !this.mob.isEating() && this.mob.getTarget() == null;
    }

    @Override
    public void stop() {
        super.stop();
        this.targetEntity = null;
        this.mob.getNavigation().stop();
    }

    @Override
    public void tick() {
        super.tick();
    }
}