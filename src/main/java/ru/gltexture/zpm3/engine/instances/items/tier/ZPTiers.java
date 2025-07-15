package ru.gltexture.zpm3.engine.instances.items.tier;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.engine.instances.items.harvest.ZPHarvestLevel;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public enum ZPTiers implements Tier {
    ZP_WOOD(ZPHarvestLevel.HARVEST_STONE, 72, 2.0F, 0.0F, 15, () -> Ingredient.of(ItemTags.PLANKS)),
    ZP_IRON_1(ZPHarvestLevel.HARVEST_IRON, 131, 4.0F, 1.0F, 15, () -> Ingredient.of(Items.IRON_INGOT)),
    ZP_IRON_2(ZPHarvestLevel.HARVEST_IRON, 342, 4.5F, 1.5F, 15, () -> Ingredient.of(Items.IRON_INGOT)),
    ZP_IRON_3(ZPHarvestLevel.HARVEST_IRON, 560, 6.0F, 2.5F, 15, () -> Ingredient.of(Items.IRON_INGOT)),
    ZP_IRON_4(ZPHarvestLevel.HARVEST_DIAMOND, 560, 8.0F, 3.5F, 15, () -> Ingredient.of(Items.IRON_INGOT)),
    ZP_IRON_5(ZPHarvestLevel.HARVEST_DIAMOND, 728, 8.0F, 4.0F, 15, () -> Ingredient.of(Items.IRON_INGOT)),
    ZP_IRON_6(ZPHarvestLevel.HARVEST_DIAMOND, 1024, 8.0F, 4.5F, 15, () -> Ingredient.of(Items.IRON_INGOT));

    public static TagKey<Block> getTagFromHarvestLevel(ZPHarvestLevel level) {
        return switch (level) {
            case HARVEST_STONE -> BlockTags.NEEDS_STONE_TOOL;
            case HARVEST_IRON -> BlockTags.NEEDS_IRON_TOOL;
            case HARVEST_DIAMOND, HARVEST_OBSIDIAN, HARVEST_ALL -> BlockTags.NEEDS_DIAMOND_TOOL;
        };
    }

    private final ZPHarvestLevel zpHarvestLevel;
    private final int uses;
    private final float speed;
    private final float damage;
    private final int enchantmentValue;
    private final LazyLoadedValue<Ingredient> repairIngredient;

    ZPTiers(@NotNull ZPHarvestLevel zpHarvestLevel, int pUses, float pSpeed, float pDamage, int pEnchantmentValue, Supplier<Ingredient> pRepairIngredient) {
        this.zpHarvestLevel = zpHarvestLevel;
        this.uses = pUses;
        this.speed = pSpeed;
        this.damage = pDamage;
        this.enchantmentValue = pEnchantmentValue;
        this.repairIngredient = new LazyLoadedValue<>(pRepairIngredient);
        this.init();
    }

    public List<ZPTierData> init() {
        final List<ZPTierData> tiers = new ArrayList<>();
        final List<Object> after = new ArrayList<>();
        final List<Object> before = new ArrayList<>();

        switch (this.getZPHarvestLevel()) {
            case HARVEST_STONE: {
                before.add(ResourceLocation.fromNamespaceAndPath("minecraft", "iron"));
                break;
            }
            case HARVEST_IRON: {
                after.add(ResourceLocation.fromNamespaceAndPath("minecraft", "stone"));
                before.add(ResourceLocation.fromNamespaceAndPath("minecraft", "diamond"));
                break;
            }
            case HARVEST_DIAMOND: {
                after.add(ResourceLocation.fromNamespaceAndPath("minecraft", "iron"));
                before.add(ResourceLocation.fromNamespaceAndPath("minecraft", "netherite"));
                break;
            }
            case HARVEST_OBSIDIAN: {
                after.add(ResourceLocation.fromNamespaceAndPath("minecraft", "diamond"));
                before.add(ResourceLocation.fromNamespaceAndPath("minecraft", "netherite"));
                break;
            }
            case HARVEST_ALL: {
                after.add(ResourceLocation.fromNamespaceAndPath("minecraft", "netherite"));
                break;
            }
        }

        tiers.add(new ZPTierData(this.name().toLowerCase(), this, after, before));
        return tiers;
    }

    public ZPHarvestLevel getZPHarvestLevel() {
        return this.zpHarvestLevel;
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
        return this.getZPHarvestLevel().getLevel();
    }

    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    public @NotNull Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Nullable
    public TagKey<Block> getTag() { return ZPTiers.getTagFromHarvestLevel(this.getZPHarvestLevel()); }
}
