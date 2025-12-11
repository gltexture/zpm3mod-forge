package ru.gltexture.zpm3.assets.entity.rendering.entities.misc;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ZPThrowableEntityRender<T extends Entity & ItemSupplier> extends ThrownItemRenderer<T> {
    public ZPThrowableEntityRender(EntityRendererProvider.Context context) {
        super(context);
    }
}