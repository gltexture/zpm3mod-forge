package ru.gltexture.zpm3.engine.mixins.impl.client;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import ru.gltexture.zpm3.assets.common.global.ZPConstants;
import ru.gltexture.zpm3.assets.debug.imgui.DearUITRSInterface;
import ru.gltexture.zpm3.assets.player.client.ZPClientGlobalSettings;

@Mixin(LightTexture.class)
public abstract class ZPGameLightMapMixin {
    @Shadow @Final private DynamicTexture lightTexture;
    @Shadow @Final private NativeImage lightPixels;
    @Shadow @Final private ResourceLocation lightTextureLocation;
    @Shadow private boolean updateLightTexture;
    @Shadow private float blockLightRedFlicker;
    @Shadow @Final private GameRenderer renderer;
    @Shadow @Final private Minecraft minecraft;

    @Shadow protected abstract float getDarknessGamma(float pPartialTicks);
    @Shadow protected abstract float calculateDarknessScale(LivingEntity pEntity, float pGamma, float pPartialTick);
    @Shadow protected abstract float notGamma(float pValue);

    private static void clampColor(Vector3f pColor) {
        pColor.set(Mth.clamp(pColor.x, 0.0F, 1.0F), Mth.clamp(pColor.y, 0.0F, 1.0F), Mth.clamp(pColor.z, 0.0F, 1.0F));
    }

    public void updateLightTexture(float pPartialTicks) {
        if (this.updateLightTexture) {
            this.updateLightTexture = false;
            this.minecraft.getProfiler().push("lightTex");
            ClientLevel clientlevel = this.minecraft.level;
            if (clientlevel != null) {
                float f = clientlevel.getSkyDarken(1.0F);
                float f1;
                if (clientlevel.getSkyFlashTime() > 0) {
                    f1 = 1.0F;
                } else {
                    f1 = f * 0.95F + 0.05F;
                }

                float f2 = this.minecraft.options.darknessEffectScale().get().floatValue();
                float f3 = this.getDarknessGamma(pPartialTicks) * f2;
                float f4 = this.calculateDarknessScale(this.minecraft.player, f3, pPartialTicks) * f2;
                float f6 = this.minecraft.player.getWaterVision();
                float f5;
                if (this.minecraft.player.hasEffect(MobEffects.NIGHT_VISION)) {
                    f5 = GameRenderer.getNightVisionScale(this.minecraft.player, pPartialTicks);
                } else if (f6 > 0.0F && this.minecraft.player.hasEffect(MobEffects.CONDUIT_POWER)) {
                    f5 = f6;
                } else {
                    f5 = 0.0F;
                }

                Vector3f vector3f = (new Vector3f(f, f, 1.0F)).lerp(new Vector3f(1.0F, 1.0F, 1.0F), 0.35F);
                float f7 = this.blockLightRedFlicker + 1.5F;
                Vector3f vector3f1 = new Vector3f();

                for(int i = 0; i < 16; ++i) {
                    for(int j = 0; j < 16; ++j) {
                        float f8 = LightTexture.getBrightness(clientlevel.dimensionType(), i) * f1;
                        float f9 = LightTexture.getBrightness(clientlevel.dimensionType(), j) * f7;
                        float f10 = f9 * ((f9 * 0.6F + 0.4F) * 0.6F + 0.4F);
                        float f11 = f9 * (f9 * f9 * 0.6F + 0.4F);
                        vector3f1.set(f9, f10, f11);
                        boolean flag = clientlevel.effects().forceBrightLightmap();
                        if (flag) {
                            vector3f1.lerp(new Vector3f(0.99F, 1.12F, 1.0F), 0.25F);
                            ZPGameLightMapMixin.clampColor(vector3f1);
                        } else {
                            Vector3f vector3f2 = (new Vector3f((Vector3fc)vector3f)).mul(f8);
                            vector3f1.add(vector3f2);
                            vector3f1.lerp(new Vector3f(0.75F, 0.75F, 0.75F), 0.04F);
                            if (this.renderer.getDarkenWorldAmount(pPartialTicks) > 0.0F) {
                                float f12 = this.renderer.getDarkenWorldAmount(pPartialTicks);
                                Vector3f vector3f3 = (new Vector3f((Vector3fc)vector3f1)).mul(0.7F, 0.6F, 0.6F);
                                vector3f1.lerp(vector3f3, f12);
                            }
                        }

                        clientlevel.effects().adjustLightmapColors(clientlevel, pPartialTicks, f, f7, f8, j, i, vector3f1);

                        if (f5 > 0.0F) {
                            float f13 = Math.max(vector3f1.x(), Math.max(vector3f1.y(), vector3f1.z()));
                            if (f13 < 1.0F) {
                                float f15 = 1.0F / f13;
                                Vector3f vector3f5 = (new Vector3f((Vector3fc)vector3f1)).mul(f15);
                                vector3f1.lerp(vector3f5, f5);
                            }
                        }

                        if (!flag) {
                            if (f4 > 0.0F) {
                                vector3f1.add(-f4, -f4, -f4);
                            }

                            ZPGameLightMapMixin.clampColor(vector3f1);
                        }

                        float f14 = this.minecraft.options.gamma().get().floatValue();
                        if (this.minecraft.player != null && (!this.minecraft.player.isCreative() || this.minecraft.options.hideGui)) {
                            if (ZPClientGlobalSettings.DARKNESS_ENABLED) {
                                f14 = ZPClientGlobalSettings.DARKNESS_FACTOR;
                                if (DearUITRSInterface.debugDarknessValueEnable) {
                                    f14 = DearUITRSInterface.debugDarknessValue;
                                }
                            }
                        }
                        Vector3f vector3f4 = new Vector3f(this.notGamma(vector3f1.x), this.notGamma(vector3f1.y), this.notGamma(vector3f1.z));
                        vector3f1.lerp(vector3f4, Math.max(-10.0F, f14 - f3));
                        vector3f1.lerp(new Vector3f(0.75F, 0.75F, 0.75F), 0.04F);
                        ZPGameLightMapMixin.clampColor(vector3f1);
                        vector3f1.mul(255.0F);
                        int j1 = 255;
                        int k = (int)vector3f1.x();
                        int l = (int)vector3f1.y();
                        int i1 = (int)vector3f1.z();
                        this.lightPixels.setPixelRGBA(j, i, -16777216 | i1 << 16 | l << 8 | k);
                    }
                }

                this.lightTexture.upload();
                this.minecraft.getProfiler().pop();
            }
        }
    }
}
