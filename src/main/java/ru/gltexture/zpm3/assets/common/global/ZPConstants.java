package ru.gltexture.zpm3.assets.common.global;

public abstract class ZPConstants {
    public static int ZOMBIE_PLAGUE_VIRUS_EFFECT_TIME_TICKS = 20 * (20 * 60);
    public static final float ZOMBIE_BULLET_DAMAGE_MULTIPLIER = 3.0f;

    public static final float PLAYER_DEFAULT_HAND_REACH = 2.0f;
    public static final float ZOMBIE_DEFAULT_HAND_REACH = 4.0f;
    public static final float FRACTURE_CHANCE_MULTIPLIER = 1.0f;
    public static final float ZOMBIE_FOLLOW_RANGE = 48.0f;
    public static final float ZOMBIE_PLAGUE_EFFECT_CHANCE_MULTIPLIER = 1.0f;

    public static boolean BLEEDING_ONLY_FOR_PLAYERS = false;
    public static float BLEEDING_CHANCE_MULTIPLIER = 1.0f;

    public static boolean ENABLE_HARDCORE_DARKNESS_SERVER_SIDE = true;
    public static float DARKNESS_GAMMA_STATIC_FACTOR_SERVER_SIDE = -0.45f;

    public static final int ZOMBIE_MAX_ANGRY_PERSISTENCE_TICKS = 1200;
    public static final int ZOMBIE_EATING_TIME = 200;
    public static final float THROWABLES_BLOCK_BREAK_MULTIPLIER = 1.0f;
    public static final int ZOMBIE_THROW_A_GIFT_TRY_DEFAULT_COOLDOWN = 300;
    public static final int MAX_ZOMBIES_IN_CHUNK = 140;
    public static float ZOMBIE_MINING_BLOCK_HARDNESS_MULTIPLIER = 1.0f;
    public static boolean USE_ZOMBIE_MINING_SHARED_MEM = true;
    public static int TIME_TO_CLEAR_SHARED_ZOMBIE_MINING_SHORT_MEM = 600;
    public static int TIME_TO_CLEAR_SHARED_ZOMBIE_MINING_LONG_MEM = 3600;

    public static float ZOMBIE_HANDS_LENGTH_FOR_MINING = 2.0f;
    public static int TOTAL_COMMON_ZOMBIE_TEXTURES = 353;
    public static int TOTAL_DOG_ZOMBIE_TEXTURES = 4;
    public static int TOTAL_MINER_ZOMBIE_TEXTURES = 6;

    public static final int PLAYER_PING_PACKET_FREQ = 20;
    public static int DEFAULT_ITEMS_THROW_COOLDOWN = 20;
    public static float DEFAULT_ITEMS_THROW_VELOCITY = 1.2f;
    public static float DEFAULT_ITEMS_THROW_INACCURACY = 8.0f;

    public static int DEFAULT_ACID_BOTTLE_AFFECT_TIME = 100;

    public static float DEFAULT_ACID_BOTTLE_DAMAGE = 2.0f;
    public static float DEFAULT_PLATE_DAMAGE = 1.0f;
    public static float DEFAULT_BRICK_DAMAGE = 3.0f;
    public static float DEFAULT_ROTTEN_FLESH_DAMAGE = 2.0f;
    public static float DEFAULT_ROCK_DAMAGE = 6.0f;

    public static float DEFAULT_ACID_BOTTLE_PACKET_RANGE = 128.0f;
    public static float DEFAULT_GUN_ACTION_PACKET_RANGE = 256.0f;
    public static float DEFAULT_BULLET_HIT_PACKET_RANGE = 256.0f;
    public static int MAX_BULLET_BLOCK_HITS = 3;
    public static int ENTITY_MAX_AABB_MEMORY_ANTILAG = 20;

    public static int ACID_DAMAGE_TICK_RATE = 4;

    public static int TORCH_FADING_TIME = 12000;
    public static int PUMPKIN_FADING_TIME = 12000;
    public static int LAVA_FADING_TIME = 3600;

    public static int MEDICINE_COOLDOWN_USE = 60;

    public static boolean FADING_TORCHES = true;
    public static boolean FADING_PUMPKINS = true;
    public static boolean FADING_LAVAS = true;
}