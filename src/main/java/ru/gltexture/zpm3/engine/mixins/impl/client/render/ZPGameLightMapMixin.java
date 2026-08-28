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

package ru.gltexture.zpm3.engine.mixins.impl.client.render;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LightTexture;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.gltexture.zpm3.engine.client.rendering.lightmap.ZPLightMapModifier;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.api.events.client.ZPEventBus_ClientRendering;

import ru.gltexture.zpm3.engine.core.ZP_EventsManager;
import ru.gltexture.zpm3.engine.core.config.builtin.ZPWorldConfig;
import ru.gltexture.zpm3.modules.debug.imgui.ZPImGuiDebugInterface;
import ru.gltexture.zpm3.modules.net_pack.ZPNetPackModule;
import ru.gltexture.zpm3.modules.net_pack.data.vars.ZPNetDataBoolean;
import ru.gltexture.zpm3.modules.net_pack.data.vars.ZPNetDataFloat;

@Mixin(LightTexture.class)
public abstract class ZPGameLightMapMixin {
    @Shadow private boolean updateLightTexture;
    @Shadow @Final private Minecraft minecraft;

    @Shadow protected abstract float getDarknessGamma(float pPartialTicks);

    @WrapOperation(method = "updateLightTexture", at = @At(value = "INVOKE", target = "Lorg/joml/Vector3f;lerp(Lorg/joml/Vector3fc;F)Lorg/joml/Vector3f;", ordinal = 5))
    private Vector3f zpm3forge$modifyGammaLerp(Vector3f self, Vector3fc other, float amount, Operation<Vector3f> original, @Local(argsOnly = true) float partialTicks) {
        float f2 = this.minecraft.options.darknessEffectScale().get().floatValue();
        float f3 = this.getDarknessGamma(partialTicks) * f2;
        float f14 = amount;
        if (this.minecraft.player != null && (!this.minecraft.player.isCreative() || this.minecraft.options.hideGui)) {
            final boolean serverDarkness = ZombiePlague3.netClient().getNetStaticDataSyncer().getVar(ZPNetPackModule.StoC__DARKNESS_ENABLED).orElse(new ZPNetDataBoolean(ZPWorldConfig.ENABLE_HARDCORE_DARKNESS_SERVER_SIDE.getVar())).getValue();
            if (serverDarkness) {
                f14 = ZombiePlague3.netClient().getNetStaticDataSyncer().getVar(ZPNetPackModule.StoC__DARKNESS_FACTOR).orElse(new ZPNetDataFloat(ZPWorldConfig.DARKNESS_GAMMA_STATIC_FACTOR_SERVER_SIDE.getVar())).getValue();
                if (ZPImGuiDebugInterface.debugDarknessValueEnable) {
                    f14 = ZPImGuiDebugInterface.debugDarknessValue;
                }
                f14 -= f3;
            }
        }
        ZP_EventsManager.pushEvent(new ZPEventBus_ClientRendering.PostCalcMinecraftLightMapEvent(ZPLightMapModifier.INSTANCE, self, f14));
        ZPLightMapModifier.LightMapModRequest lightTextureLocation = null;
        while ((lightTextureLocation = ZPLightMapModifier.INSTANCE.pop()) != null) {
            if (lightTextureLocation.rgb_MUL() != null) {
                self.mul(lightTextureLocation.rgb_MUL());
            }
            if (lightTextureLocation.rgb_ADD() != null) {
                self.add(lightTextureLocation.rgb_ADD());
            }
            f14 *= lightTextureLocation.gamma_MUL();
            f14 += lightTextureLocation.gamme_ADD();
        }
        return original.call(self, other, f14);
    }

    @Inject(method = "updateLightTexture", at = @At("HEAD"))
    public void updateLightTexture(float pPartialTicks, CallbackInfo ci) {
        if (this.updateLightTexture) {
            ClientLevel clientlevel = this.minecraft.level;
            if (clientlevel != null && this.minecraft.player != null) {
                ZP_EventsManager.pushEvent(new ZPEventBus_ClientRendering.PreCalcMinecraftLightMapEvent(ZPLightMapModifier.INSTANCE));
            }
        }
    }
}
