package ru.gltexture.zpm3.engine.core.api.events;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import ru.gltexture.zpm3.engine.client.rendering.lightmap.ZPLightMapModifier;

public abstract class ZPEventBus {
    public enum ObjectState {
        SPAWN,
        DESTROY
    }

    public enum State {
        START,
        END
    }

    public enum Run {
        PRE,
        POST
    }

    public interface IEvent {
        default boolean canBeCancelled() {
            return this instanceof Cancellable;
        }

        @SuppressWarnings("all")
        default boolean isCancelled() {
            return this.canBeCancelled() && ((Cancellable) this).isCancelled();
        }

        class EmptyEvent implements IEvent {
        }
    }

    public static abstract class Cancellable {
        private boolean isCancelled;

        public Cancellable() {
            this.isCancelled = false;
        }

        public boolean isCancelled() {
            return this.isCancelled;
        }

        public void setCancelled(boolean cancelled) {
            this.isCancelled = cancelled;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static final class PreCalcMinecraftLightMapEvent extends Cancellable implements IEvent {
        private final ZPLightMapModifier zpLightMapModifier;

        public PreCalcMinecraftLightMapEvent(@NotNull ZPLightMapModifier zpLightMapModifier) {
            this.zpLightMapModifier = zpLightMapModifier;
        }

        public ZPLightMapModifier getZpLightMapModifier() {
            return this.zpLightMapModifier;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static final class PostCalcMinecraftLightMapEvent extends Cancellable implements IEvent {
        private final ZPLightMapModifier zpLightMapModifier;
        private final Vector3f currentRGB;
        private final float currentGAMMA;

        public PostCalcMinecraftLightMapEvent(@NotNull ZPLightMapModifier zpLightMapModifier, @NotNull Vector3f currentRGB, float currentGAMMA) {
            this.zpLightMapModifier = zpLightMapModifier;
            this.currentRGB = currentRGB;
            this.currentGAMMA = currentGAMMA;
        }

        public Vector3f getCurrentRGB() {
            return this.currentRGB;
        }

        public float getCurrentGAMMA() {
            return this.currentGAMMA;
        }

        public ZPLightMapModifier getZpLightMapModifier() {
            return this.zpLightMapModifier;
        }
    }
}
/*
EventLauncher.pushEvent(new ZPEventBus.RenderOGLSceneEvent(this, frameTicking, ZPEventBus.Run.POST, toRenderObjects, toRenderLiquids), TODO);

    public static final class Class123 implements IEvent {

    }
 */