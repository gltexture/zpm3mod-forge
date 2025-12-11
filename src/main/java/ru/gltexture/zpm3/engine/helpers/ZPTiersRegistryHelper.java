package ru.gltexture.zpm3.engine.helpers;

import net.minecraft.world.item.Tier;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.instances.items.tier.ZPTier;

import java.util.HashSet;
import java.util.Set;

public abstract class ZPTiersRegistryHelper {
    public static Set<ZPTier[]> tierSet = new HashSet<>();

    public static void addToRegister(@NotNull ZPTier[] tier) {
        ZPTiersRegistryHelper.tierSet.add(tier);
    }

    public static void clear() {
        ZPTiersRegistryHelper.tierSet = null;
    }
}
