package ru.gltexture.zpm3.assets.common.rendering.entities.zombies;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.instances.entities.mobs.zombies.ZPMinerZombie;
import ru.gltexture.zpm3.assets.common.rendering.entities.zombies.base.ZPDefaultZombieRender;

public class ZPMinerZombieRender extends ZPDefaultZombieRender<ZPMinerZombie> {
    public ZPMinerZombieRender(EntityRendererProvider.Context p_174456_, @NotNull String path, int maxTextures) {
        super(p_174456_, path, maxTextures);
    }
}
