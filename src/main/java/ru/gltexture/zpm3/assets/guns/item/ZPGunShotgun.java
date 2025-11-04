package ru.gltexture.zpm3.assets.guns.item;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.guns.processing.logic.IGunLogicProcessor;
import ru.gltexture.zpm3.assets.guns.processing.logic.shotgun.ZPDefaultShotgunClientLogic;
import ru.gltexture.zpm3.assets.guns.processing.logic.shotgun.ZPDefaultShotgunServerLogic;
import ru.gltexture.zpm3.engine.service.ZPUtility;

public class ZPGunShotgun extends ZPBaseGun {
    protected @OnlyIn(Dist.CLIENT) IGunLogicProcessor clientLogic;
    protected IGunLogicProcessor serverLogic;

    public ZPGunShotgun(@NotNull Properties pProperties, @NotNull GunProperties gunProperties) {
        super(pProperties, gunProperties);
        gunProperties.getAnimationData().setHasShutterAnimation(true, GunProperties.AnimationData.ShutterAnimationType.SHOTGUN);
        ZPUtility.sides().onlyClient(() -> {
            this.clientLogic = new ZPDefaultShotgunClientLogic();
        });
        this.serverLogic = new ZPDefaultShotgunServerLogic();
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
