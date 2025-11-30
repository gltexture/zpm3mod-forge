package ru.gltexture.zpm3.assets.mob_effects.instances;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientMobEffectExtensions;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.damage.ZPDamageSources;

import java.util.function.Consumer;

public class ZPBleedingEffect extends ZPDefaultMobEffect {

    public ZPBleedingEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return pDuration % (18 - pAmplifier * 2) == 0;
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity pLivingEntity, int pAmplifier) {
        if (!pLivingEntity.level().isClientSide()) {
            pLivingEntity.hurt(ZPDamageSources.bleed((ServerLevel) pLivingEntity.level()), 1);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void initializeClient(@NotNull Consumer<IClientMobEffectExtensions> consumer) {
        consumer.accept(new ZPDefaultMobEffect.DefaultZPEffectClientExtension(true, "bleeding.png"));
    }
}
