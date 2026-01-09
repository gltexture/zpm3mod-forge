package ru.gltexture.zpm3.assets.common.init;

import net.minecraft.world.item.Item;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.init.helper.ZPRegBlockItems;
import ru.gltexture.zpm3.engine.core.ZPRegistryConveyor;
import ru.gltexture.zpm3.engine.registry.ZPRegistry;

public class ZPBiomeModifying extends ZPRegistry<BiomeModifier> {
    public ZPBiomeModifying() {
        super(ZPRegistryConveyor.Target.BIOME_MODIFIER);
    }

    @Override
    protected void runRegister(@NotNull ZPRegSupplier<BiomeModifier> regSupplier) {
    }

    @Override
    protected void postRegister(String name, RegistryObject<BiomeModifier> object) {
        super.postRegister(name, object);
    }

    @Override
    public int priority() {
        return 100;
    }

    @Override
    public @NotNull String getID() {
        return this.getClass().getSimpleName();
    }
}