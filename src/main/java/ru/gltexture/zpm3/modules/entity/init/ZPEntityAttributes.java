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

package ru.gltexture.zpm3.modules.entity.init;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZPRegistryConveyor;
import ru.gltexture.zpm3.engine.registry.ZPCommonRegistry;

public class ZPEntityAttributes extends ZPCommonRegistry<Attribute> {
    public static RegistryObject<RangedAttribute> zm_attack_range_multiplier;
    public static RegistryObject<RangedAttribute> zm_mining_speed;
    public static RegistryObject<RangedAttribute> zm_random_effect_chance;
    public static RegistryObject<RangedAttribute> zm_throw_a_gift_chance;

    public ZPEntityAttributes() {
        super(ZPRegistryConveyor.Target.ATTRIBUTE);
    }

    @Override
    protected void runRegister(@NotNull ZPRegSupplier<Attribute> regSupplier) {
        ZPEntityAttributes.zm_attack_range_multiplier = regSupplier.register("zm_attack_range_multiplier", () -> new RangedAttribute("zpm3.zm_attack_range_multiplier", 0.5f, 0.0D, 1024.0D)).end();
        ZPEntityAttributes.zm_mining_speed = regSupplier.register("zm_mining_speed", () -> new RangedAttribute("zpm3.zm_mining_speed", 0.01f, 0.0f, 12.0f)).end();
        ZPEntityAttributes.zm_random_effect_chance = regSupplier.register("zm_effect_chance", () -> new RangedAttribute("zpm3.zm_effect_chance", 0.015f, 0.0f, 1.0f)).end();
        ZPEntityAttributes.zm_throw_a_gift_chance = regSupplier.register("zm_throw_a_gift_chance", () -> new RangedAttribute("zpm3.zm_throw_a_gift_chance", 0.01f, 0.0f, 1.0f)).end();
    }

    @Override
    public void postProcessing() {
        super.postProcessing();
    }

    @Override
    protected void postRegister(String name, RegistryObject<Attribute> object) {
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