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

package ru.gltexture.zpm3.engine.client.rendering.shaders;

import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.client.callbacking.ZPClientCallbacksManager;
import ru.gltexture.zpm3.modules.armor.events.client.ZPPlayerArmorSoundOnClientEvent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@OnlyIn(Dist.CLIENT)
public class ZPResourcesReloader implements PreparableReloadListener {
    @Override
    public @NotNull CompletableFuture<Void> reload(@NotNull PreparationBarrier pPreparationBarrier, @NotNull ResourceManager pResourceManager, @NotNull ProfilerFiller pPreparationsProfiler, @NotNull ProfilerFiller pReloadProfiler, @NotNull Executor pBackgroundExecutor, @NotNull Executor pGameExecutor) {
        CompletableFuture<Void> prepare = CompletableFuture.runAsync(() -> {
            // ....
        }, pBackgroundExecutor);
        return prepare.thenCompose(pPreparationBarrier::wait).thenRunAsync(() -> {
            ZPClientCallbacksManager.reloadResources(Minecraft.getInstance().getWindow());
            ZPPlayerArmorSoundOnClientEvent.clear();
        }, pGameExecutor);
    }
}