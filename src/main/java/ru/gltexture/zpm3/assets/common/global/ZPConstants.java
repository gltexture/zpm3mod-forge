package ru.gltexture.zpm3.assets.common.global;

import ru.gltexture.zpm3.engine.core.config.ZPConfigurableConstant;
import ru.gltexture.zpm3.engine.core.config.ZPConfigurator;

public  class ZPConstants implements ZPConfigurator.ZPClassWithConfConstants {
    public ZPConstants() {
    }

    public static final String GROUP_ZOMBIE = "Zombie";
    public static final String GROUP_COMBAT = "Combat";
    public static final String GROUP_NETWORK = "Network";
    public static final String GROUP_WORLD = "World";
    public static final String GROUP_CLIENT = "Client";

    // ===== ZOMBIE =====

    @ZPConfigurableConstant(
            description = "Maximum radius within which a zombie can call other zombies for assistance.",
            group = ZPConstants.GROUP_ZOMBIE,
            type = ZPConfigurableConstant.TYPES.FLOAT
    )
    public static float MAX_RADIUS_ZOMBIE_CAN_CALL_FOR_HELP = 16.0f;

    @ZPConfigurableConstant(
            description = "Maximum number of zombies that can simultaneously target a single player.",
            group = ZPConstants.GROUP_ZOMBIE,
            type = ZPConfigurableConstant.TYPES.INT
    )
    public static int MAX_ZOMBIES_CAN_BE_TARGETED_ON_PLAYER = 2;

    @ZPConfigurableConstant(
            description = "Maximum number of zombies a single zombie can call for assistance when engaging a target.",
            group = ZPConstants.GROUP_ZOMBIE,
            type = ZPConfigurableConstant.TYPES.INT
    )
    public static int MAX_ENTITIES_ZOMBIE_CAN_CALL_TO_HELP = 3;

    @ZPConfigurableConstant(
            description = "Increases the interval between zombie path updates; higher values reduce CPU load but make zombie AI less responsive.",
            group = ZPConstants.GROUP_ZOMBIE,
            type = ZPConfigurableConstant.TYPES.FLOAT,
            min = 0.1,
            max = 3.0
    )
    public static float ZOMBIE_PATH_UPDATE_COOLDOWN_PUNISHMENT_GRADE = 1.75f;

    @ZPConfigurableConstant(
            description = "Infection duration in ticks applied by a zombie to the player when attacking. During this period the Zombie Plague effect remains active, weakening the target over time.",
            group = ZPConstants.GROUP_ZOMBIE,
            type = ZPConfigurableConstant.TYPES.INT
    )
    public static int ZOMBIE_PLAGUE_VIRUS_EFFECT_TIME_TICKS = 20 * (20 * 60);

    @ZPConfigurableConstant(
            description = "Multiplier of firearm projectile damage dealt to zombies.",
            group = ZPConstants.GROUP_ZOMBIE,
            type = ZPConfigurableConstant.TYPES.FLOAT,
            min = 0.0,
            max = 1.0
    )
    public static float ZOMBIE_APPLY_NEGATIVE_EFFECT_ON_ENTITY_CHANCE_MULTIPLIER = 1.0f;

    @ZPConfigurableConstant(
            description = "Base chance that a zombie will throw trash or a projectile at a nearby entity based on game logic calculation.",
            group = ZPConstants.GROUP_ZOMBIE,
            type = ZPConfigurableConstant.TYPES.FLOAT,
            min = 0.0,
            max = 1.0
    )
    public static float ZOMBIE_THROW_A_GIFT_CHANCE = 1.0f;

    @ZPConfigurableConstant(
            description = "Multiplier of firearm projectile damage inflicted on zombies. 3.0 = triple damage compared to the base bullet damage value.",
            group = ZPConstants.GROUP_ZOMBIE,
            type = ZPConfigurableConstant.TYPES.FLOAT
    )
    public static float ZOMBIE_BULLET_DAMAGE_MULTIPLIER = 3.0f;

    @ZPConfigurableConstant(
            description = "Default attack reach distance for zombies measured in blocks.",
            group = ZPConstants.GROUP_ZOMBIE,
            type = ZPConfigurableConstant.TYPES.FLOAT
    )
    public static float ZOMBIE_DEFAULT_HAND_REACH = 4.0f;

    @ZPConfigurableConstant(
            description = "Maximum distance in blocks that a zombie can follow a previously detected target. Detection radius is always 25–35% smaller than this value to avoid unrealistic pursuit behavior.",
            group = ZPConstants.GROUP_ZOMBIE,
            type = ZPConfigurableConstant.TYPES.FLOAT
    )
    public static float ZOMBIE_FOLLOW_RANGE = 48.0f;

    @ZPConfigurableConstant(
            description = "Multiplier that increases the chance of infection effects being applied by zombies (0.0–1.0 range). A value of 1.0 means 100% of standard infection probability logic.",
            group = ZPConstants.GROUP_ZOMBIE,
            type = ZPConfigurableConstant.TYPES.FLOAT,
            min = 0.0,
            max = 1.0
    )
    public static float ZOMBIE_PLAGUE_EFFECT_CHANCE_MULTIPLIER = 1.0f;

    @ZPConfigurableConstant(
            description = "Duration in ticks during which an enraged zombie remains persistent and cannot despawn, preventing mob unloading when the target is lost briefly or leaves visibility.",
            group = ZPConstants.GROUP_ZOMBIE,
            type = ZPConfigurableConstant.TYPES.INT
    )
    public static int ZOMBIE_MAX_ANGRY_PERSISTENCE_TICKS = 1200;

    @ZPConfigurableConstant(
            description = "If enabled, a zombie carrying important loot will not despawn, ensuring rare drop persistence and preventing loss on chunk unload.",
            group = ZPConstants.GROUP_ZOMBIE,
            type = ZPConfigurableConstant.TYPES.BOOLEAN
    )
    public static boolean ZOMBIE_STOP_DESPAWNING_IF_HAS_IMPORTANT_LOOT = true;

    @ZPConfigurableConstant(
            description = "If enabled, zombies use X-ray target tracing through obstacles when following an enraged target. Does not affect detection radius.",
            group = ZPConstants.GROUP_ZOMBIE,
            type = ZPConfigurableConstant.TYPES.BOOLEAN
    )
    public static boolean ZOMBIE_XRAY_LOOK = true;

    @ZPConfigurableConstant(
            description = "Allows zombies to pick up loot from the ground when enabled.",
            group = ZPConstants.GROUP_ZOMBIE,
            type = ZPConfigurableConstant.TYPES.BOOLEAN
    )
    public static boolean ZOMBIE_CAN_PICK_UP_LOOT = true;

    @ZPConfigurableConstant(
            description = "Eating duration for a zombie in ticks.",
            group = ZPConstants.GROUP_ZOMBIE,
            type = ZPConfigurableConstant.TYPES.INT
    )
    public static int ZOMBIE_EATING_TIME = 200;

    @ZPConfigurableConstant(
            description = "Cooldown interval in ticks between zombie attempts to throw trash or a projectile at a nearby entity.",
            group = ZPConstants.GROUP_ZOMBIE,
            type = ZPConfigurableConstant.TYPES.INT
    )
    public static int ZOMBIE_THROW_A_GIFT_TRY_DEFAULT_COOLDOWN = 300;

    @ZPConfigurableConstant(
            description = "Maximum height of zombie's mining system.",
            group = ZPConstants.GROUP_ZOMBIE,
            type = ZPConfigurableConstant.TYPES.FLOAT
    )
    public static float ZOMBIE_MAX_MINING_HEIGHT = 9999999.0f;

    @ZPConfigurableConstant(
            description = "Minimum height of zombie's mining system.",
            group = ZPConstants.GROUP_ZOMBIE,
            type = ZPConfigurableConstant.TYPES.FLOAT
    )
    public static float ZOMBIE_MIN_MINING_HEIGHT = -9999999.0f;

    @ZPConfigurableConstant(
            description = "Maximum number of zombies allowed in one chunk to limit overcrowding and AI performance load.",
            group = ZPConstants.GROUP_ZOMBIE,
            type = ZPConfigurableConstant.TYPES.INT
    )
    public static int MAX_ZOMBIES_IN_CHUNK = 120;

    @ZPConfigurableConstant(
            description = "Block hardness multiplier applied when zombies mine or break blocks.",
            group = ZPConstants.GROUP_ZOMBIE,
            type = ZPConfigurableConstant.TYPES.FLOAT
    )
    public static float ZOMBIE_MINING_BLOCK_HARDNESS_MULTIPLIER = 1.0f;

    @ZPConfigurableConstant(
            description = "If enabled, zombies share global block destruction memory (short + long), affecting cooperative block breaking logic. If disabled, each zombie mines independently (classic system).",
            group = ZPConstants.GROUP_ZOMBIE,
            type = ZPConfigurableConstant.TYPES.BOOLEAN
    )
    public static boolean USE_ZOMBIE_MINING_SHARED_GLOBAL_MEM = true;

    @ZPConfigurableConstant(
            description = "Time in ticks after which a zombie hit partially stored block damage (short memory) is reset.",
            group = ZPConstants.GROUP_ZOMBIE,
            type = ZPConfigurableConstant.TYPES.INT
    )
    public static int TIME_TO_CLEAR_SHARED_ZOMBIE_MINING_SHORT_MEM = 600;

    @ZPConfigurableConstant(
            description = "Time in ticks after which long-term stored zombie block damage memory is cleared. Typically used for persistent environment destruction data.",
            group = ZPConstants.GROUP_ZOMBIE,
            type = ZPConfigurableConstant.TYPES.INT
    )
    public static int TIME_TO_CLEAR_SHARED_ZOMBIE_MINING_LONG_MEM = 3600;

    @ZPConfigurableConstant(
            description = "Zombie hand reach length used specifically for mining path calculations measured in blocks.",
            group = ZPConstants.GROUP_ZOMBIE,
            type = ZPConfigurableConstant.TYPES.FLOAT
    )
    public static float ZOMBIE_HANDS_LENGTH_FOR_MINING = 2.0f;

    @ZPConfigurableConstant(
            description = "Total number of common zombie texture variations registered in the game.",
            group = ZPConstants.GROUP_ZOMBIE,
            type = ZPConfigurableConstant.TYPES.INT
    )
    public static int TOTAL_COMMON_ZOMBIE_TEXTURES = 353;

    @ZPConfigurableConstant(
            description = "Total number of dog-zombie texture variations registered in the game.",
            group = ZPConstants.GROUP_ZOMBIE,
            type = ZPConfigurableConstant.TYPES.INT
    )
    public static int TOTAL_DOG_ZOMBIE_TEXTURES = 4;

    @ZPConfigurableConstant(
            description = "Total number of miner-zombie texture variations registered in the game.",
            group = ZPConstants.GROUP_ZOMBIE,
            type = ZPConfigurableConstant.TYPES.INT
    )
    public static int TOTAL_MINER_ZOMBIE_TEXTURES = 6;


    // ===== PLAYER =====
    @ZPConfigurableConstant(
            description = "Default hand reach for players measured in blocks used for melee interaction logic.",
            group = ZPConstants.GROUP_COMBAT,
            type = ZPConfigurableConstant.TYPES.FLOAT
    )
    public static float PLAYER_DEFAULT_HAND_REACH = 2.0f;


    // ===== COMBAT / DAMAGE / ANTI-LAG =====
    @ZPConfigurableConstant(
            description = "Acid inventory damage tick rate.",
            group = ZPConstants.GROUP_COMBAT,
            type = ZPConfigurableConstant.TYPES.INT
    )
    public static int ACID_DAMAGE_TICK_RATE = 4;

    @ZPConfigurableConstant(
            description = "Acid bottle debuff duration in ticks applied when hitting an entity.",
            group = ZPConstants.GROUP_COMBAT,
            type = ZPConfigurableConstant.TYPES.INT
    )
    public static int ACID_BOTTLE_AFFECT_TIME = 100;

    @ZPConfigurableConstant(
            description = "Base damage dealt on inventory items by an acid bottle projectile when hitting an entity.",
            group = ZPConstants.GROUP_COMBAT,
            type = ZPConfigurableConstant.TYPES.FLOAT
    )
    public static float ACID_BOTTLE_DAMAGE = 2.0f;

    @ZPConfigurableConstant(
            description = "Base damage value for a thrown plate item when hitting an entity.",
            group = ZPConstants.GROUP_COMBAT,
            type = ZPConfigurableConstant.TYPES.FLOAT
    )
    public static float PLATE_DAMAGE = 1.0f;

    @ZPConfigurableConstant(
            description = "Base damage value for a thrown brick item when hitting an entity.",
            group = ZPConstants.GROUP_COMBAT,
            type = ZPConfigurableConstant.TYPES.FLOAT
    )
    public static float BRICK_DAMAGE = 3.0f;

    @ZPConfigurableConstant(
            description = "Damage dealt by rotten flesh throwable when hitting an entity.",
            group = ZPConstants.GROUP_COMBAT,
            type = ZPConfigurableConstant.TYPES.FLOAT
    )
    public static float ROTTEN_FLESH_DAMAGE = 2.0f;

    @ZPConfigurableConstant(
            description = "Base damage value for a thrown rock item when hitting an entity.",
            group = ZPConstants.GROUP_COMBAT,
            type = ZPConfigurableConstant.TYPES.FLOAT
    )
    public static float ROCK_DAMAGE = 6.0f;

    @ZPConfigurableConstant(
            description = "Number of ticks entity AABB hitbox data is stored in anti-lag memory for collision interpolation and hit validation. Higher values improve lag compensation but increase memory cost.",
            group = ZPConstants.GROUP_COMBAT,
            type = ZPConfigurableConstant.TYPES.INT
    )
    public static int ENTITY_MAX_AABB_MEMORY_ANTILAG = 20;

    @ZPConfigurableConstant(
            description = "Max number of times one bullet raycast can register block hits before stopping penetration calculations.",
            group = ZPConstants.GROUP_COMBAT,
            type = ZPConfigurableConstant.TYPES.INT
    )
    public static int MAX_BULLET_BLOCK_HITS = 3;


    // ===== NETWORK =====
    @ZPConfigurableConstant(
            description = "Frequency in ticks at which the player ping packet is sent from client to server for latency tracking.",
            group = ZPConstants.GROUP_NETWORK,
            type = ZPConfigurableConstant.TYPES.INT
    )
    public static int PLAYER_PING_PACKET_FREQ = 20;

    @ZPConfigurableConstant(
            description = "Maximum distance in blocks at which nearby players receive gun fire action packets (shoot events, reload events, animation triggers).",
            group = ZPConstants.GROUP_NETWORK,
            type = ZPConfigurableConstant.TYPES.FLOAT
    )
    public static float GUN_ACTION_PACKET_RANGE = 256.0f;

    @ZPConfigurableConstant(
            description = "Maximum distance in blocks at which players receive bullet hit result packets (raycast impact, blood effect spawn, entity hit validation).",
            group = ZPConstants.GROUP_NETWORK,
            type = ZPConfigurableConstant.TYPES.FLOAT
    )
    public static float BULLET_HIT_PACKET_RANGE = 128.0f;


    // ===== GAMEPLAY =====
    @ZPConfigurableConstant(
            description = "Multiplier that increases the chance of limb fracture when an attack lands. 1.0 = default fracture logic probability. 0 disables fractures.",
            group = ZPConstants.GROUP_COMBAT,
            type = ZPConfigurableConstant.TYPES.FLOAT,
            min = 0.0,
            max = 1.0
    )
    public static float FRACTURE_CHANCE_MULTIPLIER = 1.0f;

    @ZPConfigurableConstant(
            description = "Multiplier, that used to reduce damage by armor on entity.",
            group = ZPConstants.GROUP_COMBAT,
            type = ZPConfigurableConstant.TYPES.FLOAT,
            min = 0.0,
            max = 1.0
    )
    public static float ARMOR_BULLET_DAMAGE_REDUCTION_MULTIPLIER = 0.75f;

    @ZPConfigurableConstant(
            description = "If enabled, only players can receive bleeding debuffs from attacks. Zombies and other instances are immune.",
            group = ZPConstants.GROUP_COMBAT,
            type = ZPConfigurableConstant.TYPES.BOOLEAN
    )
    public static boolean BLEEDING_ONLY_FOR_PLAYERS = false;

    @ZPConfigurableConstant(
            description = "Multiplier for bleeding chance calculation. 1.0 = default. 0 disables bleeding. 5.0 = 5× more likely to inflict bleeding.",
            group = ZPConstants.GROUP_COMBAT,
            type = ZPConfigurableConstant.TYPES.FLOAT,
            min = 0.0,
            max = 5.0
    )
    public static float BLEEDING_CHANCE_MULTIPLIER = 1.0f;

    @ZPConfigurableConstant(
            description = "Path-update (NOT PATHGEN) algorithm. 0=Vanilla 1=Rewrote.",
            group = ZPConstants.GROUP_WORLD,
            type = ZPConfigurableConstant.TYPES.INT,
            min = 0.0,
            max = 1.0
    )
    public static int ZP_PATH_UPDATER_ALG = 1;

    @ZPConfigurableConstant(
            description = "Night time progression divider. During night, the world time is incremented once every N server ticks. Higher values slow down night duration.",
            group = ZPConstants.GROUP_WORLD,
            type = ZPConfigurableConstant.TYPES.INT,
            min = 1.0,
            max = Float.MAX_VALUE
    )
    public static int WORLD_NIGHT_TIME_SLOWDOWN_CYCLE_TICKING = 2;

    @ZPConfigurableConstant(
            description = "Day time progression divider. During day, the world time is incremented once every N server ticks. Higher values slow down day duration.",
            group = ZPConstants.GROUP_WORLD,
            type = ZPConfigurableConstant.TYPES.INT,
            min = 1.0,
            max = Float.MAX_VALUE
    )
    public static int WORLD_DAY_TIME_SLOWDOWN_CYCLE_TICKING = 2;

    @ZPConfigurableConstant(
            description = "New vanilla's concrete destroy speed.",
            group = ZPConstants.GROUP_WORLD,
            type = ZPConfigurableConstant.TYPES.FLOAT,
            min = 0.0
    )
    public static float ZP_VANILLA_CONCRETE_DESTROY_SPEED = 300.0f;

    @ZPConfigurableConstant(
            description = "New entity-item lifespan.",
            group = ZPConstants.GROUP_WORLD,
            type = ZPConfigurableConstant.TYPES.INT,
            min = 0.0,
            max = Float.MAX_VALUE
    )
    public static int ENTITY_ITEM_LIFESPAN = 16000;

    @ZPConfigurableConstant(
            description = "Enables item pickup via the <key>. Works on both client and server. If disabled on the server, the feature is forcibly disabled on all clients.",
            group = ZPConstants.GROUP_WORLD,
            type = ZPConfigurableConstant.TYPES.BOOLEAN
    )
    public static boolean PICK_UP_ON_F = true;

    @ZPConfigurableConstant(
            description = "If enabled, server applies extreme darkness shader gamma reduction for all players regardless of client settings.",
            group = ZPConstants.GROUP_WORLD,
            type = ZPConfigurableConstant.TYPES.BOOLEAN
    )
    public static boolean ENABLE_HARDCORE_DARKNESS_SERVER_SIDE = true;

    @ZPConfigurableConstant(
            description = "Static gamma factor applied to players when hardcore darkness is enabled. Negative values darken the world, positive values brighten it.",
            group = ZPConstants.GROUP_WORLD,
            type = ZPConfigurableConstant.TYPES.FLOAT,
            min = -1.0,
            max = 1.0
    )
    public static float DARKNESS_GAMMA_STATIC_FACTOR_SERVER_SIDE = -0.45f;

    @ZPConfigurableConstant(
            description = "Multiplier for block breaking power of thrown items (plates, bricks, rocks, etc.) when interacting with destructible world objects.",
            group = ZPConstants.GROUP_WORLD,
            type = ZPConfigurableConstant.TYPES.FLOAT
    )
    public static float THROWABLES_BLOCK_BREAK_MULTIPLIER = 1.0f;

    @ZPConfigurableConstant(
            description = "Send a net-packet headshot and bullet entity-hit effects (blood + sound).",
            group = ZPConstants.GROUP_COMBAT,
            type = ZPConfigurableConstant.TYPES.BOOLEAN
    )
    public static boolean SEND_PACKET_ABOUT_BULLET_ENTITY_HIT = true;

    @ZPConfigurableConstant(
            description = "Bonus damage, caused by headshot.",
            group = ZPConstants.GROUP_COMBAT,
            type = ZPConfigurableConstant.TYPES.FLOAT
    )
    public static float BULLET_HEADSHOT_BONUS_DAMAGE = 2.0f;

    @ZPConfigurableConstant(
            description = "Cooldown in ticks before a medicine item can be used again. Prevents spamming heals or cures.",
            group = ZPConstants.GROUP_COMBAT,
            type = ZPConfigurableConstant.TYPES.INT
    )
    public static int MEDICINE_USE_COOLDOWN = 60;

    @ZPConfigurableConstant(
            description = "If enabled, bullets are capable of breaking specific marked blocks if the block has the destructible flag.",
            group = ZPConstants.GROUP_WORLD,
            type = ZPConfigurableConstant.TYPES.BOOLEAN
    )
    public static boolean CAN_BULLET_BREAK_BLOCK = true;

    @ZPConfigurableConstant(
            description = "Maximum block's hardness, that can be broken by bullet.",
            group = ZPConstants.GROUP_COMBAT,
            type = ZPConfigurableConstant.TYPES.FLOAT
    )
    public static float MAX_BULLET_HIT_BLOCK_HARDNESS = 1.0f;

    // ===== WORLD / ENVIRONMENT =====

    @ZPConfigurableConstant(
            description = "Increases cooking time. References to all vanilla crafting blocks (furnace, campfire etc.).",
            group = ZPConstants.GROUP_WORLD,
            type = ZPConfigurableConstant.TYPES.FLOAT
    )
    public static float ZP_COOKING_TIME_ALL_CRAFTING_BLOCKS_MULTIPLIER = 2.0f;

    @ZPConfigurableConstant(
            description = "Duration in ticks before a placed torch light source fades.",
            group = ZPConstants.GROUP_WORLD,
            type = ZPConfigurableConstant.TYPES.INT
    )
    public static int TORCH_FADING_TIME = 12000;

    @ZPConfigurableConstant(
            description = "Duration in ticks before a placed pumpkin light source fades and is removed.",
            group = ZPConstants.GROUP_WORLD,
            type = ZPConfigurableConstant.TYPES.INT
    )
    public static int PUMPKIN_FADING_TIME = 24000;

    @ZPConfigurableConstant(
            description = "Duration in ticks before a lava turns into stone.",
            group = ZPConstants.GROUP_WORLD,
            type = ZPConfigurableConstant.TYPES.INT
    )
    public static int LAVA_FADING_TIME = 3600;

    @ZPConfigurableConstant(
            description = "Allows torches to fade over time when enabled.",
            group = ZPConstants.GROUP_WORLD,
            type = ZPConfigurableConstant.TYPES.BOOLEAN
    )
    public static boolean FADING_TORCHES = true;

    @ZPConfigurableConstant(
            description = "Allows pumpkins to fade over time when enabled.",
            group = ZPConstants.GROUP_WORLD,
            type = ZPConfigurableConstant.TYPES.BOOLEAN
    )
    public static boolean FADING_PUMPKINS = true;

    @ZPConfigurableConstant(
            description = "Allows lava fade over time when enabled.",
            group = ZPConstants.GROUP_WORLD,
            type = ZPConfigurableConstant.TYPES.BOOLEAN
    )
    public static boolean FADING_LAVAS = true;


    // ===== THROWABLE ITEMS PLAYER DEFAULTS =====
    @ZPConfigurableConstant(
            description = "Default throw velocity applied to items when thrown by players.",
            group = ZPConstants.GROUP_COMBAT,
            type = ZPConfigurableConstant.TYPES.FLOAT
    )
    public static float ITEMS_THROW_VELOCITY = 1.2f;

    @ZPConfigurableConstant(
            description = "Default inaccuracy angle applied to items when thrown by players.",
            group = ZPConstants.GROUP_COMBAT,
            type = ZPConfigurableConstant.TYPES.FLOAT
    )
    public static float ITEMS_THROW_INACCURACY = 8.0f;

    @ZPConfigurableConstant(
            description = "Cooldown in ticks between item throws by a player.",
            group = ZPConstants.GROUP_COMBAT,
            type = ZPConfigurableConstant.TYPES.INT
    )
    public static int ITEMS_THROW_COOLDOWN = 20;

    // ===== CLIENT =====

    @ZPConfigurableConstant(
            description = "(ONLY CLIENT) Fancy ZP item-entity animation.",
            group = ZPConstants.GROUP_CLIENT,
            type = ZPConfigurableConstant.TYPES.BOOLEAN
    )
    public static boolean FANCY_ITEM_ENTITIES = true;

    @ZPConfigurableConstant(
            description = "(ONLY CLIENT) Fob Item Scaling.",
            group = ZPConstants.GROUP_CLIENT,
            type = ZPConfigurableConstant.TYPES.BOOLEAN
    )
    public static boolean FOV_ITEM_SCALING = true;

    @Override
    public String configName() {
        return "default_zp3_ver1";
    }
}