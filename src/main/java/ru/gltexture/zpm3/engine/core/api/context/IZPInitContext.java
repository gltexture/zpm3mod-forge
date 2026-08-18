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

package ru.gltexture.zpm3.engine.core.api.context;

import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.api.events.ZP3EventHandlerClass;
import ru.gltexture.zpm3.modules.entity.population.ZPSetupPopulation;
import ru.gltexture.zpm3.modules.net_pack.data.accessors.ZPNetDataAccessor;
import ru.gltexture.zpm3.modules.net_pack.data.data_ent.ZPNetDataVar;

public interface IZPInitContext {
    void registerZP3EventHandlerClass(@NotNull Class<? extends ZP3EventHandlerClass> clazz);

    void defineNetAccessorOnEntity(@NotNull Class<? extends Entity> clazz, @NotNull ZPNetDataAccessor<?> dataAccessor);
    <E> void defineStaticNetAccessor_ForServer(@NotNull ZPNetDataAccessor<E> accessor, @NotNull ZPNetDataVar<E> defaultValue);
    <E> void defineStaticNetAccessor_ForClient(@NotNull ZPNetDataAccessor<E> accessor, @NotNull ZPNetDataVar<E> defaultValue);

    //void addLootTablesRegistry(@NotNull ZPSyntheticLootCasesDataGenRegistry object);
    void runPopulationSetup(@NotNull ZPSetupPopulation setup);
}
