package ru.gltexture.zpm3.engine.rendering.entities;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import ru.gltexture.zpm3.engine.objects.entities.ZPThrowableEntity;


public class ZPThrowableEntityRender extends ThrownItemRenderer<ZPThrowableEntity> {
    public ZPThrowableEntityRender(EntityRendererProvider.Context context) {
        super(context);
    }
}
