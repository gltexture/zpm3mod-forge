package ru.gltexture.zpm3.engine.client.rendering.lightmap;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.ArrayDeque;
import java.util.Deque;

@OnlyIn(Dist.CLIENT)
public class ZPLightMapModifier {
    public static ZPLightMapModifier INSTANCE = new ZPLightMapModifier();
    private final Deque<LightMapModRequest> lightMapModRequests;

    private ZPLightMapModifier() {
        this.lightMapModRequests = new ArrayDeque<>();
    }

    public void add(LightMapModRequest lightMapModRequest) {
        this.lightMapModRequests.add(lightMapModRequest);
    }

    public LightMapModRequest pop() {
        return this.lightMapModRequests.pollLast();
    }

    public Deque<LightMapModRequest> getLightMapModRequests() {
        return this.lightMapModRequests;
    }

    public record LightMapModRequest(@Nullable Vector3f rgb_ADD, @Nullable Vector3f rgb_MUL, float gamma_MUL, float gamme_ADD) {
        public LightMapModRequest(float gamma_MUL, float gamme_ADD) {
            this(null, null, gamma_MUL, gamme_ADD);
        }

        public LightMapModRequest(@Nullable Vector3f rgb_ADD, @Nullable Vector3f rgb_MUL) {
            this(rgb_ADD, rgb_MUL, 1.0f, 0.0f);
        }
    }
}
