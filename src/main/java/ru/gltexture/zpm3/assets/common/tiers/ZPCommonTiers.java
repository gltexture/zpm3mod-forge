package ru.gltexture.zpm3.assets.common.tiers;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.engine.instances.items.tier.ZPTier;
import ru.gltexture.zpm3.engine.instances.items.tier.ZPTierData;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public enum ZPCommonTiers implements ZPTier {
    ZP_WOOD("zp_wood", 1, 72, 2.0F, 0.0F, 15, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(ItemTags.PLANKS), null, Tiers.WOOD),
    ZP_WRENCH("zp_wrench", 2, 128, 8.0F, 0.0F, 15, BlockTags.NEEDS_IRON_TOOL, () -> Ingredient.of(Items.IRON_INGOT), ZP_WOOD, Tiers.WOOD),
    ZP_IRON_1("zp_iron_1", 2, 131, 4.0F, 1.0F, 15, BlockTags.NEEDS_IRON_TOOL, () -> Ingredient.of(Items.IRON_INGOT), ZP_WRENCH, Tiers.WOOD),
    ZP_IRON_2("zp_iron_2", 2, 342, 4.5F, 1.5F, 15, BlockTags.NEEDS_IRON_TOOL, () -> Ingredient.of(Items.IRON_INGOT), ZP_IRON_1, Tiers.WOOD),
    ZP_IRON_3("zp_iron_3", 3, 560, 6.0F, 2.5F, 15, BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(Items.IRON_INGOT), ZP_IRON_2, Tiers.WOOD),
    ZP_IRON_4("zp_iron_4", 3, 560, 8.0F, 3.5F, 15, BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(Items.IRON_INGOT), ZP_IRON_3, Tiers.WOOD),
    ZP_IRON_5("zp_iron_5", 4, 728, 8.0F, 4.0F, 15, BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(Items.IRON_INGOT), ZP_IRON_4, Tiers.WOOD),
    ZP_IRON_6("zp_iron_6", 4, 1024, 8.0F, 4.5F, 15, BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(Items.IRON_INGOT), ZP_IRON_5, Tiers.WOOD);

    public final String id;
    public final int level;
    public final int uses;
    public final float speed;
    public final float damage;
    public final int enchant;
    public final TagKey<Block> tag;
    public final Supplier<Ingredient> repair;

    public final Object after;
    public final Object before;

    ZPCommonTiers(String id, int level, int uses, float speed, float damage, int enchant, TagKey<Block> tag, Supplier<Ingredient> repair, Object after, Object before) {
        this.id = id;
        this.level = level;
        this.uses = uses;
        this.speed = speed;
        this.damage = damage;
        this.enchant = enchant;
        this.tag = tag;
        this.repair = repair;
        this.after = after;
        this.before = before;
    }

    @Override
    public ZPTierData init() {
        return new ZPTierData(this.id, this, this.after == null ? new ArrayList<>() : List.of(this.after), List.of(this.before));
    }

    @Override
    public int getLevel() {
        return this.level;
    }

    public Object getAfter() {
        return this.after;
    }

    public Object getBefore() {
        return this.before;
    }

    @Override
    public float getSpeed() {
        return this.speed;
    }

    @Override
    public float getAttackDamageBonus() {
        return this.damage;
    }

    @Override
    public int getUses() {
        return this.uses;
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchant;
    }

    @Override
    public @NotNull Ingredient getRepairIngredient() {
        return this.repair.get();
    }

    @Nullable
    @Override
    public TagKey<Block> getTag() {
        return this.tag;
    }

    @Override
    public ZPTier[] tiers() {
        return values();
    }
}
