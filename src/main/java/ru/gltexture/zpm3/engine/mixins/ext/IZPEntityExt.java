package ru.gltexture.zpm3.engine.mixins.ext;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import java.util.Deque;
import java.util.Iterator;
import java.util.Objects;

public interface IZPEntityExt {
    int getAcidLevel();
    int getIntoxicationLevel();

    void setAcidLevel(int acidLevel);
    void setIntoxicationLevel(int intoxicationLevel);

    void defineZPSyncData();

    boolean touchesAcidBlock();
    boolean touchesToxicBlock();

    Deque<Snapshot> getAabbDeque();

    default AABB getAABBWithLagCompensation(@NotNull Entity entity, @NotNull ServerPlayer serverPlayer) {
        int ping;
        if (serverPlayer instanceof IZPPlayerMixinExt ext) {
            ping = ext.getPing();
        } else {
            ping = serverPlayer.connection.getPlayer().latency;
        }
        long targetTime = System.currentTimeMillis() - ping / 2L;
        for (Snapshot s : this.getAabbDeque()) {
            if (s.timeMillis <= targetTime) {
                return s.box();
            }
        }
        return this.getAabbDeque().isEmpty() ? entity.getBoundingBox() : Objects.requireNonNull(this.getAabbDeque().peekLast()).box();
    }

    default void addAcidLevel(int acidLevel) {
        this.setAcidLevel(this.getAcidLevel() + acidLevel);
    }

    default void addIntoxicationLevel(int intoxicationLevel) {
        this.setIntoxicationLevel(this.getIntoxicationLevel() + intoxicationLevel);
    }

    record Snapshot(long timeMillis, AABB box) {}
}
