package ru.gltexture.zpm3.engine.core.config.builtin;

import ru.gltexture.zpm3.engine.core.config.ZPConfigConstantsClass;
import ru.gltexture.zpm3.engine.core.config.vars.*;

public class ZPExperimentalConfig implements ZPConfigConstantsClass {
    @ZPVarDefinition(description = "Allow acid blocks to break surrounding blocks (EXPERIMENTAL, may cause lag).")
    public static final ZPConfig_BOOL ALLOW_ACID_LIQUID_DESTROY_BLOCKS =
            new ZPConfig_BOOL(true);
}