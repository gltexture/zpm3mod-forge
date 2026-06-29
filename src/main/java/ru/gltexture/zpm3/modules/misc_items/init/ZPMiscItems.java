package ru.gltexture.zpm3.modules.misc_items.init;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.modules.melee_throwables_tools.init.helper.ZPRegToolItems;
import ru.gltexture.zpm3.engine.core.ZPRegistryConveyor;
import ru.gltexture.zpm3.engine.instances.items.*;
import ru.gltexture.zpm3.engine.registry.ZPRegistry;
import ru.gltexture.zpm3.engine.registry.collection.IZPCollectRegistryObjects;
import ru.gltexture.zpm3.modules.misc_items.init.helper.ZPRegMiscItems;

public class ZPMiscItems extends ZPRegistry<Item> implements IZPCollectRegistryObjects {
    // MISC
    public static RegistryObject<ZPItem> cement_material;
    public static RegistryObject<ZPItem> chisel_material;
    public static RegistryObject<ZPItem> scrap_material;
    public static RegistryObject<ZPItem> scrap_stack_material;
    public static RegistryObject<ZPItem> shelves_material;
    public static RegistryObject<ZPItem> table_material;

    public ZPMiscItems() {
        super(ZPRegistryConveyor.Target.ITEM);
    }

    @Override
    protected void runRegister(@NotNull ZPRegSupplier<Item> regSupplier) {
        ZPRegMiscItems.init(regSupplier);
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