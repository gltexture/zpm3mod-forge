package ru.gltexture.zpm3.engine.mixins;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.transformer.ext.IExtension;
import org.spongepowered.asm.mixin.transformer.ext.ITargetClassContext;

import java.util.concurrent.atomic.AtomicBoolean;

public class IMixinExt implements IExtension {
    private static final AtomicBoolean INIT = new AtomicBoolean(false);

    @Override
    public boolean checkActive(MixinEnvironment environment) {
        return true;
    }

    @Override
    public void preApply(ITargetClassContext context) {
        if (INIT.compareAndSet(false, true)) {
            ZPMixinPlugin.initLibs();
        }
    }

    @Override
    public void postApply(ITargetClassContext context) {

    }

    @Override
    public void export(MixinEnvironment env, String name, boolean force, ClassNode classNode) {

    }
}
