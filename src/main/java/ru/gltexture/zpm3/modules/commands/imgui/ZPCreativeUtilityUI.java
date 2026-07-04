package ru.gltexture.zpm3.modules.commands.imgui;

import com.mojang.blaze3d.platform.Window;
import imgui.ImGui;
import imgui.flag.ImGuiCond;
import imgui.type.ImString;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.engine.client.rendering.ui.imgui.interfaces.DearUIInterface;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.zones.ZPFlagZones;
import ru.gltexture.zpm3.modules.net_pack.data.ZPClientZonesData;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ZPCreativeUtilityUI implements DearUIInterface {
    public static @Nullable String currentSelectedZoneID;

    private static final ImString inputId = new ImString(64);
    public static int[] inputStart = new int[]{0, 0, 0};
    public static int[] inputEnd = new int[]{0, 0, 0};

    private static final Set<ZPFlagZones.Zone.AvailableFlags> inputFlags = new HashSet<>();

    public static boolean ENABLE_UTILITY = ZombiePlague3.isDevEnvironment();

    private static boolean ALLOW_IN_SURVIVAL() {
        return ZombiePlague3.isDevEnvironment();
    }

    private static void clearInput() {
        ZPCreativeUtilityUI.inputId.clear();
        ZPCreativeUtilityUI.inputStart = new int[]{0, 0, 0};
        ZPCreativeUtilityUI.inputEnd = new int[]{0, 0, 0};
        ZPCreativeUtilityUI.inputFlags.clear();
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

    @Override
    public void drawGui(@NotNull Window window, @NotNull MouseHandler mouseHandler, @NotNull KeyboardHandler keyboardHandler) {
        if (!ZPCreativeUtilityUI.ALLOW_IN_SURVIVAL() || Minecraft.getInstance().player == null) {
            return;
        }

        if (ZPCreativeUtilityUI.ENABLE_UTILITY) {
            ImGui.setNextWindowPos(0, 0, ImGuiCond.Once);
            ImGui.setNextWindowSize(400, 600, ImGuiCond.Once);
            ImGui.begin("Creative Utility");

            if (ImGui.collapsingHeader("Zones")) {
                ImGui.indent();
                if (ImGui.collapsingHeader("+ Create")) {
                    ImGui.pushID("CreateUtilxyz");
                    ImGui.inputText("ID", ZPCreativeUtilityUI.inputId);

                    ImGui.inputInt3("Start XYZ", ZPCreativeUtilityUI.inputStart);
                    ImGui.inputInt3("End XYZ", ZPCreativeUtilityUI.inputEnd);

                    {
                        ZPCreativeUtilityUI.drawXYZUtil(inputStart, inputEnd);
                    }

                    ImGui.separator();
                    ImGui.text("Flags");

                    for (ZPFlagZones.Zone.AvailableFlags f : ZPFlagZones.Zone.AvailableFlags.values()) {
                        final boolean flag = ZPCreativeUtilityUI.inputFlags.contains(f);
                        if (ImGui.checkbox(f.name(), flag)) {
                            if (flag) {
                                ZPCreativeUtilityUI.inputFlags.remove(f);
                            } else {
                                ZPCreativeUtilityUI.inputFlags.add(f);
                            }
                        }
                    }

                    if (ImGui.button("Create")) {
                        if (!ZPCreativeUtilityUI.inputId.isEmpty()) {
                            Minecraft.getInstance().player.connection.sendCommand(String.format("zp3 zoneCreate %s %d %d %d %d %d %d",
                                    ZPCreativeUtilityUI.inputId.get(),
                                    ZPCreativeUtilityUI.inputStart[0],
                                    ZPCreativeUtilityUI.inputStart[1],
                                    ZPCreativeUtilityUI.inputStart[2],
                                    ZPCreativeUtilityUI.inputEnd[0],
                                    ZPCreativeUtilityUI.inputEnd[1],
                                    ZPCreativeUtilityUI.inputEnd[2]
                            ));

                            for (ZPFlagZones.Zone.AvailableFlags flag : ZPCreativeUtilityUI.inputFlags) {
                                Minecraft.getInstance().player.connection.sendCommand(
                                        "zp3 zoneAddFlag " + ZPCreativeUtilityUI.inputId.get() + " " + flag.name()
                                );
                            }

                            ZPCreativeUtilityUI.clearInput();
                        }
                    }
                    ImGui.popID();
                } else {
                    ZPCreativeUtilityUI.clearInput();
                }
                if (ImGui.collapsingHeader("List")) {
                    ImGui.pushID("EditUtilXYZ");
                    String preview = ZPCreativeUtilityUI.currentSelectedZoneID == null ? "<None>" : ZPCreativeUtilityUI.currentSelectedZoneID;
                    if (ImGui.beginCombo("Selected Zone", preview)) {
                        if (ImGui.selectable("<None>", ZPCreativeUtilityUI.currentSelectedZoneID == null)) {
                            ZPCreativeUtilityUI.currentSelectedZoneID = null;
                        }
                        for (String id : ZPClientZonesData.zoneDataList.keySet()) {
                            boolean selected = id.equals(ZPCreativeUtilityUI.currentSelectedZoneID);
                            if (ImGui.selectable(id, selected)) {
                                ZPCreativeUtilityUI.currentSelectedZoneID = id;
                            }
                            if (selected) {
                                ImGui.setItemDefaultFocus();
                            }
                        }
                        ImGui.endCombo();
                    }

                    boolean zoneSelected = ZPCreativeUtilityUI.currentSelectedZoneID != null;
                    ImGui.beginDisabled(!zoneSelected);
                    @Nullable ZPClientZonesData.ZoneData zone = ZPClientZonesData.zoneDataList.get(ZPCreativeUtilityUI.currentSelectedZoneID);
                    final int[] selZoneStartXYZ = zone != null ? new int[]{zone.min().x, zone.min().y, zone.min().z} : new int[]{0, 0, 0};
                    final int[] selZoneEndXYZ = zone != null ? new int[]{zone.max().x, zone.max().y, zone.max().z} : new int[]{0, 0, 0};

                    final int[] copyArrS = Arrays.copyOf(selZoneStartXYZ, 3);
                    final int[] copyArrE = Arrays.copyOf(selZoneEndXYZ, 3);

                    ImGui.separator();
                    ImGui.text("Coordinates");
                    ImGui.inputInt3("Start XYZ", copyArrS);
                    ImGui.inputInt3("End XYZ", copyArrE);
                    ZPCreativeUtilityUI.drawXYZUtil(copyArrS, copyArrE);
                    if (Arrays.compare(selZoneStartXYZ, copyArrS) != 0 || Arrays.compare(selZoneEndXYZ, copyArrE) != 0) {
                        Minecraft.getInstance().player.connection.sendCommand(String.format("zp3 zoneSetBounds %s %d %d %d %d %d %d",
                                ZPCreativeUtilityUI.currentSelectedZoneID, copyArrS[0], copyArrS[1], copyArrS[2], copyArrE[0], copyArrE[1], copyArrE[2]));
                    }
                    ImGui.separator();
                    ImGui.text("Flags");

                    for (ZPFlagZones.Zone.AvailableFlags flag : ZPFlagZones.Zone.AvailableFlags.values()) {
                        String[] splittedFlags = zone != null ? zone.flags().split(";") : null;
                        boolean enabled = splittedFlags != null && Arrays.stream(zone.flags().split(";")).anyMatch(z -> z.equals(flag.name()));
                        if (ImGui.checkbox(flag.name(), enabled)) {
                            if (enabled) {
                                Minecraft.getInstance().player.connection.sendCommand("zp3 zoneRemoveFlags " + ZPCreativeUtilityUI.currentSelectedZoneID + " " + flag.name());
                            } else {
                                Minecraft.getInstance().player.connection.sendCommand("zp3 zoneAddFlags " + ZPCreativeUtilityUI.currentSelectedZoneID + " " + flag.name());
                            }
                        }
                    }
                    ImGui.separator();
                    if (ImGui.button("DELETE ZONE")) {
                        Minecraft.getInstance().player.connection.sendCommand("zp3 zoneRemove " + ZPCreativeUtilityUI.currentSelectedZoneID);
                        ZPCreativeUtilityUI.currentSelectedZoneID = null;
                    }
                    ImGui.endDisabled();
                    ImGui.popID();
                }
                ImGui.unindent();
            }
            ImGui.end();
        }
    }
}
