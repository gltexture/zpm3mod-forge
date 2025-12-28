package ru.gltexture.zpm3.engine.core.config;

import net.minecraft.util.Mth;
import org.checkerframework.checker.units.qual.C;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.engine.core.ZPLogger;
import ru.gltexture.zpm3.engine.exceptions.ZPIOException;
import ru.gltexture.zpm3.engine.exceptions.ZPRuntimeException;
import ru.gltexture.zpm3.engine.service.ZPPath;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public final class ZPConfigurator {
    private static Map<String, List<ClassFieldData>> CACHED = null;
    private final List<ZPClassWithConfConstants> classList;

    public ZPConfigurator() {
        this.classList = new ArrayList<>();
    }

    public void addClass(ZPClassWithConfConstants classWithConfConstantsClass) {
        this.classList.add(classWithConfConstantsClass);
    }

    private int parseIntLenient(String s) {
        try {
            return (int) Math.floor(Double.parseDouble(s.trim()));
        } catch (NumberFormatException e) {
            throw new ZPIOException("Invalid int value: " + s, e);
        }
    }


    public void processConfiguration(ZPPath diskPath) {
        ZPLogger.info("Config-processing, " + diskPath);
        this.classList.forEach(e -> {
            ZPPath path = new ZPPath(diskPath, e.configName() + ".config");
            final @Nullable Map<String, ClassFieldData> readExistingFile = this.readExistingConfigFile(path);
            File file = new File(path.getFullPath());
            int i = 1;
            try (FileWriter fileWriter = new FileWriter(file)) {
                final Map<String, List<ClassFieldData>> readClassFields = CACHED == null ? this.getClassFieldDataSet(e) : CACHED;
                CACHED = readClassFields;
                fileWriter.write("#AUTO_GENERATED: " + LocalDate.now() + "\n");
                // FILE-PROCESSING
                {
                    for (Map.Entry<String, List<ClassFieldData>> entry : readClassFields.entrySet()) {
                        fileWriter.append("\n\n# =========================== (").append(entry.getKey()).append(") ===========================\n\n");
                        for (ClassFieldData classFieldData : entry.getValue()) {
                            final ClassFieldData fieldFromReadFile = readExistingFile != null ? readExistingFile.get(classFieldData.fieldName) : null;
                            Object newValue = null;
                            if (fieldFromReadFile != null) {
                                if (readExistingFile.containsKey(classFieldData.fieldName)) {
                                    newValue = fieldFromReadFile.currentValue;
                                }
                                if (!classFieldData.type.equals(fieldFromReadFile.type)) {
                                    ZPLogger.error("Param type mismatch! Param: " + classFieldData.fieldName + " Types: " + classFieldData.type + "/" + fieldFromReadFile.type);
                                    readExistingFile.replace(classFieldData.fieldName, new ClassFieldData(fieldFromReadFile.fieldName, fieldFromReadFile.group, fieldFromReadFile.description, classFieldData.type, fieldFromReadFile.defaultValue, fieldFromReadFile.currentValue));
                                }
                            }
                            fileWriter.append("\n# ").append(String.valueOf(i++)).append(". \n");
                            fileWriter.append(classFieldData.toString(newValue));
                        }
                    }
                    ZPLogger.info("Wrote Config: " + path + (readExistingFile == null ? " NEW" : " OLD"));
                }
                // CLASS-PROCESSING
                {
                    if (readExistingFile != null) {
                        for (Map.Entry<String, ClassFieldData> classFieldDatas : readExistingFile.entrySet()) {
                            ClassFieldData classFieldData = classFieldDatas.getValue();
                            Field field = e.getClass().getDeclaredField(classFieldData.fieldName());
                            if (field.isAnnotationPresent(ZPConfigurableConstant.class)) {
                                ZPConfigurableConstant zpConfigurableConstant = field.getAnnotation(ZPConfigurableConstant.class);
                                field.setAccessible(true);

                                Object currentValue = null;
                                final String readValue = classFieldData.currentValue;
                                final String type = classFieldData.type;
                                try {
                                    switch (Objects.requireNonNull(ZPConfigurableConstant.TYPES.getType(type))) {
                                        case STRING -> currentValue = readValue;
                                        case FLOAT -> currentValue = Float.parseFloat(readValue);
                                        case INT -> currentValue = this.parseIntLenient(readValue);
                                        case BOOLEAN -> currentValue = (readValue.equals("true") || readValue.equals("1")) ? Boolean.TRUE : Boolean.FALSE;
                                    }
                                    if (currentValue != null) {
                                        if (currentValue instanceof Float f) {
                                            currentValue = (float) Mth.clamp(f, zpConfigurableConstant.min(), zpConfigurableConstant.max());
                                        } else if (currentValue instanceof Integer f) {
                                            currentValue = (int) Mth.clamp(f, zpConfigurableConstant.min(), zpConfigurableConstant.max());
                                        } else if (currentValue instanceof Double f) {
                                            currentValue = (float) Mth.clamp(f, zpConfigurableConstant.min(), zpConfigurableConstant.max());
                                        }
                                        field.set(null, currentValue);
                                    }
                                    ZPLogger.info("Read config field: " + e.getClass() + " F: " + classFieldData.fieldName());
                                } catch (Exception ex) {
                                    ZPLogger.exception(new ZPIOException("Failed to read config-var " + classFieldData.fieldName + " with type " + type + ". Check the config! ", ex));
                                }
                            }
                        }
                    }
                }
            } catch (IllegalAccessException ex) {
                throw new ZPRuntimeException(ex);
            } catch (IOException | ClassCastException ex) {
                ZPLogger.exception(ex);
            } catch (NoSuchFieldException ex) {
                ZPLogger.warn("Couldn't find in " + e.getClass().getSimpleName() + " field. " + ex.getMessage());
            }
        });
    }

    private @Nullable Map<String, ClassFieldData> readExistingConfigFile(ZPPath path) {
        File file = new File(path.getFullPath());
        try {
            if (!file.exists()) {
                new File(path.getDirectory().getFullPath()).mkdirs();
                file.createNewFile();
                return null;
            }
        } catch (Exception e) {
            throw new ZPIOException("Error, while reading config: " + path, e);
        }
        final Map<String, ClassFieldData> map = new HashMap<>();
        String line = "EMPTYLINE";
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.startsWith("#") && !line.isEmpty()) {
                    final String[] strings = line.split(":");
                    final String fieldName = strings[0];
                    final String type = strings[1];
                    final String currentValueS = strings[2];
                    map.put(fieldName, new ClassFieldData(fieldName, "", "", type, null, currentValueS));
                }
            }
        } catch (Exception e) {
            throw new ZPIOException("Error, while reading config: " + path + "\n LINE: " + line, e);
        }
        return map;
    }

    public Map<String, List<ClassFieldData>> getClassFieldDataSet(ZPClassWithConfConstants classWithConfConstants) throws ZPIOException, IllegalAccessException {
        List<ClassFieldData> classFieldDataList = new ArrayList<>();
        Field[] fields = classWithConfConstants.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(ZPConfigurableConstant.class)) {
                int mods = field.getModifiers();
                if (!Modifier.isPublic(mods) || !Modifier.isStatic(mods)) {
                    throw new ZPIOException("Field " + field.getName() + " is not public static!");
                }
                ZPConfigurableConstant configurableConstant = field.getAnnotation(ZPConfigurableConstant.class);
                Object value = field.get(null);
                ClassFieldData classFieldData = new ClassFieldData(field.getName(), configurableConstant.group(), configurableConstant.description(), configurableConstant.type().getId(), value, value.toString());
                classFieldDataList.add(classFieldData);
            }
        }
        return classFieldDataList.stream().collect(Collectors.groupingBy(ClassFieldData::group));
    }

    public interface ZPClassWithConfConstants {
        String configName();
    }

    public record ClassFieldData(String fieldName, String group, String description, String type, Object defaultValue, @NotNull String currentValue) {
        public @NotNull String toString(@Nullable Object newValue) {
            StringBuilder builder = new StringBuilder();
            builder.append(this.fieldName)
                    .append(":")
                    .append(this.type)
                    .append(":")
                    .append(newValue != null ? newValue.toString() : currentValue)
                    .append("\n# ")
                    .append(this.description)
                    .append(" | Default=")
                    .append(defaultValue.toString())
                    .append("\n");
            return builder.toString();
        }
    }
}
