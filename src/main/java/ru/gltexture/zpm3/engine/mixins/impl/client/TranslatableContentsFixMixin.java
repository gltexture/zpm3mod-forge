package ru.gltexture.zpm3.engine.mixins.impl.client;

import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.contents.TranslatableContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(TranslatableContents.class)
public class TranslatableContentsFixMixin {
    @Inject(method = "decomposeTemplate", at = @At("HEAD"), cancellable = true)
    private void decomposeTemplate(String pFormatTemplate, Consumer<FormattedText> pConsumer, CallbackInfo ci) {
        if (pFormatTemplate == null) {
            ci.cancel();
        }
    }
}
