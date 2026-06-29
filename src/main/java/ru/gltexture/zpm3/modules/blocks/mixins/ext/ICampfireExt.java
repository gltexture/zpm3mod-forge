package ru.gltexture.zpm3.modules.blocks.mixins.ext;

import ru.gltexture.zpm3.modules.blocks.instances.block_entities.IFadingBlockEntity;

public interface ICampfireExt extends IFadingBlockEntity {
    int zpm3forge$fadeCooldown();
    void zpm3forge$incCooldown(int inc);
    void zpm3forge$setCooldown(int cooldown);
}
