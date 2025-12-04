package ru.gltexture.zpm3.assets.common;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.DispensibleContainerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.events.client.ZPRenderWorldEventWithPickUpCheck;
import ru.gltexture.zpm3.assets.common.events.common.ZPEntityItemEvent;
import ru.gltexture.zpm3.assets.common.events.common.ZPLivingEvents;
import ru.gltexture.zpm3.assets.common.events.common.ZPMobAttributes;
import ru.gltexture.zpm3.assets.common.global.ZPConstants;
import ru.gltexture.zpm3.assets.common.init.*;
import ru.gltexture.zpm3.assets.common.instances.block_entities.ZPFadingBlockEntity;
import ru.gltexture.zpm3.assets.common.keybind.ZPCommonKeyBindings;
import ru.gltexture.zpm3.assets.common.population.SetupPopulation;
import ru.gltexture.zpm3.assets.common.rendering.entities.misc.ZPRenderEntityItem;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.asset.ZPAsset;
import ru.gltexture.zpm3.engine.core.asset.ZPAssetData;
import ru.gltexture.zpm3.engine.helpers.ZPEntityRenderMatchHelper;
import ru.gltexture.zpm3.engine.instances.blocks.IHotLiquid;
import ru.gltexture.zpm3.engine.service.ZPUtility;

public class ZPCommonAsset extends ZPAsset {
    public ZPCommonAsset(@NotNull ZPAssetData zpAssetData) {
        super(zpAssetData);
    }

    public ZPCommonAsset() {
    }

    @Override
    public void commonSetup() {
        DispenserBlock.registerBehavior(Items.LAVA_BUCKET, new DefaultDispenseItemBehavior() {
            private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();

            public @NotNull ItemStack execute(@NotNull BlockSource p_123561_, @NotNull ItemStack p_123562_) {
                DispensibleContainerItem dispensiblecontaineritem = (DispensibleContainerItem) p_123562_.getItem();
                BlockPos blockpos = p_123561_.getPos().relative(p_123561_.getBlockState().getValue(DispenserBlock.FACING));
                Level level = p_123561_.getLevel();
                if (dispensiblecontaineritem.emptyContents(null, level, blockpos, null, p_123562_)) {
                    dispensiblecontaineritem.checkExtraContent(null, level, p_123562_, blockpos);
                    if (level instanceof ServerLevel) {
                        BlockEntity be = level.getBlockEntity(blockpos);
                        if (be != null) {
                            if (be instanceof ZPFadingBlockEntity zpFadingBlock) {
                                zpFadingBlock.setActive(true);
                            }
                        }
                    }
                    return new ItemStack(Items.BUCKET);
                } else {
                    return this.defaultDispenseItemBehavior.dispense(p_123561_, p_123562_);
                }
            }
        });
        DispenserBlock.registerBehavior(Items.BUCKET, new DefaultDispenseItemBehavior() {
            private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();

            public @NotNull ItemStack execute(@NotNull BlockSource pSource, @NotNull ItemStack pStack) {
                LevelAccessor levelaccessor = pSource.getLevel();
                BlockPos blockpos = pSource.getPos().relative(pSource.getBlockState().getValue(DispenserBlock.FACING));
                BlockState blockstate = levelaccessor.getBlockState(blockpos);
                Block block = blockstate.getBlock();
                if (block instanceof IHotLiquid iHotLiquid && iHotLiquid.bucketFillingChance() < 1.0f) {
                    return super.execute(pSource, pStack);
                }
                if (block instanceof BucketPickup) {
                    ItemStack itemstack = ((BucketPickup) block).pickupBlock(levelaccessor, blockpos, blockstate);
                    if (itemstack.isEmpty()) {
                        return super.execute(pSource, pStack);
                    } else {
                        levelaccessor.gameEvent(null, GameEvent.FLUID_PICKUP, blockpos);
                        Item item = itemstack.getItem();
                        pStack.shrink(1);
                        if (pStack.isEmpty()) {
                            return new ItemStack(item);
                        } else {
                            if (pSource.<DispenserBlockEntity>getEntity().addItem(new ItemStack(item)) < 0) {
                                this.defaultDispenseItemBehavior.dispense(pSource, new ItemStack(item));
                            }

                            return pStack;
                        }
                    }
                } else {
                    return super.execute(pSource, pStack);
                }
            }
        });
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void clientSetup() {
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void clientDestroy() {

    }

    @Override
    public void initMixins(ZombiePlague3.@NotNull IMixinEntry mixinEntry) {
        mixinEntry.addMixinConfigData(new ZombiePlague3.IMixinEntry.MixinConfig("common", "ru.gltexture.zpm3.assets.common.mixins.impl"),
                new ZombiePlague3.IMixinEntry.MixinClass("common.ZPTorchMixin", ZPSide.COMMON),
                new ZombiePlague3.IMixinEntry.MixinClass("common.ZPWallTorchMixin", ZPSide.COMMON),
                new ZombiePlague3.IMixinEntry.MixinClass("common.ZPPumpkinMixin", ZPSide.COMMON),
                new ZombiePlague3.IMixinEntry.MixinClass("common.ZPLavaMixin", ZPSide.COMMON),
                new ZombiePlague3.IMixinEntry.MixinClass("common.ZPFluidPlacedMixin", ZPSide.COMMON),
                new ZombiePlague3.IMixinEntry.MixinClass("common.ZPMobCategoryMixin", ZPSide.COMMON),
                new ZombiePlague3.IMixinEntry.MixinClass("common.ZPForgeItemMixin", ZPSide.COMMON)
        );
    }

    @Override
    public void initializeAsset(ZombiePlague3.@NotNull IAssetEntry assetEntry) {
        SetupPopulation.setup(ZombiePlague3.getPopulationController());
        assetEntry.addZP3RegistryClass(ZPSounds.class);
        assetEntry.addZP3RegistryClass(ZPItems.class);
        assetEntry.addZP3RegistryClass(ZPBlockItems.class);
        assetEntry.addZP3RegistryClass(ZPBlocks.class);
        assetEntry.addZP3RegistryClass(ZPTorchBlocks.class);
        assetEntry.addZP3RegistryClass(ZPEntityAttributes.class);
        assetEntry.addZP3RegistryClass(ZPEntities.class);
        assetEntry.addZP3RegistryClass(ZPBlockEntities.class);
        assetEntry.addZP3RegistryClass(ZPFluids.class);
        assetEntry.addZP3RegistryClass(ZPFluidTypes.class);
        assetEntry.addZP3RegistryClass(ZPDamageTypes.class);

        ZPUtility.sides().onlyClient(() -> {
            assetEntry.addEventClass(ZPRenderWorldEventWithPickUpCheck.class);
        });
        assetEntry.addEventClass(ZPLivingEvents.class);
        assetEntry.addEventClass(ZPMobAttributes.class);
        assetEntry.addEventClass(ZPEntityItemEvent.class);

        ZPUtility.sides().onlyClient(() -> {
            assetEntry.addZP3RegistryClass(ZPTabs.class);
        });
    }

    @Override
    public void preCommonInitializeAsset() {
        ZombiePlague3.registerConfigClass(new ZPConstants());
        ZPUtility.sides().onlyClient(() -> {
            ZPEntityRenderMatchHelper.matchEntityRendering(() -> EntityType.ITEM, ZPRenderEntityItem::new);
            ZombiePlague3.registerKeyBindings(new ZPCommonKeyBindings());;
        });
    }
}
