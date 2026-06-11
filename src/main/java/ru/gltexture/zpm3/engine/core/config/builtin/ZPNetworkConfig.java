package ru.gltexture.zpm3.engine.core.config.builtin;

import ru.gltexture.zpm3.engine.core.config.ZPConfigConstantsClass;
import ru.gltexture.zpm3.engine.core.config.vars.*;

public class ZPNetworkConfig implements ZPConfigConstantsClass {

    @ZPVarDefinition(description = "Frequency in ticks at which the player ping packet is sent for latency tracking.")
    public static final ZPConfig_INT PLAYER_PING_PACKET_FREQ =
            new ZPConfig_INT(20);

    @ZPVarDefinition(description = "Maximum distance in blocks at which nearby players receive gun fire action packets (shoot events, reload events, animations).")
    public static final ZPConfig_FLOAT GUN_ACTION_PACKET_RANGE =
            new ZPConfig_FLOAT(256.0f);

    @ZPVarDefinition(description = "Maximum distance in blocks at which players receive bullet hit result packets (raycast impact, blood, entity hit validation).")
    public static final ZPConfig_FLOAT BULLET_HIT_PACKET_RANGE =
            new ZPConfig_FLOAT(128.0f);

    @ZPVarDefinition(description = "Send a net packet for headshot and bullet entity-hit effects (blood + sound).")
    public static final ZPConfig_BOOL SEND_PACKET_ABOUT_BULLET_ENTITY_HIT =
            new ZPConfig_BOOL(true);
}