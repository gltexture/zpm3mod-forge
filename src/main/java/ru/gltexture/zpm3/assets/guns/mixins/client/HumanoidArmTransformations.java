package ru.gltexture.zpm3.assets.guns.mixins.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import ru.gltexture.zpm3.assets.guns.item.ZPBaseGun;

public abstract class HumanoidArmTransformations {
    public static void setupAnimZp(HumanoidModel<?> model, LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        Minecraft mc = Minecraft.getInstance();
        boolean isLocalPlayer = entity.equals(mc.player);
        boolean isThirdPerson = !mc.options.getCameraType().isFirstPerson();

        if (isLocalPlayer && !isThirdPerson) {
            return;
        }

        float headYawRad = (float) Math.toRadians(netHeadYaw);
        float headPitchRad = (float) Math.toRadians(headPitch);

        ItemStack mainHand = entity.getMainHandItem();
        ItemStack offHand = entity.getOffhandItem();

        if (HumanoidArmTransformations.isPistol(mainHand)) {
            model.rightArm.xRot = -1.5F + headPitchRad;
            model.rightArm.yRot = headYawRad;
            model.rightArm.zRot = 0.0F;
            model.rightArm.x = -5.0F;
            model.rightArm.y = 2.0F;
        }

        if (HumanoidArmTransformations.isPistol(offHand)) {
            model.leftArm.xRot = -1.5F + headPitchRad;
            model.leftArm.yRot = headYawRad;
            model.leftArm.zRot = 0.0F;
            model.leftArm.x = 5.0F;
            model.leftArm.y = 2.0F;
        }
    }

    private static boolean isPistol(ItemStack stack) {
        return stack != null && stack.getItem() instanceof ZPBaseGun;
    }
}
