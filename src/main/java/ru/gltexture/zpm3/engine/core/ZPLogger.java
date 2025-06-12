package ru.gltexture.zpm3.engine.core;

public final class ZPLogger {
    public static void info(String message) {
        ZombiePlague3.LOGGER.info(message);
    }

    public static void warn(String message) {
        ZombiePlague3.LOGGER.warn(message);
    }

    public static void error(String message) {
        ZombiePlague3.LOGGER.error(message);
    }

    public static void exception(Exception e) {
        ZombiePlague3.LOGGER.error("Process caught an exception");
        System.err.println("\n****************************************Exception****************************************");
        e.printStackTrace(System.err);
        System.err.println("\n****************************************Exception****************************************");
    }
}
