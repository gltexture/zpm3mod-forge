package ru.gltexture.zpm3.assets.common.instances.entities.mobs.ai;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.AABB;
import ru.gltexture.zpm3.assets.common.instances.entities.mobs.zombies.ZPAbstractZombie;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;

import java.util.EnumSet;
import java.util.List;
import java.util.Objects;

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
        this.angryTicks = 120;
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