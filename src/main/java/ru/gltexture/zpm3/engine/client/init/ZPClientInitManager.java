package ru.gltexture.zpm3.engine.client.init;

import com.mojang.blaze3d.platform.Window;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.exceptions.ZPRuntimeException;

import java.util.HashSet;
import java.util.Set;

@OnlyIn(Dist.CLIENT)
public abstract class ZPClientInitManager {
    private static Set<InitRun> setInit = new HashSet<>();
    private static Set<InitRun> setDestroy = new HashSet<>();

    public static void setupRunner(InitRun runnable) {
        if (ZPClientInitManager.setInit == null) {
            throw new ZPRuntimeException("Tried to init client's function too late");
        }
        ZPClientInitManager.setInit.add(runnable);
    }

    public static void destroyRunner(InitRun runnable) {
        if (ZPClientInitManager.setDestroy == null) {
            throw new ZPRuntimeException("Tried to destroy client's function too late");
        }
        ZPClientInitManager.setDestroy.add(runnable);
    }

    public static void clearInit() {
        ZPClientInitManager.setInit = null;
    }

    public static void clearDestroy() {
        ZPClientInitManager.setDestroy = null;
    }

    public static Set<InitRun> getSetInit() {
        return ZPClientInitManager.setInit;
    }

    public static Set<InitRun> getSetDestroy() {
        return setDestroy;
    }

    @FunctionalInterface
    public interface InitRun {
        void run(@NotNull Window window);
    }
}
