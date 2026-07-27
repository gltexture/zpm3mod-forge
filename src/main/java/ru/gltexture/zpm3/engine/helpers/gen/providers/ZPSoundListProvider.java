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

package ru.gltexture.zpm3.engine.helpers.gen.providers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class ZPSoundListProvider implements DataProvider {
    public static final Set<ZPSoundEvent> soundEvents = new HashSet<>();
    private final DataGenerator generator;
    private final String modId;

    public ZPSoundListProvider(DataGenerator generator, String modId) {
        this.generator = generator;
        this.modId = modId;
    }

    public static void addNewSoundEvent(@NotNull ZPSoundListProvider.ZPSoundEvent soundEvent) {
        ZPSoundListProvider.soundEvents.add(soundEvent);
    }

    private static void clear() {
        ZPSoundListProvider.soundEvents.clear();
    }

    @SuppressWarnings("all")
    @Override
    public @NotNull CompletableFuture<?> run(@NotNull CachedOutput pOutput) {
        List<CompletableFuture<?>> futures = new ArrayList<>();

        JsonObject soundsJson = new JsonObject();

        for (ZPSoundEvent soundEvent : soundEvents) {
            JsonObject soundEntry = new JsonObject();
            soundEntry.addProperty("replace", soundEvent.replace());
            soundEntry.addProperty("subtitle", soundEvent.subTitle());

            JsonArray soundList = new JsonArray();

            for (SoundData soundData : soundEvent.soundData()) {
                JsonObject soundObject = new JsonObject();
                soundObject.addProperty("name", this.modId + ":" + soundData.name());

                if (soundData.volume() != 1.0f) {
                    soundObject.addProperty("volume", soundData.volume());
                }

                if (soundData.pitch() != 1.0f) {
                    soundObject.addProperty("pitch", soundData.pitch());
                }

                if (soundData.weight() != 1.0f) {
                    soundObject.addProperty("weight", (int) soundData.weight());
                }

                if (soundData.stream()) {
                    soundObject.addProperty("stream", true);
                }

                if (soundData.attenuation_distance() != 0) {
                    soundObject.addProperty("attenuation_distance", soundData.attenuation_distance());
                }

                if (!soundData.type().equals("file")) {
                    soundObject.addProperty("type", soundData.type());
                }

                soundList.add(soundObject);
            }

            soundEntry.add("sounds", soundList);
            soundsJson.add(soundEvent.name(), soundEntry);
        }

        Path soundsJsonPath = generator.getPackOutput().getOutputFolder().resolve("assets/" + this.modId + "/sounds.json");

        futures.add(DataProvider.saveStable(pOutput, soundsJson, soundsJsonPath));

        ZPSoundListProvider.clear();
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
    }

    @Override
    public @NotNull String getName() {
        return "SoundList";
    }

    public record ZPSoundEvent(@NotNull String name, boolean replace, @NotNull String subTitle, @NotNull List<SoundData> soundData) { ; }

    public record SoundData(@NotNull String name, float volume, float pitch, float weight, boolean stream, int attenuation_distance, @NotNull String type) {
        public SoundData(String name) {
            this(name, 1.0f, 1.0f, 1.0f, false, 0, "file");
        }

        public SoundData(String name, float volume, float pitch) {
            this(name, volume, pitch, 1.0f, false, 0, "file");
        }

        public SoundData(String name, boolean stream) {
            this(name, 1.0f, 1.0f, 1.0f, stream, 0, "file");
        }

        public SoundData(String name, float volume, float pitch, boolean stream) {
            this(name, volume, pitch, 1.0f, stream, 0, "file");
        }

        public static SoundData event(String name) {
            return new SoundData(name, 1.0f, 1.0f, 1.0f, false, 0, "event");
        }
    }
}