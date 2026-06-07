package ru.gltexture.zpm3.modules.player.mixins.impl.client;

import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import ru.gltexture.zpm3.modules.player.mixins.IPlayerMixin;

@OnlyIn(Dist.CLIENT)
@Mixin(LocalPlayer.class)
public class ZPCPlayerFeaturesMixin implements IPlayerMixin {
    @Override
    public boolean canEat(boolean pCanAlwaysEat) {
        return true;
    }
}