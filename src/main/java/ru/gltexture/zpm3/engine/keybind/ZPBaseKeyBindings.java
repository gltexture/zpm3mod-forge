package ru.gltexture.zpm3.engine.keybind;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public final class ZPBaseKeyBindings extends ZPKeyBindingsManager {
    public static KeyMapping reloadKey;

    @Override
    public void init() {
        ZPBaseKeyBindings.reloadKey = this.addKeyBinding(new KeyMapping("zpm.key.reload", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_R, "key.categories.zpm"));
    }
}
