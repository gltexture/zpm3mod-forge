package ru.gltexture.zpm3.modules.common.mixins.ext;

import ru.gltexture.zpm3.modules.common.instances.block_entities.IFadingBlockEntity;

public interface ICampfireExt extends IFadingBlockEntity {
    int zpm3forge$fadeCooldown();
    void zpm3forge$incCooldown(int inc);
    void zpm3forge$setCooldown(int cooldown);
}
