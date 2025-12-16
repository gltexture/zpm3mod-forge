package ru.gltexture.zpm3.assets.entity.instances.mobs.ai;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import ru.gltexture.zpm3.assets.entity.instances.mobs.zombies.ZPAbstractZombie;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;

public class ZPZombieHelpWantedGoal extends Goal {
    private static final TargetingConditions HURT_BY_TARGETING = TargetingConditions.forCombat().ignoreLineOfSight().ignoreInvisibilityTesting();
    private int ticksToAlertHelp;
    protected final Mob mob;
    protected LivingEntity targetMob;

    public ZPZombieHelpWantedGoal(Mob mob) {
        this.setFlags(EnumSet.of(Flag.TARGET));
        this.ticksToAlertHelp = ZPRandom.getRandom().nextInt(81);
        this.mob = mob;
    }

    public boolean canUse() {
        LivingEntity livingentity = this.mob.getTarget();
        if (this.ticksToAlertHelp-- <= 0 && (livingentity instanceof Player || livingentity instanceof Villager)) {
            this.ticksToAlertHelp = 100 + ZPRandom.getRandom().nextInt(101);
            return this.canAttack(livingentity);
        } else {
            return false;
        }
    }

    @Override
    public boolean canContinueToUse() {
        return false;
    }

    public void start() {
        this.targetMob = this.mob.getTarget();
        if (this.targetMob != null) {
            this.alertOthers();
        }
        super.start();
    }

    protected float helpAlertRange() {
        return 12.0f;
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

    protected boolean canAttack(@Nullable LivingEntity pPotentialTarget) {
        if (pPotentialTarget == null) {
            return false;
        } else if (!ZPZombieHelpWantedGoal.HURT_BY_TARGETING.test(this.mob, pPotentialTarget)) {
            return false;
        } else {
            return this.mob.isWithinRestriction(pPotentialTarget.blockPosition());
        }
    }
}