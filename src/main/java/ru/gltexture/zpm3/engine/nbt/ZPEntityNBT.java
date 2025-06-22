package ru.gltexture.zpm3.engine.nbt;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.utils.Pair;

public record ZPEntityNBT(@NotNull Entity entity) {
    public static final String PERSISTED_NBT_TAG = "ZPM3EntityPersisted";

    public CompoundTag getTag() {
        CompoundTag data = this.entity.getPersistentData();
        return data.getCompound(ZPEntityNBT.PERSISTED_NBT_TAG);
    }

    public ZPEntityNBT putTagInt(@NotNull Pair<@NotNull ZPEntityTag, @NotNull Integer> value) {
        this.getTag().putInt(value.getFirst().id(), value.getSecond());
        return this;
    }

    public ZPEntityNBT putTagBoolean(@NotNull Pair<@NotNull ZPEntityTag, @NotNull Boolean> value) {
        this.getTag().putBoolean(value.getFirst().id(), value.getSecond());
        return this;
    }

    public ZPEntityNBT putTagString(@NotNull Pair<@NotNull ZPEntityTag, @NotNull String> value) {
        this.getTag().putString(value.getFirst().id(), value.getSecond());
        return this;
    }

    public ZPEntityNBT putTagDouble(@NotNull Pair<@NotNull ZPEntityTag, @NotNull Double> value) {
        this.getTag().putDouble(value.getFirst().id(), value.getSecond());
        return this;
    }

    public ZPEntityNBT putTagFloat(@NotNull Pair<@NotNull ZPEntityTag, @NotNull Float> value) {
        this.getTag().putFloat(value.getFirst().id(), value.getSecond());
        return this;
    }

    public ZPEntityNBT putTagLong(@NotNull Pair<@NotNull ZPEntityTag, @NotNull Long> value) {
        this.getTag().putLong(value.getFirst().id(), value.getSecond());
        return this;
    }

    public int getTagInt(@NotNull ZPEntityTag key) {
        return this.getTag().getInt(key.id());
    }

    public boolean getTagBoolean(@NotNull ZPEntityTag key) {
        return this.getTag().getBoolean(key.id());
    }

    public String getTagString(@NotNull ZPEntityTag key) {
        return this.getTag().getString(key.id());
    }

    public double getTagDouble(@NotNull ZPEntityTag key) {
        return this.getTag().getDouble(key.id());
    }

    public float getTagFloat(@NotNull ZPEntityTag key) {
        return this.getTag().getFloat(key.id());
    }

    public long getTagLong(@NotNull ZPEntityTag key) {
        return this.getTag().getLong(key.id());
    }

    public boolean has(@NotNull ZPEntityTag key) {
        return this.getTag().contains(key.id());
    }

    public ZPEntityNBT incrementInt(@NotNull ZPEntityTag key) {
        return this.incrementInt(key, 1);
    }

    public ZPEntityNBT incrementInt(@NotNull ZPEntityTag key, int amount) {
        int current = this.getTagInt(key);
        this.getTag().putInt(key.id(), current + amount);
        return this;
    }

    public ZPEntityNBT decrementInt(@NotNull ZPEntityTag key) {
        return this.decrementInt(key, 1);
    }

    public ZPEntityNBT decrementInt(@NotNull ZPEntityTag key, int amount) {
        int current = this.getTagInt(key);
        this.getTag().putInt(key.id(), current - amount);
        return this;
    }

    public void remove(@NotNull ZPEntityTag key) {
        this.getTag().remove(key.id());
    }
}
