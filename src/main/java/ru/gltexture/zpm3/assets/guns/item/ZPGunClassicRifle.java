package ru.gltexture.zpm3.assets.guns.item;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.init.ZPSounds;
import ru.gltexture.zpm3.assets.guns.processing.logic.IGunLogicProcessor;
import ru.gltexture.zpm3.assets.guns.processing.logic.classic_rifle.ZPDefaultClassicRifleClientLogic;
import ru.gltexture.zpm3.assets.guns.processing.logic.classic_rifle.ZPDefaultClassicRifleServerLogic;
import ru.gltexture.zpm3.assets.guns.processing.logic.pistol.ZPDefaultPistolClientLogic;
import ru.gltexture.zpm3.assets.guns.processing.logic.pistol.ZPDefaultPistolServerLogic;
import ru.gltexture.zpm3.engine.service.ZPUtility;

public class ZPGunClassicRifle extends ZPBaseGun {
    protected @OnlyIn(Dist.CLIENT) IGunLogicProcessor clientLogic;
    protected IGunLogicProcessor serverLogic;

    public ZPGunClassicRifle(@NotNull Properties pProperties, @NotNull GunProperties gunProperties) {
        super(pProperties, gunProperties);
        gunProperties.getAnimationData().setHasShutterAnimation(true, GunProperties.AnimationData.ShutterAnimationType.CLASSIC);
        gunProperties.getAnimationData().setShutterSound(ZPSounds.rifle_shutter.get());
        gunProperties.getAnimationData().setShutterAnimationSpeed(3.0f);
        ZPUtility.sides().onlyClient(() -> {
            this.clientLogic = new ZPDefaultClassicRifleClientLogic();
        });
        this.serverLogic = new ZPDefaultClassicRifleServerLogic();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public IGunLogicProcessor getClientGunLogic() {
        return this.clientLogic;
    }

    @Override
    public IGunLogicProcessor getServerGunLogic() {
        return this.serverLogic;
    }
}
