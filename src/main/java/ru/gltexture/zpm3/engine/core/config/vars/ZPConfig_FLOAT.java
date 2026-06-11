package ru.gltexture.zpm3.engine.core.config.vars;

import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;

public class ZPConfig_FLOAT extends ZPConfigVar<Float> {
    private final float min;
    private final float max;

    public ZPConfig_FLOAT(Float var) {
        super(var, ZPConfigVar.FLOAT);
        this.min = -127000.0f;
        this.max = 127000.0f;
    }

    public ZPConfig_FLOAT(Float var, float min, float max) {
        super(var, ZPConfigVar.FLOAT);
        this.min = min;
        this.max = max;
    }

    @Override
    public @Nullable String additionInfo() {
        return "min=" + min + ", max=" + max;
    }

    @Override
    public void setVar(Float var) {
        super.setVar(Mth.clamp(var, min, max));
    }

    public float getMin() {
        return this.min;
    }

    public float getMax() {
        return this.max;
    }
}
