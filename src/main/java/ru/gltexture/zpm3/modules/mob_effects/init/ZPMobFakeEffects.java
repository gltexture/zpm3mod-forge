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

package ru.gltexture.zpm3.modules.mob_effects.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.modules.mob_effects.client.ZPFakeClientEffect;

@OnlyIn(Dist.CLIENT)
public class ZPMobFakeEffects {
    public static ZPFakeClientEffect fakeRadiation = new ZPFakeClientEffect(
            MobEffectCategory.HARMFUL,
            ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "fake_rad"),
            () -> ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "textures/mob_effects/fake/radiation.png"),
            0xffc965);
    public static ZPFakeClientEffect fakeAcid = new ZPFakeClientEffect(
            MobEffectCategory.HARMFUL,
            ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "fake_acid"),
            () -> ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "textures/mob_effects/fake/acid.png"),
            0x8cff8c);
    public static ZPFakeClientEffect fakeIntoxication = new ZPFakeClientEffect(
            MobEffectCategory.HARMFUL,
            ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "fake_intoxicaton"),
            () -> ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "textures/mob_effects/fake/intoxication.png"),
            0x90cc41);
    public static ZPFakeClientEffect fakeSeasickness = new ZPFakeClientEffect(
            MobEffectCategory.HARMFUL,
            ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "fake_seasickness"),
            () -> ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "textures/mob_effects/fake/seasickness.png"),
            0x8c8cff);
}
