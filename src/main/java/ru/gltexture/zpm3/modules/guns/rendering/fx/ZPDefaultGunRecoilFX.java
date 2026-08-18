/*
 *
 *  * zpm3forge
 *  * Copyright (C) 2026 gltexture
 *  *
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program. If not, see <https://www.gnu.org/licenses/>.
 *
 */

package ru.gltexture.zpm3.modules.guns.rendering.fx;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import ru.gltexture.zpm3.modules.guns.item.ZPBaseGun;
import ru.gltexture.zpm3.modules.guns.processing.logic.ZPDefaultGunLogicFunctions;

//@Deprecated(forRemoval = true)
public class ZPDefaultGunRecoilFX implements IZPGunRecoilFX {
    private final float[] recoilPrev;
    private final float[] recoil;
    private final float[] recoilStrengthLast;
    private final boolean[] revoilProgression;

    protected ZPDefaultGunRecoilFX() {
        this.recoilPrev = new float[] {0.0f, 0.0f};
        this.recoil = new float[] {0.0f, 0.0f};
        this.recoilStrengthLast = new float[] {0.0f, 0.0f};
        this.revoilProgression = new boolean[] {false, false};
    }

    public static ZPDefaultGunRecoilFX create() {
        return new ZPDefaultGunRecoilFX();
    }

    @Override
    public void onShot(@NotNull Player player, @NotNull ZPBaseGun baseGun, @NotNull ItemStack itemStack, ZPDefaultGunLogicFunctions.GunClientData_Shot gunFXData) {
        if (!player.equals(Minecraft.getInstance().player)) {
            return;
        }
        final int id = gunFXData.isRightHand() ? 1 : 0;
        this.recoilStrengthLast[id] = Math.min(gunFXData.recoilStrength() < 0.0f ? -1.0f : gunFXData.recoilStrength(), 16.0f);
        this.recoilPrev[id] = this.recoil[id] > 0.0f ? 1.0f : 0;
        this.recoil[id] = this.recoil[id] > 0.0f ? 0.5f : 0;
        this.revoilProgression[id] = true;
    }

    @Override
    public @Nullable Matrix4f getCurrentRecoilTransformation(@NotNull Player player, @NotNull ZPBaseGun baseGun, @NotNull ItemStack itemStack, boolean rightHand, float partialTicks) {
        final int id = rightHand ? 1 : 0;
        float translateConst = 0.05f;
        float rotateConst = 15.0f;

        final float strength = this.recoilStrengthLast[id] < 0.0f ? 0.2f : Math.max((float) Math.sqrt(this.recoilStrengthLast[id] / 3.0f), 0.5f);
        final float recoilStage = Mth.lerp(partialTicks, this.recoilPrev[id], this.recoil[id]);
        final float scale = (float) Math.pow(recoilStage, 4.0f) * strength;

        if (id == 0) {
            rotateConst *= -1.0f;
            translateConst *= -1.0f;
        }

        float yTranslation = scale * translateConst;
        if (baseGun.getGunProperties().getHeldType().equals(ZPBaseGun.GunProperties.HeldType.RIFLE) && player.getOffhandItem().isEmpty()) {
            yTranslation *= -0.5f;
        }

        Matrix4f matrix4f = new Matrix4f().identity();
        Vector3f translate = new Vector3f(0.0f, yTranslation, scale * translateConst);
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
            for (int i = 0; i < 2; i++) {
                this.recoilPrev[i] = this.recoil[i];
                if (this.revoilProgression[i]) {
                    this.recoil[i] += 0.5f;
                    if (this.recoil[i] >= 1.0f) {
                        this.recoil[i] = 1.0f;
                        this.revoilProgression[i] = false;
                    }
                } else {
                    this.recoil[i] = Math.max(this.recoil[i] - 0.5f, 0.0f);
                }
            }
        }
    }
}
