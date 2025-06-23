package ru.gltexture.zpm3.assets.common.instances.items.tier;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public enum ZPTiers implements Tier {
    ZP_WOOD(0, 72, 2.0F, 0.0F, 15, () -> Ingredient.of(ItemTags.PLANKS)),
    ZP_IRON_1(1, 131, 4.0F, 1.0F, 15, () -> Ingredient.of(Items.IRON_INGOT)),
    ZP_IRON_2(1, 342, 4.5F, 1.5F, 15, () -> Ingredient.of(Items.IRON_INGOT)),
    ZP_IRON_3(1, 560, 6.0F, 2.5F, 15, () -> Ingredient.of(Items.IRON_INGOT)),
    ZP_IRON_4(2, 560, 8.0F, 3.5F, 15, () -> Ingredient.of(Items.IRON_INGOT)),
    ZP_IRON_5(2, 728, 8.0F, 4.0F, 15, () -> Ingredient.of(Items.IRON_INGOT)),
    ZP_IRON_6(2, 1024, 8.0F, 4.5F, 15, () -> Ingredient.of(Items.IRON_INGOT));

    public static TagKey<Block> getTagFromVanillaTier(ZPTiers tier)
    {
        return switch(tier)
        {
            case ZP_WOOD -> Tags.Blocks.NEEDS_WOOD_TOOL;
            case ZP_IRON_1, ZP_IRON_2, ZP_IRON_3, ZP_IRON_4, ZP_IRON_5, ZP_IRON_6 -> BlockTags.NEEDS_IRON_TOOL;
        };
    }

    private final int level;
    private final int uses;
    private final float speed;
    private final float damage;
    private final int enchantmentValue;
    private final LazyLoadedValue<Ingredient> repairIngredient;

    ZPTiers(int pLevel, int pUses, float pSpeed, float pDamage, int pEnchantmentValue, Supplier<Ingredient> pRepairIngredient) {
        this.level = pLevel;
        this.uses = pUses;
        this.speed = pSpeed;
        this.damage = pDamage;
        this.enchantmentValue = pEnchantmentValue;
        this.repairIngredient = new LazyLoadedValue<>(pRepairIngredient);
    }

    public int getUses() {
        return this.uses;
    }

    public float getSpeed() {
        return this.speed;
    }

    public float getAttackDamageBonus() {
        return this.damage;
    }

    public int getLevel() {
        return this.level;
    }

    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    public @NotNull Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Nullable
    public TagKey<Block> getTag() { return ZPTiers.getTagFromVanillaTier(this); }
}
