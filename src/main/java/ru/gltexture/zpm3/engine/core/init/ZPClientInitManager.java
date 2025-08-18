package ru.gltexture.zpm3.engine.core.init;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ZPClientInitManager extends ZPInitManager {
    static ZPClientInitManager INSTANCE = new ZPClientInitManager();

    private ZPClientInitManager() {
    }
}
