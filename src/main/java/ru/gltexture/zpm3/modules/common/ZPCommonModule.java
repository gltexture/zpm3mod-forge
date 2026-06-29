package ru.gltexture.zpm3.modules.common;

import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import ru.gltexture.zpm3.modules.blocks.init.*;
import ru.gltexture.zpm3.modules.common.init.*;
import ru.gltexture.zpm3.modules.food_medicine.init.ZPFoodMedicineItems;
import ru.gltexture.zpm3.modules.guns.init.ZPGunItems;
import ru.gltexture.zpm3.modules.melee_throwables_tools.init.ZPMeleeThrowableToolsItems;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.module.ZPModule;
import ru.gltexture.zpm3.engine.core.module.ZPModuleData;
import ru.gltexture.zpm3.engine.recipes.ZPRecipesController;
import ru.gltexture.zpm3.engine.recipes.ZPRecipesRegistry;
import ru.gltexture.zpm3.engine.recipes.IZPRecipeSpec;
import ru.gltexture.zpm3.engine.service.ZPUtility;
import ru.gltexture.zpm3.modules.misc_items.init.ZPMiscItems;

import java.util.ArrayList;
import java.util.Collection;
import java.util.*;

public class ZPCommonModule extends ZPModule {
    public ZPCommonModule(@NotNull ZPModuleData zpModuleData) {
        super(zpModuleData);
    }

    public ZPCommonModule() {
    }

    @Override
    public void fml_commonSetupEvent() {
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void fml_clientSetupEvent() {
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void clientShutDown() {

    }

    //@Override
    //public void initMixins(ZombiePlague3.@NotNull IMixinEntry mixinEntry) {
    //    mixinEntry.addMixinConfigData(new ZombiePlague3.IMixinEntry.MixinConfig("common", "ru.gltexture.zpm3.modules.common.mixins.impl"),
    //            new ZombiePlague3.IMixinEntry.MixinClass("common.ZPFadingTorchMixin", ZPSide.COMMON),
    //            new ZombiePlague3.IMixinEntry.MixinClass("common.ZPWallTorchMixin", ZPSide.COMMON),
    //            new ZombiePlague3.IMixinEntry.MixinClass("common.ZPFadingPumpkinMixin", ZPSide.COMMON),
    //            new ZombiePlague3.IMixinEntry.MixinClass("common.ZPFadingLavaMixin", ZPSide.COMMON),
    //            new ZombiePlague3.IMixinEntry.MixinClass("common.ZPFluidPlacedFadingBlockMixin", ZPSide.COMMON),
    //            new ZombiePlague3.IMixinEntry.MixinClass("common.ZPMobCategoryIncreaseSpawnMixin", ZPSide.COMMON),
    //            new ZombiePlague3.IMixinEntry.MixinClass("common.ZPMilkDisableMixin", ZPSide.COMMON)
    //    );
    //}

    @Override
    public void initialize(ZombiePlague3.@NotNull IModuleEntry moduleEntry) {
        moduleEntry.addRegistryClass(ZPSounds.class);
        moduleEntry.addRegistryClass(ZPDamageTypes.class);
        ZPUtility.sides().onlyClient(() -> {
            moduleEntry.addRegistryClass(ZPTabs.class);
        });
    }

    @Override
    public void preInitialize() {
    }

    @Override
    public void postInitialize() {
    }
}
