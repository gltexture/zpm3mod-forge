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

package ru.gltexture.zpm3.modules.debug.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import ru.gltexture.zpm3.engine.service.Pair;
import ru.gltexture.zpm3.engine.zones.ZPZoneManager;

public class ZPRenderLines {
    public static void drawLine(PoseStack poseStack, Vector3f start, Vector3f end, float r, float g, float b, float a) {
        Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
        Vec3 camPos = camera.getPosition();

        final Vector3f s = new Vector3f(start).sub((float) camPos.x, (float) camPos.y, (float) camPos.z);
        final Vector3f e = new Vector3f(end).sub((float) camPos.x, (float) camPos.y, (float) camPos.z);

        Matrix4f matrix = poseStack.last().pose();
        MultiBufferSource.BufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
        VertexConsumer builder = bufferSource.getBuffer(RenderType.lines());
        for (Direction direction : LevelRenderer.DIRECTIONS) {
            builder.vertex(matrix, s.x(), s.y(), s.z()).color(r, g, b, a).normal(direction.getStepX(), direction.getStepY(), direction.getStepZ()).endVertex();
            builder.vertex(matrix, e.x(), e.y(), e.z()).color(r, g, b, a).normal(direction.getStepX(), direction.getStepY(), direction.getStepZ()).endVertex();
        }
        bufferSource.endBatch(RenderType.lines());
    }

    public static void drawAABB(PoseStack poseStack, Vector3f start, Vector3f end, float r, float g, float b, float a) {
        Pair<Vector3f, Vector3f> pair = ZPZoneManager.Zone.min_max(start, end);
        final Vector3f min = pair.first();
        final Vector3f max = pair.second();
        float minX = min.x();
        float minY = min.y();
        float minZ = min.z();

        float maxX = max.x();
        float maxY = max.y();
        float maxZ = max.z();

        Vector3f p000 = new Vector3f(minX, minY, minZ);
        Vector3f p001 = new Vector3f(minX, minY, maxZ);
        Vector3f p010 = new Vector3f(minX, maxY, minZ);
        Vector3f p011 = new Vector3f(minX, maxY, maxZ);
        Vector3f p100 = new Vector3f(maxX, minY, minZ);
        Vector3f p101 = new Vector3f(maxX, minY, maxZ);
        Vector3f p110 = new Vector3f(maxX, maxY, minZ);
        Vector3f p111 = new Vector3f(maxX, maxY, maxZ);

        ZPRenderLines.drawLine(poseStack, p000, p001, r, g, b, a);
        ZPRenderLines.drawLine(poseStack, p001, p101, r, g, b, a);
        ZPRenderLines.drawLine(poseStack, p101, p100, r, g, b, a);
        ZPRenderLines.drawLine(poseStack, p100, p000, r, g, b, a);

        ZPRenderLines.drawLine(poseStack, p010, p011, r, g, b, a);
        ZPRenderLines.drawLine(poseStack, p011, p111, r, g, b, a);
        ZPRenderLines.drawLine(poseStack, p111, p110, r, g, b, a);
        ZPRenderLines.drawLine(poseStack, p110, p010, r, g, b, a);

        ZPRenderLines.drawLine(poseStack, p000, p010, r, g, b, a);
        ZPRenderLines.drawLine(poseStack, p001, p011, r, g, b, a);
        ZPRenderLines.drawLine(poseStack, p100, p110, r, g, b, a);
        ZPRenderLines.drawLine(poseStack, p101, p111, r, g, b, a);
    }
}
