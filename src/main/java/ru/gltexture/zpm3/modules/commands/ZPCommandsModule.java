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
import ru.gltexture.zpm3.engine.client.rendering.ZPRenderHelper;
import ru.gltexture.zpm3.engine.service.ZPUtility;
import ru.gltexture.zpm3.engine.zones.ZPZoneFlag;
import ru.gltexture.zpm3.engine.zones.ZPZoneManager;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.module.ZPModule;
import ru.gltexture.zpm3.engine.core.module.ZPModuleData;
import ru.gltexture.zpm3.engine.events.ZPEventClass;
import ru.gltexture.zpm3.modules.commands.events.client.ZPCreativeUtilityMenuEvent;
import ru.gltexture.zpm3.modules.commands.events.client.ZPRenderZones;
import ru.gltexture.zpm3.modules.commands.imgui.ZPCreativeUtilityUI;

import java.util.*;

public class ZPCommandsModule extends ZPModule {
    public ZPCommandsModule(@NotNull ZPModuleData zpModuleData) {
        super(zpModuleData);
    }

    public ZPCommandsModule() {
    }

    @Override
    public void fml_commonSetupEvent() {
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void fml_clientSetupEvent() {
        if (ZPRenderHelper.INSTANCE.getDearUIRenderer() != null) {
            ZPRenderHelper.INSTANCE.getDearUIRenderer().getInterfacesManager().addInterface(new ZPCreativeUtilityUI());
        }
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
    public void initialize(ZombiePlague3.@NotNull IModuleEntry moduleEntry) {
        moduleEntry.addMinecraftEventClass(ZPCommandsEvent.class);
        ZPUtility.sides().onlyClient(() -> {
            moduleEntry.addMinecraftEventClass(ZPRenderZones.class);
            moduleEntry.addMinecraftEventClass(ZPCreativeUtilityMenuEvent.class);
        });
    }

    @Override
    public void preInitialize() {

    }

    @Override
    public void postInitialize() {

    }

    public static class ZPCommandsEvent implements ZPEventClass {

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
                            //                ZombiePlague3.net().sendToAll(ZPSendGlobalSettings_StoC.create());
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
                                    .then(Commands.argument("id", StringArgumentType.string())
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
                                                                                                String id = StringArgumentType.getString(ctx, "id");
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
                                                                                                ZPZoneManager.Zone zone = new ZPZoneManager.Zone(id, new Vector3i(x1, y1, z1), new Vector3i(x2, y2, z2), new HashSet<>());
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
                                    .then(Commands.argument("id", StringArgumentType.string())
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
                                                                                                String id = StringArgumentType.getString(ctx, "id");
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
                                    .then(Commands.argument("id", StringArgumentType.string())
                                            .executes(ctx -> {
                                                ServerPlayer player = ctx.getSource().getPlayerOrException();
                                                ServerLevel level = (ServerLevel) player.level();
                                                if (!player.hasPermissions(3)) {
                                                    return 0;
                                                }
                                                String id = StringArgumentType.getString(ctx, "id");

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
                                            ctx.getSource().sendSuccess(() -> Component.literal(zone.uniqueId() + " : " + zone.start() + " | " + zone.end() + " Flags: " + zone.flags()), false);
                                        }
                                        return 1;
                                    })
                            )
                            .then(Commands.literal("zoneEraseFlags")
                                    .then(Commands.argument("id", StringArgumentType.string())
                                            .executes(ctx -> {
                                                ServerPlayer player = ctx.getSource().getPlayerOrException();
                                                if (!player.hasPermissions(3)) {
                                                    return 0;
                                                }
                                                ServerLevel level = (ServerLevel) player.level();
                                                String id = StringArgumentType.getString(ctx, "id");
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
                                    .then(Commands.argument("id", StringArgumentType.string())
                                            .then(Commands.argument("flag", StringArgumentType.string())
                                                    .suggests((ctx, builder) -> {
                                                        for (ZPZoneFlag f : ZPZoneFlag.values()) {
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
                                                        String id = StringArgumentType.getString(ctx, "id");
                                                        String flagStr = StringArgumentType.getString(ctx, "flag");
                                                        ZPZoneFlag flag = ZPZoneFlag.valueOf(flagStr);
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
                                    .then(Commands.argument("id", StringArgumentType.string())
                                            .then(Commands.argument("flag", StringArgumentType.string())
                                                    .suggests((ctx, builder) -> {
                                                        for (ZPZoneFlag f : ZPZoneFlag.values()) {
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
                                                        String id = StringArgumentType.getString(ctx, "id");
                                                        String flagStr = StringArgumentType.getString(ctx, "flag");
                                                        ZPZoneFlag flag = ZPZoneFlag.valueOf(flagStr);
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
                                        for (ZPZoneFlag availableFlags : ZPZoneFlag.values()) {
                                            ctx.getSource().sendSuccess(() -> Component.literal(availableFlags.id() + ", "), false);
                                        }
                                        return 1;
                                    })
                            )
            );
        }
    }
}
