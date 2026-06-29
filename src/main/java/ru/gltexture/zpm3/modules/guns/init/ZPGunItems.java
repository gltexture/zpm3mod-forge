package ru.gltexture.zpm3.modules.guns.init;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZPRegistryConveyor;
import ru.gltexture.zpm3.engine.instances.items.*;
import ru.gltexture.zpm3.engine.registry.ZPRegistry;
import ru.gltexture.zpm3.engine.registry.collection.IZPCollectRegistryObjects;
import ru.gltexture.zpm3.modules.guns.init.helper.ZPRegGuns;
import ru.gltexture.zpm3.modules.guns.item.ZPGunClassicRifle;
import ru.gltexture.zpm3.modules.guns.item.ZPGunPistol;
import ru.gltexture.zpm3.modules.guns.item.ZPGunShotgun;

public class ZPGunItems extends ZPRegistry<Item> implements IZPCollectRegistryObjects {
    //GUNS
    public static RegistryObject<ZPGunPistol> handmade_pistol;
    public static RegistryObject<ZPItem> _handmade_pistol;

    public static RegistryObject<ZPGunPistol> admin_pistol;

    public static RegistryObject<ZPGunPistol> makarov;
    public static RegistryObject<ZPItem> _makarov;

    public static RegistryObject<ZPGunPistol> m1911;
    public static RegistryObject<ZPItem> _m1911;

    public static RegistryObject<ZPGunPistol> usp;
    public static RegistryObject<ZPItem> _usp;

    public static RegistryObject<ZPGunPistol> colt;
    public static RegistryObject<ZPItem> _colt;

    public static RegistryObject<ZPGunPistol> deagle;
    public static RegistryObject<ZPGunPistol> golden_deagle;
    public static RegistryObject<ZPItem> _deagle;

    public static RegistryObject<ZPGunPistol> uzi;
    public static RegistryObject<ZPItem> _uzi;

    public static RegistryObject<ZPGunShotgun> shotgun;
    public static RegistryObject<ZPItem> _shotgun;

    public static RegistryObject<ZPGunPistol> akm;
    public static RegistryObject<ZPItem> _akm;

    public static RegistryObject<ZPGunClassicRifle> mosin;
    public static RegistryObject<ZPItem> _mosin;

    public ZPGunItems() {
        super(ZPRegistryConveyor.Target.ITEM);
    }

    @Override
    protected void runRegister(@NotNull ZPRegSupplier<Item> regSupplier) {
        ZPRegGuns.init(regSupplier);
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