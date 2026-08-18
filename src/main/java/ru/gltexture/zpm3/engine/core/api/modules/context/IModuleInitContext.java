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

package ru.gltexture.zpm3.engine.core.api.modules.context;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.engine.core.api.context.IZPInitContext;
import ru.gltexture.zpm3.engine.events.ZPForgeEventHandlerClass;
import ru.gltexture.zpm3.engine.instances.items.tier.ZPTier;
import ru.gltexture.zpm3.engine.instances.items.tier.ZPTierData;
import ru.gltexture.zpm3.engine.network.ZPNetwork;
import ru.gltexture.zpm3.engine.recipes.ZPRecipesRegistry;
import ru.gltexture.zpm3.engine.registry.ZPCommonRegistry;
import ru.gltexture.zpm3.modules.loot_cases.events.provider.ZPSyntheticLootCasesDataGenRegistry;
import ru.gltexture.zpm3.modules.loot_cases.loot_tables.ZPLootTable;
import ru.gltexture.zpm3.modules.loot_cases.loot_tables.synthetic.ZPSyntheticLootCaseDescription;

public interface IModuleInitContext extends IZPInitContext {
    void registerForgeEventHandlerClass(@NotNull Class<? extends ZPForgeEventHandlerClass> clazz);
    void registerEventHandlerInstance(@NotNull ZPForgeEventHandlerClass object);
    void registerTier(@NotNull ZPTierData tier);
    void registerNetworkPacket(@NotNull ZPNetwork.PacketData<?> packetData);

    void addCommonZp3RegistryClass(@NotNull Class<? extends ZPCommonRegistry<?>> zpRegistryProcessorClass);
    void registerSyntheticLootCase(@NotNull ZPSyntheticLootCaseDescription lootCase);
    void registerSyntheticLootTable(@NotNull ZPLootTable lootTable);
    void addRecipesRegistry(@NotNull ZPRecipesRegistry... recipesRegistries);
    void addTier(@NotNull ZPTier[] tier);
}
