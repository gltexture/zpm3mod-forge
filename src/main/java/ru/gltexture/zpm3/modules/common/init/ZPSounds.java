/*
 *
 *  * zpm3forge
 *  * Copyright (C) 2026 gltexture
 *  *
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program. If not, see <https://www.gnu.org/licenses/>.
 *
 */

package ru.gltexture.zpm3.modules.common.init;

import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.registry.ZPCommonRegistry;
import ru.gltexture.zpm3.modules.common.init.helper.ZPRegSounds;
import ru.gltexture.zpm3.engine.core.ZPRegistryConveyor;

public class ZPSounds extends ZPCommonRegistry<SoundEvent> {
    public static RegistryObject<SoundEvent> nv_goggles;
    public static RegistryObject<SoundEvent> breath;
    public static RegistryObject<SoundEvent> breath_water;

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

    public static RegistryObject<SoundEvent> m16_fire;
    public static RegistryObject<SoundEvent> m16_reload;

    public static RegistryObject<SoundEvent> mp5_fire;
    public static RegistryObject<SoundEvent> mp5_reload;

    public static RegistryObject<SoundEvent> machinegun_fire;
    public static RegistryObject<SoundEvent> machinegun_reload;

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
    public static RegistryObject<SoundEvent> geiger_fx;

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
