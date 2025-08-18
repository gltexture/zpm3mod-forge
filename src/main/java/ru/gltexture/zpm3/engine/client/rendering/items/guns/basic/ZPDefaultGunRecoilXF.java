package ru.gltexture.zpm3.engine.client.rendering.items.guns.basic;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraftforge.event.TickEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import ru.gltexture.zpm3.assets.debug.imgui.DearUITRSInterface;
import ru.gltexture.zpm3.engine.client.callbacking.ZPClientCallbacks;
import ru.gltexture.zpm3.engine.client.rendering.items.guns.fx.IZPGunRecoilFX;
import ru.gltexture.zpm3.engine.instances.guns.ZPBaseGun;

public class ZPDefaultGunRecoilXF implements IZPGunRecoilFX {
    private float[] recoilPrev;
    private float[] recoil;
    private float[] recoilStrengthLast;

    protected ZPDefaultGunRecoilXF() {
        this.recoilPrev = new float[] {0.0f, 0.0f};
        this.recoil = new float[] {0.0f, 0.0f};
        this.recoilStrengthLast = new float[] {0.0f, 0.0f};
    }

    public static ZPDefaultGunRecoilXF create() {
        return new ZPDefaultGunRecoilXF();
    }

    @Override
    public void triggerRecoil(@NotNull ZPBaseGun baseGun, ZPClientCallbacks.ZPGunShotCallback.@NotNull GunFXData gunFXData) {
        final int id = gunFXData.isRightHand() ? 1 : 0;
        this.recoilStrengthLast[id] = gunFXData.recoilStrength();
        this.recoilPrev[id] = 0;
        this.recoil[id] = 0;
    }

    @Override
    public @Nullable Matrix4f getCurrentRecoilTransformation(boolean rightHand, float partialTicks) {
        final int id = rightHand ? 1 : 0;
        float translateConst = 0.05f;
        float rotateConst = 15.0f;

        final float strength = Math.max(this.recoilStrengthLast[id] / 3.0f, 1.0f);
        final float recoilStage = Mth.lerp(partialTicks, this.recoilPrev[id], this.recoil[id]);
        final float scale = (float) Math.pow(recoilStage, 4.0f) * strength;

        if (id == 0) {
            rotateConst *= -1.0f;
            translateConst *= -1.0f;
        }

        Matrix4f matrix4f = new Matrix4f().identity();
        Vector3f translate = new Vector3f(0.0f, scale * translateConst, scale * translateConst);
        Vector3f rotate = new Vector3f(scale * rotateConst, 0.0f, 0.0f);

        matrix4f.translate(translate)
                .rotateX((float) Math.toRadians(rotate.x))
                .rotateY((float) Math.toRadians(rotate.y))
                .rotateZ((float) Math.toRadians(rotate.z));

        return matrix4f;
    }

    @Override
    public void onTick(TickEvent.@NotNull Phase phase) {
        if (phase == TickEvent.Phase.START) {
            if (Minecraft.getInstance().player != null) {
                if (DearUITRSInterface.emmitShells) {
                    ZPDefaultGunParticlesFX.emmitParticleShell(true, Minecraft.getInstance().player);
                    ZPDefaultGunParticlesFX.emmitParticleShell(false, Minecraft.getInstance().player);
                }
                if (DearUITRSInterface.emmitSmoke) {
                    ZPDefaultGunParticlesFX.emmitParticleSmoke(true, Minecraft.getInstance().player);
                    ZPDefaultGunParticlesFX.emmitParticleSmoke(false, Minecraft.getInstance().player);
                }
            }
            for (int i = 0; i < 2; i++) {
                this.recoilPrev[i] = this.recoil[i];
                if (this.recoilStrengthLast[i] > 0.0f) {
                    this.recoil[i] += 0.5f;
                    if (this.recoil[i] >= 1.0f) {
                        this.recoil[i] = 1.0f;
                        this.recoilStrengthLast[i] = 0.0f;
                    }
                } else {
                    this.recoil[i] = Math.max(this.recoil[i] - 0.5f, 0.0f);
                }
            }
        }
    }
}
