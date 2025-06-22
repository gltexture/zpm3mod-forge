package ru.gltexture.zpm3.assets.common.instances.items;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EnderpearlItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.global.ZPConstants;
import ru.gltexture.zpm3.assets.common.init.ZPEntities;
import ru.gltexture.zpm3.assets.common.instances.entities.ZPAcidBottleEntity;
import ru.gltexture.zpm3.assets.entity.nbt.ZPTagsList;
import ru.gltexture.zpm3.engine.nbt.ZPEntityTag;
import ru.gltexture.zpm3.engine.nbt.ZPEntityNBT;
import ru.gltexture.zpm3.engine.objects.entities.ZPThrowableEntity;
import ru.gltexture.zpm3.engine.objects.items.ZPItem;
import ru.gltexture.zpm3.engine.utils.Pair;

public class ZPItemThrowable extends ZPItem {
    private final ThrowableProjectileFabric throwableProjectileFabric;

    public ZPItemThrowable(@NotNull ThrowableProjectileFabric throwableProjectileFabric, Properties pProperties) {
        super(pProperties);
        this.throwableProjectileFabric = throwableProjectileFabric;
    }

    public ThrowableProjectileFabric getThrowableProjectileFabric() {
        return this.throwableProjectileFabric;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player pPlayer, @NotNull InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        pPlayer.getCooldowns().addCooldown(this, ZPConstants.DEFAULT_ITEMS_THROW_COOLDOWN);

        pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
        if (!pLevel.isClientSide) {
            ZPAcidBottleEntity acidBottle = new ZPAcidBottleEntity(ZPEntities.acid_bottle_entity.get(), pPlayer, pLevel);
            acidBottle.setItem(itemstack);
            acidBottle.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 1.0F, 12.0F);
            pLevel.addFreshEntity(this.getThrowableProjectileFabric().createThrowableEntity(ZPConstants.DEFAULT_ITEMS_THROW_INACCURACY, ZPConstants.DEFAULT_ITEMS_THROW_VELOCITY, itemstack, pLevel, pPlayer, pHand));
        }

        pPlayer.awardStat(Stats.ITEM_USED.get(this));
        if (!pPlayer.getAbilities().instabuild) {
            itemstack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
    }

    @FunctionalInterface
    public interface ThrowableProjectileFabric {
        ZPThrowableEntity createThrowableEntity(float inaccuracy, float velocity, @NotNull ItemStack itemstack, @NotNull Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pHand);
    }
}
