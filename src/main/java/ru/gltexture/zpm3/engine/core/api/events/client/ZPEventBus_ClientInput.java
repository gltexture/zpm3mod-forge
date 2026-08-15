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

package ru.gltexture.zpm3.engine.core.api.events.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import ru.gltexture.zpm3.engine.client.rendering.lightmap.ZPLightMapModifier;
import ru.gltexture.zpm3.engine.core.api.events.ZPEventDef;

@OnlyIn(Dist.CLIENT)
public abstract class ZPEventBus_ClientInput {
    public static final class CharEvent implements ZPEventDef.IEvent {
        private final long window;
        private final int codepoint;

        public CharEvent(long window, int codepoint) {
            this.window = window;
            this.codepoint = codepoint;
        }

        public long getWindow() {
            return this.window;
        }

        public int getCodepoint() {
            return this.codepoint;
        }
    }

    public static final class WindowResizeEvent implements ZPEventDef.IEvent {
        private final long window;
        private final int width;
        private final int height;

        public WindowResizeEvent(long window, int width, int height) {
            this.window = window;
            this.width = width;
            this.height = height;
        }

        public long getWindow() {
            return this.window;
        }

        public int getWidth() {
            return this.width;
        }

        public int getHeight() {
            return this.height;
        }
    }

    public static final class MouseButtonEvent implements ZPEventDef.IEvent {
        private final long window;
        private final int button;
        private final int action;
        private final int modifiers;

        public MouseButtonEvent(long window, int button, int action, int modifiers) {
            this.window = window;
            this.button = button;
            this.action = action;
            this.modifiers = modifiers;
        }

        public long getWindow() {
            return this.window;
        }

        public int getButton() {
            return this.button;
        }

        public int getAction() {
            return this.action;
        }

        public int getModifiers() {
            return this.modifiers;
        }

        public boolean isPressed() {
            return this.action == GLFW.GLFW_PRESS;
        }

        public boolean isReleased() {
            return this.action == GLFW.GLFW_RELEASE;
        }

        public boolean isRepeated() {
            return this.action == GLFW.GLFW_REPEAT;
        }
    }

    public static final class MouseScrollEvent implements ZPEventDef.IEvent {
        private final long window;
        private final double xOffset;
        private final double yOffset;

        public MouseScrollEvent(long window, double xOffset, double yOffset) {
            this.window = window;
            this.xOffset = xOffset;
            this.yOffset = yOffset;
        }

        public long getWindow() {
            return this.window;
        }

        public double getXOffset() {
            return this.xOffset;
        }

        public double getYOffset() {
            return this.yOffset;
        }
    }

    public static final class KeyboardEvent implements ZPEventDef.IEvent {
        private final long window;
        private final int key;
        private final int scanCode;
        private final int action;
        private final int modifiers;

        public KeyboardEvent(long window, int key, int scanCode, int action, int modifiers) {
            this.window = window;
            this.key = key;
            this.scanCode = scanCode;
            this.action = action;
            this.modifiers = modifiers;
        }

        public long getWindow() {
            return this.window;
        }

        public int getKey() {
            return this.key;
        }

        public int getScanCode() {
            return this.scanCode;
        }

        public int getAction() {
            return this.action;
        }

        public int getModifiers() {
            return this.modifiers;
        }

        public boolean isPressed() {
            return this.action == GLFW.GLFW_PRESS;
        }

        public boolean isRepeated() {
            return this.action == GLFW.GLFW_REPEAT;
        }

        public boolean isReleased() {
            return this.action == GLFW.GLFW_RELEASE;
        }
    }
}
/*
EventLauncher.pushEvent(new ZPEventBus_ClientRendering.RenderOGLSceneEvent(this, frameTicking, ZPEventBus_ClientRendering.Run.POST, toRenderObjects, toRenderLiquids), TODO);

    public static final class Class123 implements IEvent {

    }
 */