package net.amik.createarsenal.block.aerialBombs;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.foundation.blockEntity.renderer.SmartBlockEntityRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemStack;

public class AerialBombBlockEntityRenderer extends SmartBlockEntityRenderer<AerialBombBlockEntity> {

    public AerialBombBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void renderSafe(AerialBombBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        super.renderSafe(be, partialTicks, ms, buffer, light, overlay);
        if (be.count < 2)
            return;

        BakedModel model = Minecraft.getInstance().getBlockRenderer().getBlockModel(be.getBlockState());
        if (be.maxCount == 9) {
            for (int i = 0; i < be.count; i++) {
                if (i == 1) // original block model contains this bomb already so skip it
                    continue;
                float xTranslation = (i % 3 - 1) * 0.33f;
                float yTranslation = (i / 3) * 0.33f;

                ms.pushPose();
                ms.translate(xTranslation, yTranslation, 0);
                Minecraft.getInstance()
                        .getItemRenderer()
                        .renderModelLists(model, ItemStack.EMPTY, light, OverlayTexture.NO_OVERLAY, ms, buffer.getBuffer(RenderType.cutout()));
                ms.popPose();
            }
        }

        if (be.maxCount == 4) {
            float[][] translations = {
                    {0.33f, 0.33f, 0},
                    {-0.33f, 0.33f, 0},
                    {0, 0.67f, 0}
            };

            for (int i = 0; i < be.count - 1; i++) {
                ms.pushPose();
                ms.translate(translations[i][0], translations[i][1], translations[i][2]);
                Minecraft.getInstance()
                        .getItemRenderer()
                        .renderModelLists(model, ItemStack.EMPTY, light, OverlayTexture.NO_OVERLAY, ms, buffer.getBuffer(RenderType.cutout()));
                ms.popPose();
            }
        }



    }
}
