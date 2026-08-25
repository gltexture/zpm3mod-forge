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

package ru.gltexture.zpm3.modules.commands.imgui;

import com.mojang.blaze3d.platform.Window;
import imgui.ImGui;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiCond;
import imgui.type.ImInt;
import imgui.type.ImString;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.engine.client.rendering.imgui.interfaces.IZPImGuiInterface;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.modules.commands.zones.ZPZoneFlag;
import ru.gltexture.zpm3.modules.commands.zones.ZPZoneManager;
import ru.gltexture.zpm3.modules.commands.zones.ZPZonesRegistry;
import ru.gltexture.zpm3.modules.commands.zones.vars.ZPZoneIntVar;
import ru.gltexture.zpm3.modules.worldgen.archiver.ZPMapArchiver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class ZPImGuiCreativeUtilityUI implements IZPImGuiInterface {
    public static @Nullable String currentSelectedZoneID;
    private static final Map<String, ImInt> INT_VAR_CACHE = new HashMap<>();
    private static final ImString inputId = new ImString(64);
    public static int[] inputStart = new int[]{0, 0, 0};
    public static int[] inputEnd = new int[]{0, 0, 0};

    private static final Set<ZPZoneFlag> inputFlags = new HashSet<>();

    public static boolean ENABLE_UTILITY = ZombiePlague3.isDevEnvironment();

    private static boolean ALLOW_IN_SURVIVAL() {
        return ZombiePlague3.isDevEnvironment();
    }

    private static void clearInput() {
        ZPImGuiCreativeUtilityUI.inputId.clear();
        ZPImGuiCreativeUtilityUI.inputStart = new int[]{0, 0, 0};
        ZPImGuiCreativeUtilityUI.inputEnd = new int[]{0, 0, 0};
        ZPImGuiCreativeUtilityUI.inputFlags.clear();
    }

    private static void drawXYZUtil(int[] start, int[] end) {
        if (!ImGui.treeNode("XYZ Util")) {
            return;
        }

        if (ImGui.button("Player -> Start")) {
            BlockPos pos = Objects.requireNonNull(Minecraft.getInstance().player).blockPosition();
            start[0] = pos.getX();
            start[1] = pos.getY();
            start[2] = pos.getZ();
        }

        ImGui.sameLine();

        if (ImGui.button("Player -> End")) {
            BlockPos pos = Objects.requireNonNull(Minecraft.getInstance().player).blockPosition();
            end[0] = pos.getX();
            end[1] = pos.getY();
            end[2] = pos.getZ();
        }

        HitResult hit = Objects.requireNonNull(Minecraft.getInstance().player).pick(128.0D, 0.0F, false);
        if (hit instanceof BlockHitResult blockHit) {
            BlockPos pos = blockHit.getBlockPos();
            if (ImGui.button("Look -> Start")) {
                start[0] = pos.getX();
                start[1] = pos.getY();
                start[2] = pos.getZ();
            }
            ImGui.sameLine();

            if (ImGui.button("Look -> End")) {
                end[0] = pos.getX();
                end[1] = pos.getY();
                end[2] = pos.getZ();
            }
        }

        ImGui.treePop();
    }

    private void archiveMapWindow() {
        final Path zpMaps = Paths.get("zp_maps");
        if (ImGui.button("Run")) {
            try {
                MinecraftServer server = Minecraft.getInstance().getSingleplayerServer();
                Files.createDirectories(zpMaps);
                final Path inner = zpMaps.resolve(server.getWorldData().getLevelName().toLowerCase().replaceAll(" ", "_"));
                Files.createDirectories(inner);
                final Path zip = inner.resolve(server.getWorldData().getLevelName().toLowerCase().replaceAll(" ", "_") + ".zip");
                ZPMapArchiver.archive(server, zip, a -> {
                    a.file("level.dat");
                    a.folder("region");
                    a.folder("entities");
                    a.folder("overworld");
                    a.folder("icon.png");
                });
                {
                    try {
                        String template = """
                                        {
                                          "archive": "%s",
                                          "preview": "",
                                          "name": "%s",
                                          "version": "1.0",
                                          "authors": [
                                            "UNKNOWN"
                                          ],
                                          "description": "TODO",
                                          "recommendedPlayers": -1,
                                          "modVersion": "0.0a"
                                        }
                                        """.formatted(
                                zip.getFileName(),
                                server.getWorldData().getLevelName()
                        );

                        Files.writeString(inner.resolve("map.json"), template, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to create map metadata", e);
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        ImGui.pushStyleColor(ImGuiCol.Text, 0xff00ff00);
        if (ImGui.button("Open Save Folder")) {
            Util.getPlatform().openFile(zpMaps.toFile());
        }
        ImGui.popStyleColor();
    }

    private void zonesWindow() {
        ImGui.indent();
        if (ImGui.collapsingHeader("+ Create")) {
            ImGui.pushID("CreateUtilxyz");
            ImGui.inputText("ID", ZPImGuiCreativeUtilityUI.inputId);

            ImGui.inputInt3("Start XYZ", ZPImGuiCreativeUtilityUI.inputStart);
            ImGui.inputInt3("End XYZ", ZPImGuiCreativeUtilityUI.inputEnd);

            {
                ZPImGuiCreativeUtilityUI.drawXYZUtil(inputStart, inputEnd);
            }

            ImGui.separator();
            ImGui.text("Flags");

            for (ZPZoneFlag f : ZPZonesRegistry.flagValues()) {
                final boolean flag = ZPImGuiCreativeUtilityUI.inputFlags.contains(f);
                if (ImGui.checkbox(f.id(), flag)) {
                    if (flag) {
                        ZPImGuiCreativeUtilityUI.inputFlags.remove(f);
                    } else {
                        ZPImGuiCreativeUtilityUI.inputFlags.add(f);
                    }
                }
            }

            if (ImGui.button("Create")) {
                if (!ZPImGuiCreativeUtilityUI.inputId.isEmpty()) {
                    Minecraft.getInstance().player.connection.sendCommand(String.format("zp3 zoneCreate %s %d %d %d %d %d %d",
                            ZPImGuiCreativeUtilityUI.inputId.get(),
                            ZPImGuiCreativeUtilityUI.inputStart[0],
                            ZPImGuiCreativeUtilityUI.inputStart[1],
                            ZPImGuiCreativeUtilityUI.inputStart[2],
                            ZPImGuiCreativeUtilityUI.inputEnd[0],
                            ZPImGuiCreativeUtilityUI.inputEnd[1],
                            ZPImGuiCreativeUtilityUI.inputEnd[2]
                    ));

                    for (ZPZoneFlag flag : ZPImGuiCreativeUtilityUI.inputFlags) {
                        Minecraft.getInstance().player.connection.sendCommand(
                                "zp3 zoneAddFlags " + ZPImGuiCreativeUtilityUI.inputId.get() + " " + flag.id()
                        );
                    }

                    ZPImGuiCreativeUtilityUI.clearInput();
                }
            }
            ImGui.popID();
        } else {
            ZPImGuiCreativeUtilityUI.clearInput();
        }
        if (ImGui.collapsingHeader("List")) {
            ImGui.pushID("EditUtilXYZ");
            String preview = ZPImGuiCreativeUtilityUI.currentSelectedZoneID == null ? "<None>" : ZPImGuiCreativeUtilityUI.currentSelectedZoneID;
            if (ImGui.beginCombo("Selected Zone", preview)) {
                if (ImGui.selectable("<None>", ZPImGuiCreativeUtilityUI.currentSelectedZoneID == null)) {
                    ZPImGuiCreativeUtilityUI.currentSelectedZoneID = null;
                }
                final Collection<ZPZoneManager.Zone> zones = ZPZoneManager.INSTANCE.getAllZonesOnLevel(Minecraft.getInstance().player.level());
                if (zones != null) {
                    for (ZPZoneManager.Zone zone : zones) {
                        boolean selected = zone.uniqueId().equals(ZPImGuiCreativeUtilityUI.currentSelectedZoneID);
                        if (ImGui.selectable(zone.uniqueId(), selected)) {
                            ZPImGuiCreativeUtilityUI.currentSelectedZoneID = zone.uniqueId();
                        }
                        if (selected) {
                            ImGui.setItemDefaultFocus();
                        }
                    }
                }
                ImGui.endCombo();
            }

            boolean zoneSelected = ZPImGuiCreativeUtilityUI.currentSelectedZoneID != null;
            ImGui.beginDisabled(!zoneSelected);
            @Nullable ZPZoneManager.Zone zone = ZPZoneManager.INSTANCE.getZoneById(Minecraft.getInstance().player.level(), ZPImGuiCreativeUtilityUI.currentSelectedZoneID);
            final int[] selZoneStartXYZ = zone != null ? new int[]{zone.start().x, zone.start().y, zone.start().z} : new int[]{0, 0, 0};
            final int[] selZoneEndXYZ = zone != null ? new int[]{zone.end().x, zone.end().y, zone.end().z} : new int[]{0, 0, 0};

            final int[] copyArrS = Arrays.copyOf(selZoneStartXYZ, 3);
            final int[] copyArrE = Arrays.copyOf(selZoneEndXYZ, 3);

            ImGui.separator();
            ImGui.text("Coordinates");
            ImGui.inputInt3("Start XYZ", copyArrS);
            ImGui.inputInt3("End XYZ", copyArrE);
            ZPImGuiCreativeUtilityUI.drawXYZUtil(copyArrS, copyArrE);
            if (Arrays.compare(selZoneStartXYZ, copyArrS) != 0 || Arrays.compare(selZoneEndXYZ, copyArrE) != 0) {
                Minecraft.getInstance().player.connection.sendCommand(String.format("zp3 zoneSetBounds %s %d %d %d %d %d %d",
                        ZPImGuiCreativeUtilityUI.currentSelectedZoneID, copyArrS[0], copyArrS[1], copyArrS[2], copyArrE[0], copyArrE[1], copyArrE[2]));
            }
            ImGui.separator();
            ImGui.text("Flags");

            for (ZPZoneFlag flag : ZPZonesRegistry.flagValues()) {
                boolean enabled = zone != null && zone.flags().contains(flag);
                if (ImGui.checkbox(flag.id(), enabled)) {
                    if (enabled) {
                        Minecraft.getInstance().player.connection.sendCommand("zp3 zoneRemoveFlags " + ZPImGuiCreativeUtilityUI.currentSelectedZoneID + " " + flag.id());
                    } else {
                        Minecraft.getInstance().player.connection.sendCommand("zp3 zoneAddFlags " + ZPImGuiCreativeUtilityUI.currentSelectedZoneID + " " + flag.id());
                    }
                }
            }
            if (zone != null && zone.int_vars() != null) {
                ImGui.text("Vars");
                for (ZPZoneIntVar zoneVar : zone.int_vars().values()) {
                    ImInt value = INT_VAR_CACHE.computeIfAbsent(zoneVar.getVariableId(), k -> new ImInt(zoneVar.getValue()));
                    ImGui.pushItemWidth(120);
                    if (ImGui.sliderInt(zoneVar.getVariableId(), value.getData(), zoneVar.getMin(), zoneVar.getMax())) {
                    }
                    if (ImGui.isItemDeactivatedAfterEdit()) {
                        Minecraft.getInstance().player.connection.sendCommand("zp3 zoneSetIntVar " + ZPImGuiCreativeUtilityUI.currentSelectedZoneID + " " + zoneVar.getVariableId() + " " + value.get());
                    }
                    ImGui.popItemWidth();
                }
            }

            ImGui.separator();
            if (ImGui.button("DELETE ZONE")) {
                Minecraft.getInstance().player.connection.sendCommand("zp3 zoneRemove " + ZPImGuiCreativeUtilityUI.currentSelectedZoneID);
                ZPImGuiCreativeUtilityUI.currentSelectedZoneID = null;
            }
            ImGui.endDisabled();
            ImGui.popID();
        }
        ImGui.unindent();
    }

    @Override
    public void drawGui(@NotNull Window window, @NotNull Input input) {
        if (!ZPImGuiCreativeUtilityUI.ALLOW_IN_SURVIVAL() || Minecraft.getInstance().player == null) {
            return;
        }

        if (ZPImGuiCreativeUtilityUI.ENABLE_UTILITY) {
            ImGui.setNextWindowPos(0, 0, ImGuiCond.Once);
            ImGui.setNextWindowSize(400, 600, ImGuiCond.Once);
            ImGui.begin("Creative Utility");

            if (Minecraft.getInstance().getSingleplayerServer() != null && ImGui.collapsingHeader("Archive Map")) {
                this.archiveMapWindow();
            }

            if (ImGui.collapsingHeader("Zones")) {
               this.zonesWindow();
            }
            ImGui.end();
        }
    }
}
