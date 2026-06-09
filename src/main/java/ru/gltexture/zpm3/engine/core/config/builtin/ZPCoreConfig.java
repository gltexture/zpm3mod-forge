package ru.gltexture.zpm3.engine.core.config.builtin;

import ru.gltexture.zpm3.engine.core.config.ZPConfigConstantsClass;
import ru.gltexture.zpm3.engine.core.config.vars.ZPConfig_BOOL;
import ru.gltexture.zpm3.engine.core.config.vars.ZPVarDefinition;


public class ZPCoreConfig implements ZPConfigConstantsClass {
    @ZPVarDefinition(description = "WIP Mode")
    public static final ZPConfig_BOOL WIP_MODE =
            new ZPConfig_BOOL(true);

    @ZPVarDefinition(description = "DEV Mode")
    public static final ZPConfig_BOOL DEV_MODE =
            new ZPConfig_BOOL(true);
}
