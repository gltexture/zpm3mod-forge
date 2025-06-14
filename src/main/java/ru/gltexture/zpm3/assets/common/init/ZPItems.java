package ru.gltexture.zpm3.assets.common.init;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.items.ItemAcidBottle;
import ru.gltexture.zpm3.engine.core.ZPRegistryConveyor;
import ru.gltexture.zpm3.engine.helpers.ZPDefaultModelsHelper;
import ru.gltexture.zpm3.engine.helpers.ZPItemTabAddHelper;
import ru.gltexture.zpm3.engine.registry.base.ZPRegistry;

public class ZPItems extends ZPRegistry<Item> {
    public static RegistryObject<Item> acid_bottle;

    public ZPItems() {
        super(ForgeRegistries.ITEMS, ZPRegistryConveyor.Target.ITEM);
    }

    @Override
    protected void runRegister(@NotNull ZPRegSupplier<Item> regSupplier) {
        ZPItems.acid_bottle = regSupplier.register("acid_bottle", () -> new ItemAcidBottle(new Item.Properties()));

        ZPItemTabAddHelper.matchTabItem(ZPTabs.zp_items_tab, ZPItems.acid_bottle);
    }

    @Override
    protected void postRegister(String name, RegistryObject<Item> object) {
        ZPDefaultModelsHelper.addNewItemWithDefaultModel(object, ZPDefaultModelsHelper.DEFAULT_ITEM);
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