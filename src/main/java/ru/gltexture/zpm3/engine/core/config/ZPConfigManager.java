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

package ru.gltexture.zpm3.engine.core.config;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.engine.core.ZPLogger;
import ru.gltexture.zpm3.engine.core.config.vars.*;
import ru.gltexture.zpm3.engine.exceptions.ZPIOException;
import ru.gltexture.zpm3.engine.exceptions.ZPRuntimeException;
import ru.gltexture.zpm3.engine.service.ZPPath;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

public class ZPConfigManager implements IZPConfigClass {
    private static final String REGEX = "[a-zA-Z0-9._-]+";
    private final Map<Class<? extends ZPConfigConstantsClass>, ZPPath> configPathMap;
    private final Map<Class<? extends ZPConfigConstantsClass>, List<ConfigVarObjectForUI>> classConfigVarObjectForUIMap;
    private final Gson gson;

    public ZPConfigManager() {
        this.gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        this.classConfigVarObjectForUIMap = new HashMap<>();
        this.configPathMap = new HashMap<>();
    }

    private static boolean isValidConfigName(String name) {
        return name != null && name.matches(ZPConfigManager.REGEX);
    }

    @Override
    public void processConfigConstants(ZPPath zpmFiles, String configName, Class<? extends ZPConfigConstantsClass> clazz) throws IllegalAccessException, IOException {
        if (!ZPConfigManager.isValidConfigName(configName)) {
            throw new ZPRuntimeException("Invalid config name: " + configName + " / " + ZPConfigManager.REGEX);
        }
        final ZPPath pathToJson = new ZPPath(zpmFiles, configName + "_zp3_config.json");
        if (this.configPathMap.containsKey(clazz)) {
            throw new ZPRuntimeException("Already processed configuration-class: " + clazz.getSimpleName());
        }
        final Map<String, OldConfValue> existingConfigVars = this.readConfigExistingVars(pathToJson);
        List<ConfigVarObjectForUI> configVarObjectForUIList = new ArrayList<>();
        this.save(configVarObjectForUIList, existingConfigVars, pathToJson, clazz);
        this.configPathMap.put(clazz, pathToJson);
    }

    @Override
    public void rewriteConfigClass(Class<? extends ZPConfigConstantsClass> clazz) {
        if (!this.configPathMap.containsKey(clazz)) {
            throw new ZPIOException("Class " + clazz.getSimpleName() + " does not exist");
        }
        try {
            final ZPPath pathToJson = Objects.requireNonNull(this.getConfigPath(clazz));
            JsonObject root = this.readConfigJSONStructure(pathToJson);
            for (Field field : clazz.getDeclaredFields()) {
                if (!field.isAnnotationPresent(ZPVarDefinition.class)) {
                    continue;
                }
                Object fieldValue = field.get(null);
                if (!(fieldValue instanceof ZPConfigVar<? extends Serializable> var)) {
                    continue;
                }
                String fieldName = field.getName();
                if (!Objects.requireNonNull(root).has(fieldName)) {
                    continue;
                }
                JsonObject configObject = root.getAsJsonObject(fieldName);
                configObject.addProperty("value", String.valueOf(var.getVar()));
            }
            this.writeConfigJSONStructure(pathToJson, Objects.requireNonNull(root));
        } catch (Exception e) {
            throw new ZPRuntimeException(e);
        }
    }

    /*
    public void rewriteConfigClassVar(Class<? extends ZPConfigConstantsClass> clazz, String varName, ZPConfigVar<?> value) {
        if (!this.configPathMap.containsKey(clazz)) {
            throw new ZPIOException("Class " + clazz.getSimpleName() + " does not exist");
        }
        try {
            final ZPPath pathToJson = Objects.requireNonNull(this.getConfigPath(clazz));
            JsonObject object = this.readConfigJSONStructure(pathToJson);
            Objects.requireNonNull(object).getAsJsonObject(varName).addProperty("value", value.getVar().toString());
            this.writeConfigJSONStructure(pathToJson, object);
        } catch (Exception e) {
            throw new ZPRuntimeException(e);
        }
    }
*/

    private void save(@Nullable List<ConfigVarObjectForUI> configVarObjectForUIList, @Nullable Map<String, OldConfValue> existingConfig, ZPPath pathToJson, Class<? extends ZPConfigConstantsClass> clazz) throws IllegalAccessException, IOException {
        if (configVarObjectForUIList != null) {
            this.classConfigVarObjectForUIMap.put(clazz, configVarObjectForUIList);
        }
        JsonObject mainObj = new JsonObject();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(ZPVarDefinition.class)) {
                final ZPVarDefinition zpVarDefinition = field.getAnnotation(ZPVarDefinition.class);
                final int mods = field.getModifiers();
                if (!Modifier.isPublic(mods) || !Modifier.isStatic(mods) || !Modifier.isFinal(mods)) {
                    throw new ZPIOException(pathToJson + " - Config's Field " + field.getName() + " is not public static final!");
                }
                final String fieldName = field.getName();
                final Object fieldValue = field.get(null);
                if (fieldValue instanceof ZPConfigVar<? extends Serializable> var) {
                    final String origType = var.getType();
                    {
                        JsonObject jsonObject = new JsonObject();
                        String description = "(" + origType + ") " + zpVarDefinition.description();
                        if (var.additionInfo() != null) {
                            description += " | " + var.additionInfo();
                        }
                        jsonObject.add("description", new JsonPrimitive(description));
                        jsonObject.add("default", new JsonPrimitive(var.getVar().toString()));
                        if (existingConfig != null) {
                            if (existingConfig.containsKey(fieldName)) {
                                try {
                                    final boolean invalidate = existingConfig.get(fieldName).defaultV().equals(existingConfig.get(fieldName).value());
                                    if (!invalidate) {
                                        ZPLogger.trace(pathToJson + " - Config's Field " + fieldName + " invalidated");
                                        ZPConfigVar<? extends Serializable> existingConfigVar = ZPConfigManager.parse(existingConfig.get(fieldName).value(), origType);
                                        if (existingConfigVar == null) {
                                            throw new ZPIOException(pathToJson + " - Config's Field " + fieldName + " is null!");
                                        }
                                        var.setVarUnsafe(existingConfigVar.getVar());
                                    }
                                } catch (Exception e) {
                                    ZPLogger.exception(e);
                                }
                            }
                        }
                        ZPLogger.trace(pathToJson + " - Config's Field " + fieldName + " = " + var.getVar().toString());
                        jsonObject.add("value", new JsonPrimitive(var.getVar().toString()));
                        if (configVarObjectForUIList != null) {
                            configVarObjectForUIList.add(new ConfigVarObjectForUI(fieldName, description, var));
                        }
                        mainObj.add(fieldName, jsonObject);
                    }
                } else {
                    throw new ZPIOException(pathToJson + " - Config's Field " + field.getName() + " is not ZPConfigVar instance!");
                }
            }
        }
        this.writeConfigJSONStructure(pathToJson, mainObj);
    }

    private void writeConfigJSONStructure(@NotNull ZPPath pathToJson, @NotNull JsonObject mainObj) throws IOException {
        try (FileWriter fileWriter = new FileWriter(pathToJson.toFile())) {
            fileWriter.write(this.getGson().toJson(mainObj));
        }
    }

    private @Nullable JsonObject readConfigJSONStructure(@NotNull ZPPath pathToJson) {
        try (JsonReader jsonReader = this.getGson().newJsonReader(new FileReader(pathToJson.toFile()))) {
            return JsonParser.parseReader(jsonReader).getAsJsonObject();
        } catch (Exception e) {
            ZPLogger.exception(e);
        }
        return null;
    }

    private Map<String, OldConfValue> readConfigExistingVars(ZPPath pathToJson) {
        final Map<String, OldConfValue> vars = new HashMap<>();
        JsonObject jsonObject = this.readConfigJSONStructure(pathToJson);
        if (jsonObject != null) {
            for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                final String fieldName = entry.getKey();
                final JsonObject config = entry.getValue().getAsJsonObject();
                final String value = config.get("value").getAsString();
                final String def = !config.has("default") ? null : config.get("default").getAsString();
                vars.put(fieldName, new OldConfValue(value, def == null ? "" : def));
            }
        }
        return vars;
    }

    public static @Nullable ZPConfigVar<?> parse(String str, String type) {
        try {
            return switch (type) {
                case ZPConfigVar.INT -> new ZPConfig_INT(Integer.parseInt(str));
                case ZPConfigVar.DOUBLE -> new ZPConfig_DOUBLE(Double.parseDouble(str));
                case ZPConfigVar.FLOAT -> new ZPConfig_FLOAT(Float.parseFloat(str));
                case ZPConfigVar.BOOL -> new ZPConfig_BOOL(Boolean.parseBoolean(str));
                case ZPConfigVar.STRING -> new ZPConfig_STRING(str);
                default -> null;
            };
        } catch (Exception e) {
            return null;
        }
    }

    public @Nullable ZPPath getConfigPath(Class<? extends ZPConfigConstantsClass> clazz) {
        return this.configPathMap.get(clazz);
    }

    public @Nullable List<ConfigVarObjectForUI> configVarObjectsForUI(@NotNull Class<? extends ZPConfigConstantsClass> clazz) {
        return this.classConfigVarObjectForUIMap.get(clazz);
    }

    public Gson getGson() {
        return this.gson;
    }

    public record OldConfValue(String value, String defaultV) { ; }

    public record ConfigVarObjectForUI(String varName, String varDescription, ZPConfigVar<?> var) {
        @SuppressWarnings("all")
        public <E extends Serializable> ZPConfigVar<E> getVarUnsafe() {
            return (ZPConfigVar<E>) this.var();
        }

        @Override
        public @NotNull String toString() {
            return this.var().toString();
        }
    }
}
