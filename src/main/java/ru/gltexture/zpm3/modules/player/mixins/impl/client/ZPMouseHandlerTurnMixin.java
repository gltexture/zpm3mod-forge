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

package ru.gltexture.zpm3.modules.player.mixins.impl.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Pose;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.gltexture.zpm3.modules.entity.util.ZPEntityUtil;
import ru.gltexture.zpm3.modules.guns.mixins.client.ZPHumanoidArmTransformations;

@OnlyIn(Dist.CLIENT)
@Mixin(MouseHandler.class)
public class ZPMouseHandlerTurnMixin {
    //TODO REMAKE
    @Inject(method = "turnPlayer", at = @At("TAIL"))
    @SuppressWarnings("removal")
    public void turn(CallbackInfo ci) {
        final LocalPlayer localPlayer = Minecraft.getInstance().player;
        if (localPlayer != null && localPlayer.getPose() == Pose.SWIMMING) {
            if (!ZPEntityUtil.isCollidingWithFluid(localPlayer, FluidTags.WATER)) {
                if (Minecraft.getInstance().getCameraEntity() != null) {
                    Minecraft.getInstance().getCameraEntity().setXRot(Math.max(Minecraft.getInstance().getCameraEntity().getXRot(), ZPHumanoidArmTransformations.X_CONSTR_DEG_P));
                    if (!ZPHumanoidArmTransformations.canEntityInSwimPosLookDown(localPlayer)) {
                        Minecraft.getInstance().getCameraEntity().setXRot((float) Math.min(Minecraft.getInstance().getCameraEntity().getXRot(), Math.toDegrees(ZPHumanoidArmTransformations.X_CONSTR_RAD_M)));
                    }
                }
            }
        }
    }
}
