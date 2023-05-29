package net.amik.createarsenal;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.amik.createarsenal.shell.ShellEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class BulletRenderer extends ThrownItemRenderer<ShellEntity> {
    private final ItemRenderer itemRenderer;
    private final float scale;
    public BulletRenderer(EntityRendererProvider.Context p_174414_) {
        this(p_174414_, 1.0F, false);
    }

    public BulletRenderer(EntityRendererProvider.Context p174414, float v, boolean b) {
        super(p174414,v,b);
        this.itemRenderer = p174414.getItemRenderer();
        this.scale = v;
    }


    @Override
    public void render(ShellEntity p_116085_, float p_116086_, float p_116087_, PoseStack p_116088_, MultiBufferSource p_116089_, int p_116090_) {
        if (p_116085_.tickCount >= 2|| !(this.entityRenderDispatcher.camera.getEntity().distanceToSqr(p_116085_) < 12.25D)) {
            p_116088_.pushPose();
            p_116088_.scale(this.scale, this.scale, this.scale);
            this.itemRenderer.renderStatic(p_116085_.getItem(), ItemTransforms.TransformType.GROUND, p_116090_, OverlayTexture.NO_OVERLAY, p_116088_, p_116089_, p_116085_.getId());
            p_116088_.popPose();
            }
        }

}
