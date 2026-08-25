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

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.client.rendering.IZPClientManager;
import ru.gltexture.zpm3.engine.client.rendering.callbacks.IZPClientCallbacksManager;
import ru.gltexture.zpm3.engine.client.rendering.hooks.IZPRenderHooksManager;
import ru.gltexture.zpm3.engine.client.rendering.imgui.interfaces.IZPImGuiInterface;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.modules.commands.zones.ZPZoneFlag;
import ru.gltexture.zpm3.modules.armor.events.client.ZPPlayerArmorSoundOnClientEvent;
import ru.gltexture.zpm3.modules.commands.events.client.ZPRenderSpecialZoneEffectsOnClient;
import ru.gltexture.zpm3.modules.mob_effects.client.ZPFakeClientEffect;
import ru.gltexture.zpm3.modules.mob_effects.client.ZPLocalPlayerFakeEffectsManager;

import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public interface IZPClientSetupContext {
    void registerImGuiInterface(@NotNull Supplier<IZPImGuiInterface> imGuiInterface);
    void registerArmorSound(@NotNull ZPPlayerArmorSoundOnClientEvent.TrackedSoundLauncher trackedSoundLauncher);
    void registerZoneEffect(@NotNull final ZPZoneFlag flag, @NotNull final ZPRenderSpecialZoneEffectsOnClient.RenderZoneEffect effect);
    void registerZpArchivedMap(@NotNull String modId, @NotNull String folder);
    void createConditionToApplyFakeEffect(@NotNull ZPFakeClientEffect key, @NotNull ZPLocalPlayerFakeEffectsManager.ZPFakeEffectSetOnPlayerCondition condition);

    @NotNull IZPClientCallbacksManager getClientCallbacksManager();

    @Deprecated(forRemoval = true)
    @NotNull IZPRenderHooksManager getClientRenderHooksManager();

    @NotNull IZPClientManager getClientManager();

    default boolean isImGuiContextValid() {
        return ZombiePlague3.getClientManager().isImGuiValid();
    }
}
