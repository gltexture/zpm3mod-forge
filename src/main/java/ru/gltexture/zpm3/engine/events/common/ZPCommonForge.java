package ru.gltexture.zpm3.engine.events.common;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.assets.guns.item.ZPBaseGun;
import ru.gltexture.zpm3.engine.mixins.ext.IZPRecipesManagerExt;
import ru.gltexture.zpm3.engine.mixins.impl.common.ZPRecipeManagerMixin;
import ru.gltexture.zpm3.engine.recipes.ZPRecipesController;
import ru.gltexture.zpm3.engine.recipes.ZPRecipesRegistry;

import java.util.Collection;
import java.util.Map;

public class ZPCommonForge {
    @SubscribeEvent
    public void onBreakBlock(PlayerInteractEvent.LeftClickBlock event) {
        if (ZPCommonForge.shouldCancelInteraction(event.getEntity())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onAttackEntity(AttackEntityEvent event) {
        if (ZPCommonForge.shouldCancelInteraction(event.getEntity())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (ZPCommonForge.shouldCancelInteraction(event.getEntity())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        if (ZPCommonForge.shouldCancelInteraction(event.getEntity())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        ServerPlayer player = (ServerPlayer) event.getEntity();
    }

    @SubscribeEvent
    public void onPlayerChangeDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        ServerPlayer player = (ServerPlayer) event.getEntity();
    }

    private static boolean shouldCancelInteraction(@NotNull Player player) {
        ItemStack stack = player.getMainHandItem();
        return stack.getItem() instanceof ZPBaseGun;
    }

    @SubscribeEvent
    public void onServerStarted(ServerStartedEvent event) {
        RecipeManager manager = event.getServer().getRecipeManager();
        if (manager instanceof IZPRecipesManagerExt ext) {
            ZombiePlague3.getRecipesController().getRegistries().forEach(e -> {
                ext.removeRecipes(e.getRecipesToRemove());
            });
        }
    }
}