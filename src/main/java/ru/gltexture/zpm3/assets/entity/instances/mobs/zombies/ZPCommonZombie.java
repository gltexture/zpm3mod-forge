package ru.gltexture.zpm3.assets.entity.instances.mobs.zombies;

import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.*;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.global.ZPConstants;
import ru.gltexture.zpm3.assets.common.init.ZPEntities;
import ru.gltexture.zpm3.assets.common.init.ZPEntityAttributes;
import ru.gltexture.zpm3.assets.entity.instances.mobs.ai.*;
import ru.gltexture.zpm3.assets.entity.instances.mobs.ai.attack.ZPZombieAttackGoalRewrite;
import ru.gltexture.zpm3.assets.entity.instances.mobs.ai.attack.ZPZombieAttackGoalVanilla;
import ru.gltexture.zpm3.assets.entity.instances.throwables.*;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;
import ru.gltexture.zpm3.engine.service.Pair;

import java.util.List;

public class ZPCommonZombie extends ZPAbstractZombie {
    public ZPCommonZombie(Level pLevel) {
        this(ZPEntities.zp_common_zombie_entity.get(), pLevel);
    }

    public ZPCommonZombie(EntityType<ZPCommonZombie> zpRockEntityEntityType, Level level) {
        super(zpRockEntityEntityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new ZPZombieMiningGoal(this, ZPZombieMiningGoal.DEFAULT_BLOCKS_FILTER(), ZPZombieMiningGoal.DEFAULT_MINING_CONDITION(60.0f)));
        this.goalSelector.addGoal(2, new ZPZombieThrowAGiftGoal(this,
                Pair.of(() -> new ZPPlateEntity(ZPEntities.plate_entity.get(), this.level()), 60),
                Pair.of(() -> new ZPRockEntity(ZPEntities.rock_entity.get(), this.level()), 10),
                Pair.of(() -> new ZPRottenFleshEntity(ZPEntities.rotten_flesh_entity.get(), this.level()), 40),
                Pair.of(() -> new ZPBrickEntity(ZPEntities.brock_entity.get(), this.level()), 20),
                Pair.of(() -> new ZPAcidBottleEntity(ZPEntities.acid_bottle_entity.get(), this.level()), 5)
        ));
        if (ZPConstants.ZP_PATH_UPDATER_ALG == 0) {
            this.goalSelector.addGoal(3, new ZPZombieAttackGoalVanilla(this, 1.0D, true));
        } else {
            this.goalSelector.addGoal(3, new ZPZombieAttackGoalRewrite(this, 1.0D, true));
        }
        this.goalSelector.addGoal(4, (new ZPZombieHelpWantedGoal(this)));
        this.goalSelector.addGoal(5, (new ZPZombieEatingGoal(this)));
        this.goalSelector.addGoal(6, new ZPZombieAngryGoal(this));
        this.goalSelector.addGoal(8, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(10, new ZPZombieRandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, ZPZombieNearestAttackableTarget.player(this, 1.0f, ZPConstants.ZOMBIE_XRAY_LOOK, 10, (e) -> true));
        this.targetSelector.addGoal(2, ZPZombieNearestAttackableTarget.nonPlayer(this, List.of(AbstractVillager.class), 0.5f, ZPConstants.ZOMBIE_XRAY_LOOK, 20, (e) -> true));
        this.targetSelector.addGoal(3, ZPZombieNearestAttackableTarget.nonPlayer(this, List.of(Cow.class, IronGolem.class, Horse.class, Sheep.class, Pig.class), 0.35f, false, 60, (e) -> true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(ForgeMod.SWIM_SPEED.get(), 1.0f)
                .add(Attributes.MAX_HEALTH, 40.0f * ZPConstants.ZOMBIE_MAX_HEALTH_MULTIPLIER)
                .add(Attributes.FOLLOW_RANGE, ZPConstants.ZOMBIE_FOLLOW_RANGE)
                .add(Attributes.MOVEMENT_SPEED, 0.23f * ZPConstants.ZOMBIE_MOVEMENT_SPEED_MULTIPLIER)
                .add(Attributes.ATTACK_DAMAGE, 2.5f * ZPConstants.ZOMBIE_ATTACK_DAMAGE_MULTIPLIER)
                .add(Attributes.ARMOR, 0.5f)
                .add(ZPEntityAttributes.zm_attack_range_multiplier.get(), 0.65f / ZPConstants.ZOMBIE_ATTACK_RANGE_MULTIPLIER)
                .add(ZPEntityAttributes.zm_mining_speed.get(), 0.005f * ZPConstants.ZOMBIE_MINING_SPEED_MULTIPLIER)
                .add(ZPEntityAttributes.zm_random_effect_chance.get(), 0.015f * ZPConstants.ZOMBIE_APPLY_NEGATIVE_EFFECT_ON_ENTITY_CHANCE_MULTIPLIER)
                .add(ZPEntityAttributes.zm_throw_a_gift_chance.get(), 0.0075f * ZPConstants.ZOMBIE_THROW_A_GIFT_CHANCE_MULTIPLIER);
    }

    @Override
    protected int getTotalZombieSkins() {
        return ZPConstants.TOTAL_COMMON_ZOMBIE_TEXTURES;
    }

    @Override
    protected @NotNull LootTable getZmLootTable() {
        return new LootTable.Builder().withPool(new LootPool.Builder().setRolls(UniformGenerator.between(0, 2)).add(LootItem.lootTableItem(Items.ROTTEN_FLESH))).build();
    }

    @Override
    protected void randomizeAttributes() {
        this.addRandomAttributeValue(ForgeMod.SWIM_SPEED.get(), ZPAbstractZombie.getRandomSalt(0.0f, 0.25f));
        this.addRandomAttributeValue(Attributes.MAX_HEALTH, ZPAbstractZombie.getRandomSalt(0.0f, 11.0f));
        this.addRandomAttributeValue(Attributes.FOLLOW_RANGE, ZPAbstractZombie.getRandomSalt(-2.0f, 2.0f));
        this.addRandomAttributeValue(Attributes.MOVEMENT_SPEED, ZPAbstractZombie.getRandomSalt(-0.005f, 0.01f));
        this.addRandomAttributeValue(Attributes.ATTACK_DAMAGE, ZPAbstractZombie.getRandomSalt(0.0f, 0.5f));
        this.addRandomAttributeValue(Attributes.ARMOR, ZPAbstractZombie.getRandomSalt(0.0f, 0.5f));
        //this.addRandomAttributeValue(Attributes.SPAWN_REINFORCEMENTS_CHANCE, 0.3f);
        this.addRandomAttributeValue(ZPEntityAttributes.zm_attack_range_multiplier.get(), ZPAbstractZombie.getRandomSalt(-0.05f, 0.05f));
        this.addRandomAttributeValue(ZPEntityAttributes.zm_mining_speed.get(), ZPAbstractZombie.getRandomSalt(-0.0006f, 0.0006f));
        this.addRandomAttributeValue(ZPEntityAttributes.zm_random_effect_chance.get(), ZPAbstractZombie.getRandomSalt(-0.005f, 0.005f));
        this.addRandomAttributeValue(ZPEntityAttributes.zm_throw_a_gift_chance.get(), ZPAbstractZombie.getRandomSalt(-0.0025f, 0.0025f));
    }

    @Override
    protected void randomEquipment(DifficultyInstance pDifficulty) {
        super.defaultRandomEquipment(pDifficulty, this::getRandomArmorForSlot, 0.0125f, (slot) -> this.getRandomToolOrWeapon(), 0.0875f);
    }

    private ItemStack getRandomArmorForSlot(EquipmentSlot slot) {
        return switch (slot) {
            case HEAD -> ZPRandom.getRandom().nextBoolean() ? new ItemStack(Items.IRON_HELMET) : new ItemStack(Items.LEATHER_HELMET);
            case CHEST -> ZPRandom.getRandom().nextBoolean() ? new ItemStack(Items.IRON_CHESTPLATE) : new ItemStack(Items.LEATHER_CHESTPLATE);
            case LEGS -> ZPRandom.getRandom().nextBoolean() ? new ItemStack(Items.IRON_LEGGINGS) : new ItemStack(Items.LEATHER_LEGGINGS);
            case FEET -> ZPRandom.getRandom().nextBoolean() ? new ItemStack(Items.IRON_BOOTS) : new ItemStack(Items.LEATHER_BOOTS);
            default -> null;
        };
    }

    private ItemStack getRandomToolOrWeapon() {
        Item[] pool = {
                Items.WOODEN_PICKAXE, Items.STONE_PICKAXE, Items.IRON_PICKAXE,
                Items.WOODEN_SHOVEL, Items.STONE_SHOVEL, Items.IRON_SHOVEL,
                Items.WOODEN_AXE, Items.STONE_AXE, Items.IRON_AXE,
                Items.IRON_SWORD, Items.WOODEN_SWORD, Items.STONE_SWORD
        };
        return new ItemStack(pool[ZPRandom.getRandom().nextInt(pool.length)]);
    }

    @Override
    public int getExperienceReward() {
        return super.getExperienceReward();
    }
}