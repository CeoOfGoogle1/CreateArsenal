package net.amik.createarsenal.shell;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

import static net.amik.createarsenal.CreateArsenal.resource;

public class DynamicBulletRenderer extends EntityRenderer<BulletEntity> implements RenderLayerParent<BulletEntity,BulletModel> {

    protected BulletModel model;

    public DynamicBulletRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.model = new BulletModel(pContext.bakeLayer(BulletModel.LAYER_LOCATION));
    }

    @Override
    protected int getSkyLightLevel(@NotNull BulletEntity pEntity, @NotNull BlockPos pPos) {
        return 15;
    }

    @Override
    protected int getBlockLightLevel(@NotNull BulletEntity pEntity, @NotNull BlockPos pPos) {
        return 15;
    }

    @Override
    public void render(BulletEntity entity, float pEntityYaw, float pPartialTicks, PoseStack ms, @NotNull MultiBufferSource pBuffer, int pPackedLight) {
        ms.pushPose();

        ms.mulPose(Vector3f.YP.rotationDegrees(Mth.lerp(pPartialTicks, entity.yRotO, entity.getYRot()) - 90.0F));
        ms.mulPose(Vector3f.ZP.rotationDegrees(Mth.lerp(pPartialTicks, entity.xRotO, entity.getXRot()) + 90.0F));

        RenderType rendertype = this.getRenderType(entity);
        if (rendertype != null) {
            VertexConsumer vertexconsumer = pBuffer.getBuffer(rendertype);
            this.model.setColor(entity.getOutsideColor(),entity.getInsideColor());
            this.model.renderToBuffer(ms, vertexconsumer, pPackedLight, pPackedLight, 1f,1f, 1f, 1.0F);
        }

        ms.popPose();
        super.render(entity, pEntityYaw, pPartialTicks, ms, pBuffer, pPackedLight);
    }

    @Override
    public @NotNull BulletModel getModel() {
        return model;
    }

    @Nullable
    protected RenderType getRenderType(BulletEntity entity) {
        ResourceLocation resourcelocation = this.getTextureLocation(entity);
        return RenderType.itemEntityTranslucentCull(resourcelocation);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull BulletEntity pEntity) {
        return resource("textures/entity/dynamic_bullet.png");
    }
}
