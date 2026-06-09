package ru.gltexture.zpm3.engine.core.config.builtin;

import ru.gltexture.zpm3.engine.core.config.ZPConfigConstantsClass;
import ru.gltexture.zpm3.engine.core.config.vars.*;

public class ZPZombieConfig implements ZPConfigConstantsClass {
    @ZPVarDefinition(description = "Black list of blocks, that cannot be mined by zombie. Example: \"minecraft\\:grass;minecraft\\:stone\".")
    public static final ZPConfig_STRING ZOMBIE_BLOCK_MINING_BLACKLIST =
            new ZPConfig_STRING("");

    @ZPVarDefinition(description = "Allows zombie to spawn at day time, via the chance random.")
    public static final ZPConfig_FLOAT ZOMBIE_SPAWN_AT_DAY_TIME_CHANCE =
            new ZPConfig_FLOAT(0.00525f);

    @ZPVarDefinition(description = "Reduces brightness sensitivity for zombie's spawn calculation.")
    public static final ZPConfig_INT ZOMBIE_BRIGHTNESS_SPAWN_SENSITIVITY_REDUCE =
            new ZPConfig_INT(1);

    @ZPVarDefinition(description = "Defines swap threshold percentage when max zombies targeting player is reached.")
    public static final ZPConfig_FLOAT CLOSEST_ZOMBIE_SWAP_TARGET_PERCENTAGE =
            new ZPConfig_FLOAT(0.05f);

    @ZPVarDefinition(description = "Maximum radius within which a zombie can call other zombies for assistance.")
    public static final ZPConfig_FLOAT MAX_RADIUS_ZOMBIE_CAN_CALL_FOR_HELP =
            new ZPConfig_FLOAT(16.0f);

    @ZPVarDefinition(description = "Maximum number of zombies targeting a single player.")
    public static final ZPConfig_INT MAX_ZOMBIES_TARGETED_ON_PLAYER =
            new ZPConfig_INT(64);

    @ZPVarDefinition(description = "Maximum number of zombies a zombie can call for help.")
    public static final ZPConfig_INT MAX_ENTITIES_ZOMBIE_CAN_CALL_TO_HELP =
            new ZPConfig_INT(3);

    @ZPVarDefinition(description = "Increases interval between zombie path updates.")
    public static final ZPConfig_FLOAT ZOMBIE_PATH_UPDATE_COOLDOWN_PUNISHMENT_GRADE =
            new ZPConfig_FLOAT(1.5f);

    @ZPVarDefinition(description = "Infection duration in ticks applied by zombie attack.")
    public static final ZPConfig_INT ZOMBIE_PLAGUE_VIRUS_EFFECT_TIME_TICKS =
            new ZPConfig_INT(20 * (20 * 60));

    @ZPVarDefinition(description = "Multiplier for firearm projectile damage on zombies.")
    public static final ZPConfig_FLOAT ZOMBIE_BULLET_DAMAGE_MULTIPLIER =
            new ZPConfig_FLOAT(3.25f);

    @ZPVarDefinition(description = "Global zombie melee attack reach multiplier.")
    public static final ZPConfig_FLOAT ZOMBIE_ATTACK_RANGE_MULTIPLIER =
            new ZPConfig_FLOAT(1.0f);

    @ZPVarDefinition(description = "Dog zombie melee attack reach multiplier.")
    public static final ZPConfig_FLOAT ZOMBIE_DOG_ATTACK_RANGE_MULTIPLIER =
            new ZPConfig_FLOAT(0.5f);

    @ZPVarDefinition(description = "Chance multiplier for negative effects applied by zombies.")
    public static final ZPConfig_FLOAT ZOMBIE_APPLY_NEGATIVE_EFFECT_ON_ENTITY_CHANCE_MULTIPLIER =
            new ZPConfig_FLOAT(1.0f);

    @ZPVarDefinition(description = "Global zombie block mining speed multiplier.")
    public static final ZPConfig_FLOAT ZOMBIE_MINING_SPEED_MULTIPLIER =
            new ZPConfig_FLOAT(1.0f);

    @ZPVarDefinition(description = "Chance multiplier for zombie throwing objects.")
    public static final ZPConfig_FLOAT ZOMBIE_THROW_A_GIFT_CHANCE_MULTIPLIER =
            new ZPConfig_FLOAT(1.0f);

    @ZPVarDefinition(description = "Global zombie max health multiplier.")
    public static final ZPConfig_FLOAT ZOMBIE_MAX_HEALTH_MULTIPLIER =
            new ZPConfig_FLOAT(1.0f);

    @ZPVarDefinition(description = "Global zombie movement speed multiplier.")
    public static final ZPConfig_FLOAT ZOMBIE_MOVEMENT_SPEED_MULTIPLIER =
            new ZPConfig_FLOAT(1.0f);

    @ZPVarDefinition(description = "Global zombie attack damage multiplier.")
    public static final ZPConfig_FLOAT ZOMBIE_ATTACK_DAMAGE_MULTIPLIER =
            new ZPConfig_FLOAT(1.0f);

    @ZPVarDefinition(description = "Chance multiplier for plague effect on hit.")
    public static final ZPConfig_FLOAT ZOMBIE_PLAGUE_EFFECT_CHANCE_MULTIPLIER =
            new ZPConfig_FLOAT(1.0f);

    @ZPVarDefinition(description = "Max follow range of zombies.")
    public static final ZPConfig_FLOAT ZOMBIE_FOLLOW_RANGE =
            new ZPConfig_FLOAT(48.0f);

    @ZPVarDefinition(description = "Angry persistence time in ticks.")
    public static final ZPConfig_INT ZOMBIE_MAX_ANGRY_PERSISTENCE_TICKS =
            new ZPConfig_INT(1800);

    @ZPVarDefinition(description = "Stop despawn if zombie has loot.")
    public static final ZPConfig_BOOL ZOMBIE_STOP_DESPAWNING_IF_HAS_IMPORTANT_LOOT =
            new ZPConfig_BOOL(true);

    @ZPVarDefinition(description = "X-ray look distance multiplier.")
    public static final ZPConfig_FLOAT ZOMBIE_XRAY_LOOK_DIST_MULTIPLIER =
            new ZPConfig_FLOAT(1.0f);

    @ZPVarDefinition(description = "Enable zombie X-ray target tracing.")
    public static final ZPConfig_BOOL ZOMBIE_XRAY_LOOK =
            new ZPConfig_BOOL(true);

    @ZPVarDefinition(description = "Allow zombies to pick up loot.")
    public static final ZPConfig_BOOL ZOMBIE_CAN_PICK_UP_LOOT =
            new ZPConfig_BOOL(true);

    @ZPVarDefinition(description = "Zombie eating time in ticks.")
    public static final ZPConfig_INT ZOMBIE_EATING_TIME =
            new ZPConfig_INT(300);

    @ZPVarDefinition(description = "Throw cooldown multiplier.")
    public static final ZPConfig_FLOAT ZOMBIE_THROW_A_GIFT_TRY_COOLDOWN_MULTIPLIER =
            new ZPConfig_FLOAT(1.0f);

    @ZPVarDefinition(description = "Maximum mining height.")
    public static final ZPConfig_FLOAT ZOMBIE_MAX_MINING_HEIGHT =
            new ZPConfig_FLOAT(9999999.0f);

    @ZPVarDefinition(description = "Minimum mining height.")
    public static final ZPConfig_FLOAT ZOMBIE_MIN_MINING_HEIGHT =
            new ZPConfig_FLOAT(-9999999.0f);

    @ZPVarDefinition(description = "Max zombies per chunk.")
    public static final ZPConfig_INT MAX_ZOMBIES_SPAWN_IN_CHUNK =
            new ZPConfig_INT(128);

    @ZPVarDefinition(description = "Block hardness multiplier for zombie mining.")
    public static final ZPConfig_FLOAT ZOMBIE_MINING_BLOCK_HARDNESS_MULTIPLIER =
            new ZPConfig_FLOAT(1.0f);

    @ZPVarDefinition(description = "Use shared zombie mining memory.")
    public static final ZPConfig_BOOL USE_ZOMBIE_MINING_SHARED_GLOBAL_MEM =
            new ZPConfig_BOOL(true);

    @ZPVarDefinition(description = "Short memory clear time.")
    public static final ZPConfig_INT TIME_TO_CLEAR_SHARED_ZOMBIE_MINING_SHORT_MEM =
            new ZPConfig_INT(600);

    @ZPVarDefinition(description = "Long memory clear time.")
    public static final ZPConfig_INT TIME_TO_CLEAR_SHARED_ZOMBIE_MINING_LONG_MEM =
            new ZPConfig_INT(3600);

    @ZPVarDefinition(description = "Zombie hand mining reach.")
    public static final ZPConfig_FLOAT ZOMBIE_HANDS_LENGTH_FOR_MINING =
            new ZPConfig_FLOAT(2.0f);

    @ZPVarDefinition(description = "Common zombie textures count.")
    public static final ZPConfig_INT TOTAL_COMMON_ZOMBIE_TEXTURES =
            new ZPConfig_INT(353);

    @ZPVarDefinition(description = "Dog zombie textures count.")
    public static final ZPConfig_INT TOTAL_DOG_ZOMBIE_TEXTURES =
            new ZPConfig_INT(4);

    @ZPVarDefinition(description = "Miner zombie textures count.")
    public static final ZPConfig_INT TOTAL_MINER_ZOMBIE_TEXTURES =
            new ZPConfig_INT(6);
}