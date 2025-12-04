package ru.gltexture.zpm3.engine.mixins.ext;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.engine.mixins.impl.common.ZPPlayerMixin;

public interface IZPPlayerMixinExt {
    void setEnabledPickUpOnF(boolean enabledPickUpOnF);
    boolean enabledPickUpOnF();

    void getResultFromServer();
    void getResultFromClient();
    int getPing();
}
