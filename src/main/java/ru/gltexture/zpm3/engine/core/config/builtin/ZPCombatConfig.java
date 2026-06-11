package ru.gltexture.zpm3.engine.core.config.builtin;

import ru.gltexture.zpm3.engine.core.config.ZPConfigConstantsClass;
import ru.gltexture.zpm3.engine.core.config.vars.*;

public class ZPCombatConfig implements ZPConfigConstantsClass {

    @ZPVarDefinition(description = "Default hand reach for players measured in blocks used for melee interaction logic.")
    public static final ZPConfig_FLOAT PLAYER_DEFAULT_HAND_REACH_DISTANCE =
            new ZPConfig_FLOAT(2.375f);

    @ZPVarDefinition(description = "Multiplier used to reduce damage taken from bullets based on armor. 1.0 = no reduction, 0 = full reduction.")
    public static final ZPConfig_FLOAT ARMOR_BULLET_DAMAGE_REDUCTION_MULTIPLIER =
            new ZPConfig_FLOAT(0.75f, 0.0f, 1.0f);

    @ZPVarDefinition(description = "Multiplier that increases the chance of fracture. 1.0 = default fracture probability, 0 = fractures disabled.")
    public static final ZPConfig_FLOAT FRACTURE_CHANCE_MULTIPLIER =
            new ZPConfig_FLOAT(1.0f, 0.0f, 1.0f);

    @ZPVarDefinition(description = "Multiplier for the chance to inflict bleeding. 1.0 = default chance, 0 = disables bleeding, 5.0 = 5× more likely.")
    public static final ZPConfig_FLOAT BLEEDING_CHANCE_MULTIPLIER =
            new ZPConfig_FLOAT(1.0f, 0.0f, 5.0f);

    @ZPVarDefinition(description = "Acid inventory damage tick rate.")
    public static final ZPConfig_INT ACID_DAMAGE_TICK_RATE =
            new ZPConfig_INT(5);

    @ZPVarDefinition(description = "Duration in ticks for acid bottle debuff (inventory breaking + damage) applied when hitting an entity directly.")
    public static final ZPConfig_INT ACID_BOTTLE_DIRECT_HIT_AFFECT_TIME =
            new ZPConfig_INT(100);

    @ZPVarDefinition(description = "Acid bottle splash radius.")
    public static final ZPConfig_FLOAT ACID_BOTTLE_SPLASH_RADIUS =
            new ZPConfig_FLOAT(1.5f);

    @ZPVarDefinition(description = "Duration in ticks for acid bottle splash debuff (inventory breaking + damage). Set to 0 to disable splash effect.")
    public static final ZPConfig_INT ACID_BOTTLE_SPLASH_HIT_MAX_AFFECT_TIME =
            new ZPConfig_INT(80);

    @ZPVarDefinition(description = "Base damage dealt to entity on direct hit.")
    public static final ZPConfig_FLOAT ACID_BOTTLE_DAMAGE =
            new ZPConfig_FLOAT(2.0f);

    @ZPVarDefinition(description = "Base damage dealt to inventory items by acid.")
    public static final ZPConfig_INT ACID_INVENTORY_DAMAGE =
            new ZPConfig_INT(4);

    @ZPVarDefinition(description = "Base damage dealt by a thrown plate when hitting an entity.")
    public static final ZPConfig_FLOAT PLATE_DAMAGE =
            new ZPConfig_FLOAT(1.0f);

    @ZPVarDefinition(description = "Base damage dealt by a thrown brick when hitting an entity.")
    public static final ZPConfig_FLOAT BRICK_DAMAGE =
            new ZPConfig_FLOAT(3.0f);

    @ZPVarDefinition(description = "Damage dealt by thrown rotten flesh when hitting an entity.")
    public static final ZPConfig_FLOAT ROTTEN_FLESH_DAMAGE =
            new ZPConfig_FLOAT(2.0f);

    @ZPVarDefinition(description = "Base damage dealt by a thrown rock when hitting an entity.")
    public static final ZPConfig_FLOAT ROCK_DAMAGE =
            new ZPConfig_FLOAT(6.0f);

    @ZPVarDefinition(description = "Maximum number of times a bullet raycast can register block hits before stopping penetration calculations.")
    public static final ZPConfig_INT MAX_BULLET_BLOCK_HITS =
            new ZPConfig_INT(3);

    @ZPVarDefinition(description = "Bonus damage caused by a headshot.")
    public static final ZPConfig_FLOAT BULLET_HEADSHOT_BONUS_DAMAGE =
            new ZPConfig_FLOAT(2.0f);

    @ZPVarDefinition(description = "Cooldown in ticks before a medicine item can be used again, preventing spamming.")
    public static final ZPConfig_INT MEDICINE_USE_COOLDOWN =
            new ZPConfig_INT(60);

    @ZPVarDefinition(description = "Maximum block hardness that can be broken by a bullet.")
    public static final ZPConfig_FLOAT MAX_BULLET_HIT_BLOCK_HARDNESS =
            new ZPConfig_FLOAT(1.0f);

    @ZPVarDefinition(description = "Default throw velocity applied to items when thrown by players.")
    public static final ZPConfig_FLOAT ITEMS_THROW_VELOCITY =
            new ZPConfig_FLOAT(1.2f);

    @ZPVarDefinition(description = "Default inaccuracy angle applied to items when thrown by players.")
    public static final ZPConfig_FLOAT ITEMS_THROW_INACCURACY =
            new ZPConfig_FLOAT(8.0f);

    @ZPVarDefinition(description = "Cooldown in ticks between item throws by a player.")
    public static final ZPConfig_INT ITEMS_THROW_COOLDOWN =
            new ZPConfig_INT(20);

    @ZPVarDefinition(description = "Multiplier for block breaking power of thrown items (plates, bricks, rocks) when interacting with destructible world objects.")
    public static final ZPConfig_FLOAT THROWABLES_BLOCK_BREAK_MULTIPLIER =
            new ZPConfig_FLOAT(1.0f);

    @ZPVarDefinition(description = "Black list of blocks, that cannot be destroyed by bullet. Example: \"minecraft\\:grass;minecraft\\:stone\".")
    public static final ZPConfig_STRING BULLET_BLOCK_BREAKING_BLACKLIST =
            new ZPConfig_STRING("");

    @ZPVarDefinition(description = "If enabled, bullets can break specific marked blocks if the block has the destructible flag.")
    public static final ZPConfig_BOOL CAN_BULLET_BREAK_BLOCK =
            new ZPConfig_BOOL(true);
}