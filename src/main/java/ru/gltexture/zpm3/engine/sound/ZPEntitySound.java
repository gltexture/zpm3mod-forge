package ru.gltexture.zpm3.engine.sound;

import net.minecraft.client.resources.sounds.EntityBoundSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class ZPEntitySound extends EntityBoundSoundInstance {
    public ZPEntitySound(SoundEvent pSoundEvent, SoundSource pSource, float pVolume, float pPitch, @NotNull Entity pEntity, long pSeed) {
        super(pSoundEvent, pSource, pVolume, pPitch, pEntity, pSeed);
        this.looping = false;
    }
}