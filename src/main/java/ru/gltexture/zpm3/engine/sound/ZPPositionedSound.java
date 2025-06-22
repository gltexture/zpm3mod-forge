package ru.gltexture.zpm3.engine.sound;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
public class ZPPositionedSound extends AbstractTickableSoundInstance {
    public ZPPositionedSound(SoundEvent pSoundEvent, SoundSource pSource, float pVolume, float pPitch, @NotNull Vector3f position, long pSeed) {
        super(pSoundEvent, pSource, RandomSource.create(pSeed));
        this.volume = pVolume;
        this.pitch = pPitch;

        this.x = position.x;
        this.y = position.y;
        this.z = position.z;
    }

    @Override
    public void tick() {
    }
}