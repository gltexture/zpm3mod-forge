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

package ru.gltexture.zpm3.modules.blocks.init;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.modules.blocks.init.helper.ZPRegTorchBlocks;
import ru.gltexture.zpm3.modules.blocks.instances.blocks.torch.ZPFadingTorchBlock;
import ru.gltexture.zpm3.modules.blocks.instances.blocks.torch.ZPFadingTorchBlockWall;
import ru.gltexture.zpm3.engine.core.ZPRegistryConveyor;
import ru.gltexture.zpm3.engine.instances.blocks.*;
import ru.gltexture.zpm3.engine.registry.ZPCommonRegistry;
import ru.gltexture.zpm3.engine.registry.collection.IZPCollectRegistryObjects;

public class ZPTorchBlocks extends ZPCommonRegistry<Block> implements IZPCollectRegistryObjects {
    public static RegistryObject<ZPTorchBlock> wall_lamp;
    public static RegistryObject<ZPWallTorchBlock> wall_lamp_wall;

    public static RegistryObject<ZPTorchBlock> wall_lamp_off;
    public static RegistryObject<ZPWallTorchBlock> wall_lamp_off_wall;

    public static RegistryObject<ZPFadingTorchBlock> torch2;
    public static RegistryObject<ZPFadingTorchBlockWall> torch2_wall;

    public static RegistryObject<ZPFadingTorchBlock> torch3;
    public static RegistryObject<ZPFadingTorchBlockWall> torch3_wall;

    public static RegistryObject<ZPFadingTorchBlock> torch4;
    public static RegistryObject<ZPFadingTorchBlockWall> torch4_wall;

    public static RegistryObject<ZPTorchBlock> torch5;
    public static RegistryObject<ZPWallTorchBlock> torch5_wall;

    public ZPTorchBlocks() {
        super(ZPRegistryConveyor.Target.BLOCK);
    }

    @Override
    protected void runRegister(@NotNull ZPRegSupplier<Block> regSupplier) {
        ZPRegTorchBlocks.init(this, regSupplier);
    }

    @Override
    public void preProcessing() {
        super.preProcessing();
    }

    @Override
    public void postProcessing() {
        super.postProcessing();
    }

    @Override
    protected void postRegister(String name, RegistryObject<Block> object) {
    }

    @Override
    public int priority() {
        return 1;
    }

    @Override
    public @NotNull String getID() {
        return this.getClass().getSimpleName();
    }
}