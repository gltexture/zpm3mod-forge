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

package ru.gltexture.zpm3.modules.guns.keybind;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;
import ru.gltexture.zpm3.engine.keybind.ZPKeyBindingsManager;

public final class ZPGunKeyBindings extends ZPKeyBindingsManager {
    public static KeyMapping reloadKey;
    public static KeyMapping unloadKey;

    @Override
    public void init() {
        ZPGunKeyBindings.reloadKey = this.addKeyBinding(new KeyMapping("key.zpm3.reload", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_R, "key.categories.zpm"));
        ZPGunKeyBindings.unloadKey = this.addKeyBinding(new KeyMapping("key.zpm3.unloadKey", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_Y, "key.categories.zpm"));
    }
}
