package ru.gltexture.zpm3.engine.utils;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;

import java.io.*;
import java.util.stream.Collectors;

public abstract class ZPUtils {
    public static String readTextFromJar(String path) throws IOException {
        try (InputStream input = ZombiePlague3.class.getClassLoader().getResourceAsStream(path)) {
            if (input == null) {
                throw new FileNotFoundException("Resource not found: " + path);
            }
            return new BufferedReader(new InputStreamReader(input)).lines().collect(Collectors.joining("\n"));
        }
    }

    public static void onlyClient(Runnable runnable) {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> runnable);
    }

    public static void onlyServer(Runnable runnable) {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> runnable);
    }
}
