package ru.gltexture.zpm3.assets.common.init;

import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.init.helper.ZPRegMelee;
import ru.gltexture.zpm3.assets.common.init.helper.ZPRegThrowable;
import ru.gltexture.zpm3.assets.common.instances.entities.ZPAcidBottleEntity;
import ru.gltexture.zpm3.assets.common.instances.entities.ZPPlateEntity;
import ru.gltexture.zpm3.assets.common.instances.entities.ZPRockEntity;
import ru.gltexture.zpm3.assets.common.instances.items.ZPItemAxe;
import ru.gltexture.zpm3.assets.common.instances.items.ZPItemPickaxe;
import ru.gltexture.zpm3.assets.common.instances.items.ZPItemSword;
import ru.gltexture.zpm3.assets.common.instances.items.ZPItemThrowable;
import ru.gltexture.zpm3.engine.core.ZPRegistryConveyor;
import ru.gltexture.zpm3.engine.helpers.ZPDefaultModelsHelper;
import ru.gltexture.zpm3.engine.helpers.ZPDispenserHelper;
import ru.gltexture.zpm3.engine.helpers.ZPItemTabAddHelper;
import ru.gltexture.zpm3.engine.registry.base.ZPRegistry;

public class ZPItems extends ZPRegistry<Item> {
    // ITEMS
    public static RegistryObject<ZPItemThrowable> acid_bottle;
    public static RegistryObject<ZPItemThrowable> plate;
    public static RegistryObject<ZPItemThrowable> rock;


    // MELEE
    public static RegistryObject<ZPItemSword> bat;
    public static RegistryObject<ZPItemSword> iron_club;
    public static RegistryObject<ZPItemSword> pipe;
    public static RegistryObject<ZPItemSword> golf_club;
    public static RegistryObject<ZPItemAxe> hatchet;
    public static RegistryObject<ZPItemPickaxe> sledgehammer;
    public static RegistryObject<ZPItemSword> crowbar;

    public ZPItems() {
        super(ForgeRegistries.ITEMS, ZPRegistryConveyor.Target.ITEM);
    }

    @Override
    protected void runRegister(@NotNull ZPRegSupplier<Item> regSupplier) {
        ZPRegThrowable.init(regSupplier);
        ZPRegMelee.init(regSupplier);
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