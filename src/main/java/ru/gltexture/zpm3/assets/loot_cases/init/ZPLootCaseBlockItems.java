package ru.gltexture.zpm3.assets.loot_cases.init;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.loot_cases.init.helper.ZPRegLootCaseItems;
import ru.gltexture.zpm3.engine.core.ZPRegistryConveyor;
import ru.gltexture.zpm3.engine.registry.ZPRegistry;

public class ZPLootCaseBlockItems extends ZPRegistry<Item> {
    public ZPLootCaseBlockItems() {
        super(ZPRegistryConveyor.Target.ITEM);
    }

    @Override
    protected void runRegister(@NotNull ZPRegSupplier<Item> regSupplier) {
        ZPRegLootCaseItems.init(regSupplier);
    }

    @Override
    protected void postRegister(String name, RegistryObject<Item> object) {
        super.postRegister(name, object);
    }

    @Override
    public int priority() {
        return 1;
    }

    @Override
    public @NotNull String getID() {
        return this.getClass().getSimpleName();
    }
}