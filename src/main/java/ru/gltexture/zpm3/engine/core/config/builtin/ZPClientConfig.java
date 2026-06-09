package ru.gltexture.zpm3.engine.core.config.builtin;

import ru.gltexture.zpm3.engine.core.config.ZPConfigConstantsClass;
import ru.gltexture.zpm3.engine.core.config.vars.*;

public class ZPClientConfig implements ZPConfigConstantsClass {

    @ZPVarDefinition(description = "(CLIENT) Show version info on screen.")
    public static final ZPConfig_BOOL SHOW_VERSION_INFO_ON_SCREEN =
            new ZPConfig_BOOL(true);

    @ZPVarDefinition(description = "(CLIENT) Render muzzle flashes. Disabling may fix rendering issues with other mods.")
    public static final ZPConfig_BOOL RENDER_MUZZLE_FLASHES =
            new ZPConfig_BOOL(true);

    @ZPVarDefinition(description = "(CLIENT) Render bullet tracers.")
    public static final ZPConfig_BOOL RENDER_BULLET_TRACERS =
            new ZPConfig_BOOL(true);

    @ZPVarDefinition(description = "(CLIENT) Render armor layer on hands.")
    public static final ZPConfig_BOOL RENDER_ARMOR_LAYERS_ON_HANDS =
            new ZPConfig_BOOL(true);

    @ZPVarDefinition(description = "(CLIENT) Fancy ZP item-entity animation.")
    public static final ZPConfig_BOOL FANCY_ITEM_ENTITIES =
            new ZPConfig_BOOL(true);

    @ZPVarDefinition(description = "(CLIENT) First-person FOV scaling.")
    public static final ZPConfig_BOOL FIRST_PERSON_RENDER_SPACE_SCALE_BY_FOV =
            new ZPConfig_BOOL(true);

    @ZPVarDefinition(description = "(CLIENT) First-person FOV scaling type: 0 = progressive (position-based), 1 = static.")
    public static final ZPConfig_INT FIRST_PERSON_RENDER_SCALE_TYPE =
            new ZPConfig_INT(0, ZPConfigVar.INT, 0, 1);
}