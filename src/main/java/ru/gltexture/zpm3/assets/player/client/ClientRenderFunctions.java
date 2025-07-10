package ru.gltexture.zpm3.assets.player.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.mojang.math.Transformation;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.InteractionHand;
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
    public static Vector3f BLOCK_TRANSLATION = new Vector3f(0.6f, -0.085f, 0.06f);
    public static Vector3f BLOCK_ROTATION = new Vector3f(0.0f, 50.0f, 0.0f);
    public static Vector3f BLOCK_SCALING = new Vector3f(1.0f);

    public static void blockAnimation(AbstractClientPlayer pPlayer, float pPartialTicks, InteractionHand pHand, ItemStack pStack, PoseStack pPoseStack) {
        if (pStack.getItem() instanceof ZPItemMedicine) {
            if (pPlayer.isUsingItem() && pPlayer.getUseItemRemainingTicks() > 0 && pPlayer.getUsedItemHand() == pHand) {
                if (pStack.getUseAnimation() == UseAnim.BLOCK) {
                    if (DearUITRSInterface.ENABLE_TRS_DEBUG) {
                        ClientRenderFunctions.BLOCK_TRANSLATION = new Vector3f(DearUITRSInterface.trsPosX[0], DearUITRSInterface.trsPosY[0], DearUITRSInterface.trsPosZ[0]);
                        ClientRenderFunctions.BLOCK_ROTATION = new Vector3f(DearUITRSInterface.trsAngleX[0], DearUITRSInterface.trsAngleY[0], DearUITRSInterface.trsAngleZ[0]);
                        ClientRenderFunctions.BLOCK_SCALING = new Vector3f(DearUITRSInterface.trsScaleX[0], DearUITRSInterface.trsScaleY[0], DearUITRSInterface.trsScaleZ[0]);
                    }

                    final Matrix4f transformation = new Matrix4f().identity();
                    transformation
                            .translate(ClientRenderFunctions.BLOCK_TRANSLATION)
                            .rotateX((float) Math.toRadians(ClientRenderFunctions.BLOCK_ROTATION.x))
                            .rotateY((float) Math.toRadians(ClientRenderFunctions.BLOCK_ROTATION.y))
                            .rotateZ((float) Math.toRadians(ClientRenderFunctions.BLOCK_ROTATION.z))
                            .scale(ClientRenderFunctions.BLOCK_SCALING);
                    final Transformation transformation1 = new Transformation(transformation);
                    pPoseStack.mulPoseMatrix(transformation);
                }
            }
        }
    }
}