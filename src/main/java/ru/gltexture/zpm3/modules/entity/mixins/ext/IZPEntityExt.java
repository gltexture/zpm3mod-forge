package ru.gltexture.zpm3.modules.entity.mixins.ext;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.modules.player.mixins.ext.IZPPlayerMixinExt;

import java.util.Deque;
import java.util.Objects;

public interface IZPEntityExt {
    int zpm3forge$getAcidLevel();
    void zpm3forge$setAcidLevel(int acidLevel);

    void zpm3forge$defineZPSyncData();


    Deque<Snapshot> zpm3forge$getAabbDeque();

    default AABB getAABBWithLagCompensation(@NotNull Entity entity, @NotNull ServerPlayer serverPlayer) {
        int ping;
        if (serverPlayer instanceof IZPPlayerMixinExt ext) {
            ping = ext.zpm3forge$getPing();
        } else {
            ping = serverPlayer.connection.getPlayer().latency;
        }
        long targetTime = System.currentTimeMillis() - ping / 2L;
        for (Snapshot s : this.zpm3forge$getAabbDeque()) {
            if (s.timeMillis <= targetTime) {
                return s.box();
            }
        }
        return this.zpm3forge$getAabbDeque().isEmpty() ? entity.getBoundingBox() : Objects.requireNonNull(this.zpm3forge$getAabbDeque().peekLast()).box();
    }

    default void addAcidLevel(int acidLevel) {
        this.zpm3forge$setAcidLevel(this.zpm3forge$getAcidLevel() + acidLevel);
    }

    record Snapshot(long timeMillis, AABB box) {}
}
