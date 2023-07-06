package net.amik.createarsenal.shell;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.amik.createarsenal.registrate.ModPartials;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
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

        ms.pushPose();

        Vec3 deltaMovement = entity.getDeltaMovement();

        ms.translate(0, 3/16f, 0);

        if (deltaMovement.x == 0) {
            if (deltaMovement.z > 0) {
                ms.translate(-1/16f, 0, 0f);

                ms.mulPose(Vector3f.YP.rotationDegrees(
                        90
                ));
            } else {
                ms.translate(1/16f, 0, 0);

                ms.mulPose(Vector3f.YP.rotationDegrees(
                        -90
                ));
            }
        }

        if (deltaMovement.z == 0) {
            if (deltaMovement.x > 0) {
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

        ModPartials.MEDIUM_ROUND.get();

        Minecraft.getInstance()
                .getItemRenderer()
                .renderModelLists(ModPartials.MEDIUM_ROUND.get(), ItemStack.EMPTY, light, OverlayTexture.NO_OVERLAY, ms, buffer.getBuffer(RenderType.cutout()));

        ms.popPose();
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull BulletEntity entity) {
        //noinspection DataFlowIssue
        return null;
    }
}
