/*
 *
 *  * zpm3forge
 *  * Copyright (C) 2026 gltexture
 *  *
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program. If not, see <https://www.gnu.org/licenses/>.
 *
 */

package ru.gltexture.zpm3.modules.guns.item;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.modules.guns.processing.logic.IGunLogicProcessor;
import ru.gltexture.zpm3.modules.guns.processing.logic.shotgun.ZPDefaultShotgunClientLogic;
import ru.gltexture.zpm3.modules.guns.processing.logic.shotgun.ZPDefaultShotgunServerLogic;
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
