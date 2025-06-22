package ru.gltexture.zpm3.assets.entity.logic;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import ru.gltexture.zpm3.assets.common.utils.ZPCommonClientUtils;
import ru.gltexture.zpm3.assets.entity.nbt.ZPTagsList;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;
import ru.gltexture.zpm3.engine.nbt.ZPEntityNBT;
import ru.gltexture.zpm3.engine.sound.ZPPositionedSound;
import ru.gltexture.zpm3.engine.utils.ZPUtility;

@OnlyIn(Dist.CLIENT)
public class EntityClientSideLogic implements EntityTickEventLogic {
    @Override
    public void onTickEntity(@NotNull Entity entity) {
        ZPEntityNBT zpEntityTag = new ZPEntityNBT(entity);

        if (zpEntityTag.getTagInt(ZPTagsList.ACID_AFFECT_COOLDOWN) > 0) {
            this.addAcidParticles(entity);
            if (entity.tickCount % 3 == 0) {
                ZPUtility.sounds().play(new ZPPositionedSound(SoundEvents.FIRE_EXTINGUISH, SoundSource.MASTER, 0.375f, 1.15f, entity.position().toVector3f(), 0L));
            }
        }
    }

    private void addAcidParticles(Entity entity) {
        final int maxParticles = 2;

        for (int i = 0; i < maxParticles; ++i) {
            final Vector3f randomVector = ZPRandom.instance.randomVector3f(0.05f);
            final Vector3f position = entity.position().toVector3f().add(0.0f, entity.getBbHeight() * ((float) i / maxParticles), 0.0f);
            position.add(ZPRandom.instance.randomVector3f(0.3f, new Vector3f(0.6f, 0.3f, 0.6f)));
            ZPCommonClientUtils.emmitAcidParticle(2.2f + ZPRandom.getRandom().nextFloat(0.3f), position, new Vector3f(randomVector.x, (randomVector.y * 0.1f) + 0.05f, randomVector.z));
        }
    }
}
