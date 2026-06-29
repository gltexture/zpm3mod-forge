package ru.gltexture.zpm3.modules.armor.events.client;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import ru.gltexture.zpm3.engine.client.rendering.lightmap.ZPLightMapModifier;
import ru.gltexture.zpm3.engine.core.api.events.ZombiePlagueEvent;
import ru.gltexture.zpm3.engine.core.api.events.ZPEventBus;
import ru.gltexture.zpm3.modules.armor.utils.ZPArmorUtils;

@OnlyIn(Dist.CLIENT)
public class ZPAdjustNightVisionGogglesLightMap {
    public static final float NV_GAMM = 2.25f;

    @ZombiePlagueEvent
    public static void lightmapUpdate(ZPEventBus.PostCalcMinecraftLightMapEvent event) {
        if (Minecraft.getInstance().player != null) {
            if (ZPArmorUtils.isEntityHasNightVisionGoggles(Minecraft.getInstance().player)) {
                event.getZpLightMapModifier().add(new ZPLightMapModifier.LightMapModRequest(1.0f, event.getCurrentGAMMA() < ZPAdjustNightVisionGogglesLightMap.NV_GAMM ? ZPAdjustNightVisionGogglesLightMap.NV_GAMM - event.getCurrentGAMMA() : 0.0f));
            }
        }
    }
}
