package ru.gltexture.zpm3.assets.mob_effects.instances;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientMobEffectExtensions;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;

public class ZPAdrenalineEffect extends ZPDefaultMobEffect {
    private static final UUID ADRENALINE_SLOW_UUID = UUID.fromString("61d86bb4-1e71-47b9-a9eb-0046ac70f8bc");
    private static final UUID ADRENALINE_HANDS_UUID = UUID.fromString("d5db9c0e-17de-47d9-8099-78e7e096b549");

    public ZPAdrenalineEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, ZPAdrenalineEffect.ADRENALINE_SLOW_UUID.toString(), 0.08, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(ForgeMod.ENTITY_REACH.get(), ZPAdrenalineEffect.ADRENALINE_HANDS_UUID.toString(), 0.25f, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return false;
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity pLivingEntity, int pAmplifier) {
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void initializeClient(@NotNull Consumer<IClientMobEffectExtensions> consumer) {
        consumer.accept(new DefaultZPEffectClientExtension(true, "adrenaline.png"));
    }
}
