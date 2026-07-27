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

package ru.gltexture.zpm3.engine.client.rendering.hooks;

import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.client.callbacking.ZPClientCallbacks;

import java.util.function.Supplier;

public interface IZPRenderHooksManager extends ZPClientCallbacks.ZPClientResourceDependentObject {
    void addSceneRenderingHook(@NotNull ZPRenderHooks.ZPSceneRenderingHook zpSceneRenderingHook);

    void addItemRendering1PersonHook(@NotNull Supplier<Item> itemSupplier, @NotNull ZPRenderHooks.ZPItemRendering1PersonHook zpItemRendering1PersonHook);
    void addItemRendering3PersonHook(@NotNull Supplier<Item> itemSupplier, @NotNull ZPRenderHooks.ZPItemRendering3PersonHook zpItemRendering3PersonHook);

    void addItemSceneRendering1PersonHookPre(@NotNull ZPRenderHooks.ZPItemSceneRendering1PersonHookPre zpItemSceneRendering1PersonHookPre);
    void addItemSceneRendering1PersonHookPost(@NotNull ZPRenderHooks.ZPItemSceneRendering1PersonHookPost zpItemSceneRendering1PersonHookPost);
    void addItemSceneRendering1PersonHooks(@NotNull ZPRenderHooks.ZPItemSceneRendering1PersonHooks zpItemSceneRendering1PersonHooks);

    void addItemSceneRendering3PersonHookPre(@NotNull ZPRenderHooks.ZPItemSceneRendering3PersonHookPre zpItemSceneRendering3PersonHookPre);
    void addItemSceneRendering3PersonHookPost(@NotNull ZPRenderHooks.ZPItemSceneRendering3PersonHookPost zpItemSceneRendering3PersonHookPost);
    void addItemSceneRendering3PersonHooks(@NotNull ZPRenderHooks.ZPItemSceneRendering3PersonHooks zpItemSceneRendering3PersonHooks);
}