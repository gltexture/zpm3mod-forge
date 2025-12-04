package ru.gltexture.zpm3.assets.common.keybind;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;
import ru.gltexture.zpm3.engine.keybind.ZPKeyBindingsManager;

public final class ZPCommonKeyBindings extends ZPKeyBindingsManager {
    public static KeyMapping pickItem;

    @Override
    public void init() {
        ZPCommonKeyBindings.pickItem = this.addKeyBinding(new KeyMapping("zpm.key.pickItem", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_X, "key.categories.zpm"));
    }
}
