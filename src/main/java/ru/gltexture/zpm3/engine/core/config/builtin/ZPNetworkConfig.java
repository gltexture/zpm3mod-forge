/*
 *
 *  * zpm3forge
 *  * Copyright (C) 2026 gltexture
 *  *
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program. If not, see <https://www.gnu.org/licenses/>.
 *
 */

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