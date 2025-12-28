package ru.gltexture.zpm3.assets.common.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.init.helper.ZPRegBlockItems;
import ru.gltexture.zpm3.assets.common.init.helper.ZPRegSounds;
import ru.gltexture.zpm3.engine.core.ZPRegistryConveyor;
import ru.gltexture.zpm3.engine.registry.ZPRegistry;

public class ZPSounds extends ZPRegistry<SoundEvent> {
    public static RegistryObject<SoundEvent> zm_miner_hurt;

    public static RegistryObject<SoundEvent> syringe;
    public static RegistryObject<SoundEvent> pills;
    public static RegistryObject<SoundEvent> bandage;

    public static RegistryObject<SoundEvent> empty;

    public static RegistryObject<SoundEvent> makarov_fire;
    public static RegistryObject<SoundEvent> makarov_reload;

    public static RegistryObject<SoundEvent> deagle_fire;
    public static RegistryObject<SoundEvent> deagle_reload;

    public static RegistryObject<SoundEvent> m1911_fire;
    public static RegistryObject<SoundEvent> m1911_reload;

    public static RegistryObject<SoundEvent> usp_fire;
    public static RegistryObject<SoundEvent> usp_reload;

    public static RegistryObject<SoundEvent> uzi_fire;
    public static RegistryObject<SoundEvent> uzi_reload;

    public static RegistryObject<SoundEvent> colt_fire;
    public static RegistryObject<SoundEvent> colt_reload;

    public static RegistryObject<SoundEvent> handmade_pistol_fire;
    public static RegistryObject<SoundEvent> handmade_pistol_reload;

    public static RegistryObject<SoundEvent> shotgun_fire;
    public static RegistryObject<SoundEvent> shotgun_reload;
    public static RegistryObject<SoundEvent> shell_insert;
    public static RegistryObject<SoundEvent> shotgun_shutter;
    public static RegistryObject<SoundEvent> rifle_shutter;
    public static RegistryObject<SoundEvent> shell_insert2;
    public static RegistryObject<SoundEvent> akm_fire;
    public static RegistryObject<SoundEvent> akm_reload;
    public static RegistryObject<SoundEvent> mosin_fire;
    public static RegistryObject<SoundEvent> impactmeat;
    public static RegistryObject<SoundEvent> headshot;
    public static RegistryObject<SoundEvent> fracture;

    public ZPSounds() {
        super(ZPRegistryConveyor.Target.SOUND_EVENT);
    }

    @Override
    protected void runRegister(@NotNull ZPRegSupplier<SoundEvent> regSupplier) {
        ZPRegSounds.init(regSupplier);
    }

    @Override
    protected void postRegister(String name, RegistryObject<SoundEvent> object) {
        super.postRegister(name, object);
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
