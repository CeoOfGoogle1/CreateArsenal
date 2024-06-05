package net.amik.createarsenal.block.seaMine;

import com.mojang.blaze3d.vertex.PoseStack;
import net.amik.createarsenal.registrate.ModBlocks;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;

public class FallingSeaMineRenderer extends EntityRenderer<FallingSeaMineEntity> {
    private final BlockRenderDispatcher dispatcher;

    public FallingSeaMineRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.5F;
        this.dispatcher = context.getBlockRenderDispatcher();
    }

    public void render(FallingSeaMineEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        BlockState blockstate = ModBlocks.SEA_MINE.getDefaultState();
        if (blockstate.getRenderShape() == RenderShape.MODEL) {
            Level level = entity.level();
            if (blockstate != level.getBlockState(entity.blockPosition()) && blockstate.getRenderShape() != RenderShape.INVISIBLE) {
                poseStack.pushPose();
                BlockPos blockpos = BlockPos.containing(entity.getX(), entity.getBoundingBox().maxY, entity.getZ());
                poseStack.translate(-0.5, 0.0, -0.5);
                BakedModel model = this.dispatcher.getBlockModel(blockstate);
                this.dispatcher
                        .getModelRenderer()
                        .tesselateBlock(
                                level,
                                model,
                                blockstate,
                                blockpos,
                                poseStack,
                                buffer.getBuffer(RenderType.cutoutMipped()),
                                false,
                                RandomSource.create(),
                                blockstate.getSeed(entity.blockPosition()),
                                OverlayTexture.NO_OVERLAY,
                                ModelData.EMPTY,
                                RenderType.cutoutMipped()
                        );
                poseStack.popPose();
                super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
            }
        }
    }


    /**
     * Returns the location of an entity's texture.
     */
    public ResourceLocation getTextureLocation(FallingSeaMineEntity entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }

}
