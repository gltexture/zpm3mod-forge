package ru.gltexture.zpm3.assets.common.init;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZPRegistryConveyor;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.registry.base.ZPRegistry;

public class ZPItems extends ZPRegistry<Item> {
    public static RegistryObject<Item> acid_bottle;

    @Override
    protected void runRegister(@NotNull ZPRegSupplier<Item> regSupplier) {
        ZPItems.acid_bottle = regSupplier.register("acid_bottle", () -> new Item(new Item.Properties()));
    }

    @Override
    protected @NotNull DeferredRegister<Item> createDeferredRegister() {
        return DeferredRegister.create(ForgeRegistries.ITEMS, ZombiePlague3.MOD_ID());
    }

    @Override
    public int priority() {
        return 0;
    }

    @Override
    public ZPRegistryConveyor.@NotNull Target getTarget() {
        return ZPRegistryConveyor.Target.ITEM;
    }

    @Override
    public @NotNull String getID() {
        return "ZPItems";
    }
}
