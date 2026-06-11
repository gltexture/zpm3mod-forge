package ru.gltexture.zpm3.engine.core.config.builtin;

import ru.gltexture.zpm3.engine.core.config.ZPConfigConstantsClass;
import ru.gltexture.zpm3.engine.core.config.vars.*;

public class ZPWorldConfig implements ZPConfigConstantsClass {

    @ZPVarDefinition(description = "Black list of blocks, that cannot be damaged and destroyed. Example: 'minecraft:grass;minecraft:stone'")
    public static final ZPConfig_STRING GLOBAL_BLOCK_DAMAGE_MEMORY_BLACKLIST =
            new ZPConfig_STRING("");

    @ZPVarDefinition(description = "Prevents fading lava or acid when placed in Creative mode.")
    public static final ZPConfig_BOOL SKIP_FADE_TICKING_LAVA_ACID_PLACED_IN_CREATIVE =
            new ZPConfig_BOOL(true);

    @ZPVarDefinition(description = "Prevents fading torches and pumpkins when placed in Creative mode.")
    public static final ZPConfig_BOOL SKIP_FADE_TICKING_TORCHES_PUMPKINS_PLACED_IN_CREATIVE =
            new ZPConfig_BOOL(true);

    @ZPVarDefinition(description = "How much internal damage can receive barbared wire, before it breaks.")
    public static final ZPConfig_INT MAX_BARBARED_WIRE_STRENGTH =
            new ZPConfig_INT(128);

    @ZPVarDefinition(description = "Tries to make acid block destruction mechanic smoother (less value = less destruction).")
    public static final ZPConfig_INT ACID_BLOCK_DESTRUCTION_CONSTRAINT =
            new ZPConfig_INT(6);

    @ZPVarDefinition(description = "Base block damage of acid block.")
    public static final ZPConfig_FLOAT ACID_BLOCK_BASE_BLOCK_DAMAGE =
            new ZPConfig_FLOAT(1.0f);

    @ZPVarDefinition(description = "Night time progression divider. During night, the world time is incremented once every N server ticks. Higher values slow down night duration.")
    public static final ZPConfig_INT WORLD_NIGHT_SLOWDOWN_CYCLE_TICKING =
            new ZPConfig_INT(2, 1, Integer.MAX_VALUE);

    @ZPVarDefinition(description = "Day time progression divider. During day, the world time is incremented once every N server ticks. Higher values slow down day duration.")
    public static final ZPConfig_INT WORLD_DAY_SLOWDOWN_CYCLE_TICKING =
            new ZPConfig_INT(3, 1, Integer.MAX_VALUE);

    @ZPVarDefinition(description = "New vanilla concrete destroy speed.")
    public static final ZPConfig_FLOAT ZP_VANILLA_CONCRETE_DESTROY_SPEED =
            new ZPConfig_FLOAT(256.0f, 0.0f, Float.MAX_VALUE);

    @ZPVarDefinition(description = "New entity-item lifespan in ticks.")
    public static final ZPConfig_INT ENTITY_ITEM_LIFESPAN =
            new ZPConfig_INT(16000, 0, Integer.MAX_VALUE);

    @ZPVarDefinition(description = "Enables item pickup via the <key>. Works on both client and server. If disabled on the server, the feature is forcibly disabled on all clients.")
    public static final ZPConfig_BOOL ALLOW_ITEMS_PICKING_ON_KEY =
            new ZPConfig_BOOL(true);

    @ZPVarDefinition(description = "If enabled, server applies extreme darkness shader gamma reduction for all players regardless of client settings.")
    public static final ZPConfig_BOOL ENABLE_HARDCORE_DARKNESS_SERVER_SIDE =
            new ZPConfig_BOOL(true);

    @ZPVarDefinition(description = "Static gamma factor applied to players when hardcore darkness is enabled. Negative values darken the world, positive values brighten it.")
    public static final ZPConfig_FLOAT DARKNESS_GAMMA_STATIC_FACTOR_SERVER_SIDE =
            new ZPConfig_FLOAT(-0.5f, -1.0f, 1.0f);

    @ZPVarDefinition(description = "Increases cooking time. Affects all vanilla crafting blocks (furnace, campfire, etc.).")
    public static final ZPConfig_FLOAT ZP_COOKING_TIME_MULTIPLIER =
            new ZPConfig_FLOAT(3.0f);

    @ZPVarDefinition(description = "Duration in ticks before a placed torch light source fades.")
    public static final ZPConfig_INT TORCH_FADING_TIME =
            new ZPConfig_INT(12000);

    @ZPVarDefinition(description = "Duration in ticks before a placed pumpkin light source fades and is removed.")
    public static final ZPConfig_INT PUMPKIN_FADING_TIME =
            new ZPConfig_INT(24000);

    @ZPVarDefinition(description = "Duration in ticks before acid turns into sand.")
    public static final ZPConfig_INT ACID_FADING_TIME =
            new ZPConfig_INT(1200);

    @ZPVarDefinition(description = "Duration in ticks before lava turns into stone.")
    public static final ZPConfig_INT LAVA_FADING_TIME =
            new ZPConfig_INT(3600);

    @ZPVarDefinition(description = "Allows torches to fade over time when enabled.")
    public static final ZPConfig_BOOL FADING_TORCHES =
            new ZPConfig_BOOL(true);

    @ZPVarDefinition(description = "Allows pumpkins to fade over time when enabled.")
    public static final ZPConfig_BOOL FADING_PUMPKINS =
            new ZPConfig_BOOL(true);

    @ZPVarDefinition(description = "Allows acid to fade over time when enabled.")
    public static final ZPConfig_BOOL FADING_ACIDS =
            new ZPConfig_BOOL(true);

    @ZPVarDefinition(description = "Allows lava to fade over time when enabled.")
    public static final ZPConfig_BOOL FADING_LAVAS =
            new ZPConfig_BOOL(true);
}