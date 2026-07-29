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

package ru.gltexture.zpm3.modules.entity.events.common;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.events.ZPForgeEventHandlerClass;
import ru.gltexture.zpm3.engine.service.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ZPEntityMobAttributes implements ZPForgeEventHandlerClass {
    public static final List<Pair<RegistryObject<EntityType<? extends LivingEntity>>, Supplier<AttributeSupplier.Builder>>> pairsToAttachAttributeCreation = new ArrayList<>();

    @SuppressWarnings("all")
    public static void addNewAttributeCreationUnsafe(@NotNull Object registryObject, @NotNull Supplier<AttributeSupplier.Builder> builder) {
        ZPEntityMobAttributes.pairsToAttachAttributeCreation.add(Pair.of((RegistryObject<EntityType<? extends LivingEntity>>) registryObject, builder));
    }

    public static void addNewAttributeCreation(@NotNull RegistryObject<EntityType<? extends LivingEntity>> registryObject, @NotNull Supplier<AttributeSupplier.Builder> builder) {
        ZPEntityMobAttributes.pairsToAttachAttributeCreation.add(Pair.of(registryObject, builder));
    }

    @SubscribeEvent
    public static void exec(@NotNull EntityAttributeCreationEvent event) {
        ZPEntityMobAttributes.pairsToAttachAttributeCreation.forEach((e) -> {
            event.put(e.first().get(), e.second().get().build());
        });
    }

    @Override
    public @NotNull ZPSide getSide() {
        return ZPSide.COMMON;
    }

    @Override
    public @NotNull Mod.EventBusSubscriber.Bus getBus() {
        return Mod.EventBusSubscriber.Bus.MOD;
    }
}
