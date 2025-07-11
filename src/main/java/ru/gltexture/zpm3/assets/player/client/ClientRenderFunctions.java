package ru.gltexture.zpm3.assets.player.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.mojang.math.Transformation;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import ru.gltexture.zpm3.assets.debug.imgui.DearUITRSInterface;
import ru.gltexture.zpm3.engine.objects.items.ZPItemMedicine;

@OnlyIn(Dist.CLIENT)
public abstract class ClientRenderFunctions {
    public static Vector3f BLOCK_TRANSLATION = new Vector3f(0.8f, 0.2f, -0.16f);
    public static Vector3f BLOCK_ROTATION = new Vector3f(-24.0f, 64.0f, 12.0f);
    public static Vector3f BLOCK_SCALING = new Vector3f(1.0f);

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
}