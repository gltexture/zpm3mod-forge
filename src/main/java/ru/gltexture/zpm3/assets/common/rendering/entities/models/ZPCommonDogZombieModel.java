package ru.gltexture.zpm3.assets.common.rendering.entities.models;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ColorableAgeableListModel;
import net.minecraft.client.model.FoxModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.FoxRenderer;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.instances.entities.mobs.zombies.ZPDogZombie;

@OnlyIn(Dist.CLIENT)
public class ZPCommonDogZombieModel<T extends ZPDogZombie> extends ColorableAgeableListModel<T> {
    public final ModelPart head;
    private final ModelPart realHead;
    private final ModelPart body;
    private final ModelPart rightHindLeg;
    private final ModelPart leftHindLeg;
    private final ModelPart rightFrontLeg;
    private final ModelPart leftFrontLeg;
    private final ModelPart tail;
    private final ModelPart realTail;
    private final ModelPart upperBody;

    public ZPCommonDogZombieModel(ModelPart pRoot) {
        this.head = pRoot.getChild("head");
        this.realHead = this.head.getChild("real_head");
        this.body = pRoot.getChild("body");
        this.upperBody = pRoot.getChild("upper_body");
        this.rightHindLeg = pRoot.getChild("right_hind_leg");
        this.leftHindLeg = pRoot.getChild("left_hind_leg");
        this.rightFrontLeg = pRoot.getChild("right_front_leg");
        this.leftFrontLeg = pRoot.getChild("left_front_leg");
        this.tail = pRoot.getChild("tail");
        this.realTail = this.tail.getChild("real_tail");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        float f = 13.5F;
        PartDefinition partdefinition1 = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(-1.0F, 13.5F, -7.0F));
        partdefinition1.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F).texOffs(16, 14).addBox(-2.0F, -5.0F, 0.0F, 2.0F, 2.0F, 1.0F).texOffs(16, 14).addBox(2.0F, -5.0F, 0.0F, 2.0F, 2.0F, 1.0F).texOffs(0, 10).addBox(-0.5F, -0.001F, -5.0F, 3.0F, 3.0F, 4.0F), PartPose.ZERO);
        partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(18, 14).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 9.0F, 6.0F), PartPose.offsetAndRotation(0.0F, 14.0F, 2.0F, ((float) Math.PI / 2F), 0.0F, 0.0F));
        partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(21, 0).addBox(-3.0F, -3.0F, -3.0F, 8.0F, 6.0F, 7.0F), PartPose.offsetAndRotation(-1.0F, 14.0F, -3.0F, ((float) Math.PI / 2F), 0.0F, 0.0F));
        CubeListBuilder cubelistbuilder = CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F);
        partdefinition.addOrReplaceChild("right_hind_leg", cubelistbuilder, PartPose.offset(-2.5F, 16.0F, 7.0F));
        partdefinition.addOrReplaceChild("left_hind_leg", cubelistbuilder, PartPose.offset(0.5F, 16.0F, 7.0F));
        partdefinition.addOrReplaceChild("right_front_leg", cubelistbuilder, PartPose.offset(-2.5F, 16.0F, -4.0F));
        partdefinition.addOrReplaceChild("left_front_leg", cubelistbuilder, PartPose.offset(0.5F, 16.0F, -4.0F));
        PartDefinition partdefinition2 = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, 12.0F, 8.0F, ((float) Math.PI / 5F), 0.0F, 0.0F));
        partdefinition2.addOrReplaceChild("real_tail", CubeListBuilder.create().texOffs(9, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F), PartPose.ZERO);
        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    protected @NotNull Iterable<ModelPart> headParts() {
        return ImmutableList.of(this.head);
    }

    protected @NotNull Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(this.body, this.rightHindLeg, this.leftHindLeg, this.rightFrontLeg, this.leftFrontLeg, this.tail, this.upperBody);
    }

    public void prepareMobModel(@NotNull T pEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick) {
        this.tail.yRot = 0.0F;

        this.body.setPos(0.0F, 14.0F, 2.0F);
        this.body.xRot = ((float) Math.PI / 2F);
        this.upperBody.setPos(-1.0F, 14.0F, -3.0F);
        this.upperBody.xRot = this.body.xRot;
        this.tail.setPos(-1.0F, 12.0F, 8.0F);
        this.rightHindLeg.setPos(-2.5F, 16.0F, 7.0F);
        this.leftHindLeg.setPos(0.5F, 16.0F, 7.0F);
        this.rightFrontLeg.setPos(-2.5F, 16.0F, -4.0F);
        this.leftFrontLeg.setPos(0.5F, 16.0F, -4.0F);
        this.rightHindLeg.xRot = Mth.cos(pLimbSwing * 0.6662F) * 1.4F * pLimbSwingAmount;
        this.leftHindLeg.xRot = Mth.cos(pLimbSwing * 0.6662F + (float) Math.PI) * 1.4F * pLimbSwingAmount;
        this.rightFrontLeg.xRot = Mth.cos(pLimbSwing * 0.6662F + (float) Math.PI) * 1.4F * pLimbSwingAmount;
        this.leftFrontLeg.xRot = Mth.cos(pLimbSwing * 0.6662F) * 1.4F * pLimbSwingAmount;
        this.realHead.zRot = 0.0f;
        this.upperBody.zRot = 0.0f;
        this.body.zRot = 0.0f;
        this.realTail.zRot = 0.0f;
    }

    public void setupAnim(@NotNull T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        this.head.xRot = pHeadPitch * ((float) Math.PI / 180F);
        this.head.yRot = pNetHeadYaw * ((float) Math.PI / 180F);
        //this.tail.xRot = pAgeInTicks;
    }
}