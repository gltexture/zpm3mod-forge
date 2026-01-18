package ru.gltexture.zpm3.engine.instances.items.tier;

import net.minecraft.world.item.Tier;

import java.util.List;

public interface ZPTier extends Tier {
    ZPTier[] tiers();
    ZPTierData init();
}
