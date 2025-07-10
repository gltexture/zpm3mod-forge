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
    public static RegistryObject<SoundEvent> syringe;
    public static RegistryObject<SoundEvent> pills;
    public static RegistryObject<SoundEvent> bandage;

    public ZPSounds() {
        super(Registries.SOUND_EVENT, ZPRegistryConveyor.Target.SOUNDS);
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
