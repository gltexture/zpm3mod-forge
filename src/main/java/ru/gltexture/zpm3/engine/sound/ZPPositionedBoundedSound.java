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
public class ZPPositionedBoundedSound extends AbstractTickableSoundInstance {
    private final int bound;
    private int age;
    private boolean died;

    public ZPPositionedBoundedSound(SoundEvent pSoundEvent, SoundSource pSource, float pVolume, float pPitch, @NotNull Vector3f position, int bound, long pSeed) {
        super(pSoundEvent, pSource, RandomSource.create(pSeed));
        this.volume = pVolume;
        this.pitch = pPitch;
        this.bound = bound;

        this.x = position.x;
        this.y = position.y;
        this.z = position.z;

        this.died = false;
    }

    @Override
    public void tick() {
        if (this.age++ >= this.bound) {
            this.stop();
            this.died = true;
        }
    }

    public boolean isDied() {
        return this.died;
    }
}