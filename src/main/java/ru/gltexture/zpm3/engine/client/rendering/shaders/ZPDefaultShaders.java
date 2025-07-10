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

    public static void init() {
        ZPLogger.info("ZP init default shaders");
        ZPShaderLoader.createNewShader(new ZPShaderLoader.ShaderData(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "imgui"), DefaultVertexFormat.POSITION_TEX_COLOR), ZPDefaultShaders.imgui);
    }
}
