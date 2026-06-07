package ru.gltexture.zpm3.modules.guns.mixins.impl.client;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import ru.gltexture.zpm3.modules.guns.mixins.ext.IZPPlayerClientDataExt;

@OnlyIn(Dist.CLIENT)
@Mixin(Player.class)
public abstract class ZPPlayerClientDataMuzzleflash3PMixin implements IZPPlayerClientDataExt {
    @Unique
    private float[] zpm3forge$scissors3Person;

    @Override
    public float[] zpm3forge$getPlayerMuzzleflashScissor3Person() {
        if (this.zpm3forge$scissors3Person == null) {
            this.zpm3forge$scissors3Person = new float[]{1.0f, 1.0f};
        }
        return this.zpm3forge$scissors3Person;
    }
}
