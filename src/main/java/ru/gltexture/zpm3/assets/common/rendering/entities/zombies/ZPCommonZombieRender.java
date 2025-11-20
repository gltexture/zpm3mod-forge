package ru.gltexture.zpm3.assets.common.rendering.entities.zombies;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.instances.entities.mobs.zombies.ZPCommonZombie;
import ru.gltexture.zpm3.assets.common.rendering.entities.zombies.base.ZPDefaultZombieRender;

public class ZPCommonZombieRender extends ZPDefaultZombieRender<ZPCommonZombie> {
    public ZPCommonZombieRender(EntityRendererProvider.Context p_174456_, @NotNull String path, int maxTextures) {
        super(p_174456_, path, maxTextures);
    }
}
