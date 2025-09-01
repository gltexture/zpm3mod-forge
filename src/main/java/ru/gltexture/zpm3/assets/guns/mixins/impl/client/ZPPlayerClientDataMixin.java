package ru.gltexture.zpm3.assets.guns.mixins.impl.client;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import ru.gltexture.zpm3.assets.guns.mixins.ext.IZPPlayerClientDataExt;

@OnlyIn(Dist.CLIENT)
@Mixin(Player.class)
public abstract class ZPPlayerClientDataMixin implements IZPPlayerClientDataExt {
    @Unique
    private float[] scissors3Person;

    @Override
    public float[] getPlayerMuzzleflashScissor3Person() {
        if (this.scissors3Person == null) {
            this.scissors3Person = new float[]{1.0f, 1.0f};
        }
        return this.scissors3Person;
    }
}
