/*
 *
 *  * zpm3forge
 *  * Copyright (C) 2026 gltexture
 *  *
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program. If not, see <https://www.gnu.org/licenses/>.
 *
 */

package ru.gltexture.zpm3.engine.core;

public final class ZPLogger {
    public static void info(String message) {
        ZombiePlague3.LOGGER.info(message);
    }

    public static void trace(String message) {
        ZombiePlague3.LOGGER.trace(message);
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
