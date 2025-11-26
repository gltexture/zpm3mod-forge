package ru.gltexture.zpm3.assets.player.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class ZPClientGlobalSettings {
    public static boolean DARKNESS_ENABLED = true;
    public static float DARKNESS_FACTOR = -1.0f;
}
