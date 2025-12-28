package ru.gltexture.zpm3.assets.guns.rendering.tracer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL46;
import ru.gltexture.zpm3.assets.common.global.ZPConstants;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public final class ZPBulletTracerManager {
    public static ZPBulletTracerManager INSTANCE = new ZPBulletTracerManager();

    private final Set<Tracer> tracerSet;
    private float tracerSpeed;

    private ZPBulletTracerManager() {
        this.tracerSet = new HashSet<>();
        this.tracerSpeed = 256.0f;
    }

    public boolean addNew(@NotNull Vector3f start, @NotNull Vector3f end) {
        if (!ZPConstants.RENDER_BULLET_TRACERS) {
            return false;
        }
        if (start.distance(end) <= 8.0f) {
            return false;
        }
        this.tracerSet.add(new Tracer(new TracerPath(start, end)));
        return true;
    }

    public void renderAll(PoseStack poseStack, float partialTicks, float deltaTicks) {
        Iterator<Tracer> tracerIterator = this.tracerSet.iterator();
        while (tracerIterator.hasNext()) {
            Tracer tracer = tracerIterator.next();
            ZPBulletTracerManager.drawShortLine(poseStack, tracer.getTracerPath().start, tracer.getTracerPath().end, tracer.getCurrentPos(), 1.0f, 1.0f, 0.85f, 0.65f);
            final float speed = this.tracerSpeed;
            final float distance = tracer.getTracerPath().end.distance(tracer.getTracerPath().start);
            final float deltaProgress = (speed / distance) * deltaTicks;
            tracer.setCurrentPos(tracer.getCurrentPos() + deltaProgress);

            if (tracer.getCurrentPos() >= 1.0f) {
                tracerIterator.remove();
            }
        }
    }

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

    public static void drawShortLine(PoseStack poseStack, Vector3f start, Vector3f end, float pos, float r, float g, float b, float a) {
        Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
        Vec3 camPos = camera.getPosition();

        start = new Vector3f(start).lerp(end, pos);
        end = new Vector3f(start).add(new Vector3f(end).sub(start).normalize().mul(2.0f));

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

    public float getTracerSpeed() {
        return this.tracerSpeed;
    }

    public ZPBulletTracerManager setTracerSpeed(float tracerSpeed) {
        this.tracerSpeed = tracerSpeed;
        return this;
    }

    public record TracerPath(@NotNull Vector3f start, @NotNull Vector3f end) { ; }

    public static class Tracer {
        private final TracerPath tracerPath;
        private float currentPos;

        public Tracer(TracerPath tracerPath) {
            this.tracerPath = tracerPath;
            this.currentPos = 0.0f;
        }

        public TracerPath getTracerPath() {
            return this.tracerPath;
        }

        public float getCurrentPos() {
            return this.currentPos;
        }

        public Tracer setCurrentPos(float currentPos) {
            this.currentPos = currentPos;
            return this;
        }
    }
}
