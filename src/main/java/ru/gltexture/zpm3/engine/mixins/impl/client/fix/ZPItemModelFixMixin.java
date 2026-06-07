package ru.gltexture.zpm3.engine.mixins.impl.client.fix;

import net.minecraft.client.renderer.block.model.BlockElement;
import net.minecraft.client.renderer.block.model.ItemModelGenerator;
import net.minecraft.client.renderer.texture.SpriteContents;
import net.minecraft.core.Direction;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Set;

/**
 * @author https://github.com/MehVahdJukaar/modelfix-multi
 */
@Mixin(ItemModelGenerator.class)
public class ZPItemModelFixMixin {
    @Unique private static final float zpm3forge$EXPAND = 0.007f;
    @Unique private static final float zpm3forge$RECESS = 0.008f;

    /**
     * @author https://github.com/MehVahdJukaar/modelfix-multi
     */
    @Inject(method = "createSideElements", at = @At("RETURN"))
    public void increaseSide(SpriteContents pSprite, String pTexture, int pTintIndex, CallbackInfoReturnable<List<BlockElement>> cir) {
        final float recess = ZPItemModelFixMixin.zpm3forge$RECESS;
        final float expand = ZPItemModelFixMixin.zpm3forge$EXPAND;
        for (BlockElement e : cir.getReturnValue()) {
            Vector3f from = e.from;
            Vector3f to = e.to;
            Set<Direction> set = e.faces.keySet();
            if (set.size() == 1) {
                Direction dir = set.stream().findAny().get();
                switch (dir) {
                    case UP -> {
                        from.set(from.x() - expand, from.y() - recess, from.z() - expand);
                        to.set(to.x() + expand, to.y() - recess, to.z() + expand);
                    }
                    case DOWN -> {
                        from.set(from.x() - expand, from.y() + recess, from.z() - expand);
                        to.set(to.x() + expand, to.y() + recess, to.z() + expand);
                    }
                    case WEST -> {
                        from.set(from.x() - recess, from.y() + expand, from.z() - expand);
                        to.set(to.x() - recess, to.y() - expand, to.z() + expand);
                    }
                    case EAST -> {
                        from.set(from.x() + recess, from.y() + expand, from.z() - expand);
                        to.set(to.x() + recess, to.y() - expand, to.z() + expand);
                    }
                }
            }
        }
    }

    /**
     * @author https://github.com/MehVahdJukaar/modelfix-multi
     * @reason ...
     */
    @Overwrite
    private void createOrExpandSpan(List<ItemModelGenerator.Span> listSpans, ItemModelGenerator.SpanFacing spanFacing, int pixelX, int pixelY) {
        ItemModelGenerator.Span existingSpan = null;
        for (ItemModelGenerator.Span span2 : listSpans) {
            if (span2.getFacing() == spanFacing) {
                int i = spanFacing.isHorizontal() ? pixelY : pixelX;
                if (span2.getAnchor() != i) {
                    continue;
                }
                if (span2.getMax() != (!spanFacing.isHorizontal() ? pixelY : pixelX) - 1) {
                    continue;
                }
                existingSpan = span2;
                break;
            }
        }
        final int length = spanFacing.isHorizontal() ? pixelX : pixelY;
        if (existingSpan == null) {
            int newStart = spanFacing.isHorizontal() ? pixelY : pixelX;
            listSpans.add(new ItemModelGenerator.Span(spanFacing, length, newStart));
        } else {
            existingSpan.expand(length);
        }
    }
}
