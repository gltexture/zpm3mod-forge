package ru.gltexture.zpm3.assets.common.instances.entities.mobs;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.*;
import ru.gltexture.zpm3.assets.common.global.ZPConstants;
import ru.gltexture.zpm3.assets.common.init.ZPEntities;
import ru.gltexture.zpm3.assets.common.instances.entities.ZPRockEntity;

public class ZPCommonZombie extends ZPAbstractZombie {
    public ZPCommonZombie(Level pLevel) {
        this(ZPEntities.zp_common_zombie_entity.get(), pLevel);
    }

    public ZPCommonZombie(EntityType<ZPCommonZombie> zpRockEntityEntityType, Level level) {
        super(zpRockEntityEntityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(1, new BreakDoorGoal(this, (e) -> true));
        //this.goalSelector.addGoal(2, new ZombieAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers(ZombifiedPiglin.class));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Turtle.class, 10, true, false, Turtle.BABY_ON_LAND_SELECTOR));
    }

    @Override
    protected int getTotalZombieSkins() {
        return ZPConstants.TOTAL_COMMON_ZOMBIE_TEXTURES;
    }

    @Override
    public int getExperienceReward() {
        if (this.isBaby()) {
            this.xpReward = (int)((double)this.xpReward * 2.5D);
        }
        return super.getExperienceReward();
    }
}