package ru.gltexture.zpm3.engine.zones.vars;

import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public non-sealed class ZPZoneIntVar extends ZPZoneVariable<Integer> {
    private final Integer min;
    private final Integer max;

    public ZPZoneIntVar(@NotNull String variableId, @NotNull Integer t, @NotNull Integer min, @NotNull Integer max) {
        super(variableId, t);
        this.min = min;
        this.max = max;
    }

    @Override
    public @Nullable String additionalChatMsh() {
        return "Value was clamped between " + this.min + " and " + this.max;
    }

    @Override
    public String toString() {
        return this.getVariableId() + "=" + this.getValue();
    }

    @Override
    public Integer getValue() {
        return Mth.clamp(super.getValue(), this.min, this.max);
    }

    public Integer getMin() {
        return this.min;
    }

    public Integer getMax() {
        return this.max;
    }
}