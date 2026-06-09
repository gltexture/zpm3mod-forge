package ru.gltexture.zpm3.engine.core.config;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.engine.core.ZPLogger;
import ru.gltexture.zpm3.engine.core.config.vars.*;
import ru.gltexture.zpm3.engine.exceptions.ZPIOException;
import ru.gltexture.zpm3.engine.service.ZPPath;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

public class ZPConfigManager {
    private final Gson gson;

    public ZPConfigManager() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public void processConfigConstants(ZPPath zpmFiles, String configName, Class<? extends ZPConfigConstantsClass> clazz) throws IllegalAccessException, IOException {
        final ZPPath pathToJson = new ZPPath(zpmFiles, configName + "_zp3_config.json");

        final Map<String, String> existingConfig = this.readConfig(pathToJson);

        JsonObject mainObj = new JsonObject();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(ZPVarDefinition.class)) {
                final ZPVarDefinition zpVarDefinition = field.getAnnotation(ZPVarDefinition.class);
                int mods = field.getModifiers();
                if (!Modifier.isPublic(mods) || !Modifier.isStatic(mods) || !Modifier.isFinal(mods)) {
                    throw new ZPIOException(configName + " - Config's Field " + field.getName() + " is not public static final!");
                }
                final String fieldName = field.getName();
                final Object fieldValue = field.get(null);
                if (fieldValue instanceof ZPConfigVar<? extends Serializable> zpConfigVar) {
                    final String origType = zpConfigVar.getType();
                    {
                        JsonObject jsonObject = new JsonObject();
                        String description = "(" + origType + ") " + zpVarDefinition.description();
                        if (zpConfigVar.additionInfo() != null) {
                            description += " | " + zpConfigVar.additionInfo();
                        }
                        jsonObject.add("description", new JsonPrimitive(description));
                        jsonObject.add("default", new JsonPrimitive(zpConfigVar.getVar().toString()));
                        if (existingConfig.containsKey(fieldName)) {
                            try {
                                ZPConfigVar<? extends Serializable> existingConfigVar = ZPConfigManager.parse(existingConfig.get(fieldName), origType);
                                if (existingConfigVar == null) {
                                    throw new ZPIOException(configName + " - Config's Field " + fieldName + " is null!");
                                }
                                zpConfigVar.setVarUnsafe(existingConfigVar.getVar());
                                zpConfigVar = existingConfigVar;
                            } catch (Exception e) {
                                ZPLogger.exception(e);
                            }
                        }
                        ZPLogger.trace(configName + " - Config's Field " + fieldName + " = " + zpConfigVar.getVar().toString());
                        jsonObject.add("value", new JsonPrimitive(zpConfigVar.getVar().toString()));
                        mainObj.add(fieldName, jsonObject);
                    }
                } else {
                    throw new ZPIOException(configName + " - Config's Field " + field.getName() + " is not ZPConfigVar instance!");
                }
            }
        }

        try (FileWriter fileWriter = new FileWriter(pathToJson.toFile())) {
            fileWriter.write(this.getGson().toJson(mainObj));
        }
    }

    private Map<String, String> readConfig(ZPPath pathToJson) {
        final Map<String, String> vars = new HashMap<>();
        try (JsonReader jsonReader = this.getGson().newJsonReader(new FileReader(pathToJson.toFile()))) {
            JsonArray jsonArray = JsonParser.parseReader(jsonReader).getAsJsonArray();
            for (JsonElement element : jsonArray) {
                JsonObject wrapper = element.getAsJsonObject();
                for (Map.Entry<String, JsonElement> entry : wrapper.entrySet()) {
                    String fieldName = entry.getKey();
                    JsonObject config = entry.getValue().getAsJsonObject();
                    String value = config.get("value").getAsString();
                    vars.put(fieldName, value);
                }
            }
        } catch (Exception e) {
            ZPLogger.exception(e);
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

    public Gson getGson() {
        return this.gson;
    }
}
