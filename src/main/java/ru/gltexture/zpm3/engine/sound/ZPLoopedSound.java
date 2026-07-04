package ru.gltexture.zpm3.engine.sound;

import net.minecraft.client.resources.sounds.EntityBoundSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class ZPLoopedSound extends EntityBoundSoundInstance {
    private final LivingEntity livingEntity;

    public ZPLoopedSound(SoundEvent pSoundEvent, SoundSource pSource, float pVolume, float pPitch, LivingEntity pEntity, long pSeed) {
        super(pSoundEvent, pSource, pVolume, pPitch, pEntity, pSeed);
        this.looping = true;
        this.livingEntity = pEntity;
    }

    @Override
    public @NotNull Attenuation getAttenuation() {
        return Attenuation.LINEAR;
    }

    public LivingEntity getLivingEntity() {
        return this.livingEntity;
    }

    public void kill() {
        this.stop();
    }
}