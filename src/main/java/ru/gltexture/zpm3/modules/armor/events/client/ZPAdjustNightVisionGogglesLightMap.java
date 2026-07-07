package ru.gltexture.zpm3.modules.armor.events.client;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import ru.gltexture.zpm3.engine.client.rendering.lightmap.ZPLightMapModifier;
import ru.gltexture.zpm3.engine.core.api.events.ZombiePlagueEvent;
import ru.gltexture.zpm3.engine.core.api.events.ZPModEventBus;
import ru.gltexture.zpm3.modules.armor.utils.ZPArmorUtil;

@OnlyIn(Dist.CLIENT)
public class ZPAdjustNightVisionGogglesLightMap {
    public static final float NV_GAMM = 2.5f;

    @ZombiePlagueEvent
    public static void lightmapUpdate(ZPModEventBus.PostCalcMinecraftLightMapEvent event) {
        if (Minecraft.getInstance().player != null) {
            if (ZPArmorUtil.isEntityHasNightVisionGoggles(Minecraft.getInstance().player) && Minecraft.getInstance().options.getCameraType().isFirstPerson()) {
                event.getZpLightMapModifier().add(new ZPLightMapModifier.LightMapModRequest(1.0f, event.getCurrentGAMMA() < ZPAdjustNightVisionGogglesLightMap.NV_GAMM ? ZPAdjustNightVisionGogglesLightMap.NV_GAMM - event.getCurrentGAMMA() : 0.0f));
            }
        }
    }
}
