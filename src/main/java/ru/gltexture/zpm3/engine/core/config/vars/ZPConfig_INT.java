package ru.gltexture.zpm3.engine.core.config.vars;

import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;

public class ZPConfig_INT extends ZPConfigVar<Integer> {
    private final int min;
    private final int max;

    public ZPConfig_INT(Integer var) {
        super(var, ZPConfigVar.INT);
        this.min = -127000;
        this.max = 127000;
    }

    public ZPConfig_INT(Integer var, int min, int max) {
        super(var, ZPConfigVar.INT);
        this.min = min;
        this.max = max;
    }

    @Override
    public @Nullable String additionInfo() {
        return "min=" + min + ", max=" + max;
    }

    @Override
    public void setVar(Integer var) {
        super.setVar(Mth.clamp(var, min, max));
    }

    public int getMin() {
        return this.min;
    }

    public int getMax() {
        return this.max;
    }
}
