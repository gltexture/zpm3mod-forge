package ru.gltexture.zpm3.assets.entity.instances.mobs.ai;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import ru.gltexture.zpm3.assets.common.global.ZPConstants;
import ru.gltexture.zpm3.assets.entity.instances.mobs.zombies.ZPAbstractZombie;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;

import javax.annotation.Nullable;
import java.util.*;

public class ZPZombieHelpWantedGoal extends Goal {
    private final List<ZPAbstractZombie> zombiesWantedForHelp;
    private final TargetingConditions HURT_BY_TARGETING;
    private int ticksToAlertHelp;
    protected final Mob mob;
    protected LivingEntity targetMob;

    public ZPZombieHelpWantedGoal(Mob mob) {
        this.setFlags(EnumSet.of(Flag.TARGET));
        this.zombiesWantedForHelp = new ArrayList<>();
        this.HURT_BY_TARGETING = TargetingConditions.forCombat().range(this.helpAlertRange()).ignoreLineOfSight().ignoreInvisibilityTesting();
        this.ticksToAlertHelp = ZPRandom.getRandom().nextInt(81);
        this.mob = mob;
    }

    public boolean canUse() {
        LivingEntity livingentity = this.mob.getTarget();
        if (livingentity == null) {
            return false;
        }
        this.zombiesWantedForHelp.removeIf(abstractZombie -> abstractZombie == null || !abstractZombie.isAlive() || abstractZombie.getTarget() == null || !abstractZombie.getTarget().equals(this.mob.getTarget()));
        if (this.zombiesWantedForHelp.size() > ZPConstants.MAX_ENTITIES_ZOMBIE_CAN_CALL_TO_HELP) {
            return false;
        }
        if (this.ticksToAlertHelp-- <= 0 && (livingentity instanceof Player || livingentity instanceof Villager)) {
            this.ticksToAlertHelp = 80 + ZPRandom.getRandom().nextInt(21);
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
        return ZPConstants.MAX_RADIUS_ZOMBIE_CAN_CALL_FOR_HELP;
    }

    protected void alertOthers() {
        AABB aabb = AABB.unitCubeFromLowerCorner(this.mob.position()).inflate(this.helpAlertRange(), this.helpAlertRange() / 2.0f, this.helpAlertRange());
        List<ZPAbstractZombie> list = this.mob.level().getEntitiesOfClass(ZPAbstractZombie.class, aabb, (e) -> !e.equals(this.mob));
        list.forEach(e -> {
            if (e.getTarget() == null || (e.getTarget().position().distanceTo(e.position()) >= this.helpAlertRange() * 0.5f && (e.getTarget().position().distanceTo(e.position()) > Objects.requireNonNull(this.targetMob).position().distanceTo(e.position())))) {
                this.zombiesWantedForHelp.add(e);
                e.setTarget(this.targetMob);
            }
        });
    }

    protected boolean canAttack(@Nullable LivingEntity pPotentialTarget) {
        if (pPotentialTarget == null) {
            return false;
        } else if (!this.HURT_BY_TARGETING.test(this.mob, pPotentialTarget)) {
            return false;
        } else {
            return this.mob.isWithinRestriction(pPotentialTarget.blockPosition());
        }
    }
}