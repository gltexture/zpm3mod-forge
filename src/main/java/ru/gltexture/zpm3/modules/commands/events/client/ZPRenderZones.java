package ru.gltexture.zpm3.modules.commands.events.client;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL46;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.events.ZPEventClass;
import ru.gltexture.zpm3.modules.commands.imgui.ZPCreativeUtilityUI;
import ru.gltexture.zpm3.modules.debug.events.ZPRenderStuffEvent;
import ru.gltexture.zpm3.modules.debug.render.ZPRenderLines;
import ru.gltexture.zpm3.modules.entity.instances.mobs.zombies.ZPAbstractZombie;
import ru.gltexture.zpm3.modules.net_pack.data.ZPClientZonesData;

public class ZPRenderZones implements ZPEventClass {
    public ZPRenderZones() {
    }

    @Override
    public @NotNull ZPSide getSide() {
        return ZPSide.CLIENT;
    }

    @Override
    public @NotNull Mod.EventBusSubscriber.Bus getBus() {
        return Mod.EventBusSubscriber.Bus.FORGE;
    }

    @SubscribeEvent
    public static void onRenderWorld(RenderLevelStageEvent event) {
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_TRANSLUCENT_BLOCKS && ZPCreativeUtilityUI.ENABLE_UTILITY) {
            //GL46.glDisable(GL46.GL_DEPTH_TEST);
            ZPClientZonesData.zoneDataList.values().forEach(e -> {
                final Vector3f color = ZPCreativeUtilityUI.currentSelectedZoneID != null && ZPCreativeUtilityUI.currentSelectedZoneID.equals(e.id()) ? new Vector3f(1.0f, 0.0f, 0.0f) : new Vector3f(1.0f);
                ZPRenderLines.drawAABB(event.getPoseStack(), new Vector3f(e.min()).add(0.5f, 0.0f, 0.5f), new Vector3f(e.max()).add(0.5f, 0.0f, 0.5f), color.x, color.y, color.z, 1.0f);
            });
            final Vector3f stVec = new Vector3f(ZPCreativeUtilityUI.inputStart[0], ZPCreativeUtilityUI.inputStart[1], ZPCreativeUtilityUI.inputStart[2]);
            final Vector3f ndVec = new Vector3f(ZPCreativeUtilityUI.inputEnd[0], ZPCreativeUtilityUI.inputEnd[1], ZPCreativeUtilityUI.inputEnd[2]);
            final Vector3f min = new Vector3f(Math.min(stVec.x(), ndVec.x()), Math.min(stVec.y(), ndVec.y()), Math.min(stVec.z(), ndVec.z()));
            final Vector3f max = new Vector3f(Math.max(stVec.x(), ndVec.x()), Math.max(stVec.y(), ndVec.y()), Math.max(stVec.z(), ndVec.z()));
            {
                ZPRenderLines.drawAABB(event.getPoseStack(), min.add(0.5f, 0.0f, 0.5f), max.add(0.5f, 0.0f, 0.5f), 0.0f, 1.0f, 1.0f, 1.0f);
            }
            //GL46.glEnable(GL46.GL_DEPTH_TEST);
        }
    }
}