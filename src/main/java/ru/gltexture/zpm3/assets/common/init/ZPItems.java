package ru.gltexture.zpm3.assets.common.init;

import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.init.helper.*;
import ru.gltexture.zpm3.assets.common.instances.items.ZPMatches;
import ru.gltexture.zpm3.assets.common.instances.items.ZPWrenchTool;
import ru.gltexture.zpm3.assets.guns.item.ZPGunClassicRifle;
import ru.gltexture.zpm3.assets.guns.item.ZPGunShotgun;
import ru.gltexture.zpm3.engine.core.ZPRegistryConveyor;
import ru.gltexture.zpm3.assets.guns.item.ZPGunPistol;
import ru.gltexture.zpm3.engine.instances.items.*;
import ru.gltexture.zpm3.engine.registry.ZPRegistry;
import ru.gltexture.zpm3.engine.registry.collection.IZPCollectRegistryObjects;

public class ZPItems extends ZPRegistry<Item> implements IZPCollectRegistryObjects {
    // MISC
    public static RegistryObject<ZPItem> cement_material;
    public static RegistryObject<ZPItem> chisel_material;
    public static RegistryObject<ZPItem> scrap_material;
    public static RegistryObject<ZPItem> scrap_stack_material;
    public static RegistryObject<ZPItem> shelves_material;
    public static RegistryObject<ZPItem> table_material;

    // SPAWNS
    public static RegistryObject<ForgeSpawnEggItem> common_zm_spawn;
    public static RegistryObject<ForgeSpawnEggItem> miner_zm_spawn;
    public static RegistryObject<ForgeSpawnEggItem> dog_zm_spawn;

    // ITEMS
    public static RegistryObject<ZPItemThrowable> acid_bottle;
    public static RegistryObject<ZPItemThrowable> plate;
    public static RegistryObject<ZPItemThrowable> rock;
    public static RegistryObject<ZPItemBucket> acid_bucket;
    public static RegistryObject<ZPItemBucket> toxicwater_bucket;

    // TOOLS
    public static RegistryObject<ZPWrenchTool> wrench;
    public static RegistryObject<ZPMatches> matches;

    // MELEE
    public static RegistryObject<ZPItemSword> bat;
    public static RegistryObject<ZPItemSword> iron_club;
    public static RegistryObject<ZPItemSword> pipe;
    public static RegistryObject<ZPItemSword> golf_club;
    public static RegistryObject<ZPItemAxe> hatchet;
    public static RegistryObject<ZPItemPickaxe> sledgehammer;
    public static RegistryObject<ZPItemSword> crowbar;
    public static RegistryObject<ZPItemSword> cleaver;


    //FOOD
    public static RegistryObject<ZPItemFood> bean;
    public static RegistryObject<ZPItemFood> sprats;
    public static RegistryObject<ZPItemFood> jam;
    public static RegistryObject<ZPItemFood> mysterious_can;
    public static RegistryObject<ZPItemFood> peaches;
    public static RegistryObject<ZPItemFood> soda;
    public static RegistryObject<ZPItemFood> water;

    //MEDICINE
    public static RegistryObject<ZPItemMedicine> adrenaline_syringe;
    public static RegistryObject<ZPItemMedicine> morphine_syringe;
    public static RegistryObject<ZPItemMedicine> antibiotics_syringe;
    public static RegistryObject<ZPItemMedicine> anti_headache_pill;
    public static RegistryObject<ZPItemMedicine> anti_hunger_pill;
    public static RegistryObject<ZPItemMedicine> anti_poison_pill;
    public static RegistryObject<ZPItemMedicine> anti_zplague_syringe;
    public static RegistryObject<ZPItemMedicine> zplague_syringe;
    public static RegistryObject<ZPItemMedicine> tire;
    public static RegistryObject<ZPItemMedicine> bandage;
    public static RegistryObject<ZPItemMedicine> military_bandage;
    public static RegistryObject<ZPItemMedicine> meth_pill;
    public static RegistryObject<ZPItemMedicine> healing_pill;
    public static RegistryObject<ZPItemMedicine> better_vision_pill;
    public static RegistryObject<ZPItemMedicine> aid_kit;

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

    public ZPItems() {
        super(ZPRegistryConveyor.Target.ITEM);
    }

    @Override
    protected void runRegister(@NotNull ZPRegSupplier<Item> regSupplier) {
        ZPRegThrowable.init(regSupplier);
        ZPRegItems.init(regSupplier);
        ZPRegMelee.init(regSupplier);
        ZPRegFood.init(regSupplier);
        ZPRegMedicine.init(this, regSupplier);
        ZPRegGuns.init(regSupplier);
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