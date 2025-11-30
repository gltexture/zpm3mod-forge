package ru.gltexture.zpm3.assets.common.instances.entities.mobs.ai;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.phys.AABB;
import ru.gltexture.zpm3.assets.common.instances.entities.mobs.zombies.ZPAbstractZombie;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;

public class ZPZombieHurtByMobGoal extends TargetGoal {
    private static final TargetingConditions HURT_BY_TARGETING = TargetingConditions.forCombat().ignoreLineOfSight().ignoreInvisibilityTesting();
    private int timestamp;

    public ZPZombieHurtByMobGoal(PathfinderMob pMob) {
        super(pMob, false);
        this.setFlags(EnumSet.of(Goal.Flag.TARGET));
    }

    public boolean canUse() {
        int i = this.mob.getLastHurtByMobTimestamp();
        LivingEntity livingentity = this.mob.getLastHurtByMob();
        if (i != this.timestamp && livingentity != null) {
            if (livingentity instanceof ZPAbstractZombie) {
                return false;
            }
            return this.canAttack(livingentity, ZPZombieHurtByMobGoal.HURT_BY_TARGETING);
        } else {
            return false;
        }
    }

    public void start() {
        this.mob.setTarget(this.mob.getLastHurtByMob());
        this.targetMob = this.mob.getTarget();
        this.timestamp = this.mob.getLastHurtByMobTimestamp();
        this.unseenMemoryTicks = 300;
        this.alertOthers();
        super.start();
    }

    protected float helpAlertRange() {
        return 32.0f;
    }

    protected void alertOthers() {
        AABB aabb = AABB.unitCubeFromLowerCorner(this.mob.position()).inflate(this.helpAlertRange(), this.helpAlertRange() / 2.0f, this.helpAlertRange());
        List<ZPAbstractZombie> list = this.mob.level().getEntitiesOfClass(ZPAbstractZombie.class, aabb, (e) -> !e.equals(this.mob));
        list.forEach(e -> {
            if (e.getTarget() == null || (e.getTarget().position().distanceTo(e.position()) > 16.0f && (e.getTarget().position().distanceTo(e.position()) > Objects.requireNonNull(this.targetMob).position().distanceTo(e.position())) && ZPRandom.instance.randomBoolean())) {
                e.setTarget(this.targetMob);
            }
        });
    }
}