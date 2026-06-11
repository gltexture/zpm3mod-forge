package ru.gltexture.zpm3.engine.core.config.builtin;

import ru.gltexture.zpm3.engine.core.config.ZPConfigConstantsClass;
import ru.gltexture.zpm3.engine.core.config.vars.*;

public class ZPEntityConfig implements ZPConfigConstantsClass {

    @ZPVarDefinition(description = "Randomized poison effect, then fry food has been eaten.")
    public static final ZPConfig_FLOAT RANDOM_FRY_FOOD_POSIONING =
            new ZPConfig_FLOAT(1.0f);

    @ZPVarDefinition(description = "If enabled, only players can receive bleeding debuffs from attacks")
    public static final ZPConfig_BOOL BLEEDING_ONLY_FOR_PLAYERS =
            new ZPConfig_BOOL(false);

    @ZPVarDefinition(description = "Number of ticks entity AABB hitbox data is stored for anti-lag memory. Higher values improve lag compensation at the cost of memory.")
    public static final ZPConfig_INT ENTITY_MAX_AABB_MEMORY_ANTILAG =
            new ZPConfig_INT(20);
}