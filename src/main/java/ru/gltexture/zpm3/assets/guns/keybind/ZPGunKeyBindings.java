package ru.gltexture.zpm3.assets.guns.keybind;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;
import ru.gltexture.zpm3.engine.keybind.ZPKeyBindingsManager;

public final class ZPGunKeyBindings extends ZPKeyBindingsManager {
    public static KeyMapping reloadKey;
    public static KeyMapping unloadKey;

    @Override
    public void init() {
        ZPGunKeyBindings.reloadKey = this.addKeyBinding(new KeyMapping("zpm.key.reload", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_R, "key.categories.zpm"));
        ZPGunKeyBindings.unloadKey = this.addKeyBinding(new KeyMapping("zpm.key.unloadKey", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_Y, "key.categories.zpm"));
    }
}
