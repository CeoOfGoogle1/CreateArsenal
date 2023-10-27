package net.amik.createarsenal.shell;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

import static net.amik.createarsenal.CreateArsenal.resource;

public class DynamicBulletRenderer extends EntityRenderer<BulletEntity> implements RenderLayerParent<BulletEntity,BulletModel> {

    protected BulletModel model;

    public DynamicBulletRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.model = new BulletModel(pContext.bakeLayer(BulletModel.LAYER_LOCATION));
    }


    @Override
    public void render(BulletEntity entity, float pEntityYaw, float pPartialTicks, PoseStack ms, @NotNull MultiBufferSource pBuffer, int pPackedLight) {
        ms.pushPose();

        ms.mulPose(Vector3f.YP.rotationDegrees(Mth.lerp(pPartialTicks, entity.yRotO, entity.getYRot()) - 90.0F));
        ms.mulPose(Vector3f.ZP.rotationDegrees(90.0F));

        if (entity.getSize().equals(ShellScale.SMALL))
            ms.translate(0, 1, 0);
        ms.translate(0, -1 * entity.getSize().ordinal(), -.075f);
        ms.scale(.6f, 1f * entity.getSize().ordinal(), .6f);

        this.model.setColor(entity.getOutsideColor(), entity.getInsideColor());
        this.model.customRender(ms, pBuffer);

        ms.popPose();
        super.render(entity, pEntityYaw, pPartialTicks, ms, pBuffer, pPackedLight);
    }

    @Override
    public @NotNull BulletModel getModel() {
        return model;
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull BulletEntity pEntity) {
        return resource("textures/entity/dynamic_bullet.png");
    }
}
