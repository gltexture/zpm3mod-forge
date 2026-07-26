package ru.gltexture.zpm3.engine.core.config.vars;

import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;

public class ZPConfig_DOUBLE extends ZPConfigVar<Double> {
    private final double min;
    private final double max;

    public ZPConfig_DOUBLE(Double var) {
        super(var, ZPConfigVar.DOUBLE);
        this.min = -127000.0f;
        this.max = 127000.0f;
    }

    public ZPConfig_DOUBLE(Double var, double min, double max) {
        super(var, ZPConfigVar.DOUBLE);
        this.min = min;
        this.max = max;
    }

    @Override
    public @Nullable String additionInfo() {
        return "min=" + min + ", max=" + max;
    }

    @Override
    public void setVar(Double var) {
        super.setVar(Mth.clamp(var, min, max));
    }

    public double getMin() {
        return this.min;
    }

    public double getMax() {
        return this.max;
    }
}
