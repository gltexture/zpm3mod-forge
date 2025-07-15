package ru.gltexture.zpm3.assets.player.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Vector3f;
import ru.gltexture.zpm3.assets.common.utils.ZPCommonClientUtils;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;
import ru.gltexture.zpm3.engine.instances.items.ZPItemMedicine;

@OnlyIn(Dist.CLIENT)
public abstract class ClientRenderFunctions {
    public static void blockAnimation(AbstractClientPlayer pPlayer, float pPartialTicks, InteractionHand pHand, ItemStack pStack, PoseStack pPoseStack) {
        if (pStack.getItem() instanceof ZPItemMedicine) {
            if (pPlayer.isUsingItem() && pPlayer.getUseItemRemainingTicks() > 0 && pPlayer.getUsedItemHand() == pHand) {
                if (pStack.getUseAnimation() == UseAnim.BLOCK) {
                    ClientRenderFunctions.applyMedicineTransform(pPlayer, pPoseStack, pPartialTicks, (pHand.equals(InteractionHand.OFF_HAND) ? HumanoidArm.LEFT : HumanoidArm.RIGHT), pStack);
                }
            }
        }
    }

    private static void applyMedicineTransform(AbstractClientPlayer pPlayer, PoseStack pPoseStack, float pPartialTicks, HumanoidArm pHand, ItemStack pStack) {
        float f = (float) pPlayer.getUseItemRemainingTicks() - pPartialTicks + 1.0F;
        float f1 = f / (float)pStack.getUseDuration();

        float f3 = 1.0F - (float)Math.pow(f1, 27.0D);
        int i = pHand == HumanoidArm.RIGHT ? 1 : -1;
        pPoseStack.translate(f3 * 1.0F * (float)i, f3 * -0.2F, f3 * 0.1F);
        pPoseStack.mulPose(Axis.YP.rotationDegrees((float)i * f3 * 80.0F));
        pPoseStack.mulPose(Axis.XP.rotationDegrees(f3 * 10.0F));
        pPoseStack.mulPose(Axis.ZP.rotationDegrees((float)i * f3 * 10.0F));
    }

    public static void addAcidParticles(Entity entity) {
        int maxParticles = 1 + (int) Math.floor(entity.getBbWidth() * entity.getBbHeight());
        maxParticles = Math.min(maxParticles, 6);

        for (int i = 0; i < maxParticles; ++i) {
            final Vector3f randomVector = ZPRandom.instance.randomVector3f(0.15f, new Vector3f(0.3f, 0.1f, 0.3f));
            final Vector3f position = entity.position().toVector3f().add(0.0f, ZPRandom.instance.randomFloat(entity.getBbHeight()), 0.0f);
            position.add(ZPRandom.instance.randomVector3f(0.3f, new Vector3f(0.6f, 0.3f, 0.6f)));
            ZPCommonClientUtils.emmitAcidParticle(2.2f + ZPRandom.getRandom().nextFloat(0.3f), position, new Vector3f(randomVector.x, (randomVector.y * 0.1f) + 0.05f, randomVector.z));
        }
    }
}