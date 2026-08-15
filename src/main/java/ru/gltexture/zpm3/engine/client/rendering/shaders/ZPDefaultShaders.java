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

package ru.gltexture.zpm3.engine.client.rendering.shaders;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import ru.gltexture.zpm3.engine.core.ZPLogger;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;

@OnlyIn(Dist.CLIENT)
public abstract class ZPDefaultShaders {
    public static final ZPShaderLoader.ZPShaderInstance imgui = new ZPShaderLoader.ZPShaderInstance();
    public static final ZPShaderLoader.ZPShaderInstance muzzleflash = new ZPShaderLoader.ZPShaderInstance();
    public static final ZPShaderLoader.ZPShaderInstance muzzleflash3dp = new ZPShaderLoader.ZPShaderInstance();
    public static final ZPShaderLoader.ZPShaderInstance image = new ZPShaderLoader.ZPShaderInstance();
    public static final ZPShaderLoader.ZPShaderInstance blur13 = new ZPShaderLoader.ZPShaderInstance();
    public static final ZPShaderLoader.ZPShaderInstance gun_gluing = new ZPShaderLoader.ZPShaderInstance();
    public static final ZPShaderLoader.ZPShaderInstance post_fx_sample = new ZPShaderLoader.ZPShaderInstance();
    public static final ZPShaderLoader.ZPShaderInstance post_fx_infection = new ZPShaderLoader.ZPShaderInstance();
    public static final ZPShaderLoader.ZPShaderInstance post_fx_nightvis = new ZPShaderLoader.ZPShaderInstance();
    public static final ZPShaderLoader.ZPShaderInstance post_fx_radiation = new ZPShaderLoader.ZPShaderInstance();
    public static final ZPShaderLoader.ZPShaderInstance post_fx_mask = new ZPShaderLoader.ZPShaderInstance();
    public static final ZPShaderLoader.ZPShaderInstance post_fx_adrenaline = new ZPShaderLoader.ZPShaderInstance();
    public static final ZPShaderLoader.ZPShaderInstance post_fx_bettervis = new ZPShaderLoader.ZPShaderInstance();
    public static final ZPShaderLoader.ZPShaderInstance post_fx_acid = new ZPShaderLoader.ZPShaderInstance();

    public static void init() {
        ZPLogger.info("ZP init default shaders");
        ZPShaderLoader.createNewShader(new ZPShaderLoader.ShaderData(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "imgui"), DefaultVertexFormat.POSITION_TEX_COLOR), ZPDefaultShaders.imgui);
        ZPShaderLoader.createNewShader(new ZPShaderLoader.ShaderData(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "muzzleflash"), DefaultVertexFormat.POSITION_TEX_COLOR), ZPDefaultShaders.muzzleflash);
        ZPShaderLoader.createNewShader(new ZPShaderLoader.ShaderData(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "muzzleflash3dp"), DefaultVertexFormat.POSITION_TEX_COLOR), ZPDefaultShaders.muzzleflash3dp);
        ZPShaderLoader.createNewShader(new ZPShaderLoader.ShaderData(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "image"), DefaultVertexFormat.POSITION_TEX), ZPDefaultShaders.image);
        ZPShaderLoader.createNewShader(new ZPShaderLoader.ShaderData(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "blur13"), DefaultVertexFormat.POSITION_TEX), ZPDefaultShaders.blur13);
        ZPShaderLoader.createNewShader(new ZPShaderLoader.ShaderData(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "gun_gluing"), DefaultVertexFormat.POSITION_TEX), ZPDefaultShaders.gun_gluing);
        ZPShaderLoader.createNewShader(new ZPShaderLoader.ShaderData(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "post_fx_sample"), DefaultVertexFormat.POSITION_TEX), ZPDefaultShaders.post_fx_sample);
        ZPShaderLoader.createNewShader(new ZPShaderLoader.ShaderData(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "post_fx_nightvis"), DefaultVertexFormat.POSITION_TEX), ZPDefaultShaders.post_fx_nightvis);
        ZPShaderLoader.createNewShader(new ZPShaderLoader.ShaderData(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "post_fx_radiation"), DefaultVertexFormat.POSITION_TEX), ZPDefaultShaders.post_fx_radiation);
        ZPShaderLoader.createNewShader(new ZPShaderLoader.ShaderData(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "post_fx_infection"), DefaultVertexFormat.POSITION_TEX), ZPDefaultShaders.post_fx_infection);
        ZPShaderLoader.createNewShader(new ZPShaderLoader.ShaderData(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "post_fx_mask"), DefaultVertexFormat.POSITION_TEX), ZPDefaultShaders.post_fx_mask);
        ZPShaderLoader.createNewShader(new ZPShaderLoader.ShaderData(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "post_fx_adrenaline"), DefaultVertexFormat.POSITION_TEX), ZPDefaultShaders.post_fx_adrenaline);
        ZPShaderLoader.createNewShader(new ZPShaderLoader.ShaderData(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "post_fx_bettervis"), DefaultVertexFormat.POSITION_TEX), ZPDefaultShaders.post_fx_bettervis);
        ZPShaderLoader.createNewShader(new ZPShaderLoader.ShaderData(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "post_fx_acid"), DefaultVertexFormat.POSITION_TEX), ZPDefaultShaders.post_fx_acid);
    }
}
