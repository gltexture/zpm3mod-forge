package ru.gltexture.zpm3.assets.common.init;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZPRegistryConveyor;
import ru.gltexture.zpm3.engine.registry.ZPRegistry;

public class ZPEntityAttributes extends ZPRegistry<Attribute> {
    public static RegistryObject<RangedAttribute> zm_attack_range_multiplier;
    public static RegistryObject<RangedAttribute> zm_mining_speed;
    public static RegistryObject<RangedAttribute> zm_random_effect_chance;
    public static RegistryObject<RangedAttribute> zm_throw_a_gift_chance;

    public ZPEntityAttributes() {
        super(ZPRegistryConveyor.Target.ATTRIBUTE);
    }

    @Override
    protected void runRegister(@NotNull ZPRegSupplier<Attribute> regSupplier) {
        ZPEntityAttributes.zm_attack_range_multiplier = regSupplier.register("zm_attack_range_multiplier", () -> new RangedAttribute("zpm3.zm_attack_range_multiplier", 64.0D, 0.0D, 1024.0D)).registryObject();
        ZPEntityAttributes.zm_mining_speed = regSupplier.register("zm_mining_speed", () -> new RangedAttribute("zpm3.zm_mining_speed", 0.01f, 0.0f, 12.0f)).registryObject();
        ZPEntityAttributes.zm_random_effect_chance = regSupplier.register("zm_effect_chance", () -> new RangedAttribute("zpm3.zm_effect_chance", 0.015f, 0.0f, 1.0f)).registryObject();
        ZPEntityAttributes.zm_throw_a_gift_chance = regSupplier.register("zm_throw_a_gift_chance", () -> new RangedAttribute("zpm3.zm_throw_a_gift_chance", 0.01f, 0.0f, 1.0f)).registryObject();
    }

    @Override
    public void postProcessing() {
        super.postProcessing();
    }

    @Override
    protected void postRegister(String name, RegistryObject<Attribute> object) {
        super.postRegister(name, object);
    }

    @Override
    public int priority() {
        return 0;
    }

    @Override
    public @NotNull String getID() {
        return this.getClass().getSimpleName();
    }
}