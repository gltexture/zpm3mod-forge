package ru.gltexture.zpm3.engine.events.common;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.mixins.ext.IZPRecipesManagerExt;
import ru.gltexture.zpm3.modules.commands.zones.ZPZoneManager;

public class ZPCommonForge {
    @SubscribeEvent
    public void onServerStarted(ServerStartedEvent event) {
        RecipeManager manager = event.getServer().getRecipeManager();
        if (manager instanceof IZPRecipesManagerExt ext) {
            ZombiePlague3.getRecipesController().getRegistries().forEach(e -> {
                ext.zpm3forge$removeRecipes(e.getRecipesToRemove());
            });
        }
    }

    @SubscribeEvent
    public void onWorldLoad(LevelEvent.Load event) {
        if (event.getLevel() instanceof ServerLevel level) {
            ZPZoneManager.INSTANCE.loadFromJSON(level);
        }
    }

    @SubscribeEvent
    public void onWorldSave(LevelEvent.Save event) {
        if (event.getLevel() instanceof ServerLevel level) {
            ZPZoneManager.INSTANCE.writeToJSON(level);
        }
    }
}