package net.amik.createarsenal.shell;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import static net.amik.createarsenal.CreateArsenal.resource;

public class DynamicBulletRenderer extends EntityRenderer<BulletEntity> {

    protected BulletModel model;

    public DynamicBulletRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.model = new BulletModel(pContext.bakeLayer(BulletModel.LAYER_LOCATION));
    }


    @Override
    public void render(BulletEntity entity, float pEntityYaw, float pPartialTicks, PoseStack ms, @NotNull MultiBufferSource pBuffer, int pPackedLight) {
        ms.pushPose();

        ms.mulPose(Axis.YP.rotationDegrees(entity.getYRot() - 90.0F));
        ms.mulPose(Axis.ZP.rotationDegrees(90.0F));

        if (entity.getSize().equals(ShellScale.SMALL))
            ms.translate(0, 1, 0);
        ms.translate(0, -1 * entity.getSize().ordinal(), -.075f);
        ms.scale(.6f, 1f * entity.getSize().ordinal(), .6f);

        this.model.setColor(entity.getOutsideColor(), entity.getInsideColor());
        this.model.render(ms, pBuffer);

        ms.popPose();
        super.render(entity, pEntityYaw, pPartialTicks, ms, pBuffer, pPackedLight);
    }


    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull BulletEntity pEntity) {
        return resource("textures/entity/dynamic_bullet.png");
    }
}
