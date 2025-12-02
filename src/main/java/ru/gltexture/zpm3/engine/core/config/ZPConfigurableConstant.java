package ru.gltexture.zpm3.engine.core.config;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ZPConfigurableConstant {
    @NotNull String description();
    @NotNull String group();
    @NotNull TYPES type();

    double min() default Double.NEGATIVE_INFINITY;
    double max() default Double.POSITIVE_INFINITY;

    enum TYPES {
        FLOAT("FLOAT"),
        STRING("STRING"),
        BOOLEAN("BOOLEAN"),
        INT("INT");

        public static @Nullable TYPES getType(String id) {
            switch (id) {
                case "FLOAT" -> {
                    return FLOAT;
                }
                case "STRING" -> {
                    return STRING;
                }
                case "BOOLEAN" -> {
                    return BOOLEAN;
                }
                case "INT" -> {
                    return INT;
                }
                default -> {
                    return null;
                }
            }
        }

        private final String id;

        TYPES(String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }
    }
}
