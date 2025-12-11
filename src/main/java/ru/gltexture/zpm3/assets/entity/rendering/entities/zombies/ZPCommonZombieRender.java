package ru.gltexture.zpm3.assets.entity.rendering.entities.zombies;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.entity.instances.mobs.zombies.ZPCommonZombie;
import ru.gltexture.zpm3.assets.entity.rendering.entities.zombies.base.ZPDefaultZombieRender;

public class ZPCommonZombieRender extends ZPDefaultZombieRender<ZPCommonZombie> {
    public ZPCommonZombieRender(EntityRendererProvider.Context p_174456_, @NotNull String path, int maxTextures) {
        super(p_174456_, path, maxTextures);
    }
}
