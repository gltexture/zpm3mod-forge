package ru.gltexture.zpm3.assets.entity.rendering.entities.zombies;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.global.ZPConstants;
import ru.gltexture.zpm3.assets.entity.instances.mobs.zombies.ZPDogZombie;
import ru.gltexture.zpm3.assets.entity.rendering.entities.layers.ZPDogZombieItemLayer;
import ru.gltexture.zpm3.assets.entity.rendering.entities.models.ZPCommonDogZombieModel;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class ZPDogZombieRenderer extends MobRenderer<ZPDogZombie, ZPCommonDogZombieModel<ZPDogZombie>> {
    private final List<ResourceLocation> ZOMBIE_LOCATION;

    public ZPDogZombieRenderer(EntityRendererProvider.Context p_174452_) {
        super(p_174452_, new ZPCommonDogZombieModel<>(p_174452_.bakeLayer(ModelLayers.WOLF)), 0.5F);
        this.addLayer(new ZPDogZombieItemLayer(this, p_174452_.getItemInHandRenderer()));

        this.ZOMBIE_LOCATION = new ArrayList<>();
        for (int i = 0; i < ZPConstants.TOTAL_COMMON_ZOMBIE_TEXTURES; i++) {
            this.ZOMBIE_LOCATION.add(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), String.format("textures/entity/zombie_dog/wolf%d.png", i)));
        }
    }

    public void render(@NotNull ZPDogZombie pEntity, float pEntityYaw, float pPartialTicks, @NotNull PoseStack pPoseStack, @NotNull MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull ZPDogZombie pEntity) {
        return this.ZOMBIE_LOCATION.get(pEntity.getSkinID());
    }
}