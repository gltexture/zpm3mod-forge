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

package ru.gltexture.zpm3.modules.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3i;
import ru.gltexture.zpm3.engine.core.ZPLogger;
import ru.gltexture.zpm3.engine.core.api.modules.context.IModuleClientSetupContext;
import ru.gltexture.zpm3.engine.core.api.modules.context.IModuleInitContext;
import ru.gltexture.zpm3.engine.core.api.modules.context.IModulePostInitContext;
import ru.gltexture.zpm3.engine.core.api.modules.context.IModulePreInitContext;
import ru.gltexture.zpm3.engine.service.ZPUtility;
import ru.gltexture.zpm3.engine.zones.ZPDefaultZones;
import ru.gltexture.zpm3.engine.zones.vars.ZPZoneIntVar;
import ru.gltexture.zpm3.modules.commands.events.client.ZPRenderSpecialZoneEffectsOnClient;
import ru.gltexture.zpm3.engine.zones.ZPZoneFlag;
import ru.gltexture.zpm3.engine.zones.ZPZoneManager;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.api.modules.ZPModule;
import ru.gltexture.zpm3.engine.core.api.modules.ZPModuleData;
import ru.gltexture.zpm3.engine.events.ZPForgeEventHandlerClass;
import ru.gltexture.zpm3.modules.commands.events.client.ZPCreativeUtilityMenuEvent;
import ru.gltexture.zpm3.modules.commands.events.client.ZPRenderZones;
import ru.gltexture.zpm3.modules.commands.imgui.ZPImGuiCreativeUtilityUI;
import ru.gltexture.zpm3.engine.zones.ZPZonesRegistry;
import ru.gltexture.zpm3.modules.net_pack.data.data_ent.ZPNetEntDataSyncer;

import java.util.*;

public class ZPCommandsModule extends ZPModule {
    public ZPCommandsModule(@NotNull ZPModuleData zpModuleData) {
        super(zpModuleData);
    }

    public ZPCommandsModule() {
    }

    @Override
    public void commonSetup() {
    }

    @Override
    public void commonShutdown() {
        
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void clientSetup(@NotNull IModuleClientSetupContext context) {
        context.registerImGuiInterface(new ZPImGuiCreativeUtilityUI());
        context.registerZoneEffect(ZPDefaultZones.toxicCloud, (zone, chunkX, chunkZ) -> {
            ZPRenderSpecialZoneEffectsOnClient.renderCloudDefaultFun(zone, chunkX, chunkZ, false);
        });
        context.registerZoneEffect(ZPDefaultZones.acidCloud, (zone, chunkX, chunkZ) -> {
            ZPRenderSpecialZoneEffectsOnClient.renderCloudDefaultFun(zone, chunkX, chunkZ, true);
        });
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void clientShutDown() {

    }

    //@Override
    //public void initMixins(ZombiePlague3.@NotNull IMixinEntry mixinEntry) {
    //    //mixinEntry.addMixinConfigData(new ZombiePlague3.IMixinEntry.MixinConfig("debug", "ru.gltexture.zpm3.modules.debug.mixins.impl"),
    //    //        new ZombiePlague3.IMixinEntry.MixinClass("client.ZPCameraMixin", ZPSide.CLIENT),
    //    //        new ZombiePlague3.IMixinEntry.MixinClass("client.ZPInputMixin", ZPSide.CLIENT));
    //}

   // public static void addNewLineToDraw(@NotNull ZPRenderStuffEvent.LineRequest lineRequest) {
   // }

    @Override
    public void initialize(@NotNull IModuleInitContext context) {
        context.registerForgeEventHandlerClass(ZPCommandsEvent.class);
        ZPUtility.sides().onlyClient(() -> {
            if (ZombiePlague3.getClientManager().isImGuiValid()) {
                context.registerForgeEventHandlerClass(ZPRenderZones.class);
                context.registerForgeEventHandlerClass(ZPCreativeUtilityMenuEvent.class);
            }
            context.registerForgeEventHandlerClass(ZPRenderSpecialZoneEffectsOnClient.class);
        });
    }

    @Override
    public void preInitialize(@NotNull IModulePreInitContext context) {

    }

    @Override
    public void postInitialize(@NotNull IModulePostInitContext context) {

    }

    public static class ZPCommandsEvent implements ZPForgeEventHandlerClass {

        public ZPCommandsEvent() {
        }

        @Override
        public @NotNull ZPSide getSide() {
            return ZPSide.COMMON;
        }

        @Override
        public @NotNull Mod.EventBusSubscriber.Bus getBus() {
            return Mod.EventBusSubscriber.Bus.FORGE;
        }

        @SubscribeEvent
        public static void onCommand(RegisterCommandsEvent event) {
            event.getDispatcher().register(
                    Commands.literal("zp3")
                            //.then(Commands.literal("refreshConfigs")
                            //        .executes(ctx -> {
                            //            ServerPlayer player = ctx.getSource().getPlayerOrException();
                            //            if (player.hasPermissions(4)) {
                            //                ZombiePlague3.processConfigurations();
                            //                ZombiePlague3.netServer().sendToAll(ZPSendGlobalSettings_StoC.create());
                            //                ctx.getSource().sendSuccess(() -> Component.literal("Success!"), false);
                            //                return 1;
                            //            }
                            //            return 0;
                            //        })
                            //)
                            //.then(Commands.literal("refreshLootTables")
                            //        .executes(ctx -> {
                            //            ServerPlayer player = ctx.getSource().getPlayerOrException();
                            //            if (player.hasPermissions(4)) {
                            //                ZPLootTablesReader.READ_FILES();
                            //                ctx.getSource().sendSuccess(() -> Component.literal("Success!"), false);
                            //                return 1;
                            //            }
                            //            return 0;
                            //        })
                            //)
                            .then(Commands.literal("zoneCreate")
                                    .then(Commands.argument("zoneId", StringArgumentType.string())
                                            .suggests((ctx, builder) -> {
                                                ServerLevel level = ctx.getSource().getLevel();
                                                Objects.requireNonNull(ZPZoneManager.INSTANCE.getAllZonesOnLevel(level)).forEach(e -> builder.suggest(e.uniqueId()));
                                                return builder.buildFuture();
                                            })
                                            .then(Commands.argument("x1", IntegerArgumentType.integer())
                                                    .then(Commands.argument("y1", IntegerArgumentType.integer())
                                                            .then(Commands.argument("z1", IntegerArgumentType.integer())
                                                                    .then(Commands.argument("x2", IntegerArgumentType.integer())
                                                                            .then(Commands.argument("y2", IntegerArgumentType.integer())
                                                                                    .then(Commands.argument("z2", IntegerArgumentType.integer())
                                                                                            .executes(ctx -> {
                                                                                                ServerPlayer player = ctx.getSource().getPlayerOrException();
                                                                                                ServerLevel level = (ServerLevel) player.level();
                                                                                                if (!player.hasPermissions(3)) {
                                                                                                    return 0;
                                                                                                }
                                                                                                String id = StringArgumentType.getString(ctx, "zoneId");
                                                                                                final int x1 = IntegerArgumentType.getInteger(ctx, "x1");
                                                                                                final int y1 = IntegerArgumentType.getInteger(ctx, "y1");
                                                                                                final int z1 = IntegerArgumentType.getInteger(ctx, "z1");
                                                                                                final int x2 = IntegerArgumentType.getInteger(ctx, "x2");
                                                                                                final int y2 = IntegerArgumentType.getInteger(ctx, "y2");
                                                                                                final int z2 = IntegerArgumentType.getInteger(ctx, "z2");
                                                                                                //final int minX = Math.start(x1, x2);
                                                                                                //final int maxX = Math.end(x1, x2);
                                                                                                //final int minY = Math.start(y1, y2);
                                                                                                //final int maxY = Math.end(y1, y2);
                                                                                                //final int minZ = Math.start(z1, z2);
                                                                                                //final int maxZ = Math.end(z1, z2);
                                                                                                ZPZoneManager.Zone zone = ZPZoneManager.CREATE_DEFAULT_ZONE(id, new Vector3i(x1, y1, z1), new Vector3i(x2, y2, z2));
                                                                                                ZPZoneManager.INSTANCE.addNewZone(level, zone);

                                                                                                ctx.getSource().sendSuccess(() -> Component.literal("Zone " + id + " created!"), false);
                                                                                                return 1;
                                                                                            })
                                                                                    )
                                                                            )
                                                                    )
                                                            )
                                                    )
                                            )
                                    )
                            )
                            .then(Commands.literal("zoneSetBounds")
                                    .then(Commands.argument("zoneId", StringArgumentType.string())
                                            .suggests((ctx, builder) -> {
                                                ServerLevel level = ctx.getSource().getLevel();
                                                Objects.requireNonNull(ZPZoneManager.INSTANCE.getAllZonesOnLevel(level)).forEach(e -> builder.suggest(e.uniqueId()));
                                                return builder.buildFuture();
                                            })
                                            .then(Commands.argument("x1", IntegerArgumentType.integer())
                                                    .then(Commands.argument("y1", IntegerArgumentType.integer())
                                                            .then(Commands.argument("z1", IntegerArgumentType.integer())
                                                                    .then(Commands.argument("x2", IntegerArgumentType.integer())
                                                                            .then(Commands.argument("y2", IntegerArgumentType.integer())
                                                                                    .then(Commands.argument("z2", IntegerArgumentType.integer())
                                                                                            .executes(ctx -> {
                                                                                                ServerPlayer player = ctx.getSource().getPlayerOrException();
                                                                                                ServerLevel level = (ServerLevel) player.level();
                                                                                                if (!player.hasPermissions(3)) {
                                                                                                    return 0;
                                                                                                }
                                                                                                String id = StringArgumentType.getString(ctx, "zoneId");
                                                                                                int x1 = IntegerArgumentType.getInteger(ctx, "x1");
                                                                                                int y1 = IntegerArgumentType.getInteger(ctx, "y1");
                                                                                                int z1 = IntegerArgumentType.getInteger(ctx, "z1");
                                                                                                int x2 = IntegerArgumentType.getInteger(ctx, "x2");
                                                                                                int y2 = IntegerArgumentType.getInteger(ctx, "y2");
                                                                                                int z2 = IntegerArgumentType.getInteger(ctx, "z2");
                                                                                                //int minX = Math.start(x1, x2);
                                                                                                //int minY = Math.start(y1, y2);
                                                                                                //int minZ = Math.start(z1, z2);
                                                                                                //int maxX = Math.end(x1, x2);
                                                                                                //int maxY = Math.end(y1, y2);
                                                                                                //int maxZ = Math.end(z1, z2);
                                                                                                if (ZPZoneManager.INSTANCE.getZoneById(level, id) == null) {
                                                                                                    ctx.getSource().sendFailure(Component.literal("Zone " + id + " doesn't exist."));
                                                                                                    return 0;
                                                                                                }
                                                                                                ZPZoneManager.INSTANCE.newZoneBounds(level, id, new Vector3i(x1, y1, z1), new Vector3i(x2, y2, z2));
                                                                                                ctx.getSource().sendSuccess(() -> Component.literal("Zone " + id + " bounds updated."), false);
                                                                                                return 1;
                                                                                            })
                                                                                    )
                                                                            )
                                                                    )
                                                            )
                                                    )
                                            )
                                    )
                            )
                            .then(Commands.literal("zoneRemove")
                                    .then(Commands.argument("zoneId", StringArgumentType.string())
                                            .suggests((ctx, builder) -> {
                                                ServerLevel level = ctx.getSource().getLevel();
                                                Objects.requireNonNull(ZPZoneManager.INSTANCE.getAllZonesOnLevel(level)).forEach(e -> builder.suggest(e.uniqueId()));
                                                return builder.buildFuture();
                                            })
                                            .executes(ctx -> {
                                                ServerPlayer player = ctx.getSource().getPlayerOrException();
                                                ServerLevel level = (ServerLevel) player.level();
                                                if (!player.hasPermissions(3)) {
                                                    return 0;
                                                }
                                                String id = StringArgumentType.getString(ctx, "zoneId");

                                                boolean removed = ZPZoneManager.INSTANCE.removeZone(level, id);
                                                if (removed) {
                                                    ctx.getSource().sendSuccess(() -> Component.literal("Zone " + id + " Removed!"), false);
                                                    return 1;
                                                }
                                                ctx.getSource().sendFailure(Component.literal("Zone " + id + " not found!"));
                                                return 0;
                                            })
                                    )
                            )
                            .then(Commands.literal("zonesList")
                                    .executes(ctx -> {
                                        ServerPlayer player = ctx.getSource().getPlayerOrException();
                                        if (!player.hasPermissions(3)) {
                                            return 0;
                                        }
                                        ServerLevel level = (ServerLevel) player.level();
                                        Collection<ZPZoneManager.Zone> zones = ZPZoneManager.INSTANCE.getAllZonesOnLevel(level);
                                        if (zones == null || zones.isEmpty()) {
                                            ctx.getSource().sendSuccess(() -> Component.literal("Empty!"), false);
                                            return 0;
                                        }
                                        for (ZPZoneManager.Zone zone : zones) {
                                            ctx.getSource().sendSuccess(() -> Component.literal(zone.uniqueId() + " : " + zone.start() + " | " + zone.end() + " Flags: " + zone.flags() + " Vars: " + zone.int_vars().values()), false);
                                        }
                                        return 1;
                                    })
                            )
                            .then(Commands.literal("zoneEraseFlags")
                                    .then(Commands.argument("zoneId", StringArgumentType.string())
                                            .suggests((ctx, builder) -> {
                                                ServerLevel level = ctx.getSource().getLevel();
                                                Objects.requireNonNull(ZPZoneManager.INSTANCE.getAllZonesOnLevel(level)).forEach(e -> builder.suggest(e.uniqueId()));
                                                return builder.buildFuture();
                                            })
                                            .executes(ctx -> {
                                                ServerPlayer player = ctx.getSource().getPlayerOrException();
                                                if (!player.hasPermissions(3)) {
                                                    return 0;
                                                }
                                                ServerLevel level = (ServerLevel) player.level();
                                                String id = StringArgumentType.getString(ctx, "zoneId");
                                                boolean updated = ZPZoneManager.INSTANCE.replaceFlags(level, id, new HashSet<>());
                                                if (updated) {
                                                    ctx.getSource().sendSuccess(() -> Component.literal("Flags of " + id + " erased!"), false);
                                                    return 1;
                                                }
                                                ctx.getSource().sendFailure(Component.literal("Zone " + id + " not found!"));
                                                return 0;
                                            })
                                    )
                            )
                            .then(Commands.literal("zoneAddFlags")
                                    .then(Commands.argument("zoneId", StringArgumentType.string())
                                            .suggests((ctx, builder) -> {
                                                ServerLevel level = ctx.getSource().getLevel();
                                                Objects.requireNonNull(ZPZoneManager.INSTANCE.getAllZonesOnLevel(level)).forEach(e -> builder.suggest(e.uniqueId()));
                                                return builder.buildFuture();
                                            })
                                            .then(Commands.argument("flag", StringArgumentType.string())
                                                    .suggests((ctx, builder) -> {
                                                        for (ZPZoneFlag f : ZPZonesRegistry.flagValues()) {
                                                            builder.suggest(f.id());
                                                        }
                                                        return builder.buildFuture();
                                                    })
                                                    .executes(ctx -> {
                                                        ServerPlayer player = ctx.getSource().getPlayerOrException();
                                                        if (!player.hasPermissions(3)) {
                                                            return 0;
                                                        }
                                                        ServerLevel level = (ServerLevel) player.level();
                                                        String id = StringArgumentType.getString(ctx, "zoneId");
                                                        String flagStr = StringArgumentType.getString(ctx, "flag");
                                                        ZPZoneFlag flag = ZPZonesRegistry.flagValueOf(flagStr);
                                                        if (flag == null) {
                                                            ctx.getSource().sendFailure(Component.literal("The flag " + flagStr + " doesn't exist. Try /zp3 zoneFlagsList"));
                                                            return 0;
                                                        }
                                                        if (!ZPZoneManager.INSTANCE.addFlag(level, id, flag)) {
                                                            ctx.getSource().sendFailure(Component.literal("Failed to add flag to zone."));
                                                            return 0;
                                                        }
                                                        ctx.getSource().sendSuccess(() -> Component.literal("Flag " + flag.id() + " added to zone " + id + "!"), false);
                                                        return 1;
                                                    })
                                            )
                                    )
                            )
                            .then(Commands.literal("zoneRemoveFlags")
                                    .then(Commands.argument("zoneId", StringArgumentType.string())
                                            .suggests((ctx, builder) -> {
                                                ServerLevel level = ctx.getSource().getLevel();
                                                Objects.requireNonNull(ZPZoneManager.INSTANCE.getAllZonesOnLevel(level)).forEach(e -> builder.suggest(e.uniqueId()));
                                                return builder.buildFuture();
                                            })
                                            .then(Commands.argument("flag", StringArgumentType.string())
                                                    .suggests((ctx, builder) -> {
                                                        for (ZPZoneFlag f : ZPZonesRegistry.flagValues()) {
                                                            builder.suggest(f.id());
                                                        }
                                                        return builder.buildFuture();
                                                    })
                                                    .executes(ctx -> {
                                                        ServerPlayer player = ctx.getSource().getPlayerOrException();
                                                        if (!player.hasPermissions(3)) {
                                                            return 0;
                                                        }
                                                        ServerLevel level = (ServerLevel) player.level();
                                                        String id = StringArgumentType.getString(ctx, "zoneId");
                                                        String flagStr = StringArgumentType.getString(ctx, "flag");
                                                        ZPZoneFlag flag = ZPZonesRegistry.flagValueOf(flagStr);
                                                        if (flag == null) {
                                                            ctx.getSource().sendFailure(Component.literal("The flag " + flagStr + " doesn't exist. Try /zp3 zoneFlagsList"));
                                                            return 0;
                                                        }
                                                        if (!ZPZoneManager.INSTANCE.removeFlag(level, id, flag)) {
                                                            ctx.getSource().sendFailure(Component.literal("Failed to remove flag from zone."));
                                                            return 0;
                                                        }
                                                        ctx.getSource().sendSuccess(() -> Component.literal("Flag " + flag.id() + " removed from zone " + id + "!"), false);
                                                        return 1;
                                                    })
                                            )
                                    )
                            )
                            .then(Commands.literal("zoneFlagsList")
                                    .executes(ctx -> {
                                        ServerPlayer player = ctx.getSource().getPlayerOrException();
                                        if (!player.hasPermissions(3)) {
                                            return 0;
                                        }
                                        for (ZPZoneFlag availableFlags : ZPZonesRegistry.flagValues()) {
                                            ctx.getSource().sendSuccess(() -> Component.literal(availableFlags.id() + ", "), false);
                                        }
                                        return 1;
                                    })
                            )
                            .then(Commands.literal("zoneSetIntVar")
                                    .then(Commands.argument("zoneId", StringArgumentType.string())
                                            .suggests((ctx, builder) -> {
                                                ServerLevel level = ctx.getSource().getLevel();
                                                Objects.requireNonNull(ZPZoneManager.INSTANCE.getAllZonesOnLevel(level)).forEach(e -> builder.suggest(e.uniqueId()));
                                                return builder.buildFuture();
                                            })
                                            .then(Commands.argument("varId", StringArgumentType.string())
                                                    .suggests((ctx, builder) -> {
                                                        ServerPlayer player = ctx.getSource().getPlayer();
                                                        if (player != null) {
                                                            String zoneId = StringArgumentType.getString(ctx, "zoneId");
                                                            Collection<ZPZoneIntVar> variables = ZPZoneManager.INSTANCE.getAllZoneIntVariables(player.level(), zoneId);
                                                            if (variables != null) {
                                                                variables.forEach(e -> builder.suggest(e.getVariableId()));
                                                            }
                                                        }
                                                        return builder.buildFuture();
                                                    })
                                                    .then(Commands.argument("value", IntegerArgumentType.integer())
                                                            .executes(ctx -> {
                                                                ServerPlayer player = ctx.getSource().getPlayerOrException();
                                                                if (!player.hasPermissions(3)) {
                                                                    return 0;
                                                                }
                                                                ServerLevel level = player.serverLevel();
                                                                String zoneId = StringArgumentType.getString(ctx, "zoneId");
                                                                if (ZPZoneManager.INSTANCE.getZoneById(level, zoneId) == null) {
                                                                    ctx.getSource().sendFailure(Component.literal("Zone not found."));
                                                                    return 0;
                                                                }
                                                                String varId = StringArgumentType.getString(ctx, "varId");
                                                                int value = IntegerArgumentType.getInteger(ctx, "value");
                                                                ZPZoneIntVar var = ZPZoneManager.INSTANCE.getZoneIntVariableByID(level, zoneId, varId);
                                                                if (var == null) {
                                                                    ctx.getSource().sendFailure(Component.literal("Variable not found"));
                                                                    return 0;
                                                                }
                                                                final ZPZoneIntVar finalVar = new ZPZoneIntVar(varId, value, var.getMin(), var.getMax());
                                                                if (!ZPZoneManager.INSTANCE.setZoneIntVariable(level, zoneId, finalVar)) {
                                                                    ctx.getSource().sendFailure(Component.literal("Failed to set var."));
                                                                    return 0;
                                                                }
                                                                ctx.getSource().sendSuccess(() -> Component.literal("Variable " + varId + " updated"), false);
                                                                if (finalVar.additionalChatMsh() != null) {
                                                                    ctx.getSource().sendSuccess(() -> Component.literal(Objects.requireNonNull(finalVar.additionalChatMsh())), false);
                                                                }
                                                                return 1;
                                                            })
                                                    )
                                            )
                                    )
                            )
                            .then(Commands.literal("zoneGetIntVars")
                                    .then(Commands.argument("zoneId", StringArgumentType.string())
                                            .suggests((ctx, builder) -> {
                                                ServerLevel level = ctx.getSource().getLevel();
                                                Objects.requireNonNull(ZPZoneManager.INSTANCE.getAllZonesOnLevel(level)).forEach(e -> builder.suggest(e.uniqueId()));
                                                return builder.buildFuture();
                                            })
                                            .executes(ctx -> {
                                                ServerPlayer player = ctx.getSource().getPlayerOrException();
                                                if (!player.hasPermissions(3)) {
                                                    return 0;
                                                }
                                                ServerLevel level = player.serverLevel();
                                                String id = StringArgumentType.getString(ctx, "zoneId");
                                                if (ZPZoneManager.INSTANCE.getZoneById(level, id) == null) {
                                                    ctx.getSource().sendFailure(Component.literal("Zone " + id + " not found!"));
                                                    return 0;
                                                }
                                                Collection<ZPZoneIntVar> vars = ZPZoneManager.INSTANCE.getAllZoneIntVariables(level, id);
                                                if (vars == null || vars.isEmpty()) {
                                                    ctx.getSource().sendSuccess(() -> Component.literal("Zone " + id + " has no int variables."), false);
                                                    return 1;
                                                }
                                                ctx.getSource().sendSuccess(() -> Component.literal("Variables of zone '" + id + "':"), false);
                                                for (ZPZoneIntVar var : vars) {
                                                    ctx.getSource().sendSuccess(() -> Component.literal(" - " + var.getVariableId() + " = " + var.getValue()), false);
                                                }
                                                return 1;
                                            })
                                    )
                            )
                            .then(Commands.literal("debugSnapshotData")
                                    .executes(ctx -> {
                                        if (!ctx.getSource().hasPermission(3)) {
                                            ctx.getSource().sendFailure(Component.literal("No permission."));
                                            return 0;
                                        }
                                        ZPNetEntDataSyncer syncer = ZombiePlague3.netServer().getNetEntDataSyncer();
                                        StringBuilder sb = new StringBuilder();
                                        sb.append("===== ZP3 Debug Data Snapshot =====\n");
                                        sb.append("NetData Sync Struct size: ").append(syncer.structSize()).append("\n");
                                        sb.append("=================================");
                                        {
                                            String output = sb.toString();
                                            ZPLogger.info("\n" + output);
                                            ServerPlayer player = ctx.getSource().getPlayerOrException();
                                            for (String line : output.split("\n")) {
                                                player.sendSystemMessage(Component.literal(line));
                                            }
                                        }
                                        return 1;
                                    })
                            )
            );
        }
    }
}
