package ru.gltexture.zpm3.assets.player.keybind;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;
import ru.gltexture.zpm3.engine.keybind.ZPKeyBindingsManager;

public final class ZPPickUpKeyBindings extends ZPKeyBindingsManager {
    public static KeyMapping pickItem;

    @Override
    public void init() {
        ZPPickUpKeyBindings.pickItem = this.addKeyBinding(new KeyMapping("zpm.key.pickItem", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_X, "key.categories.zpm"));
    }
}
