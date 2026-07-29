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

package ru.gltexture.zpm3.modules.debug.events;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import ru.gltexture.zpm3.modules.debug.render.ZPRenderLines;
import ru.gltexture.zpm3.modules.entity.instances.mobs.zombies.ZPAbstractZombie;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.events.ZPForgeEventHandlerClass;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class ZPRenderStuffEvent implements ZPForgeEventHandlerClass {
    private static List<LineRequest> lineRequestList = new ArrayList<>();

    public ZPRenderStuffEvent() {
    }

    @Override
    public @NotNull ZPSide getSide() {
        return ZPSide.CLIENT;
    }

    @Override
    public @NotNull Mod.EventBusSubscriber.Bus getBus() {
        return Mod.EventBusSubscriber.Bus.FORGE;
    }

    @SubscribeEvent
    public static void onRenderWorld(RenderLevelStageEvent event) {
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_TRANSLUCENT_BLOCKS) {
            ZPRenderStuffEvent.lineRequestList.forEach((e) -> {
                ZPRenderLines.drawLine(event.getPoseStack(), e.start, e.end, 1.0f, 0.0f, 0.0f, 1.0f);
            });
            ZPRenderStuffEvent.lineRequestList.clear();
            if (false && Minecraft.getInstance().player != null) {
                Minecraft.getInstance().level.getEntitiesOfClass(ZPAbstractZombie.class, new AABB(Minecraft.getInstance().player.getOnPos()).inflate(32.0f)).forEach(e -> ZPRenderStuffEvent.renderZombieLinesToMine(event.getPoseStack(), e));
            }
        }
    }

    private static void renderZombieLinesToMine(PoseStack poseStack, ZPAbstractZombie abstractZombie) {
        int id = abstractZombie.getEntityData().get(ZPAbstractZombie.DEBUG_TARGET_ID);
        Entity target = id < 0 ? null : abstractZombie.level().getEntity(id);

        if (target != null) {
            final Vector3f targetPos = target.position().toVector3f().add(0.0f, (float) ((target.getBoundingBox().maxY - target.getBoundingBox().minY) * 0.5f), 0.0f);
            final float yHalf = (float) ((abstractZombie.getBoundingBox().maxY - abstractZombie.getBoundingBox().minY) / 2.0f);
            final float yQuat = yHalf * 0.25f;
            final Vector3f mobCenter = abstractZombie.position().toVector3f().add(0.0f, yHalf, 0.0f);
            final float dy = (float) (targetPos.y - abstractZombie.position().y);
            final Vector3f rayCenter = mobCenter.add(0.0f, Mth.clamp(dy * 0.3f, -yQuat, yQuat), 0.0f);
            final Vector3f up = new Vector3f(0.0f, 1.0f, 0.0f);

            float dirLength = 2.0f;

            Vector3f lookAt = (new Vector3f(targetPos).sub(rayCenter)).normalize();
            Vector3f right = new Vector3f(lookAt).cross(up).normalize();

            final Vector3f angle1 = new Vector3f(lookAt);
            final Vector3f angle2 = new Vector3f(lookAt).add(new Vector3f(right).mul(0.5f));
            final Vector3f angle3 = new Vector3f(lookAt).add(new Vector3f(right).mul(-0.5f));
            final Vector3f angle4 = new Vector3f(lookAt).add(new Vector3f(up).mul(0.5f));
            final Vector3f angle5 = new Vector3f(lookAt).add(new Vector3f(up).mul(-0.5f));
            final Vector3f angle6 = new Vector3f(lookAt).mul(1.0f, 0.0f, 1.0f);

            final Vector3f end1 = new Vector3f(rayCenter).add(angle1.normalize().mul(dirLength));
            final Vector3f end2 = new Vector3f(rayCenter).add(angle2.normalize().mul(dirLength));
            final Vector3f end3 = new Vector3f(rayCenter).add(angle3.normalize().mul(dirLength));
            final Vector3f end4 = new Vector3f(rayCenter).add(angle4.normalize().mul(dirLength));
            final Vector3f end5 = new Vector3f(rayCenter).add(angle5.normalize().mul(dirLength));
            Vector3f vector6N = angle6.normalize();
            final Vector3f end6 = new Vector3f(rayCenter).add(new Vector3f(vector6N).mul(1.5f));

            ZPRenderLines.drawLine(poseStack, rayCenter, end1,1.0f, 0.0f, 0.0f, 1.0f);
            ZPRenderLines.drawLine(poseStack, rayCenter, end2,1.0f, 1.0f, 0.0f, 1.0f);
            ZPRenderLines.drawLine(poseStack, rayCenter, end3,1.0f, 1.0f, 0.0f, 1.0f);
            ZPRenderLines.drawLine(poseStack, rayCenter, end4,1.0f, 1.0f, 0.0f, 1.0f);
            ZPRenderLines.drawLine(poseStack, rayCenter, end5,1.0f, 1.0f, 0.0f, 1.0f);

            if (Math.abs(vector6N.dot(lookAt)) < 0.7f) {
                ZPRenderLines.drawLine(poseStack, rayCenter, end6, 1.0f, 0.0f, 1.0f, 1.0f);
            }
        }
    }

    public static void addNewLineToDraw(@NotNull LineRequest lineRequest) {
        ZPRenderStuffEvent.lineRequestList.add(lineRequest);
    }

    public record LineRequest(@NotNull Vector3f start, @NotNull Vector3f end) {};
}