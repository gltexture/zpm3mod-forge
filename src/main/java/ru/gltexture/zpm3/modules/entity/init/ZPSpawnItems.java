package ru.gltexture.zpm3.modules.entity.init;

import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZPRegistryConveyor;
import ru.gltexture.zpm3.engine.registry.ZPRegistry;
import ru.gltexture.zpm3.engine.registry.collection.IZPCollectRegistryObjects;
import ru.gltexture.zpm3.modules.entity.init.helper.ZPRegSpawns;

public class ZPSpawnItems extends ZPRegistry<Item> implements IZPCollectRegistryObjects {
    // SPAWNS
    public static RegistryObject<ForgeSpawnEggItem> common_zm_spawn;
    public static RegistryObject<ForgeSpawnEggItem> miner_zm_spawn;
    public static RegistryObject<ForgeSpawnEggItem> dog_zm_spawn;

    public ZPSpawnItems() {
        super(ZPRegistryConveyor.Target.ITEM);
    }

    @Override
    protected void runRegister(@NotNull ZPRegSupplier<Item> regSupplier) {
        ZPRegSpawns.init(regSupplier);
    }

    @Override
    protected void postRegister(String name, RegistryObject<Item> object) {
    }

    @Override
    public int priority() {
        return 0;
    }

    @Override
    public @NotNull String getID() {
        return this.getClass().getSimpleName();
    }
}