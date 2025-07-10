package ru.gltexture.zpm3.engine.client.rendering.entities;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ItemSupplier;


public class ZPThrowableEntityRender<T extends Entity & ItemSupplier> extends ThrownItemRenderer<T> {
    public ZPThrowableEntityRender(EntityRendererProvider.Context context) {
        super(context);
    }
}