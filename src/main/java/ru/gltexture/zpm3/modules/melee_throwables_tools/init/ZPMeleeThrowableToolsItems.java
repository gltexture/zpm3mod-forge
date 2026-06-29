package ru.gltexture.zpm3.modules.melee_throwables_tools.init;

import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZPRegistryConveyor;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.instances.items.*;
import ru.gltexture.zpm3.engine.recipes.IZPRecipeSpec;
import ru.gltexture.zpm3.engine.recipes.ZPRecipesController;
import ru.gltexture.zpm3.engine.recipes.ZPRecipesRegistry;
import ru.gltexture.zpm3.engine.registry.ZPRegistry;
import ru.gltexture.zpm3.engine.registry.collection.IZPCollectRegistryObjects;
import ru.gltexture.zpm3.modules.blocks.init.ZPBlocks;
import ru.gltexture.zpm3.modules.common.ZPCommonModule;
import ru.gltexture.zpm3.modules.food_medicine.init.ZPFoodMedicineItems;
import ru.gltexture.zpm3.modules.guns.init.ZPGunItems;
import ru.gltexture.zpm3.modules.melee_throwables_tools.init.helper.ZPRegToolItems;
import ru.gltexture.zpm3.modules.melee_throwables_tools.init.helper.ZPRegMelee;
import ru.gltexture.zpm3.modules.melee_throwables_tools.init.helper.ZPRegThrowable;
import ru.gltexture.zpm3.modules.melee_throwables_tools.instances.items.ZPMatches;
import ru.gltexture.zpm3.modules.melee_throwables_tools.instances.items.ZPWrenchTool;
import ru.gltexture.zpm3.modules.misc_items.init.ZPMiscItems;

import java.util.*;

public class ZPMeleeThrowableToolsItems extends ZPRegistry<Item> implements IZPCollectRegistryObjects {
    // ITEMS
    public static RegistryObject<ZPItemThrowable> acid_bottle;
    public static RegistryObject<ZPItemThrowable> plate;
    public static RegistryObject<ZPItemThrowable> rock;
    public static RegistryObject<ZPItemBucket> acid_bucket;
    public static RegistryObject<ZPItemBucket> toxicwater_bucket;

    // TOOLS
    public static RegistryObject<ZPWrenchTool> wrench;
    public static RegistryObject<ZPMatches> matches;

    // MELEE
    public static RegistryObject<ZPItemSword> bat;
    public static RegistryObject<ZPItemSword> iron_club;
    public static RegistryObject<ZPItemSword> pipe;
    public static RegistryObject<ZPItemSword> golf_club;
    public static RegistryObject<ZPItemAxe> hatchet;
    public static RegistryObject<ZPItemPickaxe> sledgehammer;
    public static RegistryObject<ZPItemSword> crowbar;
    public static RegistryObject<ZPItemSword> cleaver;

    public ZPMeleeThrowableToolsItems() {
        super(ZPRegistryConveyor.Target.ITEM);
    }

    @Override
    protected void runRegister(@NotNull ZPRegSupplier<Item> regSupplier) {
        ZPRegThrowable.init(regSupplier);
        ZPRegToolItems.init(regSupplier);
        ZPRegMelee.init(regSupplier);
    }

    @Override
    protected void postRegister(String name, RegistryObject<Item> object) {
    }

    @Override
    public int priority() {
        return 0;
    }

    @Override
    public @NotNull String getID() {
        return this.getClass().getSimpleName();
    }
}