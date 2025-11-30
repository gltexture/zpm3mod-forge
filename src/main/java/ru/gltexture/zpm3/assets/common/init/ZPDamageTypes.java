package ru.gltexture.zpm3.assets.common.init;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageType;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZPRegistryConveyor;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.exceptions.ZPRuntimeException;
import ru.gltexture.zpm3.engine.helpers.gen.providers.ZPDamageTypesProvider;
import ru.gltexture.zpm3.engine.registry.ZPRegistry;

import java.util.logging.Level;

public class ZPDamageTypes extends ZPRegistry<DamageType> {
    public static ResourceKey<DamageType> zp_bullet;
    public static ResourceKey<DamageType> zp_bleeding;

    public ZPDamageTypes() {
        super(ZPRegistryConveyor.Target.DAMAGE_TYPE);
    }

    @Override
    protected void runRegister(@NotNull ZPRegSupplier<DamageType> regSupplier) {
        ZPDamageTypes.zp_bullet = this.createResourceKey("zp_bullet", (s) -> {
            ZPDamageTypesProvider.addDamageTypeToGen(new ZPDamageTypesProvider.ZPDamageTypeGenData(s, false, false, false, false, 0.0f, "never"));
        });

        ZPDamageTypes.zp_bleeding = this.createResourceKey("zp_bleeding", (s) -> {
            ZPDamageTypesProvider.addDamageTypeToGen(new ZPDamageTypesProvider.ZPDamageTypeGenData(s, true, false, false, false, 0.0f, "never"));
        });
    }

    @Override
    public void postProcessing() {
        super.postProcessing();
    }

    @NotNull
    public static Holder<DamageType> getDamageType(@NotNull ServerLevel level, @NotNull ResourceKey<DamageType> id) {
        return level.registryAccess()
                .registryOrThrow(Registries.DAMAGE_TYPE)
                .getHolder(id)
                .orElseThrow(() -> new ZPRuntimeException("DamageType " + id + " not found in registry!"));
    }

    @Override
    protected void postRegister(String name, RegistryObject<DamageType> object) {
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