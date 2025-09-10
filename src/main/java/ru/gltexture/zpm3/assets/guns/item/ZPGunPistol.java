package ru.gltexture.zpm3.assets.guns.item;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.guns.processing.logic.IGunLogicProcessor;
import ru.gltexture.zpm3.assets.guns.processing.logic.pistol.ZPDefaultPistolClientLogic;
import ru.gltexture.zpm3.assets.guns.processing.logic.pistol.ZPDefaultPistolServerLogic;
import ru.gltexture.zpm3.engine.service.ZPUtility;

public class ZPGunPistol extends ZPBaseGun {
    protected @OnlyIn(Dist.CLIENT) IGunLogicProcessor clientLogic;
    protected IGunLogicProcessor serverLogic;

    public ZPGunPistol(@NotNull Properties pProperties, @NotNull GunProperties gunProperties) {
        super(pProperties, gunProperties);
        ZPUtility.sides().onlyClient(() -> {
            this.clientLogic = new ZPDefaultPistolClientLogic();
        });
        this.serverLogic = new ZPDefaultPistolServerLogic();
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
