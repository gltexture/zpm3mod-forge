package ru.gltexture.zpm3.modules.guns.mixins.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;
import ru.gltexture.zpm3.modules.guns.item.ZPBaseGun;

@Deprecated(forRemoval = true)
public abstract class ZPHumanoidArmTransformations {
    public static final float X_CONSTR_DEG_P = -60.0f;
    public static final float X_CONSTR_RAD_M = (float) (Math.PI / 8.0f);

    private static BlockHitResult rayCastBlock(Level world, Vec3 start, Vec3 end) {
        ClipContext context = new ClipContext(start, end, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, null);
        return world.clip(context);
    }

    public static boolean canEntityInSwimPosLookDown(LivingEntity player) {
        if (player.getPose() == Pose.SWIMMING) {
            final float yaw = player.getYRot() * Mth.DEG_TO_RAD;
            final float angle = 60.0f * Mth.DEG_TO_RAD;
            final float horizontal = Mth.cos(angle);
            final float vertical = -Mth.sin(angle);
            Vec3 dir = new Vec3(-Mth.sin(yaw) * horizontal, vertical, Mth.cos(yaw) * horizontal);
            Vector3f fromVec = player.getPosition(0.0f).toVector3f().add(0.0f, player.getEyeHeight(), 0.0f);
            Vector3f toVec = new Vector3f(fromVec).add(dir.toVector3f().mul(0.5f));
            BlockHitResult blockHitResult = ZPHumanoidArmTransformations.rayCastBlock(player.level(), new Vec3(fromVec), new Vec3(toVec));
            return blockHitResult.getType() != HitResult.Type.BLOCK;
        }
        return false;
    }

    public static void setupRotations(LivingEntity livingEntity, LivingEntityRenderer<?, ?> pEntityLiving, PoseStack pPoseStack, float pAgeInTicks, float pRotationYaw, float pPartialTicks) {
    }

    public static void setupAnimZpPre(HumanoidModel<?> model, LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    public static void setupAnimZpPost(HumanoidModel<?> model, LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        Minecraft mc = Minecraft.getInstance();
        final boolean isLocalPlayer = entity.equals(mc.player);
        final boolean isThirdPerson = !mc.options.getCameraType().isFirstPerson();
        final boolean swimAnim = entity.getPose() == Pose.SWIMMING;
        final boolean crouchAnim = !swimAnim && entity.isCrouching();


        if (swimAnim) {
            if (!entity.isInWater()) {
                headPitch = Math.max(headPitch, ZPHumanoidArmTransformations.X_CONSTR_DEG_P);
                if (!ZPHumanoidArmTransformations.canEntityInSwimPosLookDown(entity)) {
                    headPitch = (float) Math.min(headPitch, Math.toDegrees(ZPHumanoidArmTransformations.X_CONSTR_RAD_M));
                }
                model.head.xRot = (float) Mth.clamp(headPitch * ((float) Math.PI / 180F) - (Math.PI / 3.0f), Math.PI * -0.6f, Math.PI * -0.125f);
            } else {
                headPitch = -(float) Math.PI / 4F;
                model.head.xRot = rotlerpRad(model.swimAmount, headPitch, -(float) Math.PI / 4F);
            }
        }

        if (isLocalPlayer && !isThirdPerson) {
            return;
        }

        float headYawRad = (float) Math.toRadians(netHeadYaw);
        float headPitchRad = (float) Math.toRadians(headPitch);
        ItemStack mainHand = entity.getMainHandItem();
        ItemStack offHand = entity.getOffhandItem();

        final boolean gunIsRight = ZPHumanoidArmTransformations.isGun(mainHand);
        final boolean gunIsLeft = ZPHumanoidArmTransformations.isGun(offHand);
        final boolean rifleIsRight = ZPHumanoidArmTransformations.isRifleType(mainHand);
        final boolean rifleIsLeft = ZPHumanoidArmTransformations.isRifleType(offHand);

        final float animConstXRot = (float) (swimAnim ? -Math.PI : -Math.PI * 0.5f);
        if (gunIsRight) {
            model.rightArm.xRot = animConstXRot + headPitchRad;
            model.rightArm.yRot = headYawRad;
            model.rightArm.zRot = 0.0F;
            model.rightArm.x = -5.0F;
            model.rightArm.y = crouchAnim ? 5.0f : 2.0F;
        }
        if (gunIsLeft) {
            model.leftArm.xRot = animConstXRot + headPitchRad;
            model.leftArm.yRot = headYawRad;
            model.leftArm.zRot = 0.0F;
            model.leftArm.x = 5.0F;
            model.leftArm.y = crouchAnim ? 5.0f : 2.0F;
        }

        if (rifleIsRight) {
            model.leftArm.xRot = animConstXRot + headPitchRad;
            model.leftArm.yRot = headYawRad;
            if (!gunIsLeft && !swimAnim) {
                model.leftArm.yRot += 0.5f;
            }
            model.leftArm.zRot = 0.0F;
            model.leftArm.x = 5.0F;
            model.leftArm.y = crouchAnim ? 5.0f : 2.0F;
        }

        boolean reloadingRight = false;
        boolean reloadingLeft = false;
        if (mainHand.getItem() instanceof ZPBaseGun baseGun) {
            if (baseGun.isUnloadingOrReloading(entity, mainHand)) {
                reloadingRight = true;
                if (rifleIsRight || rifleIsLeft) {
                    reloadingLeft = true;
                }
            }
        }
        if (offHand.getItem() instanceof ZPBaseGun baseGun) {
            if (baseGun.isUnloadingOrReloading(entity, offHand)) {
                reloadingLeft = true;
            }
        }

        if (reloadingLeft) {
            if (swimAnim) {
                model.leftArm.xRot = -3.0f;
                model.leftArm.yRot = -0.9f;
                model.leftArm.zRot = -0.3f;
            } else {
                model.leftArm.xRot = -0.96f;
                model.leftArm.yRot = -0.29f;
                model.leftArm.zRot = 1.15f;
            }
        }

        if (reloadingRight) {
            if (swimAnim) {
                model.rightArm.xRot = -3.0f;
                model.rightArm.yRot = 0.9f;
                model.rightArm.zRot = 0.2f;
            } else {
                model.rightArm.xRot = -0.93f;
                model.rightArm.yRot = 0.0f;
                model.rightArm.zRot = -0.69f;
            }
        }
    }

    private static float rotlerpRad(float pAngle, float pMaxAngle, float pMul) {
        float f = (pMul - pMaxAngle) % ((float)Math.PI * 2F);
        if (f < -(float)Math.PI) {
            f += ((float)Math.PI * 2F);
        }

        if (f >= (float)Math.PI) {
            f -= ((float)Math.PI * 2F);
        }

        return pMaxAngle + pAngle * f;
    }

    public static boolean isGun(ItemStack stack) {
        return stack != null && stack.getItem() instanceof ZPBaseGun;
    }

    public static boolean isRifleType(ItemStack stack) {
        return stack != null && stack.getItem() instanceof ZPBaseGun baseGun && baseGun.getGunProperties().getHeldType().equals(ZPBaseGun.GunProperties.HeldType.RIFLE);
    }
}
