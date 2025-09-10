package ru.gltexture.zpm3.assets.common;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.events.both.ZPLivingKnockBack;
import ru.gltexture.zpm3.assets.common.init.*;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.asset.ZPAsset;
import ru.gltexture.zpm3.engine.core.asset.ZPAssetData;
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
                new ZombiePlague3.IMixinEntry.MixinClass("both.TorchMixin", ZPSide.BOTH),
                new ZombiePlague3.IMixinEntry.MixinClass("both.WallTorchMixin", ZPSide.BOTH),
                new ZombiePlague3.IMixinEntry.MixinClass("both.PumpkinMixin", ZPSide.BOTH),
                new ZombiePlague3.IMixinEntry.MixinClass("both.LavaMixin", ZPSide.BOTH)
        );
    }

    @Override
    public void initAsset(ZombiePlague3.@NotNull IAssetEntry assetEntry) {
        assetEntry.addRegistryClass(ZPSounds.class);
        assetEntry.addRegistryClass(ZPItems.class);
        assetEntry.addRegistryClass(ZPBlockItems.class);
        assetEntry.addRegistryClass(ZPBlocks.class);
        assetEntry.addRegistryClass(ZPTorchBlocks.class);
        assetEntry.addRegistryClass(ZPEntities.class);
        assetEntry.addRegistryClass(ZPBlockEntities.class);
        assetEntry.addRegistryClass(ZPFluids.class);
        assetEntry.addRegistryClass(ZPFluidTypes.class);
        assetEntry.addRegistryClass(ZPDamageTypes.class);

        assetEntry.addEventClass(ZPLivingKnockBack.class);

        ZPUtility.sides().onlyClient(() -> {
            assetEntry.addRegistryClass(ZPTabs.class);
        });
    }
}
