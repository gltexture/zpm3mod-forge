package ru.gltexture.zpm3.assets.common.events.common;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.events.ZPEventClass;
import ru.gltexture.zpm3.engine.service.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ZPMobAttributes implements ZPEventClass {
    public static final List<Pair<RegistryObject<EntityType<? extends LivingEntity>>, Supplier<AttributeSupplier.Builder>>> pairsToAttachAttributeCreation = new ArrayList<>();

    @SuppressWarnings("all")
    public static void addNewAttributeCreationUnsafe(@NotNull Object registryObject, @NotNull Supplier<AttributeSupplier.Builder> builder) {
        ZPMobAttributes.pairsToAttachAttributeCreation.add(Pair.of((RegistryObject<EntityType<? extends LivingEntity>>) registryObject, builder));
    }

    public static void addNewAttributeCreation(@NotNull RegistryObject<EntityType<? extends LivingEntity>> registryObject, @NotNull Supplier<AttributeSupplier.Builder> builder) {
        ZPMobAttributes.pairsToAttachAttributeCreation.add(Pair.of(registryObject, builder));
    }

    @SubscribeEvent
    public static void exec(@NotNull EntityAttributeCreationEvent event) {
        ZPMobAttributes.pairsToAttachAttributeCreation.forEach((e) -> {
            event.put(e.first().get(), e.second().get().build());
        });
    }

    @Override
    public @NotNull ZPSide getSide() {
        return ZPSide.COMMON;
    }

    @Override
    public @NotNull Mod.EventBusSubscriber.Bus getBus() {
        return Mod.EventBusSubscriber.Bus.MOD;
    }
}
