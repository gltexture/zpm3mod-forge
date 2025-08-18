package ru.gltexture.zpm3.engine.nbt;

import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.engine.service.Pair;

import java.util.function.Predicate;

public abstract class ZPAbstractNBTClass <T> {
    protected final T t;

    public ZPAbstractNBTClass(T t) {
        this.t = t;
    }

    public abstract CompoundTag getTag();

    public ZPAbstractNBTClass<T> putTagInt(@NotNull Pair<@NotNull ZPTagID, @NotNull Integer> value) {
        this.getTag().putInt(value.first().id(), value.second());
        return this;
    }

    public ZPAbstractNBTClass<T> putTagBoolean(@NotNull Pair<@NotNull ZPTagID, @NotNull Boolean> value) {
        this.getTag().putBoolean(value.first().id(), value.second());
        return this;
    }

    public ZPAbstractNBTClass<T> putTagString(@NotNull Pair<@NotNull ZPTagID, @NotNull String> value) {
        this.getTag().putString(value.first().id(), value.second());
        return this;
    }

    public ZPAbstractNBTClass<T> putTagDouble(@NotNull Pair<@NotNull ZPTagID, @NotNull Double> value) {
        this.getTag().putDouble(value.first().id(), value.second());
        return this;
    }

    public ZPAbstractNBTClass<T> putTagFloat(@NotNull Pair<@NotNull ZPTagID, @NotNull Float> value) {
        this.getTag().putFloat(value.first().id(), value.second());
        return this;
    }

    public ZPAbstractNBTClass<T> putTagLong(@NotNull Pair<@NotNull ZPTagID, @NotNull Long> value) {
        this.getTag().putLong(value.first().id(), value.second());
        return this;
    }

    public int getTagInt(@NotNull ZPTagID key) {
        return this.getTag().getInt(key.id());
    }

    public boolean getTagBoolean(@NotNull ZPTagID key) {
        return this.getTag().getBoolean(key.id());
    }

    public String getTagString(@NotNull ZPTagID key) {
        return this.getTag().getString(key.id());
    }

    public double getTagDouble(@NotNull ZPTagID key) {
        return this.getTag().getDouble(key.id());
    }

    public float getTagFloat(@NotNull ZPTagID key) {
        return this.getTag().getFloat(key.id());
    }

    public long getTagLong(@NotNull ZPTagID key) {
        return this.getTag().getLong(key.id());
    }

    public boolean has(@NotNull ZPTagID key) {
        return this.getTag().contains(key.id());
    }

    public ZPAbstractNBTClass<T> incrementInt(@NotNull ZPTagID key) {
        return this.incrementInt(key, 1);
    }

    public ZPAbstractNBTClass<T> incrementInt(@NotNull ZPTagID key, int amount) {
        int current = this.getTagInt(key);
        this.getTag().putInt(key.id(), current + amount);
        return this;
    }

    public ZPAbstractNBTClass<T> decrementInt(@Nullable Predicate<Integer> decrementIf, @NotNull ZPTagID key) {
        return this.decrementInt(decrementIf, key, 1);
    }

    public ZPAbstractNBTClass<T> decrementInt(@Nullable Predicate<Integer> decrementIf, @NotNull ZPTagID key, int amount) {
        if (decrementIf != null && decrementIf.test(this.getTagInt(key))) {
            int current = this.getTagInt(key);
            this.getTag().putInt(key.id(), current - amount);
        }
        return this;
    }

    public void remove(@NotNull ZPTagID key) {
        this.getTag().remove(key.id());
    }
}
