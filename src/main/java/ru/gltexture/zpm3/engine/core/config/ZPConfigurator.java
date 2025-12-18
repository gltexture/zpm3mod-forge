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
                            Object newValue = null;
                            if (readExistingFile != null && readExistingFile.containsKey(classFieldData.fieldName)) {
                                newValue = readExistingFile.get(classFieldData.fieldName).currentValue;
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

                                Object value = classFieldData.currentValue;
                                if (value instanceof Float f) {
                                    value = (float) Mth.clamp(f, zpConfigurableConstant.min(), zpConfigurableConstant.max());
                                } else if (value instanceof Integer f) {
                                    value = (int) Mth.clamp(f, zpConfigurableConstant.min(), zpConfigurableConstant.max());
                                } else if (value instanceof Double f) {
                                    value = (float) Mth.clamp(f, zpConfigurableConstant.min(), zpConfigurableConstant.max());
                                }

                                field.set(null, value);
                                ZPLogger.info("Read config field: " + e.getClass() + " F: " + classFieldData.fieldName());
                            }
                        }
                    }
                }
            } catch (IllegalAccessException | IllegalArgumentException ex) {
                throw new ZPRuntimeException(ex);
            } catch (IOException | ClassCastException ex) {
                ex.printStackTrace(System.err);
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
                    Object currentValue = null;
                    switch (Objects.requireNonNull(ZPConfigurableConstant.TYPES.getType(type))) {
                        case STRING -> currentValue = currentValueS;
                        case FLOAT -> currentValue = Float.parseFloat(currentValueS);
                        case INT -> currentValue = Integer.parseInt(currentValueS);
                        case BOOLEAN -> currentValue = currentValueS.equals("true") ? Boolean.TRUE : Boolean.FALSE;
                        default -> throw new ZPIOException("UNKNOWN TYPE: " + type);
                    }
                    map.put(fieldName, new ClassFieldData(fieldName, "", "", type, null, Objects.requireNonNull(currentValue)));
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
                ClassFieldData classFieldData = new ClassFieldData(field.getName(), configurableConstant.group(), configurableConstant.description(), configurableConstant.type().getId(), value, value);
                classFieldDataList.add(classFieldData);
            }
        }
        return classFieldDataList.stream().collect(Collectors.groupingBy(ClassFieldData::group));
    }

    public interface ZPClassWithConfConstants {
        String configName();
    }

    public record ClassFieldData(String fieldName, String group, String description, String type, Object defaultValue, @NotNull Object currentValue) {

        public @NotNull String toString(@Nullable Object newValue) {
            return this.fieldName + ":" + this.type + ":" + (newValue != null ? newValue.toString() : currentValue.toString()) + "\n# " + this.description + " | Default=" + defaultValue.toString() + "\n";
        }
    }
}
