package net.amik.createarsenal.shell;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class BulletRenderer extends EntityRenderer<BulletEntity> {

    public BulletRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected int getBlockLightLevel(@NotNull BulletEntity pEntity, @NotNull BlockPos pPos) {
        return 15;
    }

    @Override
    public void render(BulletEntity entity, float yaw, float pt, @NotNull PoseStack ms, @NotNull MultiBufferSource buffer,
                       int light) {
        ItemStack item = entity.getItem();
        if (item.isEmpty())
            return;

        ms.pushPose();

        if (entity.getDeltaMovement().get(Direction.Axis.X) == 0)
            ms.translate(-1/16f, 1/8f, -3/16f);
        else
            ms.translate(-3/16f, 1/8f, -1/16f);

        ms.mulPose(Vector3f.YP.rotationDegrees(entity.getDeltaMovement().get(Direction.Axis.X) == 0 ? 90 : 0));

        Minecraft.getInstance()
                .getItemRenderer()
                .renderStatic(item, ItemTransforms.TransformType.GROUND, light, OverlayTexture.NO_OVERLAY, ms, buffer, 0);

        ms.popPose();
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull BulletEntity entity) {
        //noinspection DataFlowIssue
        return null;
    }
}
