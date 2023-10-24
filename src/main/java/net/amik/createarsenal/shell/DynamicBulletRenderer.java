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
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

import static net.amik.createarsenal.CreateArsenal.resource;

public class DynamicBulletRenderer extends EntityRenderer<BulletEntity> implements RenderLayerParent<BulletEntity,BulletModel> {

    protected BulletModel model;

    public DynamicBulletRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.model=new BulletModel(pContext.bakeLayer(BulletModel.LAYER_LOCATION));
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
    public void render(BulletEntity entity, float pEntityYaw, float pPartialTick, PoseStack ms, @NotNull MultiBufferSource pBuffer, int pPackedLight) {

        ms.pushPose();

        Vec3 deltaMovement = entity.getDeltaMovement();

        ms.translate(0, -3 / 8f, 0);


        if (deltaMovement.z == 0) {
            if (deltaMovement.x > 0) {
                ms.translate(-1 / 16f, 0, 0f);

                ms.mulPose(Vector3f.YP.rotationDegrees(
                        90
                ));
            } else {
                ms.translate(1 / 16f, 0, 0);

                ms.mulPose(Vector3f.YP.rotationDegrees(
                        -90
                ));
            }
        }

        if (deltaMovement.x == 0) {
            if (deltaMovement.z > 0) {
                ms.translate(0f, 0, 1/16f);

                ms.mulPose(Vector3f.YP.rotationDegrees(
                        180
                ));
            } else {
                ms.translate(0, 0f, -1/16f);

                ms.mulPose(Vector3f.YP.rotationDegrees(
                        0
                ));
            }
        }

        ms.scale(.6f, .6f, 1.5f * entity.getSize().ordinal());


        RenderType rendertype = this.getRenderType(entity);
        if (rendertype != null) {
            VertexConsumer vertexconsumer = pBuffer.getBuffer(rendertype);
            this.model.setColor(entity.getOutsideColor(),entity.getInsideColor());
            this.model.renderToBuffer(ms, vertexconsumer, pPackedLight, pPackedLight, 1f,1f, 1f, 1.0F);
        }
        super.render(entity, pEntityYaw, pPartialTick, ms, pBuffer, pPackedLight);
        ms.popPose();
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
