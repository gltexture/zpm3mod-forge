package ru.gltexture.zpm3.assets.player.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class ZPClientGlobalSettings {
    public static int DAY_TIME_CYCLE_TICKS_FREEZE = 1;
    public static int NIGHT_TIME_CYCLE_TICKS_FREEZE = 1;
    public static boolean DARKNESS_ENABLED = true;
    public static boolean SERVER_PICK_UP_ON_F = true;
    public static float DARKNESS_FACTOR = -1.0f;
}
